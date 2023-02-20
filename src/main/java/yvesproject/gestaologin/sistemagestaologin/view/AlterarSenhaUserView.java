package yvesproject.gestaologin.sistemagestaologin.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AlterarSenhaUserView {

	private JFrame frame;
	private JPasswordField txtNovaSenha;
	private JButton btnSalvarNovaSenha;
	private JPasswordField txtConfirmarNovaSenha;
	
	/**
	 * @author yvesl
	 * View para alterar senha 
	 * PERMITIDO DE ACESSO: Usuário comum
	 */
	
	public AlterarSenhaUserView() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100,100, 854, 488);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		txtNovaSenha = new JPasswordField();
		txtNovaSenha.setBounds(216, 146, 401, 40);
		txtNovaSenha.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtNovaSenha.setColumns(10);
		frame.getContentPane().add(txtNovaSenha);
		
		JLabel lblNovaSenha = new JLabel("Nova senha:");
		lblNovaSenha.setBounds(216, 118, 157, 27);
		lblNovaSenha.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(lblNovaSenha);
		
		btnSalvarNovaSenha = new JButton("Salvar");
		btnSalvarNovaSenha.setBounds(525, 316, 92, 31);
		btnSalvarNovaSenha.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(btnSalvarNovaSenha);
		
		JLabel lblConfirmarNovaSenha = new JLabel("Confirmar nova senha:");
		lblConfirmarNovaSenha.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblConfirmarNovaSenha.setBounds(216, 223, 269, 27);
		frame.getContentPane().add(lblConfirmarNovaSenha);
		
		txtConfirmarNovaSenha = new JPasswordField();
		txtConfirmarNovaSenha.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtConfirmarNovaSenha.setColumns(10);
		txtConfirmarNovaSenha.setBounds(216, 250, 401, 40);
		frame.getContentPane().add(txtConfirmarNovaSenha);
	}

	public JFrame getFrame() {
		return frame;
	}

	public JTextField getTxtNovaSenha() {
		return txtNovaSenha;
	}

	public JButton getBtnSalvarNovaSenha() {
		return btnSalvarNovaSenha;
	}

	public JTextField getTxtConfirmarNovaSenha() {
		return txtConfirmarNovaSenha;
	}
}
