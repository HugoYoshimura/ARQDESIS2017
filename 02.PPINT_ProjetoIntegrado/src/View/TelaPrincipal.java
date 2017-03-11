package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Controller.ConjuntoCtrl;
import Controller.EmpresaCtrl;
import Controller.UsuarioCtrl;
import Model.Empresa;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame implements ActionListener {
	private ResourceBundle bn = ResourceBundle.getBundle("tela", new Locale(
			"pt", "BR"));
	private JMenuBar menuBar;
	private JMenu arquivo, empresa, funcionario, catraca, ar, sair, idioma;
	private JMenuItem port, engl, esp, cadFuncionario, excFuncionario,
			conFuncionario, altFuncionario, cadEmpresa, excEmpresa, conEmpresa,
			altEmpresa, logout, sairIM;
	private int modo, idUser, idEmp;
	private JPanel tela;
	private JLabel mensagemLb;
	private DateFormat dtFormat;
	private JFormattedTextField dataNascimentoFT;

	// construtor sem argumento para configurar a GUI
	public TelaPrincipal(int type) {
		super("Controle Predial");
		criarMenu(type);
	}

	private void criarMenu(int type) {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		ItemHandler itemHandler = new ItemHandler();
		
		// Adicionando os menus a barra de menu
		arquivo = new JMenu(bn.getString("telaPrincipal.menu.arquivo"));
		empresa = new JMenu(bn.getString("telaPrincipal.menu.empresa"));
		funcionario = new JMenu(bn.getString("telaPrincipal.menu.funcionario"));
		catraca = new JMenu(bn.getString("telaPrincipal.menu.catraca"));
		ar = new JMenu(bn.getString("telaPrincipal.menu.ac"));
		sair = new JMenu(bn.getString("telaPrincipal.menu.sair"));

		// Criando itens do menu Arquivo
		idioma = new JMenu(bn.getString("telaPrincipal.menu.idioma"));
		port = new JMenuItem(bn.getString("telaPrincipal.menuitem.pt"));
		port.addActionListener(itemHandler);

		engl = new JMenuItem(bn.getString("telaPrincipal.menuitem.en"));
		engl.addActionListener(itemHandler);

		esp = new JMenuItem(bn.getString("telaPrincipal.menuitem.es"));
		esp.addActionListener(itemHandler);

		port.setEnabled(false);
		engl.setEnabled(true);
		esp.setEnabled(true);

		// Criando itens do menu Funcionário
		cadFuncionario = new JMenuItem(
				bn.getString("telaPrincipal.menuitemF.cadastrar"));
		cadFuncionario.addActionListener(itemHandler);

		excFuncionario = new JMenuItem(
				bn.getString("telaPrincipal.menuitemF.excluir"));
		excFuncionario.addActionListener(itemHandler);

		conFuncionario = new JMenuItem(
				bn.getString("telaPrincipal.menuitemF.consultar"));
		conFuncionario.addActionListener(itemHandler);

		altFuncionario = new JMenuItem(
				bn.getString("telaPrincipal.menuitemF.alterar"));
		altFuncionario.addActionListener(itemHandler);

		// Criando itens do menu Empresa
		cadEmpresa = new JMenuItem(
				bn.getString("telaPrincipal.menuitemE.cadastrar"));
		cadEmpresa.addActionListener(itemHandler);

		excEmpresa = new JMenuItem(
				bn.getString("telaPrincipal.menuitemE.excluir"));
		excEmpresa.addActionListener(itemHandler);
		
		conEmpresa = new JMenuItem(
				bn.getString("telaPrincipal.menuitemE.consultar"));
		conEmpresa.addActionListener(itemHandler);
		
		altEmpresa = new JMenuItem(
				bn.getString("telaPrincipal.menuitemE.alterar"));
		altEmpresa.addActionListener(itemHandler);

		// Criando itens do menu sair
		logout = new JMenuItem("Log out");
		logout.addActionListener(itemHandler);
		sairIM = new JMenuItem(bn.getString("telaPrincipal.menuitemE.sair"));
		sairIM.addActionListener(itemHandler);

		// Adicionando os itens no menuBar
		menuBar.add(arquivo);
		menuBar.add(empresa);
		menuBar.add(funcionario);
		menuBar.add(catraca);
		menuBar.add(ar);
		menuBar.add(sair);

		// Adicionando os itens do menu Arquivo

		arquivo.add(idioma);
		idioma.add(port);
		idioma.add(engl);
		idioma.add(esp);

		// Adicionando os itens no menu Funcionario
		funcionario.add(cadFuncionario);
		funcionario.add(excFuncionario);
		funcionario.add(conFuncionario);
		funcionario.add(altFuncionario);

		// Adicionando os itens no menu Empresa
		empresa.add(cadEmpresa);
		empresa.add(excEmpresa);
		empresa.add(conEmpresa);
		empresa.add(altEmpresa);

		// Adicionando o item no menu Sair
		sair.add(logout);
		sair.add(sairIM);

		dtFormat = new SimpleDateFormat("dd/MM/yyyy");
		dataNascimentoFT = new JFormattedTextField(dtFormat);

		add(dataNascimentoFT, BorderLayout.CENTER);
		boolean f = false;
		if (type == 1) {
			arquivo.setEnabled(!f);
			empresa.setEnabled(f);
			funcionario.setEnabled(f);
			catraca.setEnabled(f);
			ar.setEnabled(f);
			sair.setEnabled(!f);
			idioma.setEnabled(!f);
		} else if (type == 2) {
			arquivo.setEnabled(!f);
			empresa.setEnabled(!f);
			funcionario.setEnabled(f);
			catraca.setEnabled(f);
			ar.setEnabled(f);
			sair.setEnabled(!f);
			idioma.setEnabled(!f);
		} else if (type == 3 || type == 4) {
			arquivo.setEnabled(true);
			empresa.setEnabled(true);
			funcionario.setEnabled(true);
			catraca.setEnabled(true);
			ar.setEnabled(true);
			sair.setEnabled(true);
			idioma.setEnabled(true);
		}

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date hoje = new Date();
		
		JPanel inicio = new JPanel();
		inicio.setLayout(new FlowLayout());
		mensagemLb = new JLabel(dateFormat.format(hoje));
		mensagemLb.setFont(new Font("Serif", Font.BOLD, 25));
		inicio.add(mensagemLb);
		setContentPane(inicio);
		
	}

	public void trocarIdioma(ResourceBundle bn) {
		arquivo.setText(bn.getString("telaPrincipal.menu.arquivo"));
		empresa.setText(bn.getString("telaPrincipal.menu.empresa"));
		funcionario.setText(bn.getString("telaPrincipal.menu.funcionario"));
		catraca.setText(bn.getString("telaPrincipal.menu.catraca"));
		ar.setText(bn.getString("telaPrincipal.menu.ac"));
		sair.setText(bn.getString("telaPrincipal.menu.sair"));
		idioma.setText(bn.getString("telaPrincipal.menu.idioma"));
		port.setText(bn.getString("telaPrincipal.menuitem.pt"));
		engl.setText(bn.getString("telaPrincipal.menuitem.en"));
		esp.setText(bn.getString("telaPrincipal.menuitem.es"));
		cadFuncionario.setText(bn
				.getString("telaPrincipal.menuitemF.cadastrar"));
		excFuncionario.setText(bn.getString("telaPrincipal.menuitemF.excluir"));
		conFuncionario.setText(bn
				.getString("telaPrincipal.menuitemF.consultar"));
		altFuncionario.setText(bn.getString("telaPrincipal.menuitemF.alterar"));
		cadEmpresa.setText(bn.getString("telaPrincipal.menuitemE.cadastrar"));
		excEmpresa.setText(bn.getString("telaPrincipal.menuitemE.excluir"));
		conEmpresa.setText(bn.getString("telaPrincipal.menuitemE.consultar"));
		altEmpresa.setText(bn.getString("telaPrincipal.menuitemE.alterar"));
		sairIM.setText(bn.getString("telaPrincipal.menuitemE.sair"));
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	private class ItemHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JFrame frame = new JFrame();
			UsuarioCtrl userCtrl = new UsuarioCtrl();
			EmpresaCtrl empCtrl = new EmpresaCtrl();
			ConjuntoCtrl conjCtrl = new ConjuntoCtrl();
			Empresa aux = new Empresa();

			if (event.getSource() == port) {
				if (tela != null) {
					bn = ResourceBundle.getBundle("tela",
							new Locale("pt", "BR"));
					if (TelaConfirm.confirmacao(bn, frame, 5)) {
						if (modo == 1) {
							getContentPane().removeAll();
							TelaUsuario userCad = new TelaUsuario(bn, modo);
							tela = userCad.criarTela();
						} else {
							int conta = userCtrl.consultarUsuario(idUser);
							getContentPane().removeAll();
							TelaUsuario userCad = new TelaUsuario(bn, modo);
							tela = userCad.criarTela();
							if (conta == 0) {
								userCad.setCampos(userCtrl.getUsuario());
							} else {
								userCad.setCampos(userCtrl.getFuncionario());
							}
						}
						getContentPane().add(tela);
						revalidate();
						repaint();
					}
				} else {
					bn = ResourceBundle.getBundle("tela",
							new Locale("pt", "BR"));
				}
				port.setEnabled(false);
				engl.setEnabled(true);
				esp.setEnabled(true);
				repaint();
				trocarIdioma(bn);
			} else if (event.getSource() == engl) {
				if (tela != null) {
					if (TelaConfirm.confirmacao(bn, frame, 5)) {
						bn = ResourceBundle.getBundle("tela", Locale.US);
						if (modo == 1) {
							getContentPane().removeAll();
							TelaUsuario userCad = new TelaUsuario(bn, 1);
							tela = userCad.criarTela();
						} else {
							int conta = userCtrl.consultarUsuario(idUser);
							getContentPane().removeAll();
							TelaUsuario userCad = new TelaUsuario(bn, modo);
							tela = userCad.criarTela();
							if (conta == 0) {
								userCad.setCampos(userCtrl.getUsuario());
							} else {
								userCad.setCampos(userCtrl.getFuncionario());
							}
						}
						getContentPane().add(tela);
						revalidate();
						repaint();
					}
				} else {
					bn = ResourceBundle.getBundle("tela", Locale.US);
				}
				port.setEnabled(true);
				engl.setEnabled(false);
				esp.setEnabled(true);
				repaint();
				trocarIdioma(bn);
			} else if (event.getSource() == esp) {
				if (tela != null) {
					if (TelaConfirm.confirmacao(bn, frame, 5)) {
						bn = ResourceBundle.getBundle("tela", new Locale("es",
								"ES"));
						if (modo == 1) {
							getContentPane().removeAll();
							TelaUsuario userCad = new TelaUsuario(bn, modo);
							tela = userCad.criarTela();
						} else if(modo > 1 && modo < 5){
							int conta = userCtrl.consultarUsuario(idUser);
							getContentPane().removeAll();
							TelaUsuario userCad = new TelaUsuario(bn, modo);
							tela = userCad.criarTela();
							if (conta == 0) {
								userCad.setCampos(userCtrl.getUsuario());
							} else {
								userCad.setCampos(userCtrl.getFuncionario());
							}
						} else if(modo == 5){
								getContentPane().removeAll();
								TelaEmpresa telaEmpresa = new TelaEmpresa(bn, modo);
								tela = telaEmpresa.criarTela();
						} else if(modo > 5 && modo < 9){
							// int conta = empCtrl.consultarEmpresa(idEmp);
							getContentPane().removeAll();
							TelaEmpresa telaEmpresa = new TelaEmpresa(bn, modo);
							tela = telaEmpresa.criarTela();
							telaEmpresa.setCampos(empCtrl.getEmp());
						}
						getContentPane().add(tela);
						revalidate();
						repaint();
					}
				} else {
					bn = ResourceBundle.getBundle("tela",
							new Locale("es", "ES"));
				}
				port.setEnabled(true);
				engl.setEnabled(true);
				esp.setEnabled(false);
				repaint();
				trocarIdioma(bn);
			}

			if (event.getSource() == sairIM) {
				if (TelaConfirm.confirmacao(bn, frame, 4)) {
					System.exit(0);
				}
			}

			// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
			// Empresa (CRUD)
			// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
			// * CADASTRO DE EMPRESA *
			if (event.getSource() == cadEmpresa) {
				modo = 5;
				getContentPane().removeAll();
				if (tela == null) {
					TelaEmpresa telaEmpresa = new TelaEmpresa(bn, 5);
					tela = telaEmpresa.criarTela();
					getContentPane().add(tela);
					revalidate();
					repaint();
				} else if (TelaConfirm.confirmacao(bn, frame, 1)) {
					TelaEmpresa telaEmpresa = new TelaEmpresa(bn, 5);
					tela = telaEmpresa.criarTela();
					getContentPane().add(tela);
					revalidate();
					repaint();
				}
			} // fim Cadastro Empresa
				// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
				// * ALTERAÇÃO DE USUÁRIO *
			else if (event.getSource() == altEmpresa) {
				modo = 6;
				if (tela == null) {
					int id = TelaBusca.busca(bn, frame, 2);
					if (id > 0) {
						int conta = empCtrl.consultarEmpresa(id);
						if (conta == 0) {
							TelaMensagem.confirmacao(bn, frame, 8);
						} else {
							getContentPane().removeAll();
							TelaEmpresa telaEmpresa = new TelaEmpresa(bn, 6);
							tela = telaEmpresa.criarTela();
							telaEmpresa.setCampos(empCtrl.getEmp());
							aux = empCtrl.getEmp();
							conjCtrl.consultarConjunto(id);
							aux.setConjuntos(conjCtrl.getConjuntos());
							telaEmpresa.setCampos(aux);
							getContentPane().add(tela);
							idEmp = telaEmpresa.getIdEmpresaTl();
							revalidate();
							repaint();
						}
					}
				} else if (TelaConfirm.confirmacao(bn, frame, 1)) {
					int id = TelaBusca.busca(bn, frame, 2);
					if (id > 0) {
						int conta = empCtrl.consultarEmpresa(id);
						if (conta == 0) {
							TelaMensagem.confirmacao(bn, frame, 8);
						} else {
							getContentPane().removeAll();
							TelaEmpresa telaEmpresa = new TelaEmpresa(bn, 6);
							tela = telaEmpresa.criarTela();
							aux = empCtrl.getEmp();
							conjCtrl.consultarConjunto(id);
							aux.setConjuntos(conjCtrl.getConjuntos());
							telaEmpresa.setCampos(aux);
							getContentPane().add(tela);
							idEmp = telaEmpresa.getIdEmpresaTl();
							revalidate();
							repaint();
						}
					}
				}
			} // fim Alterar Empresa
				// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
				// * CONSULTA DE EMPRESA *
			else if (event.getSource() == conEmpresa) {
				modo = 7;
				if (tela == null) {
					int id = TelaBusca.busca(bn, frame, 2);
					if (id > 0) {
						int conta = empCtrl.consultarEmpresa(id);
						if (conta == 0) {
							TelaMensagem.confirmacao(bn, frame, 8);
						} else {
							getContentPane().removeAll();
							TelaEmpresa telaEmpresa = new TelaEmpresa(bn, 7);
							tela = telaEmpresa.criarTela();
							telaEmpresa.setCampos(empCtrl.getEmp());
							aux = empCtrl.getEmp();
							conjCtrl.consultarConjunto(id);
							aux.setConjuntos(conjCtrl.getConjuntos());
							telaEmpresa.setCampos(aux);
							getContentPane().add(tela);
							idEmp = telaEmpresa.getIdEmpresaTl();
							revalidate();
							repaint();
						}
					}
				} else if (TelaConfirm.confirmacao(bn, frame, 1)) {
					int id = TelaBusca.busca(bn, frame, 2);
					if (id > 0) {
						int conta = empCtrl.consultarEmpresa(id);
						if (conta == 0) {
							TelaMensagem.confirmacao(bn, frame, 8);
						} else {
							getContentPane().removeAll();
							TelaEmpresa telaEmpresa = new TelaEmpresa(bn, 7);
							tela = telaEmpresa.criarTela();
							aux = empCtrl.getEmp();
							conjCtrl.consultarConjunto(id);
							aux.setConjuntos(conjCtrl.getConjuntos());
							telaEmpresa.setCampos(aux);
							getContentPane().add(tela);
							idEmp = telaEmpresa.getIdEmpresaTl();
							revalidate();
							repaint();
						}
					}
				}
			} // fim Consultar Empresa
				// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
				// * REMOÇÃO DE EMPRESA *
			else if (event.getSource() == excEmpresa) {
				modo = 8;
				if (tela == null) {
					int id = TelaBusca.busca(bn, frame, 2);
					if (id > 0) {
						int conta = empCtrl.consultarEmpresa(id);
						if (conta == 0) {
							TelaMensagem.confirmacao(bn, frame, 8);
						} else {
							getContentPane().removeAll();
							TelaEmpresa telaEmpresa = new TelaEmpresa(bn, 8);
							tela = telaEmpresa.criarTela();
							telaEmpresa.setCampos(empCtrl.getEmp());
							aux = empCtrl.getEmp();
							conjCtrl.consultarConjunto(id);
							aux.setConjuntos(conjCtrl.getConjuntos());
							telaEmpresa.setCampos(aux);
							getContentPane().add(tela);
							idEmp = telaEmpresa.getIdEmpresaTl();
							revalidate();
							repaint();
						}
					}
				} else if (TelaConfirm.confirmacao(bn, frame, 1)) {
					int id = TelaBusca.busca(bn, frame, 2);
					if (id > 0) {
						int conta = empCtrl.consultarEmpresa(id);
						if (conta == 0) {
							TelaMensagem.confirmacao(bn, frame, 8);
						} else {
							getContentPane().removeAll();
							TelaEmpresa telaEmpresa = new TelaEmpresa(bn, 8);
							tela = telaEmpresa.criarTela();
							aux = empCtrl.getEmp();
							conjCtrl.consultarConjunto(id);
							aux.setConjuntos(conjCtrl.getConjuntos());
							telaEmpresa.setCampos(aux);
							getContentPane().add(tela);
							idEmp = telaEmpresa.getIdEmpresaTl();
							revalidate();
							repaint();
						}
					}
				}
			} // fim Remover Empresa

			// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
			// Usuário (CRUD)
			// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
			// * CADASTRO DE USUÁRIO *
			else if (event.getSource() == cadFuncionario) {
				modo = 1;
				getContentPane().removeAll();
				if (tela == null) {
					TelaUsuario userCad = new TelaUsuario(bn, 1);
					tela = userCad.criarTela();
					getContentPane().add(tela);
					revalidate();
					repaint();
				} else if (TelaConfirm.confirmacao(bn, frame, 1)) {
					TelaUsuario userCad = new TelaUsuario(bn, 1);
					tela = userCad.criarTela();
					getContentPane().add(tela);
					revalidate();
					repaint();
				}
			} // fim cadastro
				// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
				// * ALTERAÇÃO DE USUÁRIO *
			else if (event.getSource() == altFuncionario) {
				modo = 2;
				if (tela == null) {
					int id = TelaBusca.busca(bn, frame, 1);
					if (id > 0) {
						int conta = userCtrl.consultarUsuario(id);
						getContentPane().removeAll();
						TelaUsuario userCad = new TelaUsuario(bn, 2);
						tela = userCad.criarTela();
						if (conta == 0) {
							userCad.setCampos(userCtrl.getUsuario());
						} else {
							userCad.setCampos(userCtrl.getFuncionario());
						}
						getContentPane().add(tela);
						idUser = userCad.getIdUsuarioTl();
						revalidate();
						repaint();
					}
				} else if (TelaConfirm.confirmacao(bn, frame, 1)) {
					int id = TelaBusca.busca(bn, frame, 1);
					if (id > 0) {
						int conta = userCtrl.consultarUsuario(id);
						getContentPane().removeAll();
						TelaUsuario userCad = new TelaUsuario(bn, 2);
						tela = userCad.criarTela();
						if (conta == 0) {
							userCad.setCampos(userCtrl.getUsuario());
						} else {
							userCad.setCampos(userCtrl.getFuncionario());
						}
						getContentPane().add(tela);
						idUser = userCad.getIdUsuarioTl();
						revalidate();
						repaint();
					}
				}
			} // fim alteração
				// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
				// * CONSULTA DE USUÁRIO *
			else if (event.getSource() == conFuncionario) {
				modo = 3;
				if (tela == null) {
					int id = TelaBusca.busca(bn, frame, 1);
					if (id > 0) {
						int conta = userCtrl.consultarUsuario(id);
						if (conta == 3) {
							TelaMensagem.confirmacao(bn, frame, 1);
						} else {
							getContentPane().removeAll();
							TelaUsuario userCad = new TelaUsuario(bn, 3);
							tela = userCad.criarTela();
							if (conta == 0) {
								userCad.setCampos(userCtrl.getUsuario());
							} else {
								userCad.setCampos(userCtrl.getFuncionario());
							}
							getContentPane().add(tela);
							idUser = userCad.getIdUsuarioTl();
							revalidate();
							repaint();
						}
					}
				} else if (TelaConfirm.confirmacao(bn, frame, 1)) {
					int id = TelaBusca.busca(bn, frame, 1);
					if (id > 0) {
						int conta = userCtrl.consultarUsuario(id);
						if (conta == 3) {
							TelaMensagem.confirmacao(bn, frame, 1);
						} else {
							getContentPane().removeAll();
							TelaUsuario userCad = new TelaUsuario(bn, 3);
							tela = userCad.criarTela();
							if (conta == 0) {
								userCad.setCampos(userCtrl.getUsuario());
							} else {
								userCad.setCampos(userCtrl.getFuncionario());
							}
							getContentPane().add(tela);
							idUser = userCad.getIdUsuarioTl();
							revalidate();
							repaint();
						}
					}
				}
			} // fim consulta
				// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
				// * REMOÇÃO DE USUÁRIO *
			else if (event.getSource() == excFuncionario) {
				modo = 4;
				if (tela == null) {
					int id = TelaBusca.busca(bn, frame, 1);
					if (id > 0) {
						int conta = userCtrl.consultarUsuario(id);
						getContentPane().removeAll();
						TelaUsuario userCad = new TelaUsuario(bn, 4);
						tela = userCad.criarTela();
						if (conta == 0) {
							userCad.setCampos(userCtrl.getUsuario());
						} else {
							userCad.setCampos(userCtrl.getFuncionario());
						}
						getContentPane().add(tela);
						idUser = userCad.getIdUsuarioTl();
						revalidate();
						repaint();
					}
				} else if (TelaConfirm.confirmacao(bn, frame, 1)) {
					int id = TelaBusca.busca(bn, frame, 1);
					if (id > 0) {
						int conta = userCtrl.consultarUsuario(id);
						getContentPane().removeAll();
						TelaUsuario userCad = new TelaUsuario(bn, 4);
						tela = userCad.criarTela();
						if (conta == 0) {
							userCad.setCampos(userCtrl.getUsuario());
						} else {
							userCad.setCampos(userCtrl.getFuncionario());
						}
						getContentPane().add(tela);
						idUser = userCad.getIdUsuarioTl();
						revalidate();
						repaint();
					}
				}
			} // fim remoção
				// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		}
	}
}
