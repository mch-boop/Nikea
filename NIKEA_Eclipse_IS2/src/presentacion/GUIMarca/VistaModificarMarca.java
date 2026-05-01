package presentacion.GUIMarca;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import negocio.marca.TMarca;
import negocio.marca.TMarca.Especialidad;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaModificarMarca extends JFrame implements IGUI {

    // ===== ATRIBUTOS =====

    private JTextField txtId, txtNombre;
    private JTextField txtNombreAct;

    private JButton btnBuscar, btnModificar, btnCancelar, btnCancelarModif;

    private JPanel panelEdicion, pBotones;
    private TMarca marcaEncontrada;

    private JCheckBox chkEspecialidades;

    private JList<Especialidad> listaEspecialidades;
    private JScrollPane scrollEspecialidades;

    // ===== CONSTRUCTORA =====

    public VistaModificarMarca() {
        setTitle("Modificar Marca");
        initGUI();
    }

    // ===== UI =====

    private void initGUI() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // ===== BÚSQUEDA =====
        JPanel pBusqueda = new JPanel(new FlowLayout());

        txtId = new JTextField(20);
        btnBuscar = new JButton("BUSCAR");
        btnCancelar = new JButton("CANCELAR");

        pBusqueda.add(new JLabel("ID Marca:"));
        pBusqueda.add(txtId);
        pBusqueda.add(btnBuscar);
        pBusqueda.add(btnCancelar);

        btnBuscar.addActionListener(e -> {
            try {
                if (txtId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID obligatorio");
                    return;
                }

                int id = Integer.parseInt(txtId.getText().trim());
                pBotones.setVisible(false);
                Controlador.getInstance().accion(Eventos.BUSCAR_MARCA_PARA_MODIFICAR, id);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido");
            }
        });

        btnCancelar.addActionListener(e -> {
            panelEdicion.setVisible(false);
            txtId.setText("");
            txtId.setEditable(true);
            setVisible(false);
            dispose();
        });

        // ===== BOTONES GENERALES =====
        pBotones = new JPanel();
        pBotones.add(btnBuscar);
        pBotones.add(btnCancelar);

        crearPanelEdicion();

        JLabel titulo = new JLabel("Modificar Marca");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(titulo);
        mainPanel.add(pBusqueda);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(pBotones);
        mainPanel.add(panelEdicion);

        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }

    // ===== PANEL EDICIÓN =====

    private void crearPanelEdicion() {

        panelEdicion = new JPanel();
        panelEdicion.setLayout(new BoxLayout(panelEdicion, BoxLayout.Y_AXIS));
        panelEdicion.setBorder(BorderFactory.createTitledBorder("Datos Marca"));
        panelEdicion.setVisible(false);

        JPanel datos = new JPanel(new GridBagLayout());

        txtNombre = new JTextField(20);
        txtNombreAct = new JTextField(20);
        txtNombreAct.setEditable(false);

        chkEspecialidades = new JCheckBox("Modificar especialidades");

        // ===== LISTA MULTISELECT ENUM =====
        listaEspecialidades = new JList<>(Especialidad.values());
        listaEspecialidades.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaEspecialidades.setEnabled(false);

        scrollEspecialidades = new JScrollPane(listaEspecialidades);
        scrollEspecialidades.setPreferredSize(new Dimension(200, 100));

        chkEspecialidades.addActionListener(e -> {
            listaEspecialidades.setEnabled(chkEspecialidades.isSelected());
            if (!chkEspecialidades.isSelected()) {
                listaEspecialidades.clearSelection();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== NOMBRE =====
        gbc.gridy = 0;

        gbc.gridx = 0;
        datos.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        datos.add(txtNombreAct, gbc);

        gbc.gridx = 2;
        datos.add(txtNombre, gbc);

        // ===== CHECK =====
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        datos.add(chkEspecialidades, gbc);

        // ===== ESPECIALIDADES =====
        gbc.gridy = 2;
        gbc.gridwidth = 3;

        datos.add(scrollEspecialidades, gbc);

        // ===== BOTONES =====
        JPanel botones = new JPanel();

        btnModificar = new JButton("GUARDAR CAMBIOS");
        btnCancelarModif = new JButton("CANCELAR");

        btnModificar.addActionListener(e -> {

            if (marcaEncontrada == null) return;

            TMarca tm = new TMarca();
            tm.setId(marcaEncontrada.getId());

            // nombre
            if (!txtNombre.getText().trim().isEmpty()) {
                tm.setNombre(txtNombre.getText().trim());
            } else {
                tm.setNombre(null);
            }

            // especialidades
            if (chkEspecialidades.isSelected()) {

                List<Especialidad> seleccionadas = listaEspecialidades.getSelectedValuesList();
                tm.setEspecialidades(seleccionadas);

            } else {
                tm.setEspecialidades(null);
            }

            int res = JOptionPane.showConfirmDialog(this,
                    "¿Confirmar modificación?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (res == JOptionPane.YES_OPTION) {
                Controlador.getInstance().accion(Eventos.MODIFICAR_MARCA, tm);
            }
        });

        btnCancelarModif.addActionListener(e -> {
            txtId.setEditable(true);
            txtId.setText("");
            limpiar();
            panelEdicion.setVisible(false);
            pBotones.setVisible(true);
            pack();
        });

        botones.add(btnModificar);
        botones.add(btnCancelarModif);

        panelEdicion.add(datos);
        panelEdicion.add(botones);
    }

    // ===== ACTUALIZAR =====

    @Override
    public void actualizar(int evento, Object datos) {

        switch (evento) {

            case Eventos.RES_BUSCAR_MARCA_PARA_MODIFICAR_OK:

                marcaEncontrada = (TMarca) datos;

                txtNombreAct.setText(marcaEncontrada.getNombre());

                // preseleccionar especialidades actuales
                listaEspecialidades.clearSelection();

                if (marcaEncontrada.getEspecialidades() != null) {
                    for (Especialidad e : marcaEncontrada.getEspecialidades()) {
                        int index = e.ordinal();
                        listaEspecialidades.addSelectionInterval(index, index);
                    }
                }

                panelEdicion.setVisible(true);
                txtId.setEditable(false);
                pack();
                break;

            case Eventos.RES_MODIFICAR_MARCA_OK:

                JOptionPane.showMessageDialog(this,
                        "Marca modificada correctamente");

                panelEdicion.setVisible(false);
                txtId.setEditable(true);
                txtId.setText("");
                limpiar();
                pack();
                break;

            case Eventos.RES_MODIFICAR_MARCA_KO_NO_EXISTE:

                JOptionPane.showMessageDialog(this,
                        "La marca no existe",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;

            case Eventos.RES_MODIFICAR_MARCA_KO_DATOS_INVALIDOS:

                JOptionPane.showMessageDialog(this,
                        "Datos inválidos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void limpiar() {
        txtNombre.setText("");
        listaEspecialidades.clearSelection();
    }
}