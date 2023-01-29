package yvesproject.gestaologin.sistemagestaologin.bussiness.loginstate;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public class DesabilitarUsuarioState extends UsuarioState {
	public DesabilitarUsuarioState(Usuario user) {
		super(user);
		user.setState("aguardando autentificação");
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().atualizarStateUsuario(user);  
	}

	@Override
	public void ativarUsuario(Usuario modificarStateUsuario) {
	    if (!modificarStateUsuario.getTipo().equals("administrador")) {
			throw new RuntimeException("Você não está autorizado à realizar a ativação do usuário.");
		}
		this.user.setStatus(new AtivarUsuarioState(this.user));
	}

	@Override
	public void cancelarUsuario(Usuario modificarStateUsuario) {
	    if (!modificarStateUsuario.getTipo().equals("administrador")) {
			throw new RuntimeException("Você não está autorizado à realizar a cancelamento do usuário.");
		}
		this.user.setStatus(new CancelarUsuarioState(this.user));
	}
}
