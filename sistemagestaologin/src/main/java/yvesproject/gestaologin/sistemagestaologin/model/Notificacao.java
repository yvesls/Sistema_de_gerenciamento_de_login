package yvesproject.gestaologin.sistemagestaologin.model;

import java.util.Date;

public class Notificacao {
	private int id;
	private String descricao;
	private Date data;
	private String status;
	
	public Notificacao() {
	}
	
	// construtor para quando a notificação foi registrada
	public Notificacao(int id, String descricao, Date data, String status) {
		this.id = id;
		this.descricao = descricao;
		this.data = data;
		this.status = status;
	}
	
	// construtor para quando a notificação não foi criada
	public Notificacao(String descricao, Date data, String status) {
		this.descricao = descricao;
		this.data = data;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	public Date getData() {
		return data;
	}
	public String getStatus() {
		return status;
	}
}
