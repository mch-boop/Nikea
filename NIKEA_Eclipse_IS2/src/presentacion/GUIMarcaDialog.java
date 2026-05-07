package presentacion;

import javax.swing.*;
import java.awt.*;

import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIMarcaDialog extends JDialog {

    // CONSTRUCTORA

    public GUIMarcaDialog(JFrame owner) {
        super(owner, "Gestión de Marca", false);
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

        JButton btnAlta = new JButton("Alta Marca");
        JButton btnBaja = new JButton("Baja Marca");
        JButton btnModificar = new JButton("Modificar Marca");
        JButton btnMostrarId = new JButton("Buscar Marca");
        JButton btnMostrarTodos = new JButton("Listar Marcas");
        JButton btnRanking = new JButton("Marca por especialidad");

        JButton[] botones = {
                btnAlta, btnBaja, btnModificar,
                btnMostrarId, btnMostrarTodos, btnRanking
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
        fila2.add(btnMostrarId);
        fila2.add(btnMostrarTodos);
        fila2.add(btnRanking);

        mainPanel.add(fila1);
        mainPanel.add(fila2);

        add(mainPanel, BorderLayout.CENTER);

        // LISTENERS

        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.ALTA_MARCA);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.BAJA_MARCA);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnMostrarId.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.BUSCAR_MARCA);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnMostrarTodos.addActionListener(e -> {
            Controlador.getInstance().accion(Eventos.MOSTRAR_MARCAS, null);
        });

        btnModificar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.MODIFICAR_MARCA);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnRanking.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.MOSTRAR_MARCAS_POR_ESPECIALIDAD);
            abrirVistaBloqueante((JDialog) vista);
        });
    }

    private void abrirVistaBloqueante(JDialog vista) {
        vista.setModal(true);
        vista.setVisible(true);
    }
}