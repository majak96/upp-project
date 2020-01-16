import { TokenStorageService } from './token-storage.service';
import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';


@Injectable({ providedIn: 'root' })
export class RoleGuardService implements CanActivate {
    constructor(
        private router: Router,
        private tokenStorageService: TokenStorageService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
      // if no one is logged in
      if (!!this.tokenStorageService.getToken()) {

        // if the role is not right
        if (this.tokenStorageService.getRole() !== route.data.role) {

          this.router.navigate(['/']);
          return false;
        }

        return true;
      }

      this.router.navigate(['/login']);

      return false;
    }
}
