package uia.com.apirest.patronesDisenno;

import uia.com.apirest.compras.InfoComprasUIA;

import java.util.ArrayList;
import java.util.List;

public abstract class AListaInfoComprasUIA implements IListaInfoComprasUIA{

    protected int id;
    protected String name;
    private List<InfoComprasUIA> items;

    public AListaInfoComprasUIA(List<InfoComprasUIA> pl) {
        this.items = pl;
    }

    public AListaInfoComprasUIA() {
        this.items = new ArrayList<InfoComprasUIA>();
    }

    public AListaInfoComprasUIA(int id, String name)
    {
        this.id=id;
        this.name=name;
    }

    public List<InfoComprasUIA> getItems() {
        return this.items;
    }

    public void setItems(List<InfoComprasUIA> l) {
        this.items = l;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

