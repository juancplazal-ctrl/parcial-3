package uniquindio.edu.co;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.util.List;

public class Main {

    private static final Restaurante restaurante =
            new Restaurante("El Buen Comer", "900123456-7", "Armenia, Quindío");

    public static void main(String[] args) {
        restaurante.agregarEmpresaDomicilio(new RapidoExpress());
        restaurante.agregarEmpresaDomicilio(new EntregaSegura());


        int opcion;
        do {
            opcion = mostrarMenu();
            switch (opcion) {
                case 1 -> registrarProducto();
                case 2 -> registrarCliente();
                case 3 -> registrarPedido();
                case 4 -> calcularValorTotal();
                case 5 -> consultarHistorial();
                case 6 -> actualizarEstadoPedido();
                case 7 -> consultarProductos();
                case 8 -> consultarVentas();
                case 0 -> mensaje("¡Hasta pronto!");
                default -> mensaje("Opción no válida");
            }
        } while (opcion != 0);
    }

    // ---------- Menú ----------

    private static int mostrarMenu() {
        String texto = leerTexto(
                "===== EL BUEN COMER =====\n"
                        + "1. Registrar producto\n"
                        + "2. Registrar cliente\n"
                        + "3. Registrar y confirmar pedido\n"
                        + "4. Calcular valor total de un pedido\n"
                        + "5. Historial de pedidos de un cliente\n"
                        + "6. Confirmar / entregar / cancelar pedido\n"
                        + "7. Consultar productos\n"
                        + "8. Consultar ventas\n"
                        + "0. Salir\n\n"
                        + "Digite una opción:");
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ---------- Funcionalidad 1: registrar productos ----------

    private static void registrarProducto() {
        int tipo = elegir("Tipo de producto", new String[]{"Plato fuerte", "Bebida", "Postre"});
        if (tipo < 0) {
            return;
        }
        String codigo = leerTexto("Código:");
        String nombre = leerTexto("Nombre:");
        String descripcion = leerTexto("Descripción:");
        double precio = leerNumero("Precio base:");
        double tiempo = leerNumero("Tiempo de preparación (minutos):");

        if (codigo.isEmpty() || nombre.isEmpty() || Double.isNaN(precio) || precio <= 0
                || Double.isNaN(tiempo) || tiempo <= 0) {
            mensaje("Datos inválidos. Producto no registrado.");
            return;
        }

        Producto producto = null;

        if (tipo == 0) {
            double peso = leerNumero("Peso (gramos):");
            int prep = elegir("Tipo de preparación", nombres(Preparacion.values()));
            boolean coccion = confirmar("¿La cocción fue aprobada?");
            if (Double.isNaN(peso) || prep < 0) {
                mensaje("Datos inválidos. Producto no registrado.");
                return;
            }
            producto = new PlatoFuerte(codigo, nombre, descripcion, precio, (int) tiempo,
                    peso, Preparacion.values()[prep], coccion);

        } else if (tipo == 1) {
            double volumen = leerNumero("Volumen (mililitros):");
            double temperatura = leerNumero("Temperatura de servicio (°C):");
            int tipoTemp = elegir("¿Fría o caliente?", nombres(Temperatura.values()));
            boolean especial = confirmar("¿Requiere preparación especial?");
            if (Double.isNaN(volumen) || Double.isNaN(temperatura) || tipoTemp < 0) {
                mensaje("Datos inválidos. Producto no registrado.");
                return;
            }
            producto = new Bebida(codigo, nombre, descripcion, precio, (int) tiempo,
                    volumen, temperatura, Temperatura.values()[tipoTemp], especial);

        } else {
            double peso = leerNumero("Peso (gramos):");
            double temperatura = leerNumero("Temperatura de almacenamiento (°C):");
            boolean refrigeracion = confirmar("¿Requiere refrigeración?");
            if (Double.isNaN(peso) || Double.isNaN(temperatura)) {
                mensaje("Datos inválidos. Producto no registrado.");
                return;
            }
            producto = new Postre(codigo, nombre, descripcion, precio, (int) tiempo,
                    peso, temperatura, refrigeracion);
        }

        if (restaurante.registrarProducto(producto)) {
            String texto = "Producto registrado.\nPrecio de venta: " + dinero(producto.calcularPrecioVenta());
            if (!producto.cumpleCondicionesCalidad()) {
                texto += "\nAtención: NO cumple las condiciones de calidad.";
            }
            mensaje(texto);
        } else {
            mensaje("Ya existe un producto con ese código.");
        }
    }

    // ---------- Funcionalidad 2: registrar clientes ----------

    private static void registrarCliente() {
        String documento = leerTexto("Documento:");
        String nombre = leerTexto("Nombre completo:");
        String telefono = leerTexto("Teléfono:");
        String correo = leerTexto("Correo electrónico:");
        String direccion = leerTexto("Dirección de residencia:");

        if (documento.isEmpty() || nombre.isEmpty()) {
            mensaje("El documento y el nombre son obligatorios.");
            return;
        }
        Cliente cliente = new Cliente(documento, nombre, telefono, correo, direccion);
        if (restaurante.registrarCliente(cliente)) {
            mensaje("Cliente registrado.");
        } else {
            mensaje("Ya existe un cliente con ese documento.");
        }
    }

    // ---------- Funcionalidad 3: registrar y confirmar pedidos ----------

    private static void registrarPedido() {
        String documento = leerTexto("Documento del cliente:");
        Cliente cliente = restaurante.buscarCliente(documento);
        if (cliente == null) {
            mensaje("El cliente no está registrado.");
            return;
        }

        String codigo = leerTexto("Código del pedido:");
        if (codigo.isEmpty()) {
            mensaje("El código es obligatorio.");
            return;
        }
        int modalidad = elegir("Modalidad de entrega", nombres(Entrega.values()));
        if (modalidad < 0) {
            return;
        }
        Entrega entrega = Entrega.values()[modalidad];

        double distancia = 0;
        if (entrega == Entrega.DOMICILIO) {
            distancia = leerNumero("Distancia del recorrido (km):");
            if (Double.isNaN(distancia) || distancia <= 0) {
                mensaje("Distancia inválida.");
                return;
            }
        }

        Pedido pedido = new Pedido(codigo, LocalDate.now(), cliente, entrega, distancia);
        if (!restaurante.registrarPedido(pedido)) {
            mensaje("No se pudo registrar: el código del pedido ya existe.");
            return;
        }

        boolean otro;
        do {
            String codProducto = leerTexto("Código del producto:");
            Producto producto = restaurante.buscarProducto(codProducto);
            if (producto == null) {
                mensaje("Ese producto no está en el menú.");
            } else {
                double cantidad = leerNumero("Cantidad:");
                if (Double.isNaN(cantidad) || !pedido.agregarDetalle(producto, (int) cantidad)) {
                    mensaje("Cantidad inválida. La cantidad debe ser mayor que cero.");
                } else {
                    mensaje("Producto agregado.");
                }
            }
            otro = confirmar("¿Agregar otro producto?");
        } while (otro);

        if (entrega == Entrega.DOMICILIO) {
            if (restaurante.solicitarDomicilio(pedido)) {
                mensaje("Una empresa aceptó el domicilio.\nCosto: " + dinero(pedido.calcularCostoDomicilio()));
            } else {
                mensaje("Ninguna empresa acepta ese recorrido.");
            }
        }

        if (restaurante.confirmarPedido(codigo)) {
            mensaje("Pedido CONFIRMADO.\nTotal: " + dinero(pedido.calcularTotal()));
        } else {
            mensaje("El pedido quedó PENDIENTE: no cumple las condiciones para confirmarse\n"
                    + "(productos sin calidad, sin productos o domicilio no aceptado).");
        }
    }

    // ---------- Funcionalidad 4: calcular valor total ----------

    private static void calcularValorTotal() {
        String codigo = leerTexto("Código del pedido:");
        double total = restaurante.calcularValorTotal(codigo);
        if (total < 0) {
            mensaje("No existe un pedido con ese código.");
            return;
        }
        Pedido pedido = restaurante.buscarPedido(codigo);
        mensaje("Subtotal productos: " + dinero(pedido.calcularSubtotal())
                + "\nDomicilio: " + dinero(pedido.calcularCostoDomicilio())
                + "\nTOTAL: " + dinero(total));
    }

    // ---------- Funcionalidad 5: historial del cliente ----------

    private static void consultarHistorial() {
        String documento = leerTexto("Documento del cliente:");
        List<ResumenPedido> historial = restaurante.consultarHistorialCliente(documento);
        if (historial == null) {
            mensaje("El cliente no está registrado.");
            return;
        }
        if (historial.isEmpty()) {
            mensaje("El cliente aún no tiene pedidos.");
            return;
        }
        StringBuilder texto = new StringBuilder("Historial de pedidos:\n\n");
        for (ResumenPedido r : historial) {
            texto.append("Código: ").append(r.codigo())
                    .append(" | Fecha: ").append(r.fecha())
                    .append(" | Estado: ").append(r.estado())
                    .append(" | Total: ").append(dinero(r.valorTotal()))
                    .append("\n");
        }
        mensaje(texto.toString());
    }

    // ---------- Consultar y actualizar pedidos ----------

    private static void actualizarEstadoPedido() {
        String codigo = leerTexto("Código del pedido:");
        if (restaurante.buscarPedido(codigo) == null) {
            mensaje("No existe un pedido con ese código.");
            return;
        }
        int accion = elegir("¿Qué desea hacer?", new String[]{"Confirmar", "Entregar", "Cancelar"});
        boolean exito;
        if (accion == 0) {
            exito = restaurante.confirmarPedido(codigo);
        } else if (accion == 1) {
            exito = restaurante.entregarPedido(codigo);
        } else if (accion == 2) {
            exito = restaurante.cancelarPedido(codigo);
        } else {
            return;
        }
        if (exito) {
            mensaje("Estado actualizado: " + restaurante.buscarPedido(codigo).getEstado());
        } else {
            mensaje("No se pudo realizar la acción con el estado actual del pedido.");
        }
    }

    // ---------- Consultas de productos ----------

    private static void consultarProductos() {
        StringBuilder texto = new StringBuilder("PRODUCTOS DISPONIBLES:\n");
        for (Producto p : restaurante.consultarProductosDisponibles()) {
            texto.append("  ").append(p).append("\n");
        }
        texto.append("\nPRODUCTOS QUE NO CUMPLEN CALIDAD:\n");
        for (Producto p : restaurante.consultarProductosNoConformes()) {
            texto.append("  ").append(p).append("\n");
        }
        mensaje(texto.toString());
    }

    // ---------- Ventas ----------

    private static void consultarVentas() {
        ReporteIngresos reporte = restaurante.calcularIngresos();
        Producto masVendido = restaurante.productoMasVendido();
        TipoProducto categoria = restaurante.categoriaMayorIngreso();

        String texto = "VENTAS DEL RESTAURANTE\n\n"
                + "Ingresos del restaurante: " + dinero(reporte.ingresosRestaurante()) + "\n"
                + "Valores de domicilios: " + dinero(reporte.ingresosDomicilios()) + "\n"
                + "Total: " + dinero(reporte.total()) + "\n\n"
                + "Producto más vendido: " + (masVendido == null ? "sin ventas" : masVendido) + "\n"
                + "Categoría con mayores ingresos: " + (categoria == null ? "sin ventas" : categoria);
        mensaje(texto);
    }

    // ---------- Datos de ejemplo (para la demostración) ----------



    // ---------- Utilidades de ventanas ----------

    private static String leerTexto(String mensaje) {
        String texto = JOptionPane.showInputDialog(null, mensaje, "El Buen Comer",
                JOptionPane.QUESTION_MESSAGE);
        if (texto == null) {
            return "";
        }
        return texto.trim();
    }

    private static double leerNumero(String mensaje) {
        try {
            return Double.parseDouble(leerTexto(mensaje).replace(",", "."));
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    private static int elegir(String mensaje, String[] opciones) {
        return JOptionPane.showOptionDialog(null, mensaje, "El Buen Comer",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);
    }

    private static boolean confirmar(String mensaje) {
        return JOptionPane.showConfirmDialog(null, mensaje, "El Buen Comer",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private static void mensaje(String texto) {
        JOptionPane.showMessageDialog(null, texto, "El Buen Comer", JOptionPane.INFORMATION_MESSAGE);
    }

    private static String[] nombres(Enum<?>[] valores) {
        String[] nombres = new String[valores.length];
        for (int i = 0; i < valores.length; i++) {
            nombres[i] = valores[i].name();
        }
        return nombres;
    }

    private static String dinero(double valor) {
        return String.format("$%,.0f", valor);
    }
}