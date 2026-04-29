package presentacion.GUICliente;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import negocio.cliente.TCliente;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaBuscarCliente extends JFrame implements IGUI {
	
	// ATRIBUTOS	
	private JTextField txtId;
	private JTextArea areaDetalles;
    private JButton btnConsultar, btnLimpiar, btnCancelar;
	
	// CONSTRUCTORA 
	
	public VistaBuscarCliente() {
		setTitle("Buscar Cliente");
		initGUI();
	}
	
	// MÉTODOS.
	
	private void initGUI() {
		// Creo panel de vista principal.
		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
		viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// Panel de búsqueda
		JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtId = new JTextField(10);
        btnConsultar = new JButton("CONSULTAR");
        panelBusqueda.add(new JLabel("ID Cliente:"));
        panelBusqueda.add(txtId);
        panelBusqueda.add(btnConsultar);
		
        // Área de visualización (no editable)
        areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);	
        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Cliente"));
        areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaDetalles.setPreferredSize(new Dimension(363, 200));
        // Lo meto en un JScrollPane para que se pueda ir scrolleando la información mostrada.
        JScrollPane scroll = new JScrollPane(areaDetalles);
        
        // Panel de botones inferiores
        JPanel panelBotones = new JPanel();
        btnLimpiar = new JButton("LIMPIAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCancelar);

        
        // Listener de botón Consulta.
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String textoId = txtId.getText();
                    // Comprobamos que se ha rellenado el campo.
                    if (textoId.isEmpty()) {
    					JOptionPane.showMessageDialog(null, "Error: debe introducir un id.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int id = Integer.parseInt(textoId);
                        Controlador.getInstance().accion(Eventos.BUSCAR_CLIENTE, id);
                    }
                } catch (NumberFormatException ex) { 	// Lanzo error si no se ha podido parsear el número.
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtId.requestFocus();	// El cursor se pone en el JTextField.
                }
            }
        });
        
        // Listener del botón Limpiar (limpio los textos).
        btnLimpiar.addActionListener(e -> {
            txtId.setText("");
            areaDetalles.setText("");
        });
        
        // Listener del botón Cancelar (cierro la ventana).
        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        
        // Label de título.
        JLabel lblTitulo = new JLabel("Introduzca el ID del Cliente a buscar:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
       
        // Añado componentes al panel principal
        viewPanel.add(lblTitulo);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelBusqueda);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(scroll);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelBotones);

        getContentPane().add(viewPanel);
        pack();
        setResizable(false); // Evitamos que se deforme el layout
        setLocationRelativeTo(null);
	}
	
	
	// MÉTODO DE IGUI
	
	
	@Override public void actualizar(int evento, Object datos) {
    	// El controlador llama a este método tras la ejecución en el SA
        switch (evento) {
        	
        	// Datos es el TCliente del cliente.
            case Eventos.RES_BUSCAR_CLIENTE_OK:
            	// Recibo el transfer de cliente para leer los datos.
                TCliente tc = (TCliente) datos;
                areaDetalles.setPreferredSize(null); 

                // Formateo los datos en un StringBuilder.
                StringBuilder sb = new StringBuilder();
                sb.append(" ------------------------------------------ \n");
                sb.append("          DETALLES DEL CLIENTE              \n");
                sb.append(" ------------------------------------------ \n");
                sb.append("ID:       ").append(tc.getId()).append("\n");
                sb.append("DNI:      ").append(tc.getDNI()).append("\n");
                sb.append("Nombre:   ").append(tc.getNombre()).append("\n");
                sb.append("Apellidos: ").append(tc.getApellidos()).append("\n");
                sb.append("Teléfono:   ").append(tc.getTelefono()).append("\n");
                
                areaDetalles.setText(sb.toString());
                this.pack();
                areaDetalles.setCaretPosition(0);
                break;
                
            // Datos es la id del cliente.
            case Eventos.RES_BUSCAR_CLIENTE_KO:
                areaDetalles.setText("");
                JOptionPane.showMessageDialog(this, "No se ha encontrado ningún cliente con el ID: " + datos, "Error", JOptionPane.ERROR_MESSAGE);
                txtId.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Error inesperado al consultar.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

}
