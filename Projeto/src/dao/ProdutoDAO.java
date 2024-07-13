package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import database.BancoDados;
import entities.Produto;


public class ProdutoDAO {
	
	private Connection conn;

    public ProdutoDAO(Connection conn) {
        this.conn = conn;
    }

    public String cadastrar(Produto produto) throws SQLException {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement("insert into produto(descricao, qtde, valor, id_usuario) values(?, ?, ?, ?)");
            st.setString(1, produto.getDescricao());
            st.setInt(2, produto.getQtde());
            st.setDouble(3, produto.getValor());
            st.setInt(4, produto.getId_usuario());
            st.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }

        return "cadastro de produto realizado !";
    }

    public ArrayList<Produto> visualizarProduto() throws SQLException,IOException {
    	ArrayList<Produto> produtos = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select * from produto");
            
            rs = st.executeQuery();

            while(rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQtde(rs.getInt("qtde"));
                produto.setValor(rs.getDouble("valor"));
                produto.setId_usuario(rs.getInt("id_usuario"));
           

                produtos.add(produto);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }

        return produtos;
    }

}
