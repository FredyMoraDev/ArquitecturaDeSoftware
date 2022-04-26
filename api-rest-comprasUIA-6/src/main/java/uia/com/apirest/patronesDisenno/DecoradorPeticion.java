package uia.com.apirest.patronesDisenno;

import uia.com.apirest.compras.InfoComprasUIA;
import uia.com.apirest.compras.ListaInfoComprasUIA;

import java.util.List;

public class DecoradorPeticion extends InfoComprasUIA implements IListaInfoComprasUIA {

    protected  ReporteNivelStockConcreto reporte;

    public DecoradorPeticion(ReporteNivelStockConcreto miReporteNS)
    {
        this.reporte=miReporteNS;
        this.setItems(this.reporte.agregaItems());
    }

    @Override
    public List<InfoComprasUIA> getItems()
    {
        return super.getItems();
    }

    @Override
    public void setItems(List<InfoComprasUIA> l)
    {
        super.setItems(l);
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }
}
