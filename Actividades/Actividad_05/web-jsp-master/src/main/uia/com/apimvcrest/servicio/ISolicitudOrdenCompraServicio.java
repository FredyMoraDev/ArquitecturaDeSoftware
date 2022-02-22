package uia.com.apimvcrest.servicio;

import uia.com.apimvcrest.modelo.ItemComprasUIAModelo;
import uia.com.apimvcrest.modelo.SolicitudOrdenCompraModelo;

import java.util.ArrayList;

public interface ISolicitudOrdenCompraServicio {
    ArrayList<SolicitudOrdenCompraModelo> getSolicitudOrdenCompra();
    Object getSolicitudOrdenCompra(int id);
    void print();

    SolicitudOrdenCompraModelo deleteSolicitudOrdenCompra(int id);
    SolicitudOrdenCompraModelo FileInputStream(int id, ItemComprasUIAModelo newItem);
}
