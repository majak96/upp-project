import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { TokenStorageService } from './token-storage.service';


const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private token: TokenStorageService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    let authRequest = request;

    const token = this.token.getToken();
    if (token != null) {
      authRequest = request.clone({ headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer' + token) });
    }

    return next.handle(authRequest);
  }
}
