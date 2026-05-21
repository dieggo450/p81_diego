package conexion;

import java.util.List;

import controladores.VeterinarioDAO;
import modelos.VeterinarioDTO;

public class MainPruebas {

    public static void main(String[] args) {

        VeterinarioDAO dao = new VeterinarioDAO();

        //Obtener a todos los veterinarios
        try {

            List<VeterinarioDTO> lista = dao.getAll();

            if (lista.isEmpty()) {
                System.out.println("No hay veterinarios en la base de datos");
            } else {
                for (VeterinarioDTO v : lista) {
                    System.out.println(
                            "ID: " + v.getId()
                            + " | NIF: " + v.getNif()
                            + " | Nombre: " + v.getNombre()
                            + " | Tel: " + v.getTelefono()
                    );
                }
            }

            //Buscar por pk
            VeterinarioDTO encontrado = dao.findByPk(1);

            if (encontrado != null) {
                System.out.println("Encontrado: " + encontrado.getNombre());
            } else {
                System.out.println("No encontrado");
            }

            //Agregar un veterinario
            VeterinarioDTO v = new VeterinarioDTO();
            v.setId(5);
            v.setNif("12348778A");
            v.setNombre("Nora Pérez");
            v.setDireccion("Calle Mayor 1");
            v.setTelefono("600123123");
            v.setEmail("nora@email.com");

            int r = dao.insertVeterinario(v);

            System.out.println("Insertados: " + r);

            // update
            VeterinarioDTO nuevosDatos = new VeterinarioDTO();
            nuevosDatos.setNif("99999999Z");
            nuevosDatos.setNombre("Nombre actualizado");
            nuevosDatos.setDireccion("Nueva dirección");
            nuevosDatos.setTelefono("999999999");
            nuevosDatos.setEmail("actualizado@email.com");

            int u = dao.updateVeterinario(3, nuevosDatos);

            System.out.println("Actualizados: " + u);

            // Borrado de datos:
            VeterinarioDTO borrar = new VeterinarioDTO();
            borrar.setId(4);

            int d = dao.deleteVeterinario(borrar);

            System.out.println("Eliminados: " + d);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
