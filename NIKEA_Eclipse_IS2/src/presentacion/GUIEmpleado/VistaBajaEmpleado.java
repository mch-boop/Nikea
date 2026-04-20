package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

public class VistaBajaEmpleado extends JFrame implements IGUI {

	// ATRIBUTOS
	
    private JTextField txtId;
    private JButton btnBaja, btnCancelar;

    // CONSTRUCTORA
    
    public VistaBajaEmpleado() {
        setTitle("Baja Empleado");
        initGUI();
    }

    
    // MÉTODOS
    
    private void initGUI() {
    	
    	// Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Inicialización del campo
        txtId = new JTextField(10);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnBaja = new JButton("DAR DE BAJA");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnBaja);
        panelBotones.add(btnCancelar);

        // Lógica de Baja
        btnBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String textoId = txtId.getText();
                    if (textoId.isEmpty()) {
                        Controlador.getInstance().accion(Eventos.RES_BAJA_EMPLEADO_KO_ID_VACIO, null);
                    } else {
                        int id = Integer.parseInt(textoId);
                        // Enviamos el ID al controlador
                        Controlador.getInstance().accion(Eventos.BAJA_EMPLEADO, id);
                    }
                } catch (NumberFormatException ex) {
                    // Si el ID no es numérico, enviamos evento de error de formato
                    Controlador.getInstance().accion(Eventos.RES_BAJA_EMPLEADO_KO_ID_FORMATO, null);
                }
            }
        });

        // Lógica de Cancelar
        btnCancelar.addActionListener(al -> {
        	// Limpiar campos
            txtId.setText("");
         // Cerrar la ventana
            setVisible(false);
            dispose();
        });

        // Añadir componentes al panel principal
        mainPanel.add(new JLabel("Introduzca el ID del empleado a dar de baja:"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(txtId);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(panelBotones);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
    	// El controlador llama a este método tras la ejecución en el SA
        switch (evento) {
            
            case Eventos.RES_BAJA_EMPLEADO_OK:
                JOptionPane.showMessageDialog(this, "El empleado con ID " + datos + " ha sido dado de baja (borrado lógico).", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtId.setText("");
                break;

            case Eventos.RES_BAJA_EMPLEADO_KO_NO_EXISTE:
                JOptionPane.showMessageDialog(this, "Error: No existe ningún empleado con el ID: " + datos, "Error", JOptionPane.ERROR_MESSAGE);
                txtId.requestFocus();
                break;

            case Eventos.RES_BAJA_EMPLEADO_KO_YA_INACTIVO:
                JOptionPane.showMessageDialog(this, "El empleado ya se encuentra en estado inactivo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                break;

            case Eventos.RES_BAJA_EMPLEADO_KO_ID_FORMATO:
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                txtId.requestFocus();
                break;

            case Eventos.RES_BAJA_EMPLEADO_KO_ID_VACIO:
                JOptionPane.showMessageDialog(this, "El campo ID no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
                txtId.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Error desconocido al procesar la baja.");
                break;
        }
    }
}
