package presentacion;

import javax.swing.*;
import java.awt.*;

import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIResumenDialog extends JDialog {

    public GUIResumenDialog(JFrame owner) {
        super(owner, "Resumen del negocio", false);
        setResizable(false);
        setSize(400, 150);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initGUI();
    }

    private void initGUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones
        JButton btnConsultar = new JButton("Ver resumen mensual");

        Dimension size = new Dimension(250, 40);
        btnConsultar.setPreferredSize(size);
        btnConsultar.setMaximumSize(size);
        btnConsultar.setMinimumSize(size);
        btnConsultar.setFocusPainted(false);

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fila.add(btnConsultar);

        mainPanel.add(fila);

        add(mainPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);

        // LISTENER
        btnConsultar.addActionListener(e -> {

            IGUI vista = FactoriaAbstractaPresentacion.getInstance()
                    .createVista(Eventos.MOSTRAR_RESUMEN_MENSUAL);

            abrirVistaBloqueante((JDialog) vista);
        });
    }

    private void abrirVistaBloqueante(JDialog vista) {
        vista.setModal(true);
        vista.setVisible(true);
    }
}
