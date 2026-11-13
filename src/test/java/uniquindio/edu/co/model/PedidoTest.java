package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    private final Cliente cliente = new Cliente("1", "Ana", "300", "a@a.com", "Calle");

    private Bebida bebida() {
        return new Bebida("B01", "Limonada", "Natural", 5000, 5,
                400, 5, Temperatura.FRIO, true);
    }

    private Pedido pedido(Entrega modalidad, double km) {
        return new Pedido("PED01", LocalDate.now(), cliente, modalidad, km);
    }

    @Test
    void subtotalSumaProductosPorCantidad() {
        Pedido p = pedido(Entrega.RESTAURANTE, 0);
        p.agregarDetalle(bebida(), 2);
        assertEquals(17000.0, p.calcularSubtotal(), 0.01);
    }

    @Test
    void noAceptaCantidadCero() {
        Pedido p = pedido(Entrega.RESTAURANTE, 0);
        assertFalse(p.agregarDetalle(bebida(), 0));
    }

    @Test
    void noSePuedeConfirmarDosVeces() {
        Pedido p = pedido(Entrega.RESTAURANTE, 0);
        p.agregarDetalle(bebida(), 1);
        assertTrue(p.confirmar());
        assertFalse(p.confirmar());
    }

    @Test
    void pedidoCanceladoNoSeEntrega() {
        Pedido p = pedido(Entrega.RESTAURANTE, 0);
        p.agregarDetalle(bebida(), 1);
        p.cancelar();
        assertFalse(p.entregar());
    }

    @Test
    void totalIncluyeElDomicilio() {
        Pedido p = pedido(Entrega.DOMICILIO, 8);
        p.agregarDetalle(bebida(), 1);
        assertTrue(p.solicitarDomicilio(new EntregaSegura()));
        assertEquals(19508.0, p.calcularTotal(), 0.01);
    }

}
