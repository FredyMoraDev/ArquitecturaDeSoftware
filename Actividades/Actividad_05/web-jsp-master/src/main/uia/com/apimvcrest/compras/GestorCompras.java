package uia.com.apimvcrest.compras;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import uia.com.apimvcrest.modelo.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


/**
 * @author amiguel
 * @version 1.0
 * @created 12-nov.-2019 11:27:37 a. m.
 */

@Repository
public class GestorCompras {

    private int opcion;
    private ListaReportesNivelStock miReporteNS;
    private PeticionOrdenCompra miPeticionOC = new PeticionOrdenCompra();
    private SolicitudOrdenCompra miSolicitudOC;
    private Comprador miComprador = new Comprador();
    private ArrayList<SolicitudOrdenCompra> misSolicitudesOC;
    HashMap<Integer, ArrayList<Cotizacion>> misSolicitudesCotizacion;
    //HashMap<Integer, ArrayList<InfoComprasUIA>> misSolicitudesOC;
    HashMap<Integer, Cotizacion> misCotizacionesOrdenCompra;
    ArrayList<CotizacionModelo> miModeloCotizaciones;
    ArrayList<ReporteModelo> miModeloReportes;
    ObjectMapper mapper = new ObjectMapper();



    public GestorCompras() throws IOException {

        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            miReporteNS = mapper.readValue(new FileInputStream("arregloItemsV1.json"), ListaReportesNivelStock.class);

        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (miReporteNS != null) {
            miPeticionOC.agregaItems(miReporteNS);

            System.out.println("----- Items List -----");

            for (int i = 0; i < miReporteNS.getItems().size(); i++) {
                List<InfoComprasUIA> miLista = miReporteNS.getItems();
                for (int j = 0; j < miLista.size(); ++j) {
                    InfoComprasUIA miNodo = miLista.get(i);
                    miNodo.print();
                }
            }

            miComprador.hazSolicitudOrdenCompra(miPeticionOC);
        }

        miSolicitudOC = miComprador.buscaVendedor(miPeticionOC);
        miComprador.agrupaVendedores(miSolicitudOC);
        int iOrden = 1;

        if (misSolicitudesOC == null)
            misSolicitudesOC = new ArrayList<SolicitudOrdenCompra>();

        for (Entry<Integer, HashMap<Integer, ArrayList<InfoComprasUIA>>> item : miComprador.getSolicitudesOrdenCompraAgrupadosXvendedores().entrySet()) {
            int iVendedor = item.getKey();
            HashMap<Integer, ArrayList<InfoComprasUIA>> nodo = item.getValue();
            //genero el identificador  idCompra
            int idCompra = item.getKey() * 1000 + iOrden * 100;
            //Formateando para ser un documento de SolicitudIrdenCompra por lo que creo una clase SolicitudOrdenCompra

            //SolicitudOrdenCompra(@JsonProperty("id")int id, @JsonProperty("name")String name,
            //@JsonProperty("codigo")String codigo, @JsonProperty("unidad")String unidad,
            //@JsonProperty("cantidad")int cantidad, @JsonProperty("vendedor")int vendedor,@JsonProperty("clasificacionProveedor")int clasificacionVendedor)

            for (Entry<Integer, ArrayList<InfoComprasUIA>> soc : nodo.entrySet()) {
                SolicitudOrdenCompra newSolicitud = new SolicitudOrdenCompra(idCompra, "SOC-" + idCompra, "", "", 0, item.getKey(), soc.getKey());
                newSolicitud.setItems(soc.getValue());
                misSolicitudesOC.add(newSolicitud);
                mapper.writeValue(new File(newSolicitud.getName() + ".json"), newSolicitud);
            }
        }

        //--Envio a Comprador las cotizacion para que genere al menos 3 cotizaciones con vendedores diferentes (0-4)
        misSolicitudesCotizacion = miComprador.hazCotizaciones(misSolicitudesOC, mapper);
        misCotizacionesOrdenCompra = miComprador.seleccionaVendedores(misSolicitudesCotizacion, mapper);

    }


    public void printMiModeloCotizaciones() {
        for (int i = 0; i < miModeloCotizaciones.size(); i++)
            miModeloCotizaciones.get(i).print();

    }

