package yvesproject.gestaologin.sistemagestaologin.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Notificacao {
	private int idNotificacao;
	private int idRemetente;
	private int idDestinatario;
	private String descricao;
	private String data;
	private String status;
	
	public Notificacao() {
	}
	
	// construtor para quando a notificação foi registrada
	public Notificacao(int idNotificacao,int idRemetente,int idDestinatario, String descricao, String data, String status) {
		this.idNotificacao = idNotificacao;
		this.idRemetente = idRemetente;
		this.idDestinatario = idDestinatario;
		this.descricao = descricao;
		this.data = data;
		this.status = status;
	}
	
	// construtor para quando a notificação não foi criada
	public Notificacao(int idRemetente,int idDestinatario, String descricao, String status) {
		this.idRemetente = idRemetente;
		this.idDestinatario = idDestinatario;
		this.descricao = descricao;
		this.status = status;
		LocalDateTime data = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = data.format(formato);
		this.data = dataFormatada;
	}
	
	public int getIdNotificacao() {
		return idNotificacao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public String getData() {
		return data;
	}
	public String getStatus() {
		return status;
	}

	public int getIdRemetente() {
		return idRemetente;
	}

	public int getIdDestinatario() {
		return idDestinatario;
	}
}
