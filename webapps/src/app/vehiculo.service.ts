import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Http } from '@angular/http';
import { Vehiculo } from './models/vehiculo';
import { ListadoParqueo } from './models/listado-parqueo';

const API_URL = environment.apiUrl;

@Injectable({
  providedIn: 'root'
})
export class VehiculoService {

  constructor(
    private httpClient: HttpClient,
    private http: Http,

  ) { }

  ingresarVehiculo(vehiculo: Vehiculo) {
    return this.http.post(API_URL + "v1/ingresarVehiculo" , vehiculo);
  }

  obtenerVehiculosParqueados() {
    return this.httpClient.get<ListadoParqueo[]>(API_URL + "v1/obtenerParqueados");
  }

  retirarVehiculo(placa: String) {
    return this.http.get(API_URL + "v1/retirarVehiculo/" + placa);
  }

}