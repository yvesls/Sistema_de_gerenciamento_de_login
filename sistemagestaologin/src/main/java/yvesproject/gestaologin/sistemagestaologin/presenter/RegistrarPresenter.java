package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import com.pss.senha.validacao.ValidadorSenha;

import yvesproject.gestaologin.sistemagestaologin.view.RegistrarView;

public class RegistrarPresenter {
	private RegistrarView view;
	private ValidadorSenha validadorSenha;
	private int validadorCamposPreenchidos;

	public RegistrarPresenter(RegistrarView view) {
		this.view = view;
		regatarAcoesView();
		this.validadorSenha = new ValidadorSenha();
		this.validadorCamposPreenchidos = 0;
	}

	private void regatarAcoesView() {
		
		view.getBtnRegistrar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// realiza o cadastro e fecha a janela
				
				if(!view.getTxtSenha().getText().isEmpty() && !view.getTxtEmail().getText().isEmpty() && !view.getTxtCPF().getText().isEmpty()) {
					List<String> retorno = validadorSenha.validar(view.getTxtSenha().getText());
					if(retorno.isEmpty()) {
						validadorCamposPreenchidos++;
					}else {
						String retornoExtenso = "";
						for(String valiNecessaria: retorno) {
							retornoExtenso += valiNecessaria.replaceAll(";", "") + "\n";
						}
						JOptionPane.showMessageDialog(null, retornoExtenso, "Atenção",
								JOptionPane.INFORMATION_MESSAGE);
					}
					if(view.getTxtCPF().getText().length() == 11) {
						validadorCamposPreenchidos++;
					}else {
						JOptionPane.showMessageDialog(null, "Digite somente números para o CPF.", "Atenção",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.", "Atenção",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if(validadorCamposPreenchidos == 2) {
					// executa o registro 
					JOptionPane.showMessageDialog(null, "Registrado com sucesso.", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
					view.getFrame().setVisible(false);
				}
			}
		});
	}
}
