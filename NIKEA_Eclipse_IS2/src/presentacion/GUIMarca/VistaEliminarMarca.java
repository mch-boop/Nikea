package presentacion.GUIMarca;

import javax.swing.*;
import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.util.List;

import presentacion.IGUI;
import negocio.marca.TMarca;
import negocio.marca.TMarca.Especialidad;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaEliminarMarca extends JDialog implements IGUI {
	
	// ATRIBUTOS
	
	private JTextField txtId;
    private JButton btnBaja, btnCancelar;

    // CONSTRUCTORA
    
    public VistaEliminarMarca() {
    	super(null, "Baja Marca", ModalityType.APPLICATION_MODAL);
        setTitle("Baja Marca");
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
    }

    private void initGUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // input
        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtId = new JTextField(10);

        panelInput.add(new JLabel("ID Marca:"));
        panelInput.add(txtId);

        // botones
        JPanel panelBotones = new JPanel();
        btnBaja = new JButton("DAR DE BAJA");
        btnCancelar = new JButton("CANCELAR");

        panelBotones.add(btnBaja);
        panelBotones.add(btnCancelar);

        // acción baja
        btnBaja.addActionListener(e -> {
            try {
                String textoId = txtId.getText();

                if (textoId.isEmpty()) {
                    actualizar(Eventos.RES_BAJA_MARCA_KO_ID_VACIO, null);
                } else {
                    int id = Integer.parseInt(textoId);
                    Controlador.getInstance().accion(Eventos.BAJA_MARCA, id);
                }

            } catch (NumberFormatException ex) {
                actualizar(Eventos.RES_BAJA_MARCA_KO_ID_FORMATO, null);
            }
        });

        // cancelar
        btnCancelar.addActionListener(e -> {
            txtId.setText("");
            setVisible(false);
        });

        // UI
        JLabel lblTitulo = new JLabel("Introduzca el ID de la marca a dar de baja:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(panelInput);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(panelBotones);

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {

        SwingUtilities.invokeLater(() -> {

            switch (evento) {
                case Eventos.RES_BAJA_MARCA_OK: {

                    TMarca tm = (TMarca) datos;
                    String info = "ID: " + tm.getId() + "\nNombre: " + tm.getNombre() + "\n" +
                    		formatear((List) tm.getEspecialidades());

                    int respuesta = JOptionPane.showConfirmDialog(
                            this, "Se ha encontrado la siguiente marca activa:\n\n" + info
                                    + "\n\n¿Está seguro de que desea darla de baja?", "Confirmar Baja",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (respuesta == JOptionPane.YES_OPTION) {
                        Controlador.getInstance().accion(Eventos.CONFIRMAR_BAJA_MARCA, tm.getId());
                    }

                    break;
                }

                case Eventos.RES_BAJA_MARCA_CONFIRMADA:
                    JOptionPane.showMessageDialog( this,
                            "La marca con ID " + datos + " se ha dado de baja correctamente."
                    );
                    txtId.setText("");
                    break;

                case Eventos.RES_BAJA_MARCA_KO_NO_EXISTE:
                    JOptionPane.showMessageDialog( this,
                            "Error: No existe ninguna marca con el ID: " + datos,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    txtId.requestFocus();
                    break;

                case Eventos.RES_BAJA_MARCA_KO_YA_INACTIVO:
                    JOptionPane.showMessageDialog( this, 
                            "La marca ya se encuentra en estado inactivo.",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE
                    );
                    break;

                case Eventos.RES_BAJA_MARCA_KO_ID_FORMATO:
                    JOptionPane.showMessageDialog(
                            this,
                            "El ID debe ser un número entero válido.",
                            "Error de Formato",
                            JOptionPane.ERROR_MESSAGE
                    );
                    txtId.requestFocus();
                    break;

                case Eventos.RES_BAJA_MARCA_KO_ID_VACIO:
                    JOptionPane.showMessageDialog(
                            this,
                            "El campo ID no puede estar vacío.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    txtId.requestFocus();
                    break;
            }
        });
    }
    
    // formato
    
    private String formatear(List<Especialidad> lista) {

        if (lista == null || lista.isEmpty())
            return " - (sin especialidades)";
        StringBuilder sb = new StringBuilder();

        for (Especialidad e : lista)
            sb.append(" - ").append(e.toString()).append("\n");
        return sb.toString();
    }
    
    
    // reseteo
    
    @Override
    public void setVisible(boolean b) {
        if (b) limpiarCampos();
        super.setVisible(b);
    }
    
    private void limpiarCampos() {
        txtId.setText("");
        txtId.requestFocus();
        pack();
    }
}
