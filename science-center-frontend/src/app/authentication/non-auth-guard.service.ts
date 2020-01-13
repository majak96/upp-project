import { TokenStorageService } from './token-storage.service';
import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';


@Injectable()
export class NonAuthGuardService implements CanActivate {


  constructor(public tokenStorageService: TokenStorageService,
              public router: Router) { }


  canActivate(): boolean {
    if (this.tokenStorageService.getToken()) {

      this.router.navigate(['']);

      return false;
    }
    return true;
  }
}