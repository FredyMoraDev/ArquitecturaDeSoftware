import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-mis-componentes',
  templateUrl: './mis-componentes.component.html',
  styleUrls: ['./mis-componentes.component.css']
})
export class MisComponentesComponent {
  @Input() tittle= '';
  @Input() description= '';


}
