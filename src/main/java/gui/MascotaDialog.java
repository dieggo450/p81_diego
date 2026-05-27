package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controladores.MascotaDAO;
import controladores.VeterinarioDAO;
import modelos.MascotaDTO;
import modelos.VeterinarioDTO;

public class MascotaDialog extends JDialog {

    private final JTextField chipField = new JTextField(20);
    private final JTextField nombreField = new JTextField(20);
    private final JTextField pesoField = new JTextField(20);
    private final JTextField fechaField = new JTextField(20);
    private final JComboBox<String> tipoCombo = new JComboBox<>(new String[] { "perro", "gato", "otros" });
    private final JComboBox<Object> veterinarianCombo = new JComboBox<>();

    private final MascotaDAO masDAO = new MascotaDAO();
    private final VeterinarioDAO vetDAO = new VeterinarioDAO();
    private final MascotaDTO mascota;
    private final Callback callback;
    private final List<VeterinarioDTO> veterinarios = new ArrayList<>();

    public interface Callback {
        void onSaved(boolean saved);
    }

    public MascotaDialog(JFrame parent, MascotaDTO mascota, Callback callback) {
        super(parent, true);
        this.mascota = mascota != null ? mascota : new MascotaDTO();
        this.callback = callback;
        setTitle(mascota == null ? "Nueva mascota" : "Editar mascota");
        setSize(500, 350);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        form.add(new JLabel("Chip:"));
        form.add(chipField);
        form.add(new JLabel("Nombre:"));
        form.add(nombreField);
        form.add(new JLabel("Peso:"));
        form.add(pesoField);
        form.add(new JLabel("Fecha nacimiento (YYYY-MM-DD):"));
        form.add(fechaField);
        form.add(new JLabel("Tipo:"));
        form.add(tipoCombo);
        form.add(new JLabel("Veterinario:"));
        form.add(veterinarianCombo);

        loadVeterinarios();

        if (mascota.getId() != 0) {
            chipField.setText(mascota.getChip());
            nombreField.setText(mascota.getNombre());
            pesoField.setText(String.valueOf(mascota.getPeso()));
            fechaField.setText(mascota.getFechaNacimiento() != null ? mascota.getFechaNacimiento().toString() : "");
            tipoCombo.setSelectedItem(mascota.getTipo());
            selectVeterinario(mascota.getVeterinarioId());
        }

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(e -> saveMascota());
        cancelButton.addActionListener(e -> closeDialog(false));

        buttons.add(saveButton);
        buttons.add(cancelButton);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void loadVeterinarios() {
        veterinarianCombo.removeAllItems();
        veterinarianCombo.addItem("Ninguno");
        try {
            for (VeterinarioDTO vet : vetDAO.getAll()) {
                veterinarios.add(vet);
                veterinarianCombo.addItem(formatVeterinario(vet));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar veterinarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatVeterinario(VeterinarioDTO vet) {
        return vet.getId() + " - " + vet.getNombre();
    }

    private void selectVeterinario(Integer selectedId) {
        if (selectedId == null) {
            veterinarianCombo.setSelectedIndex(0);
            return;
        }
        for (int i = 0; i < veterinarianCombo.getItemCount(); i++) {
            Object item = veterinarianCombo.getItemAt(i);
            if (item instanceof String) continue;
            if (item.toString().startsWith(selectedId + " - ")) {
                veterinarianCombo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void saveMascota() {
        if (chipField.getText().trim().isEmpty() || nombreField.getText().trim().isEmpty() || fechaField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chip, Nombre y Fecha son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double peso;
        try {
            peso = Double.parseDouble(pesoField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Peso debe ser un número válido.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaField.getText().trim());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Fecha inválida. Usa formato YYYY-MM-DD.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mascota.setChip(chipField.getText().trim());
        mascota.setNombre(nombreField.getText().trim());
        mascota.setPeso(peso);
        mascota.setFechaNacimiento(fecha);
        mascota.setTipo((String) tipoCombo.getSelectedItem());
        mascota.setVeterinarioId(extractSelectedVeterinarioId());

        try {
            if (mascota.getId() == 0) {
                masDAO.insertMascota(mascota);
            } else {
                masDAO.updateMascota(mascota.getId(), mascota);
            }
            closeDialog(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error guardando mascota: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Integer extractSelectedVeterinarioId() {
        int selectedIndex = veterinarianCombo.getSelectedIndex();
        if (selectedIndex <= 0) {
            return null;
        }
        int vetIndex = selectedIndex - 1;
        if (vetIndex >= 0 && vetIndex < veterinarios.size()) {
            return veterinarios.get(vetIndex).getId();
        }
        return null;
    }

    private void closeDialog(boolean saved) {
        callback.onSaved(saved);
        dispose();
    }
}
