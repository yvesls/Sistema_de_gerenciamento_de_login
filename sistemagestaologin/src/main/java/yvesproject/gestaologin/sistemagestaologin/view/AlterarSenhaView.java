package yvesproject.gestaologin.sistemagestaologin.view;

import javax.swing.JFrame;

public class AlterarSenhaView {

	private JFrame frame;

	/**
	 * @author yvesl
	 * View de registro de novos usuários
	 * PERMITIDO DE ACESSO: Usuário comum
	 */
	
	public AlterarSenhaView() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds( 100,100, 854, 488);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
