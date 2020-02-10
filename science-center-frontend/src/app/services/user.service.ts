import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
    providedIn: 'root'
  })
export class UserService {

  httpOptions = {
    headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
  };

  baseUrl = 'https://localhost:9997/user/';

  constructor(private http: HttpClient) { }


  getUsers(role: string): Observable<User[]> {

    return this.http.get<User[]>(this.baseUrl + role, this.httpOptions);
  }

  addUser(user: any) {

    return this.http.post(this.baseUrl, user, this.httpOptions);
  }

}
