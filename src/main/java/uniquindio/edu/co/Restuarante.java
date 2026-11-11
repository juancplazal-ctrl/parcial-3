package uniquindio.edu.co;

import java.util.ArrayList;
import java.util.List;

public class Restaurante {

    private final String nombreComercial;
    private final String nit;
    private final String direccion;
    private final List<Producto> menu = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Pedido> historialPedidos = new ArrayList<>();
    private final List<EmpresaDomicilio> empresasDomicilio = new ArrayList<>();

    public Restaurante(String nombreComercial, String nit, String direccion) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
    }
}