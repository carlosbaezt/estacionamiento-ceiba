import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { Http } from '@angular/http';

const API_URL = environment.apiUrl;


@Injectable({
  providedIn: 'root'
})
export class TrmService {

  constructor(
    private http: Http,
  ) { }

  consultarTrm() {
    return this.http.get(API_URL + "v1/trm");
  }
}
