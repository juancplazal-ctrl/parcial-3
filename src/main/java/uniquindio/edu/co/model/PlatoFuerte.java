package uniquindio.edu.co.model;

public class PlatoFuerte extends Producto {

    //ATRIBUTOS

    private double pesoGramos;
    private Preparacion tipoPreparacion;
    private boolean coccionAprobada;

    //METODO CONSTRUCTOR

    public PlatoFuerte(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacionMin, double pesoGramos, Preparacion tipoPreparacion, boolean coccionAprobada){

        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMin);
        this.pesoGramos = pesoGramos;
        this.tipoPreparacion = tipoPreparacion;
        this.coccionAprobada = coccionAprobada;

    }

    //METODOS PARA CALCULAR EL PRECIO DEL PLATO Y SI CUMPLE LAS CONDICIONES

    @Override
    public double calcularPrecioVenta() {
        return getPrecioBase() * 1.20;
    }

    @Override
    public boolean cumpleCondicionesCalidad(){
        if(pesoGramos < 200 || pesoGramos > 900){
            return false;
        }
        if(getTiempoPreparacionMin() < 10 || getTiempoPreparacionMin() > 60){
            return false;
        }
    return coccionAprobada;
    }

    @Override
    public TipoProducto getCategoria(){
        return TipoProducto.PLATO_FUERTE;
    }

    //GETTER Y SETTER

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public Preparacion getTipoPreparacion() {
        return tipoPreparacion;
    }

    public void setTipoPreparacion(Preparacion tipoPreparacion) {
        this.tipoPreparacion = tipoPreparacion;
    }

    public boolean isCoccionAprobada() {
        return coccionAprobada;
    }

    public void setCoccionAprobada(boolean coccionAprobada) {
        this.coccionAprobada = coccionAprobada;
    }
}
