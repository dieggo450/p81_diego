package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import controladores.MascotaDAO;
import controladores.VeterinarioDAO;
import modelos.MascotaDTO;
import modelos.VeterinarioDTO;

public class MainFrame extends JFrame {

    private final VeterinarioDAO vetDAO = new VeterinarioDAO();
    private final MascotaDAO masDAO = new MascotaDAO();

    private final DefaultTableModel vetTableModel = new DefaultTableModel(
            new Object[] { "ID", "NIF", "Nombre", "Dirección", "Teléfono", "Email" }, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final DefaultTableModel mascotaTableModel = new DefaultTableModel(
            new Object[] { "ID", "Chip", "Nombre", "Peso", "Fecha", "Tipo", "Veterinario" }, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable vetTable = new JTable(vetTableModel);
    private final JTable mascotaTable = new JTable(mascotaTableModel);

    public MainFrame() {
        setTitle("Gestión de Clínica Veterinaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Veterinarios", createVeterinarioPanel());
        tabs.addTab("Mascotas", createMascotaPanel());

        add(tabs, BorderLayout.CENTER);

        refreshVetTable();
        refreshMascotaTable();
    }

    private JPanel createVeterinarioPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        vetTable.setPreferredScrollableViewportSize(new Dimension(800, 300));
        panel.add(new JScrollPane(vetTable), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton addButton = new JButton("Nuevo");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Borrar");
        JButton refreshButton = new JButton("Actualizar");

        addButton.addActionListener(e -> openVeterinarioDialog(null));
        editButton.addActionListener(e -> editSelectedVeterinario());
        deleteButton.addActionListener(e -> deleteSelectedVeterinario());
        refreshButton.addActionListener(e -> refreshVetTable());

        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);
        buttons.add(refreshButton);

        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createMascotaPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mascotaTable.setPreferredScrollableViewportSize(new Dimension(800, 300));
        panel.add(new JScrollPane(mascotaTable), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(1, 5, 10, 10));
        JButton addButton = new JButton("Nuevo");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Borrar");
        JButton assignButton = new JButton("Asignar veterinario");
        JButton refreshButton = new JButton("Actualizar");

        addButton.addActionListener(e -> openMascotaDialog(null));
        editButton.addActionListener(e -> editSelectedMascota());
        deleteButton.addActionListener(e -> deleteSelectedMascota());
        assignButton.addActionListener(e -> assignVeterinarioToSelectedMascota());
        refreshButton.addActionListener(e -> refreshMascotaTable());

        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);
        buttons.add(assignButton);
        buttons.add(refreshButton);

        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshVetTable() {
        SwingUtilities.invokeLater(() -> {
            vetTableModel.setRowCount(0);
            try {
                List<VeterinarioDTO> lista = vetDAO.getAll();
                for (VeterinarioDTO v : lista) {
                    vetTableModel.addRow(new Object[] { v.getId(), v.getNif(), v.getNombre(), v.getDireccion(), v.getTelefono(), v.getEmail() });
                }
            } catch (Exception e) {
                showError("No se pudieron cargar los veterinarios: " + e.getMessage());
            }
        });
    }

    private void refreshMascotaTable() {
        SwingUtilities.invokeLater(() -> {
            mascotaTableModel.setRowCount(0);
            try {
                List<MascotaDTO> lista = masDAO.getAll();
                for (MascotaDTO m : lista) {
                    String vetNombre = "";
                    if (m.getVeterinarioId() != null) {
                        VeterinarioDTO vet = vetDAO.findByPk(m.getVeterinarioId());
                        vetNombre = vet != null ? vet.getNombre() : String.valueOf(m.getVeterinarioId());
                    }
                    mascotaTableModel.addRow(new Object[] { m.getId(), m.getChip(), m.getNombre(), m.getPeso(), m.getFechaNacimiento(), m.getTipo(), vetNombre });
                }
            } catch (Exception e) {
                showError("No se pudieron cargar las mascotas: " + e.getMessage());
            }
        });
    }

    private void openVeterinarioDialog(VeterinarioDTO current) {
        JDialog dialog = new VeterinarioDialog(this, current, saved -> {
            if (saved) {
                refreshVetTable();
                refreshMascotaTable();
            }
        });
        dialog.setVisible(true);
    }

    private void editSelectedVeterinario() {
        int row = vetTable.getSelectedRow();
        if (row < 0) {
            showInfo("Selecciona un veterinario para editar.");
            return;
        }
        int id = (int) vetTableModel.getValueAt(row, 0);
        try {
            VeterinarioDTO vet = vetDAO.findByPk(id);
            if (vet != null) {
                openVeterinarioDialog(vet);
            }
        } catch (Exception e) {
            showError("No se pudo cargar el veterinario: " + e.getMessage());
        }
    }

    private void deleteSelectedVeterinario() {
        int row = vetTable.getSelectedRow();
        if (row < 0) {
            showInfo("Selecciona un veterinario para borrar.");
            return;
        }
        int id = (int) vetTableModel.getValueAt(row, 0);
        try {
            VeterinarioDTO vet = vetDAO.findByPk(id);
            if (vet != null) {
                int option = JOptionPane.showConfirmDialog(this, "¿Borrar veterinario?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    vetDAO.deleteVeterinario(vet);
                    refreshVetTable();
                    refreshMascotaTable();
                }
            }
        } catch (Exception e) {
            showError("No se pudo borrar el veterinario: " + e.getMessage());
        }
    }

    private void openMascotaDialog(MascotaDTO current) {
        JDialog dialog = new MascotaDialog(this, current, saved -> {
            if (saved) {
                refreshMascotaTable();
            }
        });
        dialog.setVisible(true);
    }

    private void editSelectedMascota() {
        int row = mascotaTable.getSelectedRow();
        if (row < 0) {
            showInfo("Selecciona una mascota para editar.");
            return;
        }
        int id = (int) mascotaTableModel.getValueAt(row, 0);
        try {
            MascotaDTO mascota = masDAO.findByPk(id);
            if (mascota != null) {
                openMascotaDialog(mascota);
            }
        } catch (Exception e) {
            showError("No se pudo cargar la mascota: " + e.getMessage());
        }
    }

    private void deleteSelectedMascota() {
        int row = mascotaTable.getSelectedRow();
        if (row < 0) {
            showInfo("Selecciona una mascota para borrar.");
            return;
        }
        int id = (int) mascotaTableModel.getValueAt(row, 0);
        try {
            MascotaDTO mascota = masDAO.findByPk(id);
            if (mascota != null) {
                int option = JOptionPane.showConfirmDialog(this, "¿Borrar mascota?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    masDAO.deleteMascota(mascota);
                    refreshMascotaTable();
                }
            }
        } catch (Exception e) {
            showError("No se pudo borrar la mascota: " + e.getMessage());
        }
    }

    private void assignVeterinarioToSelectedMascota() {
        int row = mascotaTable.getSelectedRow();
        if (row < 0) {
            showInfo("Selecciona una mascota para asignar un veterinario.");
            return;
        }
        int id = (int) mascotaTableModel.getValueAt(row, 0);
        try {
            MascotaDTO mascota = masDAO.findByPk(id);
            if (mascota != null) {
                JDialog dialog = new AsignarVeterinarioDialog(this, mascota, saved -> {
                    if (saved) {
                        refreshMascotaTable();
                    }
                });
                dialog.setVisible(true);
            }
        } catch (Exception e) {
            showError("No se pudo abrir el diálogo de asignación: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
