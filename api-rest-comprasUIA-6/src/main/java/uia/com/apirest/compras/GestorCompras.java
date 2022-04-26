package uia.com.apirest.compras;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import uia.com.apirest.modelo.*;
import uia.com.apirest.patronesDisenno.DecoradorPeticion;
import uia.com.apirest.patronesDisenno.ReporteNivelStockConcreto;

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
    private ReporteNivelStockConcreto miReporteNS;
    private DecoradorPeticion miDecoradorPeticionOC;
    private PeticionOrdenCompra miPeticionOC = new PeticionOrdenCompra();
    private SolicitudOrdenCompra miSolicitudOC;
    private Comprador miComprador = new Comprador();
    private ArrayList<SolicitudOrdenCompra> misSolicitudesOC;
    HashMap<Integer, ArrayList<Cotizacion>> misSolicitudesCotizacion;
    //HashMap<Integer, ArrayList<InfoComprasUIA>> misSolicitudesOC;
    HashMap<Integer, Cotizacion> misCotizacionesOrdenCompra;
    ArrayList<CotizacionModelo> miModeloCotizaciones;
    ArrayList<ReporteModelo> miModeloReportes;
    ArrayList<SolicitudOCModelo> miModeloSolicitudesOC = new ArrayList<SolicitudOCModelo>();

    ObjectMapper mapper = new ObjectMapper();


    public GestorCompras() throws IOException {

        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            miReporteNS = mapper.readValue(new FileInputStream("arregloItemsV1.json"), ReporteNivelStockConcreto.class);

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

        miDecoradorPeticionOC = new DecoradorPeticion(miReporteNS);

        miComprador.hazSolicitudOrdenCompra(miDecoradorPeticionOC);

        miSolicitudOC = miComprador.buscaVendedor(miDecoradorPeticionOC.getItems());
        miComprador.agrupaVendedores(miSolicitudOC);
        int iOrden = 1;


        if (misSolicitudesOC == null)
            misSolicitudesOC = new ArrayList<SolicitudOrdenCompra>();

        for (Entry<Integer, HashMap<Integer, ArrayList<InfoComprasUIA>>> item : miComprador.getSolicitudesOrdenCompraAgrupadosXvendedores().entrySet())
        {
            int iVendedor = item.getKey();
            HashMap<Integer, ArrayList<InfoComprasUIA>> nodo = item.getValue();
            //genero el identificador  idCompra
            int idCompra = item.getKey() * 1000 + iOrden * 100;
            //Formateando para ser un documento de SolicitudIrdenCompra por lo que creo una clase SolicitudOrdenCompra

            //SolicitudOrdenCompra(@JsonProperty("id")int id, @JsonProperty("name")String name,
            //@JsonProperty("codigo")String codigo, @JsonProperty("unidad")String unidad,
            //@JsonProperty("cantidad")int cantidad, @JsonProperty("vendedor")int vendedor,@JsonProperty("clasificacionProveedor")int clasificacionVendedor)

            for (Entry<Integer, ArrayList<InfoComprasUIA>> soc : nodo.entrySet())
            {
                SolicitudOrdenCompra newSolicitud = new SolicitudOrdenCompra(idCompra, "SOC-" + idCompra, "", "", 0, item.getKey(), soc.getKey());
                newSolicitud.setItems(soc.getValue());
                misSolicitudesOC.add(newSolicitud);
                mapper.writeValue(new File("C:/TSU-2022/ComprasProy/SolicitudOrdenCompra-" + newSolicitud.getName() + ".json"), newSolicitud);
            }
        }

        //--Envio a Comprador las cotizacion para que genere al menos 3 cotizaciones con vendedores diferentes (0-4)
        misSolicitudesCotizacion = miComprador.hazCotizaciones(misSolicitudesOC, mapper);
        misCotizacionesOrdenCompra = miComprador.seleccionaVendedores(misSolicitudesCotizacion, mapper);

    }

    public HashMap<Integer, Cotizacion> getMisCotizacionesOrdenCompra() {
        return misCotizacionesOrdenCompra;
    }

    public void setMisCotizacionesOrdenCompra(HashMap<Integer, Cotizacion> misCotizacionesOrdenCompra) {
        this.misCotizacionesOrdenCompra = misCotizacionesOrdenCompra;
    }

    private InfoComprasUIA agregaItems(InfoComprasUIA item)
    {
        List<InfoComprasUIA> miLista = item.getItems();

        InfoComprasUIA newItem = new PeticionOrdenCompra(item.getId(), item.getName(), item.getDescripcion(),
                "PZA", item.getPedidoProveedor(), item.getConsumo(), item.getExistenciaMinima(), item.getExistencia());

        List<InfoComprasUIA> miNewLista;
        InfoComprasUIA nodo = null;

            for(int j=0; j<miLista.size(); ++j)
            {
                InfoComprasUIA miNodo = miLista.get(j);
                nodo = new PeticionOrdenCompra(miNodo.getId(), miNodo.getName(), miNodo.getDescripcion(),
                        "PZA", miNodo.getPedidoProveedor(), miNodo.getConsumo(), miNodo.getExistenciaMinima(), miNodo.getExistencia());
                if (newItem.getItems() == null)
                {
                    miNewLista = new ArrayList<InfoComprasUIA>();
                    newItem.setItems((List<InfoComprasUIA>) miNewLista);
                }
                nodo.setPadre(newItem.getId());
                newItem.getItems().add(nodo);
                miNodo.print();
            }

        return newItem;

    }


    public void printMiModeloCotizaciones() {
        for (int i = 0; i < miModeloCotizaciones.size(); i++)
            miModeloCotizaciones.get(i).print();

    }

    public ArrayList<CotizacionModelo> getCotizaciones(int id)
    {
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
                            , misCotizacionesOrdenCompra.get(i).getItems().get(j).getName()
                            , misCotizacionesOrdenCompra.get(i).getItems().get(j).getClasificacion()
                            , misCotizacionesOrdenCompra.get(i).getItems().get(j).getId()
                            , misCotizacionesOrdenCompra.get(i).getItems().get(j).getCodigo()
                    );
                    if(nodo.getId()==id)
                        miModeloCotizaciones.add(item);
                }
            }
        }

        return miModeloCotizaciones;
    }


    public CotizacionModelo getCotizacion(int id) {
        if (this.miModeloCotizaciones == null)
            this.getCotizaciones(id);
        for (int i = 0; i < this.miModeloCotizaciones.size(); i++) {
            if (this.miModeloCotizaciones.get(i).getId() == id)
                return this.miModeloCotizaciones.get(i);
        }

        return null;
    }

    public void agregaRNSI(InfoComprasUIA reporte) throws IOException {
        //if (this.miReporteNS == null)
        //    this.miReporteNS = new ListaReportesNivelStock();
        this.miReporteNS.getItems().add(reporte);

        this.salvaRNSI();
    }

    private void salvaRNSI() throws IOException {
        mapper.writeValue(new File(miReporteNS.getName() + ".json"), miReporteNS);
    }

    public CotizacionModelo deleteCotizacion(int id) {
        CotizacionModelo item = null;
        for (Entry<Integer, Cotizacion> nodo : misCotizacionesOrdenCompra.entrySet()) {
            if (nodo.getValue().getId() == id) {
                item = new CotizacionModelo(nodo.getValue().getId()
                        , nodo.getValue().getName()
                        , nodo.getValue().getCodigo()
                        , nodo.getValue().getVendedor()
                        , nodo.getValue().getClasificacion()
                        , nodo.getValue().getTotal()
                        , nodo.getValue().getEntrega());
                if (nodo.getValue().getItems() != null) {
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
                    //item.setItems((ItemComprasUIAModelomisItemsCotizaciones);
                }
                misCotizacionesOrdenCompra.remove(nodo.getKey());
                break;
            }
        }
        return item;
    }


    public InfoComprasUIA buscaCotizacion(int id) {
        for (Entry<Integer, Cotizacion> nodo : misCotizacionesOrdenCompra.entrySet()) {
            if (nodo.getValue().getId() == id) {
                return nodo.getValue();
            }
        }
        return null;
    }


    public CotizacionModelo putCotizacion(int id, ItemComprasUIAModelo newItem) {
        InfoComprasUIA cotizacion = null;
        InfoComprasUIA item = null;
        if ((cotizacion = this.buscaCotizacion(id)) != null)
            if ((item = cotizacion.buscaItem(newItem.getName())) != null) {
                item.setCantidad(newItem.getCantidad());
                item.setClasificacion(newItem.getClasificacion());
                item.setCodigo(newItem.getCodigo());
                item.setDescripcion(newItem.getDescripcion());
                item.setName(newItem.getName());
            }
        return this.getCotizacion(id);
    }

    public ArrayList<ReporteModelo> getReportes() {
        miModeloReportes = new ArrayList<ReporteModelo>();

        for (int i = 0; i < miReporteNS.getItems().size(); i++) {
            //    public ReporteModelo(int id, String name, String codigo, int vendedor, int clasificacion, int existenciaMinima, int existencia, int consumo)
                    ReporteModelo nodo = new ReporteModelo(
                            miReporteNS.getItems().get(i).getId()
                            , miReporteNS.getItems().get(i).getName()
                            , miReporteNS.getItems().get(i).getCodigo()
                            , miReporteNS.getItems().get(i).getVendedor()
                            , miReporteNS.getItems().get(i).getClasificacion()
                            , miReporteNS.getItems().get(i).getExistenciaMinima()
                            , miReporteNS.getItems().get(i).getExistencia()
                            , miReporteNS.getItems().get(i).getConsumo()
                            );
                    miModeloReportes.add(nodo);
                }

        return miModeloReportes;

    }

    public ReporteModelo getReporte(int id) {
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

    public ReporteModelo deleteReporte(int id) {
        return null;
    }

    public ReporteModelo putReporte(int id, ItemComprasUIAModelo newItem) {
        return null;
    }


    public ArrayList<ItemSolicitudOCModelo> getSolicitudesOC(int id) {

        ArrayList<ItemSolicitudOCModelo> misItemsSolicitudesOC = new ArrayList<ItemSolicitudOCModelo>();

        for(int i=0; i<misSolicitudesOC.size(); i++) {
            InfoComprasUIA nodoSolicitud = misSolicitudesOC.get(i);
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
                        misItemsSolicitudesOC.add(nodo);
                }
            }
        }
        return misItemsSolicitudesOC;
    }

    public SolicitudOCModelo getSolicitudOC(int id) {
        if (this.miModeloSolicitudesOC == null)
            this.getSolicitudesOC(id);
        for (int i = 0; i < this.miModeloSolicitudesOC.size(); i++) {
            if (this.miModeloSolicitudesOC.get(i).getId() == id)
                return this.miModeloSolicitudesOC.get(i);
        }

        return null;
    }


    public void printMiModeloSolicitudesOC() {
    }

    public SolicitudOCModelo deleteSolicitudOC(int id) {
        return null;
    }

    public SolicitudOCModelo putSolicitudOC(int id, ItemComprasUIAModelo newItem) {
        return null;
    }


    public void postReporte(ItemComprasUIAModelo newItem)
    {
    }

    public ItemReporteModelo agregaRNSIItem(InfoComprasUIA item) throws IOException {
        int id = miReporteNS.getItems().size();
        String name = "reporte_agregado"+id;
        ReporteNivelStock newReporte = new ReporteNivelStock(id, name);
        ArrayList<InfoComprasUIA> miLista = new ArrayList<InfoComprasUIA>();

        miLista.add(item);
        newReporte.setItems(miLista);

        miReporteNS.getItems().add(newReporte);

        if (this.miReporteNS == null)
            this.miReporteNS = new ReporteNivelStockConcreto();
        this.miReporteNS.getItems().add(newReporte);
        try
        {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.writeValue(new File("arregloItemsV1.json"), miReporteNS);

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
        ItemReporteModelo nodo = null;

        for (int i = 0; i < newReporte.getItems().size(); i++) {
            //   CotizacionModelo(int id, String name, String codigo,  int vendedor, int clasificacionVendedor, double total, int entrega)
            if (newReporte.getItems().get(i) != null)
            {
                ArrayList<ItemComprasUIAModelo> misItemsReportes = new ArrayList<ItemComprasUIAModelo>();
                    //ItemReporteModelo(int cantidad, double valorUnitario, double subtotal, double total)
                    nodo = new ItemReporteModelo(
                            newReporte.getItems().get(i).getCantidad()
                            , newReporte.getItems().get(i).getName()
                            , newReporte.getItems().get(i).getClasificacion()
                            , newReporte.getItems().get(i).getId()
                            , newReporte.getItems().get(i).getCodigo()
                            , newReporte.getItems().get(i).getExistenciaMinima()
                            , newReporte.getItems().get(i).getExistencia()
                            , newReporte.getItems().get(i).getConsumo()
                            , newReporte.getItems().get(i).getPedidoProveedor());
                    nodo.setId(newReporte.getItems().get(i).getId() + 1);
                    miModeloReportes = getReportes();
            }
        }
        return nodo;
    }


}//end KardexListaKClientes