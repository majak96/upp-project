import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Form } from '../model/form';
import { Value } from '../model/value';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  baseUrl = 'http://localhost:9997/magazine/';

  httpOptions = {
    headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  startNewMagazineProcess(): Observable<Form> {

    return this.http.get<Form>(this.baseUrl, this.httpOptions);
  }

  submitMagazineForm(formValues: Value[], taskId: string) {

    return this.http.post(this.baseUrl + taskId, formValues, this.httpOptions);
  }

  getUserFields(processId: string): Observable<Form> {

    return this.http.get<Form>(this.baseUrl + processId, this.httpOptions);
  }

  submitUsersForm(formValues: Value[], taskId: string) {

    return this.http.post(this.baseUrl + 'users/' + taskId, formValues, this.httpOptions);
  }
}
