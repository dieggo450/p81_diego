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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertVeterinario'");
    }

    @Override
    public int deleteVeterinario(VeterinarioDTO p) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteVeterinario'");
    }

    @Override
    public int updateVeterinario(int pk, VeterinarioDTO nuevosDatos) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateVeterinario'");
    }

    private Connection con = null;

    public VeterinarioDAO() {
        con = Conexion.getInstance();
    }

}
