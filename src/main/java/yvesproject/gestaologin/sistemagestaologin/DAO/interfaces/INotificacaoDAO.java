package yvesproject.gestaologin.sistemagestaologin.DAO.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import yvesproject.gestaologin.sistemagestaologin.model.Notificacao;

public interface INotificacaoDAO {
	
	public boolean salvar(Notificacao notificacao) throws SQLException;
	
	public boolean removerTodasNotificacoesUser(int idUsuario) throws SQLException;
	
	public boolean atualizarStatus(Notificacao notificacao) throws SQLException;
	
	public ArrayList<Notificacao> getTodasNotificacoesNaoLidasEnderecadasAdmin() throws SQLException;
	
	public ArrayList<Notificacao> getTodasNotNaoLidasEnderecadasAoUsuario(int idUsuario) throws SQLException;
	
	public int getQtdNotificacoesNaoLidasEnderecadasAdmin() throws SQLException;
	
	public int getQtdNotificacoesNaoLidasEnderecadasUsuario(int idUsuario) throws SQLException;
	
	public int getQtdNotificacoesLidasRemetente(int idRemetente) throws SQLException;
	
	public int getQtdNotificacoesEnviadasRemetente(int idRemetente) throws SQLException;
}
