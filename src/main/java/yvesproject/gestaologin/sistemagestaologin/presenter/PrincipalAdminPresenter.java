package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.bussiness.log.SingletonLogStrategy;
import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Observer;
import yvesproject.gestaologin.sistemagestaologin.bussiness.observer.Subject;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;
import yvesproject.gestaologin.sistemagestaologin.view.AlterarSenhaUserView;
import yvesproject.gestaologin.sistemagestaologin.view.ConfiguracoesAdminView;
import yvesproject.gestaologin.sistemagestaologin.view.EnviarNotificacaoAllUsersView;
import yvesproject.gestaologin.sistemagestaologin.view.EnviarNotificacaoUserView;
import yvesproject.gestaologin.sistemagestaologin.view.NotificacoesAdminView;
import yvesproject.gestaologin.sistemagestaologin.view.PrincipalAdminView;

public class PrincipalAdminPresenter extends Subject implements Observer {
	private PrincipalAdminView view;
	private Usuario userSelecionado;
	private ArrayList<Usuario> users;
	private Usuario adminLogado;
	private NotificacoesAdminPresenter notificacoesPresenter;
	private int numNotificacoes;
	private DefaultTableModel modelUserSelecionado;

	public PrincipalAdminPresenter(PrincipalAdminView view, Usuario adminLogado) {
		this.view = view;
		this.adminLogado = adminLogado;
		exibeStatusLogin();
		this.userSelecionado = null;
		this.users = null;
		regatarAcoesView();
	}

