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
                m.setVeterinarioId(rs.getInt("veterinario_id"));

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
                m.setVeterinarioId(rs.getInt("veterinario_id"));

                return m;
            }
        }

        return null;
    }

    @Override
    public int insertMascota(MascotaDTO mascota) throws SQLException {
        String sql = "INSERT INTO mascota VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mascota.getId());
            ps.setString(2, mascota.getChip());
            ps.setString(3, mascota.getNombre());
            ps.setDouble(4, mascota.getPeso());
            ps.setDate(5, Date.valueOf(mascota.getFechaNacimiento()));
            ps.setString(6, mascota.getTipo());
            ps.setInt(7, mascota.getVeterinarioId());

            return ps.executeUpdate();
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
            ps.setInt(6, nuevosDatos.getVeterinarioId());
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
                m.setVeterinarioId(rs.getInt("veterinario_id"));

                lista.add(m);
            }
        }

        return lista;
    }

}
