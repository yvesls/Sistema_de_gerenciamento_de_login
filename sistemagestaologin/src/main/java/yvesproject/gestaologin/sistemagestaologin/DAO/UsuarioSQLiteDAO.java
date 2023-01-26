package yvesproject.gestaologin.sistemagestaologin.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import yvesproject.gestaologin.sistemagestaologin.DAO.interfaces.IUsuarioDAO;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public class UsuarioSQLiteDAO  extends ConexaoSQLiteDAO implements IUsuarioDAO {

	@Override
	public boolean salvar(Usuario usuario) {
		PreparedStatement stmt = null;
		try {
			conectar();
			String sql = ""
					+ "INSERT INTO Usuario (email, senha, tipo, status, nome, cpf, notificacoesEnviadas, notificacoesLidas, dataCadastro) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			stmt = criarStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, usuario.getEmail());
			stmt.setString(2, usuario.getSenha());
			stmt.setString(3, usuario.getTipo());
			stmt.setString(4, usuario.getState());
			stmt.setString(5, usuario.getNome());
			stmt.setString(6, usuario.getCpf());
			stmt.setInt(7, usuario.getNotEnviadas());
			stmt.setInt(8, usuario.getNotLidas());
			stmt.setString(9, usuario.getDataCadastro());
			
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		fechar();
		return true;
	}

	@Override
	public boolean remover() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Boolean getIsUsuarios() {
		conectar();
		ResultSet result = null;
		Boolean temRegistro = null;
		PreparedStatement stmt = null;
		String sql = "SELECT count(*) FROM Usuario;";

		stmt = this.criarStatement(sql);
		try {
			result = stmt.executeQuery();
			while (result.next()) {
				temRegistro = result.getBoolean(1);
			}
			
			fechar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return temRegistro;
	}

}
