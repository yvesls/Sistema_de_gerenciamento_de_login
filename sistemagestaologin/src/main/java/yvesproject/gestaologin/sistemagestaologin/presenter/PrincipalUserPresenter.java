package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import yvesproject.gestaologin.sistemagestaologin.view.PrincipalUserView;

public class PrincipalUserPresenter {
	private PrincipalUserView view;

	public PrincipalUserPresenter(PrincipalUserView view) {
		this.view = view;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnOpenAlterarSenha().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e a view responsável por alterar a senha do usuário
				view.getFrame().setVisible(false);
			}
		});
		
		view.getBtnOpenNotificacoes().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e a view de notificações do usuario
				
			}
		});
	}
}
