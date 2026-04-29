package presentacion.GUIDescuento;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import negocio.descuento.TDescuento;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaAltaDescuento extends JFrame implements IGUI {

    // ATRIBUTOS
    private JTextField txtCodigo, txtDescuento;
    private JTextArea areaDescripcion;
    private JSpinner importeMin, productosMin;
    private JRadioButton rbImporte, rbProductos;
    private JButton btnAceptar, btnCancelar; 

    // CONSTRUCTORA
    public VistaAltaDescuento() {
        setTitle("Alta Descuento");
        initGUI();    
    }

    // MÉTODOS
    private void limpiarCampos() {
        txtCodigo.setText("");
        areaDescripcion.setText("");
        txtDescuento.setText("");

        if (importeMin != null) importeMin.setValue(100.0);
        if (productosMin != null) productosMin.setValue(10);
        if (rbImporte != null) rbImporte.setSelected(true);
        
        txtCodigo.requestFocus();
    }

    private void initGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //CODIGO
        txtCodigo = new JTextField(20);

        //DESCRIPCION
        areaDescripcion = new JTextArea(3, 20);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(areaDescripcion);

        //DESCUENTO
        txtDescuento = new JTextField(20);

        //DINAMICO (Cantidad/Importe)
        SpinnerNumberModel importeModel = new SpinnerNumberModel(100.0, 0.0, null, 10.0);
        importeMin = new JSpinner(importeModel);
        ((JSpinner.NumberEditor)importeMin.getEditor()).getTextField().setColumns(10);
        
        SpinnerNumberModel productosModel = new SpinnerNumberModel(10, 0, null, 1);
        productosMin = new JSpinner(productosModel);
        ((JSpinner.NumberEditor)productosMin.getEditor()).getTextField().setColumns(10);

        JPanel panelDinamico = new JPanel(new CardLayout());
        
        JPanel cardImporte = new JPanel();
        cardImporte.add(new JLabel("Importe mínimo (€):"));
        cardImporte.add(importeMin);

        JPanel cardProductos = new JPanel();
        cardProductos.add(new JLabel("Productos mínimos (uds):"));
        cardProductos.add(productosMin);

        panelDinamico.add(cardImporte, "IMPORTE");
        panelDinamico.add(cardProductos, "PRODUCTOS");

        //TIPO
        rbImporte = new JRadioButton("Por importe", true);
        rbProductos = new JRadioButton("Por cantidad");
        ButtonGroup group = new ButtonGroup();
        group.add(rbImporte);
        group.add(rbProductos);

        CardLayout cl = (CardLayout) panelDinamico.getLayout();
        rbImporte.addActionListener(e -> cl.show(panelDinamico, "IMPORTE"));
        rbProductos.addActionListener(e -> cl.show(panelDinamico, "PRODUCTOS"));

        // --- ORDEN ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Codigo
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtCodigo, gbc);

        // escripción
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        formPanel.add(scrollDesc, gbc);

        // Descuento
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Descuento (%):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDescuento, gbc);

        // Tipo
        JPanel panelRadioCheck = new JPanel();
        panelRadioCheck.add(rbImporte);
        panelRadioCheck.add(rbProductos);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        formPanel.add(panelRadioCheck, gbc);

        // Cambia
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Condición:"), gbc);
        gbc.gridx = 1;
        formPanel.add(panelDinamico, gbc);

        // BOTONES
        JPanel panelBotones = new JPanel();
        btnAceptar = new JButton("ACEPTAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        btnCancelar.addActionListener(e -> {
            limpiarCampos();
            dispose();
        });

        btnAceptar.addActionListener(e -> {
            try {
                //Valido
                if (txtCodigo.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El código es obligatorio.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                    txtCodigo.requestFocusInWindow();
                    return;
                }

                if (txtDescuento.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe indicar un porcentaje.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                    txtDescuento.requestFocusInWindow();
                    return;
                }

                //Transfer
                boolean esImporte = rbImporte.isSelected();
                TDescuento td = new TDescuento(esImporte);

                td.setCodigo(txtCodigo.getText().trim());
                td.setNombre(areaDescripcion.getText().trim());
                
                //El porcentaje es un int
                int porc = Integer.parseInt(txtDescuento.getText().trim());
                td.setPorcentaje(porc);

                if (esImporte) {
                    td.setImporteMin((Double) importeMin.getValue());
                } else {
                    td.setProductosMin((Integer) productosMin.getValue());
                }

                //Controlador
                Controlador.getInstance().accion(Eventos.ALTA_DESCUENTO, td);

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "El descuento debe ser un número entero (ej: 15).", "Error de formato", JOptionPane.ERROR_MESSAGE);
                txtDescuento.requestFocusInWindow();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
            }
        });

        // Ensamblar todo
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(panelBotones);

        setContentPane(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_ALTA_DESCUENTO_OK:
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Descuento creado con éxito. ID: " + datos);
                    break;

                case Eventos.RES_ALTA_DESCUENTO_YA_EXISTE:
                    JOptionPane.showMessageDialog(this, "El código de descuento ya está en uso.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    txtCodigo.requestFocus();
                    break;

                case Eventos.RES_ALTA_DESCUENTO_CONFIRMAR_REACTIVACION:
                    TDescuento tdReac = (TDescuento) datos;
                    int resp = JOptionPane.showConfirmDialog(this, 
                        "Existe un descuento inactivo con este código.\n¿Desea reactivarlo con los nuevos datos?", 
                        "Confirmar Reactivación", JOptionPane.YES_NO_OPTION);
                    
                    if (resp == JOptionPane.YES_OPTION) {
                        Controlador.getInstance().accion(Eventos.REACTIVAR_DESCUENTO, tdReac);
                    }
                    break;

                case Eventos.RES_ALTA_DESCUENTO_KO_CODIGO:
                    JOptionPane.showMessageDialog(this, "El código no es válido o está vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtCodigo.requestFocus();
                    break;

                case Eventos.RES_ALTA_DESCUENTO_KO_PORCENTAJE:
                    JOptionPane.showMessageDialog(this, "El porcentaje debe estar entre 1 y 100.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtDescuento.requestFocus();
                    break;

                case Eventos.RES_ALTA_DESCUENTO_KO:
                    JOptionPane.showMessageDialog(this, "Error al reactivar usuario.", "Error Grave", JOptionPane.ERROR_MESSAGE);
                    break;

                default:
                    JOptionPane.showMessageDialog(this, "Error desconocido / descontrolado", "Error Grave", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });
    }
}