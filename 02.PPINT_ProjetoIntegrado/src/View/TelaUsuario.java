package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import Controller.UsuarioCtrl;
import Model.Funcionario;
import Model.Usuario;

public class TelaUsuario extends JFrame implements ActionListener {
	private JLabel tituloLB, idLB, cpfLB, nomeLB, loginLB, senhaLB, dataNascimentoLB, sexoLB, enderecoLB, cepLB,
			telefoneLB, tipoContaLB, tipoAcessoLB, idEmpresaLB, horEntradaLB, horSaidaLB;
	private JPasswordField senhaPF;
	private JTextField nomeTF, loginTF, enderecoTF, idEmpresaTF;
	private MaskFormatter cpfMF, dataNascMF, cepMF, telefoneMF;
	private JFormattedTextField cpfJFT, dataNascJFT, cepJFT, telefoneJFT;
	private JButton cadastrarBT, cancelarBT;
	private ResourceBundle bn = null;
	private JComboBox contaJCB, acessoJCB, entradaJCB, saidaJCB, sexoJCB;
	private String[] conta, acesso, sexo = { "M", "F" }, horario, dia, mes, ano;
	private JPanel pnFinal, pnDadosPessoais, pnDadosEmpresa, pnDadosLogin, pnTela;
	private TitledBorder bdTitleDP, bdTitleDE, bdTitleDL;
	private int modo;
	private Usuario user;
	private Funcionario func;
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;

	public TelaUsuario(ResourceBundle bn, int modo) {
		this.bn = bn;
		this.modo = modo;

	}

