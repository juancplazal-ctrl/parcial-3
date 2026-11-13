package uniquindio.edu.co.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private final String documento;
    private final String nombre;
    private final String telefono;
    private final String correo;
    private final String domicilio;
    private final List<Pedido> historialPedidos = new ArrayList<>();

    public Cliente (String documento, String nombre, String telefono, String correo, String domicilio){

        this.documento = documento;
        this.nombre = nombre;
        this.telefono =telefono;
        this.correo = correo;
        this.domicilio = domicilio;

    }

    public boolean agregarPedido(Pedido pedido){
        if(pedido == null){
        return false;
    }
    for(Pedido p : historialPedidos){
        if(p.getCodigo().equals(pedido.getCodigo())){
            return false;
            }
        }
    historialPedidos.add(pedido);
    return true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public List<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }

    public List<ResumenPedido> consultarHistorial(){
        List<ResumenPedido> resumenes = new ArrayList<>();
        for(Pedido p : historialPedidos){
            resumenes.add(p.crearResumen());
        }
        return resumenes;
    }
}
