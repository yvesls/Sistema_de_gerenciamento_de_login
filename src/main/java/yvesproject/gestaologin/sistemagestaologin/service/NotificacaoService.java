package yvesproject.gestaologin.sistemagestaologin.service;

import java.awt.HeadlessException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Observer;
import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Subject;
import yvesproject.gestaologin.sistemagestaologin.model.Notificacao;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public class NotificacaoService extends Subject implements Observer {
	private Usuario usuario;
	private Notificacao notificacao;
	private int qtdNotLidas;
	private Notificacao notEnvio;
	private int qtdNotEnviadas;
	private Observer observador;

	public NotificacaoService() {
	}

	/* construtor instanciado quando é necessário passar um usuário para realizar um processo de notificação
	 * @ yves
	 * getNotificacoesService (NotificacoesUsuarioPresenter), getNotificacoesService (NotificacoesAdminPresenter) */
	public NotificacaoService(Usuario usuario, Notificacao notificacao, Observer observador) {
		this.usuario = usuario;
		this.notificacao = notificacao;
		this.observador = observador;
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
	}

	/* construtor padrão da classe quando é necessário passar a notificação e o observador para devolver uma notificação e atualizar a view 
	 * @ yves
	 * enviarNotificacao (EnviarNotificacaoUserPresenter), enviarNotificacaoAllUsuarios (EnviarNotificacaoAllUsersPresenter) */
	public NotificacaoService(Notificacao notificacao, Observer observador) {
		this.notificacao = notificacao;
		this.observador = observador;
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
	}

	/* atualiza o status da notificação enviada para lida
	 * @ yves
	 * update (NotificacaoService), enviarNotificacao (EnviarNotificacaoUserPresenter), enviarNotificacaoAllUsuarios (EnviarNotificacaoAllUsersPresenter) */
	public boolean atualizarStatusNotificacao() throws SQLException {
		notificacao.setStatus("lida");
		return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().atualizarStatus(this.notificacao);
	}

	/* atualiza o status da notificação enviada para lida
	 * @ yves
	 * update (NotificacaoService), enviarNotificacao (EnviarNotificacaoUserPresenter), enviarNotificacaoAllUsuarios (EnviarNotificacaoAllUsersPresenter) */
	public boolean atualizarQtdNotificacaoLida() throws SQLException {
		qtdNotLidas = ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
				.getQtdNotificacoesLidasRemetente(notificacao.getIdRemetente());
		Usuario usuarioNot = new Usuario();
		usuarioNot.setNotLidas(qtdNotLidas++);
		usuarioNot.setIdUsuario(notificacao.getIdRemetente());
		return ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().atualizarQtdNotificacoesLidas(usuarioNot);
	}

	/* envia uma notificação de confirmação e boas vindas para o usuário
	 * @ yves
	 * update (NotificacaoService) */
	public boolean notificarConfirmacaoPadraoBoasVindas() throws SQLException {
		notEnvio = new Notificacao(ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getIdAdministrador(),
				notificacao.getIdRemetente(), "Bem vindo!", "não lida");
		return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().salvar(notEnvio);
	}

	/* envia uma notificação de confirmação e boas vindas para o usuário
	 * @ yves
	 * enviarNotificacao (EnviarNotificacaoUserPresenter), enviarNotificacaoAllUsuarios (EnviarNotificacaoAllUsersPresenter) */
	public boolean enviarNotificacaoAdminParaSelecionados() throws SQLException {
		return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().salvar(notificacao);
	}

	public boolean atualizaQtdNotificacoesEnviadas() throws HeadlessException, SQLException {
		Usuario atualizarQtdNotEnv = new Usuario();
		qtdNotEnviadas = ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
				.getQtdNotificacoesEnviadasRemetente(usuario.getIdUsuario());
		atualizarQtdNotEnv.setNotEnviadas(qtdNotEnviadas++);
		atualizarQtdNotEnv.setIdUsuario(usuario.getIdUsuario());
		if (ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO()
				.atualizarQtdNotificacoesEnviadas(atualizarQtdNotEnv)) {
			add(observador);
			notifyObservers("atualizar lista de notificações");
			return true;

		}
		return false;
	}
	
	/* envia notificação de solicitação de autentificação do usuário para o administrador ao realizar registro
	 * @ yves
	 * executarRegistro (RegistrarPresenter) */
	public boolean enviarNotificacaoDeAutentificacao(int idGerado, Usuario novoUsuario) throws SQLException {
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		this.notificacao = new Notificacao(idGerado,
				ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getIdAdministrador(),
				"O usuário " + novoUsuario.getNome() + " solicita autorização de login.", "não lida");
		if (ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().salvar(this.notificacao)) {
			novoUsuario = ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getUsuarioPorId(idGerado);
			novoUsuario.setNotEnviadas(1);
			ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().atualizar(novoUsuario);
			return true;
		}
		return false;
	}

	@Override
	public void update(String status) throws HeadlessException, SQLException {
		if (status.equals("administrador valida login do usuário")) {
			if (atualizarStatusNotificacao()) {
				if (atualizarQtdNotificacaoLida()) {
					if (notificarConfirmacaoPadraoBoasVindas()) {
						if (atualizaQtdNotificacoesEnviadas()) {
						} else {
							JOptionPane.showMessageDialog(null,
									"Ocorreu um erro inesperado ao atualizar a sua quantidade de notificação enviada. Tente novamente mais tarde.",
									"Atenção", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Ocorreu um erro inesperado ao enviar notificação de confirmação para o usuário. Tente novamente mais tarde.",
								"Atenção", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Ocorreu um erro inesperado ao atualizar a quantidade de notificação lida do usuário. Tente novamente mais tarde.",
							"Atenção", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Ocorreu um erro inesperado ao atualizar o status da notificação. Tente novamente mais tarde.",
						"Atenção", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (status.equals("usuário leu a notificação")) {
			if (atualizarStatusNotificacao()) {
				if (atualizarQtdNotificacaoLida()) {
				} else {
					JOptionPane.showMessageDialog(null,
							"Ocorreu um erro inesperado ao atualizar a quantidade de notificação lida do usuário. Tente novamente mais tarde.",
							"Atenção", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Ocorreu um erro inesperado ao atualizar o status da notificação. Tente novamente mais tarde.",
						"Atenção", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (status.equals("administrador invalida login do usuário")) {
			if (atualizarStatusNotificacao()) {
			} else {
				JOptionPane.showMessageDialog(null,
						"Ocorreu um erro inesperado ao atualizar o status da notificação. Tente novamente mais tarde.",
						"Atenção", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		add(observador);
		notifyObservers("atualizar lista de notificações");
	}
}
