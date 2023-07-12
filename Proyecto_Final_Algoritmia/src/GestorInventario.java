import java.io.*;
import java.util.Scanner;

public class GestorInventario {
    private String[] productos;
    private double[] preciosCompra;
    private int[] cantidades;
    private double[] preciosVenta;
    private int totalProductos;

    public GestorInventario(int capacidadMaxima) {
        productos = new String[capacidadMaxima];
        preciosCompra = new double[capacidadMaxima];
        cantidades = new int[capacidadMaxima];
        preciosVenta = new double[capacidadMaxima];
        totalProductos = 0;
    }

    public void agregarProducto(String producto, double precioCompra, int cantidad, double precioVenta) {
        productos[totalProductos] = producto;
        preciosCompra[totalProductos] = precioCompra;
        cantidades[totalProductos] = cantidad;
        preciosVenta[totalProductos] = precioVenta;
        totalProductos++;
        System.out.println("Producto agregado con éxito.");
    }

    public void buscarProducto(String producto) {
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].equalsIgnoreCase(producto)) {
                System.out.println("Producto encontrado:");
                System.out.println("Nombre: " + productos[i]);
                System.out.println("Precio de compra: $" + preciosCompra[i]);
                System.out.println("Cantidad en inventario: " + cantidades[i]);
                System.out.println("Precio de venta: $" + preciosVenta[i]);
                return;
            }
        }
        System.out.println("El producto no se encuentra en el inventario.");
    }

    public void guardarInventario(String archivo) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            for (int i = 0; i < totalProductos; i++) {
                writer.write(productos[i] + "," + preciosCompra[i] + "," + cantidades[i] + "," + preciosVenta[i]);
                writer.newLine();
            }
            writer.close();
            System.out.println("Inventario guardado con éxito.");
        } catch (IOException e) {
            System.out.println("Error al guardar el inventario: " + e.getMessage());
        }
    }

    public void cargarInventario(String archivo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                String producto = datos[0];
                double precioCompra = Double.parseDouble(datos[1]);
                int cantidad = Integer.parseInt(datos[2]);
                double precioVenta = Double.parseDouble(datos[3]);
                agregarProducto(producto, precioCompra, cantidad, precioVenta);
            }
            reader.close();
            System.out.println("Inventario cargado con éxito.");
        } catch (IOException e) {
            System.out.println("Error al cargar el inventario: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String archivo = "inventario.txt";

        System.out.print("Ingrese la capacidad máxima del inventario: ");
        int capacidadMaxima = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        GestorInventario gestor = new GestorInventario(capacidadMaxima);

        // Cargar inventario si existe un archivo guardado
        File archivoInventario = new File(archivo);
        if (archivoInventario.exists()) {
            gestor.cargarInventario(archivo);
        }

        while (true) {
            System.out.println("\n=== GESTOR DE INVENTARIO ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Guardar inventario");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del producto: ");
                    String nombreProducto = scanner.nextLine();
                    System.out.print("Ingrese el precio de compra: ");
                    double precioCompra = scanner.nextDouble();
                    System.out.print("Ingrese la cantidad en inventario: ");
                    int cantidad = scanner.nextInt();
                    System.out.print("Ingrese el precio de venta: ");
                    double precioVenta = scanner.nextDouble();
                    gestor.agregarProducto(nombreProducto, precioCompra, cantidad, precioVenta);
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del producto a buscar: ");
                    String productoBuscado = scanner.nextLine();
                    gestor.buscarProducto(productoBuscado);
                    break;
                case 3:
                    gestor.guardarInventario(archivo);
                    break;
                case 4:
                    System.out.println("¡Hasta luego!");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}
