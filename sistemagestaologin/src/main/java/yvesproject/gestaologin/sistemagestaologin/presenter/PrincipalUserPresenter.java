package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Observer;
import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Subject;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;
import yvesproject.gestaologin.sistemagestaologin.view.AlterarSenhaUserView;
import yvesproject.gestaologin.sistemagestaologin.view.NotificacoesUsuarioView;
import yvesproject.gestaologin.sistemagestaologin.view.PrincipalUserView;

public class PrincipalUserPresenter extends Subject implements Observer {
	private PrincipalUserView view;
	private Usuario usuarioLogado;
	private int numNotificacoes;
	private NotificacoesUsuarioPresenter notificacoesPresenter;

	public PrincipalUserPresenter(PrincipalUserView view, Usuario usuarioLogado) {
		this.view = view;
		this.usuarioLogado = usuarioLogado;
		exibeStatusLogin();
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnOpenAlterarSenha().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e a view responsável por alterar a senha do usuário
				AlterarSenhaUserView window = new AlterarSenhaUserView();
				new AlterarSenhaUserPresenter(window, usuarioLogado);
			}
		});

		view.getBtnOpenNotificacoes().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e a view de notificações do usuario
				if(numNotificacoes != 0) {
					openNotificacoesUsuario();
				}else {
					JOptionPane.showMessageDialog(null, "Não há notificações não lidas.");
				}
			}
		});
		
		this.view.getBtnAtualizarManualmente().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						atualizarPagina();
					}
				});
			}
		});
	}

	@Override
	public void update(String status) {
		if(status.equals("login usuario") || status.equals("atualizar quantidade de notificações do usuário") 
				|| status.equals("atualizar página")) {
			getQtdNotificacoesUsuario();
		}else {
		}
	}

	public void getQtdNotificacoesUsuario() {
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		numNotificacoes = ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
				.getQtdNotificacoesNaoLidasEnderecadasUsuario(usuarioLogado.getIdUsuario());
		if (numNotificacoes != 0) {
			view.getBtnOpenNotificacoes().setText(String.valueOf(numNotificacoes));
			view.getBtnOpenNotificacoes().setBackground(Color.red);
		} else {
			view.getBtnOpenNotificacoes().setText(String.valueOf(numNotificacoes));
			view.getBtnOpenNotificacoes().setBackground(Color.green);
		}
	}

	public void openNotificacoesUsuario() {
		NotificacoesUsuarioView window = new NotificacoesUsuarioView();
		notificacoesPresenter = new NotificacoesUsuarioPresenter(window, this, usuarioLogado);
		add(notificacoesPresenter);
		notifyObservers("abrir notificações usuário");
	}
	
	public void exibeStatusLogin() {
		view.getLblUserName().setText(usuarioLogado.getNome());
		view.getLblUserTypeName().setText(usuarioLogado.getTipo());
	}
	
	public void atualizarPagina() {
		add(this);
		notifyObservers("atualizar página");
	}
}
