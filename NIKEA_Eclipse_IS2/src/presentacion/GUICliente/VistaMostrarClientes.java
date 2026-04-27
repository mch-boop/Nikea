package presentacion.GUICliente;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import negocio.cliente.TCliente;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarClientes extends JFrame implements IGUI {

	// ATRIBUTOS
	
    private JTextArea areaListado;
    private JButton btnCargar, btnLimpiar, btnCancelar;

    // CONSTRUCTORA
    
    public VistaMostrarClientes() {
        setTitle("Listado General de Clientes");
        initGUI();
    }
    

    // MÉTODOS
    
    private void initGUI() {
    	
    	// Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Área de texto para el listado
        areaListado = new JTextArea(20, 50);
        areaListado.setEditable(false);
        areaListado.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
        JScrollPane scroll = new JScrollPane(areaListado);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Clientes en el Sistema"));

        // Label de título.
        JLabel lblTitulo = new JLabel("Mostrar todos los clientes del sistema.");
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);  
        
        // Panel superior con el botón de carga
        JPanel panelNorte = new JPanel(new BorderLayout());
        btnCargar = new JButton("MOSTRAR");
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(Box.createRigidArea(new Dimension(0, 10)));
        panelNorte.add(btnCargar, BorderLayout.SOUTH);

        // Panel inferior con botones 
        JPanel panelSur = new JPanel();
        btnLimpiar = new JButton("LIMPIAR");
        btnCancelar = new JButton("CANCELAR");
        panelSur.add(btnLimpiar);
        panelSur.add(btnCancelar);

        // Listener de botón Carga
        btnCargar.addActionListener(e -> {
            // No necesitamos enviar datos para listar, el SA ya sabe qué hacer
            Controlador.getInstance().accion(Eventos.MOSTRAR_CLIENTES, null);
        });

        // Lógica de Limpiar (limpiamos texto del área).
        btnLimpiar.addActionListener(e -> areaListado.setText(""));

        // Lógica de Cancelar
        btnCancelar.addActionListener(e -> {
        	// Cerramos ventana.
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
                
                if (lista.isEmpty()) {
                    areaListado.setText("No hay empleados registrados en el sistema.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    // Cabecera
                    sb.append(String.format("%-5s | %-12s | %-25s | %-10s | %-10s\n", "ID", "DNI", "NOMBRE COMPLETO", "TELÉFONO"));
                    sb.append("--------------------------------------------------------------------------------\n");
                    
                    for (TCliente tc : lista) {
                        String nombreCompleto = tc.getNombre() + " " + tc.getApellidos();
                        
                        sb.append(String.format("%-5d | %-12s | %-25s | %-10d n", 
                        		tc.getId(), tc.getDNI(), nombreCompleto, tc.getTelefono()));
                    }
                    areaListado.setText(sb.toString());
                }
                break;

            case Eventos.RES_MOSTRAR_CLIENTES_KO:
                areaListado.setText("");
                JOptionPane.showMessageDialog(this, "Error al recuperar la lista de clientes.", "Error", JOptionPane.ERROR_MESSAGE);
                break;

            default:
                break;
        }
    }
}
