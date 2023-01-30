package yvesproject.gestaologin.sistemagestaologin.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import yvesproject.gestaologin.sistemagestaologin.DAO.interfaces.IUsuarioDAO;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public class UsuarioSQLiteDAO extends ConexaoSQLiteDAO implements IUsuarioDAO {

	@Override
	public int salvar(Usuario usuario) {
		PreparedStatement stmt = null;
		int idGerado = -1;
		ResultSet result = null;
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
			result = stmt.getGeneratedKeys();
			if (result.next()) {
				idGerado = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return idGerado;
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
		return idGerado;
	}

	@Override
	public boolean remover(int idUsuario) {
		conectar();
		String sql = "DELETE FROM Usuario WHERE idUsuario = '" + idUsuario + "';";
		PreparedStatement stmt = this.criarStatement(sql);
		try {
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

	public boolean atualizar(Usuario usuario) {
		conectar();
		String sql = "UPDATE Usuario SET email=?, "
				+ "senha=?, tipo=?, status=?, nome=?, cpf=?, notificacoesEnviadas=?, notificacoesLidas=?, dataCadastro = ? WHERE idUsuario = '"
				+ usuario.getIdUsuario() + "'";
		PreparedStatement stmt = criarStatement(sql);
		try {
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

	public boolean atualizarQtdNotificacoesLidas(Usuario usuario) {
		conectar();
		String sql = "UPDATE Usuario SET notificacoesLidas=? WHERE idUsuario = '" + usuario.getIdUsuario() + "';";
		PreparedStatement stmt = criarStatement(sql);
		try {
			stmt.setInt(1, usuario.getNotLidas());
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

	public boolean atualizarQtdNotificacoesEnviadas(Usuario usuario) {
		conectar();
		String sql = "UPDATE Usuario SET notificacoesEnviadas=? WHERE idUsuario = '" + usuario.getIdUsuario() + "';";
		PreparedStatement stmt = criarStatement(sql);
		try {
			stmt.setInt(1, usuario.getNotEnviadas());
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

	public boolean atualizarStateUsuario(Usuario usuario) {
		conectar();
		String sql = "UPDATE Usuario SET status=? WHERE idUsuario = '"
				+ usuario.getIdUsuario() + "';";
		PreparedStatement stmt = criarStatement(sql);
		try {
			stmt.setString(1, usuario.getState());
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
	
	public Boolean getIsUsuarios() {
		conectar();
		ResultSet result = null;
		Boolean temRegistro = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM Usuario;";

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

	public ArrayList<Usuario> getUsuariosPorNome(String nome) {
		conectar();
		ArrayList<Usuario> listaUsers = new ArrayList<>();
		ResultSet result = null;
		PreparedStatement stmt = null;
		Usuario user = new Usuario();

		String sql = "SELECT idUsuario, email, senha, tipo, status, nome, cpf, notificacoesEnviadas, notificacoesLidas, dataCadastro FROM Usuario WHERE"
				+ " nome LIKE '%" + nome + "%' AND tipo = 'usuario';";
		stmt = this.criarStatement(sql);

		try {
			stmt.executeQuery();
			result = stmt.executeQuery();
			while (result.next()) {
				user = new Usuario(result.getInt("idUsuario"), result.getString("email"), result.getString("senha"),
						result.getString("tipo"), result.getString("status"), result.getString("nome"),
						result.getString("cpf"), result.getInt("notificacoesEnviadas"),
						result.getInt("notificacoesLidas"), result.getString("dataCadastro"));

				listaUsers.add(user);
			}
			fechar();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return listaUsers;
	}

	public Usuario getUserIsRegister(Usuario usuario) {
		conectar();
		Usuario user = null;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = ""
				+ "SELECT idUsuario, email, senha, tipo, status, nome, cpf, notificacoesEnviadas, notificacoesLidas, dataCadastro FROM Usuario WHERE email = '"
				+ usuario.getEmail() + "' AND " + "senha = '" + usuario.getSenha() + "';";

		stmt = criarStatement(sql);
		try {
			result = stmt.executeQuery();
			if (result.next()) {
				user = new Usuario(result.getInt("idUsuario"), result.getString("email"), result.getString("senha"),
						result.getString("tipo"), result.getString("status"), result.getString("nome"),
						result.getString("cpf"), result.getInt("notificacoesEnviadas"),
						result.getInt("notificacoesLidas"), result.getString("dataCadastro"));
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
		return user;
	}

	public Usuario getUsuarioPorId(int idUsuario) {
		conectar();
		Usuario user = null;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = ""
				+ "SELECT idUsuario, email, senha, tipo, status, nome, cpf, notificacoesEnviadas, notificacoesLidas, dataCadastro FROM Usuario WHERE idUsuario = '"
				+ idUsuario + "';";

		stmt = criarStatement(sql);
		try {
			result = stmt.executeQuery();
			if (result.next()) {
				user = new Usuario(result.getInt("idUsuario"), result.getString("email"), result.getString("senha"),
						result.getString("tipo"), result.getString("status"), result.getString("nome"),
						result.getString("cpf"), result.getInt("notificacoesEnviadas"),
						result.getInt("notificacoesLidas"), result.getString("dataCadastro"));
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
		return user;
	}

	public ArrayList<Usuario> getTodosUsuarios() {
		conectar();
		ArrayList<Usuario> listaUsers = new ArrayList<>();
		Usuario user = new Usuario();
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT * FROM Usuario WHERE tipo != 'administrador';";

		stmt = criarStatement(sql);
		try {
			result = stmt.executeQuery();
			while (result.next()) {
				user = new Usuario(result.getInt("idUsuario"), "", "", result.getString("tipo"),
						result.getString("status"), result.getString("nome"), result.getString("cpf"),
						result.getInt("notificacoesEnviadas"), result.getInt("notificacoesLidas"),
						result.getString("dataCadastro"));
				listaUsers.add(user);
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
		return listaUsers;
	}
	
	public int getIdAdministrador() {
		conectar();
		int user = -1;
		ResultSet result = null;
		PreparedStatement stmt = null;
		String sql = "SELECT idUsuario FROM Usuario LIMIT 1;";

		stmt = criarStatement(sql);
		try {
			result = stmt.executeQuery();
			if (result.next()) {
				user = result.getInt("idUsuario");
			}
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
		fechar();
		return user;
	}
}
