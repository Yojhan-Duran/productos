package controlador;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Galleta;
import modelo.GalletaDAO;
import vista.GalletaFrame;

/**
 *
 * Controlador para la entidad Galleta.
 *
 */
public class GalletaController implements ActionListener {

    private Galleta galleta;
    private GalletaDAO galletaDAO;
    private GalletaFrame vista;
    private DefaultTableModel modeloTabla;

    private int idGalleta = 0;
    private String nombre;
    private String sabor;
private String marca;
private Double precioPaquete;
private int cantidadPaquete;
private int cantidadPaquetesStock;
   

    public GalletaController(GalletaFrame vista) {
        this.vista = vista;
        this.vista.setVisible(true);
        this.galleta = new Galleta();
        this.galletaDAO = new GalletaDAO();
        this.modeloTabla = new DefaultTableModel();
        this.vista.getBtnAgregar().addActionListener(this);
        this.vista.getBtnActualizar().addActionListener(this);
        this.vista.getBtnBorrar().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);
        this.vista.getTblTabla().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                llenarCampos(e);
            }
        });
        listarTabla();
    }

    private void listarTabla() {
        String[] titulos = new String[]{"ID-Galleta", "Nombre", "Sabor", "Marca", "PrecioPaquete","CantidadPaquete", "CantidadPaquetesStock"};
        modeloTabla = new DefaultTableModel(titulos, 0);
        List<Galleta> listaGalletas = galletaDAO.listar();
        for (Galleta galleta : listaGalletas) {
            modeloTabla.addRow(new Object[]{galleta.getIdGalleta(), galleta.getNombre(), galleta.getSabor(), galleta.getMarca(),galleta.getPrecioPaquete(), galleta.getCantidadPaquete(), galleta.getCantidadPaquetesStock()});
        }
        vista.getTblTabla().setModel(modeloTabla);
        vista.getTblTabla().setPreferredSize(new Dimension(350, modeloTabla.getRowCount() * 16));
    }

    private void llenarCampos(MouseEvent e) {
        JTable target = (JTable) e.getSource();
idGalleta = (int) vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 0);
galleta.setNombre(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 1).toString());
galleta.setSabor(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 2).toString());
galleta.setMarca(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 3).toString());
galleta.setPrecioPaquete(Double.parseDouble(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 4).toString()));
galleta.setCantidadPaquete(Integer.parseInt(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 5).toString()));
galleta.setCantidadPaquetesStock(Integer.parseInt(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 6).toString()));

// Llenar los campos de la interfaz gráfica con los valores de la galleta seleccionada
vista.getTxtNombre().setText(galleta.getNombre());
vista.getTxtSabor().setText(galleta.getSabor());
vista.getTxtMarca().setText(galleta.getMarca());
vista.getTxtPrecioPaquete().setText(String.valueOf(galleta.getPrecioPaquete()));
vista.getTxtCantidadPaquete().setText(String.valueOf(galleta.getCantidadPaquete()));
vista.getTxtCantidadPaquetesStock().setText(String.valueOf(galleta.getCantidadPaquetesStock()));

    }

    private boolean validarDatos() {
        if ("".equals(galleta.getNombre()) || 
        "".equals(galleta.getSabor()) || 
        "".equals(galleta.getMarca()) || 
        galleta.getPrecioPaquete() <= 0 || 
        galleta.getCantidadPaquete() <= 0 || 
        galleta.getCantidadPaquetesStock() < 0) {
        JOptionPane.showMessageDialog(null, "Los campos no pueden ser vacíos y los valores numéricos deben ser válidos", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    return true;
    }


    private boolean cargarDatos() {
         try {
        nombre = vista.getTxtNombre().getText();
        sabor = vista.getTxtSabor().getText();
        marca = vista.getTxtMarca().getText();
        precioPaquete = Double.parseDouble(vista.getTxtPrecioPaquete().getText());
        cantidadPaquete = Integer.parseInt(vista.getTxtCantidadPaquete().getText());
        cantidadPaquetesStock = Integer.parseInt(vista.getTxtCantidadPaquetesStock().getText());
        return true;
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Los campos precio y cantidad deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    }

    private void limpiarCampos() {
        vista.getTxtNombre().setText("");
vista.getTxtSabor().setText("");
vista.getTxtMarca().setText("");
vista.getTxtPrecioPaquete().setText("");
vista.getTxtCantidadPaquete().setText("");
vista.getTxtCantidadPaquetesStock().setText("");
idGalleta = 0;
nombre = "";
sabor = "";
marca = "";
precioPaquete = 0.0;
cantidadPaquete = 0;
cantidadPaquetesStock = 0;

    }

    private void agregarGalleta() {
    try {
        if (validarDatos()) {
            if (cargarDatos()) {
                Galleta galletaNueva = new Galleta( nombre, sabor, marca, precioPaquete, cantidadPaquete, cantidadPaquetesStock);
                galletaDAO.agregar(galletaNueva);
                JOptionPane.showMessageDialog(null, "Registro exitoso");
                limpiarCampos();
            }
        }
    } catch (HeadlessException e) {
        System.out.println("Error al agregar: " + e);
    } finally {
        listarTabla();
    }
}


    private void actualizarGalleta() {
    try {
        if (validarDatos()) {
            if (cargarDatos()) {
                Galleta galletaActualizada = new Galleta(idGalleta, nombre, sabor, marca, precioPaquete, cantidadPaquete, cantidadPaquetesStock);
                galletaDAO.actualizar(galletaActualizada);
                JOptionPane.showMessageDialog(null, "Registro actualizado");
                limpiarCampos();
            }
        }
    } catch (HeadlessException e) {
        System.out.println("Error al actualizar: " + e);
    } finally {
        listarTabla();
    }
}


    private void borrarGalleta() {
        try {
            if (idGalleta != 0) {
                galletaDAO.borrar(idGalleta);
                JOptionPane.showMessageDialog(null, "Registro borrado");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una galleta de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException e) {
            System.out.println("Error al borrar: " + e);
        } finally {
            listarTabla();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == vista.getBtnAgregar()) {
            agregarGalleta();
        }
        if (ae.getSource() == vista.getBtnActualizar()) {
            actualizarGalleta();
        }
        if (ae.getSource() == vista.getBtnBorrar()) {
            borrarGalleta();
        }
        if (ae.getSource() == vista.getBtnLimpiar()) {
            limpiarCampos();
        }
    }
}
