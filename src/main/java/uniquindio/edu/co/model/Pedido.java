package uniquindio.edu.co.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private final String codigo;
    private final LocalDate fecha;
    private final Cliente cliente;
    private final Entrega modalidad;
    private EstPedido estado;
    private final double distanciaKm;
    private EmpresaDomi empresaDomi;
    private boolean entregaAceptada;
    private final List<DetallePedido> detalles = new ArrayList<>();

    public Pedido (String codigo, LocalDate fecha, Cliente cliente, Entrega modalidad, double distanciaKm) {

        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.modalidad = modalidad;
        this.estado = EstPedido.PENDIENTE;
        this.distanciaKm = distanciaKm;
        this.entregaAceptada = false;
    }

    public boolean agregarDetalle(Producto producto, int cantidad) {
        if (estado != EstPedido.PENDIENTE) {
            return false;
        }
        if (producto == null || cantidad <= 0) {
            return false;
        }
        detalles.add(new DetallePedido(producto, cantidad));
        return true;
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (DetallePedido d : detalles) {
            subtotal += d.subTotal();
        }
        return subtotal;
    }

    public boolean solicitarDomicilio(EmpresaDomi empresa) {
        if (modalidad != Entrega.DOMICILIO || empresa == null) {
            return false;
        }
        if (empresa.aceptaRecorrido(distanciaKm)) {
            this.empresaDomi = empresa;
            this.entregaAceptada = true;
            return true;
        }
        this.entregaAceptada = false;
        return false;
    }

    public double calcularCostoDomicilio() {
        if (modalidad == Entrega.DOMICILIO && entregaAceptada && empresaDomi != null) {
            return empresaDomi.calcularCosto(distanciaKm);
        }
        return 0;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularCostoDomicilio();
    }

    public boolean puedeConfirmarse() {
        if (estado != EstPedido.PENDIENTE || detalles.isEmpty()) {
            return false;
        }
        for (DetallePedido d : detalles) {
            if (!d.producto().cumpleCondicionesCalidad()) {
                return false;
            }
        }
        if (modalidad == Entrega.DOMICILIO && !entregaAceptada) {
            return false;
        }
        return true;
    }

    public boolean confirmar() {
        if (!puedeConfirmarse()) {
            return false;
        }
        estado = EstPedido.CONFIRMADO;
        return true;
    }

    public boolean entregar() {
        if (estado != EstPedido.CONFIRMADO) {
            return false;
        }
        estado = EstPedido.ENTREGADO;
        return true;
    }

    public boolean cancelar() {
        if (estado == EstPedido.ENTREGADO || estado == EstPedido.CANCELADO) {
            return false;
        }
        estado = EstPedido.CANCELADO;
        return true;
    }

    public boolean esContabilizable() {
        return estado == EstPedido.CONFIRMADO || estado == EstPedido.ENTREGADO;
    }

    public ResumenPedido crearResumen() {
        return new ResumenPedido(codigo, fecha, estado, calcularTotal());
    }

    @Override
    public String toString() {
        return codigo + " | " + fecha + " | " + estado + " | $" + calcularTotal();
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Entrega getModalidad() {
        return modalidad;
    }

    public EstPedido getEstado() {
        return estado;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public EmpresaDomi getEmpresaDomi() {
        return empresaDomi;
    }

    public boolean isEntregaAceptada() {
        return entregaAceptada;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setEstado(EstPedido estado) {
        this.estado = estado;
    }

    public void setEmpresaDomi(EmpresaDomi empresaDomi) {
        this.empresaDomi = empresaDomi;
    }

    public void setEntregaAceptada(boolean entregaAceptada) {
        this.entregaAceptada = entregaAceptada;
    }
}
