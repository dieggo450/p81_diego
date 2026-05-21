package modelos;

import java.sql.SQLException;
import java.util.List;

public interface IVeterinario {
    //Método para obtener todos los registros de la tabla
    List<VeterinarioDTO> getAll() throws  SQLException;

    //Buscar un registro por clave primaria
    VeterinarioDTO findByPk(int pk) throws  SQLException;

    //insertar un registro
    int insertVeterinario (VeterinarioDTO veterinario) throws SQLException;

    //Borrar un registro
    int deleteVeterinario (VeterinarioDTO p) throws SQLException;

    //Modificar un registro
    //Se modifica al veterinario que tenga esa "pk"
    // con los nuevos datos que traiga
    int updateVeterinario (int pk, VeterinarioDTO nuevosDatos) throws  SQLException;


}
