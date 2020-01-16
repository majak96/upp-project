import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Field } from '../model/field';
import { MagazineService } from '../services/magazine.service';
import { Router } from '@angular/router';
import { Value } from '../model/value';

@Component({
  selector: 'app-new-magazine',
  templateUrl: './new-magazine.component.html',
  styleUrls: ['./new-magazine.component.css']
})
export class NewMagazineComponent implements OnInit {

  magazineForm: FormGroup;
  fieldList: Field[];
  taskId: string;
  processId: string;

  firstForm: boolean = true;

  constructor(private magazineService: MagazineService,
              private router: Router) { }

  ngOnInit() {
    this.initializeRegistrationForm();
  }

  // sends a request to start the new magazine process
  // gets the list of fields for the form
  initializeRegistrationForm()  {
    this.magazineService.startNewMagazineProcess().subscribe(
      data => {
        console.log('Started the new magazine process.');
        this.taskId = data.taskId;
        this.fieldList = data.fieldList;
        this.processId = data.processId;

        console.log(this.processId);

        this.magazineForm = this.createFormGroup(data.fieldList);
      },
      error => {
        alert('An error occured.');
      }
    );
  }

  // gets the list of fields for the form for editors and reviewers
  initializeUsersForm()  {

    console.log(this.processId);

    this.magazineService.getUserFields(this.processId).subscribe(
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

      group[field.id] = new FormControl('', validators);

      if (field.type === 'boolean') {
        group[field.id].value = false;
      }

      if (field.type === 'enum' && field.multiple === false) {
        console.log(field.values.entries);
      }

    });

    return new FormGroup(group);
  }

  // submits the data for a new magazine
  onSubmit() {

    const valuesList = new Array<Value>();

    this.fieldList.forEach(field => {
      valuesList.push({id: field.id, value: this.magazineForm.value[field.id].toString()});
    });

    console.log(valuesList);

    if (this.firstForm) {
      this.magazineService.submitMagazineForm(valuesList, this.taskId).subscribe(
        data => {
          alert('You have successfully added a new magazine!');

          this.firstForm = false;
          this.initializeUsersForm();
        },
        error => {
          alert(error.error);
        }
      );
    } else {
      this.magazineService.submitUsersForm(valuesList, this.taskId).subscribe(
        data => {
          alert('You have successfully added reviewers and editors!');

          this.router.navigateByUrl('');
        },
        error => {
          alert(error.error);
        }
      );
    }

  }

    // validates form
    validateForm() {
      if(this.firstForm) {
        const scientificAreas: string[] = this.magazineForm.value['form_scientific_area'];

        if (this.magazineForm.valid && scientificAreas.length >= 1) {
          return true;
        }
      } else {
        const reviewers: string[] = this.magazineForm.value['form_reviewers'];

        if (this.magazineForm.valid && reviewers.length >= 2) {
          return true;
        }
      }

      return false;
    }
}
