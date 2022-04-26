package uia.com.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uia.com.apirest.modelo.ItemComprasUIAModelo;
import uia.com.apirest.patronesDisenno.fachada.FachadaCotizaciones;

import java.io.IOException;
import java.util.List;

@RestController
public class CotizacionesController
{
    private FachadaCotizaciones servicioCotizaciones;
    //ArrayList<CotizacionModelo> cotizaciones = new ArrayList<CotizacionModelo>();

    @Autowired
    public CotizacionesController(FachadaCotizaciones servicio) throws IOException {
        this.servicioCotizaciones = (FachadaCotizaciones) servicio;
    }



    @GetMapping("/cotizaciones")
    public List<ItemComprasUIAModelo> cotizaciones(@RequestParam int id)
    {
        return servicioCotizaciones.getItems(id);
    }


}
