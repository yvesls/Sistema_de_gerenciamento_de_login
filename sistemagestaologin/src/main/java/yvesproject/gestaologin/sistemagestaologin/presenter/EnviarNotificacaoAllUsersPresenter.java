package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.model.Notificacao;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;
import yvesproject.gestaologin.sistemagestaologin.service.NotificacaoService;
import yvesproject.gestaologin.sistemagestaologin.view.EnviarNotificacaoAllUsersView;

public class EnviarNotificacaoAllUsersPresenter {
	private EnviarNotificacaoAllUsersView view;
	private ArrayList<Usuario> users;
	private Notificacao notificacao;
	private NotificacaoService notService;
	private PrincipalAdminPresenter principalPresenter;

	public EnviarNotificacaoAllUsersPresenter(EnviarNotificacaoAllUsersView view,ArrayList<Usuario> users, PrincipalAdminPresenter principalPresenter) {
		this.view = view;
		this.users = users;
		this.principalPresenter = principalPresenter;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnEnviarNotificacao().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// processa a notificação e fecha o chat
				if (!view.getTxtMensagem().getText().isEmpty()) {
					for(Usuario user : users) {
						notificacao = new Notificacao(1, user.getIdUsuario(), view.getTxtMensagem().getText(),"não lida");
						notService = new NotificacaoService(new Usuario(ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getIdAdministrador(), "administrador"), notificacao, principalPresenter);
						if (notService.enviarNotificacaoAdminParaSelecionados()) {
							if(notService.atualizaQtdNotificacoesEnviadas()) {
							}else {
								JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar a quantidade de notificação enviada.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar a notificação.");
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Escreva a descrição da notificação.");
				}
				view.getFrame().setVisible(false);
			}
		});
	}
}
