/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * Clase que representa una galleta.
 *
 */
public class Galleta {
    private int idGalleta;
    private String nombre;
    private String sabor;
    private String marca;
    private double precioPaquete;
    private int cantidadPaquete;
    private int cantidadPaquetesStock;

    // Constructor vacío
    public Galleta() {

    }

    // Constructor para agregar una nueva galleta
    public Galleta(String nombre, String sabor, String marca, double precioPaquete, int cantidadPaquete, int cantidadPaquetesStock) {
        this.nombre = nombre;
        this.sabor = sabor;
        this.marca = marca;
        this.precioPaquete = precioPaquete;
        this.cantidadPaquete = cantidadPaquete;
        this.cantidadPaquetesStock = cantidadPaquetesStock;
    }

    // Constructor para actualizar una galleta existente
    public Galleta(int idGalleta, String nombre, String sabor, String marca, double precioPaquete, int cantidadPaquete, int cantidadPaquetesStock) {
        this.idGalleta = idGalleta;
        this.nombre = nombre;
        this.sabor = sabor;
        this.marca = marca;
        this.precioPaquete = precioPaquete;
        this.cantidadPaquete = cantidadPaquete;
        this.cantidadPaquetesStock = cantidadPaquetesStock;
    }

    // Getters y setters
    public int getIdGalleta() {
        return idGalleta;
    }

    public void setIdGalleta(int idGalleta) {
        this.idGalleta = idGalleta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
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

    public int getCantidadPaquete() {
        return cantidadPaquete;
    }

    public void setCantidadPaquete(int cantidadPaquete) {
        this.cantidadPaquete = cantidadPaquete;
    }

    public int getCantidadPaquetesStock() {
        return cantidadPaquetesStock;
    }

    public void setCantidadPaquetesStock(int cantidadPaquetesStock) {
        this.cantidadPaquetesStock = cantidadPaquetesStock;
    }
}
