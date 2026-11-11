package uniquindio.edu.co;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private final String codigo;
    private final LocalDate fecha;
    private final Cliente cliente;
    private final ModalidadEntrega modalidad;
    private EstPedido estado;
    private final double distanciaKm;
    private EmpresaDomi empresaDomi;
    private boolean entregaAceptada;
    private final List<DetallePedido> detalles = new ArrayList<>();

    public Pedido (String codigo, LocalDate fecha, Cliente cliente, ModalidadEntrega modalidad, EstPedido estado,double distanciaKm) {

        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.modalidad = modalidad;
        this.estado = EstPedido.PENDIENTE;
        this.distanciaKm = distanciaKm;
        this.entregaAceptada = false;
    }
}
