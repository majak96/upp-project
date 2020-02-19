import { Magazine } from './../model/magazine';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Form } from '../model/form';
import { Value } from '../model/value';
import { SubmitResponse } from '../model/submitresponse';
import { Issue } from '../model/issue';

@Injectable({
  providedIn: 'root'
})
export class IssueService {

  baseUrl = 'https://localhost:9997/issue/';

  httpOptions = {
    headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getMagazineIssues(magazineId: number): Observable<Issue[]> {

    return this.http.get<Issue[]>(this.baseUrl + 'magazine/' + magazineId, this.httpOptions);
  }
}
