package presentacion.GUIServicio;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import negocio.servicio.TServicio;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaEliminarServicio extends JFrame implements IGUI {

    private JTextField txtId;
    private JButton btnBaja;
    private JButton btnCancelar;

    public VistaEliminarServicio() {
        setTitle("Baja Servicio");
        initGUI();
    }

    private void initGUI() {
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtId = new JTextField(10);
        panelInput.add(new JLabel("ID Servicio:"));
        panelInput.add(txtId);

        JPanel panelBotones = new JPanel();
        btnBaja = new JButton("DAR DE BAJA");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnBaja);
        panelBotones.add(btnCancelar);

        btnBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String textoId = txtId.getText();
                    if (textoId.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se ha completado el campo ID Servicio.", "Error", JOptionPane.ERROR_MESSAGE);
                        txtId.requestFocus();
                    } else {
                        int id = Integer.parseInt(textoId);
                        Controlador.getInstance().accion(Eventos.BAJA_SERVICIO, id);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtId.requestFocus();
                }
            }
        });

        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        JLabel lblTitulo = new JLabel("Introduzca el ID del Servicio a dar de baja:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        viewPanel.add(lblTitulo);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelInput);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelBotones);

        getContentPane().add(viewPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_BAJA_SERVICIO_OK:
                    TServicio servicio = (TServicio) datos;
                    String info = "ID: " + servicio.getId() + "\nNombre: " + servicio.getNombre() + "\nDescripción: " + servicio.getDescripcion();

                    int respuesta = JOptionPane.showConfirmDialog(this,
                            "Se ha encontrado el siguiente servicio activo:\n\n" + info +
                            "\n\n¿Está seguro de que desea darlo de baja?",
                            "Confirmar Baja",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

                    if (respuesta == JOptionPane.YES_OPTION) {
                        Controlador.getInstance().accion(Eventos.CONFIRMAR_BAJA_SERVICIO, servicio.getId());
                    }
                    break;

                case Eventos.RES_BAJA_SERVICIO_CONFIRMADA:
                    JOptionPane.showMessageDialog(this, "El servicio con ID " + datos + " se ha dado de baja correctamente.");
                    txtId.setText("");
                    break;

                case Eventos.RES_BAJA_SERVICIO_KO_NO_EXISTE:
                    JOptionPane.showMessageDialog(this, "Error: No existe ningún servicio con el ID: " + datos, "Error", JOptionPane.ERROR_MESSAGE);
                    txtId.requestFocus();
                    break;

                case Eventos.RES_BAJA_SERVICIO_KO:
                    JOptionPane.showMessageDialog(this, "El servicio ya se encuentra en estado inactivo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    break;

                default:
                    break;
            }
        });
    }
}
