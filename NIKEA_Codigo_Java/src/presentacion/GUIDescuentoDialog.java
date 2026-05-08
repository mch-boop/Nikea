package presentacion;

import javax.swing.*;
import java.awt.*;

import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIDescuentoDialog extends JDialog {

    // CONSTRUCTORA

    public GUIDescuentoDialog(JFrame owner) {
        super(owner, "Gestión de Descuento", false);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initGUI();

        pack();
        setLocationRelativeTo(null);
    }

    // MÉTODOS

    private void initGUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnAlta = new JButton("Alta descuento");
        JButton btnBaja = new JButton("Baja descuento");
        JButton btnModificar = new JButton("Modificar descuento");
        JButton btnListar = new JButton("Listar descuentos");
        JButton btnBuscar = new JButton("Buscar descuento");
        JButton btnAnadir = new JButton("Añadir descuento a factura");

        JButton[] botones = {
                btnAlta, btnBaja, btnModificar,
                btnBuscar, btnListar, btnAnadir
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

        // FILA 2 (3 botones)
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fila2.add(btnBuscar);
        fila2.add(btnListar);
        fila2.add(btnAnadir);

        mainPanel.add(fila1);
        mainPanel.add(fila2);

        add(mainPanel, BorderLayout.CENTER);

        // LISTENERS

        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.ALTA_DESCUENTO);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.BAJA_DESCUENTO);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnModificar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.MODIFICAR_DESCUENTO);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.BUSCAR_DESCUENTO);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnListar.addActionListener(e -> {
            Controlador.getInstance().accion(Eventos.MOSTRAR_DESCUENTOS, null);
        });

        btnAnadir.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.ANNADIR_DESCUENTO_FACTURA);
            abrirVistaBloqueante((JDialog) vista);
        });
    }

    private void abrirVistaBloqueante(JDialog vista) {
        vista.setModal(true);
        vista.setVisible(true);
    }
}