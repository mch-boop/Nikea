package presentacion.operacionResumenTOA;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import negocio.operacionTOA.TResumenNegocio;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaOperacionResumen extends JDialog implements IGUI {

    private JTextField txtMes, txtAnio;
    private JButton btnAceptar, btnCancelar;

    private JLabel lblClientes, lblServicios, lblFacturas, lblMarcas;

    public VistaOperacionResumen() {
        super(null, "Resumen del negocio", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
    }

    private void initGUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // CAMPOS INPUT
        txtMes = new JTextField(10);
        txtAnio = new JTextField(10);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        formPanel.add(new JLabel("Mes (1-12):"), c);
        c.gridx = 1;
        formPanel.add(txtMes, c);

        c.gridx = 0; c.gridy = 1;
        formPanel.add(new JLabel("Año:"), c);
        c.gridx = 1;
        formPanel.add(txtAnio, c);

        // RESULTADOS
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(4,1,5,5));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Resumen"));

        lblClientes = new JLabel("Clientes: -");
        lblServicios = new JLabel("Servicios: -");
        lblFacturas = new JLabel("Facturas: -");
        lblMarcas = new JLabel("Marcas: -");

        resultPanel.add(lblClientes);
        resultPanel.add(lblServicios);
        resultPanel.add(lblFacturas);
        resultPanel.add(lblMarcas);

        // BOTONES
        JPanel botones = new JPanel();
        btnAceptar = new JButton("CONSULTAR");
        btnCancelar = new JButton("CERRAR");

        botones.add(btnAceptar);
        botones.add(btnCancelar);

        // LISTENER ACEPTAR
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (txtMes.getText().trim().isEmpty()) {
                        mostrarError("El mes es obligatorio", txtMes);
                        return;
                    }

                    if (txtAnio.getText().trim().isEmpty()) {
                        mostrarError("El año es obligatorio", txtAnio);
                        return;
                    }

                    int mes = Integer.parseInt(txtMes.getText().trim());
                    int anio = Integer.parseInt(txtAnio.getText().trim());

                    if (mes < 1 || mes > 12) {
                        mostrarError("El mes debe estar entre 1 y 12", txtMes);
                        return;
                    }

                    if (anio < 2000) {
                        mostrarError("El año no es válido", txtAnio);
                        return;
                    }

                    int[] datos = {mes, anio};

                    Controlador.getInstance().accion(Eventos.MOSTRAR_RESUMEN_MENSUAL, datos);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VistaOperacionResumen.this,
                            "Mes y año deben ser numéricos",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // LISTENER CANCELAR
        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        // MONTAJE
        mainPanel.add(new JLabel("Introduce mes y año:"));
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));
        mainPanel.add(resultPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));
        mainPanel.add(botones);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void mostrarError(String msg, JTextField campo) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.WARNING_MESSAGE);
        campo.requestFocus();
    }

    // MÉTODO DEL CONTROLADOR
    @Override
    public void actualizar(int evento, Object datos) {

        SwingUtilities.invokeLater(() -> {

            switch (evento) {

            case Eventos.RES_RESUMEN_MENSUAL_OK:

                TResumenNegocio resumen = (TResumenNegocio) datos;

                lblClientes.setText("Clientes: " + resumen.getTotalClientes());
                lblServicios.setText("Servicios: " + resumen.getTotalServicios());
                lblFacturas.setText("Facturas: " + resumen.getTotalFacturas());
                lblMarcas.setText("Marcas: " + resumen.getTotalMarcas());

                break;

            case Eventos.RES_RESUMEN_MENSUAL_KO:
                JOptionPane.showMessageDialog(this,
                        "Error al obtener el resumen",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;

            default:
                JOptionPane.showMessageDialog(this,
                        "Evento desconocido",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            }
        });
    }
}