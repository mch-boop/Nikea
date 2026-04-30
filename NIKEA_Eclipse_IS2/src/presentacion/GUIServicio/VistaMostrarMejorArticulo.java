package presentacion.GUIServicio;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

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
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
@SuppressWarnings("serial")
public class VistaMostrarMejorArticulo extends JFrame implements IGUI {

		private JTextField txtId;
		private JTextArea areaDetalles;
	    private JButton btnSalir;
	
		public VistaMostrarMejorArticulo() {
			setTitle("Mostrar Mejor Articulo");
			initGUI();
		}
		
		
		private void initGUI() {
			JPanel viewPanel = new JPanel();
			viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
			viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	        areaDetalles = new JTextArea(10, 30);
	        areaDetalles.setEditable(false);	
	        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Articulo"));
	        areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 13));
	        JScrollPane scroll = new JScrollPane(areaDetalles);
	        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);	
	        
	        JPanel panelBotones = new JPanel();
	        btnSalir = new JButton("SALIR");
	        panelBotones.add(btnSalir);
	        btnSalir.addActionListener(e -> {
	            setVisible(false);
	            dispose();
	        });
	               
	        
	        // Ttulo.
	        JLabel lblTitulo = new JLabel("Datos del Mejor Articulo.");
	        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
	       
	        viewPanel.add(lblTitulo);
	        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	        viewPanel.add(scroll);
	        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	        viewPanel.add(panelBotones);

	        getContentPane().add(viewPanel);
	        pack();
	        setResizable(false); 
	        setLocationRelativeTo(null);
	        
	        Controlador.getInstance().accion(Eventos.MOSTRAR_MEJOR_ARTICULO, null);
		}
		
		
		
		
		@Override public void actualizar(int evento, Object datos) {
	        switch (evento) {

	            case Eventos.RES_MOSTRAR_MEJOR_ARTICULO_OK:
	                TArticulo tc = (TArticulo) datos;
	                StringBuilder sb = new StringBuilder();
	                sb.append("Id:       ").append(tc.getId()).append("\n");
	                sb.append("Nombre:      ").append(tc.getNombre()).append("\n");
	                sb.append("Descripcion:   ").append(tc.getDescripcion()).append("\n");
	                sb.append("Stock: ").append(tc.getStock()).append("\n");
	                sb.append("Precio:   ").append(tc.getPrecioActual()).append("\n");
	                sb.append("Marca:   ").append(tc.getMarca()).append("\n");
	         	                areaDetalles.setText(sb.toString());
	                break;

	            case Eventos.RES_MOSTRAR_MEJOR_ARTICULO_KO:
	                areaDetalles.setText("");
	                JOptionPane.showMessageDialog(this, "No se ha podido encontrar un mejor articulo", "Error", JOptionPane.ERROR_MESSAGE);
	                txtId.requestFocus();
	                break;

	        }
	    }
	}

