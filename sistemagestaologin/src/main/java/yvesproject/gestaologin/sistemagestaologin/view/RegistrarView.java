package yvesproject.gestaologin.sistemagestaologin.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JTextField txtSenha;
	private JButton btnRegistrar;
	private JLabel lblErro;
	private JTextField txtCPF;
	
	public RegistrarView() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100,100, 854, 488);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JLabel lblNomeCentral = new JLabel("Register");
		lblNomeCentral.setBounds(300, 38, 241, 52);
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
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(500, 396, 116, 31);
		btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(btnRegistrar);
		
		lblErro = new JLabel("");
		lblErro.setForeground(new Color(255, 0, 0));
		lblErro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblErro.setBounds(216, 366, 401, 19);
		frame.getContentPane().add(lblErro);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblCpf.setBounds(216, 298, 157, 27);
		frame.getContentPane().add(lblCpf);
		
		txtCPF = new JTextField();
		txtCPF.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtCPF.setColumns(10);
		txtCPF.setBounds(216, 325, 401, 40);
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
}
