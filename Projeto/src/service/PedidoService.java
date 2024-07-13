package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.PedidoDAO;
import dao.ProdutoDAO;
import database.BancoDados;
import entities.Pedido;

public class PedidoService {

	public PedidoService() {
		
	}
	
	 public static String cadastrar(Pedido pedido) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new PedidoDAO(conn).cadastrar(pedido);
	 }
	 
	 public static String removerProdutoDoPedido(int pedidoId, int produtoId) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new PedidoDAO(conn).removerProdutoDoPedido(pedidoId, produtoId); 
	 }
	 
	 public static List<Pedido> visualizarPedidosPorUsuario(int idUsuario) throws SQLException, IOException {
		 Connection conn = BancoDados.conectar();
		return new PedidoDAO(conn).visualizarPedidosPorUsuario(idUsuario);
	 }
}
