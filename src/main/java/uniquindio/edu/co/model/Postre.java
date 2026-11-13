package uniquindio.edu.co.model;

public class Postre extends Producto {

    //ATRIBUTOS

    private double pesoGramos;
    private double temperaturaAlmacenamiento;
    private boolean requiereRefrigeracion;

    //METODO CONSTRUCTOR

    public Postre(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacionMin, double pesoGramos, double temperaturaAlmacenamiento, boolean requiereRefrigeracion) {

        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMin);
        this.pesoGramos = pesoGramos;
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
        this.requiereRefrigeracion = requiereRefrigeracion;

    }

    //METODO PARA CALCULAR EL PRECIO DEL POSTRE Y EL VALOR FINAL

    @Override
    public  double calcularPrecioVenta(){
        double precio = getPrecioBase() * 1.15;
        if(requiereRefrigeracion){
            precio = precio + getPrecioBase() * 0.05;
        }
        return  precio;
    }

    @Override
    public boolean cumpleCondicionesCalidad(){
        if(pesoGramos < 80 || pesoGramos > 500){
            return false;
        }
        if(requiereRefrigeracion){
            return temperaturaAlmacenamiento >= 0 && temperaturaAlmacenamiento <= 6;
        }
        return temperaturaAlmacenamiento >= 15 && temperaturaAlmacenamiento <= 30;
    }

    //GETTER SETTER

    @Override
    public TipoProducto getCategoria(){
        return TipoProducto.POSTRE;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public double getTemperaturaAlmacenamiento() {
        return temperaturaAlmacenamiento;
    }

    public void setTemperaturaAlmacenamiento(double temperaturaAlmacenamiento) {
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
    }

    public boolean isRequiereRefrigeracion() {
        return requiereRefrigeracion;
    }

    public void setRequiereRefrigeracion(boolean requiereRefrigeracion) {
        this.requiereRefrigeracion = requiereRefrigeracion;
    }
}