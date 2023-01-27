package yvesproject.gestaologin.sistemagestaologin.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import yvesproject.gestaologin.sistemagestaologin.DAO.interfaces.INotificacaoDAO;
import yvesproject.gestaologin.sistemagestaologin.model.Notificacao;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public class NotificacaoSQLiteDAO extends ConexaoSQLiteDAO implements INotificacaoDAO {

	@Override
	public boolean salvar(Notificacao notificacao) {
		PreparedStatement stmt = null;
		try {
			conectar();
			String sql = ""
					+ "INSERT INTO Notificacoes (idRemetente, idDestinatario, descricao, status, data) "
					+ "VALUES (?, ?, ?, ?, ?);";
			
			stmt = criarStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, notificacao.getIdRemetente());
			stmt.setInt(2, notificacao.getIdDestinatario());
			stmt.setString(3, notificacao.getDescricao());
			stmt.setString(4, notificacao.getStatus());
			stmt.setString(5, notificacao.getData());
			
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
	
	public boolean atualizarStatus(Notificacao notificacao){
		conectar();
		String sql = "UPDATE Notificacoes SET status=?"
				+ "WHERE idNotificacao = '" + notificacao.getIdNotificacao() + "'AND idRemetente = '" + notificacao.getIdRemetente() + "' AND idDestinatario = '"+ notificacao.getIdDestinatario() +"';";
		PreparedStatement stmt = criarStatement(sql);
		try {
			stmt.setString(1, notificacao.getStatus());
			stmt.executeUpdate();
			fechar();
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
		return true;
	}
	public ArrayList<Notificacao> getTodasNotificacoesEnderecadasAdmin(){
		conectar();
		ArrayList<Notificacao> listaNots = new ArrayList<>();
		Notificacao not = new Notificacao();
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM Notificacoes WHERE idDestinatario = 1;";

		stmt = criarStatement(sql);
		try {
			result = stmt.executeQuery();
			while (result.next()) {
				not = new Notificacao(result.getInt("idNotificacao"), result.getInt("idRemetente"), result.getInt("idDestinatario"), result.getString("descricao"),
						result.getString("status"), result.getString("data"));
				listaNots.add(not);
			}
			fechar();
		} catch (SQLException e) {
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
		return listaNots;
	}
	
	public int getQtdNotificacoesEnderecadasAdmin() {
		conectar();
		int qtd = -1;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT COUNT(*) FROM Notificacoes WHERE idDestinatario = 1;";

		stmt = criarStatement(sql);
		try {
			result = stmt.executeQuery();
			while (result.next()) {
				qtd = result.getInt(1);
			}
			fechar();
		} catch (SQLException e) {
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
		return qtd;
	}
	
	public int getQtdNotificacoesLidasRemetente(int idRemetente) {
		conectar();
		int qtd = -1;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT COUNT(*) FROM Notificacoes WHERE status = 'lida' AND idRemetente = '" + idRemetente + "';";

		stmt = criarStatement(sql);
		try {
			result = stmt.executeQuery();
			while (result.next()) {
				qtd = result.getInt(1);
			}
			fechar();
		} catch (SQLException e) {
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
		return qtd;
	}
	
	public int getQtdNotificacoesEnviadasRemetente(int idRemetente) {
		conectar();
		int qtd = -1;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT COUNT(*) FROM Notificacoes WHERE idRemetente = '" + idRemetente + "';";

		stmt = criarStatement(sql);
		try {
			result = stmt.executeQuery();
			while (result.next()) {
				qtd = result.getInt(1);
			}
			fechar();
		} catch (SQLException e) {
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
		return qtd;
	}
}
