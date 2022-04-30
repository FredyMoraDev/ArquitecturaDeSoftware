package uia.com.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import uia.com.apirest.modelo.ReporteModelo;
import uia.com.apirest.modelo.ItemComprasUIAModelo;
import uia.com.apirest.patronesDisenno.fachada.FachadaReportes;
import uia.com.apirest.servicio.ReportesServicio;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReportesController {
    private FachadaReportes servicioReportes;

    //ArrayList<ReporteModelo> reportees = new ArrayList<ReporteModelo>();
    @Autowired
    public ReportesController(FachadaReportes servicio) throws IOException {
        this.servicioReportes = servicio;
    }

    @GetMapping("/reportes")
    public ArrayList<ReporteModelo> viewReportes(ModelMap model) {
        System.out.println("Ejecutando reportes");
        System.out.println(servicioReportes.getItems());
        return servicioReportes.getItems();
    }
}








