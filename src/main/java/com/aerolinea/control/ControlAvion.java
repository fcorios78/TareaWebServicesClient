package com.aerolinea.control;

import com.aerolinea.entidad.Aviones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.ws.WebServiceRef;

@ManagedBean
@SessionScoped
public class ControlAvion implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8085/WebServices/wsAvion.wsdl")
    private com.aerolinea.cliente.WsAvion_Service service_2;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8085/WebServices/wsAvion.wsdl")
    private com.aerolinea.cliente.WsAvion_Service service;
    private List<Aviones> aviones;
    private Aviones avion;

    @PostConstruct
    public void init() {
        avion = new Aviones();
        aviones = new ArrayList<>();
    }

    public ControlAvion() {
    }

    public List<Aviones> getAviones() {
        List<com.aerolinea.cliente.Aviones> lista = consultarAviones();
        aviones.clear();
        for (com.aerolinea.cliente.Aviones a : lista) {
            aviones.add(new Aviones(a.getIdavion(), a.getCapacidad(), a.getDescripcion()));
        }
        return aviones;
    }

    public void setAviones(List<Aviones> aviones) {
        this.aviones = aviones;
    }

    public Aviones getAvion() {
        return avion;
    }

    public void setAvion(Aviones avion) {
        this.avion = avion;
    }

    public String preparaNuevo() {
        avion = new Aviones();
        return "avionForm.xhtml?faces-redirect=true";
    }
    public String edicion(Aviones item) {
        avion=item;
        return "avionForm.xhtml?faces-redirect=true";
    }
    public String guardar() {
        if (avion.getIdavion() == null) {
            com.aerolinea.cliente.Aviones a = new com.aerolinea.cliente.Aviones();
            a.setCapacidad(avion.getCapacidad());
            a.setDescripcion(avion.getDescripcion());
            create(a);
        }else{
            com.aerolinea.cliente.Aviones a = new com.aerolinea.cliente.Aviones();
            a.setIdavion(avion.getIdavion());
            a.setCapacidad(avion.getCapacidad());
            a.setDescripcion(avion.getDescripcion());
            update(a);
        }
        return "avionLista.xhtml?faces-redirect=true";
    }
    private void update(com.aerolinea.cliente.Aviones a) {
        try {
            com.aerolinea.cliente.WsAvion port = service.getWsAvionPort();
            port.edit(a);
        } catch (Exception ex) {
        }
    }    

    private void create(com.aerolinea.cliente.Aviones a) {
        try {
            com.aerolinea.cliente.WsAvion port = service.getWsAvionPort();
            port.create(a);
        } catch (Exception ex) {
        }
    }
    public String eliminar(Aviones item) {        
        avion=item;
        com.aerolinea.cliente.Aviones a = new com.aerolinea.cliente.Aviones();
        a.setIdavion(avion.getIdavion());
        a.setCapacidad(avion.getCapacidad());
        a.setDescripcion(avion.getDescripcion());
        System.out.println(avion.getIdavion());
        delete(a);
        return "avionLista.xhtml?faces-redirect=true";
    }

    private void delete(com.aerolinea.cliente.Aviones a) {
        try {
            com.aerolinea.cliente.WsAvion port = service.getWsAvionPort();
            port.remove(a);
        } catch (Exception ex) {
        }
    }    
    private List<com.aerolinea.cliente.Aviones> consultarAviones() {
        try {
            com.aerolinea.cliente.WsAvion port = service.getWsAvionPort();
            java.util.List<com.aerolinea.cliente.Aviones> result = port.findAll();
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

   
    
}
