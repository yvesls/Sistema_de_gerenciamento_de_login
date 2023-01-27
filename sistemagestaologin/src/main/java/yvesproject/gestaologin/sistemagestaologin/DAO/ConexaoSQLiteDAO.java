package yvesproject.gestaologin.sistemagestaologin.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoSQLiteDAO {
	protected Connection conexao;
	protected boolean conectar() {
		String url = "jdbc:sqlite:DdModuloLoginUsuario.db";
		try {
			setConexao(DriverManager.getConnection(url));
			//System.out.println(conexao);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	protected boolean fechar() {
		try {
			if(this.getConexao().isClosed() == false) {
				this.getConexao().close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	} 
	
	protected Statement criarStatement() {
		try {
			return this.getConexao().createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected PreparedStatement criarStatement(String pSQL, int RETURN_GENERATE_KEYS) {
		try {
			return this.getConexao().prepareStatement(pSQL, RETURN_GENERATE_KEYS);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected PreparedStatement criarStatement(String pSQL) {
		try {
			return this.getConexao().prepareStatement(pSQL);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected Connection getConexao() {
		return conexao;
	}

	protected void setConexao(Connection conexao) {
		this.conexao = conexao;
	}
}
