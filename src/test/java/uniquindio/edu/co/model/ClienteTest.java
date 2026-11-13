package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    //PRUEBAS

    private Cliente cliente() {
        return new Cliente("1094", "Ana Perez", "300", "ana@correo.com", "Calle 1");
    }

    private Pedido pedido(Cliente c, String codigo) {
        return new Pedido(codigo, LocalDate.now(), c, Entrega.RESTAURANTE, 0);
    }

    @Test
    void agregaUnPedido() {
        Cliente c = cliente();
        assertTrue(c.agregarPedido(pedido(c, "PED01")));
    }

    @Test
    void noAgregaPedidoConCodigoRepetido() {
        Cliente c = cliente();
        Pedido p = pedido(c, "PED01");
        c.agregarPedido(p);
        assertFalse(c.agregarPedido(p));
    }

    @Test
    void noAgregaPedidoNulo() {
        assertFalse(cliente().agregarPedido(null));
    }

    @Test
    void historialNuevoEstaVacio() {
        assertEquals(0, cliente().consultarHistorial().size());
    }

    @Test
    void historialMuestraElPedidoAgregado() {
        Cliente c = cliente();
        c.agregarPedido(pedido(c, "PED01"));
        assertEquals(1, c.consultarHistorial().size());
        assertEquals("PED01", c.consultarHistorial().get(0).codigo());
    }

}
