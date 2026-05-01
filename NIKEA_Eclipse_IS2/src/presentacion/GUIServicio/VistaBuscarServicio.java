package presentacion.GUIServicio;

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

import negocio.servicio.TArticulo;
import negocio.servicio.TServicio;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaBuscarServicio extends JFrame implements IGUI {
	
	// ATRIBUTOS	
	private JTextField txtId;
	private JTextArea areaDetalles;
    private JButton btnConsultar, btnLimpiar, btnCancelar;
	
	// CONSTRUCTORA 
	
	public VistaBuscarServicio() {
		setTitle("Buscar Servicio");
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
        panelBusqueda.add(new JLabel("ID Servicio:"));
        panelBusqueda.add(txtId);
        panelBusqueda.add(btnConsultar);
		
        // Área de visualización (no editable)
        areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);	
        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Servicio"));
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
                    if (textoId.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: debe introducir un id.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        txtId.requestFocus();
                        return;
                    }
                    else {
                        int id = Integer.parseInt(textoId);
                        if (id <= 0) {
                        	JOptionPane.showMessageDialog(null, "El id debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                            txtId.requestFocus();
                            return;
                        }
                        Controlador.getInstance().accion(Eventos.BUSCAR_SERVICIO, id);
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
        JLabel lblTitulo = new JLabel("Introduzca el ID del Servicio a buscar:");
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
            case Eventos.RES_BUSCAR_SERVICIO_OK:
            	// Recibo el transfer de cliente para leer los datos.
                TServicio tc = (TServicio) datos;
                areaDetalles.setPreferredSize(null); 

                // Formateo los datos en un StringBuilder.
                StringBuilder sb = new StringBuilder();
                sb.append(" ------------------------------------------ \n");
                sb.append("          DETALLES DEL SERVICIO             \n");
                sb.append(" ------------------------------------------ \n");
                sb.append("ID:       ").append(tc.getId()).append("\n");
                sb.append("Nombre:      ").append(tc.getNombre()).append("\n");
                sb.append("Descripción:   ").append(tc.getDescripcion()).append("\n");
                sb.append("Stock: ").append(tc.getStock()).append("\n");
                sb.append("Tipo:   ").append(tc.getTipo()).append("\n");

                if (tc.getTipo() != null && tc.getTipo() == 1 && tc instanceof TArticulo) {
                    sb.append("Marca:   ").append(((TArticulo) tc).getMarca()).append("\n");
                }

                areaDetalles.setText(sb.toString());
                this.pack();
                areaDetalles.setCaretPosition(0);
                break;
                
            // Datos es la id del cliente.
            case Eventos.RES_BUSCAR_SERVICIO_KO:
                areaDetalles.setText("");
                JOptionPane.showMessageDialog(this, "No se ha encontrado ningún servicio activo con el ID: " + datos, "Error", JOptionPane.ERROR_MESSAGE);
                txtId.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Error inesperado al consultar.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

}

