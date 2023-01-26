package yvesproject.gestaologin.sistemagestaologin.DAO.interfaces;

import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public interface IUsuarioDAO {
	
	public boolean salvar(Usuario usuario);

	public boolean remover();
}
