import { Component, OnInit,OnChanges,Input } from '@angular/core';
import { VehiculoService } from '../vehiculo.service';
import { ListadoParqueo } from '../models/listado-parqueo';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-listado-vehiculos',
    templateUrl: './listado-vehiculos.component.html',
    styleUrls: ['./listado-vehiculos.component.css']
})
export class ListadoVehiculosComponent implements OnInit,OnChanges {

    @Input() recargarListado: string;
    listadoParqueos: ListadoParqueo[];
    procesoExitoso: boolean;
    titulo: String;
    mensaje: String;
    formatter = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
        minimumFractionDigits: 0
      });

    constructor(
        private vehiculoService: VehiculoService,
        private modalService: NgbModal
    ){ }

    ngOnInit() {
        this.cargarListadoParqueos();
    }
    
    ngOnChanges(){
        this.cargarListadoParqueos();
    }

    cargarListadoParqueos()
    {
        this.vehiculoService.obtenerVehiculosParqueados().subscribe(
            data => {
                this.listadoParqueos = data;
            }
        );
    }

    retirarVehiculo(placa, content)
    {
        this.vehiculoService.retirarVehiculo(placa).subscribe(
            res => {
                var response = res.json();
                this.titulo = "Fantástico";
                this.mensaje = "El Vehículo " + response.vehiculo.placa +" salio del parqueadero exitosamente, el valor a pagar es: " + this.formatter.format(response.precio);
                this.procesoExitoso = true;
                this.mostrarModalConfirmacion(content);
                this.cargarListadoParqueos();

            },
            err => 
            {
                console.log(err);
                this.titulo = "Error";
                if (err.status == 0 || err.status == 500) {
                    this.mensaje = "Error de conexión, por favor contacte al administrador";
                }
                else {
                    this.mensaje = err.text();
                }
                this.procesoExitoso = false;
                this.mostrarModalConfirmacion(content);
                this.cargarListadoParqueos();
            }
        );
    }

    mostrarModalConfirmacion(content)
    {
        this.modalService.open(content, { size: 'lg', centered: true  });
    }
}