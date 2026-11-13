package uniquindio.edu.co.model;

import java.time.LocalDate;

public record ResumenPedido(String codigo, LocalDate fecha, EstPedido estado, double valorTotal) {
}
