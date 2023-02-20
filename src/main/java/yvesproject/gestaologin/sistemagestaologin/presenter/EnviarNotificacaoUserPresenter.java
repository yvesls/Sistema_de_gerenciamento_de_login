package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.bussiness.log.SingletonLogStrategy;
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

	public EnviarNotificacaoUserPresenter(EnviarNotificacaoUserView view, Usuario usuarioSelecionado,
			PrincipalAdminPresenter principalPresenter) {
		this.view = view;
		this.usuarioSelecionado = usuarioSelecionado;
		this.principalPresenter = principalPresenter;
		regatarAcoesView();
	}

	/* configura todas as ações de interação da view
	 * @ yves
	 * construtor (EnviarNotificacaoUserPresenter) */
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

	/* realiza o processo de envio de notificação para o usuário selecionado
	 * @ yves
	 * resgatarAcoesView (EnviarNotificacaoUserPresenter) */
	private void enviarNotificacao() {
		try {
			int idAdmin = ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getIdAdministrador();
			notificacao = new Notificacao(idAdmin, usuarioSelecionado.getIdUsuario(), view.getTxtMensagem().getText(),
					"não lida");
			notService = new NotificacaoService(new Usuario(idAdmin, "administrador"), notificacao, principalPresenter);
			if (notService.enviarNotificacaoAdminParaSelecionados()) {
				if (notService.atualizaQtdNotificacoesEnviadas()) {
					SingletonLogStrategy.getInstance().getLog().registrarLog("Envio de notificações",
							this.principalPresenter.getAdminLogado().getNome(),
							this.principalPresenter.getAdminLogado().getTipo());
				} else {
					JOptionPane.showMessageDialog(null,
							"Ocorreu um erro ao atualizar a quantidade de notificação enviada.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar a notificação.");
			}
		} catch (HeadlessException | SQLException | IOException e) {
			e.printStackTrace();
			try {
				SingletonLogStrategy.getInstance().getLog().registrarErroLog(e.getMessage() ,"Envio de notificações",
						this.principalPresenter.getAdminLogado().getNome(),
						this.principalPresenter.getAdminLogado().getTipo());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
