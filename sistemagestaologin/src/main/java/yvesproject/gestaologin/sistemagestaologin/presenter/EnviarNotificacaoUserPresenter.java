package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import yvesproject.gestaologin.sistemagestaologin.view.EnviarNotificacaoUserView;

public class EnviarNotificacaoUserPresenter {
	private EnviarNotificacaoUserView view;

	public EnviarNotificacaoUserPresenter(EnviarNotificacaoUserView view) {
		this.view = view;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnEnviarNotificacao().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// processa a notificação e fecha o chat
				view.getFrame().setVisible(false);
			}
		});
	}
}
