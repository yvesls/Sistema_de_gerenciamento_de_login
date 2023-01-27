package yvesproject.gestaologin.sistemagestaologin.presenter;

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
import yvesproject.gestaologin.sistemagestaologin.model.Notificacao;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;
import yvesproject.gestaologin.sistemagestaologin.view.NotificacoesView;

public class NotificacoesPresenter extends Subject implements Observer {
	private NotificacoesView view;
	private Notificacao notSelecionada;
	private ArrayList<Notificacao> nots;
	private int qtdNotLidas;
	private int qtdNotEnviadas;
	private Notificacao notConfirmacao;
	private Usuario usuario;
	private Observer principalPresenter;

	public NotificacoesPresenter(NotificacoesView view, Observer principalPresenter) {
		this.view = view;
		this.principalPresenter = principalPresenter;
		this.notSelecionada = null;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnMarcarComoLida().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// verifica se há notificação selecionada
				if (notSelecionada != null) {
System.out.println(notSelecionada.getData());
					if (notSelecionada.getData().equals("não lida")) {
						ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
						notSelecionada.setStatus("lida");
						// atualiza o status da notificação
						if (ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
								.atualizarStatus(notSelecionada)) {
							qtdNotLidas = ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
									.getQtdNotificacoesLidasRemetente(notSelecionada.getIdRemetente());
							usuario = new Usuario();
							usuario.setNotLidas(qtdNotLidas++);
							usuario.setIdUsuario(notSelecionada.getIdRemetente());
							// atualiza a quantidade de notificação lida do usuário
							if (ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO()
									.atualizarQtdNotificacoesLidas(usuario)) {
								notConfirmacao = new Notificacao(1, usuario.getIdUsuario(), "Bem vindo!", "não lida");
								// envia uma notificação de confirmação e boas vindas para o usuário.
								if (ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
										.salvar(notConfirmacao)) {
									usuario = new Usuario();
									qtdNotEnviadas = ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
											.getQtdNotificacoesEnviadasRemetente(1);
									usuario.setNotEnviadas(qtdNotEnviadas++);
									usuario.setIdUsuario(1);
									// atualiza a quantidade de notiicação enviada do administrador
									if (ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO()
											.atualizarQtdNotificacoesEnviadas(usuario)) {
										JOptionPane.showMessageDialog(null, "Notificação atualizada com sucesso.",
												"Sucesso", JOptionPane.INFORMATION_MESSAGE);
										add(principalPresenter);
										notifyObservers("atualizar notificações lidas");
									} else {
										JOptionPane.showMessageDialog(null,
												"Ocorreu um erro inesperado ao atualizar a sua quantidade de notificação enviada. Tente novamente mais tarde.",
												"Atenção", JOptionPane.INFORMATION_MESSAGE);
									}
								} else {
									JOptionPane.showMessageDialog(null,
											"Ocorreu um erro inesperado ao enviar notificação de confirmação para o usuário. Tente novamente mais tarde.",
											"Atenção", JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null,
										"Ocorreu um erro inesperado ao atualizar a quantidade de notificação lida do usuário. Tente novamente mais tarde.",
										"Atenção", JOptionPane.INFORMATION_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Ocorreu um erro inesperado ao atualizar o status da notificação. Tente novamente mais tarde.",
									"Atenção", JOptionPane.INFORMATION_MESSAGE);
						}

					} else {
						JOptionPane.showMessageDialog(null, "Esta notificação já foi confirmada como lida.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma notificação primeiro.");
				}
			}
		});

		view.getBtnFechar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Modifica o status da notificação
				view.getFrame().setVisible(false);
			}
		});

		this.view.getTable().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				DefaultTableModel modelo = (DefaultTableModel) view.getTableSelecionado().getModel();
				modelo.setNumRows(0);
				for (Notificacao not : nots) {
					if (not.getIdNotificacao() == Integer
							.valueOf(String.valueOf(view.getTable().getValueAt(view.getTable().getSelectedRow(), 0)))) {
						notSelecionada = new Notificacao();
						notSelecionada = not;
						modelo.addRow(new Object[] { notSelecionada.getIdNotificacao(), notSelecionada.getDescricao(),
								notSelecionada.getData(), notSelecionada.getStatus() });
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
	}

	public ArrayList<Notificacao> getTodasNotEnderecadasAoAdmin() {
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().getTodasNotificacoesEnderecadasAdmin();
	}

	@Override
	public void update(String status) {
		if (status.equals("abrir notificações")) {
			this.nots = getTodasNotEnderecadasAoAdmin();
			if (this.nots.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Não há notificações registradas.");
			} else {
				exibirTodasNotificacoesEnderecadasAoAdmin();
			}
		} else {
		}
	}

	public void exibirTodasNotificacoesEnderecadasAoAdmin() {
		DefaultTableModel modelo = (DefaultTableModel) this.view.getTable().getModel();
		modelo.setNumRows(0);
		for (Notificacao not : this.nots) {
			modelo.addRow(new Object[] { not.getIdNotificacao(), not.getDescricao(), not.getData(), not.getStatus() });
		}
	}
}
