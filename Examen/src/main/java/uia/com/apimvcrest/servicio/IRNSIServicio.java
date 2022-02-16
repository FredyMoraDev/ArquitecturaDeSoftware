package uia.com.apimvcrest.servicio;

import uia.com.apimvcrest.compras.InfoComprasUIA;

import java.io.IOException;

public interface IRNSIServicio {
    void agregaRNSI(InfoComprasUIA reporte) throws IOException;
}
