package uniquindio.edu.co.model;

public class EntregaSegura implements EmpresaDomi{

    private final double tarifaBase;
    private final double tarifaPorKm;
    private final double recargo;
    private final double umbralRecargoKm;
    private final double maxKm;

    public EntregaSegura() {
        this.tarifaBase = 7000;
        this.tarifaPorKm = 1000;
        this.recargo = 3000;
        this.umbralRecargoKm = 5;
        this.maxKm = 20;
    }

    @Override
    public String getNombre(){
        return  "EntregaSegura";
    }

    @Override
    public boolean aceptaRecorrido(double km){
        return km > 0 && km <= maxKm;
    }

    @Override
    public double calcularCosto(double km){
        double costo = tarifaBase + tarifaPorKm + km;
        if(km > umbralRecargoKm){
            costo = costo + recargo;
        }
        return costo;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public double getTarifaPorKm() {
        return tarifaPorKm;
    }

    public double getRecargo() {
        return recargo;
    }

    public double getUmbralRecargoKm() {
        return umbralRecargoKm;
    }

    public double getMaxKm() {
        return maxKm;
    }
}
