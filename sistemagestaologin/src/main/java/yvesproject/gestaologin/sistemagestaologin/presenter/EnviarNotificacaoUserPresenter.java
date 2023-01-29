package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Subject;
import yvesproject.gestaologin.sistemagestaologin.model.Notificacao;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;
import yvesproject.gestaologin.sistemagestaologin.service.NotificacaoService;
import yvesproject.gestaologin.sistemagestaologin.view.EnviarNotificacaoUserView;

public class EnviarNotificacaoUserPresenter {
	private EnviarNotificacaoUserView view;
	private Usuario usuarioSelecionado;
	private NotificacaoService notService;
	private Notificacao notificacao;
	private PrincipalAdminPresenter principalPresenter;

	public EnviarNotificacaoUserPresenter(EnviarNotificacaoUserView view, Usuario usuarioSelecionado, PrincipalAdminPresenter principalPresenter) {
		this.view = view;
		this.usuarioSelecionado = usuarioSelecionado;
		this.principalPresenter = principalPresenter;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnEnviarNotificacao().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// processa a notificação e fecha o chat
				if (!view.getTxtMensagem().getText().isEmpty()) {
					enviarNotificacao();
				} else {
					JOptionPane.showMessageDialog(null, "Escreva a descrição da notificação.");
				}
				view.getFrame().setVisible(false);
			}
		});
	}
	
	public void enviarNotificacao() {
		notificacao = new Notificacao(1, usuarioSelecionado.getIdUsuario(), view.getTxtMensagem().getText(),"não lida");
		notService = new NotificacaoService(usuarioSelecionado, notificacao, principalPresenter);
		if (notService.enviarNotificacaoAdminParaSelecionados()) {
			notService.atualizaQtdNotificacoesEnviadas();
		} else {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar a notificação.");
		}
	}
}
