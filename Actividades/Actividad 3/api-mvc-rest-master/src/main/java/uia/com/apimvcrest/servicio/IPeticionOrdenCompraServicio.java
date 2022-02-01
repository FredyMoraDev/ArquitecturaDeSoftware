package uia.com.apimvcrest.servicio;

import uia.com.apimvcrest.modelo.PeticionOrdenCompraModelo;

import java.util.ArrayList;

public interface IPeticionOrdenCompraServicio {
    ArrayList <PeticionOrdenCompraModelo> getPeticionOrdenCompra();
    Object getPeticionOrdenCompra(int id);
    void printpeticion();
}
