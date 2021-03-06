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
export class PaperService {

  baseUrl = 'https://localhost:9997/paper/';

  httpOptions = {
    headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  startNewPaperProcess(): Observable<Form> {

    return this.http.get<Form>(this.baseUrl + 'form/', this.httpOptions);
  }

  getIssuePapers(issueId: number): Observable<Paper[]> {

    return this.http.get<Paper[]>(this.baseUrl + 'issue/' + issueId, this.httpOptions);
  }

}
