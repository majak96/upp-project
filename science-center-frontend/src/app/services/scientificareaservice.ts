import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { ScientificArea } from '../model/scientificarea';

@Injectable({
    providedIn: 'root'
  })
export class ScientificAreaService {

  httpOptions = {
    headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
  };

  baseUrl = 'https://localhost:9997/scientificarea/';

  constructor(private http: HttpClient) { }


  getScientificAreas(): Observable<ScientificArea[]> {

    return this.http.get<ScientificArea[]>(this.baseUrl, this.httpOptions);
  }

}
