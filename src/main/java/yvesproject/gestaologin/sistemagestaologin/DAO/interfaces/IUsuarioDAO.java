package yvesproject.gestaologin.sistemagestaologin.DAO.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public interface IUsuarioDAO {
	
	public int salvar(Usuario usuario) throws SQLException;

	public boolean remover(int idUsuario) throws SQLException;
	
	public boolean atualizar(Usuario usuario) throws SQLException;
	
	public boolean atualizarQtdNotificacoesLidas(Usuario usuario) throws SQLException;
	
	public boolean atualizarQtdNotificacoesEnviadas(Usuario usuario) throws SQLException;
	
	public boolean atualizarStateUsuario(Usuario usuario) throws SQLException;
	
	public Boolean getIsUsuarios() throws SQLException;
	
	public ArrayList<Usuario> getUsuariosPorNome(String nome) throws SQLException;
	
	public Usuario getUserIsRegister(Usuario usuario) throws SQLException;
	
	public Usuario getUsuarioPorId(int idUsuario) throws SQLException;
	
	public ArrayList<Usuario> getTodosUsuarios() throws SQLException;
	
	public int getIdAdministrador() throws SQLException;
}
