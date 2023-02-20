package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import yvesproject.gestaologin.sistemagestaologin.bussiness.log.SingletonLogStrategy;
import yvesproject.gestaologin.sistemagestaologin.view.ConfiguracoesAdminView;

public class ConfiguracoesAdminPresenter {
	private ConfiguracoesAdminView view;

	public ConfiguracoesAdminPresenter(ConfiguracoesAdminView view) {
		this.view = view;
		configuraButtonRadio();
		regatarAcoesView();
	}

	/* configura todas as ações de interação da view
	 * @ yves
	 * construtor (ConfiguracoesAdminPresenter) */
	private void regatarAcoesView() {
		view.getBtnConfirmar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(view.getLogCsv().isSelected()) {
					SingletonLogStrategy.getInstance().getLog().modificarParaCsvState(SingletonLogStrategy.getInstance().getLog());
				}else {
					SingletonLogStrategy.getInstance().getLog().modificarParaJsonState(SingletonLogStrategy.getInstance().getLog());
				}
				view.getFrame().setVisible(false);
			}
		});

		this.view.getLogCsv().addActionListener((ActionEvent e) -> {
			this.configuracaoButtonRadioCsv();
		});

		this.view.getLogJson().addActionListener((ActionEvent e) -> {
			this.configuracaoButtonRadioJson();
		});
	}

	/* configura a maneira como o botão radio do csv vai se portar ao ser clicado
	 * @ yves
	 * resgatarAcoesView (ConfiguracoesAdminPresenter) */
	private void configuracaoButtonRadioCsv() {
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

	/* configura a maneira como o botão radio do json vai se portar ao ser clicado
	 * @ yves
	 * resgatarAcoesView (ConfiguracoesAdminPresenter) */
	private void configuracaoButtonRadioJson() {
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
	
	/* configura qual botão vai estar ativado de acordo com qual forma de registro log está persistida nas configurações do properties
	 * @ yves
	 * resgatarAcoesView (ConfiguracoesAdminPresenter) */
	private void configuraButtonRadio() {
		Properties props = new Properties();
		InputStream input;
		try {
			input = new FileInputStream("config.properties");
			props.load(input);
			if(props.getProperty("LOG").equals("CSV")) {
				this.view.getLogCsv().setSelected(true);
				this.view.getLogJson().setSelected(false);
			}else {
				this.view.getLogCsv().setSelected(false);
				this.view.getLogJson().setSelected(true);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
