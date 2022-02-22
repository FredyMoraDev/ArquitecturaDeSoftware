package uia.com.apimvcrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import uia.com.apimvcrest.modelo.CotizacionModelo;
import uia.com.apimvcrest.modelo.ItemComprasUIAModelo;
import uia.com.apimvcrest.modelo.SolicitudOrdenCompraModelo;
import uia.com.apimvcrest.servicio.CotizacionServicio;
import uia.com.apimvcrest.servicio.SolicitudOrdenCompraServicio;

import java.io.IOException;
import java.util.List;

@Controller
public class SolicitudOrdenCompraesController
{
    private SolicitudOrdenCompraServicio servicioSolicitudOrdenCompra;

    @Autowired
    public SolicitudOrdenCompraesController (SolicitudOrdenCompraServicio servicio) throws IOException {
        this.servicioSolicitudOrdenCompra = servicio;
    }
    @GetMapping("/viewSolicitudes")
    public String viewSolicitudes(ModelMap model)
    {
        List<SolicitudOrdenCompraModelo> SolicitudOrdenCompraes = servicioSolicitudOrdenCompra.getSolicitudOrdenCompra();
        System.out.println("SolicitudOrdenCompraes: " + SolicitudOrdenCompraes.toString());
        model.addAttribute("solicitudes", servicioSolicitudOrdenCompra.getSolicitudOrdenCompra());
        return "view-solicitudes";
    }

    @GetMapping("/solicitud/{id}")
    public SolicitudOrdenCompraModelo solicitudOrdenCompraById(@PathVariable int id)
    {
        return  servicioSolicitudOrdenCompra.getSolicitudOrdenCompra(id);
    }

    @DeleteMapping("/solicitud/{id}")
    public SolicitudOrdenCompraModelo deleteSolicitudOrdenCompraById(@PathVariable int id)
    {
        return  servicioSolicitudOrdenCompra.deleteSolicitudOrdenCompra(id);
    }

    @PutMapping("/solicitud/{id}")
    public SolicitudOrdenCompraModelo SolicitudOrdenCompraPutById(@PathVariable int id, @RequestBody ItemComprasUIAModelo newItem)
    {
        return  servicioSolicitudOrdenCompra.putSolicitudOrdenCompra(id, newItem);
    }

    public SolicitudOrdenCompraServicio getServicioSolicitudOrdenCompraes() {
        return servicioSolicitudOrdenCompra;
    }

    public void setServicioSolicitudOrdenCompraes(SolicitudOrdenCompraServicio servicioSolicitudOrdenCompraes) {
        this.servicioSolicitudOrdenCompra = servicioSolicitudOrdenCompraes;
    }

}
