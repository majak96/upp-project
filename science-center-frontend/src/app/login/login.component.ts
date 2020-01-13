import { Validators, FormGroup, FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication/authetication-service.service';
import { TokenStorageService } from '../authentication/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private autheticationService: AuthenticationService,
              private tokenStorageService: TokenStorageService,
              private router: Router) { }

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  onSubmit() {
    this.autheticationService.submitLoginForm(
      {username: this.loginForm.value['username'], password: this.loginForm.value['password']}).subscribe(
      data => {
        console.log('Successfully logged in.');

        this.tokenStorageService.saveToken(data.token);
        this.tokenStorageService.saveUsername(data.username);

        window.location.reload();
      },
      error => {
        alert('Incorrect username or password!');
      }
    );
  }

}
