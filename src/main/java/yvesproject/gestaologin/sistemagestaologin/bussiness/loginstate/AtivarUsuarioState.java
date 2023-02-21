package yvesproject.gestaologin.sistemagestaologin.bussiness.loginstate;

import java.awt.HeadlessException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public class AtivarUsuarioState extends UsuarioState {
	public AtivarUsuarioState(Usuario user) {
		super(user);
		user.setState("ativo");
		try {
			if(ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().atualizarStateUsuario(user)) {
				JOptionPane.showMessageDialog(null, "Situação do usuário atualizada!");
			}
		} catch (HeadlessException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cancelarUsuario(Usuario modificarStateUsuario) {
		if (!modificarStateUsuario.getTipo().equals("administrador")) {
			throw new RuntimeException("Você não está autorizado à realizar a cancelamento do usuário.");
		}
		this.user.setStatus(new CancelarUsuarioState(this.user));
	}
}
