import { Magazine } from './../model/magazine';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Form } from '../model/form';
import { Value } from '../model/value';
import { SubmitResponse } from '../model/submitresponse';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  baseUrl = 'https://localhost:9997/magazine/';

  httpOptions = {
    headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  startNewMagazineProcess(): Observable<Form> {

    return this.http.get<Form>(this.baseUrl + 'form/', this.httpOptions);
  }

  submitMagazineForm(formValues: Value[], taskId: string): Observable<SubmitResponse> {

    return this.http.post<SubmitResponse>(this.baseUrl + 'form/' + taskId, formValues, this.httpOptions);
  }

  submitUsersForm(formValues: Value[], taskId: string) {

    return this.http.post(this.baseUrl + 'form/users/' + taskId, formValues, this.httpOptions);
  }

  getAllMagazines(): Observable<Magazine[]> {

    return this.http.get<Magazine[]>(this.baseUrl, this.httpOptions);
  }
}
