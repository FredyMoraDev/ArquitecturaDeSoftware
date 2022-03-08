import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IReporte } from '../iReporte';
import { ReportesService } from '../reportes.service';
import { HttpClient    } from '@angular/common/http';

@Component({
  selector: 'app-reportes-componente',
  templateUrl: './reportes-componente.component.html',
  styleUrls: ['./reportes-componente.component.css']
})
export class ReportesComponenteComponent implements OnInit {


  public reporte: IReporte = {
    id: 0,
    name: "Fredy" ,
    codigo: "1",
    vendedor: 2,
    clasificacionVendedor: 3,
    total: 4,
    entregado: 5
  };

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




}
