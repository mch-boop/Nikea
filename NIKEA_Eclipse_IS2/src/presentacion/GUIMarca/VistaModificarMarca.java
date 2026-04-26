package presentacion.GUIMarca;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import negocio.marca.TMarca;
import negocio.marca.TMarca.Especialidad;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaModificarMarca extends JFrame implements IGUI {

	// ATRIBUTOS

    private JTextField txtId, txtNombre;
    private JButton btnModificar, btnCancelar;
    private JCheckBox chkEspecialidades;
    private JPanel panelEspecialidades;
    private Map<Especialidad, JCheckBox> checkBoxes;
    private TMarca marcaEncontrada;
    private static TMarca datosNuevos;
    
	// CONSTRUCTORA

    public VistaModificarMarca() {
        setTitle("Modificar Marca");
        initGUI();
    }

	// MÉTODOS

    private void initGUI() {

    	JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtId = new JTextField(20);
        txtNombre = new JTextField(20);

        txtNombre.setToolTipText("Vacío = mantener nombre actual");

        // CHECKBOX PARA MOSTRAR ESPECIALIDADES
        chkEspecialidades = new JCheckBox("Modificar especialidades");

        // PANEL ESPECIALIDADES (oculto por defecto)
        panelEspecialidades = new JPanel();
        panelEspecialidades.setLayout(new BoxLayout(panelEspecialidades, BoxLayout.Y_AXIS));
        panelEspecialidades.setBorder(BorderFactory.createTitledBorder("Especialidades"));
        panelEspecialidades.setVisible(false);

        checkBoxes = new HashMap<>();
        for (Especialidad e : Especialidad.values()) {
            JCheckBox cb = new JCheckBox(e.toString());
            checkBoxes.put(e, cb);
            panelEspecialidades.add(cb);
        }

        chkEspecialidades.addActionListener(e -> {
            panelEspecialidades.setVisible(chkEspecialidades.isSelected());
            this.pack();
        });

        // BOTONES
        btnModificar = new JButton("GUARDAR CAMBIOS");
        btnCancelar = new JButton("CANCELAR");

        btnModificar.addActionListener(e -> {

            if (marcaEncontrada == null) return;

            datosNuevos = new TMarca();
            datosNuevos.setId(marcaEncontrada.getId());

            // nombre opcional
            datosNuevos.setNombre(
                    txtNombre.getText().trim().isEmpty()
                            ? null
                            : txtNombre.getText().trim()
            );

            // ESPECIALIDADES SOLO SI CHECKED
            if (chkEspecialidades.isSelected()) {

                List<Especialidad> lista = new ArrayList<>();

                for (Map.Entry<Especialidad, JCheckBox> entry : checkBoxes.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        lista.add(entry.getKey());
                    }
                }

                datosNuevos.setEspecialidades(lista);
            } else {
                datosNuevos.setEspecialidades(null); // importante: no modificar
            }

            // confirmación (igual que empleado)
            VistaModificarConfirmarMarca dialog =
                    new VistaModificarConfirmarMarca(this, marcaEncontrada, datosNuevos);

            dialog.setVisible(true);

            if (dialog.isConfirmado()) {
                Controlador.getInstance().accion(Eventos.MODIFICAR_MARCA, datosNuevos);
            }
        });

        btnCancelar.addActionListener(e -> {
            limpiar();
            setVisible(false);
            dispose();
        });

        // FORMULARIO
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        form.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        form.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        form.add(chkEspecialidades, gbc);

        gbc.gridy = 3;
        form.add(panelEspecialidades, gbc);

        JPanel botones = new JPanel();
        botones.add(btnModificar);
        botones.add(btnCancelar);

        mainPanel.add(form);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(botones);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {

        switch (evento) {

            case Eventos.RES_BUSCAR_MARCA_PARA_MODIFICAR_OK:

                marcaEncontrada = (TMarca) datos;

                txtId.setText(String.valueOf(marcaEncontrada.getId()));
                txtId.setEditable(false);

                this.setVisible(true);
                this.pack();
                break;

            case Eventos.RES_MODIFICAR_MARCA_OK:
                JOptionPane.showMessageDialog(this, "Marca modificada correctamente.");
                dispose();
                break;

            case Eventos.RES_MODIFICAR_MARCA_KO_NO_EXISTE:
                JOptionPane.showMessageDialog(this, "La marca no existe.");
                break;

            case Eventos.RES_MODIFICAR_MARCA_KO_DATOS_INVALIDOS:
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
                break;
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");

        chkEspecialidades.setSelected(false);
        panelEspecialidades.setVisible(false);

        for (JCheckBox cb : checkBoxes.values()) {
            cb.setSelected(false);
        }
    }
}