    public ArrayList<CotizacionModelo> getCotizaciones() {
        miModeloCotizaciones = new ArrayList<CotizacionModelo>();
        for (int i = 0; i < misCotizacionesOrdenCompra.size(); i++) {
            //   CotizacionModelo(int id, String name, String codigo,  int vendedor, int clasificacionVendedor, double total, int entrega)
            CotizacionModelo item = new CotizacionModelo(misCotizacionesOrdenCompra.get(i).getId()
                    , misCotizacionesOrdenCompra.get(i).getName()
                    , misCotizacionesOrdenCompra.get(i).getCodigo()
                    , misCotizacionesOrdenCompra.get(i).getVendedor()
                    , misCotizacionesOrdenCompra.get(i).getClasificacion()
                    , misCotizacionesOrdenCompra.get(i).getTotal()
                    , misCotizacionesOrdenCompra.get(i).getEntrega());
            if (misCotizacionesOrdenCompra.get(i).getItems() != null) {
                ArrayList<ItemCotizacionModelo> misItemsCotizaciones = new ArrayList<ItemCotizacionModelo>();
                for (int j = 0; j < misCotizacionesOrdenCompra.get(i).getItems().size(); j++) {
                    //ItemCotizacionModelo(int cantidad, double valorUnitario, double subtotal, double total)
                    ItemCotizacionModelo nodo = new ItemCotizacionModelo(
                            misCotizacionesOrdenCompra.get(i).getItems().get(j).getCantidad()
                            , misCotizacionesOrdenCompra.get(i).getValorUnitario()
                            , misCotizacionesOrdenCompra.get(i).getSubtotal()
                            , misCotizacionesOrdenCompra.get(i).getTotal()
                            , misCotizacionesOrdenCompra.get(i).getName()
                            , misCotizacionesOrdenCompra.get(i).getClasificacion()
                            , misCotizacionesOrdenCompra.get(i).getId()
                            , misCotizacionesOrdenCompra.get(i).getCodigo()
                    );
                    misItemsCotizaciones.add(nodo);
                }
                item.setItems(misItemsCotizaciones);
                miModeloCotizaciones.add(item);
            }
        }

        return miModeloCotizaciones;
    }


    public CotizacionModelo getCotizacion(int id) {
        if (this.miModeloCotizaciones == null)
            this.getCotizaciones();
        for (int i = 0; i < this.miModeloCotizaciones.size(); i++) {
            if (this.miModeloCotizaciones.get(i).getId() == id)
                return this.miModeloCotizaciones.get(i);
        }

        return null;
    }

    public void agregaRNSI(InfoComprasUIA reporte) throws IOException {
        if (this.miReporteNS == null)
            this.miReporteNS = new ListaReportesNivelStock();
        this.miReporteNS.getItems().add(reporte);

        this.salvaRNSI();
    }

    private void salvaRNSI() throws IOException {
        mapper.writeValue(new File(miReporteNS.getName() + ".json"), miReporteNS);
    }

    public CotizacionModelo deleteCotizacion(int id) {
        CotizacionModelo item = null;
        for (Entry<Integer, Cotizacion> nodo : misCotizacionesOrdenCompra.entrySet())
        {
            if (nodo.getValue().getId() == id)
            {
                        item = new CotizacionModelo(nodo.getValue().getId()
                                , nodo.getValue().getName()
                                , nodo.getValue().getCodigo()
                                , nodo.getValue().getVendedor()
                                , nodo.getValue().getClasificacion()
                                , nodo.getValue().getTotal()
                                , nodo.getValue().getEntrega());
                        if (nodo.getValue().getItems() != null)
                        {
                            ArrayList<ItemCotizacionModelo> misItemsCotizaciones = new ArrayList<ItemCotizacionModelo>();
                            for (int j = 0; j < nodo.getValue().getItems().size(); j++) {
                                //ItemCotizacionModelo(int cantidad, double valorUnitario, double subtotal, double total)
                                ItemCotizacionModelo nodoItem = new ItemCotizacionModelo(
                                        nodo.getValue().getItems().get(j).getCantidad()
                                        , 0.0
                                        , 0.0
                                        , 0.0);
                                misItemsCotizaciones.add(nodoItem);
                            }
                            item.setItems(misItemsCotizaciones);
                    }
                   misCotizacionesOrdenCompra.remove(nodo.getKey());
                   break;
            }
        }
        return item;
    }


