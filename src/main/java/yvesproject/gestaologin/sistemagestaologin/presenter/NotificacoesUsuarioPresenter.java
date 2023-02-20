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
import yvesproject.gestaologin.sistemagestaologin.view.NotificacoesUsuarioView;

public class NotificacoesUsuarioPresenter extends Subject implements Observer {
	private NotificacoesUsuarioView view;
	private Notificacao notSelecionada;
	private ArrayList<Notificacao> nots;
	private Observer principalPresenter;
	private NotificacaoService notificacaoService;
	private Usuario usuario;
	private DefaultTableModel notModelSelecionado;
	
	public NotificacoesUsuarioPresenter(NotificacoesUsuarioView view, Observer principalPresenter, Usuario usuario) {
		this.view = view;
		this.principalPresenter = principalPresenter;
		this.notSelecionada = null;
		this.usuario = usuario;
		regatarAcoesView();
	}

	/* configura todas as ações de interação da view
	 * @ yves
	 * construtor (NotificacoesUsuarioPresenter) */
	private void regatarAcoesView() {
		view.getBtnMarcarComoLida().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// verifica se há notificação selecionada
				if (notSelecionada != null) {
					if (notSelecionada.getStatus().equals("não lida")) {
						// modifica o state do usuario
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
				try {
					notifyObservers("atualizar quantidade de notificações do usuário");
				} catch (HeadlessException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				view.getFrame().setVisible(false);
			}
		});

		this.view.getTable().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				// responsável por exibir na tabela inferior de seleção de notificações a notificação selecionada
				notModelSelecionado = (DefaultTableModel) view.getTableSelecionado().getModel();
				notModelSelecionado.setNumRows(0);
				for (Notificacao not : nots) {
					if (not.getIdNotificacao() == Integer
							.valueOf(String.valueOf(view.getTable().getValueAt(view.getTable().getSelectedRow(), 0)))) {
						notSelecionada = new Notificacao();
						notSelecionada = not;
						notModelSelecionado.addRow(new Object[] { notSelecionada.getIdNotificacao(), notSelecionada.getDescricao(),
								notSelecionada.getData(), notSelecionada.getStatus() });
					}
				}
				// centraliza os dados da tabela
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

	/* retorna todas as notificações não lidas endereçadas ao usuário do banco de dados
	 * @ yves
	 * update (NotificacoesUsuarioPresenter) */
	private ArrayList<Notificacao> getTodasNotNaoLidasEnderecadasAoUsuario() {
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		try {
			return ConexaoSingletonDAO.getInstance().getNotificacaoSqliteDAO().getTodasNotNaoLidasEnderecadasAoUsuario(usuario.getIdUsuario());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(String status) {
		if (status.equals("abrir notificações usuário")) {
			this.nots = getTodasNotNaoLidasEnderecadasAoUsuario();
			exibirTodasNotificacoesEnderecadasAoUsuario();
			if (notModelSelecionado != null) {
				notModelSelecionado.setNumRows(0);
				notSelecionada = null;
			}
		} else if(status.equals("atualizar lista de notificações")) {
			this.nots = getTodasNotNaoLidasEnderecadasAoUsuario();
			exibirTodasNotificacoesEnderecadasAoUsuario();
			if (notModelSelecionado != null) {
				notModelSelecionado.setNumRows(0);
				notSelecionada = null;
			}
		}
	}

	/* exibe todas as notificações endereçadas ao usuário na tabela principal da view
	 * @ yves
	 * update (NotificacoesUsuarioPresenter) */
	private void exibirTodasNotificacoesEnderecadasAoUsuario() {
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

	/* instancia e passa um comando específico para o serviço de notificações quando o usuário marca a notificação como lida
	 * @ yves
	 * resgataAcoesView (NotificacoesUsuarioPresenter) */
	private void getNotificacoesService() {
		notificacaoService = new NotificacaoService(usuario, notSelecionada, this);
		add(notificacaoService);
		try {
			notifyObservers("usuário leu a notificação");
			// registra em log a operação
			SingletonLogStrategy.getInstance().getLog().registrarLog("Leitura de notificações",
					usuario.getNome(),
					usuario.getTipo());
		} catch (HeadlessException | SQLException | IOException e) {
			try {
				// registra em log caso ocorra um erro na operação
				SingletonLogStrategy.getInstance().getLog().registrarErroLog(e.getMessage(), "Leitura de notificações",
						usuario.getNome(),
						usuario.getTipo());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
