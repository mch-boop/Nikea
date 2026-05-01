package presentacion.GUIDescuento;

import java.awt.*;
import javax.swing.*;

import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import negocio.descuento.TDescuento;

@SuppressWarnings("serial")
public class VistaBuscarDescuento extends JDialog implements IGUI {

    // ATRIBUTOS
    private JTextField txtId;
    private JTextArea areaDetalles;
    private JButton btnConsultar, btnLimpiar, btnCancelar;

    // CONSTRUCTORA
    public VistaBuscarDescuento() {
    	super(null, "Consultar Descuento por ID", ModalityType.APPLICATION_MODAL);
        setTitle("Consultar Descuento por ID");
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
    }

    // MÉTODOS
    private void initGUI() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtId = new JTextField(10);
        btnConsultar = new JButton("CONSULTAR");
        panelBusqueda.add(new JLabel("ID Descuento:"));
        panelBusqueda.add(txtId);
        panelBusqueda.add(btnConsultar);
        
        gbc.gridy = 0;
        mainPanel.add(panelBusqueda, gbc);

        areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);
        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Descuento"));
        areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaDetalles.setBackground(new Color(245, 245, 245));
        areaDetalles.setPreferredSize(new Dimension(363, 200));
        
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(areaDetalles, gbc);

        JPanel panelBotones = new JPanel();
        btnLimpiar = new JButton("LIMPIAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCancelar);
        
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(panelBotones, gbc);

        // LOGICA DE BOTONES
        btnConsultar.addActionListener(e -> {
            try {
                String textoId = txtId.getText().trim();
                if (textoId.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe introducir un ID.");
                } else {
                    Controlador.getInstance().accion(Eventos.BUSCAR_DESCUENTO, Integer.parseInt(textoId));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.");
            }
        });

        btnLimpiar.addActionListener(e -> {
            limpiarCampos();
        });

        btnCancelar.addActionListener(e -> {
            txtId.setText("");
            areaDetalles.setText("");
            dispose();
            dispose();
        });

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_BUSCAR_DESCUENTO_OK:
                    TDescuento td = (TDescuento) datos;
                    areaDetalles.setPreferredSize(null);
                    
                    StringBuilder sb = new StringBuilder();
                    sb.append(" ------------------------------------------ \n");
                    sb.append("         DETALLES DEL DESCUENTO             \n");
                    sb.append(" ------------------------------------------ \n");
                    sb.append("ID:          ").append(td.getId()).append("\n");
                    sb.append("Código:      ").append(td.getCodigo()).append("\n");
                    sb.append("Nombre:      ").append(td.getNombre()).append("\n");
                    sb.append("Porcentaje:  ").append(td.getPorcentaje()).append(" %\n");
                    
                    if (td.isTipo()) {
                        sb.append("Tipo:        POR IMPORTE\n");
                        sb.append("Imp. Mín:    ").append(td.getImporteMin()).append(" €\n");
                    } else {
                        sb.append("Tipo:        POR CANTIDAD\n");
                        sb.append("Prod. Mín:   ").append(td.getProductosMin()).append(" uds\n");
                    }
                    
                    sb.append(" ------------------------------------------ \n");

                    areaDetalles.setText(sb.toString());
                    this.pack();
                    areaDetalles.setCaretPosition(0);
                    break;

                case Eventos.RES_BUSCAR_DESCUENTO_KO:
                    txtId.setText("");
                    areaDetalles.setText("");
                    areaDetalles.setPreferredSize(new Dimension(363, 200));
                    pack();
                    JOptionPane.showMessageDialog(this, "No existe descuento activo con ese ID", "Error", JOptionPane.ERROR_MESSAGE);
                    txtId.requestFocus();
                    break;
            }
        });
    }
    
    
    // reset
    
    @Override
    public void setVisible(boolean b) {
        if (b) limpiarCampos();
        super.setVisible(b);
    }
    
    private void limpiarCampos() {
    	txtId.setText("");
        areaDetalles.setText("");
        areaDetalles.setPreferredSize(new Dimension(363, 200));
        pack(); 
    }
}