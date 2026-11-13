package uniquindio.edu.co.model;

import java.util.ArrayList;
import java.util.List;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Restaurante {

    //ATRIBUTOS Y ARRAY LIST

    private final String nombreComercial;
    private final String nit;
    private final String direccion;
    private final List<Producto> menu = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Pedido> historialPedidos = new ArrayList<>();
    private final List<EmpresaDomi> empresasDomicilio = new ArrayList<>();

    //METODO CONSTRUCTOR

    public Restaurante(String nombreComercial, String nit, String direccion) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
    }

    //METODO PARA REGISTRAR EL PRODUCTO

    public boolean registrarProducto(Producto producto) {
        if (producto == null || buscarProducto(producto.getCodigo()) != null) {
            return false;
        }
        menu.add(producto);
        return true;
    }

    //METODO PARA REGISTRAR AL CLIENTE

    public boolean registrarCliente(Cliente cliente) {
        if (cliente == null || buscarCliente(cliente.getDocumento()) != null) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    //METODO PARA GREGAR LA EMPRESA DE DOMICILIO

    public void agregarEmpresaDomicilio(EmpresaDomi empresa) {
        if (empresa != null) {
            empresasDomicilio.add(empresa);
        }
    }

    //METODO PARA BUSCAR EL PRODUCTO MEDIANTE EL CODIGO

    public Producto buscarProducto(String codigo) {
        for (Producto p : menu) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    //METODO PARA BUSCAR A UN CLIENTE

    public Cliente buscarCliente(String documento) {
        for (Cliente c : clientes) {
            if (c.getDocumento().equals(documento)) {
                return c;
            }
        }
        return null;
    }

    //METODO PARA BUSCAR UN PEDIDO

    public Pedido buscarPedido(String codigo) {
        for (Pedido p : historialPedidos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    //METODO PARA REGISTRAR UN PEDIDO

    public boolean registrarPedido(Pedido pedido) {
        if (pedido == null) {
            return false;
        }
        Cliente registrado = buscarCliente(pedido.getCliente().getDocumento());
        if (registrado == null || buscarPedido(pedido.getCodigo()) != null) {
            return false;
        }
        historialPedidos.add(pedido);
        registrado.agregarPedido(pedido);
        return true;
    }

    //METODO PARA SOLICITAR UN DOMICILIO

    public boolean solicitarDomicilio(Pedido pedido) {
        for (EmpresaDomi empresa : empresasDomicilio) {
            if (pedido.solicitarDomicilio(empresa)) {
                return true;
            }
        }
        return false;
    }

    //METODO PARA CONFIRMAR EL PEDIDO

    public boolean confirmarPedido(String codigo) {
        Pedido pedido = buscarPedido(codigo);
        if (pedido == null) {
            return false;
        }
        for (DetallePedido d : pedido.getDetalles()) {
            if (buscarProducto(d.producto().getCodigo()) == null) {
                return false;
            }
        }
        return pedido.confirmar();
    }

    //METODOS PARA ENTREGAR O CANLCELAR EL PEDIDO

    public boolean entregarPedido(String codigo) {
        Pedido pedido = buscarPedido(codigo);
        return pedido != null && pedido.entregar();
    }

    public boolean cancelarPedido(String codigo) {
        Pedido pedido = buscarPedido(codigo);
        return pedido != null && pedido.cancelar();
    }

    //METODO PARA CALCULAR EL VALOR TOTAL

    public double calcularValorTotal(String codigo) {
        Pedido pedido = buscarPedido(codigo);
        if (pedido == null) {
            return -1;
        }
        return pedido.calcularTotal();
    }

    //LIST PARA EL HISTORIAL DEL CLIENTE, PRODUCTOD DISPONIBLES, PRODUCTOS NO CONFORMES

    public List<ResumenPedido> consultarHistorialCliente(String documento) {
        Cliente cliente = buscarCliente(documento);
        if (cliente == null) {
            return null;
        }
        return cliente.consultarHistorial();
    }

    public List<Producto> consultarProductosDisponibles() {
        List<Producto> disponibles = new ArrayList<>();
        for (Producto p : menu) {
            if (p.cumpleCondicionesCalidad()) {
                disponibles.add(p);
            }
        }
        return disponibles;
    }

    public List<Producto> consultarProductosNoConformes() {
        List<Producto> noConformes = new ArrayList<>();
        for (Producto p : menu) {
            if (!p.cumpleCondicionesCalidad()) {
                noConformes.add(p);
            }
        }
        return noConformes;
    }

    //METODO PARA CALCUALR LOS INGRESOS DEL RESTAURANTE

    public ReporteIngresos calcularIngresos() {
        double restaurante = 0;
        double domicilios = 0;
        for (Pedido p : historialPedidos) {
            if (p.esContabilizable()) {
                restaurante += p.calcularSubtotal();
                domicilios += p.calcularCostoDomicilio();
            }
        }
        return new ReporteIngresos(restaurante, domicilios, restaurante + domicilios);
    }

    //METODO PARA VER CUAL ES EL PRODUCTO MAS VENDIDO

    public Producto productoMasVendido() {
        Map<String, Integer> unidades = new HashMap<>();
        for (Pedido p : historialPedidos) {
            if (p.esContabilizable()) {
                for (DetallePedido d : p.getDetalles()) {
                    String cod = d.producto().getCodigo();
                    unidades.put(cod, unidades.getOrDefault(cod, 0) + d.cantidad());
                }
            }
        }
        String mejor = null; // código del producto con más unidades
        int max = 0;  // mayor cantidad de unidades encontrada hasta ahora
        for (Map.Entry<String, Integer> e : unidades.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                mejor = e.getKey();
            }
        }
        if (mejor == null) {
            return null;
        }
        return buscarProducto(mejor);
    }

    public TipoProducto categoriaMayorIngreso() {
        Map<TipoProducto, Double> ingresos = new EnumMap<>(TipoProducto.class);
        for (Pedido p : historialPedidos) {
            if (p.esContabilizable()) {
                for (DetallePedido d : p.getDetalles()) {
                    TipoProducto cat = d.producto().getCategoria();
                    ingresos.put(cat, ingresos.getOrDefault(cat, 0.0) + d.subTotal());
                }
            }
        }
        TipoProducto mejor = null;
        double max = 0;
        for (Map.Entry<TipoProducto, Double> e : ingresos.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                mejor = e.getKey();
            }
        }
        return mejor;
    }

    //GETTER

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Producto> getMenu() {
        return menu;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }

    public List<EmpresaDomi> getEmpresasDomicilio() {
        return empresasDomicilio;
    }
}