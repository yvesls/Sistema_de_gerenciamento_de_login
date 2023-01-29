package yvesproject.gestaologin.sistemagestaologin.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import yvesproject.gestaologin.sistemagestaologin.bussiness.loginstate.DesabilitarUsuarioState;
import yvesproject.gestaologin.sistemagestaologin.bussiness.loginstate.UsuarioState;

public class Usuario {
	private int idUsuario;
	private String email;
	private String senha;
	private String tipo;
	private String state;
	private String nome;
	private String cpf;
	private int notEnviadas;
	private int notLidas;
	private String dataCadastro;
	private UsuarioState status;
	
	public Usuario() {
	}
	
	// construtor para validação de usuário
	public Usuario(int idUsuario, String state) {
		this.idUsuario = idUsuario;
		if(state.equals("usuario")) {
			this.status =  new DesabilitarUsuarioState(this);
		}
	}
	
	// Construtor para usuário registrado
	public Usuario(int idUsuario, String email, String senha, String tipo, String state, String nome, String cpf,
			int notEnviadas, int notLidas, String dataCadastro) {
		this.idUsuario = idUsuario;
		this.email = email;
		this.senha = senha;
		this.tipo = tipo;
		this.nome = nome;
		this.cpf = cpf;
		this.state = state;
		this.notEnviadas = notEnviadas;
		this.notLidas = notLidas;
		this.dataCadastro = dataCadastro;
	}

	
	// Construtor para usuário não registrado
	public Usuario(String email, String senha, String tipo, String state, String nome, String cpf, int notEnviadas, int notLidas) {
		this.email = email;
		this.senha = senha;
		this.tipo = tipo;
		this.state = state;
		this.nome = nome;
		this.cpf = cpf;
		this.notEnviadas = notEnviadas;
		this.notLidas = notLidas;
		LocalDateTime data = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = data.format(formato);
		this.dataCadastro = dataFormatada;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public String getTipo() {
		return tipo;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public String getState() {
		return state;
	}

	public int getNotEnviadas() {
		return notEnviadas;
	}

	public int getNotLidas() {
		return notLidas;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public void setNotEnviadas(int notEnviadas) {
		this.notEnviadas = notEnviadas;
	}

	public void setNotLidas(int notLidas) {
		this.notLidas = notLidas;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public void ativarUsuarioState(Usuario modificarStateUsuario) {
        this.status.ativarUsuario(modificarStateUsuario);
    }

    public void cancelarUsuarioState(Usuario modificarStateUsuario) {
        this.status.cancelarUsuario(modificarStateUsuario);
    }

	public UsuarioState getStatus() {
		return status;
	}

	public void setStatus(UsuarioState status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", email=" + email + ", senha=" + senha + ", tipo=" + tipo
				+ ", state=" + state + ", nome=" + nome + ", cpf=" + cpf + ", notEnviadas=" + notEnviadas
				+ ", notLidas=" + notLidas + ", dataCadastro=" + dataCadastro + ", status=" + status + "]";
	}
}
