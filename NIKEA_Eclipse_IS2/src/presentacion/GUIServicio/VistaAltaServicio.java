package presentacion.GUIServicio;

import java.awt.BorderLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import negocio.servicio.TArticulo;
import negocio.servicio.TMontaje;
import negocio.servicio.TServicio;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaAltaServicio extends JFrame implements IGUI {

    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextField txtStock;
    private JTextField txtPrecio;
    private JRadioButton rbArticulo;
    private JRadioButton rbMontaje;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public VistaAltaServicio() {
        setTitle("Alta Servicio");
        initGUI();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtStock.setText("");
        txtPrecio.setText("");
        rbArticulo.setSelected(true);
        txtNombre.requestFocus();
        repaint();
        revalidate();
    }

    private void initGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtNombre = new JTextField(20);
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtStock = new JTextField(20);
        txtPrecio = new JTextField(20);

        rbArticulo = new JRadioButton("Artículo", true);
        rbMontaje = new JRadioButton("Montaje");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rbArticulo);
        grupoTipo.add(rbMontaje);

        JPanel panelTipo = new JPanel();
        panelTipo.add(new JLabel("Tipo:"));
        panelTipo.add(rbArticulo);
        panelTipo.add(rbMontaje);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setPreferredSize(new Dimension(220, 90));
        formPanel.add(scrollDescripcion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtStock, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Precio actual:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtPrecio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Tipo de servicio:"), gbc);
        gbc.gridx = 1;
        formPanel.add(panelTipo, gbc);

        JPanel panelBotones = new JPanel();
        btnAceptar = new JButton("ACEPTAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtNombre.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: El nombre es un campo obligatorio.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        txtNombre.requestFocus();
                        return;
                    }

                    if (txtDescripcion.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: La descripción es un campo obligatorio.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        txtDescripcion.requestFocus();
                        return;
                    }

                    if (txtStock.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: El stock es un campo obligatorio.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        txtStock.requestFocus();
                        return;
                    }

                    if (txtPrecio.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: El precio actual es un campo obligatorio.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        txtPrecio.requestFocus();
                        return;
                    }

                    TServicio servicio;
                    if (rbArticulo.isSelected()) {
                        servicio = new TArticulo();
                        servicio.setTipo(1);
                    } else {
                        servicio = new TMontaje();
                        servicio.setTipo(2);
                    }

                    servicio.setNombre(txtNombre.getText().trim());
                    servicio.setDescripcion(txtDescripcion.getText().trim());
                    servicio.setStock(Integer.valueOf(txtStock.getText().trim()));
                    servicio.setPrecioActual(Integer.valueOf(txtPrecio.getText().trim()));
                    servicio.setActivo(true);

                    Controlador.getInstance().accion(Eventos.ALTA_SERVICIO, servicio);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El stock y el precio deben ser números válidos.");
                }
            }
        });

        btnCancelar.addActionListener(e -> {
            limpiarCampos();
            setVisible(false);
            dispose();
        });

        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(panelTipo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(panelBotones);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_ALTA_SERVICIO_OK:
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Servicio creado con ID: " + datos, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case Eventos.RES_ALTA_SERVICIO_YA_EXISTE:
                    if (datos instanceof TServicio) {
                        TServicio duplicado = (TServicio) datos;
                        JOptionPane.showMessageDialog(this,
                                "Ya existe un servicio con ese nombre: " + duplicado.getNombre(),
                                "Aviso",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "El servicio ya existe.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case Eventos.RES_ALTA_SERVICIO_KO:
                    JOptionPane.showMessageDialog(this, "No se ha podido dar de alta el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    break;
            }
        });
    }
}
