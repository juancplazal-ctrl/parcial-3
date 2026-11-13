package uniquindio.edu.co.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlatoFuerteTest {

    //PRUEBAS

    private PlatoFuerte plato(double peso, int tiempo, boolean coccion) {
        return new PlatoFuerte("P01", "Bandeja", "Tipico", 20000, tiempo,
                peso, Preparacion.ASADO, coccion);
    }

    @Test
    void precioConIncrementoDel20Porciento() {
        assertEquals(24000.0, plato(500, 30, true).calcularPrecioVenta(), 0.01);
    }

    @Test
    void platoValidoCumpleCalidad() {
        assertTrue(plato(500, 30, true).cumpleCondicionesCalidad());
    }

    @Test
    void pesoMenorA200NoCumple() {
        assertFalse(plato(150, 30, true).cumpleCondicionesCalidad());
    }

    @Test
    void tiempoMenorA10NoCumple() {
        assertFalse(plato(500, 5, true).cumpleCondicionesCalidad());
    }

    @Test
    void sinCoccionAprobadaNoCumple() {
        assertFalse(plato(500, 30, false).cumpleCondicionesCalidad());
    }

}
