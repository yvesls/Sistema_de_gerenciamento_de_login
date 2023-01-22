package yvesproject.gestaologin.sistemagestaologin.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class LoginView {
	private JFrame frame;
	private JTextField txtEmail;
	private JTextField txtSenha;
	private JButton btnLogar;
	private JButton btnOpenRegistrar;
	private JLabel lblErro;
	
	/**
	 * @author yvesl 
	 * Tela de login de todos os usuários
	 * PERMITIDO DE ACESSO: Todos
	 */

	public LoginView() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100,100, 854, 488);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JLabel lblNomeCentral = new JLabel("Login");
		lblNomeCentral.setBounds(357, 38, 110, 52);
		lblNomeCentral.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeCentral.setFont(new Font("Trebuchet MS", Font.BOLD, 44));
		frame.getContentPane().add(lblNomeCentral);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(216, 166, 401, 40);
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 28));
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		txtSenha = new JTextField();
		txtSenha.setBounds(216, 247, 401, 40);
		txtSenha.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtSenha.setColumns(10);
		frame.getContentPane().add(txtSenha);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(216, 137, 64, 29);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(lblEmail);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(216, 217, 157, 27);
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(lblSenha);
		
		btnLogar = new JButton("Logar");
		btnLogar.setBounds(525, 316, 92, 31);
		btnLogar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(btnLogar);
		
		btnOpenRegistrar = new JButton("Registrar");
		btnOpenRegistrar.setBounds(379, 316, 116, 31);
		btnOpenRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(btnOpenRegistrar);
		
		lblErro = new JLabel("");
		lblErro.setForeground(new Color(255, 0, 0));
		lblErro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblErro.setBounds(216, 286, 401, 19);
		frame.getContentPane().add(lblErro);
	}

	public JFrame getFrame() {
		return frame;
	}

	public JTextField getTxtEmail() {
		return txtEmail;
	}

	public JTextField getTxtSenha() {
		return txtSenha;
	}

	public JButton getBtnLogar() {
		return btnLogar;
	}

	public JButton getBtnOpenRegistrar() {
		return btnOpenRegistrar;
	}

	public JLabel getLblErro() {
		return lblErro;
	}
}
