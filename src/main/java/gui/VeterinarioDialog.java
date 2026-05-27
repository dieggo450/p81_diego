package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controladores.VeterinarioDAO;
import modelos.VeterinarioDTO;

public class VeterinarioDialog extends JDialog {

    private final JTextField nifField = new JTextField(20);
    private final JTextField nombreField = new JTextField(20);
    private final JTextField direccionField = new JTextField(20);
    private final JTextField telefonoField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);

    private final VeterinarioDAO vetDAO = new VeterinarioDAO();
    private final VeterinarioDTO veterinario;
    private final Callback callback;

    public interface Callback {
        void onSaved(boolean saved);
    }

    public VeterinarioDialog(JFrame parent, VeterinarioDTO veterinario, Callback callback) {
        super(parent, true);
        this.veterinario = veterinario != null ? veterinario : new VeterinarioDTO();
        this.callback = callback;
        setTitle(veterinario == null ? "Nuevo veterinario" : "Editar veterinario");
        setSize(450, 300);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.add(new JLabel("NIF:"));
        form.add(nifField);
        form.add(new JLabel("Nombre:"));
        form.add(nombreField);
        form.add(new JLabel("Dirección:"));
        form.add(direccionField);
        form.add(new JLabel("Teléfono:"));
        form.add(telefonoField);
        form.add(new JLabel("Email:"));
        form.add(emailField);

        if (veterinario.getId() != 0) {
            nifField.setText(veterinario.getNif());
            nombreField.setText(veterinario.getNombre());
            direccionField.setText(veterinario.getDireccion());
            telefonoField.setText(veterinario.getTelefono());
            emailField.setText(veterinario.getEmail());
        }

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(e -> saveVeterinario());
        cancelButton.addActionListener(e -> closeDialog(false));

        buttons.add(saveButton);
        buttons.add(cancelButton);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void saveVeterinario() {
        if (nifField.getText().trim().isEmpty() || nombreField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIF y Nombre son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        veterinario.setNif(nifField.getText().trim());
        veterinario.setNombre(nombreField.getText().trim());
        veterinario.setDireccion(direccionField.getText().trim());
        veterinario.setTelefono(telefonoField.getText().trim());
        veterinario.setEmail(emailField.getText().trim());

        try {
            if (veterinario.getId() == 0) {
                vetDAO.insertVeterinario(veterinario);
            } else {
                vetDAO.updateVeterinario(veterinario.getId(), veterinario);
            }
            closeDialog(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error guardando veterinario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeDialog(boolean saved) {
        callback.onSaved(saved);
        dispose();
    }
}
