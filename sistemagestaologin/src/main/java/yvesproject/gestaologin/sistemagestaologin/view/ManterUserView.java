package yvesproject.gestaologin.sistemagestaologin.view;

import javax.swing.JFrame;

public class ManterUserView {

	private JFrame frame;

	/**
	 * @author yvesl
	 * View que contém o CRUD para manipulação dos registros dos usuários cadastrados
	 * PERMITIDO DE ACESSO: Adminstrador
	 */
	
	public ManterUserView() {
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
