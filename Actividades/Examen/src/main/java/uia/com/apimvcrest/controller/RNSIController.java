package uia.com.apimvcrest.controller;

import org.springframework.web.bind.annotation.*;
import uia.com.apimvcrest.compras.InfoComprasUIA;
import uia.com.apimvcrest.compras.ListaInfoComprasUIA;
import uia.com.apimvcrest.servicio.CotizacionServicio;
import uia.com.apimvcrest.servicio.RNSIServicio;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class RNSIController
{
    private RNSIServicio servicioRNSI = new RNSIServicio();

    public RNSIController() throws IOException {
    }

    @PostMapping("/rnsi")
    @ResponseBody
    public void rnsi(@RequestBody InfoComprasUIA reporte) throws IOException {
        System.out.println("Probando RNSIController");
        servicioRNSI.agregaRNSI(reporte);
    }

}
