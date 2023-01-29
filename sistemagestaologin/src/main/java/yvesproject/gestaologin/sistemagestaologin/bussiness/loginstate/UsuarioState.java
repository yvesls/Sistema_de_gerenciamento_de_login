package yvesproject.gestaologin.sistemagestaologin.bussiness.loginstate;

import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public abstract class UsuarioState {
	protected Usuario user;

	public UsuarioState(Usuario user) {
		this.user = user;
	}

	public void ativarUsuario(Usuario modificarStateUsuario) {
		throw new RuntimeException("Você não está autorizado à realizar a ativação do usuário.");
	}

	public void cancelarUsuario(Usuario modificarStateUsuario) {
		throw new RuntimeException("Você não está autorizado à realizar o cancelamento do usuário.");
	}
}
