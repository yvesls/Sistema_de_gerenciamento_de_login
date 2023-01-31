package yvesproject.gestaologin.sistemagestaologin.DAO;

import yvesproject.gestaologin.sistemagestaologin.DAO.interfaces.IFactorySQLiteDAO;

public class ConexaoSingletonDAO {
	private static ConexaoSingletonDAO singleton;

	private UsuarioSQLiteDAO usuarioSqliteDAO;
	private NotificacaoSQLiteDAO notificacaoSqliteDAO;

	private ConexaoSingletonDAO() {
	}

	private void configurar(IFactorySQLiteDAO factorySQLiteDAO) {
		this.usuarioSqliteDAO = factorySQLiteDAO.getUsuarioSQLiteDAO();
		this.notificacaoSqliteDAO = factorySQLiteDAO.getNotificacaoSQLiteDAO();
	}

	public static void configurarSingleton(IFactorySQLiteDAO factorySQLiteDAO) {
		if (singleton == null) {
			singleton = new ConexaoSingletonDAO();
		}
		singleton.configurar(factorySQLiteDAO);
	}

	public static ConexaoSingletonDAO getInstance() {
		return singleton;
	}

	public UsuarioSQLiteDAO getUsuarioSqliteDAO() {
		return usuarioSqliteDAO;
	}

	public NotificacaoSQLiteDAO getNotificacaoSqliteDAO() {
		return notificacaoSqliteDAO;
	}

}
