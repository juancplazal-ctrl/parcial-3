package uniquindio.edu.co;

public abstract class Producto implements Verificar {

    private final String codigo;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int tiempoPreparacionMin;


    protected Producto(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacionMin){

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiempoPreparacionMin = tiempoPreparacionMin;
    }


}
