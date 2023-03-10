package yvesproject.gestaologin.sistemagestaologin.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import yvesproject.gestaologin.sistemagestaologin.DAO.interfaces.INotificacaoDAO;
import yvesproject.gestaologin.sistemagestaologin.model.Notificacao;

public class NotificacaoSQLiteDAO extends ConexaoSQLiteDAO implements INotificacaoDAO {

	public NotificacaoSQLiteDAO() {
		PreparedStatement stmt = null;
		conectar();
		String sql = " CREATE TABLE IF NOT EXISTS Notificacoes (" 
				+ "idNotificacao  INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "idRemetente    INTEGER    NOT NULL "
				+ "CONSTRAINT fkRemetente REFERENCES Usuario (idUsuario)," 
				+ "idDestinatario INTEGER   NOT NULL "
				+ "CONSTRAINT fkDestinatario REFERENCES Usuario (idUsuario),"
				+ "descricao      TEXT (45) NOT NULL," 
				+ "data           TEXT (20) NOT NULL,"
				+ "status         TEXT (45) NOT NULL" 
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
	 * NotificacaoService */
	@Override
	public boolean salvar(Notificacao notificacao) throws SQLException {
		PreparedStatement stmt = null;

		conectar();
		String sql = "" + "INSERT INTO Notificacoes (idRemetente, idDestinatario, descricao, status, data) "
				+ "VALUES (?, ?, ?, ?, ?);";

		stmt = criarStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, notificacao.getIdRemetente());
		stmt.setInt(2, notificacao.getIdDestinatario());
		stmt.setString(3, notificacao.getDescricao());
		stmt.setString(4, notificacao.getStatus());
		stmt.setString(5, notificacao.getData());

		stmt.executeUpdate();
		stmt.close();
		fechar();
		return true;
	}
	
	@Override
	public boolean removerTodasNotificacoesUser(int idUsuario) throws SQLException {
		conectar();
		String sql = "DELETE FROM Notificacoes WHERE idRemetente = '" + idUsuario + "' OR idDestinatario ='" + idUsuario + "';";
		PreparedStatement stmt = this.criarStatement(sql);
		stmt.executeUpdate();
		fechar();

		stmt.close();
		return true;
	}

	/* @ yves
	 * NotificacaoService */
	@Override
	public boolean atualizarStatus(Notificacao notificacao) throws SQLException {
		conectar();
		String sql = "UPDATE Notificacoes SET status=?" + "WHERE idNotificacao = '" + notificacao.getIdNotificacao()
				+ "'AND idRemetente = '" + notificacao.getIdRemetente() + "' AND idDestinatario = '"
				+ notificacao.getIdDestinatario() + "';";
		PreparedStatement stmt = criarStatement(sql);
		stmt.setString(1, notificacao.getStatus());
		stmt.executeUpdate();
		fechar();
		stmt.close();
		return true;
	}

	/* @ yves
	 * NotificacoesAdminPresenter */
	@Override
	public ArrayList<Notificacao> getTodasNotificacoesNaoLidasEnderecadasAdmin() throws SQLException {
		conectar();
		ArrayList<Notificacao> listaNots = new ArrayList<>();
		Notificacao not = new Notificacao();
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM Notificacoes WHERE idDestinatario = 1 AND status = 'n??o lida';";

		stmt = criarStatement(sql);

		result = stmt.executeQuery();
		while (result.next()) {
			not = new Notificacao(result.getInt("idNotificacao"), result.getInt("idRemetente"),
					result.getInt("idDestinatario"), result.getString("descricao"), result.getString("data"),
					result.getString("status"));
			listaNots.add(not);
		}
		fechar();
		stmt.close();
		return listaNots;
	}

	/* @ yves
	 * NotificacoesUsuarioPresenter */
	@Override
	public ArrayList<Notificacao> getTodasNotNaoLidasEnderecadasAoUsuario(int idUsuario) throws SQLException {
		conectar();
		ArrayList<Notificacao> listaNots = new ArrayList<>();
		Notificacao not = new Notificacao();
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM Notificacoes WHERE idDestinatario = " + idUsuario + " AND status = 'n??o lida';";

		stmt = criarStatement(sql);
		result = stmt.executeQuery();
		while (result.next()) {
			not = new Notificacao(result.getInt("idNotificacao"), result.getInt("idRemetente"),
					result.getInt("idDestinatario"), result.getString("descricao"), result.getString("data"),
					result.getString("status"));
			listaNots.add(not);
		}
		fechar();

		stmt.close();
		return listaNots;
	}

	/* @ yves
	 * PrincipalAdminPresenter */
	@Override
	public int getQtdNotificacoesNaoLidasEnderecadasAdmin() throws SQLException {
		conectar();
		int qtd = -1;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT COUNT(*) FROM Notificacoes WHERE idDestinatario = 1 AND status = 'n??o lida';";

		stmt = criarStatement(sql);
		result = stmt.executeQuery();
		while (result.next()) {
			qtd = result.getInt(1);
		}
		fechar();

		stmt.close();
		return qtd;
	}

	/* @ yves
	 * PrincipalUserPresenter */
	@Override
	public int getQtdNotificacoesNaoLidasEnderecadasUsuario(int idUsuario) throws SQLException {
		conectar();
		int qtd = -1;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT COUNT(*) FROM Notificacoes WHERE idDestinatario = " + idUsuario
				+ " AND status = 'n??o lida';";

		stmt = criarStatement(sql);
		result = stmt.executeQuery();
		while (result.next()) {
			qtd = result.getInt(1);
		}
		fechar();

		stmt.close();
		return qtd;
	}

	/* @ yves
	 * NotificacaoService */
	@Override
	public int getQtdNotificacoesLidasRemetente(int idRemetente) throws SQLException {
		conectar();
		int qtd = -1;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT COUNT(*) FROM Notificacoes WHERE status = 'lida' AND idRemetente = '" + idRemetente + "';";

		stmt = criarStatement(sql);

		result = stmt.executeQuery();
		while (result.next()) {
			qtd = result.getInt(1);
		}
		fechar();
		stmt.close();
		return qtd;
	}
	
	/* @ yves
	 * NotificacaoService */
	@Override
	public int getQtdNotificacoesEnviadasRemetente(int idRemetente) throws SQLException {
		conectar();
		int qtd = -1;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT COUNT(*) FROM Notificacoes WHERE idRemetente = '" + idRemetente + "';";

		stmt = criarStatement(sql);

		result = stmt.executeQuery();
		while (result.next()) {
			qtd = result.getInt(1);
		}
		fechar();
		stmt.close();
		return qtd;
	}
}
