package uniquindio.edu.co.model;

public record DetallePedido(Producto producto, int cantidad) {

    //CLASE RECORD CON METODO PARA CALCULAR EL TOTAL DEL CONSUMO

    public double subTotal(){
        return producto.calcularPrecioVenta() * cantidad;
    }

}
