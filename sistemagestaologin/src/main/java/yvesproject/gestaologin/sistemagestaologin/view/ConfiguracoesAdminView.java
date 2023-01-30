package yvesproject.gestaologin.sistemagestaologin.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class ConfiguracoesAdminView {

	private JFrame frame;
	private JRadioButton logCsv;
	private JRadioButton logJson;
	private JButton btnConfirmar;
	
	/**
	 * @author yvesl
	 * View para alterar senha 
	 * PERMITIDO DE ACESSO: Usuário comum
	 */
	
	public ConfiguracoesAdminView() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100,100, 854, 488);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JLabel lblNovaSenha = new JLabel("Escolha o formato do arquivo de registro de operações:");
		lblNovaSenha.setBounds(216, 118, 157, 27);
		lblNovaSenha.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(lblNovaSenha);
		
		logCsv = new JRadioButton("CSV", true);
		logCsv.setFont(new Font("Tahoma", Font.PLAIN, 28));
		logCsv.setBounds(216, 146, 401, 40);
		frame.getContentPane().add(logCsv);
		
		
		logJson = new JRadioButton("JSON", false);
		logJson.setFont(new Font("Tahoma", Font.PLAIN, 28));
		logJson.setBounds(525, 316, 92, 31);
		frame.getContentPane().add(logJson);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setFont(new Font("Tahoma", Font.PLAIN, 28));
		btnConfirmar.setBounds(216, 250, 401, 40);
		frame.getContentPane().add(btnConfirmar);
	}

	public JFrame getFrame() {
		return frame;
	}

	public JRadioButton getLogCsv() {
		return logCsv;
	}

	public JRadioButton getLogJson() {
		return logJson;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}
}


