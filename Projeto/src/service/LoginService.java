package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.LoginDAO;
import database.BancoDados;

public class LoginService {

public LoginService() {
		
	}
	
	public static int login(String userName, String senha) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new LoginDAO(conn).login(userName, senha);
	}
}
