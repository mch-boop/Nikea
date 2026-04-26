package presentacion.GUIMarca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import negocio.marca.TMarca;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaAnadirMarca extends JFrame implements IGUI {

    // ATRIBUTOS
    private JTextField txtNombre;
    private JButton btnAceptar, btnCancelar;

    // CONSTRUCTORA
    public VistaAnadirMarca() {
        setTitle("Alta Marca");
        initGUI();
    }

    // MÉTODOS

    private void limpiarCampos() {
        txtNombre.setText("");
        txtNombre.requestFocus();
        repaint();
        revalidate();
    }

    private void initGUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtNombre = new JTextField(20);

        // Panel formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtNombre, gbc);

        // Panel botones
        JPanel panelBotones = new JPanel();
        btnAceptar = new JButton("ACEPTAR");
        btnCancelar = new JButton("CANCELAR");

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        // Acción ACEPTAR
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "El nombre es obligatorio.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                    txtNombre.requestFocus();
                    return;
                }

                TMarca tm = new TMarca();
                tm.setNombre(txtNombre.getText());
                tm.setActivo(true);

                Controlador.getInstance().accion(Eventos.ALTA_MARCA, tm);
            }
        });

        // Acción CANCELAR
        btnCancelar.addActionListener(e -> {
            limpiarCampos();
            setVisible(false);
            dispose();
        });

        // Montaje
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(panelBotones);

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {

        SwingUtilities.invokeLater(() -> {

            switch (evento) {

                case Eventos.RES_ALTA_MARCA_OK:
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this,
                            "Marca creada con ID: " + datos);
                    break;

                case Eventos.RES_ALTA_MARCA_YA_EXISTE:
                    JOptionPane.showMessageDialog(this,
                            "Ya existe una marca con ese nombre.",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                    txtNombre.requestFocus();
                    break;

                case Eventos.RES_ALTA_MARCA_REACTIVADA:
                    JOptionPane.showMessageDialog(this,
                            "La marca existía pero estaba inactiva. Se ha reactivado correctamente.");
                    limpiarCampos();
                    break;

                case Eventos.RES_ALTA_MARCA_KO_NOMBRE:
                    JOptionPane.showMessageDialog(this,
                            "El nombre no puede estar vacío.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    txtNombre.requestFocus();
                    break;

                case Eventos.RES_ALTA_MARCA_KO:
                    JOptionPane.showMessageDialog(this,
                            "Error en el sistema.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;

                default:
                    JOptionPane.showMessageDialog(this,
                            "Error no identificado.");
                    break;
            }
        });
    }
}