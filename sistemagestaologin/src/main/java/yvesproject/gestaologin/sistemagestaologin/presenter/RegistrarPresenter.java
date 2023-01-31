package yvesproject.gestaologin.sistemagestaologin.presenter;

import java.awt.HeadlessException;
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
import yvesproject.gestaologin.sistemagestaologin.service.NotificacaoService;
import yvesproject.gestaologin.sistemagestaologin.view.RegistrarView;

public class RegistrarPresenter {
	private RegistrarView view;
	private ValidadorSenha validadorSenha;
	private int validadorCamposPreenchidos;
	private Usuario novoUsuario;
	private int idGerado;
	private NotificacaoService notService;

	public RegistrarPresenter(RegistrarView view) {
		this.view = view;
		regatarAcoesView();
		this.validadorSenha = new ValidadorSenha();
		this.validadorCamposPreenchidos = 0;
	}

	private void regatarAcoesView() {

		view.getBtnRegistrar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// valida se todos os campos foram preenchidos
				if (!view.getTxtSenha().getText().isEmpty() && !view.getTxtEmail().getText().isEmpty()
						&& !view.getTxtCPF().getText().isEmpty() && !view.getTxtNome().getText().isEmpty()) {
					// chama o validador de senhas
					List<String> retorno = validadorSenha.validar(view.getTxtSenha().getText());
					if (retorno.isEmpty()) {
						validadorCamposPreenchidos++;
					} else {
						// exibe o motivo da não aceitação da senha registrada
						String retornoExtenso = "";
						for (String valiNecessaria : retorno) {
							retornoExtenso += valiNecessaria.replaceAll(";", "") + "\n";
						}
						JOptionPane.showMessageDialog(null, retornoExtenso, "Atenção", JOptionPane.INFORMATION_MESSAGE);
						validadorCamposPreenchidos = 0;
					}
					if (view.getTxtCPF().getText().length() == 11) {
						// valida o tamanho do cpf (infelizmente só possui essa validação até o momento)
						validadorCamposPreenchidos++;
					} else {
						JOptionPane.showMessageDialog(null,
								"CPF incorreto. Falta dados ou precisa digitar somente números.", "Atenção",
								JOptionPane.INFORMATION_MESSAGE);
						validadorCamposPreenchidos = 0;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.", "Atenção",
							JOptionPane.INFORMATION_MESSAGE);
				}
				// executa o registro
				executaRegistro();
			}
		});
	}

	// registra o novo usuário
	public int registrarNovoUsuario() throws SQLException {
		ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
		return ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().salvar(novoUsuario);
	}

	public void executaRegistro() {
		if (validadorCamposPreenchidos == 2) {
			// verifica se há usuários cadastrados, caso não tenha, registra o primeiro usuário como administrador
			ConexaoSingletonDAO.configurarSingleton(new FactorySQLiteDAO());
			try {
				if (ConexaoSingletonDAO.getInstance().getUsuarioSqliteDAO().getIsUsuarios()) {
					// registrar usuário comum
					novoUsuario = new Usuario(view.getTxtEmail().getText(), view.getTxtSenha().getText(), "usuario",
							"aguardando autetificação", view.getTxtNome().getText(), view.getTxtCPF().getText(), 0, 0);
					idGerado = registrarNovoUsuario();
					if (idGerado != -1) {
						notService = new NotificacaoService();
						try {
							if(!notService.enviarNotificacaoDeAutentificacao(idGerado, novoUsuario)) {
								JOptionPane.showMessageDialog(null,
										"Ocorreu um erro inesperado ao enviar a notificação de autorização para login.",
										"Atenção", JOptionPane.INFORMATION_MESSAGE);
							}else {
								SingletonLogStrategy.getInstance().getLog().registrarLog("Leitura de notificações",
										novoUsuario.getNome(),
										novoUsuario.getTipo());
							}
						} catch (HeadlessException | SQLException | IOException e) {
							try {
								SingletonLogStrategy.getInstance().getLog().registrarErroLog(e.getMessage(), "Leitura de notificações",
										novoUsuario.getNome(),
										novoUsuario.getTipo());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Registrado com sucesso.", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
						view.getFrame().setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null,
								"Ocorreu um erro inesperado ao registrar o usuário. Tente novamente mais tarde.",
								"Atenção", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					// registrar administrador
					novoUsuario = new Usuario(view.getTxtEmail().getText(), view.getTxtSenha().getText(),
							"administrador", "ativo", view.getTxtNome().getText(), view.getTxtCPF().getText(), 0,
							0);
					idGerado = registrarNovoUsuario();
					if (idGerado != -1) {
						JOptionPane.showMessageDialog(null, "Registrado com sucesso.", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
						// fecha a janela
						view.getFrame().setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null,
								"Ocorreu um erro inesperado ao registrar o usuário. Tente novamente mais tarde.",
								"Atenção", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				SingletonLogStrategy.getInstance().getLog().registrarLog("Inclusão de usuário",
						novoUsuario.getNome(),
						novoUsuario.getTipo());
			} catch (HeadlessException | SQLException | IOException e) {
				try {
					SingletonLogStrategy.getInstance().getLog().registrarErroLog(e.getMessage(), "Inclusão de usuário",
							novoUsuario.getNome(),
							novoUsuario.getTipo());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}else {
		}
	}
}
