package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import com.pss.senha.validacao.ValidadorSenha;

import yvesproject.gestaologin.sistemagestaologin.DAO.ConexaoSingletonDAO;
import yvesproject.gestaologin.sistemagestaologin.DAO.FactorySQLiteDAO;
import yvesproject.gestaologin.sistemagestaologin.bussiness.log.SingletonLogStrategy;
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
				if(!view.getTxtNovaSenha().getText().isEmpty() && !view.getTxtConfirmarNovaSenha().getText().isEmpty()) {
					if(view.getTxtNovaSenha().getText().equals(view.getTxtConfirmarNovaSenha().getText())) {
						validadorSenha = new ValidadorSenha();
						List<String> retorno = validadorSenha.validar(view.getTxtNovaSenha().getText());
						if (retorno.isEmpty()) {
							usuario = new Usuario(usuario.getIdUsuario(), usuario.getEmail(), view.getTxtNovaSenha().getText(), usuario.getTipo(), usuario.getState(), usuario.getNome(), usuario.getCpf(),
									usuario.getNotEnviadas(), usuario.getNotLidas(), usuario.getDataCadastro());
							ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
							try {
								ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().atualizar(usuario);
								SingletonLogStrategy.getInstance().getLog().registrarLog("Atualização de senha",
										usuario.getNome(),
										usuario.getTipo());
							} catch (SQLException | IOException e1) {
								try {
									SingletonLogStrategy.getInstance().getLog().registrarErroLog(e1.getMessage(), "Leitura de notificações",
											usuario.getNome(),
											usuario.getTipo());
								} catch (IOException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								e1.printStackTrace();
							}
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
