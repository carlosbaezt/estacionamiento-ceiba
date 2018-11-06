import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faFlagCheckered } from '@fortawesome/free-solid-svg-icons';
import { IngresarVehiculoComponent } from './ingresar-vehiculo/ingresar-vehiculo.component';
import { faCar } from '@fortawesome/free-solid-svg-icons';
import { faMotorcycle } from '@fortawesome/free-solid-svg-icons';
import { FormsModule }   from '@angular/forms';
import { HttpModule } from '@angular/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UpperCaseTextDirective } from './upper-case-text.directive';
import { ListadoVehiculosComponent } from './listado-vehiculos/listado-vehiculos.component';
import { HttpClientModule } from "@angular/common/http";
import { faCarCrash } from '@fortawesome/free-solid-svg-icons';
import { faArrowCircleRight } from '@fortawesome/free-solid-svg-icons';
import { faArrowRight } from '@fortawesome/free-solid-svg-icons';
import { TrmComponent } from './trm/trm.component';
import { faMoneyBillAlt } from '@fortawesome/free-solid-svg-icons';

// Add an icon to the library for convenient access in other components
library.add(faFlagCheckered);
library.add(faCar);
library.add(faMotorcycle);
library.add(faCarCrash);
library.add(faArrowCircleRight);
library.add(faArrowRight);
library.add(faMoneyBillAlt);


@NgModule({
  declarations: [
    AppComponent,
    IngresarVehiculoComponent,
    UpperCaseTextDirective,
    ListadoVehiculosComponent,
    TrmComponent
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    FormsModule,
    HttpModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
