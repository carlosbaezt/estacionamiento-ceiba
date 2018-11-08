import { Component, OnInit } from '@angular/core';
import { TrmService } from '../trm.service';


@Component({
  selector: 'app-trm',
  templateUrl: './trm.component.html',
  styleUrls: ['./trm.component.css']
})
export class TrmComponent implements OnInit {
  
  mensaje: String;
  peticionExitosa: boolean;
  cargandoPeticion = true;

  constructor(
    private trmService: TrmService,
  ) { }

  ngOnInit() {
    this.consultarTrm();
  }

  consultarTrm()
    {
        this.trmService.consultarTrm().subscribe(
            res => {
              var response = res.json();
              this.mensaje = "$1.00 (USD) =  $" + response.trm + " (COP)";
              this.cargandoPeticion = false;
              this.peticionExitosa = true;
            },
            err => 
            {
              this.mensaje = "Error al obtener el TRM, por favor contacte al administrador";
              this.cargandoPeticion = false;
              this.peticionExitosa = false;
            }
        );
    }

}