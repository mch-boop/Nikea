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
    private JTextField txtNombreAct, txtApellidoAct, txtTelefonoAct, txtDNIAct;
    private JButton btnBuscar, btnModificar, btnCancelar, btnCancelarModif;
    private JPanel panelEdicion;
    private TCliente clienteEncontrado;

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
        
        // Panel de búsqueda.
        JPanel pBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtId = new JTextField(20);	        // El ID es obligatorio para saber a quién modificar
        btnBuscar = new JButton("BUSCAR");
        btnCancelar = new JButton("CANCELAR");
        pBusqueda.add(new JLabel("ID Cliente a modificar:"));
        pBusqueda.add(txtId);
        pBusqueda.add(btnBuscar);
        pBusqueda.add(btnCancelar);
        
        // Listener de botón Cancelar
        btnCancelar.addActionListener(e -> {
        	// Cerrar ventana.
            setVisible(false);
            dispose();
        });
        
        btnBuscar.addActionListener(e -> {
        	try {
                if (txtId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El ID es obligatorio para identificar al cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
	                int id = (Integer.parseInt(txtId.getText()));
	                Controlador.getInstance().accion(Eventos.MODIFICAR_BUSCAR_CLIENTE, id);
	            }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Asegúrese de que el ID sea un número válido.");
            }
        });
        
        // Label de título.
        JLabel lblTitulo = new JLabel("Introduzca el ID del Cliente a modificar:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Panel de botones
        JPanel pBotones = new JPanel();
        pBotones.add(btnBuscar); pBotones.add(btnCancelar);
        
        // Creo panel de Edición.
        crearPanelEdicion();
        
        // Añadir componentes al panel principal
        mainPanel.add(lblTitulo);
        mainPanel.add(pBusqueda);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(pBotones);
        mainPanel.add(panelEdicion);
        
        
        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    // Método auxiliar
    void crearPanelEdicion() {
    	// Panel de edición.
        panelEdicion = new JPanel(new GridBagLayout());
        panelEdicion.setBorder(BorderFactory.createTitledBorder("Información del Cliente"));
        panelEdicion.setVisible(false); // Se activará al buscar con éxito
        
        // Campos de cambio.
        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtTelefono = new JTextField(20);
        txtDNI = new JTextField(20);
        
        txtNombre.setToolTipText("Deje este campo vacío para conservar el nombre actual");
        txtApellido.setToolTipText("Deje este campo vacío para conservar el apellido actual");
        txtTelefono.setToolTipText("Deje este campo vacío para conservar el telefono actual");
        txtDNI.setToolTipText("Deje este campo vacío para conservar el DNI actual");
        
        // Inicializar campos actuales (bloqueados)
        txtNombreAct = new JTextField(15); txtNombreAct.setEditable(false);
        txtApellidoAct = new JTextField(15); txtApellidoAct.setEditable(false);
        txtTelefonoAct = new JTextField(15); txtTelefonoAct.setEditable(false);
        txtDNIAct = new JTextField(15); txtDNIAct.setEditable(false);
        
        // Ahora configuramos el panel de edición.
        // Layout del formulario (3 columnas: Etiqueta | Actual | Nuevo)
        GridBagConstraints ajuste = new GridBagConstraints();
        ajuste.fill = GridBagConstraints.HORIZONTAL;
        ajuste.insets = new Insets(5, 5, 5, 5);

        // Cabeceras de columna
        ajuste.gridy = 0; ajuste.gridx = 1; 
        panelEdicion.add(new JLabel("Dato actual"), ajuste);
        ajuste.gridx = 2; 
        panelEdicion.add(new JLabel("Dato nuevo"), ajuste);

        // Fila 1: Nombre
        ajuste.gridy = 1; ajuste.gridx = 0; panelEdicion.add(new JLabel("Nombre:"), ajuste);
        ajuste.gridx = 1; panelEdicion.add(txtNombreAct, ajuste);
        ajuste.gridx = 2; panelEdicion.add(txtNombre, ajuste);

        // Fila 2: Apellido
        ajuste.gridy = 2; ajuste.gridx = 0; panelEdicion.add(new JLabel("Apellidos:"), ajuste);
        ajuste.gridx = 1; panelEdicion.add(txtApellidoAct, ajuste);
        ajuste.gridx = 2; panelEdicion.add(txtApellido, ajuste);

        // Fila 3: Teléfono
        ajuste.gridy = 3; ajuste.gridx = 0; panelEdicion.add(new JLabel("Teléfono:"), ajuste);
        ajuste.gridx = 1; panelEdicion.add(txtTelefonoAct, ajuste);
        ajuste.gridx = 2; panelEdicion.add(txtTelefono, ajuste);

        // Fila 4: DNI
        ajuste.gridy = 4; ajuste.gridx = 0; panelEdicion.add(new JLabel("DNI:"), ajuste);
        ajuste.gridx = 1; panelEdicion.add(txtDNIAct, ajuste);
        ajuste.gridx = 2; panelEdicion.add(txtDNI, ajuste);
        
        
        // Panel de botones
        JPanel pBotones = new JPanel();
        btnModificar = new JButton("GUARDAR CAMBIOS");
        btnCancelarModif = new JButton("CANCELAR");
             
        
        // Listener de botón Modificar.
        btnModificar.addActionListener(new ActionListener() {
            @SuppressWarnings("null")
			@Override
            public void actionPerformed(ActionEvent e) {
                try {
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
	
	                // Cuando se haya llegado al listener de btnModificar es porque ya hemos pasado por 
	                // la primera llamada a actualizar y por tanto hemos recibido e inicializado clienteEncontrado.
	                String info = "ID: " + clienteEncontrado.getId() + "\nNombre: " + clienteEncontrado.getNombre() + " " 
	                        + clienteEncontrado.getApellidos() + "\nDNI: " + clienteEncontrado.getDNI();
	                        
                    int respuesta = JOptionPane.showConfirmDialog(null, 
                        "¿Está seguro de que desea modificar este cliente?:\n\n" + info ,
                        "Confirmar Modificación:", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.WARNING_MESSAGE);
                    
                    
                    if (respuesta == JOptionPane.YES_OPTION) {
                        Controlador.getInstance().accion(Eventos.MODIFICAR_CLIENTE, tc);
                    }
	            }
                 catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Asegúrese de que el Teléfono sea un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Listener de botón CancelarModif
        btnCancelarModif.addActionListener(e -> {
        	// Cerrar ventana.
            setVisible(false);
            dispose();
        });
        
        pBotones.add(btnModificar); 
        pBotones.add(btnCancelarModif);
        panelEdicion.add(pBotones);  
    }
    
    // Datos es la id del cliente.
    @Override
    public void actualizar(int evento, Object datos) {
        switch (evento) {

            case Eventos.RES_MODIFICAR_BUSCAR_CLIENTE_OK:
            	clienteEncontrado = (TCliente) datos;
            	limpiarCampos();
            	
            	// Rellenar datos actuales
                txtNombreAct.setText(clienteEncontrado.getNombre());
                txtApellidoAct.setText(clienteEncontrado.getApellidos());
                txtTelefonoAct.setText(String.valueOf(clienteEncontrado.getTelefono()));
                txtDNIAct.setText(clienteEncontrado.getDNI());
                
            	// Mostramos el panel y ajustamos la ventana
                panelEdicion.setVisible(true);
                txtId.setEditable(false);
                pack();
                break;
                
            case Eventos.RES_MODIFICAR_CLIENTE_OK:
                JOptionPane.showMessageDialog(this, "Cliente modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                // Aquí sí queremos ocultar el panel y resetear todo para buscar a otro cliente
                panelEdicion.setVisible(false);
                txtId.setEditable(true);
                txtId.setText("");
                pack();
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
		txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtDNI.setText("");
	}
}