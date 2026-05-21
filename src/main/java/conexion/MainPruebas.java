package conexion;

import java.util.List;

import controladores.VeterinarioDAO;
import modelos.VeterinarioDTO;

public class MainPruebas {

    public static void main(String[] args) {

        VeterinarioDAO dao = new VeterinarioDAO();

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

          
            VeterinarioDTO encontrado = dao.findByPk(1);

            if (encontrado != null) {
                System.out.println("Encontrado: " + encontrado.getNombre());
            } else {
                System.out.println("No encontrado");
            }

            
            VeterinarioDTO v = new VeterinarioDTO();
            v.setId(4);
            v.setNif("12348878A");
            v.setNombre("Juan Pérez");
            v.setDireccion("Calle Mayor 1");
            v.setTelefono("600123123");
            v.setEmail("juan@email.com");

            int r = dao.insertVeterinario(v);

            System.out.println("Insertados: " + r);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
