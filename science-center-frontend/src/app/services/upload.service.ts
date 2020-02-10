import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  baseUrl = 'https://localhost:9997/upload/';


  constructor(private http: HttpClient) { }

  uploadFile(file: File, processInstanceId: string): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();

    formdata.append('file', file);
    formdata.append('processInstanceId', processInstanceId);

    const req = new HttpRequest('POST', this.baseUrl, formdata, {
      reportProgress: true,
      responseType: 'text'
    });

    return this.http.post<any>(this.baseUrl, formdata);
  }

  getFiles(): Observable<any> {
    return this.http.get('/getallfiles');
  }
}
