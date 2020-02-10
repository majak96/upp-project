import { Validators, FormGroup, FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../authentication/token-storage.service';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  constructor(private autheticationService: AuthenticationService,
              private tokenStorageService: TokenStorageService,
              private router: Router) { }

  ngOnInit() {

  }

  // submit form values and log in
  onSubmit() {
    const formValues = {username: this.loginForm.value['username'],
                      password: this.loginForm.value['password']}


    this.autheticationService.submitLoginForm(formValues).subscribe(
      data => {
        console.log('Successfully logged in.');

        this.tokenStorageService.saveToken(data.token);
        this.tokenStorageService.saveUsername(data.username);
        this.tokenStorageService.saveRole(data.role);

        window.location.reload();
      },
      error => {
        if (error.status === 401) {
          alert('Incorrect username or password!');
        } else {
          alert('An error occured! Please try again.');
        }
      }
    );
  }

}
