package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.Color;
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

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Observer;
import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Subject;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;
import yvesproject.gestaologin.sistemagestaologin.view.EnviarNotificacaoAllUsersView;
import yvesproject.gestaologin.sistemagestaologin.view.EnviarNotificacaoUserView;
import yvesproject.gestaologin.sistemagestaologin.view.NotificacoesView;
import yvesproject.gestaologin.sistemagestaologin.view.PrincipalAdminView;

public class PrincipalAdminPresenter extends Subject implements Observer {
	private PrincipalAdminView view;
	private Usuario userSelecionado;
	private ArrayList<Usuario> users;
	private Usuario adminLogado;
	private NotificacoesPresenter notificacoesPresenter;
	private EnviarNotificacaoUserPresenter enviarNotUserPresenter;
	private EnviarNotificacaoAllUsersPresenter enviarNotAllUsersPresenter;
	private int numNotificacoes;

	public PrincipalAdminPresenter(PrincipalAdminView view, Usuario adminLogado) {
		this.view = view;
		this.adminLogado = adminLogado;
		exibeStatusLogin();
		this.userSelecionado = null;
		this.users = null;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnOpenNotificarTodosUsuarios().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e view que permite o envio de mensagens para todos os
				enviarNotAllUsers();
			}
		});

		this.view.getTable().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				DefaultTableModel modelo = (DefaultTableModel) view.getTableSelecionado().getModel();
				modelo.setNumRows(0);
				for (Usuario user : users) {
					if (user.getIdUsuario() == Integer
							.valueOf(String.valueOf(view.getTable().getValueAt(view.getTable().getSelectedRow(), 0)))) {
						userSelecionado = new Usuario();
						userSelecionado = user;
						modelo.addRow(new Object[] { userSelecionado.getIdUsuario(), userSelecionado.getNome(),
								userSelecionado.getCpf(), userSelecionado.getDataCadastro(), userSelecionado.getState(),
								userSelecionado.getNotEnviadas(), userSelecionado.getNotLidas() });
					}
				}
				DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
				centralizado.setHorizontalAlignment(SwingConstants.CENTER);
				int i = 0;
				while (i < view.getTableSelecionado().getColumnCount()) {
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
							JOptionPane.showMessageDialog(null, "Tem certeza que deseja excluir este usuário?", "Atenção",
									JOptionPane.INFORMATION_MESSAGE);
							ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
							if(ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().remover(userSelecionado.getIdUsuario())) {
								JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso.", "Sucesso",
										JOptionPane.INFORMATION_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "É preciso selecionar um usuário primeiro!");
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
							enviarNotUser();
						} else {
							JOptionPane.showMessageDialog(null, "É preciso selecionar um usuário primeiro!");
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
						if(!view.getTextFieldCampoBuscar().getText().isEmpty()) {
							ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
							users = ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getUsuariosPorNome(view.getTextFieldCampoBuscar().getText());
							exibirTodosUsuarios();
						}else {
							JOptionPane.showMessageDialog(null, "É preciso selecionar inserir um nome primeiro!");
						}
					}
				});
			}
		});

		this.view.getBtnOpenNotificacoes().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						if(numNotificacoes != 0) {
							openNotificacoes();
						}else {
							JOptionPane.showMessageDialog(null, "Não há notificações não lidas.");
						}
					}
				});
			}
		});
	}

	@Override
	public void update(String status) {
		if (status.equals("login administrador") || status.equals("atualizar notificações lidas")
				|| status.equals("administrador enviou notificação para usuário selecionado")
				|| status.equals("administrador enviou notificação para todos os usuários")) {
			this.users = this.buscarTodosUsuarios();
			// verifica se há usuários registrados e lista todos
			if (this.users.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Não há usuários registrados.");
			} else {
				this.exibirTodosUsuarios();
			}
			// recupera todas as notificações do administrador
			this.getQtdNotificacoesAdmin();
		} else {

		}
	}

	public ArrayList<Usuario> buscarTodosUsuarios() {
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		return ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getTodosUsuarios();
	}

	public void exibirTodosUsuarios() {
		DefaultTableModel modelo = (DefaultTableModel) this.view.getTable().getModel();
		modelo.setNumRows(0);
		for (Usuario user : this.users) {
			modelo.addRow(new Object[] { user.getIdUsuario(), user.getNome(), user.getCpf(), user.getDataCadastro(), user.getState(),
					user.getNotEnviadas(), user.getNotLidas() });
		}
		DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
		centralizado.setHorizontalAlignment(SwingConstants.CENTER);
		int i = 0;
		while (i < view.getTable().getColumnCount()) {
			view.getTable().getColumnModel().getColumn(i).setCellRenderer(centralizado);
			i++;
		}
	}

	public void getQtdNotificacoesAdmin() {
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		numNotificacoes = ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
				.getQtdNotificacoesNaoLidasEnderecadasAdmin();
		if (numNotificacoes != 0) {
			view.getBtnOpenNotificacoes().setText(String.valueOf(numNotificacoes));
			view.getBtnOpenNotificacoes().setBackground(Color.red);
		} else {
			view.getBtnOpenNotificacoes().setText(String.valueOf(numNotificacoes));
			view.getBtnOpenNotificacoes().setBackground(Color.green);
		}
	}

	public void openNotificacoes() {
		notifyObservers("");
		// exibir tela que apresenta todas as notificações do administrador
		NotificacoesView window = new NotificacoesView();
		notificacoesPresenter = new NotificacoesPresenter(window, this);
		add(notificacoesPresenter);
		notifyObservers("abrir notificações");
	}

	public void enviarNotUser() {
		EnviarNotificacaoUserView window = new EnviarNotificacaoUserView();
		enviarNotUserPresenter = new EnviarNotificacaoUserPresenter(window, userSelecionado, this);
	}

	public void enviarNotAllUsers() {
		EnviarNotificacaoAllUsersView window = new EnviarNotificacaoAllUsersView();
		enviarNotAllUsersPresenter = new EnviarNotificacaoAllUsersPresenter(window, users, this);
	}
	
	public void exibeStatusLogin() {
		view.getLblUserName().setText(adminLogado.getNome());
		view.getLblUserTypeName().setText(adminLogado.getTipo());
	}
}
