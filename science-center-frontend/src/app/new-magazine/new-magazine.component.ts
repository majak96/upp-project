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

  firstForm = true;

  constructor(private magazineService: MagazineService,
              private taskService: TaskService,
              private router: Router) { }

  ngOnInit() {
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

        this.magazineForm = this.createFormGroup(data.fieldList);
      },
      error => {
        alert('An error occured.');
      }
    );
  }

  // gets the list of fields for the form for editors and reviewers
  initializeUsersForm()  {
    this.taskService.getTask(this.nextTaskId).subscribe(
      data => {
        this.taskId = data.taskId;
        this.fieldList = data.fieldList;
        this.processId = data.processId;

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

      if (field.type === 'enum' && !field.multiple) {
        group[field.id] = new FormControl(Object.keys(field.values)[1], validators);
      } else {
        group[field.id] = new FormControl('', validators);
      }

      if (field.type === 'boolean') {
        group[field.id].value = false;
      }

    });

    return new FormGroup(group);
  }

  // submits the data
  onSubmit() {

    const valuesList = new Array<Value>();

    this.fieldList.forEach(field => {
      valuesList.push({id: field.id, value: this.magazineForm.value[field.id].toString()});
    });

    // submits the data for a new magazine
    if (this.firstForm) {
      this.magazineService.submitMagazineForm(valuesList, this.taskId).subscribe(
        data => {
          alert('You have successfully added a new magazine!');

          this.firstForm = false;
          this.nextTaskId = data.nextTask;

          this.initializeUsersForm();
        },
        error => {
          if (error.status === 400) {
            alert(error.error);
          } else {
            alert('An error occured! Please try again.');
          }
        }
      );
    // submits the data for the reviewers and editors
    } else {
      this.magazineService.submitUsersForm(valuesList, this.taskId).subscribe(
        data => {
          alert('You have successfully added reviewers and editors!');

          this.router.navigateByUrl('');
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

    // validates form
    validateForm() {

      let result = true;

      // check required enum fields
      this.fieldList.forEach(field => {
        let list: string[] = [];
        if (field.minNumber != null && field.type === 'enum') {
          list = this.magazineForm.value[field.id];

        }

        if (list.length < field.minNumber) {
          result = false;
        }
      });

      // also check if the rest of the fields are valid
      return result && this.magazineForm.valid;
    }

}
