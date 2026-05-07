package presentacion;

import javax.swing.*;
import java.awt.*;

import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIFacturaDialog extends JDialog {

    // CONSTRUCTORA

    public GUIFacturaDialog(JFrame owner) {
        super(owner, "Gestión de Facturas", false);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initGUI();
        setLocationRelativeTo(owner);
    }

    // MÉTODOS

    private void initGUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones
        JButton btnIniciar = new JButton("Iniciar venta");
        JButton btnAnnadir = new JButton("Añadir servicio");
        JButton btnEliminar = new JButton("Eliminar servicio");
        JButton btnCerrar = new JButton("Cerrar venta");

        JButton btnBuscar = new JButton("Buscar factura");
        JButton btnListar = new JButton("Listar facturas");
        JButton btnListarCliente = new JButton("Facturas de cliente");

        JButton[] botones = {
                btnIniciar, btnAnnadir, btnEliminar, btnCerrar,
                btnBuscar, btnListar, btnListarCliente
        };

        Dimension size = new Dimension(220, 40);

        for (JButton b : botones) {
            b.setFocusPainted(false);
            b.setPreferredSize(size);
            b.setMaximumSize(size);
            b.setMinimumSize(size);
        }

        // FILA 1 (4 botones)
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fila1.add(btnIniciar);
        fila1.add(btnAnnadir);
        fila1.add(btnEliminar);
        fila1.add(btnCerrar);

        // FILA 2 (4 botones)
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fila2.add(btnBuscar);
        fila2.add(btnListar);
        fila2.add(btnListarCliente);

        mainPanel.add(fila1);
        mainPanel.add(fila2);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);

        // LISTENERS

        btnIniciar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.INICIAR_VENTA);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnAnnadir.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.ANNADIR_SERVICIO);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnEliminar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.ELIMINAR_SERVICIO);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnCerrar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.CERRAR_VENTA);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.BUSCAR_FACTURA);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnListar.addActionListener(e -> {
            Controlador.getInstance().accion(Eventos.MOSTRAR_FACTURAS, null);
        });

        btnListarCliente.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.MOSTRAR_FACTURAS_CLIENTE);
            abrirVistaBloqueante((JDialog) vista);
        });
    }

    private void abrirVistaBloqueante(JDialog ventana) {

        ventana.setModal(true);
        ventana.setVisible(true);

        ventana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                GUIFacturaDialog.this.toFront();
            }
        });
    }
}