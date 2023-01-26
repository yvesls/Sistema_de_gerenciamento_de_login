package yvesproject.gestaologin.sistemagestaologin;

import java.awt.EventQueue;
import yvesproject.gestaologin.sistemagestaologin.presenter.LoginPresenter;
import yvesproject.gestaologin.sistemagestaologin.view.LogarView;

public class App {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogarView window = new LogarView();
					new LoginPresenter(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
