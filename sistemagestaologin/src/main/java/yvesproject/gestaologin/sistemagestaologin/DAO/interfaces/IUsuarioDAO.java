package yvesproject.gestaologin.sistemagestaologin.DAO.interfaces;

import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public interface IUsuarioDAO {
	
	public int salvar(Usuario usuario);

	public boolean remover(int idUsuario);
}
