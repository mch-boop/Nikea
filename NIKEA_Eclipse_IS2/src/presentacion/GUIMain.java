package presentacion;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class GUIMain extends JFrame {

	// CONSTANTES
	
	private final Color AZUL_NIKEA = new Color(0, 81, 158);
	private final Color AMARILLO_NIKEA = new Color(255, 218, 26);
	private final Color FONDO = new Color(15, 35, 65);
	private final Color TEXTO_FOOTER = new Color(120, 130, 150);
	
	// CONSTRUCTORA
	
	public GUIMain() {
		super("[NIKEA - IS2]");
		initGUI();
	}
	
	// MÉTODOS
	
	private void initGUI() {
		
		// Configuración de la ventana
		setMinimumSize(new Dimension(850, 650));
		setSize(1000, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setBackground(FONDO);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// CREACIÓN DE COMPONENTES

		// Botones 
		JButton btnClientes   = crearBotonNikea("CLIENTE");
		JButton btnEmpleados  = crearBotonNikea("EMPLEADO");
		JButton btnFacturas   = crearBotonNikea("FACTURA");
		JButton btnServicios  = crearBotonNikea("SERVICIO");
		JButton btnMarcas     = crearBotonNikea("MARCA");
		JButton btnDescuentos = crearBotonNikea("DESCUENTO");

		// Logo central 
		ImagePanel logoPanel = new ImagePanel("resources/logo.png");

		// LISTENERS (FUNCIONALIDAD)

		btnClientes.addActionListener(e -> new GUIClienteDialog(this).setVisible(true));
		btnEmpleados.addActionListener(e -> new GUIEmpleadoDialog(this).setVisible(true));
		btnFacturas.addActionListener(e -> new GUIFacturaDialog(this).setVisible(true));
		btnServicios.addActionListener(e -> new GUIServicioDialog(this).setVisible(true));
		btnMarcas.addActionListener(e -> new GUIMarcaDialog(this).setVisible(true));
		btnDescuentos.addActionListener(e -> new GUIDescuentoDialog(this).setVisible(true));

		// ORGANIZACIÓN DEL LAYOUT

		// Fila Superior
		JPanel rowTop = new JPanel(new GridLayout(1, 3, 30, 0));
		rowTop.setOpaque(false);
		rowTop.setBorder(new EmptyBorder(20, 50, 20, 50));
		rowTop.add(btnClientes);
		rowTop.add(btnEmpleados);
		rowTop.add(btnFacturas);

		gbc.gridx = 0; gbc.gridy = 0;
		gbc.weightx = 1.0; gbc.weighty = 0.2;
		gbc.fill = GridBagConstraints.BOTH;
		add(rowTop, gbc);

		// Logo Central 
		gbc.gridy = 1;
		gbc.weighty = 0.4;
		gbc.insets = new Insets(30, 100, 30, 100);
		add(logoPanel, gbc);

		// Fila Inferior
		JPanel rowBottom = new JPanel(new GridLayout(1, 3, 30, 0));
		rowBottom.setOpaque(false);
		rowBottom.setBorder(new EmptyBorder(20, 50, 20, 50));
		rowBottom.add(btnServicios);
		rowBottom.add(btnMarcas);
		rowBottom.add(btnDescuentos);

		gbc.gridy = 2;
		gbc.weighty = 0.2;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(rowBottom, gbc);

		// Footer
		JLabel footer = new JLabel("NIKEA | Proyecto de Ingeniería del Software II", SwingConstants.CENTER);
		footer.setForeground(TEXTO_FOOTER);
		footer.setFont(new Font("Verdana", Font.PLAIN, 12));
		gbc.gridy = 3;
		gbc.weighty = 0.1;
		add(footer, gbc);
	}

	// MÉTODOS AUXILIARES DE ESTILO
	
	private JButton crearBotonNikea(String texto) {
		JButton boton = new JButton(texto) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				if (getModel().isPressed()) {
					g2.setColor(AZUL_NIKEA.darker());
				} else if (getModel().isRollover()) {
					g2.setColor(new Color(20, 100, 185));
				} else {
					g2.setColor(AZUL_NIKEA);
				}
				
				int arc = Math.min(getWidth(), getHeight()) / 2;
				g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));
				g2.dispose();
				super.paintComponent(g);
			}
		};

		boton.setContentAreaFilled(false);
		boton.setFocusPainted(false);
		boton.setBorderPainted(false);
		boton.setForeground(AMARILLO_NIKEA);
		boton.setFont(new Font("Arial Black", Font.PLAIN, 16));
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		return boton;
	}

	// CLASE INTERNA PARA EL LOGO
	
	class ImagePanel extends JPanel {
		private Image img;

		public ImagePanel(String path) {
			this.img = new ImageIcon(path).getImage();
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (img != null) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				int iw = img.getWidth(this);
				int ih = img.getHeight(this);
				
				double ratio = Math.min((double) getWidth() / iw, (double) getHeight() / ih);
				
				// Logo pequeño: limitado al 80% de su tamaño original
				ratio = Math.min(ratio, 0.8); 

				int nw = (int) (iw * ratio);
				int nh = (int) (ih * ratio);

				int x = (getWidth() - nw) / 2;
				int y = (getHeight() - nh) / 2;

				g2.drawImage(img, x, y, nw, nh, this);
				g2.dispose();
			}
		}
	}
}