package presentacion;

import javax.swing.*;
import java.awt.*;

import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIClienteDialog extends JDialog {

    // CONSTRUCTORA

    public GUIClienteDialog(JFrame owner) {
        super(owner, "Gestión de Clientes", false);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initGUI();
        setLocationRelativeTo(owner);
    }

    // MÉTODO INITGUI

    private void initGUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones
        JButton btnAlta = new JButton("Alta Cliente");
        JButton btnBaja = new JButton("Baja Cliente");
        JButton btnModificar = new JButton("Modificar Cliente");
        JButton btnBuscar = new JButton("Buscar Cliente");
        JButton btnListar = new JButton("Listar Clientes");

        JButton[] botones = {
                btnAlta, btnBaja, btnModificar,
                btnBuscar, btnListar
        };

        Dimension size = new Dimension(220, 40);

        for (JButton b : botones) {
            b.setFocusPainted(false);
            b.setPreferredSize(size);
            b.setMaximumSize(size);
            b.setMinimumSize(size);
        }

        // FILA 1 (3 botones)
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fila1.add(btnAlta);
        fila1.add(btnBaja);
        fila1.add(btnModificar);

        // FILA 2 (2 botones)
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fila2.add(btnBuscar);
        fila2.add(btnListar);

        mainPanel.add(fila1);
        mainPanel.add(fila2);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);

        // LISTENERS

        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.ALTA_CLIENTE);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.BAJA_CLIENTE);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnModificar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.MODIFICAR_CLIENTE);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.BUSCAR_CLIENTE);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnListar.addActionListener(e -> {
            Controlador.getInstance().accion(Eventos.MOSTRAR_CLIENTES, null);
        });
    }

    private void abrirVistaBloqueante(JDialog vista) {
        vista.setModal(true);
        vista.setVisible(true);
    }
}