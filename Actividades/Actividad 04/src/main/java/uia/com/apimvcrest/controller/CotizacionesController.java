package uia.com.apimvcrest.controller;

import org.springframework.web.bind.annotation.*;
import uia.com.apimvcrest.modelo.CotizacionModelo;
import uia.com.apimvcrest.servicio.CotizacionServicio;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/servicio") //Mapp para el controlador
public class CotizacionesController
{
    private CotizacionServicio servicioCotizaciones = new CotizacionServicio();

    public CotizacionesController() throws IOException {
    }

    @GetMapping("/getCotizacion")
    public ArrayList<CotizacionModelo> getCotizaciones(){
        ArrayList<CotizacionModelo> miModeloCotizaciones = new ArrayList<>();
        miModeloCotizaciones.add(new CotizacionModelo(1, "prueba", "1", 1, 1, 0.0,  1));
        return miModeloCotizaciones;
    }

    @GetMapping("/cotizaciones")
    public ArrayList<CotizacionModelo> cotizaciones()
    {
        return servicioCotizaciones.getCotizaciones();
    }

    @GetMapping("/cotizacion/{id}")
    public CotizacionModelo cotizacionById(@PathVariable int id)
    {
        return servicioCotizaciones.getCotizacion(id);
    }

    @DeleteMapping("/deletecotizacion/{id}")
    public CotizacionModelo deleteCotizacion (@PathVariable int id)
    {
        this.servicioCotizaciones.deleteCotizacion(id);
        return servicioCotizaciones.getCotizacion(id);
    }
}
