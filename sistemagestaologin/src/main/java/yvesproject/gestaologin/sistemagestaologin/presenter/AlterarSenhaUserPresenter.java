package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import com.pss.senha.validacao.ValidadorSenha;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.model.Usuario;
import yvesproject.gestaologin.sistemagestaologin.view.AlterarSenhaUserView;

public class AlterarSenhaUserPresenter {
	private AlterarSenhaUserView view;
	private ValidadorSenha validadorSenha;
	private Usuario usuario;

	public AlterarSenhaUserPresenter(AlterarSenhaUserView view, Usuario usuario) {
		this.view = view;
		this.usuario = usuario;
		regatarAcoesView();
	}

	private void regatarAcoesView() {
		view.getBtnSalvarNovaSenha().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Valida a nova senha e registra-a
				if(!view.getTxtNovaSenha().getText().isEmpty() && view.getTxtConfirmarNovaSenha().getText().isEmpty()) {
					if(view.getTxtNovaSenha().getText() == view.getTxtConfirmarNovaSenha().getText()) {
						List<String> retorno = validadorSenha.validar(view.getTxtNovaSenha().getText());
						if (retorno.isEmpty()) {
							usuario = new Usuario(usuario.getIdUsuario(), usuario.getEmail(), view.getTxtNovaSenha().getText(), usuario.getTipo(), usuario.getState(), usuario.getNome(), usuario.getCpf(),
									usuario.getNotEnviadas(), usuario.getNotLidas(), usuario.getDataCadastro());
							ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
							ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().atualizar(usuario);
							JOptionPane.showMessageDialog(null, "Senha alterada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
							view.getFrame().setVisible(false);
						}else {
							// exibe o motivo da não aceitação da senha registrada
							String retornoExtenso = "";
							for (String valiNecessaria : retorno) {
								retornoExtenso += valiNecessaria.replaceAll(";", "") + "\n";
							}
							JOptionPane.showMessageDialog(null, retornoExtenso, "Atenção", JOptionPane.INFORMATION_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "A confirmação da nova senha tem que ser igual nova senha.", "Atenção", JOptionPane.INFORMATION_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Um dos campos está em branco.", "Atenção", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
	}

}
