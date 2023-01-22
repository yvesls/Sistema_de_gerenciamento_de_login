package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import yvesproject.gestaologin.sistemagestaologin.model.User;
import yvesproject.gestaologin.sistemagestaologin.view.EnviarNotificacaoAllUsersView;
import yvesproject.gestaologin.sistemagestaologin.view.EnviarNotificacaoUserView;
import yvesproject.gestaologin.sistemagestaologin.view.NotificacoesView;
import yvesproject.gestaologin.sistemagestaologin.view.PrincipalAdminView;

public class PrincipalAdminPresenter {
	private PrincipalAdminView view;
	private User userSelecionado;
	private ArrayList<User> users;

	public PrincipalAdminPresenter(PrincipalAdminView view) {
		this.view = view;
		this.userSelecionado = null;
		this.users = null;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnOpenNotificarTodosUsuarios().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e view que permite o envio de mensagens para todos os usuários
				EnviarNotificacaoAllUsersView window = new EnviarNotificacaoAllUsersView();
				new EnviarNotificacaoAllUsersPresenter(window);
			}
		});
		
		this.view.getTable().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				DefaultTableModel modelo = (DefaultTableModel) view.getTableSelecionado().getModel();
				modelo.setNumRows(0);
				for (User user : users) {
					if (user.getId() == Integer
							.valueOf(String.valueOf(view.getTable().getValueAt(view.getTable().getSelectedRow(), 0)))) {
						userSelecionado = new User();
						userSelecionado = user;
						modelo.addRow(new Object[] { userSelecionado.getId(), userSelecionado.getNome(),
								userSelecionado.getCpf(), userSelecionado.getDataCadastro(), userSelecionado.getState(),
								userSelecionado.getNotEnviadas(), userSelecionado.getNotLidas()});
					}
				}
				DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();        
				centralizado.setHorizontalAlignment(SwingConstants.CENTER);
				int i = 0;
				while(i < view.getTableSelecionado().getColumnCount()) {
					view.getTableSelecionado().getColumnModel().getColumn(i).setCellRenderer(centralizado);
					i++;
				}
			}
		});
		
		this.view.getBtnExcluirUsuario().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						if (userSelecionado != null) {
							// confirma a ação do administrador
						} else {
							JOptionPane.showMessageDialog(null, "É preciso selecionar um funcionário primeiro!");
						}
					}
				});
			}
		});
		
		this.view.getBtnOpenNotificarUsuarioSelecionado().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						if (userSelecionado != null) {
							// inicia o presenter e view que permite o envio de mensagens para o usuário selecionado
							EnviarNotificacaoUserView window = new EnviarNotificacaoUserView();
							new EnviarNotificacaoUserPresenter(window);
						} else {
							JOptionPane.showMessageDialog(null, "É preciso selecionar um funcionário primeiro!");
						}
					}
				});
			}
		});
		
		this.view.getBtnBuscarUsuarios().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						// realiza o processo de busca e exibe na tabela
					}
				});
			}
		});
		
		this.view.getBtnOpenNotificacoes().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						// exibir tela que apresenta todas as notificações do administrador
						NotificacoesView window = new NotificacoesView();
						new NotificacoesPresenter(window);
					}
				});
			}
		});

	}
}
