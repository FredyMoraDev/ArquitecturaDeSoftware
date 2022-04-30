package uia.com.apirest.patronesDisenno.fachada;

import org.springframework.stereotype.Component;
import uia.com.apirest.compras.GestorCompras;
import uia.com.apirest.modelo.ItemComprasUIAModelo;
import uia.com.apirest.modelo.ReporteModelo;

import java.util.ArrayList;
@Component
public class FachadaReportes extends FachadaModel {

    public FachadaReportes(GestorCompras gestorCompras) {
        super(gestorCompras);
    }
    @Override
    public ArrayList<ItemComprasUIAModelo> getItems(int id) {
        return null;
    }

    @Override
    public ArrayList<ReporteModelo> getItems() {
        return this.itemsReporte();
    }


}