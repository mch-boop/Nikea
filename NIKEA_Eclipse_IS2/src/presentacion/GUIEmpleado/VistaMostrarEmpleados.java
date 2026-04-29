package presentacion.GUIEmpleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.Collection;
import negocio.empleado.TEmpleado;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarEmpleados extends JFrame implements IGUI {

	// ATRIBUTOS
	
	private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private JButton btnSalir;

    // CONSTRUCTORA
    
    public VistaMostrarEmpleados() {
        setTitle("Listado General de Empleados");
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
        String[] columnas = {"ID", "DNI", "NOMBRE COMPLETO", "TIPO", "SUELDO (€)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };
        
        tablaEmpleados = new JTable(modeloTabla);
        tablaEmpleados.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scroll = new JScrollPane(tablaEmpleados);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Empleados Activos"));
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

    @Override
    @SuppressWarnings("unchecked")
    public void actualizar(int evento, Object datos) {
        switch (evento) {

        case Eventos.RES_MOSTRAR_EMPLEADOS_OK:
            Collection<TEmpleado> lista = (Collection<TEmpleado>) datos;
            
            modeloTabla.setRowCount(0); // Limpiar tabla antes de cargar
            
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay empleados registrados en el sistema.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                boolean hayActivos = false;
                for (TEmpleado te : lista) {
                    if (te.isActivo()) { // Solo mostramos si el estado es activo
                        Object[] fila = {
                            te.getId(),
                            te.getDNI(),
                            te.getNombre() + " " + te.getApellido(),
                            te.getTipo() == 1 ? "Vendedor" : "Montador",
                            String.format("%.2f", te.getSueldo())
                        };
                        modeloTabla.addRow(fila);
                        hayActivos = true;
                    }
                }
                
                if (!hayActivos) {
                    JOptionPane.showMessageDialog(this, "No hay empleados activos para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
                } 
            }
            break;

        case Eventos.RES_MOSTRAR_EMPLEADOS_KO:
            modeloTabla.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Error al recuperar la lista de empleados.", "Error", JOptionPane.ERROR_MESSAGE);
            break;

            default:
                break;
        }
    }
}
