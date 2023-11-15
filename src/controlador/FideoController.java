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
import modelo.Fideo;
import modelo.FideoDAO;
import vista.FideoFrame;

public class FideoController implements ActionListener {

    private Fideo fideo;
    private FideoDAO fideoDAO;
    private FideoFrame vista;
    private DefaultTableModel modeloTabla;

    private int idFideo = 0;
    private String tipo;
    private String marca;
    private double precioPaquete;
    private double pesoPaquete;
    private int cantidadPaquetesStock;

    public FideoController(FideoFrame vista) {
        this.vista = vista;
        this.vista.setVisible(true);
        this.fideo = new Fideo();
        this.fideoDAO = new FideoDAO();
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
        String[] titulos = new String[]{"Id_Fideo", "Tipo", "Marca", "PrecioPaquete", "PesoPaquete", "CantidadPaquetesStock"};
        modeloTabla = new DefaultTableModel(titulos, 0);
        List<Fideo> listaFideos = fideoDAO.listar();
        for (Fideo fideo : listaFideos) {
            modeloTabla.addRow(new Object[]{fideo.getIdFideo(), fideo.getTipo(), fideo.getMarca(), fideo.getPrecioPaquete(), fideo.getPesoPaquete(), fideo.getCantidadPaquetesStock()});
        }
        vista.getTblTabla().setModel(modeloTabla);
        vista.getTblTabla().setPreferredSize(new Dimension(350, modeloTabla.getRowCount() * 16));
    }

    private void llenarCampos(MouseEvent e) {
        JTable target = (JTable) e.getSource();
        idFideo = (int) vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 0);
        tipo = vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 1).toString();
        marca = vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 2).toString();
        precioPaquete = Double.parseDouble(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 3).toString());
        pesoPaquete = Double.parseDouble(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 4).toString());
        cantidadPaquetesStock = Integer.parseInt(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 5).toString());

        vista.getTxtTipo().setText(tipo);
        vista.getTxtMarca().setText(marca);
        vista.getTxtPrecioPaquete().setText(String.valueOf(precioPaquete));
        vista.getTxtPesoPaquete().setText(String.valueOf(pesoPaquete));
        vista.getTxtCantidadPaquetesStock().setText(String.valueOf(cantidadPaquetesStock));
    }

    private boolean validarDatos() {
        if ("".equals(tipo) ||
                "".equals(marca) ||
                precioPaquete <= 0 ||
                pesoPaquete <= 0 ||
                cantidadPaquetesStock < 0) {
            JOptionPane.showMessageDialog(null, "Los campos no pueden ser vacíos y los valores numéricos deben ser válidos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean cargarDatos() {
        try {
            tipo = vista.getTxtTipo().getText();
            marca = vista.getTxtMarca().getText();
            precioPaquete = Double.parseDouble(vista.getTxtPrecioPaquete().getText());
            pesoPaquete = Double.parseDouble(vista.getTxtPesoPaquete().getText());
            cantidadPaquetesStock = Integer.parseInt(vista.getTxtCantidadPaquetesStock().getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Los campos precio y peso deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void limpiarCampos() {
        vista.getTxtTipo().setText("");
        vista.getTxtMarca().setText("");
        vista.getTxtPrecioPaquete().setText("");
        vista.getTxtPesoPaquete().setText("");
        vista.getTxtCantidadPaquetesStock().setText("");
        idFideo = 0;
        tipo = "";
        marca = "";
        precioPaquete = 0.0;
        pesoPaquete = 0.0;
        cantidadPaquetesStock = 0;
    }

    private void agregarFideo() {
        try {
            if (validarDatos()) {
                if (cargarDatos()) {
                    Fideo fideoNuevo = new Fideo(tipo, marca, precioPaquete, pesoPaquete, cantidadPaquetesStock);
                    fideoDAO.agregar(fideoNuevo);
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

    private void actualizarFideo() {
        try {
            if (validarDatos()) {
                if (cargarDatos()) {
                    Fideo fideoActualizado = new Fideo(idFideo, tipo, marca, precioPaquete, pesoPaquete, cantidadPaquetesStock);
                    fideoDAO.actualizar(fideoActualizado);
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

    private void borrarFideo() {
        try {
            if (idFideo != 0) {
                fideoDAO.borrar(idFideo);
                JOptionPane.showMessageDialog(null, "Registro borrado");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un fideo de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
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
            agregarFideo();
        }
        if (ae.getSource() == vista.getBtnActualizar()) {
            actualizarFideo();
        }
        if (ae.getSource() == vista.getBtnBorrar()) {
            borrarFideo();
        }
        if (ae.getSource() == vista.getBtnLimpiar()) {
            limpiarCampos();
        }
    }
}
