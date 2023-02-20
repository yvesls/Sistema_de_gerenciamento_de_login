package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.bussiness.log.SingletonLogStrategy;
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

	/* configura todas as ações de interação da view
	 * @ yves
	 * construtor (EnviarNotificacaoAllUsersPresenter) */
	private void regatarAcoesView() {
		view.getBtnEnviarNotificacao().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// processa a notificação e fecha o chat
				if (!view.getTxtMensagem().getText().isEmpty()) {
					enviarNotificacaoAllUsuarios();
				} else {
					JOptionPane.showMessageDialog(null, "Escreva a descrição da notificação.");
				}
				view.getFrame().setVisible(false);
			}
		});
	}
	
	/* realiza o processo de envio de notificação para todos os usuários exceto o de status como 'cancelado'
	 * @ yves
	 * resgatarAcoesView (EnviarNotificacaoAllUsersPresenter) */
	private void enviarNotificacaoAllUsuarios() {
		for(Usuario user : users) {
			if(!user.getState().equals("cancelado")) {
				try {
					notificacao = new Notificacao(
							ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getIdAdministrador(),
							user.getIdUsuario(), view.getTxtMensagem().getText(), "não lida");
					notService = new NotificacaoService(
							new Usuario(ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO()
									.getIdAdministrador(), "administrador"),
							notificacao, principalPresenter);
					if (notService.enviarNotificacaoAdminParaSelecionados()) {
						if (notService.atualizaQtdNotificacoesEnviadas()) {
							SingletonLogStrategy.getInstance().getLog().registrarLog(
									"Envio de notificações", principalPresenter.getAdminLogado().getNome(),
									principalPresenter.getAdminLogado().getTipo());
						} else {
							JOptionPane.showMessageDialog(null,
									"Ocorreu um erro ao atualizar a quantidade de notificação enviada.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar a notificação.");
					}
				} catch (HeadlessException | SQLException | IOException e1) {
					try {
						SingletonLogStrategy.getInstance().getLog().registrarErroLog(e1.getMessage(),
								"Envio de notificações", principalPresenter.getAdminLogado().getNome(),
								principalPresenter.getAdminLogado().getTipo());
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					e1.printStackTrace();
				}
			}
			
		}
	}
}
