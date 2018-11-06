import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Vehiculo } from '../models/vehiculo';
import { VehiculoService } from '../vehiculo.service';
import { NgForm } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
    selector: 'app-ingresar-vehiculo',
    templateUrl: './ingresar-vehiculo.component.html',
    styleUrls: ['./ingresar-vehiculo.component.css']
})

export class IngresarVehiculoComponent implements OnInit {

    @Output() header = new EventEmitter();

    vehiculoModel = new Vehiculo();
    titulo: String;
    mensaje: String;
    tipoVehiculoMoto: number = 1;
    tipoVehiculoCarro: number = 2;
    recargar: number = 1;
    procesoExitoso: boolean;

    constructor(
        private vehiculoService: VehiculoService,
        private modalService: NgbModal
    ) {

    }

    ngOnInit() {
        
    }

    onSubmit(form: NgForm, content) {
        this.vehiculoService.ingresarVehiculo(this.vehiculoModel).subscribe(
            res => {
                this.titulo = "Fantástico";
                this.mensaje = "El Vehículo " + this.vehiculoModel.placa + " ingresó al parqueadero exitosamente";
                this.procesoExitoso = true;
                this.mostrarModalConfirmacion(content);
                form.resetForm();
                this.recargarListadoParqueos();
            },
            err => {
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
            }
        )
    }

    recargarListadoParqueos() {
        this.header.emit(this.recargar++);
    }

    mostrarModalConfirmacion(content)
    {
        this.modalService.open(content, { size: 'lg', centered: true  });
    }
}
