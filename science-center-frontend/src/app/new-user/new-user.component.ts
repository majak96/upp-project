import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ScientificArea } from '../model/scientificarea';
import { ScientificAreaService } from '../services/scientificareaservice';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent implements OnInit {

  userForm: FormGroup;

  scientificAreas: ScientificArea[];

  constructor(private scientificAreaService: ScientificAreaService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit() {

    this.getScientificAreas();

    // initialize the form
    this.userForm = new FormGroup(
      {
        firstName: new FormControl('', [Validators.required]),
        lastName: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required]),
        country: new FormControl('', [Validators.required]),
        title: new FormControl(''),
        email: new FormControl('', [Validators.required, Validators.email]),
        username: new FormControl('', [Validators.required]),
        password: new FormControl('', [Validators.required]),
        role: new FormControl('Administrator', [Validators.required]),
        scientificAreas: new FormControl('', [])
      }
    );
  }

  getScientificAreas() {
    this.scientificAreaService.getScientificAreas().subscribe(
      data => {
        this.scientificAreas = data;
      },
      error => {
        alert('An error occurred. Please try again!')
      }
    );
  }

  // submit form and create a new user
  onSubmit() {
    const valuesList: any = {
      firstName: this.userForm.value.firstName,
      lastName: this.userForm.value.lastName,
      city: this.userForm.value.city,
      country: this.userForm.value.country,
      title: this.userForm.value.title,
      email: this.userForm.value.email,
      username: this.userForm.value.username,
      password: this.userForm.value.password,
      role: this.userForm.value.role === 'Administrator' ? 'ROLE_ADMINISTRATOR' : 'ROLE_EDITOR',
      scientificAreas: this.userForm.value.scientificAreas.length >= 1 ? this.userForm.value.scientificAreas : []
    };

    this.userService.addUser(valuesList).subscribe(
      data => {
        this.router.navigateByUrl('/users');
        alert('Successfully added a new user!');
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

  validateUserForm(): boolean {
    if (this.userForm.value.role === 'Administrator') {
      return this.userForm.valid;
    } else {
      return this.userForm.valid && (this.userForm.value.scientificAreas.length >= 1);
    }
  }

}
