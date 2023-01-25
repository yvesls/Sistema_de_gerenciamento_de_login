package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import yvesproject.gestaologin.sistemagestaologin.view.LogarView;
import yvesproject.gestaologin.sistemagestaologin.view.PrincipalAdminView;
import yvesproject.gestaologin.sistemagestaologin.view.RegistrarView;

public class LoginPresenter {
	private LogarView view;

	public LoginPresenter(LogarView view) {
		this.view = view;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnLogar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrincipalAdminView window = new PrincipalAdminView();
				new PrincipalAdminPresenter(window);
				// realiza o login e fecha a janela
				view.getFrame().setVisible(false);
			}
		});

		view.getBtnOpenRegistrar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e abre a janela de registro de novos usuários
				new RegistrarPresenter(new RegistrarView());
			}
		});
	}

}
