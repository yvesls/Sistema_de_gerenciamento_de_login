package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import yvesproject.gestaologin.sistemagestaologin.view.AlterarSenhaUserView;
import yvesproject.gestaologin.sistemagestaologin.view.PrincipalUserView;

public class AlterarSenhaUserPresenter {
	private AlterarSenhaUserView view;

	public AlterarSenhaUserPresenter(AlterarSenhaUserView view) {
		this.view = view;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnSalvarNovaSenha().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrincipalUserView window = new PrincipalUserView();
				new PrincipalUserPresenter(window, null);
				// Valida a nova senha e registra-a
				view.getFrame().setVisible(false);
			}
		});
	}

}
