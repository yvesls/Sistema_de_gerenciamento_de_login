package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import yvesproject.gestaologin.sistemagestaologin.model.Notificacao;
import yvesproject.gestaologin.sistemagestaologin.view.NotificacoesView;

public class NotificacoesPresenter {
	private NotificacoesView view;
	private Notificacao notSelecionada;
	private ArrayList<Notificacao> nots;

	public NotificacoesPresenter(NotificacoesView view) {
		this.view = view;
		this.notSelecionada = null;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnMarcarComoLida().addActionListener(new ActionListener() {
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
								notSelecionada.getData(), notSelecionada.getStatus()});
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
	}
}