	public JPanel criarTela() {

		// instanciação do MaskFormatter
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		try {
			cpfMF = new MaskFormatter("###.###.###-##");
			dataNascMF = new MaskFormatter("##/##/####");
			cepMF = new MaskFormatter("#####-###");
			telefoneMF = new MaskFormatter("(##)####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cpfMF.setPlaceholderCharacter('_');
		dataNascMF.setPlaceholderCharacter('_');
		cepMF.setPlaceholderCharacter('_');
		telefoneMF.setPlaceholderCharacter('_');

		// Preenchimento do vetor de horas
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		horario = new String[48];
		int hora = 0;
		for (int i = 0; i < horario.length; i++) {
			if (i % 2 == 0) {
				if ((hora - 10) <= 0) {
					horario[i] = "0" + hora + ":00:00";
				} else {
					horario[i] = hora + ":00:00";
				}
			} else {
				if ((hora - 10) <= 0) {
					horario[i] = "0" + hora + ":30:00";
					hora++;
				} else {
					horario[i] = hora + ":30:00";
					hora++;
				}

			}
		}

		String[] contaAux = { bn.getString("telaUsuario.lista.tipoConta1"),
				bn.getString("telaUsuario.lista.tipoConta2"), bn.getString("telaUsuario.lista.tipoConta3"),
				bn.getString("telaUsuario.lista.tipoConta4") };
		conta = contaAux;

		String[] acessoAux = { bn.getString("telaUsuario.lista.tipoAcesso1"),
				bn.getString("telaUsuario.lista.tipoAcesso2") };
		acesso = acessoAux;

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// JPanel de Dados Pessoais
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		pnDadosPessoais = new JPanel(new GridLayout(3, 1, 5, 5));

		nomeLB = new JLabel(bn.getString("telaUsuario.label.nome"));
		nomeTF = new JTextField(45);

		cpfLB = new JLabel("CPF*");
		cpfJFT = new JFormattedTextField(cpfMF);

		dataNascimentoLB = new JLabel(bn.getString("telaUsuario.label.dataNascimento"));
		dataNascJFT = new JFormattedTextField(dataNascMF);

		sexoLB = new JLabel(bn.getString("telaUsuario.label.sexo"));
		sexoJCB = new JComboBox(sexo);

		enderecoLB = new JLabel(bn.getString("telaUsuario.label.endereco"));
		enderecoTF = new JTextField(60);

		cepLB = new JLabel(bn.getString("telaUsuario.label.cep"));
		cepJFT = new JFormattedTextField(cepMF);

		telefoneLB = new JLabel(bn.getString("telaUsuario.label.telefone"));
		telefoneJFT = new JFormattedTextField(telefoneMF);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Painel = P(componentes)
		// JPanel P(Nome)
		JPanel pnDPNome = new JPanel(new GridLayout(2, 1, 5, 5));
		pnDPNome.add(nomeLB);
		pnDPNome.add(nomeTF);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// JPanel P(CPF, DataNascimento, Sexo)
		JPanel pnDPCSN = new JPanel(new GridLayout(2, 3, 5, 5));
		pnDPCSN.add(cpfLB);
		pnDPCSN.add(dataNascimentoLB);
		pnDPCSN.add(sexoLB);
		pnDPCSN.add(cpfJFT);
		pnDPCSN.add(dataNascJFT);
		pnDPCSN.add(sexoJCB);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// JPanel P(Endereço)
		JPanel pnDPE = new JPanel(new GridLayout(2, 1, 5, 5));
		pnDPE.add(enderecoLB);
		pnDPE.add(enderecoTF);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// JPanel P(CEP, Telefone)
		JPanel pnDPCT = new JPanel(new GridLayout(2, 2, 5, 5));
		pnDPCT.add(cepLB);
		pnDPCT.add(telefoneLB);
		pnDPCT.add(cepJFT);
		pnDPCT.add(telefoneJFT);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// JPanel P(Endereço) + P(CEP, Telefone)
		JPanel pnDPECT = new JPanel(new GridLayout(1, 2, 5, 5));
		pnDPECT.add(pnDPE);
		pnDPECT.add(pnDPCT);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// JPanel P(nome) + P(CPF, DNasc, Sexo) + P(Endereço, CEP, Telefone)
		pnDadosPessoais.add(pnDPNome);
		pnDadosPessoais.add(pnDPCSN);
		pnDadosPessoais.add(pnDPECT);

		bdTitleDP = new TitledBorder(new EtchedBorder(), bn.getString("telaUsuario.bordertitle.usuario"));
		pnDadosPessoais.setBorder(bdTitleDP);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// JPanel Cadastro de Login
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		loginLB = new JLabel("Login*");
		loginTF = new JTextField(45);

		senhaLB = new JLabel(bn.getString("telaUsuario.label.senha"));
		senhaPF = new JPasswordField(10);

		pnDadosLogin = new JPanel(new GridLayout(2, 2, 5, 5));
		pnDadosLogin.add(loginLB);
		pnDadosLogin.add(senhaLB);
		pnDadosLogin.add(loginTF);
		pnDadosLogin.add(senhaPF);

		bdTitleDL = new TitledBorder(new EtchedBorder(), "Dados do Login");
		pnDadosLogin.setBorder(bdTitleDL);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// JPanel Dados da Empresa
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		idEmpresaLB = new JLabel(bn.getString("telaUsuario.label.idEmpresa") + "*");
		idEmpresaTF = new JTextField();

		horEntradaLB = new JLabel(bn.getString("telaUsuario.label.horEntrada") + "*");
		entradaJCB = new JComboBox(horario);
		entradaJCB.setSelectedIndex(16);
		entradaJCB.setMaximumRowCount(5);

		horSaidaLB = new JLabel(bn.getString("telaUsuario.label.horSaida") + "*");
		saidaJCB = new JComboBox(horario);
		saidaJCB.setSelectedIndex(34);
		saidaJCB.setMaximumRowCount(5);

		tipoAcessoLB = new JLabel(bn.getString("telaUsuario.label.tipoAcesso"));
		acessoJCB = new JComboBox(acesso);
		acessoJCB.setSelectedIndex(1);
		acessoJCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					boolean edit;
					if (acessoJCB.getSelectedIndex() == 0) {
						edit = false;
						horEntradaLB.setText(bn.getString("telaUsuario.label.horSaida"));
						horSaidaLB.setText(bn.getString("telaUsuario.label.horSaida"));
					} else {
						edit = true;
						horEntradaLB.setText(bn.getString("telaUsuario.label.horSaida") + "*");
						horSaidaLB.setText(bn.getString("telaUsuario.label.horSaida") + "*");
					}
					entradaJCB.setEnabled(edit);
					saidaJCB.setEnabled(edit);
				} else if (event.getStateChange() == ItemEvent.DESELECTED) {

				}
			}
		});

		tipoContaLB = new JLabel(bn.getString("telaUsuario.label.tipoConta"));
		contaJCB = new JComboBox(conta);
		contaJCB.setSelectedIndex(0);
		contaJCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				boolean edit = true;
				if (event.getStateChange() == ItemEvent.SELECTED) {
					if (contaJCB.getSelectedIndex() < 2) {
						acessoJCB.setSelectedIndex(1);
						acessoJCB.setEnabled(edit);
						acessoJCB.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent event) {
								boolean edit = true;
								if (event.getStateChange() == ItemEvent.SELECTED) {
									if (acessoJCB.getSelectedIndex() == 1) {
										edit = true;
										horEntradaLB.setText(bn.getString("telaUsuario.label.horSaida") + "*");
										horSaidaLB.setText(bn.getString("telaUsuario.label.horSaida") + "*");
									} else {
										edit = false;
										horEntradaLB.setText(bn.getString("telaUsuario.label.horSaida"));
										horSaidaLB.setText(bn.getString("telaUsuario.label.horSaida"));
									}
									entradaJCB.setEnabled(edit);
									saidaJCB.setEnabled(edit);
								} else if (event.getStateChange() == ItemEvent.DESELECTED) {
									entradaJCB.setEnabled(edit);
									saidaJCB.setEnabled(edit);
								}
							}
						});
						idEmpresaLB.setText(bn.getString("telaUsuario.label.idEmpresa") + "*");
					} else {
						acessoJCB.setSelectedIndex(0);
						edit = false;
						acessoJCB.setEnabled(edit);
						entradaJCB.setEnabled(edit);
						saidaJCB.setEnabled(edit);
						idEmpresaLB.setText(bn.getString("telaUsuario.label.idEmpresa"));
					}
					idEmpresaTF.setEditable(edit);
					idEmpresaTF.setText("");
				} else if (event.getStateChange() == ItemEvent.DESELECTED) {
					idEmpresaTF.setEditable(edit);
					idEmpresaTF.setText("");
				}
			}
		});

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

		JPanel pnDETipos = new JPanel(new GridLayout(2, 4, 5, 5));
		pnDETipos.add(tipoContaLB);
		pnDETipos.add(new JLabel(""));
		pnDETipos.add(tipoAcessoLB);
		pnDETipos.add(new JLabel(""));

		pnDETipos.add(contaJCB);
		pnDETipos.add(new JLabel(""));
		pnDETipos.add(acessoJCB);
		pnDETipos.add(new JLabel(""));

		JPanel pnDECnpj = new JPanel(new GridLayout(2, 1, 5, 5));
		pnDECnpj.add(idEmpresaLB);
		pnDECnpj.add(idEmpresaTF);

		JPanel pnDEES = new JPanel(new GridLayout(2, 2, 5, 5));
		pnDEES.add(horEntradaLB);
		pnDEES.add(horSaidaLB);
		pnDEES.add(entradaJCB);
		pnDEES.add(saidaJCB);

		JPanel pnDECES = new JPanel(new GridLayout(1, 2, 5, 5));
		pnDECES.add(pnDECnpj);
		pnDECES.add(pnDEES);

		pnDadosEmpresa = new JPanel(new GridLayout(3, 1, 5, 5));
		pnDadosEmpresa.add(pnDETipos);
		pnDadosEmpresa.add(pnDECES);
		pnDadosEmpresa.add(pnDadosLogin);

		bdTitleDE = new TitledBorder(new EtchedBorder(), bn.getString("telaUsuario.bordertitle.empresa"));
		pnDadosEmpresa.setBorder(bdTitleDE);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// JPanel Finais
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		boolean f = true;
		if (modo == 1) {
			cadastrarBT = new JButton(bn.getString("telaUsuario.button.cadastrar"));
			f = true;
		} else if (modo == 2) {
			cadastrarBT = new JButton(bn.getString("telaUsuario.button.alterar"));
			f = true;
		} else if (modo == 3) {
			cadastrarBT = new JButton();
			f = false;
		} else {
			cadastrarBT = new JButton(bn.getString("telaUsuario.button.remover"));
			f = false;
		}
		cpfJFT.setEditable(f);
		nomeTF.setEditable(f);
		dataNascJFT.setEditable(f);
		sexoJCB.setEnabled(f);
		enderecoTF.setEditable(f);
		cepJFT.setEditable(f);
		telefoneJFT.setEditable(f);
		contaJCB.setEnabled(f);
		acessoJCB.setEnabled(f);
		idEmpresaTF.setEditable(f);
		entradaJCB.setEnabled(f);
		saidaJCB.setEnabled(f);
		loginTF.setEditable(f);
		senhaPF.setEditable(f);

		cadastrarBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				UsuarioCtrl userCtrl = new UsuarioCtrl();
				JFrame frame = new JFrame();
				user = new Usuario();
				func = new Funcionario();
				if (modo == 1) {
					if (setAtributos()) {
						if (userCtrl.cadastrarUsuario(user) == null) {
							TelaMensagem.confirmacao(bn, frame, 6);
						} else {
							if (contaJCB.getSelectedIndex() == 0 || contaJCB.getSelectedIndex() == 1) {
								func.setIdEmpresaFK(Integer.parseInt(idEmpresaTF.getText()));
								if (acessoJCB.getSelectedIndex() == 0) {
									func.setHorarioEntrada("00:00:00");
									func.setHorarioSaida("00:00:00");
								} else {
									func.setHorarioEntrada(entradaJCB.getSelectedItem().toString());
									func.setHorarioSaida(saidaJCB.getSelectedItem().toString());
								}
								func.setUsuarioAtributos(user);
								userCtrl.cadastrarUsuario(func);
							}
							char[] senhaChar = senhaPF.getPassword();
							String senhaString = "";
							for (int i = 0; i < senhaChar.length; i++) {
								senhaString += senhaChar[i];
							}
							TelaMensagem.confirmacao(bn, frame, 7);
							cadastrarBT.setEnabled(false);
						}
					} else {
						TelaMensagem.confirmacao(bn, frame, 4);
					}

				} else if (modo == 2) {
					if (setAtributos()) {
						String[] ids = idLB.getText().split(" ");
						int conta = userCtrl.consultarUsuario(Integer.parseInt(ids[1]));
						user.setIdUsuario((Integer.parseInt(ids[1])));
						userCtrl.alterarUsuario(user);
						if (contaJCB.getSelectedIndex() == 0 || contaJCB.getSelectedIndex() == 1) {
							func.setIdEmpresaFK(Integer.parseInt(idEmpresaTF.getText()));
							if (acessoJCB.getSelectedIndex() == 0) {
								func.setHorarioEntrada("00:00:00");
								func.setHorarioSaida("00:00:00");
							} else {
								func.setHorarioEntrada(entradaJCB.getSelectedItem().toString());
								func.setHorarioSaida(saidaJCB.getSelectedItem().toString());
							}
							func.setUsuarioAtributos(user);
							if (conta == 0) {
								userCtrl.cadastrarUsuario(func);
							} else {
								userCtrl.alterarUsuario(func);
							}
							TelaMensagem.confirmacao(bn, frame, 7);
							cadastrarBT.setEnabled(false);
						} else {
							if (conta == 1) {

							}
						}
					}
				} else if (modo == 3) {

				} else {
					if (TelaConfirm.confirmacao(bn, frame, 3)) {
						String[] idLabel = idLB.getText().split(" ");
						int id = Integer.parseInt(idLabel[idLabel.length - 1]);
						userCtrl.removerUsuario(id);
						if (user.getExist() == 1) {
							TelaMensagem.confirmacao(bn, frame, 1);
						} else if (user.getExist() == 0) {
							TelaMensagem.confirmacao(bn, frame, 2);
							cadastrarBT.setEnabled(false);
						}
					}
				}
			}
		});

		pnTela = new JPanel(new GridLayout(2, 1, 5, 5));
		pnTela.add(pnDadosPessoais);
		pnTela.add(pnDadosEmpresa);

		pnFinal = new JPanel(new BorderLayout(10, 10));
		pnFinal.add(pnTela, BorderLayout.CENTER);

		idLB = new JLabel("ID: ");
		if (modo == 1) {
			tituloLB = new JLabel(bn.getString("telaUsuario.label.titulo1"), SwingConstants.CENTER);
		} else if (modo == 2) {
			tituloLB = new JLabel(bn.getString("telaUsuario.label.titulo2"), SwingConstants.CENTER);
		} else if (modo == 3) {
			tituloLB = new JLabel(bn.getString("telaUsuario.label.titulo3"), SwingConstants.CENTER);
		} else {
			tituloLB = new JLabel(bn.getString("telaUsuario.label.titulo4"), SwingConstants.CENTER);
		}
		tituloLB.setFont(new Font("Titulo", Font.BOLD, 20));

		JPanel pnTitulo = new JPanel(new GridLayout(2, 1));
		pnTitulo.add(tituloLB);
		pnTitulo.add(idLB);

		pnFinal.add(pnTitulo, BorderLayout.NORTH);

		if (modo == 3) {

		} else {
			JPanel pnButton = new JPanel(new GridLayout(1, 3, 5, 5));
			pnButton.add(new JLabel(""));
			pnButton.add(cadastrarBT);
			pnButton.add(new JLabel(""));
			pnFinal.add(pnButton, BorderLayout.SOUTH);
		}

		pnFinal.setPreferredSize(new Dimension(640, 480));
		pnFinal.setMinimumSize(new Dimension(640, 480));
		return pnFinal;

	}

	public boolean setAtributos() {
		boolean ok = true;
		JFrame frame = new JFrame();
		if (cpfJFT.getText().equals("___.___.___-__")) {
			ok = false;
		} else {
			user.setCpf(cpfJFT.getText());
		}

		if (nomeTF == null || nomeTF.equals("") || ok == false) {
			ok = false;
		} else {
			user.setNome(nomeTF.getText());
		}

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date hoje = Calendar.getInstance().getTime();
		String hojeString = dateFormat.format(hoje);

		String[] dmaTela = dataNascJFT.getText().split("/");
		int diaTela = Integer.parseInt(dmaTela[0]);
		int mesTela = Integer.parseInt(dmaTela[1]);
		int anoTela = Integer.parseInt(dmaTela[2]);

		String[] dmaHoje = hojeString.split("/");
		int diaHoje = Integer.parseInt(dmaHoje[0]);
		int mesHoje = Integer.parseInt(dmaHoje[1]);
		int anoHoje = Integer.parseInt(dmaHoje[2]);

		if (diaTela > 31 || mesTela > 12 || anoTela > anoHoje
				|| (diaTela >= diaHoje && mesTela >= mesHoje && anoTela >= anoHoje)
				|| dataNascJFT.getText().equals("__/__/____") || ok == false) {
			TelaMensagem.confirmacao(bn, frame, 5);
			ok = false;
		} else if (mesTela == 2 && diaTela > 29) {
			TelaMensagem.confirmacao(bn, frame, 5);
			ok = false;
		} else if ((mesTela == 4 || mesTela == 6 || mesTela == 9 || mesTela == 11) && diaTela > 30) {
			TelaMensagem.confirmacao(bn, frame, 5);
			ok = false;
		} else {
			user.setDataNascimento(dataNascJFT.getText());
		}

		if (enderecoTF == null || enderecoTF.equals("") || ok == false) {
			ok = false;
		} else {
			user.setEndereco(enderecoTF.getText());
		}

		if (cepJFT.getText().equals("_____-___") || ok == false) {
			ok = false;
		} else {
			user.setCep(cepJFT.getText());
		}

		if (telefoneJFT.getText().equals("(__)____-____") || ok == false) {
			ok = false;
		} else {
			user.setTelefone(telefoneJFT.getText());
		}

		if (loginTF == null || loginTF.equals("") || ok == false) {
			ok = false;
		} else {
			user.setLogin(loginTF.getText());
		}

		if (senhaPF.getPassword() == null || senhaPF.getPassword().length > 10) {
			ok = false;
		} else {
			String password = "";
			for (int i = 0; i < senhaPF.getPassword().length; i++) {
				password += senhaPF.getPassword()[i];
			}
			user.setSenha(password);
		}

		if (sexoJCB.getSelectedItem().equals("M")) {
			user.setSexo("M");
		} else if (sexoJCB.getSelectedItem().equals("F")) {
			user.setSexo("F");
		}

		// Tipo Acesso 1: Livre 2: Restrito
		user.setTipoAcesso(acessoJCB.getSelectedIndex() + 1);

		// Tipo Conta 1: Funcionário 2: Funcionário CAC 3: Atendente 4:
		// Síndico
		user.setTipoConta(contaJCB.getSelectedIndex() + 1);

		return ok;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public void setCampos(Usuario userCampos) {
		idLB.setText("ID: " + userCampos.getIdUsuario());
		cpfJFT.setText(userCampos.getCpf());
		nomeTF.setText(userCampos.getNome());

		String[] date = userCampos.getDataNascimento().split("-");
		dataNascJFT.setText(date[2] + "/" + date[1] + "/" + date[0]);

		if (userCampos.getSexo().equals("M")) {
			sexoJCB.setSelectedIndex(0);
		} else {
			sexoJCB.setSelectedIndex(1);
		}
		enderecoTF.setText(userCampos.getEndereco());
		cepJFT.setText(userCampos.getCep());
		telefoneJFT.setText(userCampos.getTelefone());

		contaJCB.setSelectedIndex(userCampos.getTipoConta() - 1);

		acessoJCB.setSelectedIndex(0);

		entradaJCB.setSelectedIndex(0);
		saidaJCB.setSelectedIndex(0);

		loginTF.setText(userCampos.getLogin());
	}

	public void setCampos(Funcionario funcCampos) {
		idLB.setText("ID: " + funcCampos.getIdUsuario());
		cpfJFT.setText(funcCampos.getCpf());
		nomeTF.setText(funcCampos.getNome());

		String[] date = funcCampos.getDataNascimento().split("-");
		dataNascJFT.setText(date[2] + "/" + date[1] + "/" + date[0]);

		if (funcCampos.getSexo().equals("M")) {
			sexoJCB.setSelectedIndex(0);
		} else {
			sexoJCB.setSelectedIndex(1);
		}
		enderecoTF.setText(funcCampos.getEndereco());
		cepJFT.setText(funcCampos.getCep());
		telefoneJFT.setText(funcCampos.getTelefone());

		if (funcCampos.getTipoAcesso() == 1) {
			acessoJCB.setSelectedIndex(0);
		} else {
			acessoJCB.setSelectedIndex(1);
		}

		if (funcCampos.getTipoConta() == 1 || funcCampos.getTipoConta() == 2) {
			contaJCB.setSelectedIndex(funcCampos.getTipoConta() - 1);
			idEmpresaTF.setText(String.valueOf(funcCampos.getIdEmpresaFK()));

			int ent = binary(horario, funcCampos.getHorarioEntrada());
			if (ent < 0) {
				entradaJCB.setSelectedIndex(0);
			} else {
				entradaJCB.setSelectedIndex(ent);
			}

			int saida = binary(horario, funcCampos.getHorarioSaida());
			if (saida < 0) {
				saidaJCB.setSelectedIndex(0);
			} else {
				saidaJCB.setSelectedIndex(saida);
			}
		} else {
			contaJCB.setSelectedIndex(funcCampos.getTipoConta() - 1);
		}

		loginTF.setText(funcCampos.getLogin());
	}

	public int binary(String[] horario, String hora) {
		int inicio = 0;
		int fim = horario.length - 1;
		while (inicio <= fim) {
			int meio = ((inicio + fim) / 2);
			if (horario[meio].equals(hora)) {
				return meio;
			} else if (horario[meio].compareTo(hora) > 0) {
				fim = meio - 1;
			} else {
				inicio = meio + 1;
			}
		}
		return -1;
	}

	public int getIdUsuarioTl() {
		String[] ids = idLB.getText().split(" ");
		return Integer.parseInt(ids[1]);
	}

	public int getTipoContaTl() {
		if(contaJCB.getSelectedIndex() == 0) {
			return 1;
		} else if(contaJCB.getSelectedIndex() == 1) {
			return 2;
		} else if(contaJCB.getSelectedIndex() == 2) {
			return 3;
		} else {
			return 4;
		}
	}

}