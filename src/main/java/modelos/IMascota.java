package modelos;

import java.sql.SQLException;
import java.util.List;

public interface IMascota {
//Método para obtener todos los registros de la tabla
    List<MascotaDTO> getAll() throws  SQLException;

    //Buscar un registro por clave primaria
    MascotaDTO findByPk(int pk) throws  SQLException;

    //insertar un registro
    int insertVeterinario (MascotaDTO mascota) throws SQLException;

    //Borrar un registro
    int deleteMascota (MascotaDTO p) throws SQLException;

    //Modificar un registro
    //Se modifica a la mascota  que tenga esa "pk"
    // con los nuevos datos que traiga
    int updateMascota (int pk, MascotaDTO nuevosDatos) throws  SQLException;

    //Obtener todas las mascotas tratadas por un veterinario
    //Basándose en su id.
    List<MascotaDTO> getByVeterinarioId(int idVeterinario) throws SQLException;
}
