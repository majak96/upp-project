import { Value } from './../model/value';
import { RegistrationService } from '../services/registration.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Field } from '../model/field';
import { TaskService } from '../services/task.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registrationForm: FormGroup;
  fieldList: Field[];
  taskId: string;
  nextTaskId: string;
  taskName: string;

  constructor(private registrationService: RegistrationService,
              private taskService: TaskService,
              private router: Router) { }

  ngOnInit() {
    this.taskName = 'Create an account';
    this.initializeRegistrationForm();
  }

  // sends a request to start the registration process
  // gets the list of fields for the registration form
  initializeRegistrationForm()  {
    this.registrationService.startRegistrationProcess().subscribe(
      data => {
        this.taskId = data.taskId;
        this.fieldList = data.fieldList;

        this.registrationForm = this.createFormGroup(data.fieldList);
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

      group[field.id] = new FormControl('', validators);

      if (field.type === 'boolean') {
        group[field.id].value = false;
      }

    });

    const formgroup = new FormGroup(group);

    fields.forEach(field => {
      if (field.value !== null) {
        formgroup.get(field.id).setValue(field.value);
      }
    });

    return formgroup;
  }

  // gets task fields with the task id
  getNextTask() {
    this.taskService.getTask(this.nextTaskId).subscribe(
      data => {
        this.taskId = data.taskId;
        this.fieldList = data.fieldList;
        this.taskName = data.taskName;

        this.registrationForm = this.createFormGroup(data.fieldList);
      },
      error => {
        alert('An error occured.');
      }
    );
  }

  // submits the data from the registration form
  onSubmit() {
    // create a list of objects to submit
    const valuesList = new Array<Value>();

    this.fieldList.forEach(field => {
      if (field.type === 'enum' && field.multiple === false) {
        valuesList.push({id: field.id, value: this.registrationForm.value[field.id].toString()});
      } else {
        valuesList.push({id: field.id, value: this.registrationForm.value[field.id]});
      }
    });

    // submit the data
    this.taskService.submitFormTask(valuesList, this.taskId).subscribe(
      data => {
        this.nextTaskId = data.nextTask;

        if (this.nextTaskId !== null) {
          // invalid data
          if (data.valid === false) {
            alert('The data you entered is not valid. Please try again!');
            this.getNextTask();
          } else {
            alert('Successfully registered! Please check your email to confirm your account.');
            this.router.navigateByUrl('');
          }

        } else {
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

    // validates form
    validateForm() {

      let result = true;

      // check required enum fields
      this.fieldList.forEach(field => {
        let list: string[] = [];
        if (field.minNumber != null && field.type === 'enum') {
          list = this.registrationForm.value[field.id];

        }

        if (list.length < field.minNumber) {
          result = false;
        }
      });

      // also check if the rest of the fields are valid
      return result && this.registrationForm.valid;
    }

}
