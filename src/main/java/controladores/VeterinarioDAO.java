package controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;
import modelos.IVeterinario;
import modelos.VeterinarioDTO;

public class VeterinarioDAO implements IVeterinario {

    private Connection con = null;

    public VeterinarioDAO() {
        con = Conexion.getInstance();
    }

    @Override
    public List<VeterinarioDTO> getAll() throws SQLException {
        List<VeterinarioDTO> lista = new ArrayList<>();

        try (Statement st = con.createStatement()) {

            ResultSet res = st.executeQuery("SELECT * FROM veterinario");

            while (res.next()) {
                VeterinarioDTO v = new VeterinarioDTO();

                v.setId(res.getInt("id"));
                v.setNif(res.getString("nif"));
                v.setNombre(res.getString("nombre"));
                v.setDireccion(res.getString("direccion"));
                v.setTelefono(res.getString("telefono"));
                v.setEmail(res.getString("email"));

                lista.add(v);
            }
        }

        return lista;
    }

    @Override
    public VeterinarioDTO findByPk(int pk) throws SQLException {
        String sql = "SELECT * FROM veterinario WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pk);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                VeterinarioDTO v = new VeterinarioDTO();

                v.setId(rs.getInt("id"));
                v.setNif(rs.getString("nif"));
                v.setNombre(rs.getString("nombre"));
                v.setDireccion(rs.getString("direccion"));
                v.setTelefono(rs.getString("telefono"));
                v.setEmail(rs.getString("email"));

                return v;
            }
        }

        return null;
    }

    @Override
    public int insertVeterinario(VeterinarioDTO veterinario) throws SQLException {
        String sql = "INSERT INTO veterinario VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, veterinario.getId());
            ps.setString(2, veterinario.getNif());
            ps.setString(3, veterinario.getNombre());
            ps.setString(4, veterinario.getDireccion());
            ps.setString(5, veterinario.getTelefono());
            ps.setString(6, veterinario.getEmail());

            return ps.executeUpdate();
        }
    }

    @Override
    public int deleteVeterinario(VeterinarioDTO p) throws SQLException {
        String sql = "DELETE FROM veterinario WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getId());

            return ps.executeUpdate();
        }
    }

    @Override
    public int updateVeterinario(int pk, VeterinarioDTO nuevosDatos) throws SQLException {
        String sql = "UPDATE veterinario SET nif=?, nombre=?, direccion=?, telefono=?, email=? WHERE id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevosDatos.getNif());
            ps.setString(2, nuevosDatos.getNombre());
            ps.setString(3, nuevosDatos.getDireccion());
            ps.setString(4, nuevosDatos.getTelefono());
            ps.setString(5, nuevosDatos.getEmail());
            ps.setInt(6, pk);

            return ps.executeUpdate();
        }
    }

    }


