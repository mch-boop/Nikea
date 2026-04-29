package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;
import negocio.empleado.TEmpleado;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaBuscarEmpleado extends JFrame implements IGUI {

	// ATRIBUTOS
	
    private JTextField txtId;
    private JTextArea areaDetalles;
    private JButton btnConsultar, btnLimpiar, btnCancelar;

    // CONSTRUCTORA
    
    public VistaBuscarEmpleado() {
        setTitle("Consultar Empleado por ID");
        initGUI();
    }

    
    // MÉTODOS
    
    private void initGUI() {
    	
    	// PANEL PRINCIPAL con GridBagLayout para centrado total
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtId = new JTextField(10);
        btnConsultar = new JButton("CONSULTAR");
        panelBusqueda.add(new JLabel("ID Empleado:"));
        panelBusqueda.add(txtId);
        panelBusqueda.add(btnConsultar);
        gbc.gridy = 0;
        mainPanel.add(panelBusqueda, gbc);

        // AREA DETALLES 
        areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);
        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Empleado"));
        areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaDetalles.setBackground(new Color(245, 245, 245));
        areaDetalles.setPreferredSize(new Dimension(363, 200));
        
        // Contenedor para el área para que no se estire
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(areaDetalles, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnLimpiar = new JButton("LIMPIAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCancelar);
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(panelBotones, gbc);

        // Lógica
        btnConsultar.addActionListener(e -> {
            try {
                String textoId = txtId.getText().trim();
                if (textoId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe introducir un ID.");
                } else {
                    Controlador.getInstance().accion(Eventos.BUSCAR_EMPLEADO, Integer.parseInt(textoId));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El ID debe ser un número entero.");
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtId.setText("");
            areaDetalles.setText("");
            areaDetalles.setPreferredSize(new Dimension(363, 200));
            pack(); // Reajusta al vaciar
        });

        btnCancelar.addActionListener(e -> {
        	txtId.setText("");
            areaDetalles.setText("");
            areaDetalles.setPreferredSize(new Dimension(363, 200));
            pack();
            setVisible(false);
            //dispose(); 
        });

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        // Usamos invokeLater para asegurar que Swing pinte el texto
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_BUSCAR_EMPLEADO_OK:
                    TEmpleado te = (TEmpleado) datos;
                    areaDetalles.setPreferredSize(null); 
                    
                    StringBuilder sb = new StringBuilder();
                    sb.append(" ------------------------------------------ \n");
                    sb.append("          DETALLES DEL EMPLEADO             \n");
                    sb.append(" ------------------------------------------ \n");
                    sb.append("ID:       ").append(te.getId()).append("\n");
                    sb.append("DNI:      ").append(te.getDNI()).append("\n");
                    sb.append("Nombre:   ").append(te.getNombre()).append("\n");
                    sb.append("Apellido: ").append(te.getApellido()).append("\n");
                    sb.append("Sueldo:   ").append(te.getSueldo()).append(" €\n");
                    
                    // Mostramos el tipo y sus datos específicos
                    if (te.getTipo() == 1) { // Vendedor
                        sb.append("Tipo:     VENDEDOR\n");
                        // Aquí hacemos el cast para sacar las ventas si el transfer las trae
                        if (te instanceof negocio.empleado.TVendedor) {
                             sb.append("Ventas:   ").append(((negocio.empleado.TVendedor)te).getNumeroVentas()).append("\n");
                        }
                    } else {
                        sb.append("Tipo:     MONTADOR\n");
                    }
                    
                                       
                    areaDetalles.setText(sb.toString());
                    this.pack();
                    areaDetalles.setCaretPosition(0);
                    break;

                case Eventos.RES_BUSCAR_EMPLEADO_KO:
                	txtId.setText("");
                    areaDetalles.setText("");
                    areaDetalles.setPreferredSize(new Dimension(363, 200));
                    pack();
                    JOptionPane.showMessageDialog(this, "No existe empleado con ID: " + datos, "Error", JOptionPane.ERROR_MESSAGE);
                    txtId.requestFocus();
                    break;
            }
        });
    }
}