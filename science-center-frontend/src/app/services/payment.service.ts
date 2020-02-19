import { Magazine } from './../model/magazine';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Form } from '../model/form';
import { Value } from '../model/value';
import { SubmitResponse } from '../model/submitresponse';
import { Paper } from '../model/paper';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  baseUrl = 'https://localhost:9997/subscription/';

  httpOptions = {
    headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  subscribeMagazine(magazineId: number): Observable<Response> {

    return this.http.post<Response>(this.baseUrl + 'create/' + magazineId, this.httpOptions);
  }

}
