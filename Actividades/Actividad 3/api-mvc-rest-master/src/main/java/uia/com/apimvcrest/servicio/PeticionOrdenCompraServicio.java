package uia.com.apimvcrest.servicio;

import uia.com.apimvcrest.compras.GestorCompras;
import uia.com.apimvcrest.modelo.PeticionOrdenCompraModelo;

import java.io.IOException;
import java.util.ArrayList;

public class PeticionOrdenCompraServicio implements IPeticionOrdenCompraServicio{
    private GestorCompras miGestorCompras = new GestorCompras();

    public PeticionOrdenCompraServicio() throws IOException {
    }

    @Override
    public ArrayList<PeticionOrdenCompraModelo> getPeticionOrdenCompra(){
        return miGestorCompras.getPeticionOrdenCompra();
    }

    @Override
    public Object getPeticionOrdenCompra(int id){
        return miGestorCompras.getPeticionOrdenCompras(id);
    }

    @Override
    public void printpeticion(){miGestorCompras.printMiModeloPeticionOrdenCompras();}

}
