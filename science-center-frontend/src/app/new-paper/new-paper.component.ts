import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Field } from '../model/field';
import { TaskService } from '../services/task.service';
import { Router } from '@angular/router';
import { PaperService } from '../services/paper.service';
import { Value } from '../model/value';

@Component({
  selector: 'app-new-paper',
  templateUrl: './new-paper.component.html',
  styleUrls: ['./new-paper.component.css']
})
export class NewPaperComponent implements OnInit {

  form: FormGroup;
  fieldList: Field[];
  taskId: string;
  nextTaskId: string;
  processId: string;

  constructor(private paperService: PaperService,
              private router: Router,
              private taskService: TaskService) { }

  ngOnInit() {

    this.initializeFirstForm();
  }

  // sends a request to start the new paper process
  // gets the list of fields for the form
  initializeFirstForm()  {
    this.paperService.startNewPaperProcess().subscribe(
      data => {
        console.log('Started the new paper process.');
        this.taskId = data.taskId;
        this.fieldList = data.fieldList;
        this.processId = data.processId;

        this.form = this.createFormGroup(data.fieldList);
      },
      error => {
        alert('An error occured.');
      }
    );
  }

  // creates a form group from a list of fields
  createFormGroup(fields: Field[]) {
    const group: any = {};

    fields.forEach(field => {

      const validators = [];

      if (field.required) {
        validators.push(Validators.required);
      }
      if (field.email) {
        validators.push(Validators.email);
      }

      if (field.type === 'long' && field.minNumber != null) {
        validators.push(Validators.min(field.minNumber));
      }

      if (field.type === 'enum' && !field.multiple) {
        group[field.id] = new FormControl(Object.keys(field.values)[0], validators);
      } else {
        group[field.id] = new FormControl('', validators);
      }

      if (field.type === 'boolean') {
        group[field.id].value = false;
      }

    });

    return new FormGroup(group);
  }

  // get task fields with the task id
  getTask(taskId: string) {
    this.taskService.getTask(taskId).subscribe(
      data => {
        this.taskId = data.taskId;
        this.fieldList = data.fieldList;

        this.form = this.createFormGroup(data.fieldList);
      },
      error => {
        alert('An error occurred.');
      }
    );
  }

  // submit data from the form
  onSubmit() {
    // create a list of objects to submit
    const valuesList = new Array<Value>();

    this.fieldList.forEach(field => {
      if (field.type === 'enum' && field.multiple === false) {
        valuesList.push({id: field.id, value: this.form.value[field.id].toString()});
      } else {
        valuesList.push({id: field.id, value: this.form.value[field.id]});
      }
    });

    // submit the data
    this.taskService.submitFormTask(valuesList, this.taskId).subscribe(
      data => {
        this.nextTaskId = data.nextTask;

        if (data.redirectLink != null) {
          document.location.href  = data.redirectLink;
        }

        if (this.nextTaskId !== null) {

          // invalid data
          if (data.valid === false) {
            alert('The data you entered is not valid. Please try again!');
          } else {
            alert('Form successfully submited.');
          }

          this.getTask(data.nextTask);
        } else {
          alert('Task successfully finished.');
          this.router.navigateByUrl('');
        }

      },
      error => {
        if (error.status === 400) {
          alert(error.error);
        } else {
          alert('An error occured! Please try again.');
        }
      }

    );
  }

}
