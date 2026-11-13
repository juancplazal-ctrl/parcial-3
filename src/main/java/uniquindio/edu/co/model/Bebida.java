package uniquindio.edu.co.model;

public class Bebida extends Producto {

    private double volumenMl;
    private double temperaturaServicio;
    private Temperatura temperatura;
    private boolean requierePreparacionEspecial;

    public Bebida(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacionMin, double volumenMl, double temperaturaServicio, Temperatura temperatura, boolean requierePreparacionEspecial) {

        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMin);
        this.volumenMl = volumenMl;
        this.temperaturaServicio = temperaturaServicio;
        this.temperatura = temperatura;
        this.requierePreparacionEspecial = requierePreparacionEspecial;

    }

    @Override
    public double calcularPrecioVenta(){
        double precio = getPrecioBase() * 1.10;
        if(requierePreparacionEspecial){
            precio = precio + 3000;
        }
    return precio;
    }

    @Override
    public boolean cumpleCondicionesCalidad(){
        if(volumenMl < 150 || volumenMl > 1500){
            return false;
        }
        if(temperatura == Temperatura.FRIO){
            return temperaturaServicio >= 0 && temperaturaServicio <= 10;
        }
        return temperaturaServicio >= 55 && temperaturaServicio <= 85;
    }

    @Override
    public TipoProducto getCategoria(){
        return TipoProducto.BEBIDA;
    }

    public double getVolumenMl() {
        return volumenMl;
    }

    public void setVolumenMl(double volumenMl) {
        this.volumenMl = volumenMl;
    }

    public double getTemperaturaServicio() {
        return temperaturaServicio;
    }

    public void setTemperaturaServicio(double temperaturaServicio) {
        this.temperaturaServicio = temperaturaServicio;
    }

    public Temperatura getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Temperatura temperatura) {
        this.temperatura = temperatura;
    }

    public boolean isRequierePreparacionEspecial() {
        return requierePreparacionEspecial;
    }

    public void setRequierePreparacionEspecial(boolean requierePreparacionEspecial) {
        this.requierePreparacionEspecial = requierePreparacionEspecial;
    }
}