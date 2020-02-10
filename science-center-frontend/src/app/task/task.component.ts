import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from '../services/task.service';
import { Field } from '../model/field';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { Value } from '../model/value';

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

  constructor(private actrouter: ActivatedRoute,
              private router: Router,
              private taskService: TaskService) { }

  id: string;

  ngOnInit() {
    // get task id
    this.actrouter.paramMap.subscribe(
      params => {
        this.id = params.get('id');
      });

    this.getTask();
  }

  // get form fields for the task with this id
  getTask() {
    this.taskService.getTask(this.id).subscribe(
      data => {
        this.fieldList = data.fieldList;
        this.taskId = data.taskId;
        this.taskName = data.taskName;

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

    });

    const formgroup = new FormGroup(group);

    fields.forEach(field => {
      if (field.value !== null) {
        formgroup.get(field.id).setValue(field.value);
      }

      if (field.readonly) {
        formgroup.get(field.id).disable();
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

        this.taskForm = this.createFormGroup(data.fieldList);
      },
      error => {
        alert('An error occured.');
      }
    );
  }

  // submits the task
  onSubmit() {
    const valuesList = new Array<Value>();

    console.log(valuesList);

    this.fieldList.forEach(field => {
      if (field.type === 'enum' && field.multiple === false) {
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
        }

        if (list.length < field.minNumber) {
          result = false;
        }
      });

      // also check if the rest of the fields are valid
      return result && this.taskForm.valid;
    }

}
