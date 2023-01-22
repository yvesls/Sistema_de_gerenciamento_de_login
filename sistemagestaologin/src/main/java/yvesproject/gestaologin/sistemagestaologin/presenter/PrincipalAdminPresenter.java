package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import yvesproject.gestaologin.sistemagestaologin.view.PrincipalAdminView;

public class PrincipalAdminPresenter {
	private PrincipalAdminView view;

	public PrincipalAdminPresenter(PrincipalAdminView view) {
		this.view = view;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnManterUsuarios().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e a view responsável por alterar os dados dos usuários
			}
		});

		view.getBtnOpenEnviarNotificacoes().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e view que permite o envio de mensagens para todos ou para um usuário específico
				
			}
		});
		
		view.getBtnOpenNotificacoes().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e a view de notificações do adminstrador
				
			}
		});
	}
}
