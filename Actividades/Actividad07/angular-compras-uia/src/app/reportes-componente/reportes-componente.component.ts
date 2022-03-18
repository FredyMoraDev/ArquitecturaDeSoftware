import { Component, Input, OnInit } from '@angular/core';
import { IReporte } from '../iReporte';
import { ReportesService } from '../reportes.service';
import { ReporteDetalleComponent } from '../reporte-detalle/reporte-detalle.component';

@Component({
  selector: 'app-reportes-componente',
  templateUrl: './reportes-componente.component.html',
  styleUrls: ['./reportes-componente.component.css']
})
export class ReportesComponenteComponent implements OnInit {

public reporte = { name: "",id: 0}


  reportes: IReporte[] = [];

  selectedReporte?: IReporte;

  constructor(private datosReportes:ReportesService){

  }

  ngOnInit(): void {

    this.datosReportes.getReportes().subscribe((data: any[]) => {
      console.log(data);
      this.reportes = data;
    });
  }



  onSelect(reporte: IReporte): void {
    this.selectedReporte = reporte;
  }

  agregar(name:string, id:string): void {
    name = name.trim();

    var newReporte = <IReporte>{};

    newReporte.id=Number(id);
    newReporte.name=name;
    newReporte.type="reporte";

    if(!name){return; }
    this.datosReportes.agregarReporte(newReporte)
    .subscribe(reporte => {
      this.reportes.push(reporte);
    });
  }



}
