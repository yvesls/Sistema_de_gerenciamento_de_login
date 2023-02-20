package yvesproject.gestaologin.sistemagestaologin.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PrincipalAdminView {

	private JFrame frame;
	private JLabel lblUserName;
	private JLabel lblUserTypeName;
	private JButton btnOpenNotificacoes;
	private JButton btnOpenNotificarTodosUsuarios;
	private JTable table;
	private JButton btnBuscarUsuarios;
	private DefaultTableModel model;
	private JTable tableSelecionado;
	private DefaultTableModel modelSelecionado;
	private JTextField textFieldCampoBuscar;
	private JButton btnExcluirUsuario;
	private JButton btnOpenNotificarUsuarioSelecionado;
	private JButton btnAtualizarManualmente;
	private JButton btnConfiguracoes;
	private JButton btnOpenAlterarSenha;
	
	/**
	 * @author yvesl
	 * View principal depois que o usuário indentificado como administrador é autenticado
	 * PERMITIDO DE ACESSO: Adminstrador
	 */
	
	public PrincipalAdminView() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds( 100,100, 945, 488);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(0, 0, 0));
		panel.setBorder(new MatteBorder(6, 0, 0, 0, (Color) new Color(128, 128, 128)));
		panel.setBackground(new Color(249, 249, 249));
		panel.setBounds(0, 408, 919, 41);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUser = new JLabel("Usuário:");
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUser.setBounds(10, 11, 57, 19);
		panel.add(lblUser);
		
		lblUserName = new JLabel("");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserName.setBounds(77, 11, 220, 19);
		panel.add(lblUserName);
		
		JLabel lblUserType = new JLabel("Tipo:");
		lblUserType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserType.setBounds(307, 11, 57, 19);
		panel.add(lblUserType);
		
		lblUserTypeName = new JLabel("");
		lblUserTypeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserTypeName.setBounds(374, 11, 197, 19);
		panel.add(lblUserTypeName);
		
		JLabel lblNotificacoes = new JLabel("Notificações não lidas:");
		lblNotificacoes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNotificacoes.setBounds(719, 11, 133, 19);
		panel.add(lblNotificacoes);
		
		btnOpenNotificacoes = new JButton("0");
		btnOpenNotificacoes.setBackground(new Color(192, 192, 192));
		btnOpenNotificacoes.setBounds(862, 11, 47, 23);
		panel.add(btnOpenNotificacoes);
		
		btnOpenNotificarTodosUsuarios = new JButton("Notificar todos os usuários");
		btnOpenNotificarTodosUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOpenNotificarTodosUsuarios.setBounds(398, 373, 215, 26);
		frame.getContentPane().add(btnOpenNotificarTodosUsuarios);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 57, 33);
		frame.getContentPane().add(lblNewLabel);
		
		textFieldCampoBuscar = new JTextField();
		textFieldCampoBuscar.setBounds(61, 13, 290, 33);
		frame.getContentPane().add(textFieldCampoBuscar);
		textFieldCampoBuscar.setColumns(10);
		
		btnBuscarUsuarios = new JButton("Buscar");
		btnBuscarUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBuscarUsuarios.setBounds(365, 11, 101, 33);
		frame.getContentPane().add(btnBuscarUsuarios);
		
		btnConfiguracoes = new JButton("Configurações");
		btnConfiguracoes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnConfiguracoes.setBounds(179, 374, 150, 25);
		frame.getContentPane().add(btnConfiguracoes);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 909, 275);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		model = new DefaultTableModel();
		Object[] column = {"ID", "Nome", "CPF", "Data de cadastro", "status", "Notificações enviadas", "Notificações lidas"};
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPaneFuncSelecionado = new JScrollPane();
		tableSelecionado = new JTable();
		tableSelecionado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		modelSelecionado = new DefaultTableModel();
		Object[] column1 = {"", "", "", "", "", "", ""};
		modelSelecionado.setColumnIdentifiers(column1);
		tableSelecionado.setModel(modelSelecionado);
		tableSelecionado.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
		scrollPaneFuncSelecionado.setViewportView(tableSelecionado);
		scrollPaneFuncSelecionado.setBounds(10, 341, 909, 26);
		frame.getContentPane().add(scrollPaneFuncSelecionado);
		
		btnExcluirUsuario = new JButton("Excluir usuário");
		btnExcluirUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExcluirUsuario.setBounds(776, 373, 143, 26);
		frame.getContentPane().add(btnExcluirUsuario);
		
		btnOpenNotificarUsuarioSelecionado = new JButton("Notificar usuário");
		btnOpenNotificarUsuarioSelecionado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOpenNotificarUsuarioSelecionado.setBounds(623, 373, 143, 26);
		frame.getContentPane().add(btnOpenNotificarUsuarioSelecionado);
		
		btnAtualizarManualmente = new JButton("Atualizar");
		btnAtualizarManualmente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAtualizarManualmente.setBounds(765, 11, 154, 33);
		frame.getContentPane().add(btnAtualizarManualmente);
		
		btnOpenAlterarSenha = new JButton("Alterar Senha");
		btnOpenAlterarSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOpenAlterarSenha.setBounds(10, 374, 159, 25);
		frame.getContentPane().add(btnOpenAlterarSenha);
		
		JLabel lblAtualizarManualmente = new JLabel("Atualizar página manualmente:");
		lblAtualizarManualmente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAtualizarManualmente.setBounds(534, 11, 221, 33);
		frame.getContentPane().add(lblAtualizarManualmente);
	}

	public JFrame getFrame() {
		return frame;
	}

	public JLabel getLblUserName() {
		return lblUserName;
	}

	public JLabel getLblUserTypeName() {
		return lblUserTypeName;
	}

	public JButton getBtnOpenNotificarTodosUsuarios() {
		return btnOpenNotificarTodosUsuarios;
	}

	public JButton getBtnOpenNotificacoes() {
		return btnOpenNotificacoes;
	}

	public JTable getTable() {
		return table;
	}

	public JButton getBtnBuscarUsuarios() {
		return btnBuscarUsuarios;
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

	public JTextField getTextFieldCampoBuscar() {
		return textFieldCampoBuscar;
	}

	public JButton getBtnExcluirUsuario() {
		return btnExcluirUsuario;
	}

	public JButton getBtnOpenNotificarUsuarioSelecionado() {
		return btnOpenNotificarUsuarioSelecionado;
	}

	public JButton getBtnAtualizarManualmente() {
		return btnAtualizarManualmente;
	}

	public JButton getBtnConfiguracoes() {
		return btnConfiguracoes;
	}

	public JButton getBtnOpenAlterarSenha() {
		return btnOpenAlterarSenha;
	}
}
