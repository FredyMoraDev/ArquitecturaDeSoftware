import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule} from '@angular/forms'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReportesComponenteComponent } from './reportes-componente/reportes-componente.component';
import { HttpClientModule } from '@angular/common/http';
import { ReporteDetalleComponent } from './reporte-detalle/reporte-detalle.component';
import { ReportesService } from './reportes.service';
import { PeticionComprasComponent } from './peticion-compras/peticion-compras.component';
import { SolicitudCompraComponent } from './solicitud-compra/solicitud-compra.component';
import { SolicitudService } from './solicitud.service';

@NgModule({
  declarations: [
    AppComponent,
    ReportesComponenteComponent,
    ReporteDetalleComponent,
    SolicitudCompraComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [ReportesService, SolicitudService],
  bootstrap: [AppComponent]
})
export class AppModule { }
