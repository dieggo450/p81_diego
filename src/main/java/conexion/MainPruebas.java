package conexion;

import java.util.List;

import controladores.VeterinarioDAO;
import modelos.VeterinarioDTO;

public class MainPruebas {

    public static void main(String[] args) {
        try {

            VeterinarioDAO dao = new VeterinarioDAO();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
