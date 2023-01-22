package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import yvesproject.gestaologin.sistemagestaologin.view.RegistrarView;

public class RegistrarPresenter {
	private RegistrarView view;

	public RegistrarPresenter(RegistrarView view) {
		this.view = view;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		
		view.getBtnRegistrar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// realiza o cadastro e fecha a janela
				view.getFrame().setVisible(false);
			}
		});
	}
}
