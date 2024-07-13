package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ProdutoDAO;
import database.BancoDados;
import entities.Pedido;
import entities.Produto;

public class ProdutoService {

	public ProdutoService() {
		
	}
	
	public static String cadastrar(Produto produto) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new ProdutoDAO(conn).cadastrar(produto);
	}
	
	public static ArrayList<Produto> visualizarProduto() throws SQLException,IOException {
		Connection conn = BancoDados.conectar();
		return new ProdutoDAO(conn).visualizarProduto();
	}
	
	 
	
}
