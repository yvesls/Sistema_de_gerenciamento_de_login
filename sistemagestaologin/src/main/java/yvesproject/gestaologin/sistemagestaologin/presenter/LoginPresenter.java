package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Subject;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;
import yvesproject.gestaologin.sistemagestaologin.view.LogarView;
import yvesproject.gestaologin.sistemagestaologin.view.PrincipalAdminView;
import yvesproject.gestaologin.sistemagestaologin.view.PrincipalUserView;
import yvesproject.gestaologin.sistemagestaologin.view.RegistrarView;

public class LoginPresenter extends Subject {
	private LogarView view;
	private Usuario usuarioLogin;
	private PrincipalAdminPresenter principalAdminPresenter;
	private PrincipalUserPresenter principalUserPresenter;

	public LoginPresenter(LogarView view) {
		this.view = view;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnLogar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// valida se os campos foram preenchidos
				if(!view.getTxtEmail().getText().isEmpty() || !view.getTxtSenha().getText().isEmpty()) {
					// valida login
					ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
					usuarioLogin = new Usuario(view.getTxtEmail().getText(), view.getTxtSenha().getText(), "", "", "", "", 0, 0);
					try {
						usuarioLogin = ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getUserIsRegister(usuarioLogin);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// se o usuário existir, realiza o login e fecha a janela
					if(usuarioLogin != null) {
						if (usuarioLogin.getState().equals("cancelado")) {
							JOptionPane.showMessageDialog(null, "A solicitação de autentificação do usuário foi negada ou o administrador baniu o usuário.", "Atenção",
									JOptionPane.INFORMATION_MESSAGE);
						}else {
							validarStatusLogin();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Usuário não encontrado. Corrija algum campo ou se registre, caso seja sua primeira vez aqui.", "Atenção",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		view.getBtnOpenRegistrar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e abre a janela de registro de novos usuários
				new RegistrarPresenter(new RegistrarView());
			}
		});
	}
	
	// verifica se o status está ativo
	public void validarStatusLogin() {
		if(usuarioLogin.getState().equals("ativo")) {
			validarTipoLogin();
		}else {
			JOptionPane.showMessageDialog(null, "Sua validação ainda não foi confirmada. Por favor, aguarde até que o adminstrador valide seu login.", "Atenção",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	// verifica o tipo de usuário e encaminha para a presenter correspondente
	public void validarTipoLogin() {
		if(usuarioLogin.getTipo().equals("administrador")) {
			view.getFrame().setVisible(false);
			PrincipalAdminView window = new PrincipalAdminView();
			this.principalAdminPresenter = new PrincipalAdminPresenter(window, usuarioLogin);
			this.add(principalAdminPresenter);
			try {
				this.notifyObservers("login administrador");
			} catch (HeadlessException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			view.getFrame().setVisible(false);
			PrincipalUserView window = new PrincipalUserView();
			this.principalUserPresenter = new PrincipalUserPresenter(window, usuarioLogin);
			this.add(principalUserPresenter);
			try {
				this.notifyObservers("login usuario");
			} catch (HeadlessException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
