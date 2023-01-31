package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import yvesproject.gestaologin.sistemagestaologin.bussiness.log.SingletonLogStrategy;
import yvesproject.gestaologin.sistemagestaologin.view.ConfiguracoesAdminView;

public class ConfiguracoesAdminPresenter {
	private ConfiguracoesAdminView view;

	public ConfiguracoesAdminPresenter(ConfiguracoesAdminView view) {
		this.view = view;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnConfirmar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(view.getLogCsv().isSelected()) {
					SingletonLogStrategy.getInstance().getLog().modificarParaCsvState(SingletonLogStrategy.getInstance().getLog());
				}else {
					SingletonLogStrategy.getInstance().getLog().modificarParaJsonState(SingletonLogStrategy.getInstance().getLog());
				}
			}
		});

		this.view.getLogCsv().addActionListener((ActionEvent e) -> {
			this.configuracaoButtonRadioCsv();
		});

		this.view.getLogJson().addActionListener((ActionEvent e) -> {
			this.configuracaoButtonRadioJson();
		});
	}

	public void configuracaoButtonRadioCsv() {
		if (this.view.getLogCsv().isSelected()) {
			if (this.view.getLogJson().isSelected()) {
				this.view.getLogJson().setSelected(false);
			} else {
				this.view.getLogJson().setSelected(true);
			}
		} else {
			this.view.getLogCsv().setSelected(true);
		}
	}

	public void configuracaoButtonRadioJson() {
		if (this.view.getLogJson().isSelected()) {
			if (this.view.getLogCsv().isSelected()) {
				this.view.getLogCsv().setSelected(false);
			} else {
				this.view.getLogCsv().setSelected(true);
			}
		} else {
			this.view.getLogJson().setSelected(true);
		}
	}
}
