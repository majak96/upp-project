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

  constructor(private actrouter: ActivatedRoute,
              private router: Router,
              private taskService: TaskService) { }

  id: string;

  ngOnInit() {
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
        console.log(data.fieldList);
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
    const group: any = {};

    fields.forEach(field => {

      const validators = [];

      if (field.required) {
        validators.push(Validators.required);
      }

      group[field.id] = new FormControl('', validators);

      if (field.type === 'boolean') {
        group[field.id].value = false;
        console.log(field.label);
      }

      if (field.type === 'enum' && field.multiple === false) {
        console.log(field.values.entries);
      }

    });

    return new FormGroup(group);
  }

  // submits the task
  onSubmit() {
    const valuesList = new Array<Value>();

    this.fieldList.forEach(field => {
      valuesList.push({id: field.id, value: this.taskForm.value[field.id].toString()});
    });

    this.taskService.submitTask(valuesList, this.taskId).subscribe(
      data => {
        alert('You have successfully submitted your task!');

        this.router.navigateByUrl('/tasks');
      },
      error => {
        alert(error.error);
      }
    );
  }

}
