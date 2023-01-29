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

	private void regatarAcoesView() {
		view.getBtnMarcarComoLida().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// verifica se há notificação selecionada
				if (notSelecionada != null) {
					if (notSelecionada.getStatus().equals("não lida")) {
						// modifica o state do usuario
						autentificarUsuario();
						// chama a service de notificações
						getNotificacoesService();
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
				add(principalPresenter);
				notifyObservers("atualizar notificações lidas");
				view.getFrame().setVisible(false);
			}
		});

		this.view.getTable().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				modelNotSelecionado = (DefaultTableModel) view.getTableSelecionado().getModel();
				modelNotSelecionado.setNumRows(0);
				for (Notificacao not : nots) {
					if (not.getIdNotificacao() == Integer
							.valueOf(String.valueOf(view.getTable().getValueAt(view.getTable().getSelectedRow(), 0)))) {
						notSelecionada = new Notificacao();
						notSelecionada = not;
						modelNotSelecionado.addRow(new Object[] { notSelecionada.getIdNotificacao(), notSelecionada.getDescricao(),
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

	public ArrayList<Notificacao> getTodasNotNaoLidasEnderecadasAoAdmin() {
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO()
				.getTodasNotificacoesNaoLidasEnderecadasAdmin();
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

	public void exibirTodasNotificacoesEnderecadasAoAdmin() {
		DefaultTableModel modelo = (DefaultTableModel) this.view.getTable().getModel();
		modelo.setNumRows(0);
		for (Notificacao not : this.nots) {
			modelo.addRow(new Object[] { not.getIdNotificacao(), not.getDescricao(), not.getData(), not.getStatus() });
		}
	}

	public void getNotificacoesService() {
		notificacaoService = new NotificacaoService(usuario, notSelecionada, this);
		add(notificacaoService);
		notifyObservers("administrador valida login do usuário");
	}

	public void autentificarUsuario() {
		modificiarUsuario = new Usuario(notSelecionada.getIdRemetente(), "usuario");
		modificiarUsuario.ativarUsuarioState(usuario);
	}
}