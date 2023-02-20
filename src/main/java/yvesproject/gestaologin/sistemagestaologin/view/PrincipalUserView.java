package yvesproject.gestaologin.sistemagestaologin.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class PrincipalUserView {

	private JFrame frame;
	private JLabel lblUserName;
	private JLabel lblUserTypeName;
	private JButton btnOpenNotificacoes;
	private JButton btnOpenAlterarSenha;
	private JButton btnAtualizarManualmente;
	/**
	 * @author yvesl
	 * View principal depois que o usuário é autenticado
	 * PERMITIDO DE ACESSO: Usuário comum
	 */
	
	public PrincipalUserView() {
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
		panel.setBounds(0, 408, 929, 41);
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
		lblNotificacoes.setBounds(729, 11, 133, 19);
		panel.add(lblNotificacoes);
		
		btnOpenNotificacoes = new JButton("0");
		btnOpenNotificacoes.setBackground(new Color(192, 192, 192));
		btnOpenNotificacoes.setBounds(872, 11, 47, 23);
		panel.add(btnOpenNotificacoes);
		
		btnOpenAlterarSenha = new JButton("Alterar Senha");
		btnOpenAlterarSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOpenAlterarSenha.setBounds(699, 372, 220, 25);
		frame.getContentPane().add(btnOpenAlterarSenha);
		
		btnAtualizarManualmente = new JButton("Atualizar");
		btnAtualizarManualmente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAtualizarManualmente.setBounds(765, 11, 154, 33);
		frame.getContentPane().add(btnAtualizarManualmente);
		
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

	public JButton getBtnOpenNotificacoes() {
		return btnOpenNotificacoes;
	}

	public JButton getBtnOpenAlterarSenha() {
		return btnOpenAlterarSenha;
	}

	public JButton getBtnAtualizarManualmente() {
		return btnAtualizarManualmente;
	}
}
