import { Form } from '../model/form';
import { Value } from '../model/value';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  baseUrl = 'http://localhost:9997/registration/';

  httpOptions = {
    headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  startRegistrationProcess(): Observable<Form> {

    return this.http.get<Form>(this.baseUrl, this.httpOptions);
  }

  submitRegistrationForm(formValues: Value[], taskId: string) {

    return this.http.post(this.baseUrl + taskId, formValues, this.httpOptions);
  }
}

