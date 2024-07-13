package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.BancoDados;
import entities.Usuario;

public class UsuarioDAO {

	private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }
    
    public String cadastrar(Usuario usuario) throws SQLException {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("insert into usuario(nome, email, nome_usuario, senha) values(?, ?, ?, ?)");
            st.setString(1, usuario.getNome());
            st.setString(2, usuario.getEmail());
            st.setString(3, usuario.getNomeUsuario());
            st.setString(4, usuario.getSenha());
            
            st.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
        
        return "cadastro realizado !";
    }
}
