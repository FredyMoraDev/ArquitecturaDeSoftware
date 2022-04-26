package uia.com.apirest.patronesDisenno;

import uia.com.apirest.compras.InfoComprasUIA;

import java.util.ArrayList;
import java.util.List;

public interface IListaInfoComprasUIA {
    public List<InfoComprasUIA> getItems();
    public void setItems(List<InfoComprasUIA> l);
    public int getId();
    public void setId(int id);
    public String getName();
    public void setName(String name);
}
