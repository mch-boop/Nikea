package presentacion.GUIMarca;

import javax.swing.*;
import java.awt.*;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaModificarBuscarMarca extends JFrame implements IGUI {

    private JTextField txtId;

    public VistaModificarBuscarMarca() {
        setTitle("Buscar Marca");
        initGUI();
    }

    private void initGUI() {

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Introduzca el ID de la marca");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtId = new JTextField(15);

        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(new JLabel("ID:"));
        p.add(txtId);

        JButton btn = new JButton("BUSCAR");
        JButton cancel = new JButton("CANCELAR");

        btn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                Controlador.getInstance().accion(Eventos.BUSCAR_MARCA_PARA_MODIFICAR, id);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido");
            }
        });

        cancel.addActionListener(e -> {
            limpiar();
            setVisible(false);
        });

        JPanel bot = new JPanel();
        bot.add(btn);
        bot.add(cancel);

        main.add(titulo);
        main.add(Box.createVerticalStrut(15)); // 👈 separación
        main.add(p);
        main.add(Box.createVerticalStrut(10));
        main.add(bot);

        add(main);

        pack();
        setLocationRelativeTo(null);
    }

    private void limpiar() {
        txtId.setText("");
    }

    @Override
    public void actualizar(int evento, Object datos) {
        if (evento == Eventos.RES_BUSCAR_MARCA_PARA_MODIFICAR_KO) {
            JOptionPane.showMessageDialog(this, "No existe la marca");
        }
    }
}