package uniquindio.edu.co.model;

public abstract class Producto implements Verificar {

    //ATRIBUTOS, CLASE PADRE

    private final String codigo;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int tiempoPreparacionMin;

    //METODO COSNTRUCTOR

    protected Producto(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacionMin){

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiempoPreparacionMin = tiempoPreparacionMin;
    }

    //SE DECLARAN METODOS
    public abstract double calcularPrecioVenta();

    public abstract TipoProducto getCategoria();

    @Override
    public String toString(){
        return codigo + " - " + nombre + " ($ " + calcularPrecioVenta() + ")";
    }

    //GETTER, SETTER

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public int getTiempoPreparacionMin() {
        return tiempoPreparacionMin;
    }

    public void setTiempoPreparacionMin(int tiempoPreparacionMin) {
        this.tiempoPreparacionMin = tiempoPreparacionMin;
    }
}
