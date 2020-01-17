import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../authentication/token-storage.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  isLoggedIn: boolean;
  role: string;

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    this.role = this.tokenStorageService.getRole();
  }

  logOut() {
    this.tokenStorageService.signOut();

    window.location.reload();
  }

}
