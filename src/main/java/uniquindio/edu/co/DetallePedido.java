package uniquindio.edu.co;

public record DetallePedido(Producto producto, int cantidad) {

    public double subTotal(){
        return producto.calcularPrecioVenta() * cantidad;
    }

}
