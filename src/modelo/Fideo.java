package modelo;
//arreglos 2.0
/**
 *
 * Clase que representa un fideo.
 *
 */
public class Fideo {
    private int idFideo;
    private String tipo;
    private String marca;
    private double precioPaquete;
    private double pesoPaquete;
    private int cantidadPaquetesStock;

    // Constructor vacío
    public Fideo() {

    }

    // Constructor para agregar un nuevo fideo
    public Fideo(String tipo, String marca, double precioPaquete, double pesoPaquete, int cantidadPaquetesStock) {
        this.tipo = tipo;
        this.marca = marca;
        this.precioPaquete = precioPaquete;
        this.pesoPaquete = pesoPaquete;
        this.cantidadPaquetesStock = cantidadPaquetesStock;
    }

    // Constructor para actualizar un fideo existente
    public Fideo(int idFideo, String tipo, String marca, double precioPaquete, double pesoPaquete, int cantidadPaquetesStock) {
        this.idFideo = idFideo;
        this.tipo = tipo;
        this.marca = marca;
        this.precioPaquete = precioPaquete;
        this.pesoPaquete = pesoPaquete;
        this.cantidadPaquetesStock = cantidadPaquetesStock;
    }

    // Getters y setters
    public int getIdFideo() {
        return idFideo;
    }

    public void setIdFideo(int idFideo) {
        this.idFideo = idFideo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecioPaquete() {
        return precioPaquete;
    }

    public void setPrecioPaquete(double precioPaquete) {
        this.precioPaquete = precioPaquete;
    }

    public double getPesoPaquete() {
        return pesoPaquete;
    }

    public void setPesoPaquete(double pesoPaquete) {
        this.pesoPaquete = pesoPaquete;
    }

    public int getCantidadPaquetesStock() {
        return cantidadPaquetesStock;
    }

    public void setCantidadPaquetesStock(int cantidadPaquetesStock) {
        this.cantidadPaquetesStock = cantidadPaquetesStock;
    }
}
