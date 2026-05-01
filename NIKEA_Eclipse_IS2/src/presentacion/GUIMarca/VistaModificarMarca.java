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
		main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Panel de búsqueda
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
					JOptionPane.showMessageDialog(this, "ID obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				int id = Integer.parseInt(txtId.getText().trim());
				pBotones.setVisible(false);

				Controlador.getInstance().accion(Eventos.BUSCAR_MARCA_PARA_MODIFICAR, id);

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
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
		panelEdicion.setLayout(new BorderLayout(10, 10));
		panelEdicion.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Edición de Marca"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		panelEdicion.setVisible(false);

		JPanel form = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Fila del nombre
        gbc.gridy = 1;
        gbc.gridx = 0;
        form.add(new JLabel("Nombre:"), gbc);

        txtNombreAct = new JTextField(15);
        txtNombreAct.setEditable(false);
        txtNombreAct.setBackground(new Color(240, 240, 240));
        gbc.gridx = 1;
        form.add(txtNombreAct, gbc);

        txtNombre = new JTextField(15);
        txtNombre.setToolTipText("Introduzca el nuevo nombre de la marca");
        gbc.gridx = 2;
        form.add(txtNombre, gbc);

        // Fila de especialidades
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        form.add(new JLabel("Especialidades:"), gbc);

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

		gbc.gridx = 1;
        form.add(panelAct, gbc);
        gbc.gridx = 2;
        form.add(panelNueva, gbc);

		// botones
		btnModificar = new JButton("GUARDAR CAMBIOS");

		btnModificar.addActionListener(e -> {

			if (marcaEncontrada == null)
				return;
			TMarca tm = new TMarca();
            tm.setId(marcaEncontrada.getId());
            tm.setNombre(txtNombre.getText().trim().isEmpty() ? null : txtNombre.getText().trim());

            List<Especialidad> lista = new ArrayList<>();
            for (Map.Entry<Especialidad, JCheckBox> entry : checkNueva.entrySet()) {
                if (entry.getValue().isSelected()) lista.add(entry.getKey());
            }
            tm.setEspecialidades(lista.isEmpty() ? null : lista);

            int res = JOptionPane.showConfirmDialog(this, "¿Confirmar modificación?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                Controlador.getInstance().accion(Eventos.MODIFICAR_MARCA, tm);
            }
		});

		JPanel pBotonGuardar = new JPanel();
        pBotonGuardar.add(btnModificar);

        panelEdicion.add(form, BorderLayout.CENTER);
        panelEdicion.add(pBotonGuardar, BorderLayout.SOUTH);
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
			}

			panelEdicion.setVisible(true);
			txtId.setEditable(false);
			pack();
			setLocationRelativeTo(null);
			break;

		case Eventos.RES_MODIFICAR_MARCA_OK:

			JOptionPane.showMessageDialog(this, "Marca modificada correctamente");

			panelEdicion.setVisible(false);
			txtId.setText("");
			txtId.setEditable(true);
			setLocationRelativeTo(null);
			pack();
			break;

		case Eventos.RES_MODIFICAR_MARCA_KO_NO_EXISTE:

			JOptionPane.showMessageDialog(this, "La marca no existe", "Error", JOptionPane.ERROR_MESSAGE);
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
		if (b)
			limpiarCampos();
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