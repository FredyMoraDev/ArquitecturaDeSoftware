package uia.com.apimvcrest.compras;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uia.com.apimvcrest.modelo.CotizacionModelo;
import uia.com.apimvcrest.modelo.ItemCotizacionModelo;
import uia.com.apimvcrest.modelo.ItemPeticionOrdenCompraModelo;
import uia.com.apimvcrest.modelo.PeticionOrdenCompraModelo;


/**
 * @author FredyMora
 * @version 1.0
 * @created 31-Ene.-2022 11:27:37 a. m.
 */
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
    ArrayList<PeticionOrdenCompraModelo> miModeloPeticionOrdenCompra;


    public GestorCompras() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            miReporteNS = mapper.readValue(new FileInputStream("C:/Users/Hp/OneDrive - Universidad Iberoamericana A.C. ACAD/IBERO SEMESTRE 4/ARQUITECTURA DE SOFTWARE/MoraFredy/ArquitecturaDeSoftware/ComprasProy/arregloItemsV1.json"), ListaReportesNivelStock.class);

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
                InfoComprasUIA miNodo = miReporteNS.getItems().get(i);
                miNodo.print();
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
                mapper.writeValue(new File("C:/Users/Hp/OneDrive - Universidad Iberoamericana A.C. ACAD/IBERO SEMESTRE 4/ARQUITECTURA DE SOFTWARE/MoraFredy/ArquitecturaDeSoftware/ComprasProy/SolicitudOrdenCompra-" + newSolicitud.getName() + ".json"), newSolicitud);
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
                            , misCotizacionesOrdenCompra.get(i).getTotal());
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


    public void printMiModeloPeticionOrdenCompras() {
        for (int i = 0; i < miModeloPeticionOrdenCompra.size(); i++)
            miModeloPeticionOrdenCompra.get(i).printpeticion();
    }

    public ArrayList<PeticionOrdenCompraModelo> getPeticionOrdenCompra() {

        miModeloPeticionOrdenCompra = new ArrayList<PeticionOrdenCompraModelo>();
        for (int i = 0; i < miModeloPeticionOrdenCompra.size(); i++) {
            PeticionOrdenCompraModelo item = new PeticionOrdenCompraModelo(misSolicitudesOC.get(i).getId()
                    , misSolicitudesOC.get(i).getName()
                    , misSolicitudesOC.get(i).getCodigo()
                    , misSolicitudesOC.get(i).getVendedor()
                    , misSolicitudesOC.get(i).getClasificacion()
                    , misSolicitudesOC.get(i).getTotal());
            if (misSolicitudesOC.get(i).getItems() != null) {
                ArrayList<ItemPeticionOrdenCompraModelo> misItemsPeticionOrdenCompra = new ArrayList<ItemPeticionOrdenCompraModelo>();
                for (int j = 0; j < misSolicitudesOC.get(i).getItems().size(); j++) {
                    ItemPeticionOrdenCompraModelo nodo = new ItemPeticionOrdenCompraModelo(misSolicitudesOC.get(i).getItems().get(j).getCantidad()
                            , misSolicitudesOC.get(i).getItems().get(j).getCantidad()
                            , misSolicitudesOC.get(i).getItems().get(j).getValorUnitario()
                            , misSolicitudesOC.get(i).getItems().get(j).getTotal());
                    misItemsPeticionOrdenCompra.add(nodo);
                }
                item.setItems(misItemsPeticionOrdenCompra);
                miModeloPeticionOrdenCompra.add(item);
            }
        }
        return miModeloPeticionOrdenCompra;
    }


    public PeticionOrdenCompraModelo getPeticionOrdenCompras(int id) {
        if (this.miModeloPeticionOrdenCompra == null)
            this.getPeticionOrdenCompra();
        for (int i = 0; i < this.miModeloPeticionOrdenCompra.size(); i++)
        {
            if(this.miModeloPeticionOrdenCompra.get(i).getId() == id)
            return this.miModeloPeticionOrdenCompra.get(i);
    }



    return null;
}
}//end KardexListaKClientes