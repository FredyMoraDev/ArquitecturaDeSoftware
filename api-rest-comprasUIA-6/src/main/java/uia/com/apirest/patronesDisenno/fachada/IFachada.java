package uia.com.apirest.patronesDisenno.fachada;

import uia.com.apirest.compras.InfoComprasUIA;
import uia.com.apirest.modelo.ItemComprasUIAModelo;
import uia.com.apirest.modelo.ItemSolicitudOCModelo;

import java.util.ArrayList;

public interface IFachada {
    ArrayList<ItemComprasUIAModelo> getItems(int id);
    ArrayList<ItemSolicitudOCModelo> getSolicitudesOC(int id);
}