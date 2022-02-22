package uia.com.apimvcrest.servicio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uia.com.apimvcrest.compras.GestorCompras;
import uia.com.apimvcrest.modelo.SolicitudOrdenCompraModelo;
import uia.com.apimvcrest.modelo.ItemComprasUIAModelo;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class SolicitudOrdenCompraServicio implements ISolicitudOrdenCompraServicio {


    GestorCompras miGestorCompras;

    public SolicitudOrdenCompraOrdenServicio() throws IOException {
    }

    @Autowired
    public SolicitudOrdenCompraServicio(GestorCompras gestorCompras) throws IOException {
        this.miGestorCompras = gestorCompras;
    }

    @Override
    public ArrayList<SolicitudOrdenCompraModelo> getSolicitudOrdenCompra() {
        return miGestorCompras.getSolicitudOrdenCompra();
    }

    @Override
    public SolicitudOrdenCompraModelo getSolicitudOrdenCompra(int id)
    {
        return miGestorCompras.getSolicitudOrdenCompra(id);
    }

    @Override
    public void print()
    {
        miGestorCompras.printMiModeloSolicitudes();
    }

    @Override
    public SolicitudOrdenCompraModelo SolicitudOrdenCompraModelo(int id) {
        return miGestorCompras.deleteSolicitudOrdenCompraModelo(id); //
    }

    @Override
    public SolicitudOrdenCompraModelo SolicitudOrdenCompraModelo(int id, ItemComprasUIAModelo newItem)
    {
        return miGestorCompras.getSolicitudOrdenCompra(id, newItem);
    }
}
