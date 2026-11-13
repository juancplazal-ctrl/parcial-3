package uniquindio.edu.co.model;

public class RapidoExpress implements EmpresaDomi {

    private final double tarifaBase;
    private final double tarifaPorKm;
    private final double maxKm;

    public RapidoExpress() {
        this.tarifaBase = 5000;
        this.tarifaPorKm = 1500;
        this.maxKm = 12;
    }

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
