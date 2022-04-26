package uia.com.apirest.patronesDisenno.fachada;

import org.springframework.stereotype.Component;
import uia.com.apirest.compras.GestorCompras;
import uia.com.apirest.modelo.ItemComprasUIAModelo;
import uia.com.apirest.modelo.ItemSolicitudOCModelo;
import uia.com.apirest.patronesDisenno.fachada.FachadaModel;

import java.util.ArrayList;

@Component
public class FachadaCotizaciones extends FachadaModel {

    public FachadaCotizaciones(GestorCompras gestorCompras) {
        super(gestorCompras);
    }

    @Override
    public ArrayList<ItemComprasUIAModelo> getItems(int id) {
        return this.itemsCotizacion(id);
    }
    @Override
    public ArrayList<ItemSolicitudOCModelo> getSolicitudesOC(int id) {
        return null;
    }
}
