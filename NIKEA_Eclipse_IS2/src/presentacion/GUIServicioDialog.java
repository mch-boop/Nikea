package presentacion;

import javax.swing.*;
import java.awt.*;

import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIServicioDialog extends JDialog {

	// CONSTRUCTORA

	public GUIServicioDialog(JFrame owner) {
		super(owner, "Gestión de Servicios", false);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		initGUI();

		pack();
		setLocationRelativeTo(null);
	}

	// MÉTODOS

	private void initGUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// BOTONES
		JButton btnAlta = new JButton("Alta Servicio");
		JButton btnBaja = new JButton("Baja Servicio");
		JButton btnModificar = new JButton("Modificar Servicio");
		JButton btnBuscar = new JButton("Buscar Servicio");
		JButton btnMostrar = new JButton("Listar Servicios");
		JButton btnMejor = new JButton("Mejor Artículo");
		JButton btnArticulosMarca = new JButton("Artículos por Marca");

		JButton[] botones = { btnAlta, btnBaja, btnModificar, btnBuscar, btnMostrar, btnMejor, btnArticulosMarca };

		Dimension size = new Dimension(220, 40);

		for (JButton b : botones) {
			b.setFocusPainted(false);
			b.setPreferredSize(size);
			b.setMaximumSize(size);
			b.setMinimumSize(size);
		}

		// FILA 1 (3 botones)
		JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		fila1.add(btnAlta);
		fila1.add(btnBaja);
		fila1.add(btnModificar);

		// FILA 2 (3 botones)
		JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		fila2.add(btnBuscar);
		fila2.add(btnMostrar);
		fila2.add(btnMejor);

		// FILA 3 (1 centrado)
		JPanel fila3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		fila3.add(btnArticulosMarca);

		mainPanel.add(fila1);
		mainPanel.add(fila2);
		mainPanel.add(fila3);

		add(mainPanel, BorderLayout.CENTER);

		// LISTENERS

		btnAlta.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_SERVICIO);
			abrirVistaBloqueante((JDialog) vista);
		});

		btnBaja.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_SERVICIO);
			abrirVistaBloqueante((JDialog) vista);
		});

		btnModificar.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_SERVICIO);
			abrirVistaBloqueante((JDialog) vista);
		});

		btnBuscar.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_SERVICIO);
			abrirVistaBloqueante((JDialog) vista);
		});

		btnMostrar.addActionListener(e -> {
			Controlador.getInstance().accion(Eventos.MOSTRAR_SERVICIOS, null);
		});

		btnMejor.addActionListener(e -> {
			Controlador.getInstance().accion(Eventos.MOSTRAR_MEJOR_ARTICULO, null);
		});

		btnArticulosMarca.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_ARTICULOS_POR_MARCA);
			abrirVistaBloqueante((JDialog) vista);
		});
	}

	private void abrirVistaBloqueante(JDialog vista) {
		vista.setModal(true);
		vista.setVisible(true);
	}
}