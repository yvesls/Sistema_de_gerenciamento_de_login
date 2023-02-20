package yvesproject.gestaologin.sistemagestaologin.DAO.interfaces;

import java.sql.SQLException;

import yvesproject.gestaologin.sistemagestaologin.model.Notificacao;

public interface INotificacaoDAO {
	
	public boolean salvar(Notificacao notificacao) throws SQLException;
	
	public boolean remover() throws SQLException;
}
