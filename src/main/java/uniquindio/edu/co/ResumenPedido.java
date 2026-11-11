package uniquindio.edu.co;

import java.time.LocalDate;

public record ResumenPedido(String codigo, LocalDate fecha, EstadoPedido estado, double valorTotal) {
}
