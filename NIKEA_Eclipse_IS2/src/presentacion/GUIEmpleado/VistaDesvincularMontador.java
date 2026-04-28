package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaDesvincularMontador extends JFrame implements IGUI {

	// ATRIBUTOS
    private JTextField txtIdMontador, txtIdMontaje;
    private JButton btnDesvincular, btnCancelar;

    // CONSTRUCTORA
    public VistaDesvincularMontador() {
        setTitle("Desvincular Montador de Montaje");
        initGUI();
        // Al ser Singleton, solo escondemos la ventana
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                limpiar();
            }
        });
    }

    private void initGUI() {
    	
    	// Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de Formulario
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        txtIdMontador = new JTextField();
        txtIdMontaje = new JTextField();

        panelCampos.add(new JLabel("ID Montador:"));
        panelCampos.add(txtIdMontador);
        panelCampos.add(new JLabel("ID Montaje:"));
        panelCampos.add(txtIdMontaje);

        // Panel de Botones
        JPanel panelBotones = new JPanel();
        btnDesvincular = new JButton("ELIMINAR VINCULACIÓN");
        btnDesvincular.setBackground(new Color(255, 200, 200)); 
        btnCancelar = new JButton("CANCELAR");
        
        panelBotones.add(btnDesvincular);
        panelBotones.add(btnCancelar);

        // Lógica de Desvincular
        btnDesvincular.addActionListener(e -> {
            try {
                String idEmplStr = txtIdMontador.getText().trim();
                String idMontStr = txtIdMontaje.getText().trim();

                if (idEmplStr.isEmpty() || idMontStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ambos campos son obligatorios.");
                } else {
                    int idMontador = Integer.parseInt(idEmplStr);
                    int idMontaje = Integer.parseInt(idMontStr);
                    
                    // Empaquetamos los IDs para el controlador
                    int[] datos = {idMontador, idMontaje};
                    Controlador.getInstance().accion(Eventos.DESVINCULAR_MONTADOR_MONTAJE, datos);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Los IDs deben ser números enteros.");
            }
        });

        // Lógica de Cancelar
        btnCancelar.addActionListener(e -> {
            limpiar();
            setVisible(false);
        });

        // Montaje de la vista
        JLabel lblInfo = new JLabel("Introduzca los IDs para eliminar la relación existente:");
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(lblInfo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(panelCampos);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(panelBotones);

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void limpiar() {
        txtIdMontador.setText("");
        txtIdMontaje.setText("");
    }

    @Override
    public void actualizar(int evento, Object datos) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_DESVINCULAR_MONTADOR_OK:
                    JOptionPane.showMessageDialog(this, "Relación eliminada correctamente.");
                    limpiar();
                    break;
                    
                case Eventos.RES_DESVINCULAR_MONTADOR_KO_RELACION_NO_EXISTE:
                    JOptionPane.showMessageDialog(this, 
                        "Error: No existe una vinculación previa entre el Montador y el Montaje indicados.", 
                        "Error de Desvinculación", 
                        JOptionPane.ERROR_MESSAGE);
                    break;
                    
                case Eventos.RES_DESVINCULAR_MONTADOR_KO_ID_NO_ENCONTRADO:
                    JOptionPane.showMessageDialog(this, "Uno de los IDs introducidos no existe en el sistema.");
                    break;
            }
        });
    }
}
