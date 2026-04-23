package presentacion.GUICliente;

import java.awt.BorderLayout;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarFacturasCliente extends JFrame implements IGUI{

	// ATRIBUTOS
	private TableModel tabla;
	private JTextField txtId;
	private JButton btnMostrar, btnCancelar;
	
	// CONSTRUCTORA
	public VistaMostrarFacturasCliente() {
		setTitle("Facturas del Cliente");
		initGUI();
	}
	
	
	// MÉTODOS.
	
	void initGUI() {
		// Creo panel de vista principal.
		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
		viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		// Panel de búsqueda
		JPanel panelId = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtId = new JTextField(10);
        panelId.add(new JLabel("ID Cliente:"));
        panelId.add(txtId);
		
        // Panel de tabla.
        JPanel panelTabla = new JPanel();
        // Creo una tabla con el modelo correspondiente y la añado al panel.
        panelTabla.add(new JTable(tabla));
        panelTabla.setBorder(BorderFactory.createTitledBorder("Detalles del Cliente"));
        // Lo meto en un JScrollPane para que se pueda ir scrolleando la información mostrada.
        JScrollPane scroll = new JScrollPane(panelTabla);
        
        // Panel de botones inferiores
        JPanel panelBotones = new JPanel();
        btnMostrar = new JButton("MOSTRAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnMostrar);
        panelBotones.add(btnCancelar);

        
        // Listener de botón Consulta.
        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String textoId = txtId.getText();
                    // Comprobamos que se ha rellenado el campo.
                    if (textoId.isEmpty()) {
    					JOptionPane.showMessageDialog(null, "Error: debe introducir un id.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int id = Integer.parseInt(textoId);
                        Controlador.getInstance().accion(Eventos.MOSTRAR_FACTURAS_CLIENTE_POR_ID, id);
                    }
                } catch (NumberFormatException ex) { 	// Lanzo error si no se ha podido parsear el número.
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtId.requestFocus();	// El cursor se pone en el JTextField.
                }
            }
        });
        
        
        // Listener del botón Cancelar (cierro la ventana).
        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        
        // Label de título.
        JLabel lblTitulo = new JLabel("Introduzca el ID del Cliente cuyas facturas se quieren mostrar:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
       
        // Añado componentes al panel principal
        viewPanel.add(lblTitulo);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelId);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(scroll);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelBotones);

        getContentPane().add(viewPanel);
        pack();
        setResizable(false); // Evitamos que se deforme el layout
        setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(int evento, Object datos) {
		// TODO Auto-generated method stub
		
	}

}
