package presentacion.GUIServicio;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import negocio.servicio.TArticulo;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarMejorArticulo extends JDialog implements IGUI {

		private JTable tablaArticulo;
		private DefaultTableModel model;
	    private JButton btnSalir;
	
		public VistaMostrarMejorArticulo() {
			super(null, "Mostrar Mejor Articulo", ModalityType.APPLICATION_MODAL);
			setTitle("Mostrar Mejor Articulo");
			
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			initGUI();
		}
		
		
		private void initGUI() {
			JPanel viewPanel = new JPanel();
			viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
			viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	        model = new DefaultTableModel(new String[] { "Id", "Nombre", "Descripcion", "Stock", "Precio", "Marca" }, 0) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };

	        tablaArticulo = new JTable(model);
	        tablaArticulo.setFont(new Font("Monospaced", Font.PLAIN, 12));
	        tablaArticulo.setRowHeight(24);

	        JScrollPane scroll = new JScrollPane(tablaArticulo);
	        scroll.setBorder(BorderFactory.createTitledBorder("Detalle del Mejor Articulo"));
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
		}

		public void cargarMejorArticulo() {
			model.setRowCount(0);
			Controlador.getInstance().accion(Eventos.MOSTRAR_MEJOR_ARTICULO, null);
		}
		
		
		
		
		@Override public void actualizar(int evento, Object datos) {
	        switch (evento) {

	            case Eventos.RES_MOSTRAR_MEJOR_ARTICULO_OK:
	                TArticulo tc = (TArticulo) datos;
	                model.setRowCount(0);
	                model.addRow(new Object[] {
	                	tc.getId(),
	                	tc.getNombre(),
	                	tc.getDescripcion(),
	                	tc.getStock(),
	                	tc.getPrecioActual(),
	                	tc.getMarca()
	                });
	                this.setVisible(true);
	                this.toFront();
	                break;

	            case Eventos.RES_MOSTRAR_MEJOR_ARTICULO_KO:
	                model.setRowCount(0);
	                break;

	        }
	    }
	}

