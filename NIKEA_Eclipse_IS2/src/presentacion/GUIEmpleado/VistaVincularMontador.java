package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaVincularMontador extends JFrame implements IGUI {

	// ATRIBUTOS 
    private JTextField txtIdMontador, txtIdMontaje;
    private JButton btnVincular, btnCancelar;

    // CONSTRUCTORA
    public VistaVincularMontador() {
        setTitle("Vincular Montador a Montaje");
        initGUI();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    // MÉTODOS
    private void initGUI() {
    	
    	// Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Formulario
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        txtIdMontaje = new JTextField();
        txtIdMontador = new JTextField();
        
        panelCampos.add(new JLabel("ID Montaje:"));
        panelCampos.add(txtIdMontaje);
        panelCampos.add(new JLabel("ID Montador:"));
        panelCampos.add(txtIdMontador);

        // Botones
        JPanel panelBotones = new JPanel();
        btnVincular = new JButton("VINCULAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnVincular);
        panelBotones.add(btnCancelar);

        btnVincular.addActionListener(e -> {
            try {
                int idMontador = Integer.parseInt(txtIdMontador.getText().trim());
                int idMontaje = Integer.parseInt(txtIdMontaje.getText().trim());
                
                // OArray con ambos IDs
                int[] datos = {idMontador, idMontaje};
                Controlador.getInstance().accion(Eventos.VINCULAR_MONTADOR_MONTAJE, datos);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Los IDs deben ser números enteros.");
            }
        });

        btnCancelar.addActionListener(e -> {
            txtIdMontador.setText("");
            txtIdMontaje.setText("");
            setVisible(false);
        });

        mainPanel.add(new JLabel("Introduzca los datos para la vinculación:"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(panelCampos);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
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
                case Eventos.RES_VINCULAR_MONTADOR_OK:
                    JOptionPane.showMessageDialog(this, "Vinculación realizada con éxito.");
                    txtIdMontador.setText(""); 
                    break;
                case Eventos.RES_VINCULAR_MONTADOR_KO_NO_EXISTE_EMPLEADO:
                    JOptionPane.showMessageDialog(this, "Error: El Montador con ID " + datos + " no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });
    }
}
