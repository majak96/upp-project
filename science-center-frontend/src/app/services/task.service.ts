import { Task } from '../model/task';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Form } from '../model/form';
import { Value } from '../model/value';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  httpOptions = {
    headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
  };

  baseUrl = 'http://localhost:9997/user/';

  constructor(private http: HttpClient) { }

  getTasks(): Observable<Task[]> {

    return this.http.get<Task[]>(this.baseUrl + 'task', this.httpOptions);
  }

  getTask(taskId: string): Observable<Form> {

    return this.http.get<Form>(this.baseUrl + 'task/' + taskId, this.httpOptions);
  }

  submitTask(formValues: Value[], taskId: string) {

    return this.http.post(this.baseUrl + 'task/' + taskId, formValues, this.httpOptions);
  }
}
