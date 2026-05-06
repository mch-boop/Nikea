package presentacion.operacionResumenTOA;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import negocio.operacionTOA.TResumenNegocio;
import presentacion.IGUI;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaOperacionResumen extends JFrame implements IGUI {

	private JLabel lblFacturas, lblClientes, lblServicios, lblMarcas;

	public VistaOperacionResumen(JFrame parent) {
		setTitle("Estadísticas Mensuales");
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 245, 245));

		// Panel Central con Grid para los indicadores
		JPanel panelIndicadores = new JPanel(new GridLayout(2, 2, 20, 20));
		panelIndicadores.setOpaque(false);
		panelIndicadores.setBorder(new EmptyBorder(30, 30, 30, 30));

		// Inicialización de etiquetas de conteo
		lblFacturas = crearCardIndicador(panelIndicadores, "Facturas Emitidas");
		lblClientes = crearCardIndicador(panelIndicadores, "Clientes Atendidos");
		lblServicios = crearCardIndicador(panelIndicadores, "Servicios Realizados");
		lblMarcas = crearCardIndicador(panelIndicadores, "Marcas Implicadas");

		add(panelIndicadores, BorderLayout.CENTER);

		// Botón Cerrar
		JButton btnCerrar = new JButton("ACEPTAR");
		btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
		btnCerrar.addActionListener(e -> dispose());
		
		JPanel panelSur = new JPanel();
		panelSur.setOpaque(false);
		panelSur.add(btnCerrar);
		add(panelSur, BorderLayout.SOUTH);

		setSize(500, 400);
		setLocationRelativeTo(null);
	}

	// Método auxiliar para crear "tarjetas" de datos
	private JLabel crearCardIndicador(JPanel parent, String titulo) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
		
		JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.ITALIC, 12));
		
		JLabel lblValor = new JLabel("0", SwingConstants.CENTER);
		lblValor.setFont(new Font("Arial Black", Font.PLAIN, 28));
		lblValor.setForeground(new Color(0, 81, 158)); // Tu azul Nikea

		card.add(lblTitulo, BorderLayout.NORTH);
		card.add(lblValor, BorderLayout.CENTER);
		parent.add(card);
		
		return lblValor;
	}

	@Override
	public void actualizar(int evento, Object datos) {
		SwingUtilities.invokeLater(() -> {
			switch (evento) {
				case Eventos.RES_RESUMEN_MENSUAL_OK:
					TResumenNegocio resumen = (TResumenNegocio) datos;
					
					// Actualizamos los textos con el tamaño de las colecciones
					lblFacturas.setText(String.valueOf(resumen.getTotalFacturas()));
					lblClientes.setText(String.valueOf(resumen.getTotalClientes()));
					lblServicios.setText(String.valueOf(resumen.getTotalServicios()));
					lblMarcas.setText(String.valueOf(resumen.getTotalMarcas()));
					
					setVisible(true);
					break;
					
				case Eventos.RES_RESUMEN_MENSUAL_KO:
					JOptionPane.showMessageDialog(this, "No hay datos para el periodo seleccionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
					break;
			}
		});
	}
}