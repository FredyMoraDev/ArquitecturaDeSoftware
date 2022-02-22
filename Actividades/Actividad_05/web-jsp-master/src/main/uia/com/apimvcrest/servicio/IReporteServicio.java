package uia.com.apimvcrest.servicio;

import uia.com.apimvcrest.modelo.ReporteModelo;
import uia.com.apimvcrest.modelo.ItemComprasUIAModelo;

import java.util.ArrayList;

public interface IReporteServicio {
    ArrayList<ReporteModelo> getReportes();
    Object getReporte(int id);
    void print();

    ReporteModelo deleteReportes(int id);
    ReporteModelo putReporte(int id, ItemComprasUIAModelo newItem);
}
