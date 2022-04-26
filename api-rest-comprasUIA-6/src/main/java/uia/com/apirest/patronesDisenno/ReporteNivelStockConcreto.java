package uia.com.apirest.patronesDisenno;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uia.com.apirest.compras.InfoComprasUIA;
import uia.com.apirest.compras.PeticionOrdenCompra;

import java.util.ArrayList;
import java.util.List;

public class  ReporteNivelStockConcreto<items> extends AListaInfoComprasUIA {

    @JsonCreator
    public ReporteNivelStockConcreto(@JsonProperty("id")int id, @JsonProperty("name")String name)
    {
        super(id, name);
    }

    public ReporteNivelStockConcreto()
    {
    }

    public List<InfoComprasUIA> agregaItems()
    {
        ArrayList<InfoComprasUIA> newListaItems = new ArrayList<InfoComprasUIA>();
        PeticionOrdenCompra nodo;
        List<InfoComprasUIA> misItems = this.getItems();

        for(int i=0; i<misItems.size(); i++)
        {
            List<InfoComprasUIA> miLista = misItems.get(i).getItems();

            List<InfoComprasUIA> newItems = null;

            PeticionOrdenCompra miNewNodo = new PeticionOrdenCompra(misItems.get(i).getId(), misItems.get(i).getName(), misItems.get(i).getDescripcion(),
                    "PZA", misItems.get(i).getPedidoProveedor(), misItems.get(i).getConsumo(), misItems.get(i).getExistenciaMinima(), misItems.get(i).getExistencia());

            for(int j=0; j<miLista.size(); ++j)
            {
                InfoComprasUIA miNodo = miLista.get(j);
                nodo = new PeticionOrdenCompra(miNodo.getId(), miNodo.getName(), miNodo.getDescripcion(),
                        "PZA", miNodo.getPedidoProveedor(), miNodo.getConsumo(), miNodo.getExistenciaMinima(), miNodo.getExistencia());
                if (newItems == null)
                {
                    newItems = new ArrayList<InfoComprasUIA>();
                }
                nodo.setPadre(this.getItems().get(i).getId());
                newItems.add(nodo);
                miNodo.print();
            }
            miNewNodo.setItems(newItems);
            newListaItems.add(miNewNodo);
        }
        return newListaItems;
    }

}