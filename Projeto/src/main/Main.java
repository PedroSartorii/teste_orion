package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entities.Pedido;
import entities.Produto;
import entities.Usuario;
import service.LoginService;
import service.PedidoService;
import service.ProdutoService;
import service.UsuarioService;

public class Main {

	public static void main(String[] args) throws IOException, SQLException {
		
		int id_login = 0;
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		while (true) {
			
		
		
		System.out.println("Selecione uma opção!");
		System.out.println("1 - Cadastrar Usuário");
		System.out.println("2 - Login");
		System.out.println("3 - Cadastro de um produto");
		System.out.println("4 - Lista de todos os produtos");
		System.out.println("5 - Cadastro de um Pedido");
		System.out.println("6 - Remover Item de um pedido");
		System.out.println("7 - Visualizar pedido de um usuário");
		if ((userInput = stdIn.readLine()) == null) {
			break;
		}
		int opc = Integer.parseInt(userInput);
		
		switch (opc) {

		case 1:
			
			try {
				
			Usuario usuario = new Usuario();
		
			System.out.println("Nome:");
			String nome = stdIn.readLine();
			System.out.println("Email:");
			String email = stdIn.readLine();
			System.out.println("Senha:");
			String senha = stdIn.readLine();
			System.out.println("Nome de usuario:");
			String nomeUsuario = stdIn.readLine();
			
			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setSenha(senha);
			usuario.setNomeUsuario(nomeUsuario);
			
			String resposta = UsuarioService.cadastrar(usuario);
			if(resposta == "cadastro realizado !") {
				System.out.println("cadastro feito");
			}
			} catch (SQLException | IOException e) {
				System.out.println("Erro ao cadastrar!");
			}

			break;
			
		case 2:
			
			System.out.println("Nome usuário:");
			String nome_usu = stdIn.readLine();
			System.out.println("Senha:");
			String senha_usu = stdIn.readLine();
			
			id_login = LoginService.login(nome_usu, senha_usu);
			if(id_login == 0) {
				System.out.println("Erro no login");
			} else {
				System.out.println("Login efetuado!");
			}
				
			
			break;
			
		case 3:

			
			try {	
			System.out.println("ID logado:"+ id_login);
			
			System.out.println("Descrição do produto:");
			String desc_prod = stdIn.readLine();
			System.out.println("Quantidade do produto:");
			int qtde_prod = Integer.parseInt(stdIn.readLine());
			System.out.println("Valor do produto:");
			Double valor_prod = Double.parseDouble(stdIn.readLine());
			
			Produto produto = new Produto();
			
			produto.setDescricao(desc_prod);
			produto.setQtde(qtde_prod);
			produto.setValor(valor_prod);
			produto.setId_usuario(id_login);
			
			String resposta = ProdutoService.cadastrar(produto);
			if(resposta == "cadastro de produto realizado !") {
				System.out.println("cadastro de produto feito");
			}
			} catch (SQLException | IOException e) {
				System.out.println("Erro ao cadastrar!");
			}
			
			break;
		case 4:
			try {
				ArrayList<Produto> produtos = ProdutoService.visualizarProduto();
				
				for(Produto produto : produtos) {
					System.out.println("ID: " + produto.getId());
					System.out.println("Descrição: " + produto.getDescricao());
					System.out.println("Quantidade: " + produto.getQtde());
					System.out.println("Valor: " + produto.getValor());
					System.out.println("ID usuario: " + produto.getId_usuario());
					System.out.println();
				}
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
			
			break;
			
		case 5:
			try {
		        List<Produto> produtos = new ArrayList<>();
		        String continuar;

		        do {
		            System.out.println("Descrição do produto:");
		            String desc_prod = stdIn.readLine();
		            System.out.println("Quantidade do produto:");
		            int qtde_prod = Integer.parseInt(stdIn.readLine());
		            System.out.println("Valor do produto:");
		            double valor_prod = Double.parseDouble(stdIn.readLine());

		            Produto produto = new Produto();
		            produto.setDescricao(desc_prod);
		            produto.setQtde(qtde_prod);
		            produto.setValor(valor_prod);
		            produto.setId_usuario(id_login);

		            produtos.add(produto);

		            System.out.println("Deseja adicionar mais um produto ao pedido? (s/n)");
		            continuar = stdIn.readLine();
		        } while (continuar.equalsIgnoreCase("s"));

		        Pedido pedido = new Pedido(0, produtos, id_login);

		        String respostaPedido = PedidoService.cadastrar(pedido);
		        if (respostaPedido.equals("Cadastro de pedido realizado com sucesso!")) {
		            System.out.println("Cadastro de pedido realizado com sucesso!");
		            System.out.println("Pedido: " + pedido.getId());
		            System.out.println("Valor do pedido: " + pedido.calcularValorPedido());
		            System.out.println("");
		        } else {
		            System.out.println("Erro ao cadastrar o pedido!");
		        }
		    } catch (SQLException | IOException e) {
		        System.out.println("Erro ao cadastrar o pedido!");
		        e.printStackTrace();
		    }
		    break;
     
		case 6:
		
		    try {
		        System.out.println("ID do pedido:");
		        int pedidoId = Integer.parseInt(stdIn.readLine());
		        System.out.println("ID do produto a remover:");
		        int produtoId = Integer.parseInt(stdIn.readLine());

		        String respostaRemocao = PedidoService.removerProdutoDoPedido(pedidoId, produtoId);
		        if (respostaRemocao.equals("Produto removido do pedido com sucesso!")) {
		            System.out.println("Produto removido do pedido com sucesso!");
		        } else {
		            System.out.println("Erro ao remover o produto do pedido: " + respostaRemocao);
		        }
		    } catch (SQLException | IOException e) {
		        System.out.println("Erro ao remover o produto do pedido!");
		        e.printStackTrace();
		    }
		    break;
		    
		case 7: 
		    try {
		        System.out.println("ID do usuário:");
		        int idUsuario = Integer.parseInt(stdIn.readLine());

		        List<Pedido> pedidos = PedidoService.visualizarPedidosPorUsuario(idUsuario);
		        if (pedidos.isEmpty()) {
		            System.out.println("Nenhum pedido encontrado para o usuário " + idUsuario);
		        } else {
		            for (Pedido pedido : pedidos) {
		                System.out.println("Pedido ID: " + pedido.getId());
		              
		                System.out.println("Produtos:");
		                for (Produto produto : pedido.getProdutos()) {
		                    System.out.println("    Produto ID: " + produto.getId());
		                    System.out.println("    Descrição: " + produto.getDescricao());
		                    System.out.println("    Quantidade: " + produto.getQtde());
		                    System.out.println("    Valor: " + produto.getValor());
		                    System.out.println();
		                }
		                System.out.println("-----------------------------");
		            }
		        }
		    } catch (SQLException | IOException e) {
		        System.out.println("Erro ao visualizar os pedidos!");
		        e.printStackTrace();
		    }
		    break;
		
	} // fim do switch
		
	}	
		
		
		
	}
}

