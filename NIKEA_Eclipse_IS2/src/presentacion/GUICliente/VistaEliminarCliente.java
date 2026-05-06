package presentacion.GUICliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import negocio.cliente.TCliente;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaEliminarCliente extends JDialog implements IGUI {

	// ATRIBUTOS
	
    private JTextField txtId;
    private JButton btnBaja, btnCancelar;

    
    // CONSTRUCTORA
    
    public VistaEliminarCliente() {
    	super(null, "Baja Cliente", ModalityType.APPLICATION_MODAL);
        setTitle("Baja Cliente");
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
    }

    
    // MÉTODOS
    
    private void initGUI() {
    	
    	// Panel principal.
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de entrada de texto.
        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtId = new JTextField(10);
        panelInput.add(new JLabel("ID Cliente:"));
        panelInput.add(txtId);
        
        // Panel de botones.
        JPanel panelBotones = new JPanel();
        btnBaja = new JButton("DAR DE BAJA");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnBaja);
        panelBotones.add(btnCancelar);

        // Listener de botón Baja.
        btnBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String textoId = txtId.getText();
                    if (textoId.isEmpty()) {
                        JOptionPane.showMessageDialog(VistaEliminarCliente.this, "No se ha completado el campo ID Cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                        txtId.requestFocus();
                    } else {
                    	// Leemos la entrada.
                        int id = Integer.parseInt(textoId);
                        if (id <= 0) {
                        	JOptionPane.showMessageDialog(VistaEliminarCliente.this, "El id debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                            txtId.requestFocus();
                            return;
                        }
                        // Enviamos el ID al controlador, primero lo buscamos para pedir confirmación y luego eliminamos.
                        Controlador.getInstance().accion(Eventos.BAJA_CLIENTE, id);
                    }
                } catch (NumberFormatException ex) {
                    // Si el ID no es numérico, enviamos evento de error de formato
                    JOptionPane.showMessageDialog(VistaEliminarCliente.this, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtId.requestFocus();
                }
            }
        });

        // Listener de botón Cancelar.
        btnCancelar.addActionListener(al -> {
            // Cerrar la ventana.
            setVisible(false);
            dispose();
        });

        // Panel de título.
        JLabel lblTitulo = new JLabel("Introduzca el ID del Cliente a dar de baja:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Añadimos componentes al panel principal.
        viewPanel.add(lblTitulo);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelInput); // Añadimos el panel alineado
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelBotones);

        getContentPane().add(viewPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // Datos es el id del cliente.
    @Override public void actualizar(int evento, Object datos) {
    	// Usamos invokeLater para evitar conflictos de hilos en Singleton
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_BAJA_CLIENTE_OK:
                    TCliente tc = (TCliente) datos; 
                    String info = "ID: " + tc.getId() + "\nNombre: " + tc.getNombre() + 
                                  " " + tc.getApellidos() + "\nDNI: " + tc.getDNI();
                    
                    int respuesta = JOptionPane.showConfirmDialog(VistaEliminarCliente.this, 
                        "Se ha encontrado el siguiente cliente activo:\n\n" + info + 
                        "\n\n¿Está seguro de que desea darlo de baja?",
                        "Confirmar Baja", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.WARNING_MESSAGE);

                    if (respuesta == JOptionPane.YES_OPTION) {
                        Controlador.getInstance().accion(Eventos.CONFIRMAR_BAJA_CLIENTE, tc.getId());
                    }
                    txtId.requestFocus();
                    break;

                case Eventos.RES_BAJA_CLIENTE_CONFIRMADA:
                    JOptionPane.showMessageDialog(VistaEliminarCliente.this, "El cliente con ID " + datos + " se ha dado de baja correctamente.");
                    txtId.setText("");
                    break;

                case Eventos.RES_BAJA_CLIENTE_KO_NO_EXISTE:
                    JOptionPane.showMessageDialog(VistaEliminarCliente.this, "Error: No existe ningún cliente con el ID: " + datos, "Error", JOptionPane.ERROR_MESSAGE);
                    txtId.requestFocus();
                    break;

                case Eventos.RES_BAJA_CLIENTE_KO_YA_INACTIVO:
                    JOptionPane.showMessageDialog(VistaEliminarCliente.this, "El cliente ya se encuentra en estado inactivo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        });
    }
    
    
    // reset
    
    @Override
    public void setVisible(boolean b) {
        if (b) limpiarCampos();
        super.setVisible(b);
    }
    
    private void limpiarCampos() {
    	txtId.setText("");
    	txtId.requestFocus();
    	pack();
    }
}