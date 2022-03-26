export interface ICotizacion {
    id: string;
    name: string;
    codigo: string ;
    unidad: number;
    cantidad: number;
    vendedor : number;
    valorUnitario: number;
    subtotal: number;
    descripcion: string;
    pedidoProveedor: number;
    clasificacion : number;
    existenciaMinima : number;
    existencia  : number;
    consumo :number;
    items: null;
    padre: number;
    type : string;
  }
