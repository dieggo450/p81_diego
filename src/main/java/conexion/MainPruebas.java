package conexion;

import java.util.List;

import controladores.VeterinarioDAO;
import modelos.VeterinarioDTO;

public class MainPruebas {

    public static void main(String[] args) {

        VeterinarioDAO dao = new VeterinarioDAO();

        try {

            // 🔹 GET ALL
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

            // 🔹 FIND BY PK
            VeterinarioDTO encontrado = dao.findByPk(1);

            if (encontrado != null) {
                System.out.println("Encontrado: " + encontrado.getNombre());
            } else {
                System.out.println("No encontrado");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
