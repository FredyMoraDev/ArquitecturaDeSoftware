package uia.com.apirest.servicio;

import uia.com.apirest.modelo.ItemSolicitudOCModelo;
import uia.com.apirest.modelo.SolicitudOCModelo;
import uia.com.apirest.modelo.ItemComprasUIAModelo;

import java.util.ArrayList;

public interface ISolicitudOCServicio {
    ArrayList<ItemSolicitudOCModelo> getSolicitudesOC(int id);

}
