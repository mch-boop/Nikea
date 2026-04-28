package presentacion.GUIMarca;

import javax.swing.*;
import java.awt.*;

import negocio.marca.TMarca;
import negocio.marca.TMarca.Especialidad;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaBuscarMarca extends JFrame implements IGUI {

	private JTextField txtId;
    private JTextArea areaDetalles;
    private JButton btnConsultar, btnLimpiar, btnCancelar;

    public VistaBuscarMarca() {
        setTitle("Consultar Marca por ID");
        initGUI();
    }

    private void initGUI() {

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // PANEL BÚSQUEDA
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtId = new JTextField(10);
        btnConsultar = new JButton("CONSULTAR");

        panelBusqueda.add(new JLabel("ID Marca:"));
        panelBusqueda.add(txtId);
        panelBusqueda.add(btnConsultar);

        gbc.gridy = 0;
        mainPanel.add(panelBusqueda, gbc);

        // ÁREA DETALLES
        areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);
        areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalles de la Marca"));
        areaDetalles.setBackground(new Color(245, 245, 245));
        
        JScrollPane scroll = new JScrollPane(areaDetalles);
        scroll.setPreferredSize(new Dimension(363, 203));
        
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(scroll, gbc);

        // BOTONES
        JPanel panelBotones = new JPanel();

        btnLimpiar = new JButton("LIMPIAR");
        btnCancelar = new JButton("CANCELAR");

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCancelar);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(panelBotones, gbc);

        // ACCIONES
        btnConsultar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                Controlador.getInstance().accion(Eventos.BUSCAR_MARCA, id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtId.setText("");
            areaDetalles.setText("");
        });

        btnCancelar.addActionListener(e -> dispose());

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override
    public void actualizar(int evento, Object datos) {

    	SwingUtilities.invokeLater(() -> {

            switch (evento) {

                case Eventos.RES_BUSCAR_MARCA_OK: {

                    TMarca tm = (TMarca) datos;

                    StringBuilder sb = new StringBuilder();

                    sb.append("----------------------------------------\n");
                    sb.append("           DETALLES DE LA MARCA        \n");
                    sb.append("----------------------------------------\n");

                    sb.append(String.format("%-15s %s\n", "ID:", tm.getId()));
                    sb.append(String.format("%-15s %s\n", "Nombre:", tm.getNombre()));

                    sb.append("----------------------------------------\n");
                    sb.append("Especialidades:\n");

                    if (tm.getEspecialidades() == null || tm.getEspecialidades().isEmpty()) {
                        sb.append("   (Sin especialidades)\n");
                    } else {
                    	for (Especialidad esp : tm.getEspecialidades()) {
                            sb.append("   - ").append(esp).append("\n");
                        }
                    }

                    sb.append("========================================\n");

                    areaDetalles.setText(sb.toString());
                    areaDetalles.setCaretPosition(0);
                    break;
                }

                case Eventos.RES_BUSCAR_MARCA_KO:
                    areaDetalles.setText("");
                    JOptionPane.showMessageDialog(this,
                            "No existe marca con ese ID",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });
    }

}
