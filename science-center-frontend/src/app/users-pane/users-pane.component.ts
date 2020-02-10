import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from '../model/user';

@Component({
  selector: 'app-users-pane',
  templateUrl: './users-pane.component.html',
  styleUrls: ['./users-pane.component.css']
})
export class UsersPaneComponent implements OnInit {

  constructor(private userService: UserService) { }

  administrators: User[];
  editors: User[];

  ngOnInit() {

    // fill the list
    this.getAdministrators();
    this.getEditors();
  }


  getAdministrators() {
    this.userService.getUsers('ROLE_ADMINISTRATOR').subscribe(
      data => {
        this.administrators = data;

        console.log(this.administrators);
      },
      error => {
        alert('An error occurred. Please try again!');
      }
    );
  }

  getEditors() {
    this.userService.getUsers('ROLE_EDITOR').subscribe(
      data => {
        this.editors = data;

        console.log(this.editors);
      },
      error => {
        alert('An error occurred. Please try again!');
      }
    );
  }
}
