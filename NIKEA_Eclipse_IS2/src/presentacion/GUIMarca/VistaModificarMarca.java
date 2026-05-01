package presentacion.GUIMarca;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import negocio.marca.TMarca;
import negocio.marca.TMarca.Especialidad;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaModificarMarca extends JDialog implements IGUI {

    // ATRIBUTOS

    private JTextField txtId, txtNombre, txtNombreAct;
    private JButton btnBuscar, btnModificar, btnCancelar;
    private JPanel panelEdicion, pBotones;
    private TMarca marcaEncontrada;

    // checkboxes
    private Map<Especialidad, JCheckBox> checkAct;
    private Map<Especialidad, JCheckBox> checkNueva;

    // CONSTRUCTOR

    public VistaModificarMarca() {
    	super(null, "Modificar Marca", ModalityType.APPLICATION_MODAL);
        setTitle("Modificar Marca");

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
    }


    // INIT 

    private void initGUI() {

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // búsqueda
        JPanel busqueda = new JPanel(new FlowLayout());

        txtId = new JTextField(20);
        btnBuscar = new JButton("BUSCAR");
        btnCancelar = new JButton("CANCELAR");

        busqueda.add(new JLabel("ID Marca:"));
        busqueda.add(txtId);
        busqueda.add(btnBuscar);
        busqueda.add(btnCancelar);

        btnBuscar.addActionListener(e -> {
            try {
                if (txtId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID obligatorio", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id = Integer.parseInt(txtId.getText().trim());
                pBotones.setVisible(false);

                Controlador.getInstance().accion( Eventos.BUSCAR_MARCA_PARA_MODIFICAR, id);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        pBotones = new JPanel();
        pBotones.add(btnBuscar);
        pBotones.add(btnCancelar);

        crearPanelEdicion();

        main.add(busqueda);
        main.add(pBotones);
        main.add(panelEdicion);

        add(main);
        pack();
        setLocationRelativeTo(null);
    }


    // panel de edición

    private void crearPanelEdicion() {

        panelEdicion = new JPanel();
        panelEdicion.setLayout(new BoxLayout(panelEdicion, BoxLayout.Y_AXIS));
        panelEdicion.setBorder(BorderFactory.createTitledBorder("Datos Marca"));
        panelEdicion.setVisible(false);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre = new JTextField(20);
        txtNombreAct = new JTextField(20);
        txtNombreAct.setEditable(false);

        checkAct = new HashMap<>();
        checkNueva = new HashMap<>();

        JPanel panelAct = new JPanel();
        panelAct.setLayout(new BoxLayout(panelAct, BoxLayout.Y_AXIS));
        panelAct.setBorder(BorderFactory.createTitledBorder("Actuales"));

        JPanel panelNueva = new JPanel();
        panelNueva.setLayout(new BoxLayout(panelNueva, BoxLayout.Y_AXIS));
        panelNueva.setBorder(BorderFactory.createTitledBorder("Nuevas"));

        // crear checkboxes por enum
        for (Especialidad e : Especialidad.values()) {

            JCheckBox c1 = new JCheckBox(e.toString());
            JCheckBox c2 = new JCheckBox(e.toString());

            c1.setEnabled(false); // actuales no editables

            checkAct.put(e, c1);
            checkNueva.put(e, c2);

            panelAct.add(c1);
            panelNueva.add(c2);
        }

        // nombre
        gbc.gridy = 0;
        gbc.gridx = 0; form.add(new JLabel("Nombre actual:"), gbc);
        gbc.gridx = 1; form.add(txtNombreAct, gbc);
        gbc.gridx = 2; form.add(txtNombre, gbc);

        // especialidades
        gbc.gridy = 1;
        gbc.gridx = 0; form.add(new JLabel("Especialidades:"), gbc);
        gbc.gridx = 1; form.add(panelAct, gbc);
        gbc.gridx = 2; form.add(panelNueva, gbc);

        // botones
        JPanel botones = new JPanel();

        btnModificar = new JButton("GUARDAR CAMBIOS");

        btnModificar.addActionListener(e -> {

            if (marcaEncontrada == null) return;
            TMarca tm = new TMarca();
            tm.setId(marcaEncontrada.getId());

            // nombre
            if (!txtNombre.getText().trim().isEmpty())
                tm.setNombre(txtNombre.getText().trim());
            else
                tm.setNombre(null);

            // especialidades nuevas
            List<Especialidad> lista = new ArrayList<>();
            for (Map.Entry<Especialidad, JCheckBox> entry : checkNueva.entrySet()) {
                if (entry.getValue().isSelected()) {
                    lista.add(entry.getKey());
                }
            }

            tm.setEspecialidades(lista.isEmpty() ? null : lista);

            // confirmación
            String info = "ID: " + marcaEncontrada.getId()
                    + "\nNombre: " + (tm.getNombre() != null ? tm.getNombre() : marcaEncontrada.getNombre())
                    + "\nEspecialidades:\n" + formatear(lista.isEmpty()
                            ? (List<Especialidad>) marcaEncontrada.getEspecialidades()
                            : lista);

            int res = JOptionPane.showConfirmDialog(this,
                    "¿Confirmar modificación?\n\n" + info,
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (res == JOptionPane.YES_OPTION) {
                Controlador.getInstance().accion(Eventos.MODIFICAR_MARCA, tm);
            }
        });

        botones.add(btnModificar);

        panelEdicion.add(form);
        panelEdicion.add(botones);
    }


    // actualizar

    @Override
    public void actualizar(int evento, Object datos) {

        switch (evento) {

            case Eventos.RES_BUSCAR_MARCA_PARA_MODIFICAR_OK:

                marcaEncontrada = (TMarca) datos;

                txtNombreAct.setText(marcaEncontrada.getNombre());

                // reset
                for (Especialidad e : Especialidad.values()) {
                    checkAct.get(e).setSelected(false);
                    checkNueva.get(e).setSelected(false);
                }

                // marcar actuales
                for (Especialidad e : marcaEncontrada.getEspecialidades()) {
                    checkAct.get(e).setSelected(true);
                    checkNueva.get(e).setSelected(true); // preselección
                }

                panelEdicion.setVisible(true);
                txtId.setEditable(false);
                pack();
                break;

            case Eventos.RES_MODIFICAR_MARCA_OK:

                JOptionPane.showMessageDialog(this, "Marca modificada correctamente");

                panelEdicion.setVisible(false);
                txtId.setText("");
                txtId.setEditable(true);
                pack();
                break;

            case Eventos.RES_MODIFICAR_MARCA_KO_NO_EXISTE:

                JOptionPane.showMessageDialog(this, "La marca no existe", "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
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

    // reseteo cada vez que abro la ventana

    @Override
    public void setVisible(boolean b) {
        if (b) limpiarCampos();
        super.setVisible(b);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtId.setEditable(true);

        txtNombre.setText("");
        txtNombreAct.setText("");

        marcaEncontrada = null;
        panelEdicion.setVisible(false);

        for (Especialidad e : Especialidad.values()) {
            checkAct.get(e).setSelected(false);
            checkNueva.get(e).setSelected(false);
        }
        if (pBotones != null) {
            pBotones.setVisible(true);
        }

        revalidate();
        repaint();
        pack();
    }
}