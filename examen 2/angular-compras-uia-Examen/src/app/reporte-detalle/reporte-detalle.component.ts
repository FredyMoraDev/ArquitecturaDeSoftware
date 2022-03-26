import { Component, OnInit, Input, Output } from '@angular/core';
import {IReporte} from '../iReporte';

@Component({
  selector: 'app-reporte-detalle',
  templateUrl: './reporte-detalle.component.html',
  styleUrls: ['./reporte-detalle.component.css']
})
export class ReporteDetalleComponent implements OnInit {

  @Input() reporte?: IReporte;

  constructor() { }

  ngOnInit(): void {
  }

}
