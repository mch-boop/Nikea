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
public class VistaModificarCliente extends JFrame implements IGUI {

	// ATRIBUTOS
	
    private JTextField txtId, txtNombre, txtApellido, txtTelefono, txtDNI;
    private JButton btnModificar, btnCancelar;

    // CONSTRUCTORA
    
    public VistaModificarCliente() {
        setTitle("Modificar Cliente");
        initGUI();
    }

    
    // MÉTODOS
    
    private void initGUI() {
    	// Creo panel principal.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // El ID es obligatorio para saber a quién modificar
        txtId = new JTextField(20);
        
        // Campos opcionales
        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtTelefono = new JTextField(20);
        txtDNI = new JTextField(20);
        
        txtNombre.setToolTipText("Deje este campo vacío para conservar el nombre actual");
        txtApellido.setToolTipText("Deje este campo vacío para conservar el nombre actual");
        txtTelefono.setToolTipText("Deje este campo vacío para conservar el nombre actual");
        txtDNI.setToolTipText("Deje este campo vacío para conservar el nombre actual");

        btnModificar = new JButton("GUARDAR CAMBIOS");
        btnCancelar = new JButton("CANCELAR");

        // Listener de botón Modificar.
        btnModificar.addActionListener(new ActionListener() {
            @SuppressWarnings("null")
			@Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtId.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El ID es obligatorio para identificar al cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
		                TCliente tc = new TCliente();
		                tc.setID(Integer.parseInt(txtId.getText()));
		
		                // Solo enviamos datos si el usuario escribió algo
		                
		                // Nombre
		                if (!txtNombre.getText().trim().isEmpty()) {
		                	tc.setNombre(txtNombre.getText().trim());
		                } else {
		                	tc.setNombre(null);  // Valor centinela: "No modificar nombre"
		                }
		
		                // Apellido
		                if (!txtApellido.getText().trim().isEmpty()) {
		                	tc.setApellidos(txtApellido.getText().trim());
		                } else {
		                	tc.setApellidos(null); // Valor centinela: "No modificar apellidos"
		                }
		
		                // Telefono 
		                if (!txtTelefono.getText().trim().isEmpty()) {
		                	tc.setTelefono(Integer.valueOf(txtTelefono.getText().trim()));
		                } else {
		                	tc.setTelefono((Integer)null); // Valor centinela: "No modificar teléfono"
		                }
		                
		                // DNI 
		                if (!txtDNI.getText().trim().isEmpty()) {
		                	tc.setDNI(txtDNI.getText().trim());
		                } else {
		                	tc.setDNI(null); // Valor centinela: "No modificar DNI"
		                }
		
		                Controlador.getInstance().accion(Eventos.MODIFICAR_CLIENTE, tc);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Asegúrese de que el ID y el Teléfono sean números válidos.");
                }
            }
        });

        // Listener de botón Cancelar
        btnCancelar.addActionListener(e -> {
        	// Cerrar ventana.
            setVisible(false);
            dispose();
        });

        // ALINEACIÓN (usando GridBagLayout y GridBagConstraints).
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints ajuste = new GridBagConstraints();
        ajuste.fill = GridBagConstraints.HORIZONTAL;
        ajuste.insets = new Insets(5, 5, 5, 5);

        // Fila 0: ID
        ajuste.gridx = 0; ajuste.gridy = 0;
        formPanel.add(new JLabel("ID Cliente (Obligatorio):"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtId, ajuste);
        
        // Fila 1: Nombre
        ajuste.gridx = 0; ajuste.gridy = 1;
        formPanel.add(new JLabel("Nuevo Nombre:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtNombre, ajuste);

        // Fila 2: Apellido
        ajuste.gridx = 0; ajuste.gridy = 2;
        formPanel.add(new JLabel("Nuevo Apellido:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtApellido, ajuste);

        // Fila 3: Teléfono
        ajuste.gridx = 0; ajuste.gridy = 3;
        formPanel.add(new JLabel("Nuevo Teléfono:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtTelefono, ajuste);

        // Fila 4: DNI
        ajuste.gridx = 0; ajuste.gridy = 4;
        formPanel.add(new JLabel("Nuevo DNI:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtDNI, ajuste);
        
        // Label de título.
        JLabel lblTitulo = new JLabel("Introduzca el ID del Cliente a modificar:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Panel de botones
        JPanel pBotones = new JPanel();
        pBotones.add(btnModificar); pBotones.add(btnCancelar);
        
        // Añadir componentes al panel principal
        mainPanel.add(lblTitulo);
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(pBotones);

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // Datos es la id del cliente.
    @Override
    public void actualizar(int evento, Object datos) {
        switch (evento) {

            case Eventos.RES_MODIFICAR_CLIENTE_OK:
                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente para el cliente con ID: " + datos, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                break;

            case Eventos.RES_MODIFICAR_CLIENTE_KO_NO_EXISTE:
                JOptionPane.showMessageDialog(this, "Error: No se encontró ningún cliente con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                txtId.requestFocus();
                break;

            case Eventos.RES_MODIFICAR_CLIENTE_KO_DATOS_INVALIDOS:
                JOptionPane.showMessageDialog(this, "Error: Los datos introducidos no son válidos para la modificación.", "Validación Fallida", JOptionPane.ERROR_MESSAGE);
                break;

            default:
                JOptionPane.showMessageDialog(this, "Error al intentar modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }


	private void limpiarCampos() {
		txtId.setText("");
		txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtDNI.setText("");
	}
}