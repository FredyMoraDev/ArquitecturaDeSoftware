import { Component, OnInit, Input, Output } from '@angular/core';
import {ICotizacion} from '../iCotizacion';
import { IReporte } from '../iReporte';
import { CotizacionService } from '../cotizacion.service';

@Component({
  selector: 'app-cotizacion-detalle',
  templateUrl: './cotizacion-detalle.component.html',
  styleUrls: ['./cotizacion-detalle.component.css']
})
export class CotizacionDetalleComponent implements OnInit {

  public cotizacion = {name: "", id: 0, padre:0 }

  cotizaciones: ICotizacion[] = [];
  selectedCotizacion?: ICotizacion;



  @Input() reporte!: IReporte;

  constructor(public datosCotizaciones:CotizacionService) { }

  ngOnInit(): void {
    this.datosCotizaciones.getCotizaciones(this.cotizacion.id).subscribe((data: any[]) => {
      console.log(data);
      this.cotizaciones = data;
    });
  }


onSelect(cotizacion: ICotizacion): void {
  this.selectedCotizacion = cotizacion;
}

agregar(name:string,id:string): void{
  name = name.trim();

  var newCotizacion = <ICotizacion>{};

  newCotizacion.id=id;
  newCotizacion.name=name;
  newCotizacion.type="cotizacionesNS";

  if(!name) {return;}
  this.datosCotizaciones.agregarCotizacion(newCotizacion)
  .subscribe(cotizacion => {
    this.cotizaciones.push(cotizacion);
  });
}

}

