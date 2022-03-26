import { Component, OnInit, Input, Output } from '@angular/core';
import { IReporte } from '../iReporte';
import { ISolicitud } from '../iSolicitud';
import {ReportesService} from '../reportes.service';
import { ReporteDetalleComponent } from '../reporte-detalle/reporte-detalle.component';
import { PeticionComprasComponent } from '../peticion-compras/peticion-compras.component';
import { SolicitudService } from '../solicitud.service';

@Component({
  selector: 'app-reportes-componente',
  templateUrl: './reportes-componente.component.html',
  styleUrls: ['./reportes-componente.component.css']
})
export class ReportesComponenteComponent implements OnInit {
 

 public reporte = { name: "", id: 0 }

 reportes: IReporte[] = [];
 solicitudes: ISolicitud[] = [];

 selectedReporte?: IReporte;

  constructor( public datosReportes:ReportesService, public datosSolicitud:SolicitudService) 
  {     
  }
               
  ngOnInit(): void {
               
    this.datosReportes.getReportes().subscribe((data: any[])=>{
      console.log(data);
      this.reportes = data;
    })
    
    this.datosSolicitud.getSolicitudes(this.reporte.id).subscribe((data: any[])=>{
      console.log(data);
      this.solicitudes = data;
    })

  }
      
 
  onSelect(reporte: IReporte): void 
  {
    this.selectedReporte = reporte;    
    this.datosSolicitud.getSolicitudes(reporte.id).subscribe((data: any[])=>{
      console.log(data);
      this.solicitudes = data;
    })

  }

  agregar(name: string, id:string): void {
    name = name.trim();

    var newReporte = <IReporte>{};
    
    newReporte.id= Number(id);
    newReporte.name=name;
    newReporte.type="reporteNS";
    
    if (!name) { return; }
    this.datosReportes.agregaReporte(newReporte)
      .subscribe(reporte => {
        this.reportes.push(reporte);
      });
  }
  
}
