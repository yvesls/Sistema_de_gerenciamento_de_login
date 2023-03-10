package yvesproject.gestaologin.sistemagestaologin;

import java.awt.EventQueue;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.bussiness.log.SingletonLogStrategy;
import yvesproject.gestaologin.sistemagestaologin.presenter.LoginPresenter;
import yvesproject.gestaologin.sistemagestaologin.view.LogarView;

public class App {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
					SingletonLogStrategy.configurarSingleton();
					new LoginPresenter(new LogarView());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
