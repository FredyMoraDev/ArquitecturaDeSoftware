package uia.com.apirest.patronesDisenno.fachada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uia.com.apirest.compras.Cotizacion;
import uia.com.apirest.compras.GestorCompras;
import uia.com.apirest.compras.InfoComprasUIA;
import uia.com.apirest.compras.SolicitudOrdenCompra;
import uia.com.apirest.modelo.*;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public abstract class FachadaModel implements IFachada {
    GestorCompras miGestorCompras;

    private HashMap<Integer, Cotizacion> misCotizaciones;
    private ArrayList<ItemComprasUIAModelo> miModeloCotizaciones = new ArrayList<ItemComprasUIAModelo>();

    private ArrayList<SolicitudOrdenCompra> misSolicitudes;
    private ArrayList<ItemComprasUIAModelo> miModeloSolicitudes = new ArrayList<ItemComprasUIAModelo>();

    @Autowired
    public FachadaModel(GestorCompras gestorCompras)
    {
        this.miGestorCompras = gestorCompras;
        misCotizaciones = this.miGestorCompras.getMisCotizacionesOrdenCompra();
        misSolicitudes = this.miGestorCompras.getMisSolicitudesOrdenCompra();
    }


    public ArrayList<ItemComprasUIAModelo> itemsCotizacion(int id)
    {
        for (int i = 0; i < misCotizaciones.size(); i++) {
            //   CotizacionModelo(int id, String name, String codigo,  int vendedor, int clasificacionVendedor, double total, int entrega)
            ItemComprasUIAModelo item = new CotizacionModelo(misCotizaciones.get(i).getId()
                    , misCotizaciones.get(i).getName()
                    , misCotizaciones.get(i).getCodigo()
                    , misCotizaciones.get(i).getVendedor()
                    , misCotizaciones.get(i).getClasificacion()
                    , misCotizaciones.get(i).getTotal()
                    , misCotizaciones.get(i).getEntrega());
            if (misCotizaciones.get(i).getItems() != null) {
                ArrayList<ItemCotizacionModelo> misItemsCotizaciones = new ArrayList<ItemCotizacionModelo>();
                for (int j = 0; j < misCotizaciones.get(i).getItems().size(); j++) {
                    //ItemCotizacionModelo(int cantidad, double valorUnitario, double subtotal, double total)
                    ItemCotizacionModelo nodo = new ItemCotizacionModelo(
                            misCotizaciones.get(i).getItems().get(j).getCantidad()
                            , misCotizaciones.get(i).getValorUnitario()
                            , misCotizaciones.get(i).getSubtotal()
                            , misCotizaciones.get(i).getTotal()
                            , misCotizaciones.get(i).getItems().get(j).getName()
                            , misCotizaciones.get(i).getItems().get(j).getClasificacion()
                            , misCotizaciones.get(i).getItems().get(j).getId()
                            , misCotizaciones.get(i).getItems().get(j).getCodigo()
                    );
                    if(nodo.getId()==id)
                        miModeloCotizaciones.add(item);
                }
            }
        }

        return this.miModeloCotizaciones;

    }

    public ArrayList<ItemComprasUIAModelo> itemsSolicitud(int id)
    {
        for(int i=0; i<misSolicitudes.size(); i++) {
            InfoComprasUIA nodoSolicitud = misSolicitudes.get(i);
            if (nodoSolicitud != null) {
                for (int j = 0; j < nodoSolicitud.getItems().size(); j++) {
                    //ItemSolicitudOCModelo(int cantidad, double valorUnitario, double subtotal, double total)
                    ItemSolicitudOCModelo nodo = new ItemSolicitudOCModelo(
                            nodoSolicitud.getItems().get(j).getCantidad()
                            , nodoSolicitud.getItems().get(j).getName()
                            , nodoSolicitud.getItems().get(j).getClasificacion()
                            , nodoSolicitud.getItems().get(j).getId()
                            , nodoSolicitud.getItems().get(j).getCodigo()
                            , nodoSolicitud.getItems().get(j).getExistenciaMinima()
                            , nodoSolicitud.getItems().get(j).getExistencia()
                            , nodoSolicitud.getItems().get(j).getConsumo()
                            , nodoSolicitud.getItems().get(j).getPedidoProveedor()
                            , nodoSolicitud.getItems().get(j).getPadre()
                    );
                    if (nodo.getId() == id)
                        miModeloSolicitudes.add(nodo);
                }
            }
        }
        return miModeloSolicitudes;
    }

    public ArrayList<ItemComprasUIAModelo> itemsReporte()
    {
        return null; //this.reporte.getItems();
    }
}