	/* configura todas as a????es de intera????o da view
	 * @ yves
	 * construtor (PrincipalAdminPresenter) */
	private void regatarAcoesView() {
		view.getBtnOpenNotificarTodosUsuarios().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e view que permite o envio de mensagens para todos os
				enviarNotAllUsers();
			}
		});

		this.view.getTable().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				modelUserSelecionado = (DefaultTableModel) view.getTableSelecionado().getModel();
				modelUserSelecionado.setNumRows(0);
				for (Usuario user : users) {
					if (user.getIdUsuario() == Integer
							.valueOf(String.valueOf(view.getTable().getValueAt(view.getTable().getSelectedRow(), 0)))) {
						userSelecionado = new Usuario();
						userSelecionado = user;
						modelUserSelecionado.addRow(new Object[] { userSelecionado.getIdUsuario(), userSelecionado.getNome(),
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
							excluirUsuarioEDadosRelacionados();
						} else {
							JOptionPane.showMessageDialog(null, "?? preciso selecionar um usu??rio primeiro!");
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
							if(!userSelecionado.getState().equals("cancelado")) {
								// inicia o presenter e view que permite o envio de mensagens para o usu??rio selecionado
								enviarNotUser();
							}else {
								JOptionPane.showMessageDialog(null, "Este usu??rio teve sua autoriza????o negada!");
							}
						} else {
							JOptionPane.showMessageDialog(null, "?? preciso selecionar um usu??rio primeiro!");
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
							try {
								users = ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getUsuariosPorNome(view.getTextFieldCampoBuscar().getText());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(!users.isEmpty()) {
								exibirTodosUsuarios();
							}else {
								JOptionPane.showMessageDialog(null, "N??o foi encontrado nenhum registro de usu??rio com esse nome.");
							}
						}else {
							JOptionPane.showMessageDialog(null, "?? preciso selecionar inserir um nome primeiro!");
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
							JOptionPane.showMessageDialog(null, "N??o h?? notifica????es n??o lidas.");
						}
					}
				});
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
		
		this.view.getBtnConfiguracoes().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						new ConfiguracoesAdminPresenter(new ConfiguracoesAdminView());
					}
				});
			}
		});
		
		view.getBtnOpenAlterarSenha().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inicia o presenter e a view respons??vel por alterar a senha do usu??rio
				AlterarSenhaUserView window = new AlterarSenhaUserView();
				new AlterarSenhaUserPresenter(window, adminLogado);
			}
		});
	}

	@Override
	public void update(String status) {
		if (status.equals("login administrador") || status.equals("atualizar notifica????es lidas")
				|| status.equals("administrador enviou notifica????o para usu??rio selecionado")
				|| status.equals("administrador enviou notifica????o para todos os usu??rios")
				|| status.equals("atualizar p??gina")) {
			try {
				this.users = this.buscarTodosUsuarios();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// limpa campo de busca
			this.view.getTextFieldCampoBuscar().setText("");
			// recupera todas as notifica????es do administrador
			this.getQtdNotificacoesAdmin();
			// limpa campo de usu??rio selecionado
			if(modelUserSelecionado != null) {
				modelUserSelecionado.setNumRows(0);
				userSelecionado = null;
			}
			// verifica se h?? usu??rios registrados e lista todos
			if (this.users.isEmpty()) {
				JOptionPane.showMessageDialog(null, "N??o h?? usu??rios registrados.");
			} else {
				this.exibirTodosUsuarios();
			}
		} else {
		}
	}

	/* busca todos os usu??rios do sistema (fora o administrador) e o retorna
	 * @ yves
	 * update (PrincipalAdminPresenter) */
	private ArrayList<Usuario> buscarTodosUsuarios() throws SQLException {
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		return ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getTodosUsuarios();
	}

	/* exibe todos os usu??rios resgatados na tabela principal da view
	 * @ yves
	 * update (PrincipalAdminPresenter) */
	private void exibirTodosUsuarios() {
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

	/* resgata do banco de dados a quantidade de notifica????es endere??adas ao administrador e configura sua exibi????o de acordo com o resultado
	 * @ yves
	 * update (PrincipalAdminPresenter) */
	private void getQtdNotificacoesAdmin() {
		try {
			numNotificacoes = ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
					.getQtdNotificacoesNaoLidasEnderecadasAdmin();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (numNotificacoes != 0) {
			view.getBtnOpenNotificacoes().setText(String.valueOf(numNotificacoes));
			view.getBtnOpenNotificacoes().setBackground(Color.red);
		} else {
			view.getBtnOpenNotificacoes().setText(String.valueOf(numNotificacoes));
			view.getBtnOpenNotificacoes().setBackground(Color.green);
		}
	}

	/* instancia a presenter de notifica????es enderecadas a usu??rio e sua view
	 * @ yves
	 * resgataAcoesView (PrincipalAdminPresenter) */
	private void openNotificacoes() {
		// exibir tela que apresenta todas as notifica????es do administrador
		NotificacoesAdminView window = new NotificacoesAdminView();
		notificacoesPresenter = new NotificacoesAdminPresenter(window, this, adminLogado);
		add(notificacoesPresenter);
		try {
			notifyObservers("abrir notifica????es");
		} catch (HeadlessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* instancia a presenter de envio de notifica????es e sua view para o usu??rio selecionado
	 * @ yves
	 * resgataAcoesView (PrincipalAdminPresenter) */
	private void enviarNotUser() {
		EnviarNotificacaoUserView window = new EnviarNotificacaoUserView();
		new EnviarNotificacaoUserPresenter(window, userSelecionado, this);
	}

	/* instancia a presenter de envio de notifica????es e sua view para todos os usu??rios (exceto o que possui status como cancelado)
	 * @ yves
	 * resgataAcoesView (PrincipalAdminPresenter) */
	private void enviarNotAllUsers() {
		EnviarNotificacaoAllUsersView window = new EnviarNotificacaoAllUsersView();
		new EnviarNotificacaoAllUsersPresenter(window, users, this);
	}
	
	/* exibe o status de login na barra inferior da view
	 * @ yves
	 * construtor (PrincipalAdminPresenter) */
	private void exibeStatusLogin() {
		view.getLblUserName().setText(adminLogado.getNome());
		view.getLblUserTypeName().setText(adminLogado.getTipo());
	}
	
	/* respons??vel atualizar manualmente os dados da view (por meio da classe observer)
	 * @ yves
	 * resgatarAcoesView (PrincipalAdminPresenter) */
	private void atualizarPagina() {
		add(this);
		try {
			notifyObservers("atualizar p??gina");
		} catch (HeadlessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* realiza o processo de exclus??o de funcion??rio e suas notifica????es
	 * @ yves
	 * resgatarAcoesView (PrincipalAdminPresenter) */
	private void excluirUsuarioEDadosRelacionados() {
		// confirma a a????o do administrador
		int op = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este usu??rio?",
				  "Exit", JOptionPane.OK_CANCEL_OPTION);
		if (op == JOptionPane.OK_OPTION) {
			try {
				if (ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO()
						.remover(userSelecionado.getIdUsuario())) {
					if(ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().removerTodasNotificacoesUser(userSelecionado.getIdUsuario())) {
						JOptionPane.showMessageDialog(null, "Usu??rio exclu??do com sucesso.", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
						atualizarPagina();
						SingletonLogStrategy.getInstance().getLog().registrarLog("Exclus??o de usu??rio",
								adminLogado.getNome(),
								adminLogado.getTipo());
					}else {
						JOptionPane.showMessageDialog(null, "Erro ao excluir as notifica????es do usu??rio.", "Erro",
								JOptionPane.ERROR);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Erro ao excluir o usu??rio.", "Erro",
							JOptionPane.ERROR);
				}
			} catch (HeadlessException | SQLException | IOException e) {
				try {
					SingletonLogStrategy.getInstance().getLog().registrarErroLog(e.getMessage(), "Exclus??o de usu??rio",
							adminLogado.getNome(),
							adminLogado.getTipo());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}

	// getters
	public Usuario getAdminLogado() {
		return adminLogado;
	}
}
