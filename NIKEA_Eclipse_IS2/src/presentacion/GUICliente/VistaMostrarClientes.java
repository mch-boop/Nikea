package presentacion.GUICliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.Collection;
import negocio.cliente.TCliente;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarClientes extends JFrame implements IGUI {

	// ATRIBUTOS
	
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JButton btnCargar, btnLimpiar, btnCancelar;

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

        // Panel superior con el botón de carga
        JPanel panelNorte = new JPanel();
        btnCargar = new JButton("MOSTRAR TODOS LOS CLIENTES");
        panelNorte.add(btnCargar);

        // Panel inferior con botones 
        JPanel panelSur = new JPanel();
        btnLimpiar = new JButton("LIMPIAR");
        btnCancelar = new JButton("CANCELAR");
        panelSur.add(btnLimpiar);
        panelSur.add(btnCancelar);

        // Lógica de Carga
        btnCargar.addActionListener(e -> {
            // No necesitamos enviar datos para listar, el SA ya sabe qué hacer
            Controlador.getInstance().accion(Eventos.MOSTRAR_CLIENTES, null);
        });

        // Lógica de Limpiar
        btnLimpiar.addActionListener(e -> modeloTabla.setRowCount(0));

        // Lógica de Cancelar
        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        // Añadir componentes al panel principal
        mainPanel.add(panelNorte, BorderLayout.NORTH);
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
        switch (evento) {

        case Eventos.RES_MOSTRAR_CLIENTES_OK:
            Collection<TCliente> lista = (Collection<TCliente>) datos;
            
            modeloTabla.setRowCount(0); // Limpiar tabla antes de cargar
            
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay clientes registrados en el sistema.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
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
    }
}
