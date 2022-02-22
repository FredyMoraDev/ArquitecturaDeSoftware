package uia.com.apimvcrest.servicio;

import uia.com.apimvcrest.compras.GestorCompras;
import uia.com.apimvcrest.compras.InfoComprasUIA;

import java.io.IOException;

public class RNSIServicio implements IRNSIServicio
{
    private GestorCompras miGestorCompras = new GestorCompras();

    public RNSIServicio() throws IOException {
    }


    @Override
    public void agregaRNSI(InfoComprasUIA reporte) throws IOException {
        this.miGestorCompras.agregaRNSI(reporte);
    }
}
