package uniquindio.edu.co;

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

}
