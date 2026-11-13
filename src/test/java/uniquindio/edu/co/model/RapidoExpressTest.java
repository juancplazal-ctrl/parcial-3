package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RapidoExpressTest {

    //PRUEBAS

    @Test
    void costoA10Km() {
        assertEquals(6510.0, new RapidoExpress().calcularCosto(10), 0.01);
    }

    @Test
    void costoA1Km() {
        assertEquals(6501.0, new RapidoExpress().calcularCosto(1), 0.01);
    }

    @Test
    void aceptaRecorridoDe12Km() {
        assertTrue(new RapidoExpress().aceptaRecorrido(12));
    }

    @Test
    void rechazaRecorridoDe13Km() {
        assertFalse(new RapidoExpress().aceptaRecorrido(13));
    }

    @Test
    void rechazaDistanciaCero() {
        assertFalse(new RapidoExpress().aceptaRecorrido(0));
    }

}
