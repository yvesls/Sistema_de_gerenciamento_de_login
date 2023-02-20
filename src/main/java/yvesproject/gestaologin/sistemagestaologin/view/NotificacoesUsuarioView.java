package yvesproject.gestaologin.sistemagestaologin.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class NotificacoesUsuarioView {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private JTable tableSelecionado;
	private DefaultTableModel modelSelecionado;
	private JButton btnMarcarComoLida;
	private JButton btnFechar;

	/**
	 * @author yvesl
	 * View visualizar as notificações e marcá-las como vistas
	 * PERMITIDO DE ACESSO: Usuário comum
	 */
	
	public NotificacoesUsuarioView() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 945, 488);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		JLabel lblNNotNaoLidas = new JLabel("Notificações não lidas:");
		lblNNotNaoLidas.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNNotNaoLidas.setBounds(37, 23, 255, 30);
		frame.getContentPane().add(lblNNotNaoLidas);

		JScrollPane scrollPaneNot = new JScrollPane();
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		model = new DefaultTableModel();
		Object[] column1 = { "id", "Descrição", "status", "Data" };
		model.setColumnIdentifiers(column1);
		table.setModel(model);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

		scrollPaneNot.setViewportView(table);
		scrollPaneNot.setBounds(37, 58, 858, 292);
		frame.getContentPane().add(scrollPaneNot);

		JScrollPane scrollPaneNotSelecionado = new JScrollPane();
		tableSelecionado = new JTable();
		tableSelecionado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		modelSelecionado = new DefaultTableModel();
		Object[] column2 = { "", "", "", "" };
		modelSelecionado.setColumnIdentifiers(column2);
		tableSelecionado.setModel(modelSelecionado);
		tableSelecionado.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

		scrollPaneNotSelecionado.setViewportView(tableSelecionado);
		scrollPaneNotSelecionado.setBounds(37, 361, 858, 24);
		frame.getContentPane().add(scrollPaneNotSelecionado);

		btnMarcarComoLida = new JButton("Marcar como lida");
		btnMarcarComoLida.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnMarcarComoLida.setBounds(640, 402, 255, 30);
		frame.getContentPane().add(btnMarcarComoLida);

		btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnFechar.setBounds(37, 402, 217, 30);
		frame.getContentPane().add(btnFechar);
	}

	public JFrame getFrame() {
		return frame;
	}

	public JTable getTable() {
		return table;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public JTable getTableSelecionado() {
		return tableSelecionado;
	}

	public DefaultTableModel getModelSelecionado() {
		return modelSelecionado;
	}

	public JButton getBtnMarcarComoLida() {
		return btnMarcarComoLida;
	}

	public JButton getBtnFechar() {
		return btnFechar;
	}
}
