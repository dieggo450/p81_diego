package vistas;

import java.time.LocalDate;
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
            sc.nextLine();

            try {
                switch (opcion) {
                    case 1:
                        gestionarVeterinarios(sc, vetDAO);
                        break;

                    case 2:
                        gestionarMascotas(sc, masDAO, vetDAO);
                        break;

                    case 3:
                        asignarMascotaAVeterinario(sc, masDAO);
                        break;

                    case 4:
                        verMascotasPorVeterinario(sc, masDAO);
                        break;

                    case 0:
                        System.out.println("Saliendo...");
                        break;

                    default:
                        System.out.println("Opción no válida");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);

        sc.close();
    }

    // (helpers removed: using Scanner.nextInt()/nextDouble() directly)

    // -------------------- Veterinarios --------------------

    private static void gestionarVeterinarios(Scanner sc, VeterinarioDAO vetDAO) throws Exception {
        System.out.println("\n--- VETERINARIOS ---");
        System.out.println("1. Listar");
        System.out.println("2. Añadir");
        System.out.println("3. Modificar");
        System.out.println("4. Borrar");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        int op = sc.nextInt();
        sc.nextLine();

        switch (op) {
            case 1:
                for (VeterinarioDTO v : vetDAO.getAll()) {
                    System.out.println(v.getId() + " - " + v.getNombre());
                }
                break;

            case 2:
                System.out.print("NIF: ");
                String nif = sc.nextLine();
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Dirección: ");
                String direccion = sc.nextLine();
                System.out.print("Teléfono: ");
                String telefono = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();

                VeterinarioDTO nuevo = new VeterinarioDTO(0, nif, nombre, direccion, telefono, email);
                int inserted = vetDAO.insertVeterinario(nuevo);
                System.out.println(inserted > 0 ? "Veterinario añadido." : "No se pudo añadir veterinario.");
                break;

            case 3:
                System.out.print("ID a modificar: ");
                int idMod = sc.nextInt();
                sc.nextLine();
                VeterinarioDTO v = vetDAO.findByPk(idMod);
                if (v == null) {
                    System.out.println("No encontrado");
                    break;
                }
                System.out.println("Dejar vacío para mantener el valor actual.");
                System.out.print("NIF (" + v.getNif() + "): ");
                String nifN = sc.nextLine();
                System.out.print("Nombre (" + v.getNombre() + "): ");
                String nombreN = sc.nextLine();
                System.out.print("Dirección (" + v.getDireccion() + "): ");
                String direccionN = sc.nextLine();
                System.out.print("Teléfono (" + v.getTelefono() + "): ");
                String telefonoN = sc.nextLine();
                System.out.print("Email (" + v.getEmail() + "): ");
                String emailN = sc.nextLine();

                if (!nifN.isEmpty()) v.setNif(nifN);
                if (!nombreN.isEmpty()) v.setNombre(nombreN);
                if (!direccionN.isEmpty()) v.setDireccion(direccionN);
                if (!telefonoN.isEmpty()) v.setTelefono(telefonoN);
                if (!emailN.isEmpty()) v.setEmail(emailN);

                int updated = vetDAO.updateVeterinario(idMod, v);
                System.out.println(updated > 0 ? "Veterinario actualizado." : "No se pudo actualizar.");
                break;

            case 4:
                System.out.print("ID a borrar: ");
                int idDel = sc.nextInt();
                sc.nextLine();
                VeterinarioDTO vd = vetDAO.findByPk(idDel);
                if (vd == null) {
                    System.out.println("No encontrado");
                    break;
                }
                System.out.print("Confirma borrado (s/n): ");
                String conf = sc.nextLine();
                if (conf.equalsIgnoreCase("s")) {
                    int del = vetDAO.deleteVeterinario(vd);
                    System.out.println(del > 0 ? "Veterinario borrado." : "No se pudo borrar.");
                } else {
                    System.out.println("Borrado cancelado.");
                }
                break;

            case 0:
            default:
                break;
        }
    }

    // nextVetId removed - DB uses AUTO_INCREMENT

    // -------------------- Mascotas --------------------

    private static void gestionarMascotas(Scanner sc, MascotaDAO masDAO, VeterinarioDAO vetDAO) throws Exception {
        System.out.println("\n--- MASCOTAS ---");
        System.out.println("1. Listar");
        System.out.println("2. Añadir");
        System.out.println("3. Modificar");
        System.out.println("4. Borrar");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        int op = sc.nextInt();
        sc.nextLine();

        switch (op) {
            case 1:
                for (MascotaDTO m : masDAO.getAll()) {
                    String vetString = m.getVeterinarioId() != null ? m.getVeterinarioId().toString() : "0";
                    System.out.println(m.getId() + " - " + m.getNombre() + " (Vet: " + vetString + ")");
                }
                break;

            case 2:
                System.out.print("Chip: ");
                String chip = sc.nextLine();
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Peso: ");
                double peso = sc.nextDouble();
                sc.nextLine();
                System.out.print("Fecha nacimiento (YYYY-MM-DD): ");
                LocalDate fecha = LocalDate.parse(sc.nextLine());
                System.out.print("Tipo: ");
                String tipo = sc.nextLine();
                System.out.print("ID veterinario (0 = ninguno): ");
                int vetIdVal = sc.nextInt();
                sc.nextLine();
                Integer vetId = vetIdVal == 0 ? null : vetIdVal;

                MascotaDTO nueva = new MascotaDTO(0, chip, nombre, peso, fecha, tipo, vetId);
                int ins = masDAO.insertMascota(nueva);
                System.out.println(ins > 0 ? "Mascota añadida." : "No se pudo añadir mascota.");
                break;

            case 3:
                System.out.print("ID a modificar: ");
                int idMod = sc.nextInt();
                sc.nextLine();
                MascotaDTO m = masDAO.findByPk(idMod);
                if (m == null) {
                    System.out.println("No encontrada");
                    break;
                }
                System.out.println("Dejar vacío para mantener el valor actual.");
                System.out.print("Chip (" + m.getChip() + "): ");
                String chipN = sc.nextLine();
                System.out.print("Nombre (" + m.getNombre() + "): ");
                String nombreN = sc.nextLine();
                System.out.print("Peso (" + m.getPeso() + "): ");
                String pesoS = sc.nextLine();
                System.out.print("Fecha nacimiento (" + m.getFechaNacimiento() + "): ");
                String fechaS = sc.nextLine();
                System.out.print("Tipo (" + m.getTipo() + "): ");
                String tipoN = sc.nextLine();
                System.out.print("ID veterinario (" + m.getVeterinarioId() + ", 0 = ninguno): ");
                String vetS = sc.nextLine();

                if (!chipN.isEmpty()) m.setChip(chipN);
                if (!nombreN.isEmpty()) m.setNombre(nombreN);
                if (!pesoS.isEmpty()) m.setPeso(Double.parseDouble(pesoS));
                if (!fechaS.isEmpty()) m.setFechaNacimiento(LocalDate.parse(fechaS));
                if (!tipoN.isEmpty()) m.setTipo(tipoN);
                if (!vetS.isEmpty()) {
                    int vetIdN = Integer.parseInt(vetS);
                    m.setVeterinarioId(vetIdN == 0 ? null : vetIdN);
                }

                int upd = masDAO.updateMascota(idMod, m);
                System.out.println(upd > 0 ? "Mascota actualizada." : "No se pudo actualizar.");
                break;

            case 4:
                System.out.print("ID a borrar: ");
                int idDel = sc.nextInt();
                sc.nextLine();
                MascotaDTO md = masDAO.findByPk(idDel);
                if (md == null) {
                    System.out.println("No encontrada");
                    break;
                }
                System.out.print("Confirma borrado (s/n): ");
                String conf = sc.nextLine();
                if (conf.equalsIgnoreCase("s")) {
                    int del = masDAO.deleteMascota(md);
                    System.out.println(del > 0 ? "Mascota borrada." : "No se pudo borrar.");
                } else {
                    System.out.println("Borrado cancelado.");
                }
                break;

            case 0:
            default:
                break;
        }
    }

    // nextMascotaId removed - DB uses AUTO_INCREMENT

    // -------------------- Asignación / Vistas --------------------

    private static void asignarMascotaAVeterinario(Scanner sc, MascotaDAO masDAO) throws Exception {
        System.out.print("ID mascota: ");
        int idMascota = sc.nextInt();
        sc.nextLine();

        System.out.print("ID veterinario: ");
        int idVet = sc.nextInt();
        sc.nextLine();

        MascotaDTO mascota = masDAO.findByPk(idMascota);

        if (mascota != null) {
            mascota.setVeterinarioId(idVet == 0 ? null : idVet);
            masDAO.updateMascota(idMascota, mascota);
            System.out.println("Mascota asignada correctamente");
        } else {
            System.out.println("Mascota no encontrada");
        }
    }

    private static void verMascotasPorVeterinario(Scanner sc, MascotaDAO masDAO) throws Exception {
        System.out.print("ID veterinario: ");
        int vetId = sc.nextInt();
        sc.nextLine();

        List<MascotaDTO> lista = masDAO.getByMascotaId(vetId);

        if (lista.isEmpty()) {
            System.out.println("No hay mascotas para este veterinario");
        } else {
            for (MascotaDTO m : lista) {
                System.out.println(m.getNombre() + " - " + m.getTipo());
            }
        }
    }
}
