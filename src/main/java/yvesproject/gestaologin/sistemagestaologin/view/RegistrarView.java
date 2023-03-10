package yvesproject.gestaologin.sistemagestaologin.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class RegistrarView {
	/**
	 * @author yvesl
	 * View de registro de novos usuários
	 * PERMITIDO DE ACESSO: Usuário comum
	 */
	
	private JFrame frame;
	private JTextField txtEmail;
	private JPasswordField txtSenha;
	private JButton btnRegistrar;
	private JLabel lblErro;
	private JTextField txtCPF;
	private JTextField txtNome;
	
	public RegistrarView() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100,100, 854, 550);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JLabel lblNomeCentral = new JLabel("Register");
		lblNomeCentral.setBounds(300, 38, 241, 52);
		lblNomeCentral.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeCentral.setFont(new Font("Trebuchet MS", Font.BOLD, 44));
		frame.getContentPane().add(lblNomeCentral);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(216, 139, 401, 40);
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 28));
		frame.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(216, 218, 401, 40);
		txtSenha.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtSenha.setColumns(10);
		frame.getContentPane().add(txtSenha);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(216, 109, 64, 29);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(lblEmail);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(216, 190, 157, 27);
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(lblSenha);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(501, 444, 116, 31);
		btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(btnRegistrar);
		
		lblErro = new JLabel("");
		lblErro.setForeground(new Color(255, 0, 0));
		lblErro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblErro.setBounds(216, 366, 401, 19);
		frame.getContentPane().add(lblErro);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblCpf.setBounds(216, 269, 157, 27);
		frame.getContentPane().add(lblCpf);
		
		txtNome = new JTextField();
		txtNome.setBounds(216, 380, 401, 40);
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtNome.setColumns(10);
		frame.getContentPane().add(txtNome);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(216, 350, 157, 27);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(lblNome);
		
		txtCPF = new JTextField();
		txtCPF.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtCPF.setColumns(10);
		txtCPF.setBounds(216, 299, 401, 40);
		frame.getContentPane().add(txtCPF);
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

	public JButton getBtnRegistrar() {
		return btnRegistrar;
	}

	public JLabel getLblErro() {
		return lblErro;
	}

	public JTextField getTxtCPF() {
		return txtCPF;
	}

	public JTextField getTxtNome() {
		return txtNome;
	}
}
