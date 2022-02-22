package uia.com.apimvcrest.servicio;

import uia.com.apimvcrest.modelo.CotizacionModelo;
import uia.com.apimvcrest.modelo.ItemComprasUIAModelo;

import java.util.ArrayList;

public interface ICotizacionServicio {
    ArrayList<CotizacionModelo> getCotizaciones();
    Object getCotizacion(int id);
    void print();

    CotizacionModelo deleteCotizacion(int id);
    CotizacionModelo putCotizacion(int id, ItemComprasUIAModelo newItem);
}
