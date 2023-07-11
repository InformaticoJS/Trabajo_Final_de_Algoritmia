package Inicio;

import java.io.Serializable;

public class Producto implements Serializable{
    
    private int codigo;
    private String nombre;
    private double precio;
    private Object descripcion;
    private byte[] foto;

    public Producto(){}
    
    public Producto(int codigo, String nombre, double precio, Object descripcion,byte[]foto){
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.foto=foto;
    }

    public int getCodigo() {
        return codigo;
    }


    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }


    public Object getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(Object descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the foto
     */
    public byte[] getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
}
