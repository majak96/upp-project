import { Value } from './../model/value';
import { RegistrationService } from '../services/registration.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Field } from '../model/field';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registrationForm: FormGroup;
  fieldList: Field[];
  taskId: string;

  constructor(private registrationService: RegistrationService,
              private router: Router) { }

  ngOnInit() {
    this.initializeRegistrationForm();
  }

  // sends a request to start the registration process
  // gets the list of fields for the registration form
  initializeRegistrationForm()  {
    this.registrationService.startRegistrationProcess().subscribe(
      data => {
        console.log('Started the registration process.');
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

    return new FormGroup(group);
  }

  // submits the registration data
  onSubmit() {
    const valuesList = new Array<Value>();

    this.fieldList.forEach(field => {
      valuesList.push({id: field.id, value: this.registrationForm.value[field.id].toString()});
    });

    this.registrationService.submitRegistrationForm(valuesList, this.taskId).subscribe(
      data => {
        alert('Successfully registered! Please check your email to confirm your account.');

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

  // validates form
  validateForm() {
    const scientificAreas: string[] = this.registrationForm.value['form_scientific_area'];

    if (this.registrationForm.valid && scientificAreas.length >= 1) {
      return true;
    }

    return false;
  }

}
