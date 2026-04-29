package presentacion.GUICliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.Collection;
import negocio.cliente.TCliente;
import presentacion.IGUI;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarClientes extends JFrame implements IGUI {

	// ATRIBUTOS
	
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JButton btnSalir;

    // CONSTRUCTORA
    
    public VistaMostrarClientes() {
        setTitle("Listado General de Clientes");
        initGUI();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                modeloTabla.setRowCount(0);
            }
        });
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    

    // MÉTODOS
    
    private void initGUI() {
    	
    	// Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Configuración de la Tabla
        String[] columnas = {"ID", "DNI", "NOMBRE COMPLETO", "TELÉFONO"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };
        
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scroll = new JScrollPane(tablaClientes);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Clientes Activos"));
        scroll.setPreferredSize(new Dimension(700, 400));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Panel inferior con el botón de salida. 
        JPanel panelSur = new JPanel();
        btnSalir = new JButton("SALIR");
        panelSur.add(btnSalir);

        // Lógica de Cancelar
        btnSalir.addActionListener(e -> { setVisible(false); });

        // Añadir componentes al panel principal
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(panelSur, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
    
    // Datos es una lista de TClientes.
    @Override
    @SuppressWarnings("unchecked")
    public void actualizar(int evento, Object datos) {
    	SwingUtilities.invokeLater(() -> {
	        switch (evento) {
	
	        case Eventos.RES_MOSTRAR_CLIENTES_OK:
	            Collection<TCliente> lista = (Collection<TCliente>) datos;
	            
	            modeloTabla.setRowCount(0); // Limpiar tabla antes de cargar
	            
	            if (lista.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "No hay clientes registrados en el sistema.", "Información", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	            	setVisible(true);
	            	
	                boolean hayActivos = false;
	                for (TCliente tc : lista) {
	                    if (tc.isActivo()) { // Solo mostramos si el estado es activo
	                        Object[] fila = {
	                            tc.getId(),
	                            tc.getDNI(),
	                            tc.getNombre() + " " + tc.getApellidos(),
	                            tc.getTelefono(),
	                        };
	                        modeloTabla.addRow(fila);
	                        hayActivos = true;
	                    }
	                }
	                
	                if (!hayActivos) {
	                    JOptionPane.showMessageDialog(this, "No hay clientes activos para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
	                } 
	            }
	            break;
	
	        case Eventos.RES_MOSTRAR_CLIENTES_KO:
	            modeloTabla.setRowCount(0);
	            JOptionPane.showMessageDialog(this, "Error al recuperar la lista de clientes.", "Error", JOptionPane.ERROR_MESSAGE);
	            break;
	
	            default:
	                break;
	        }  
	    });
    }
}
