package presentacion.GUIMarca;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import negocio.marca.TMarca;
import negocio.marca.TMarca.Especialidad;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;


@SuppressWarnings("serial")
public class VistaModificarMarca extends JFrame implements IGUI {
	
	// ATRIBUTOS

    private TMarca marcaActual;

    // IZQUIERDA (ANTIGUO)
    private JLabel lblNombreAnt;
    private JLabel lblEspAnt;

    // DERECHA (NUEVO)
    private JTextField txtNombreNuevo;
    private Map<Especialidad, JCheckBox> checkBoxes;

    private JButton btnGuardar;
    private JButton btnCancelar;

    // CONSTRUCTOR 

    public VistaModificarMarca() {
        setTitle("Modificar Marca");
        initGUI();
    }

    // INICIALIZACION

    private void initGUI() {

        setLayout(new GridLayout(1, 2));

        // izquierda
        JPanel panelAntiguo = new JPanel();
        panelAntiguo.setLayout(new BoxLayout(panelAntiguo, BoxLayout.Y_AXIS));
        panelAntiguo.setBorder(BorderFactory.createTitledBorder("Datos actuales"));

        lblNombreAnt = new JLabel();
        lblEspAnt = new JLabel();

        panelAntiguo.add(lblNombreAnt);
        panelAntiguo.add(lblEspAnt);

        // derecha
        JPanel panelNuevo = new JPanel();
        panelNuevo.setLayout(new BoxLayout(panelNuevo, BoxLayout.Y_AXIS));
        panelNuevo.setBorder(BorderFactory.createTitledBorder("Nuevos datos"));

        txtNombreNuevo = new JTextField(20);

        panelNuevo.add(new JLabel("Nombre:"));
        panelNuevo.add(txtNombreNuevo);

        checkBoxes = new HashMap<>();

        JPanel panelEsp = new JPanel();
        panelEsp.setLayout(new BoxLayout(panelEsp, BoxLayout.Y_AXIS));
        panelEsp.setBorder(BorderFactory.createTitledBorder("Especialidades"));

        for (Especialidad e : Especialidad.values()) {
            JCheckBox cb = new JCheckBox(e.toString());
            checkBoxes.put(e, cb);
            panelEsp.add(cb);
        }

        panelNuevo.add(panelEsp);

        // botones
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        panelNuevo.add(panelBotones);

        add(panelAntiguo);
        add(panelNuevo);

        // ACCIONES 

        btnGuardar.addActionListener(e -> {

            if (marcaActual == null) return;

            List<Especialidad> nuevas = new ArrayList<>();

            for (Map.Entry<Especialidad, JCheckBox> entry : checkBoxes.entrySet()) {
                if (entry.getValue().isSelected()) {
                    nuevas.add(entry.getKey());
                }
            }

            TMarca m = new TMarca();
            m.setId(marcaActual.getId());
            m.setNombre(txtNombreNuevo.getText());
            m.setEspecialidades(nuevas);
            m.setActivo(true);

            Controlador.getInstance().accion(
                    Eventos.MODIFICAR_MARCA,
                    m
            );
        });

        btnCancelar.addActionListener(e -> dispose());

        setSize(700, 400);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {

        switch (evento) {

            case Eventos.RES_ABRIR_MODIFICAR_MARCA_OK:
                cargarDatos((TMarca) datos);
                break;

            case Eventos.RES_MODIFICAR_MARCA_OK:
                JOptionPane.showMessageDialog(this, "Marca modificada");
                break;

            case Eventos.RES_MODIFICAR_MARCA_KO:
                JOptionPane.showMessageDialog(this, "Error al modificar");
                break;
        }
    }

    private void cargarDatos(TMarca m) {

        this.marcaActual = m;

        lblNombreAnt.setText("Nombre: " + m.getNombre());
        lblEspAnt.setText("Especialidades: " + m.getEspecialidades());

        txtNombreNuevo.setText(m.getNombre());

        for (JCheckBox cb : checkBoxes.values()) {
            cb.setSelected(false);
        }

        for (Especialidad e : m.getEspecialidades()) {
            checkBoxes.get(e).setSelected(true);
        }
    }

}
