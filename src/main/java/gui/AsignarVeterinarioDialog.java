package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import controladores.MascotaDAO;
import controladores.VeterinarioDAO;
import modelos.MascotaDTO;
import modelos.VeterinarioDTO;

public class AsignarVeterinarioDialog extends JDialog {

    private final JComboBox<Object> veterinarianCombo = new JComboBox<>();
    private final MascotaDAO masDAO = new MascotaDAO();
    private final VeterinarioDAO vetDAO = new VeterinarioDAO();
    private final MascotaDTO mascota;
    private final List<VeterinarioDTO> veterinarios = new ArrayList<>();
    private final Callback callback;

    public interface Callback {
        void onSaved(boolean saved);
    }

    public AsignarVeterinarioDialog(JFrame parent, MascotaDTO mascota, Callback callback) {
        super(parent, true);
        this.mascota = mascota;
        this.callback = callback;
        setTitle("Asignar veterinario a " + mascota.getNombre());
        setSize(400, 180);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel form = new JPanel(new GridLayout(2, 1, 8, 8));
        form.add(new JLabel("Veterinario:"));
        form.add(veterinarianCombo);

        loadVeterinarios();
        selectVeterinario(mascota.getVeterinarioId());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(e -> saveAsignacion());
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
                veterinarianCombo.addItem(vet.getId() + " - " + vet.getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar veterinarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selectVeterinario(Integer selectedId) {
        if (selectedId == null) {
            veterinarianCombo.setSelectedIndex(0);
            return;
        }
        for (int i = 2; i < veterinarianCombo.getItemCount(); i++) {
            String item = veterinarianCombo.getItemAt(i).toString();
            if (item.startsWith(selectedId + " - ")) {
                veterinarianCombo.setSelectedIndex(i);
                return;
            }
        }
        veterinarianCombo.setSelectedIndex(0);
    }

    private void saveAsignacion() {
        int selectedIndex = veterinarianCombo.getSelectedIndex();
        Integer veterinarioId = null;
        if (selectedIndex > 0) {
            veterinarioId = veterinarios.get(selectedIndex - 1).getId();
        }
        mascota.setVeterinarioId(veterinarioId);
        try {
            masDAO.updateMascota(mascota.getId(), mascota);
            closeDialog(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error asignando veterinario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeDialog(boolean saved) {
        callback.onSaved(saved);
        dispose();
    }
}
