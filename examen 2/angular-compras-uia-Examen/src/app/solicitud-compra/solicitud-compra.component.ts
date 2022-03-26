import { Component, OnInit, Input, Output } from '@angular/core';
import { ISolicitud } from '../iSolicitud';
import { IReporte } from '../iReporte';
import {SolicitudService} from '../solicitud.service';

@Component({
  selector: 'app-solicitud-compra',
  templateUrl: './solicitud-compra.component.html',
  styleUrls: ['./solicitud-compra.component.css']
})
export class SolicitudCompraComponent implements OnInit {
 

 public solicitud = { name: "", id: 0, padre:0 }

 solicitudes: ISolicitud[] = [];

 selectedSolicitud?: ISolicitud;

 @Input() reporte!: IReporte;

  constructor( public datosSolicitudes:SolicitudService) 
  { 
    
  }
               
  ngOnInit(): void {
               
    this.datosSolicitudes.getSolicitudes(this.reporte.id).subscribe((data: any[])=>{
      console.log(data);
      this.solicitudes = data;
    })
    


  }
      
 
  onSelect(solicitud: ISolicitud): void {
    this.selectedSolicitud = solicitud;
  }

  agregar(name: string, id:string): void {
    name = name.trim();

    var newSolicitud = <ISolicitud>{};
    
    newSolicitud.id=id;
    newSolicitud.name=name;
    newSolicitud.type="solicitudNS";
    
    if (!name) { return; }
    this.datosSolicitudes.agregaSolicitud(newSolicitud)
      .subscribe(solicitud => {
        this.solicitudes.push(solicitud);
      });
  }
  
}
