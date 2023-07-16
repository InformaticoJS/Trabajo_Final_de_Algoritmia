import java.io.*; // Se importa las clases  para manejar archivos
import java.util.Scanner; // Importar la clase Scanner para leer la entrada 

public class GestorInventario {
    private String[] productos; //Se declara el Array para almacenar los nombres de los productos , private indica que la variable productos es de tipo privado, lo que significa que solo puede ser accedida desde dentro de la clase   
                                //GestorInventario donde está declarada.
    private double[] preciosCompra; // Array para almacenar los precios de compra de los productos
    private int[] cantidades; // Array para almacenar las cantidades de los productos en inventario
    private double[] preciosVenta; // Array para almacenar los precios de venta de los productos
    private int totalProductos; // Variable para almacenar el número total de productos en inventario

    public GestorInventario(int capacidadMaxima) {
        productos = new String[capacidadMaxima]; // Se inicializa el array de productos con la capacidad máxima
        preciosCompra = new double[capacidadMaxima]; // Se inicializa el array de preciosCompra con la capacidad máxima
        cantidades = new int[capacidadMaxima]; // Se inicializa el array de cantidades con la capacidad máxima
        preciosVenta = new double[capacidadMaxima]; // Se inicializa el array de preciosVenta con la capacidad máxima
        totalProductos = 0; // Se inicializa el contador de totalProductos en 0
    }

