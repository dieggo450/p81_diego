package vistas;

import java.util.List;
import java.util.Scanner;

import controladores.MascotaDAO;
import controladores.VeterinarioDAO;
import modelos.MascotaDTO;
import modelos.VeterinarioDTO;

public class Programa {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        VeterinarioDAO vetDAO = new VeterinarioDAO();
        MascotaDAO masDAO = new MascotaDAO();

        int opcion;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Gestionar veterinarios");
            System.out.println("2. Gestionar mascotas");
            System.out.println("3. Asignar mascota a veterinario");
            System.out.println("4. Ver mascotas por veterinario");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();

            try {

                switch (opcion) {

                    // =========================
                    // VETERINARIOS
                    // =========================
                    case 1:
                        System.out.println("\n--- VETERINARIOS ---");
                        for (VeterinarioDTO v : vetDAO.getAll()) {
                            System.out.println(v.getId() + " - " + v.getNombre());
                        }
                        break;

                    // =========================
                    // MASCOTAS
                    // =========================
                    case 2:
                        System.out.println("\n--- MASCOTAS ---");
                        for (MascotaDTO m : masDAO.getAll()) {
                            System.out.println(m.getId() + " - " + m.getNombre() + " (Vet: " + m.getVeterinarioId() + ")");
                        }
                        break;

                    // =========================
                    // ASIGNAR MASCOTA A VET
                    // =========================
                    case 3:

                        System.out.print("ID mascota: ");
                        int idMascota = sc.nextInt();

                        System.out.print("ID veterinario: ");
                        int idVet = sc.nextInt();

                        MascotaDTO mascota = masDAO.findByPk(idMascota);

                        if (mascota != null) {
                            mascota.setVeterinarioId(idVet);
                            masDAO.updateMascota(idMascota, mascota);
                            System.out.println("Mascota asignada correctamente");
                        } else {
                            System.out.println("Mascota no encontrada");
                        }

                        break;

                    // =========================
                    // VER MASCOTAS POR VET
                    // =========================
                    case 4:

                        System.out.print("ID veterinario: ");
                        int vetId = sc.nextInt();

                        List<MascotaDTO> lista = masDAO.getByMascotaId(vetId);

                        if (lista.isEmpty()) {
                            System.out.println("No hay mascotas para este veterinario");
                        } else {
                            for (MascotaDTO m : lista) {
                                System.out.println(m.getNombre() + " - " + m.getTipo());
                            }
                        }

                        break;

                    case 0:
                        System.out.println("Saliendo...");
                        break;

                    default:
                        System.out.println("Opción no válida");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } while (opcion != 0);

        sc.close();
    }
}
