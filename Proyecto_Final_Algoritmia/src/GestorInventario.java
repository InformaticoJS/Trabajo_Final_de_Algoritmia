import java.io.*; // Importa todas las clases necesarias para manejar archivos
import java.util.Scanner; // Importa la clase Scanner para leer la entrada del usuario

public class GestorInventario {
    public static final int capacidad_maxima = 1000; // Se declara una constante llamada "capacidad_maxima" con valor 100.
    public static String[][] inventario = new String[capacidad_maxima][4]; // Se declara un arreglo bidimensional de cadenas llamado "inventario" con 100 filas y 4 columnas. Es decir, puede almacenar hasta 100 productos, y cada producto tiene 4 datos: nombre, precio de compra, cantidad y precio de venta.
    public static int totalProductos = 0; // Se declara una variable entera llamada "totalProductos" y se inicializa con el valor 0. Esta variable llevará la cuenta del número actual de productos almacenados en el inventario.
    
    // Método principal (main) del programa
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in); // Se crea una instancia de la clase Scanner para leer la entrada del usuario
        int opcion=0;
        double opcion2;
        do {
            // Menú de opciones para el usuario
            System.out.println("\n=== GESTOR DE INVENTARIO ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Guardar productos en un txt");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Editar producto");
            System.out.println("6. Mostrar productos");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            boolean Entero=false;
            
            while (!Entero) {
            
            String opcion1 = teclado.nextLine(); // Lee la opción seleccionada por el usuario
            try {
                opcion2 = Double.parseDouble(opcion1);
                if(opcion2>=1&&opcion2<=7&&(opcion2%1==0||opcion2%2==0||opcion2%3==0||opcion2%4==0||opcion2%5==0||opcion2%6==0||opcion2%7==0)){
                Entero = true;
                opcion =(int)opcion2;}
                else{
                System.out.println("Escriba una opcion señalada");    
                }
            } catch (NumberFormatException e) {
                System.out.println("Escriba una opcion valida.");
            }        
            }
            

            switch (opcion) {
                case 1:
                    agregarProducto(); // Llama al método para agregar un producto al inventario
                    break;
                case 2:
                    buscarProducto(); // Llama al método para buscar un producto en el inventario
                    break;
                case 3:
                    guardarProductosEnTxt(); // Llama al método para guardar los productos en un archivo de texto
                    break;
                case 4:
                    eliminarProducto(); // Llama al método para eliminar un producto del inventario
                    break;
                case 5:
                    editarProducto(); // Llama al método para editar los datos de un producto en el inventario
                    break;
                case 6:
                    mostrarProductos(); // Llama al método para mostrar todos los productos en el inventario
                    break;
                case 7:
                    System.out.println("¡Hasta luego!"); // Mensaje de despedida
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente."); // Mensaje de opción inválida
            }
        } while (opcion != 7); // Se repite el menú hasta que el usuario seleccione la opción 7 (Salir).
    }

    // Método para agregar un producto al inventario
    public static void agregarProducto() {
        Scanner scanner = new Scanner(System.in); // Se crea una instancia de la clase Scanner para leer la entrada del usuario
        if (totalProductos < capacidad_maxima) { // Verifica si aún hay espacio en el inventario para agregar más productos          
            boolean numero1 = false,numero2 = false,numero3 = false;
            double n1,n2,n3;
            int j=-1;
            String precioCompra=" ",cantidad=" ",precioVenta=" ", nombreProducto=" ";
           System.out.print("Ingrese el nombre del producto: ");
           
           do{         
           nombreProducto = scanner.nextLine(); // Lee el nombre del producto
           j++;
           if(nombreProducto.equalsIgnoreCase(inventario[j][0])){
              System.out.println("El nombre ya ha sido utilizado, elije otro:"); 
           }else{
               break;
           }
           }while(nombreProducto.equalsIgnoreCase(inventario[j][0]));
           System.out.print("Ingrese el precio de compra: ");
            while (!numero1) {
            precioCompra = scanner.nextLine();
            try {
                n1 = Double.parseDouble(precioCompra);        
                if(n1>0){
                numero1 = true;}
                else{
                System.out.println("Escriba un precio positivo"); 
                }}
            catch (NumberFormatException e) {
                System.out.println("Escriba un precio valido");
            }}
            System.out.print("Ingrese la cantidad en inventario: ");
            while (!numero2) {
            cantidad = scanner.nextLine();
            try {
                n2 = Double.parseDouble(cantidad);
                double n11= Math.floor(n2);
                double n12= Math.ceil(n2) ;         
                if(n2>0&&n11==n12){
                numero2 = true;}
                else{
                System.out.println("Escriba una cantidad entera positiva"); 
                }}
            catch (NumberFormatException e) {
                System.out.println("Escriba una cantidad valida");
            }}
            System.out.print("Ingrese el precio de venta: ");
            while (!numero3) {
            precioVenta = scanner.nextLine();
            try {
                n3 = Double.parseDouble(precioVenta);        
                if(n3>0){
                numero3 = true;}
                else{
                System.out.println("Escriba un precio positivo"); 
                }}
            catch (NumberFormatException e) {
                System.out.println("Escriba un precio valido");
            }}

            inventario[totalProductos][0] = nombreProducto; // Guarda el nombre del producto en el inventario
            inventario[totalProductos][1] = precioCompra; // Guarda el precio de compra en el inventario
            inventario[totalProductos][2] = cantidad; // Guarda la cantidad en inventario
            inventario[totalProductos][3] = precioVenta; // Guarda el precio de venta en el inventario
            totalProductos++; // Incrementa el contador de totalProductos

            System.out.println("Producto agregado con éxito."); // Mensaje de éxito al agregar el producto
        } else {
            System.out.println("El inventario está lleno. No se pueden agregar más productos."); // Mensaje de inventario lleno
        }
    }

    // Método para buscar un producto en el inventario
    public static void buscarProducto() {
        Scanner scanner = new Scanner(System.in); // Se crea una instancia de la clase Scanner para leer la entrada del usuario
        System.out.print("Ingrese el nombre del producto a buscar: ");
        String nombreProducto = scanner.nextLine(); // Lee el nombre del producto a buscar

        boolean encontrado = false;
        for (int i = 0; i < totalProductos; i++) { // Recorre el inventario buscando el producto
            if (inventario[i][0].equalsIgnoreCase(nombreProducto)) { // Compara el nombre del producto ignorando mayúsculas y minúsculas
                System.out.println("Producto encontrado:");
                System.out.println("Nombre: " + inventario[i][0]);
                System.out.println("Precio de compra: " + inventario[i][1]);
                System.out.println("Cantidad en inventario: " + inventario[i][2]);
                System.out.println("Precio de venta: " + inventario[i][3]);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("El producto no se encuentra en el inventario."); // Mensaje de producto no encontrado
        }
    }

    // Método para guardar los productos en un archivo de texto
    public static void guardarProductosEnTxt() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("inventario.txt")); // Crea un BufferedWriter para escribir en el archivo "inventario.txt"
            for (int i = 0; i < totalProductos; i++) { // Recorre el inventario
                writer.write(inventario[i][0] + "," + inventario[i][1] + "," + inventario[i][2] + "," + inventario[i][3]); // Escribe los datos del producto separados por comas en una línea del archivo
                writer.newLine(); // Escribe un salto de línea para el siguiente producto
            }
            writer.close(); // Cierra el BufferedWriter
            System.out.println("Inventario guardado en 'inventario.txt' con éxito."); // Mensaje de éxito al guardar el inventario
        } catch (IOException e) {
            System.out.println("Error al guardar el inventario: " + e.getMessage()); // Mensaje de error si ocurre una excepción al guardar el inventario
        }
    }

    // Método para eliminar un producto del inventario
    public static void eliminarProducto() {
        Scanner scanner = new Scanner(System.in); // Se crea una instancia de la clase Scanner para leer la entrada del usuario
        System.out.print("Ingrese el nombre del producto a eliminar: ");
        String nombreProducto = scanner.nextLine(); // Lee el nombre del producto a eliminar

        int indice = -1; // Variable para guardar el índice del producto a eliminar
        for (int i = 0; i < totalProductos; i++) { // Recorre el inventario buscando el producto a eliminar
            if (inventario[i][0].equalsIgnoreCase(nombreProducto)) { // Compara el nombre del producto ignorando mayúsculas y minúsculas
                indice = i; // Guarda el índice del producto a eliminar
                break;
            }
        }

        if (indice != -1) { // Si se encontró el producto
            for (int i = indice; i < totalProductos - 1; i++) { // Desplaza los productos hacia la izquierda para eliminar el producto
                inventario[i][0] = inventario[i + 1][0];
                inventario[i][1] = inventario[i + 1][1];
                inventario[i][2] = inventario[i + 1][2];
                inventario[i][3] = inventario[i + 1][3];
            }
            totalProductos--; // Decrementa el contador de totalProductos
            System.out.println("Producto eliminado con éxito."); // Mensaje de éxito al eliminar el producto
        } else {
            System.out.println("El producto no se encuentra en el inventario."); // Mensaje de producto no encontrado
        }
    }

    // Método para editar los datos de un producto en el inventario
    public static void editarProducto() {
        Scanner scanner = new Scanner(System.in); // Se crea una instancia de la clase Scanner para leer la entrada del usuario
        System.out.print("Ingrese el nombre del producto a editar: ");
        String nombreProducto = scanner.nextLine(); // Lee el nombre del producto a editar

        int indice = -1; // Variable para guardar el índice del producto a editar
        for (int i = 0; i < totalProductos; i++) { // Recorre el inventario buscando el producto a editar
            if (inventario[i][0].equalsIgnoreCase(nombreProducto)) { // Compara el nombre del producto ignorando mayúsculas y minúsculas
                indice = i; // Guarda el índice del producto a editar
                break;
            }
        }

        if (indice != -1) { // Si se encontró el producto
            boolean numero1 = false,numero2 = false,numero3 = false;
            double n1,n2,n3;
            System.out.println("Ingrese los nuevos datos para el producto:");
            System.out.print("Precio de compra: ");
            while (!numero1) {// Lee el nuevo precio de compra y lo guarda en el inventario
            inventario[indice][1] = scanner.nextLine();
            try {
                n1 = Double.parseDouble(inventario[indice][1]);        
                if(n1>0){
                numero1 = true;}
                else{
                System.out.println("Escriba un precio positivo"); 
                }}
            catch (NumberFormatException e) {
                System.out.println("Escriba un precio valido");
            }}          
            System.out.print("Cantidad en inventario: ");// Lee la nueva cantidad y la guarda en el inventario
             while (!numero2) {
            inventario[indice][2] = scanner.nextLine();
            try {
                n2 = Double.parseDouble(inventario[indice][2]);
                double n11= Math.floor(n2);
                double n12= Math.ceil(n2) ;         
                if(n2>0&&n11==n12){
                numero2 = true;}
                else{
                System.out.println("Escriba una cantidad entera positiva"); 
                }}
            catch (NumberFormatException e) {
                System.out.println("Escriba una cantidad valida");
            }}
            System.out.print("Precio de venta: ");// Lee el nuevo precio de venta y lo guarda en el inventario
            while (!numero3) {
            inventario[indice][3] = scanner.nextLine();
            try {
                n3 = Double.parseDouble(inventario[indice][3]);        
                if(n3>0){
                numero3 = true;}
                else{
                System.out.println("Escriba un precio positivo"); 
                }}
            catch (NumberFormatException e) {
                System.out.println("Escriba un precio valido");
            }}
            System.out.println("Producto editado con éxito."); // Mensaje de éxito al editar el producto
        } else {
            System.out.println("El producto no se encuentra en el inventario."); // Mensaje de producto no encontrado
        }
    }

    // Método para mostrar todos los productos en el inventario
    public static void mostrarProductos() {
        if (totalProductos > 0) { // Verifica si hay productos en el inventario
            System.out.println("=== LISTA DE PRODUCTOS ===");
            for (int i = 0; i < totalProductos; i++) { // Recorre el inventario y muestra los datos de cada producto
                System.out.println("Nombre: " + inventario[i][0]);
                System.out.println("Precio de compra: " + inventario[i][1]);
                System.out.println("Cantidad en inventario: " + inventario[i][2]);
                System.out.println("Precio de venta: " + inventario[i][3]);
                System.out.println("--------------------------");
            }
        } else {
            System.out.println("No hay productos en el inventario."); // Mensaje de inventario vacío
 }
}
}