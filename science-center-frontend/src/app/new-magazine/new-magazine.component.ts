import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Field } from '../model/field';
import { MagazineService } from '../services/magazine.service';
import { Router } from '@angular/router';
import { Value } from '../model/value';
import { TaskService } from '../services/task.service';
import { listLazyRoutes } from '@angular/compiler/src/aot/lazy_routes';

@Component({
  selector: 'app-new-magazine',
  templateUrl: './new-magazine.component.html',
  styleUrls: ['./new-magazine.component.css']
})
export class NewMagazineComponent implements OnInit {

  magazineForm: FormGroup;
  fieldList: Field[];
  taskId: string;
  nextTaskId: string;
  processId: string;
  taskName: string;

  constructor(private magazineService: MagazineService,
              private taskService: TaskService,
              private router: Router) { }

  ngOnInit() {
    this.taskName = 'Add a new magazine';
    this.initializeNewMagazineForm();
  }

  // sends a request to start the new magazine process
  // gets the list of fields for the form
  initializeNewMagazineForm()  {
    this.magazineService.startNewMagazineProcess().subscribe(
      data => {
        console.log('Started the new magazine process.');
        this.taskId = data.taskId;
        this.fieldList = data.fieldList;
        this.processId = data.processId;

        console.log(data.fieldList);

        this.magazineForm = this.createFormGroup(data.fieldList);
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

  getNextTask() {
    this.taskService.getTask(this.nextTaskId).subscribe(
      data => {
        this.taskId = data.taskId;
        this.fieldList = data.fieldList;
        this.taskName = data.taskName;

        this.magazineForm = this.createFormGroup(data.fieldList);
      },
      error => {
        alert('An error occured.');
      }
    );
  }

  // submits the data from the form
  onSubmit() {
    // create a list of objects to submit
    const valuesList = new Array<Value>();

    this.fieldList.forEach(field => {
      if (field.type === 'enum' && field.multiple === false) {
        valuesList.push({id: field.id, value: this.magazineForm.value[field.id].toString()});
      } else if (field.type === 'enum' && field.multiple === true) {
        console.log(field.id + this.magazineForm.value[field.id].length);
        if (this.magazineForm.value[field.id].length >= 1) {
          valuesList.push({id: field.id, value: this.magazineForm.value[field.id]});
        } else {
          valuesList.push({id: field.id, value: []});
        }
      } else {
        valuesList.push({id: field.id, value: this.magazineForm.value[field.id]});
      }
    });

    console.log(valuesList);

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
            alert('Form successfully submited.');
            this.getNextTask();
          }

        } else {
          alert('Successfully created a new magazine.');
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
        if (field.minNumber != null && field.type === 'enum' && field.multiple) {
          list = this.magazineForm.value[field.id];

          if (list.length < field.minNumber) {
            result = false;
          }
        }


      });

      // also check if the rest of the fields are valid
      return result && this.magazineForm.valid;
    }

}
