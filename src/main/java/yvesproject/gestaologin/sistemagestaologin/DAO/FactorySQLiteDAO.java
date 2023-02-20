package yvesproject.gestaologin.sistemagestaologin.DAO;

import yvesproject.gestaologin.sistemagestaologin.DAO.interfaces.IFactorySQLiteDAO;

public class FactorySQLiteDAO implements IFactorySQLiteDAO {
	
	@Override
	public UsuarioSQLiteDAO getUsuarioSQLiteDAO() {
		return new UsuarioSQLiteDAO();
	}

	@Override
	public NotificacaoSQLiteDAO getNotificacaoSQLiteDAO() {
		return new NotificacaoSQLiteDAO();
	}
}
