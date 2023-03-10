package yvesproject.gestaologin.sistemagestaologin.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import yvesproject.gestaologin.sistemagestaologin.DAO.interfaces.IUsuarioDAO;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public class UsuarioSQLiteDAO extends ConexaoSQLiteDAO implements IUsuarioDAO {

	public UsuarioSQLiteDAO() {
		PreparedStatement stmt = null;
		conectar();
		String sql = "CREATE TABLE IF NOT EXISTS Usuario ("
				+ "    idUsuario            INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "    email                TEXT (45) UNIQUE,"
				+ "    senha                TEXT (10) NOT NULL,"
				+ "    tipo                 TEXT (45) NOT NULL,"
				+ "    status               TEXT (45) NOT NULL,"
				+ "    nome                 TEXT (45) NOT NULL,"
				+ "    cpf                  TEXT (11) NOT NULL UNIQUE,"
				+ "    notificacoesEnviadas INTEGER   NOT NULL,"
				+ "    notificacoesLidas    INTEGER   NOT NULL,"
				+ "    dataCadastro         TEXT (25) NOT NULL"
				+ ");";
		stmt = criarStatement(sql);
		try {
			stmt.execute();
			fechar();
			stmt.close();
		} catch (SQLException e) {
			fechar();
			e.printStackTrace();
		}
	}
	
	/* @ yves
	 * RegistrarPresenter */
	@Override
	public int salvar(Usuario usuario) throws SQLException {
		PreparedStatement stmt = null;
		int idGerado = -1;
		ResultSet result = null;

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
		result = stmt.getGeneratedKeys();
		if (result.next()) {
			idGerado = result.getInt(1);
		}

		fechar();
		stmt.close();
		return idGerado;
	}

	/* @ yves
	 * PrincipalAdminPresenter */
	@Override
	public boolean remover(int idUsuario) throws SQLException {
		conectar();
		String sql = "DELETE FROM Usuario WHERE idUsuario = '" + idUsuario + "';";
		PreparedStatement stmt = this.criarStatement(sql);
		stmt.executeUpdate();
		fechar();

		stmt.close();
		return true;
	}

	/* @ yves
	 * AlterarSenha, NotificacaoService */
	@Override
	public boolean atualizar(Usuario usuario) throws SQLException {
		conectar();
		String sql = "UPDATE Usuario SET email=?, "
				+ "senha=?, tipo=?, status=?, nome=?, cpf=?, notificacoesEnviadas=?, notificacoesLidas=?, dataCadastro = ? WHERE idUsuario = '"
				+ usuario.getIdUsuario() + "'";
		PreparedStatement stmt = criarStatement(sql);

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
		fechar();
		stmt.close();
		return true;
	}

	/* @ yves
	 * NotificacaoService */
	@Override
	public boolean atualizarQtdNotificacoesLidas(Usuario usuario) throws SQLException {
		conectar();
		String sql = "UPDATE Usuario SET notificacoesLidas=? WHERE idUsuario = '" + usuario.getIdUsuario() + "';";
		PreparedStatement stmt = criarStatement(sql);
		stmt.setInt(1, usuario.getNotLidas());
		stmt.executeUpdate();
		fechar();
		stmt.close();
		return true;
	}

	/* @ yves
	 * NotificacaoService */
	@Override
	public boolean atualizarQtdNotificacoesEnviadas(Usuario usuario) throws SQLException {
		conectar();
		String sql = "UPDATE Usuario SET notificacoesEnviadas=? WHERE idUsuario = '" + usuario.getIdUsuario() + "';";
		PreparedStatement stmt = criarStatement(sql);
		stmt.setInt(1, usuario.getNotEnviadas());
		stmt.executeUpdate();
		fechar();
		stmt.close();
		return true;
	}

	/* @ yves
	 * AtivarUsuarioState, CancelarUsuarioState, DesabilitarUsuarioState */
	@Override
	public boolean atualizarStateUsuario(Usuario usuario) throws SQLException {
		conectar();
		String sql = "UPDATE Usuario SET status=? WHERE idUsuario = '" + usuario.getIdUsuario() + "';";
		PreparedStatement stmt = criarStatement(sql);
		stmt.setString(1, usuario.getState());
		stmt.executeUpdate();
		fechar();
		stmt.close();
		return true;
	}

	/* @ yves
	 * RegistrarPresenter */
	@Override
	public Boolean getIsUsuarios() throws SQLException {
		conectar();
		ResultSet result = null;
		Boolean temRegistro = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM Usuario;";

		stmt = this.criarStatement(sql);
		result = stmt.executeQuery();
		while (result.next()) {
			temRegistro = result.getBoolean(1);
		}

		fechar();

		stmt.close();
		return temRegistro;
	}

	/* @ yves
	 * PrincipalAdminPresenter */
	@Override
	public ArrayList<Usuario> getUsuariosPorNome(String nome) throws SQLException {
		conectar();
		ArrayList<Usuario> listaUsers = new ArrayList<>();
		ResultSet result = null;
		PreparedStatement stmt = null;
		Usuario user = new Usuario();

		String sql = "SELECT idUsuario, email, senha, tipo, status, nome, cpf, notificacoesEnviadas, notificacoesLidas, dataCadastro FROM Usuario WHERE"
				+ " nome LIKE '%" + nome + "%' AND tipo = 'usuario';";
		stmt = this.criarStatement(sql);
		stmt.executeQuery();
		result = stmt.executeQuery();
		while (result.next()) {
			user = new Usuario(result.getInt("idUsuario"), result.getString("email"), result.getString("senha"),
					result.getString("tipo"), result.getString("status"), result.getString("nome"),
					result.getString("cpf"), result.getInt("notificacoesEnviadas"), result.getInt("notificacoesLidas"),
					result.getString("dataCadastro"));

			listaUsers.add(user);
		}
		fechar();
		stmt.close();
		return listaUsers;
	}

	/* @ yves
	 * LoginPresenter */
	@Override
	public Usuario getUserIsRegister(Usuario usuario) throws SQLException {
		conectar();
		Usuario user = null;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = ""
				+ "SELECT idUsuario, email, senha, tipo, status, nome, cpf, notificacoesEnviadas, notificacoesLidas, dataCadastro FROM Usuario WHERE email = '"
				+ usuario.getEmail() + "' AND " + "senha = '" + usuario.getSenha() + "';";

		stmt = criarStatement(sql);

		result = stmt.executeQuery();
		if (result.next()) {
			user = new Usuario(result.getInt("idUsuario"), result.getString("email"), result.getString("senha"),
					result.getString("tipo"), result.getString("status"), result.getString("nome"),
					result.getString("cpf"), result.getInt("notificacoesEnviadas"), result.getInt("notificacoesLidas"),
					result.getString("dataCadastro"));
		}

		fechar();
		stmt.close();
		return user;
	}

	/* @ yves
	 * NotificacaoService */
	@Override
	public Usuario getUsuarioPorId(int idUsuario) throws SQLException {
		conectar();
		Usuario user = null;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = ""
				+ "SELECT idUsuario, email, senha, tipo, status, nome, cpf, notificacoesEnviadas, notificacoesLidas, dataCadastro FROM Usuario WHERE idUsuario = '"
				+ idUsuario + "';";

		stmt = criarStatement(sql);
		result = stmt.executeQuery();
		if (result.next()) {
			user = new Usuario(result.getInt("idUsuario"), result.getString("email"), result.getString("senha"),
					result.getString("tipo"), result.getString("status"), result.getString("nome"),
					result.getString("cpf"), result.getInt("notificacoesEnviadas"), result.getInt("notificacoesLidas"),
					result.getString("dataCadastro"));
		}

		fechar();
		stmt.close();
		return user;
	}

	/* @ yves
	 * PrincipalAdminPresenter */
	@Override
	public ArrayList<Usuario> getTodosUsuarios() throws SQLException {
		conectar();
		ArrayList<Usuario> listaUsers = new ArrayList<>();
		Usuario user = new Usuario();
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM Usuario WHERE tipo != 'administrador';";

		stmt = criarStatement(sql);
		result = stmt.executeQuery();
		while (result.next()) {
			user = new Usuario(result.getInt("idUsuario"), "", "", result.getString("tipo"), result.getString("status"),
					result.getString("nome"), result.getString("cpf"), result.getInt("notificacoesEnviadas"),
					result.getInt("notificacoesLidas"), result.getString("dataCadastro"));
			listaUsers.add(user);
		}
		fechar();
		stmt.close();
		return listaUsers;
	}

	/* @ yves
	 * EnviarNotificacaoAllUsersPresenter, EnviarNotificacaoUserPresenter, NotificacaoPresenter */
	@Override
	public int getIdAdministrador() throws SQLException {
		conectar();
		int user = -1;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT idUsuario FROM Usuario LIMIT 1;";

		stmt = criarStatement(sql);

		result = stmt.executeQuery();
		if (result.next()) {
			user = result.getInt("idUsuario");
		}
		stmt.close();
		fechar();
		return user;
	}
}
