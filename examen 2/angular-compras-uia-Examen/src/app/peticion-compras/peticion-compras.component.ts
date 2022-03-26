import { Component, OnInit, Input, Output } from '@angular/core';
import { IPeticion } from '../iPeticion';
import { IReporte } from '../iReporte';
import {PeticionesService} from '../peticiones.service';
import { PeticionDetalleComponent } from '../peticion-detalle/peticion-detalle.component';

@Component({
  selector: 'app-peticion-compras',
  templateUrl: './peticion-compras.component.html',
  styleUrls: ['./peticion-compras.component.css']
})
export class PeticionComprasComponent implements OnInit {


 public peticion = { name: "", id: 0 }

 peticiones: IPeticion[] = [];

 selectedPeticion?: IPeticion;
 @Input() reporte?: IReporte;

  constructor( public datosPeticiones:PeticionesService)
  {

  }

  ngOnInit(): void {

    this.datosPeticiones.getPeticiones().subscribe((data: any[])=>{
      console.log(data);
      this.peticiones = data;
    })



  }


  onSelect(peticion: IPeticion): void {
    this.selectedPeticion = peticion;
  }

  agregar(name: string, id:string): void {
    name = name.trim();

    var newPeticion = <IPeticion>{};

    newPeticion.id=id;
    newPeticion.name=name;
    newPeticion.type="peticionNS";

    if (!name) { return; }
    this.datosPeticiones.agregaPeticion(newPeticion)
      .subscribe(peticion => {
        this.peticiones.push(peticion);
      });
  }

}
