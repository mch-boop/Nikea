package presentacion.GUIServicio;
 
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
 
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
 
import negocio.servicio.TArticulo;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
 
@SuppressWarnings("serial")
public class VistaMostrarMejorArticulo extends JDialog implements IGUI {
 
    private JTextArea areaDetalles;
    private JButton btnSalir;
 
    public VistaMostrarMejorArticulo() {
        super(null, "Mostrar Mejor Artículo", ModalityType.APPLICATION_MODAL);
        setTitle("Mostrar Mejor Artículo");
 
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initGUI();
        
        // ✅ Cargar datos cada vez que se abre la ventana
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                cargarMejorArticulo();
            }
        });
    }
 
    private void initGUI() {
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
 
        // ✅ Cambio: JTextArea en lugar de JTable
        areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);
        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalle del Mejor Artículo"));
        areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaDetalles.setPreferredSize(new Dimension(400, 200));
        
        // Lo meto en un JScrollPane para que se pueda ir scrolleando
        JScrollPane scroll = new JScrollPane(areaDetalles);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnSalir = new JButton("SALIR");
        panelBotones.add(btnSalir);
        
        // ✅ Cambio: Solo setVisible(false), no dispose()
        btnSalir.addActionListener(e -> {
            setVisible(false);
        });
 
        // Título
        JLabel lblTitulo = new JLabel("Datos del Mejor Artículo");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        // Agregar componentes al panel
        viewPanel.add(lblTitulo);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(scroll);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelBotones);
 
        getContentPane().add(viewPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
 
    public void cargarMejorArticulo() {
        areaDetalles.setText("");  // Limpiar antes de cargar
        Controlador.getInstance().accion(Eventos.MOSTRAR_MEJOR_ARTICULO, null);
    }
 
    @Override
    public void actualizar(int evento, Object datos) {
        switch (evento) {
 
            case Eventos.RES_MOSTRAR_MEJOR_ARTICULO_OK:
                TArticulo articulo = (TArticulo) datos;
                
                // ✅ Cambio: Formatear datos en StringBuilder (igual que VistaBuscarServicio)
                StringBuilder sb = new StringBuilder();
                sb.append(" ------------------------------------------ \n");
                sb.append("        DETALLES DEL MEJOR ARTÍCULO       \n");
                sb.append(" ------------------------------------------ \n");
                sb.append("ID:              ").append(articulo.getId()).append("\n");
                sb.append("Nombre:          ").append(articulo.getNombre()).append("\n");
                sb.append("Descripción:     ").append(articulo.getDescripcion()).append("\n");
                sb.append("Stock:           ").append(articulo.getStock()).append("\n");
                sb.append("Precio Actual:   ").append(articulo.getPrecioActual()).append("\n");
                sb.append("Marca:           ").append(articulo.getMarca() != null ? articulo.getMarca() : "-").append("\n");
                sb.append(" ------------------------------------------ \n");
                
                areaDetalles.setText(sb.toString());
                areaDetalles.setCaretPosition(0);
                setVisible(true);
                break;
 
            case Eventos.RES_MOSTRAR_MEJOR_ARTICULO_KO:
                areaDetalles.setText("");
                break;
 
            default:
                break;
        }
    }
}