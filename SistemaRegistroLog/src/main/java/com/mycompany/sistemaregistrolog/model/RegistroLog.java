/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sistemaregistrolog.model;

public class RegistroLog {
	String operacao;
    String nome;
    String data;
    String hora;
    String tipoUsuario;
    String mensagemErro;

    public RegistroLog() {
    }
    
    public RegistroLog(String operacao, String nome, String data, String hora, String tipoUsuario) {
		super();
		this.operacao = operacao;
		this.nome = nome;
		this.data = data;
		this.hora = hora;
		this.tipoUsuario = tipoUsuario;
	}

    public RegistroLog(String mensagemErro, String operacao, String nome, String data, String hora, String tipoUsuario) {

		this.operacao = operacao;
		this.nome = nome;
		this.data = data;
		this.hora = hora;
		this.tipoUsuario = tipoUsuario;
		this.mensagemErro = mensagemErro;
	}

	public String getOperacao() {
		return operacao;
	}

	public String getNome() {
		return nome;
	}

	public String getData() {
		return data;
	}

	public String getHora() {
		return hora;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}
}
