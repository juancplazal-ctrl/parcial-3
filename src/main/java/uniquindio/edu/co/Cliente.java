package uniquindio.edu.co;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private final String nombre;
    private final int documento;
    private final int telefono;
    private final String correo;
    private final String domicilio;
    private final List<Pedido> historialPedidos = new ArrayList<>();

    public Cliente (String nombre, int documento, int telefono, String correo, String domicilio){

        this.nombre = nombre;
        this.documento = documento;
        this.telefono =telefono;
        this.correo = correo;
        this.domicilio = domicilio;

    }
}
