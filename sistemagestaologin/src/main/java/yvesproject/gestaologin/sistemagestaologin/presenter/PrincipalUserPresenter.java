package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Observer;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;
import yvesproject.gestaologin.sistemagestaologin.view.AlterarSenhaUserView;
import yvesproject.gestaologin.sistemagestaologin.view.NotificacoesView;
import yvesproject.gestaologin.sistemagestaologin.view.PrincipalUserView;

public class PrincipalUserPresenter implements Observer {
	private PrincipalUserPresenter thisPresenter;
	private PrincipalUserView view;
	private Usuario usuarioLogado;
	private String status;
	
	public PrincipalUserPresenter(PrincipalUserView view, Usuario usuarioLogado) {
		this.view = view;
		this.usuarioLogado = usuarioLogado;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnOpenAlterarSenha().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e a view responsável por alterar a senha do usuário
				AlterarSenhaUserView window = new AlterarSenhaUserView();
				new AlterarSenhaUserPresenter(window);
			}
		});
		
		view.getBtnOpenNotificacoes().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e a view de notificações do usuario
				NotificacoesView window = new NotificacoesView();
				new NotificacoesPresenter(window, thisPresenter);
			}
		});
	}

	@Override
	public void update(String status) {
		if(status.equals("login usuario")) {
			
		}else {
		}
	}
}
