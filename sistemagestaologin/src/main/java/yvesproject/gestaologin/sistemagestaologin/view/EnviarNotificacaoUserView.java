package yvesproject.gestaologin.sistemagestaologin.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class EnviarNotificacaoUserView {

	private JFrame frame;
	private JTextField txtMensagem;
	private JButton btnEnviarNotificacao;
	/**
	 * @author yvesl
	 * View que permite o envio de notificações para o usuário selecionado
	 * PERMITIDO DE ACESSO: Adminstrador
	 */
	
	public EnviarNotificacaoUserView() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100,100, 854, 266);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		txtMensagem = new JTextField();
		txtMensagem.setBounds(62, 98, 555, 40);
		txtMensagem.setFont(new Font("Tahoma", Font.PLAIN, 28));
		txtMensagem.setColumns(10);
		frame.getContentPane().add(txtMensagem);
		
		JLabel lblNovaSenha = new JLabel("Mensagem:");
		lblNovaSenha.setBounds(62, 60, 157, 40);
		lblNovaSenha.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(lblNovaSenha);
		
		btnEnviarNotificacao = new JButton("Enviar");
		btnEnviarNotificacao.setBounds(648, 98, 92, 40);
		btnEnviarNotificacao.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frame.getContentPane().add(btnEnviarNotificacao);
	}

	public JFrame getFrame() {
		return frame;
	}

	public JTextField getTxtMensagem() {
		return txtMensagem;
	}

	public JButton getBtnEnviarNotificacao() {
		return btnEnviarNotificacao;
	}
}
