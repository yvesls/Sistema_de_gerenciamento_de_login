package yvesproject.gestaologin.sistemagestaologin.DAO.interfaces;

import java.sql.SQLException;

import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public interface IUsuarioDAO {
	
	public int salvar(Usuario usuario) throws SQLException;

	public boolean remover(int idUsuario) throws SQLException;
}
