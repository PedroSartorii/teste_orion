package entities;

public class Produto {

	private int id;
	private String descricao;
	private double valor;
	private int qtde;
	private int id_usuario;
	
	public Produto() {
    	
    }

	public Produto(int id, String descricao, double valor, int qtde, int id_usuario) {
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.qtde = qtde;
		this.id_usuario = id_usuario;
	}


	
	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}
	
	
	
	
}
