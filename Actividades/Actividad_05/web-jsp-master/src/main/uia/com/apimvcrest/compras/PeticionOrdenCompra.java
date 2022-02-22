package uia.com.apimvcrest.compras;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PeticionOrdenCompra extends InfoComprasUIA
{
    @JsonCreator
    public PeticionOrdenCompra(@JsonProperty("id")int id, @JsonProperty("name")String name,
                               @JsonProperty("codigo")String codigo, @JsonProperty("unidad")String unidad,
                               @JsonProperty("cantidad")int cantidad)
    {
        super(id, name);
    }

    public PeticionOrdenCompra() {
        super(-1, "");
    }

    public void agregaItems(ListaReportesNivelStock miReporteNS)
    {
        PeticionOrdenCompra nodo;
        for(int i=0; i<miReporteNS.getItems().size(); i++)
        {
            List<InfoComprasUIA> miLista = miReporteNS.getItems().get(i).getItems();
            List<InfoComprasUIA> miNewLista;

            for(int j=0; j<miLista.size(); ++j)
            {
                InfoComprasUIA miNodo = miLista.get(i);
                if (miNodo.getPedidoProveedor() > 0) {
                    nodo = new PeticionOrdenCompra(miNodo.getId(), miNodo.getName(), miNodo.getDescripcion(),
                            "PZA", miNodo.getPedidoProveedor());
                    if (this.getItems() == null) {
                        miNewLista = new ArrayList<InfoComprasUIA>();
                        this.setItems((List<InfoComprasUIA>) miNewLista);
                    }
                    this.getItems().add(nodo);
                }
                miNodo.print();
            }
        }

    }
}
