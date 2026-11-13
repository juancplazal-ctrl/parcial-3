package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntregaSeguraTest {

    //PRUEBAS

    @Test
    void costoA3KmSinRecargo() {
        EntregaSegura empresa = new EntregaSegura();
        assertEquals(8003.0, empresa.calcularCosto(3));
    }

    @Test
    void costoA5KmNoAplicaRecargo() {
        EntregaSegura empresa = new EntregaSegura();
        assertEquals(8005.0, empresa.calcularCosto(5));
    }

    @Test
    void costoA8KmAplicaRecargo() {
        EntregaSegura empresa = new EntregaSegura();
        assertEquals(11008.0, empresa.calcularCosto(8));
    }

    @Test
    void aceptaRecorridoDe20Km() {
        EntregaSegura empresa = new EntregaSegura();
        assertTrue(empresa.aceptaRecorrido(20));
    }

    @Test
    void rechazaRecorridoDe21Km() {
        EntregaSegura empresa = new EntregaSegura();
        assertFalse(empresa.aceptaRecorrido(21));
    }
}



