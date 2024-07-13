package entities;

import java.util.List;

public class Pedido {
	
	private int id;
	private List<Produto> produtos;
	private int id_usuario;
	private double valor_pedido;
	
	
	public Pedido() {
    	
    }


	public Pedido(int id, List<Produto> produtos, int id_usuario) {
		
		this.id = id;
		this.produtos = produtos;
		this.id_usuario = id_usuario;
		this.valor_pedido = calcularValorPedido();
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public List<Produto> getProdutos() {
		return produtos;
	}



	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public int getId_usuario() {
		return id_usuario;
	}



	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}



	public double calcularValorPedido() {
		return produtos.stream().mapToDouble(Produto::getValor).sum(); 
	}

	
	
	
	

}
