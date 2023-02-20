package yvesproject.gestaologin.sistemagestaologin.DAO.interfaces;

import yvesproject.gestaologin.sistemagestaologin.DAO.NotificacaoSQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.UsuarioSQLiteDAO;

public interface IFactorySQLiteDAO {
	
	public UsuarioSQLiteDAO getUsuarioSQLiteDAO();

	public NotificacaoSQLiteDAO getNotificacaoSQLiteDAO();
}
