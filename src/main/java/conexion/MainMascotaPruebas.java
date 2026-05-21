package conexion;

import controladores.MascotaDAO;
import modelos.MascotaDTO;

public class MainMascotaPruebas {

    public static void main(String[] args) {

        MascotaDAO dao = new MascotaDAO();

        try {

          //Obtención de todas las mascotas

            for (MascotaDTO m : dao.getAll()) {
                System.out.println(
                        "ID: " + m.getId()
                        + " | chip: " + m.getChip()
                        + " | Nombre: " + m.getNombre()
                        + " | Peso: " + m.getPeso()
                        + " | Tipo: " + m.getTipo()
                        + " | VetID: " + m.getVeterinarioId()
                );
            }


          
            // Busqueda por Pk

            MascotaDTO encontrada = dao.findByPk(1);

            if (encontrada != null) {
                System.out.println("Encontrada: " + encontrada.getNombre());
            } else {
                System.out.println("No encontrada");
            }


          //Agregacion de nueva mascota 
          // Hay que tener en cuenta que si se ejecuta dos veces el codigo da error
          // Pues repetiriamos campos que son unicos

            MascotaDTO m = new MascotaDTO();
            m.setId(10);
            m.setChip("CHIP9999");
            m.setNombre("Luna");
            m.setPeso(12.3);
            m.setFechaNacimiento(java.time.LocalDate.of(2021, 5, 10));
            m.setTipo("perro");
            m.setVeterinarioId(3);

            int r = dao.insertMascota(m);
            System.out.println("Insertadas: " + r);


            //Acutalizacion

            m.setNombre("Luna actualizada");
            m.setPeso(23.0);

            int u = dao.updateMascota(10, m);
            System.out.println("Actualizadas: " + u);


            //Borrado

            int d = dao.deleteMascota(m);
            System.out.println("Eliminadas: " + d);


            
            System.out.println("\n===== POR VETERINARIO =====");

            for (MascotaDTO x : dao.getByMascotaId(3)) {
                System.out.println(
                        x.getNombre() + " - " + x.getTipo()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

