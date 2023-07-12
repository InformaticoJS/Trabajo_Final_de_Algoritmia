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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la capacidad máxima del inventario: ");
        int capacidadMaxima = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        GestorInventario gestor = new GestorInventario(capacidadMaxima);

        while (true) {
            System.out.println("\n=== GESTOR DE INVENTARIO ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Salir");
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
                    System.out.println("¡Hasta luego!");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}