    public InfoComprasUIA buscaCotizacion(int id)
    {
        for (Entry<Integer, Cotizacion> nodo : misCotizacionesOrdenCompra.entrySet())
        {
            if (nodo.getValue().getId() == id)
            {
                return nodo.getValue();
            }
        }
        return null;
    }


    public CotizacionModelo putCotizacion(int id, ItemComprasUIAModelo newItem)
    {
        InfoComprasUIA cotizacion = null;
        InfoComprasUIA item = null;
        if((cotizacion=this.buscaCotizacion(id)) != null)
            if((item = cotizacion.buscaItem(newItem.getName())) != null)
            {
                item.setCantidad(newItem.getCantidad());
                item.setClasificacion(newItem.getClasificacion());
                item.setCodigo(newItem.getCodigo());
                item.setDescripcion(newItem.getDescripcion());
                item.setName(newItem.getName());
            }
        return this.getCotizacion(id);
    }

    public ArrayList<ReporteModelo> getReportes()
    {
        miModeloReportes = new ArrayList<ReporteModelo>();

        for (int i = 0; i < miReporteNS.getItems().size(); i++) {
            //   CotizacionModelo(int id, String name, String codigo,  int vendedor, int clasificacionVendedor, double total, int entrega)
            ReporteModelo item = new ReporteModelo(miReporteNS.getItems().get(i).getId()
                    , miReporteNS.getItems().get(i).getName()
                    , miReporteNS.getItems().get(i).getCodigo()
                    , miReporteNS.getItems().get(i).getVendedor()
                    , miReporteNS.getItems().get(i).getClasificacion()
                    , miReporteNS.getItems().get(i).getExistenciaMinima()
                    , miReporteNS.getItems().get(i).getExistencia()
                    , miReporteNS.getItems().get(i).getConsumo());
            if (miReporteNS.getItems().get(i).getItems() != null) {
                ArrayList<ItemReporteModelo> misItemsReportes = new ArrayList<ItemReporteModelo>();
                for (int j = 0; j < miReporteNS.getItems().get(i).getItems().size(); j++) {
                    //ItemReporteModelo(int cantidad, double valorUnitario, double subtotal, double total)
                    ItemReporteModelo nodo = new ItemReporteModelo(
                            miReporteNS.getItems().get(i).getItems().get(j).getCantidad()
                            , miReporteNS.getItems().get(i).getItems().get(j).getName()
                            , miReporteNS.getItems().get(i).getItems().get(j).getClasificacion()
                            , miReporteNS.getItems().get(i).getItems().get(j).getId()
                            , miReporteNS.getItems().get(i).getItems().get(j).getCodigo()
                            , miReporteNS.getItems().get(i).getItems().get(j).getExistenciaMinima()
                            , miReporteNS.getItems().get(i).getItems().get(j).getExistencia()
                            , miReporteNS.getItems().get(i).getItems().get(j).getConsumo()
                            , miReporteNS.getItems().get(i).getItems().get(j).getPedidoProveedor());
                    nodo.setId(miReporteNS.getItems().get(i).getId()+1+j);
                    misItemsReportes.add(nodo);
                }
                item.setItems(misItemsReportes);
                miModeloReportes.add(item);
            }
        }

        return miModeloReportes;

    }

    public ReporteModelo getReporte(int id)
    {
        if (this.miModeloReportes == null)
            this.getReportes();
        for (int i = 0; i < this.miModeloReportes.size(); i++) {
            if (this.miModeloReportes.get(i).getId() == id)
                return this.miModeloReportes.get(i);
        }

        return null;
    }

    public void printMiModeloReportes() {
    }

    public ReporteModelo deleteReporte(int id)
    {
        return null;
    }

    public ReporteModelo putReporte(int id, ItemComprasUIAModelo newItem)
    {
        return null;
    }

    public ArrayList<SolicitudOrdenCompraModelo> getSolicitudOrdenCompra() {
    }

    public void printMiModeloSolicitudes() {
    }
}//end KardexListaKClientes