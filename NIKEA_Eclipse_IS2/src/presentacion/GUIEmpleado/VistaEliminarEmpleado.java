package presentacion.GUIEmpleado;

import javax.swing.*;

import negocio.empleado.TEmpleado;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaEliminarEmpleado extends JFrame implements IGUI {

	// ATRIBUTOS
	
    private JTextField txtId;
    private JButton btnBaja, btnCancelar;

    // CONSTRUCTORA
    
    public VistaEliminarEmpleado() {
        setTitle("Baja Empleado");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initGUI();
    }

    
    // MÉTODOS
    
    private void initGUI() {
    	
    	// Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ALINEACIÓN
        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.txtId = new JTextField(10);
        panelInput.add(new JLabel("ID Empleado:"));
        panelInput.add(txtId);
        
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
                    	actualizar(Eventos.RES_BAJA_EMPLEADO_KO_ID_VACIO, null);
                    } else {
                        int id = Integer.parseInt(textoId);
                        // Enviamos el ID al controlador
                        Controlador.getInstance().accion(Eventos.BAJA_EMPLEADO, id);
                    }
                } catch (NumberFormatException ex) {
                    // Si el ID no es numérico, enviamos evento de error de formato
                	actualizar(Eventos.RES_BAJA_EMPLEADO_KO_ID_FORMATO, null);
                }
            }
        });

        // Lógica de Cancelar
        btnCancelar.addActionListener(al -> {
        	// Limpiar campos
            txtId.setText("");
            // Cerrar la ventana
            dispose();
        });

        // Añadir componentes al panel principal
        JLabel lblTitulo = new JLabel("Introduzca el ID del empleado a dar de baja:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(panelInput); // Añadimos el panel alineado
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(panelBotones);

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        // Usamos invokeLater para evitar conflictos de hilos en Singleton
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_BAJA_EMPLEADO_OK:
                    TEmpleado te = (TEmpleado) datos; 
                    String info = "ID: " + te.getId() + "\nNombre: " + te.getNombre() + 
                                  " " + te.getApellido() + "\nDNI: " + te.getDNI();
                    
                    int respuesta = JOptionPane.showConfirmDialog(this, 
                        "Se ha encontrado el siguiente empleado activo:\n\n" + info + 
                        "\n\n¿Está seguro de que desea darlo de baja?",
                        "Confirmar Baja", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.WARNING_MESSAGE);

                    if (respuesta == JOptionPane.YES_OPTION) {
                        Controlador.getInstance().accion(Eventos.CONFIRMAR_BAJA_EMPLEADO, te.getId());
                    }
                    break;

                case Eventos.RES_BAJA_EMPLEADO_CONFIRMADA:
                    JOptionPane.showMessageDialog(this, "El empleado con ID " + datos + " se ha dado de baja correctamente.");
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

                // Eliminamos el default con mensaje de error porque en Singleton 
                // esta vista puede recibir eventos que no le pertenecen.
            }
        });
    }
}