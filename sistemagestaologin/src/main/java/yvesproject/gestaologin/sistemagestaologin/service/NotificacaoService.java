package yvesproject.gestaologin.sistemagestaologin.service;

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

	public NotificacaoService(Usuario usuario, Notificacao notificacao, Observer observador) {
		this.usuario = usuario;
		this.notificacao = notificacao;
		this.observador = observador;
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
	}

	public NotificacaoService(Notificacao notificacao, Observer observador) {
		this.notificacao = notificacao;
		this.observador = observador;
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
	}

	// atualiza o status da notificação
	public boolean atualizarStatusNotificacao() {
		notificacao.setStatus("lida");
		return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().atualizarStatus(this.notificacao);
	}

	public boolean atualizarQtdNotificacaoLida() {
		qtdNotLidas = ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
				.getQtdNotificacoesLidasRemetente(notificacao.getIdRemetente());
		Usuario usuarioNot = new Usuario();
		usuarioNot.setNotLidas(qtdNotLidas++);
		usuarioNot.setIdUsuario(notificacao.getIdRemetente());
		return ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().atualizarQtdNotificacoesLidas(usuarioNot);
	}

	// envia uma notificação de confirmação e boas vindas para o usuário.
	public boolean notificarConfirmacaoPadraoBoasVindas() {
		notEnvio = new Notificacao(ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getIdAdministrador(), notificacao.getIdRemetente(), "Bem vindo!", "não lida");
		return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().salvar(notEnvio);
	}

	public boolean enviarNotificacaoAdminParaSelecionados() {
		return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().salvar(notificacao);
	}

	public boolean atualizaQtdNotificacoesEnviadas() {
		Usuario atualizarQtdNotEnv = new Usuario();
		qtdNotEnviadas = ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
				.getQtdNotificacoesEnviadasRemetente(usuario.getIdUsuario());
		atualizarQtdNotEnv.setNotEnviadas(qtdNotEnviadas++);
		atualizarQtdNotEnv.setIdUsuario(usuario.getIdUsuario());
		if (ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO()
				.atualizarQtdNotificacoesEnviadas(atualizarQtdNotEnv)) {
			if (usuario.getTipo().equals("administrador")) {
				add(observador);
				notifyObservers("atualizar lista de notificações");
				return true;
			} else {
				add(observador);
				notifyObservers("atualizar lista de notificações usuário");
				return true;
			}
		}
		return false;
	}

	public boolean enviarNotificacaoDeAutentificacao(int idGerado, Usuario novoUsuario) {

		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		this.notificacao = new Notificacao(idGerado, ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getIdAdministrador(),
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
	public void update(String status) {
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
					if (atualizaQtdNotificacoesEnviadas()) {
						
					} else {
						JOptionPane.showMessageDialog(null,
								"Ocorreu um erro inesperado ao atualizar a sua quantidade de notificação enviada. Tente novamente mais tarde.",
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
		}
	}
}
