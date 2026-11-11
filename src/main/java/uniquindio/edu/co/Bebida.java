package uniquindio.edu.co;

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
}