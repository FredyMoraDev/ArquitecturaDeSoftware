package uia.com.apimvcrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uia.com.apimvcrest.modelo.PeticionOrdenCompraModelo;
import uia.com.apimvcrest.servicio.PeticionOrdenCompraServicio;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class PeticionOrdenCompraController {

    private PeticionOrdenCompraServicio servicioPeticionOrdenCompra = new PeticionOrdenCompraServicio();

    public PeticionOrdenCompraController () throws IOException {
    }

    @GetMapping("/peticionesordencompra")
    public ArrayList<PeticionOrdenCompraModelo> peticionordencompra(){
        return servicioPeticionOrdenCompra.getPeticionOrdenCompra();
    }

    @GetMapping("/peticionordencompra/{id}")
    public PeticionOrdenCompraModelo peticionById(@PathVariable int id){
        return servicioPeticionOrdenCompra.getPeticionOrdenCompra(id);
    }
}
