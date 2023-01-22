package yvesproject.gestaologin.sistemagestaologin.model;

import java.util.Date;

public class User {
	private int id;
	private String type;
	private String nome;
	private int cpf;
	private Date dataCadastro;
	private String state;
	private int notEnviadas;
	private int notLidas;
	
	public User() {
	}

	// Construtor para usuário registrado
	public User(int id, String type, String nome, int cpf, Date dataCadastro, String state, int notEnviadas, int notLidas) {
		this.id = id;
		this.type = type;
		this.nome = nome;
		this.cpf = cpf;
		this.dataCadastro = dataCadastro;
		this.state = state;
		this.notEnviadas = notEnviadas;
		this.notLidas = notLidas;
	}
	
	// Construtor para usuário não registrado
	public User(String type, String nome, int cpf, Date dataCadastro, String state, int notEnviadas, int notLidas) {
		this.type = type;
		this.nome = nome;
		this.cpf = cpf;
		this.dataCadastro = dataCadastro;
		this.state = state;
		this.notEnviadas = notEnviadas;
		this.notLidas = notLidas;
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getNome() {
		return nome;
	}

	public int getCpf() {
		return cpf;
	}

	public Date getDataCadastro() {
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
}
