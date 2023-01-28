package yvesproject.gestaologin.sistemagestaologin.service;

import javax.swing.JOptionPane;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Observer;
import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Subject;
import yvesproject.gestaologin.sistemagestaologin.model.Notificacao;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;
import yvesproject.gestaologin.sistemagestaologin.presenter.NotificacoesPresenter;

public class NotificacaoService extends Subject implements Observer {
	private Usuario usuario;
	private Notificacao notificacao;
	private int qtdNotLidas;
	private Notificacao notEnvio;
	private int qtdNotEnviadas;
	private Observer observador;

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
		usuario = new Usuario();
		usuario.setNotLidas(qtdNotLidas++);
		usuario.setIdUsuario(notificacao.getIdRemetente());
		return ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().atualizarQtdNotificacoesLidas(usuario);
	}

	// envia uma notificação de confirmação e boas vindas para o usuário.
	public boolean notificarConfirmacaoPadraoBoasVindas() {
		notEnvio = new Notificacao(1, usuario.getIdUsuario(), "Bem vindo!", "não lida");
		return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().salvar(notEnvio);
	}
	
	public boolean enviarNotificacaoAdminParaSelecionados() {
		return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().salvar(notificacao);
	}

	public void atualizaQtdNotificacoesEnviadasAdmin() {
		usuario = new Usuario();
		qtdNotEnviadas = ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
				.getQtdNotificacoesEnviadasRemetente(1);
		usuario.setNotEnviadas(qtdNotEnviadas++);
		usuario.setIdUsuario(1);
		if (ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().atualizarQtdNotificacoesEnviadas(usuario)) {
			add(observador);
			notifyObservers("atualizar lista de notificações");
			JOptionPane.showMessageDialog(null, "Notificação atualizada com sucesso.", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,
					"Ocorreu um erro inesperado ao atualizar a sua quantidade de notificação enviada. Tente novamente mais tarde.",
					"Atenção", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void update(String status) {
		if (status.equals("administrador valida login do usuário")) {
			if (atualizarStatusNotificacao()) {
				if (atualizarQtdNotificacaoLida()) {
					if (notificarConfirmacaoPadraoBoasVindas()) {
						atualizaQtdNotificacoesEnviadasAdmin();
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
		} else {
		}
	}
}