    public void agregarProducto(String producto, double precioCompra, int cantidad, double precioVenta) {
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].equalsIgnoreCase(producto)) {
                // El producto ya existe en el inventario, se sobrescriben los datos
                preciosCompra[i] = precioCompra;
                cantidades[i] = cantidad;
                preciosVenta[i] = precioVenta;
                System.out.println("Producto actualizado con éxito.");
                return;
            }
        }
    
        // Si el producto no existe, se agrega al inventario
        productos[totalProductos] = producto;
        preciosCompra[totalProductos] = precioCompra;
        cantidades[totalProductos] = cantidad;
        preciosVenta[totalProductos] = precioVenta;
        totalProductos++;
        
        System.out.println("Producto agregado con éxito.");
    }
    

    public void buscarProducto(String producto) {
        for (int i = 0; i < totalProductos; i++) { // Recorre los productos en el inventario
            if (productos[i].equalsIgnoreCase(producto)) { // Compara el nombre del producto con el nombre buscado (ignorando mayúsculas y minúsculas)
                System.out.println("Producto encontrado:"); // Imprime un mensaje indicando que el producto fue encontrado
                System.out.println("Nombre: " + productos[i]); // Imprime el nombre del producto
                System.out.println("Precio de compra: $" + preciosCompra[i]); // Imprime el precio de compra del producto
                System.out.println("Cantidad en inventario: " + cantidades[i]); // Imprime la cantidad en inventario del producto
                System.out.println("Precio de venta: $" + preciosVenta[i]); // Imprime el precio de venta del producto
                return;
            }
        }
        System.out.println("El producto no se encuentra en el inventario."); // Imprime un mensaje indicando que el producto no fue encontrado
    }

    public void guardarInventario(String archivo) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo)); // Abre un BufferedWriter para escribir en el archivo especificado ,Osea crea un objeto BufferedWriter que se utiliza para escribir en un archivo especificado por la variable archivo. Este objeto se utilizará posteriormente para escribir datos en el archivo utilizando los métodos proporcionados por la clase BufferedWriter.
            for (int i = 0; i < totalProductos; i++) { // Recorre los productos en el inventario
                writer.write(productos[i] + "," + preciosCompra[i] + "," + cantidades[i] + "," + preciosVenta[i]); // Escribe los datos del producto separados por comas en una línea
                writer.newLine(); // Escribe un salto de línea
            }
            writer.close(); // Cierra el BufferedWriter
            System.out.println("Inventario guardado con éxito."); // Imprime un mensaje de éxito
        } catch (IOException e) {
            System.out.println("Error al guardar el inventario: " + e.getMessage()); // Imprime un mensaje de error si ocurre una excepción
        }
    }

    public void cargarInventario(String archivo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo)); // crea un objeto BufferedWriter que se utiliza para escribir en un archivo especificado por la variable archivo, para leer del archivo especificado(se nota que esta hecho con IA porque las variables estan en un ingles rancio xd)
            String linea;
            while ((linea = reader.readLine()) != null) { // Lee cada línea del archivo
                String[] datos = linea.split(","); // Divide la línea en un array de datos separados por comas
                String producto = datos[0]; // Obtiene el nombre del producto
                double precioCompra = Double.parseDouble(datos[1]); // Convierte el dato del precio de compra a tipo double
                int cantidad = Integer.parseInt(datos[2]); // Convierte el dato de la cantidad a tipo int
                double precioVenta = Double.parseDouble(datos[3]); // Convierte el dato del precio de venta a tipo double
                agregarProducto(producto, precioCompra, cantidad, precioVenta); // Agrega el producto al inventario utilizando los datos leídos
            }
            reader.close(); // Cierra el BufferedReader
            System.out.println("Inventario cargado con éxito."); // Muestra un mensaje de éxito
        } catch (IOException e) {
            System.out.println("Error al cargar el inventario: " + e.getMessage()); // Muestra un mensaje de error si ocurre una excepción
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Se crea una instancia Scanner para leer la entrada del usuario
        String archivo = "inventario.txt"; // Define el nombre del archivo donde se guardará el inventario

        System.out.print("Ingrese la capacidad máxima del inventario: "); // Solicita al usuario que ingrese la capacidad máxima del inventario
        int capacidadMaxima = scanner.nextInt(); // Leer la capacidad máxima del inventario
        scanner.nextLine(); // se utiliza para asegurarse de que no se capture accidentalmente un carácter de salto de línea después de leer un número con nextInt(), para que puedas leer correctamente el texto después.

        GestorInventario gestor = new GestorInventario(capacidadMaxima); // crea un nuevo objeto GestorInventario y lo asigna a la variable gestor. El objeto gestor se inicializa utilizando el constructor de la clase GestorInventario con un valor de capacidad máxima especificado por la variable capacidadMaxima

        // Cargar inventario si existe un archivo guardado
        File archivoInventario = new File(archivo); // Crea un objeto File con el nombre del archivo
        if (archivoInventario.exists()) { // Verifica si el archivo existe
            gestor.cargarInventario(archivo); // Carga los datos del inventario desde el archivo
        }

        while (true) {
            System.out.println("\n=== GESTOR DE INVENTARIO ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Guardar inventario");
            System.out.println("4. Cargar inventario");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt(); // Lee la opción seleccionada por el usuario
            scanner.nextLine(); // // se utiliza para asegurarse de que no se capture accidentalmente un carácter de salto de línea después de leer un número con nextInt(), para que puedas leer correctamente el texto después.

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del producto: ");
                    String nombreProducto = scanner.nextLine(); // Lee el nombre del producto
                    System.out.print("Ingrese el precio de compra: ");
                    double precioCompra = scanner.nextDouble(); // Lee el precio de compra
                    System.out.print("Ingrese la cantidad en inventario: ");
                    int cantidad = scanner.nextInt(); // Lee la cantidad en inventario
                    System.out.print("Ingrese el precio de venta: ");
                    double precioVenta = scanner.nextDouble(); // Lee el precio de venta
                    gestor.agregarProducto(nombreProducto, precioCompra, cantidad, precioVenta); // Llama al método agregarProducto del GestorInventario
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del producto a buscar: ");
                    String productoBuscado = scanner.nextLine(); // Lee el nombre del producto a buscar
                    gestor.buscarProducto(productoBuscado); // Llama al método buscarProducto del GestorInventario
                    break;
                case 3:
                    gestor.guardarInventario(archivo); // Llama al método guardarInventario del GestorInventario
                    break;
                case 4:
                    gestor.cargarInventario(archivo); // Llama al método cargarInventario del GestorInventario
                    break;
                case 5:
                    System.out.println("¡Hasta luego!");
                    System.exit(0); // Sale del programa
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}
