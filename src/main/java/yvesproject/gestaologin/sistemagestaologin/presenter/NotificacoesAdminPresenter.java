package yvesproject.gestaologin.sistemagestaologin.presenter;

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
import yvesproject.gestaologin.sistemagestaologin.model.Notificacao;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;
import yvesproject.gestaologin.sistemagestaologin.service.NotificacaoService;
import yvesproject.gestaologin.sistemagestaologin.view.NotificacoesAdminView;

public class NotificacoesAdminPresenter extends Subject implements Observer {
	private NotificacoesAdminView view;
	private Notificacao notSelecionada;
	private ArrayList<Notificacao> nots;
	private Observer principalPresenter;
	private NotificacaoService notificacaoService;
	private Usuario usuario;
	private Usuario modificiarUsuario;
	private DefaultTableModel modelNotSelecionado;

	public NotificacoesAdminPresenter(NotificacoesAdminView view, Observer principalPresenter, Usuario usuario) {
		this.view = view;
		this.principalPresenter = principalPresenter;
		this.notSelecionada = null;
		this.usuario = usuario;
		regatarAcoesView();
	}

	/*
	 * configura todas as ações de interação da view
	 * 
	 * @ yves construtor (NotificacoesAdminPresenter)
	 */
	private void regatarAcoesView() {
		view.getBtnMarcarComoLida().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// verifica se há notificação selecionada
				if (notSelecionada != null) {
					// modifica o state do usuario
					autentificarUsuario();
					// chama a service de notificações
					getNotificacoesService(true);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma notificação primeiro.");
				}
			}
		});

		view.getBtnInvalidarSolicitacao().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// verifica se há notificação selecionada
				if (notSelecionada != null) {
					// modifica o state do usuario
					InvalidarAutentificacaoUsuario();
					// chama a service de notificações
					getNotificacoesService(false);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma notificação primeiro.");
				}
			}
		});

		view.getBtnFechar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Modifica o status da notificação
				add(principalPresenter);
				try {
					notifyObservers("atualizar notificações lidas");
				} catch (HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				view.getFrame().setVisible(false);
			}
		});

		this.view.getTable().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				// exibe na tabela inferior de notificação selecionada os dados da notificação selecionada
				modelNotSelecionado = (DefaultTableModel) view.getTableSelecionado().getModel();
				modelNotSelecionado.setNumRows(0);
				for (Notificacao not : nots) {
					if (not.getIdNotificacao() == Integer
							.valueOf(String.valueOf(view.getTable().getValueAt(view.getTable().getSelectedRow(), 0)))) {
						notSelecionada = new Notificacao();
						notSelecionada = not;
						modelNotSelecionado.addRow(new Object[] { notSelecionada.getIdNotificacao(),
								notSelecionada.getDescricao(), notSelecionada.getData(), notSelecionada.getStatus() });
					}
				}
				// centraliza os dados da tabela de notificação selecionada
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

	/* retorna todas as notificações não lidas endereçadas ao administrador do banco de dados
	 * @ yves
	 * update (NotificacoesAdminPresenter) */
	private ArrayList<Notificacao> getTodasNotNaoLidasEnderecadasAoAdmin() {
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		try {
			return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
					.getTodasNotificacoesNaoLidasEnderecadasAdmin();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(String status) {
		if (status.equals("abrir notificações")) {
			this.nots = getTodasNotNaoLidasEnderecadasAoAdmin();
			exibirTodasNotificacoesEnderecadasAoAdmin();
			// limpa campo de usuário selecionado
			if (modelNotSelecionado != null) {
				modelNotSelecionado.setNumRows(0);
				notSelecionada = null;
			}
		} else if (status.equals("atualizar lista de notificações")) {
			this.nots = getTodasNotNaoLidasEnderecadasAoAdmin();
			exibirTodasNotificacoesEnderecadasAoAdmin();
			// limpa campo de usuário selecionado
			if (modelNotSelecionado != null) {
				modelNotSelecionado.setNumRows(0);
				notSelecionada = null;
			}
		}
	}

	/* exibe todas as notificações endereçadas ao admin na tabela principal da view
	 * @ yves
	 * update (NotificacoesAdminPresenter) */
	private void exibirTodasNotificacoesEnderecadasAoAdmin() {
		DefaultTableModel modelo = (DefaultTableModel) this.view.getTable().getModel();
		modelo.setNumRows(0);
		for (Notificacao not : this.nots) {
			modelo.addRow(new Object[] { not.getIdNotificacao(), not.getDescricao(), not.getData(), not.getStatus() });
		}
		// centraliza os dados da tabela
		DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
		centralizado.setHorizontalAlignment(SwingConstants.CENTER);
		int i = 0;
		while (i < view.getTable().getColumnCount()) {
			view.getTable().getColumnModel().getColumn(i).setCellRenderer(centralizado);
			i++;
		}
	}
	
	/* instancia e passa um comando específico para o serviço de notificações quando o administrador marca a notificação como lida
	 * @ yves
	 * resgataAcoesView (NotificacoesAdminPresenter) */
	private void getNotificacoesService(boolean isValida) {
		// caso o administrador tenha clicado em validar o novo usuário
		if (isValida) {
			notificacaoService = new NotificacaoService(usuario, notSelecionada, this);
			add(notificacaoService);
			try {
				notifyObservers("administrador valida login do usuário");
				SingletonLogStrategy.getInstance().getLog().registrarLog("Envio de notificações", usuario.getNome(),
						usuario.getTipo());
				SingletonLogStrategy.getInstance().getLog().registrarLog("Leitura de notificações", usuario.getNome(),
						usuario.getTipo());
				SingletonLogStrategy.getInstance().getLog().registrarLog("Validação de usuário", usuario.getNome(),
						usuario.getTipo());
			} catch (HeadlessException | SQLException | IOException e) {
				try {
					SingletonLogStrategy.getInstance().getLog().registrarErroLog(e.getMessage(),
							"Envio de notificações", usuario.getNome(), usuario.getTipo());
					SingletonLogStrategy.getInstance().getLog().registrarErroLog(e.getMessage(),
							"Leitura de notificações", usuario.getNome(), usuario.getTipo());
					SingletonLogStrategy.getInstance().getLog().registrarErroLog(e.getMessage(), "Validação de usuário",
							usuario.getNome(), usuario.getTipo());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		} else {
			notificacaoService = new NotificacaoService(usuario, notSelecionada, this);
			add(notificacaoService);
			try {
				notifyObservers("administrador invalida login do usuário");
				SingletonLogStrategy.getInstance().getLog().registrarLog("Leitura de notificações", usuario.getNome(),
						usuario.getTipo());
				SingletonLogStrategy.getInstance().getLog().registrarLog("Validação de usuário", usuario.getNome(),
						usuario.getTipo());
			} catch (HeadlessException | SQLException | IOException e) {
				try {
					SingletonLogStrategy.getInstance().getLog().registrarErroLog(e.getMessage(),
							"Leitura de notificações", usuario.getNome(), usuario.getTipo());
					SingletonLogStrategy.getInstance().getLog().registrarErroLog(e.getMessage(), "Validação de usuário",
							usuario.getNome(), usuario.getTipo());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

		}

	}

	/* realiza a modificação do status do usuário para 'ativo'
	 * @ yves
	 * resgataAcoesView (NotificacoesAdminPresenter) */
	private void autentificarUsuario() {
		modificiarUsuario = new Usuario(notSelecionada.getIdRemetente(), "usuario");
		modificiarUsuario.ativarUsuarioState(usuario);
	}

	/* realiza a modificação do status do usuário para 'cancelado'
	 * @ yves
	 * resgataAcoesView (NotificacoesAdminPresenter) */
	private void InvalidarAutentificacaoUsuario() {
		modificiarUsuario = new Usuario(notSelecionada.getIdRemetente(), "usuario");
		modificiarUsuario.cancelarUsuarioState(usuario);
	}
}