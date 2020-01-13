import { Form } from './../model/form';
import { Value } from './../model/value';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Field } from '../model/field';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  baseUrl = 'http://localhost:9997/registration/';

  constructor(private http: HttpClient) { }

  startRegistrationProcess(): Observable<Form> {

    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };

    return this.http.get<Form>(this.baseUrl, httpOptions);
  }


  submitRegistrationForm(formValues: Value[], taskId: string) {

    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };

    return this.http.post(this.baseUrl + taskId, formValues, httpOptions);
  }
}

