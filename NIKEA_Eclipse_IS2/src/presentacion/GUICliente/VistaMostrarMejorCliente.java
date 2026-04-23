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
public class VistaMostrarMejorCliente extends JFrame implements IGUI {

	// ATRIBUTOS	
	private JTextField txtId;
	private JTextArea areaDetalles;
    private JButton btnSalir;
	
	// CONSTRUCTORA 
	
	public VistaMostrarMejorCliente() {
		setTitle("Mostrar Mejor Cliente");
		initGUI();
	}
	
	// MÉTODOS.
	
	void initGUI() {
		// Creo panel de vista principal.
		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
		viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Área de visualización (no editable)
        areaDetalles = new JTextArea(10, 30);
        areaDetalles.setEditable(false);	
        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Cliente"));
        areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 13));
        // Lo meto en un JScrollPane para que se pueda ir scrolleando la información mostrada.
        JScrollPane scroll = new JScrollPane(areaDetalles);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);	// Para que el JTextArea no quede muy pequeño.
        
        JPanel panelBotones = new JPanel();
        btnSalir = new JButton("SALIR");
        panelBotones.add(btnSalir);
        // Listener del botón Cancelar (cierro la ventana).
        btnSalir.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
               
        
        // Label de título.
        JLabel lblTitulo = new JLabel("Datos del Mejor Cliente.");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
       
        // Añado componentes al panel principal
        viewPanel.add(lblTitulo);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(scroll);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelBotones);

        getContentPane().add(viewPanel);
        pack();
        setResizable(false); // Evitamos que se deforme el layout
        setLocationRelativeTo(null);
        
        // Llamada al controlador para que se lance el proceso de buscar el mejor cliente.
        Controlador.getInstance().accion(Eventos.MOSTRAR_MEJOR_CLIENTE, null);
	}
	
	
	// MÉTODO DE IGUI
	
	// Datos es el TCliente del cliente.
	@Override public void actualizar(int evento, Object datos) {
    	// El controlador llama a este método tras la ejecución en el SA
        switch (evento) {

            case Eventos.RES_BUSCAR_EMPLEADO_OK:
            	// Recibo el transfer de cliente para leer los datos.
                TCliente tc = (TCliente) datos;
                // Formateo los datos en un StringBuilder.
                StringBuilder sb = new StringBuilder();
                sb.append("ID:       ").append(tc.getId()).append("\n");
                sb.append("DNI:      ").append(tc.getDNI()).append("\n");
                sb.append("Nombre:   ").append(tc.getNombre()).append("\n");
                sb.append("Apellidos: ").append(tc.getApellidos()).append("\n");
                sb.append("Estado:   ").append(tc.isActivo() ? "ACTIVO" : "INACTIVO (Baja)");
                
                // Muestro el texto.
                areaDetalles.setText(sb.toString());
                break;

            case Eventos.RES_BUSCAR_EMPLEADO_KO:
                areaDetalles.setText("");
                JOptionPane.showMessageDialog(this, "No se ha encontrado ningún cliente con el ID: " + ((TCliente)datos).getId(), "Error", JOptionPane.ERROR_MESSAGE);
                txtId.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Error inesperado al consultar.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}