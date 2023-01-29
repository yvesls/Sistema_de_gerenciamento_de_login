package yvesproject.gestaologin.sistemagestaologin.bussiness.loginstate;

import javax.swing.JOptionPane;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public class CancelarUsuarioState extends UsuarioState {
	public CancelarUsuarioState(Usuario user) {
		super(user);
		user.setState("cancelado");
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		if(ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().atualizarStateUsuario(user)) {
			JOptionPane.showMessageDialog(null, "Situação do usuário atualizada!");
		}
	}

}
