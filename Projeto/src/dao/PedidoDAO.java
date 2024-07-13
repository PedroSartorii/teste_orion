package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.BancoDados;
import entities.Pedido;
import entities.Produto;

public class PedidoDAO {

	private Connection conn;

    public PedidoDAO(Connection conn) {
        this.conn = conn;
    }
    
    public String cadastrar(Pedido pedido) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn.setAutoCommit(false); // Inicia a transação

            // Inserir o pedido
            st = conn.prepareStatement("INSERT INTO pedido (id_usuario, valor) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, pedido.getId_usuario());
            st.setDouble(2, pedido.calcularValorPedido());
            st.executeUpdate();

            rs = st.getGeneratedKeys();
            int pedidoId;
            if (rs.next()) {
                pedidoId = rs.getInt(1);
                pedido.setId(pedidoId);
            } else {
                conn.rollback();
                return "Id do pedido não gerado!";
            }

            // Inserir os produtos do pedido
            for (Produto produto : pedido.getProdutos()) {
                st = conn.prepareStatement("INSERT INTO produto (descricao, qtde, valor, id_usuario, CODPROD) VALUES (?, ?, ?, ?, ?)");
                st.setString(1, produto.getDescricao());
                st.setInt(2, produto.getQtde());
                st.setDouble(3, produto.getValor());
                st.setInt(4, produto.getId_usuario());
                st.setInt(5, pedidoId);
                st.executeUpdate();
            }

            conn.commit(); // Confirma a transação
            return "Cadastro de pedido realizado com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback(); // Desfaz a transação em caso de erro
            return "Erro ao cadastrar pedido: " + e.getMessage();
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            conn.setAutoCommit(true); // Retorna o modo de commit automático
        }
    }
    
    public String removerProdutoDoPedido(int pedidoId, int produtoId) throws SQLException {
        PreparedStatement st = null;

        try {
            conn.setAutoCommit(false); 

            // Remover o produto do pedido
            st = conn.prepareStatement("DELETE FROM produto WHERE id = ? AND CODPROD = ?");
            st.setInt(1, produtoId);
            st.setInt(2, pedidoId);
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                conn.commit(); 
                return "Produto removido do pedido com sucesso!";
            } else {
                conn.rollback();
                return "Produto ou pedido não encontrado!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback(); 
            return "Erro ao remover produto do pedido: " + e.getMessage();
        } finally {
            if (st != null) st.close();
            conn.setAutoCommit(true); 
        }
    }
    
    public List<Pedido> visualizarPedidosPorUsuario(int idUsuario) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Pedido> pedidos = new ArrayList<>();

        try {
            // Recuperar os pedidos do usuário
            st = conn.prepareStatement("SELECT id, valor FROM pedido WHERE id_usuario = ?");
            st.setInt(1, idUsuario);
            rs = st.executeQuery();

            while (rs.next()) {
                int pedidoId = rs.getInt("id");
                double valorPedido = rs.getDouble("valor");

                // Recuperar os produtos do pedido
                PreparedStatement stProdutos = conn.prepareStatement("SELECT id, descricao, qtde, valor FROM produto WHERE CODPROD = ?");
                stProdutos.setInt(1, pedidoId);
                ResultSet rsProdutos = stProdutos.executeQuery();

                List<Produto> produtos = new ArrayList<>();
                while (rsProdutos.next()) {
                    Produto produto = new Produto();
                    produto.setId(rsProdutos.getInt("id"));
                    produto.setDescricao(rsProdutos.getString("descricao"));
                    produto.setQtde(rsProdutos.getInt("qtde"));
                    produto.setValor(rsProdutos.getDouble("valor"));
                    produto.setId_usuario(idUsuario); // Adiciona o id do usuário ao produto
                    produtos.add(produto);
                }

                rsProdutos.close();
                stProdutos.close();

                Pedido pedido = new Pedido(pedidoId, produtos, idUsuario);
                
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }

        return pedidos;
    }
    
    
}
