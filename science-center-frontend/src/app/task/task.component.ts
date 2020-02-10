import { UploadService } from './../services/upload.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from '../services/task.service';
import { Field } from '../model/field';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { Value } from '../model/value';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  taskForm: FormGroup;
  taskId: string;
  taskName: string;
  fieldList: Field[];
  nextTaskId: string;
  processId: string;

  selectedFiles: FileList;
  currentFileUpload: File;

  downloadLink: string;

  constructor(private actrouter: ActivatedRoute,
              private router: Router,
              private taskService: TaskService,
              private sanitizer: DomSanitizer,
              private uploadService: UploadService) { }

  id: string;

  ngOnInit() {
    // get task id
    this.actrouter.paramMap.subscribe(
      params => {
        this.id = params.get('id');
      });

    this.downloadLink = '';
    this.getTask();
  }

  // get form fields for the task with this id
  getTask() {
    this.taskService.getTask(this.id).subscribe(
      data => {
        this.fieldList = data.fieldList;
        this.taskId = data.taskId;
        this.taskName = data.taskName;
        this.processId = data.processId;
        this.taskForm = this.createFormGroup(data.fieldList);
      },
      error => {
        if (error.status === 403) {
          alert('You must be logged in to see the task!');
        } else if (error.status === 404) {
          alert('Task with this id doesn\'t exist!');
        } else {
          alert('An error occured! Please try again.');
        }
      }
    );
  }

  // creates a form group from a list of fields
  createFormGroup(fields: Field[]) {

    console.log(fields);

    const group: any = {};

    fields.forEach(field => {

      if (!field.download) {
        const validators = [];

        if (field.required) {
          validators.push(Validators.required);
        }

        if (field.type === 'long' && field.minNumber != null) {
          validators.push(Validators.min(field.minNumber));
        }

        if (field.type === 'enum' && !field.multiple && field.value == null) {
          group[field.id] = new FormControl(Object.keys(field.values)[0], validators);
        } else if (field.type === 'enum' && !field.multiple && field.value != null) {
          group[field.id] = new FormControl(field.value, validators);
        } else {
          group[field.id] = new FormControl('', validators);
        }

        if (field.type === 'boolean') {
          group[field.id].value = false;
        }
      } else {
        this.downloadLink = field.value;
      }
    });

    const formgroup = new FormGroup(group);

    fields.forEach(field => {
      if (!field.download){

        if (field.value !== null) {
          formgroup.get(field.id).setValue(field.value);
        }

        if (field.readonly) {
          formgroup.get(field.id).disable();
        }
      }
    });

    console.log(formgroup.getRawValue());

    return formgroup;
  }

  // gets task fields with the task id
  getNextTask() {
    this.taskService.getTask(this.nextTaskId).subscribe(
      data => {
        this.taskId = data.taskId;
        this.fieldList = data.fieldList;
        this.taskName = data.taskName;

        this.taskForm = this.createFormGroup(data.fieldList);
      },
      error => {
        alert('An error occured.');
      }
    );
  }

  onSubmit() {
    let upload = false;
    this.fieldList.forEach(field => {
      if (field.upload === true) {
        upload = true;
      }
    });

    if (upload) {
      this.currentFileUpload = this.selectedFiles.item(0);
      this.uploadService.uploadFile(this.currentFileUpload, this.processId).subscribe(
        data => {
          console.log('File is completely uploaded!');

          this.submitForm();
        },
        errors => {
          alert('There was an error while uplodaing the file');
        }
      );

      this.selectedFiles = undefined;
    } else {
      this.submitForm();
    }
  }

  // submits the task
  submitForm() {
    const valuesList = new Array<Value>();

    console.log(valuesList);

    this.fieldList.forEach(field => {
      if (field.download) {

      } else if (field.type === 'enum' && field.multiple === false) {
        valuesList.push({id: field.id, value: this.taskForm.getRawValue()[field.id].toString()});
      } else {
        valuesList.push({id: field.id, value: this.taskForm.getRawValue()[field.id]});
      }
    });

    this.taskService.submitFormTask(valuesList, this.taskId).subscribe(
      data => {
        /*alert('You have successfully submitted your task!');

        this.router.navigateByUrl('/tasks');*/
        this.nextTaskId = data.nextTask;

        if (this.nextTaskId !== null) {
          // invalid data
          if (data.valid === false) {
            alert('The data you entered is not valid. Please try again!');
            this.getNextTask();
          } else {
            alert('You have successfully submitted your task!');
            this.getNextTask();
          }

        } else {
          alert('You have successfully submitted your task!');
          this.router.navigateByUrl('/tasks');
        }
      },
      error => {
        if (error.status !== 500) {
          alert(error.error);
        } else {
          alert('An error occured! Please try again.');
        }
      }
    );
  }


    // validates form
    validateForm() {

      let result = true;

      // check required enum fields
      this.fieldList.forEach(field => {
        let list: string[] = [];
        if (field.minNumber != null && field.type === 'enum') {
          list = this.taskForm.value[field.id];

          if (list.length < field.minNumber) {
            result = false;
          }
        }

      });

      let upload = false;
      this.fieldList.forEach(field => {
        if (field.upload === true) {
          upload = true;
        }
      });

      if (upload) {
        return this.taskForm.valid && (this.selectedFiles !== undefined);
      } else {
        return result && this.taskForm.valid;
      }

    }

    public getSantizeUrl(url: string) {
      return this.sanitizer.bypassSecurityTrustResourceUrl(url);
    }

    selectFile(event) {
      this.selectedFiles = event.target.files;
    }

}
