package uniquindio.edu.co.model;

public class RapidoExpress implements EmpresaDomi {

    //ATRIBUTOS

    private final double tarifaBase;
    private final double tarifaPorKm;
    private final double maxKm;

    //METODO CONSTRUCTOR, SE DECLARAN PARA SABER LO VALORES DE UNA VEZ

    public RapidoExpress() {
        this.tarifaBase = 5000;
        this.tarifaPorKm = 1500;
        this.maxKm = 12;
    }

    //METODOS PARA EL NOMBRE, SI ACEPTA EL DOMI Y PARA CALCULAR CUANTO SERIA EL DOMI

    @Override
    public String getNombre(){
        return "RapidoExpress";
    }

    @Override
    public boolean aceptaRecorrido(double km){
        return km > 0 && km <= maxKm;
    }

    @Override
    public double calcularCosto(double km){
        return tarifaBase + tarifaPorKm + km;
    }

    //GETTER

    public double getTarifaBase() {
        return tarifaBase;
    }

    public double getTarifaPorKm() {
        return tarifaPorKm;
    }

    public double getMaxKm() {
        return maxKm;
    }
}
