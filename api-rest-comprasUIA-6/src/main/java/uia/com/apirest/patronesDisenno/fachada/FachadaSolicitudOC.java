package uia.com.apirest.patronesDisenno.fachada;

import org.springframework.stereotype.Component;
import uia.com.apirest.compras.GestorCompras;
import uia.com.apirest.modelo.ItemComprasUIAModelo;
import uia.com.apirest.modelo.ItemSolicitudOCModelo;
import uia.com.apirest.modelo.ReporteModelo;

import java.util.ArrayList;

@Component
public class FachadaSolicitudOC extends FachadaModel {

    public FachadaSolicitudOC(GestorCompras gestorCompras) {
        super(gestorCompras);
    }
    @Override
    public ArrayList<ItemComprasUIAModelo> getItems(int id) {
        return this.itemsSolicitud(id);
    }

    @Override
    public ArrayList<ReporteModelo> getItems() {
        return null;
    }
}
