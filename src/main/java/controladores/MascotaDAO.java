package controladores;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;
import modelos.IMascota;
import modelos.MascotaDTO;

public class MascotaDAO implements IMascota {

    private Connection con = null;

    public MascotaDAO() {
        con = Conexion.getInstance();
    }

    @Override
    public List<MascotaDTO> getAll() throws SQLException {
        List<MascotaDTO> lista = new ArrayList<>();

        try (Statement st = con.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT * FROM mascota");

            while (rs.next()) {

                MascotaDTO m = new MascotaDTO();

                m.setId(rs.getInt("id"));
                m.setChip(rs.getString("numero_chip"));
                m.setNombre(rs.getString("nombre"));
                m.setPeso(rs.getDouble("peso"));
                m.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                m.setTipo(rs.getString("tipo"));
                int vetId = rs.getInt("veterinario_id");
                m.setVeterinarioId(rs.wasNull() ? null : vetId);

                lista.add(m);
            }

        }

        return lista;
    }

    @Override
    public MascotaDTO findByPk(int pk) throws SQLException {
        String sql = "SELECT * FROM mascota WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pk);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                MascotaDTO m = new MascotaDTO();

                m.setId(rs.getInt("id"));
                m.setChip(rs.getString("numero_chip"));
                m.setNombre(rs.getString("nombre"));
                m.setPeso(rs.getDouble("peso"));
                m.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                m.setTipo(rs.getString("tipo"));
                int vetId = rs.getInt("veterinario_id");
                m.setVeterinarioId(rs.wasNull() ? null : vetId);

                return m;
            }
        }

        return null;
    }

    @Override
    public int insertMascota(MascotaDTO mascota) throws SQLException {
        String sql = "INSERT INTO mascota (numero_chip,nombre,peso,fecha_nacimiento,tipo,veterinario_id) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, mascota.getChip());
            ps.setString(2, mascota.getNombre());
            ps.setDouble(3, mascota.getPeso());
            ps.setDate(4, Date.valueOf(mascota.getFechaNacimiento()));
            ps.setString(5, mascota.getTipo());
            if (mascota.getVeterinarioId() == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, mascota.getVeterinarioId());
            }

            int affected = ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int genId = keys.getInt(1);
                    mascota.setId(genId);
                    return genId;
                }
            }
            return affected;
        }
    }

    @Override
    public int deleteMascota(MascotaDTO p) throws SQLException {
        String sql = "DELETE FROM mascota WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getId());

            return ps.executeUpdate();
        }
    }

    @Override
    public int updateMascota(int pk, MascotaDTO nuevosDatos) throws SQLException {
        String sql = "UPDATE mascota SET numero_chip=?, nombre=?, peso=?, fecha_nacimiento=?, tipo=?, veterinario_id=? WHERE id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevosDatos.getChip());
            ps.setString(2, nuevosDatos.getNombre());
            ps.setDouble(3, nuevosDatos.getPeso());
            ps.setDate(4, Date.valueOf(nuevosDatos.getFechaNacimiento()));
            ps.setString(5, nuevosDatos.getTipo());
            if (nuevosDatos.getVeterinarioId() == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, nuevosDatos.getVeterinarioId());
            }
            ps.setInt(7, pk);

            return ps.executeUpdate();
        }
    }

    @Override
    public List<MascotaDTO> getByMascotaId(int idVeterinario) throws SQLException {
        List<MascotaDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM mascota WHERE veterinario_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVeterinario);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                MascotaDTO m = new MascotaDTO();

                m.setId(rs.getInt("id"));
                m.setChip(rs.getString("numero_chip"));
                m.setNombre(rs.getString("nombre"));
                m.setPeso(rs.getDouble("peso"));
                m.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                m.setTipo(rs.getString("tipo"));
                int vetId = rs.getInt("veterinario_id");
                m.setVeterinarioId(rs.wasNull() ? null : vetId);

                lista.add(m);
            }
        }

        return lista;
    }

}
