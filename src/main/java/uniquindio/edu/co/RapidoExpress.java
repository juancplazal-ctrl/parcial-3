package uniquindio.edu.co;

public class RapidoExpress implements EmpresaDomi  {

    private final double tarifaBase;
    private final double tarifaPorKm;
    private final double maxKm;

    public RapidoExpress() {
        this.tarifaBase = 5000;
        this.tarifaPorKm = 1500;
        this.maxKm = 12;
    }
}
