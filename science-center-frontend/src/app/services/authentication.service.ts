import { JwtResponse } from '../model/jwtresponse';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Login } from '../model/login';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  baseUrl = 'https://localhost:9997/login';

  constructor(private http: HttpClient) { }

  submitLoginForm(formValues: Login): Observable<JwtResponse> {

    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json' })
    };

    return this.http.post<JwtResponse>(this.baseUrl, formValues, httpOptions);
  }
}

