package uia.com.apirest.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uia.com.apirest.compras.GestorCompras;
import uia.com.apirest.modelo.ItemSolicitudOCModelo;
import uia.com.apirest.modelo.SolicitudOCModelo;
import uia.com.apirest.modelo.ItemComprasUIAModelo;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class SolicitudOCServicio implements ISolicitudOCServicio {


    GestorCompras miGestorCompras;

    public SolicitudOCServicio() throws IOException {
    }

    @Autowired
    public SolicitudOCServicio(GestorCompras gestorCompras) throws IOException {
        this.miGestorCompras = gestorCompras;
    }

    @Override
    public ArrayList<ItemSolicitudOCModelo> getSolicitudesOC(int id) {
        return miGestorCompras.getSolicitudesOC(id);
    }

}
