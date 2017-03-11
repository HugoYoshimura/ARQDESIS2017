package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import Controller.ConjuntoCtrl;
import Controller.EmpresaCtrl;
import Model.Conjunto;
import Model.Empresa;

public class TelaEmpresa extends JDialog implements ActionListener {
	private JLabel tituloLB, idLB, idEmpresa, nomeFantasiaLB, razaoSocialLB, cnpjLB, horFuncionamentoLB, temperaturaLB,
			inicioFuncLB, fimFuncLB, acOnLB, acOffLB, conjuntosLB;
	private JTextField nomeFantasiaTF, razaoSocialTF;
	private JButton cadastrarBT, conjuntoBT;
	private MaskFormatter cnpjMF;
	private JFormattedTextField cnpjJFT;
	private ResourceBundle bn = null;
	private JComboBox inicioJCB, fimJCB, acOnJCB, acOffJCB, temperaturaJCB;
	private String[] horario, temperaturas;
	private JPanel pnNome, pnCnpj, pnHorario, pnConjuntoS, pnConjuntoF, pnAC, pnSubEmpresa1, pnSubEmpresa2,
			pnSubEmpresa3, panelEmpresa;
	private ArrayList<Conjunto> conjuntos, conjuntosB, conjuntosA, conjuntosBD;
	private int modo;
	private Empresa emp;

	public TelaEmpresa(ResourceBundle bn, int modo) {
		this.bn = bn;
		this.modo = modo;
		emp = new Empresa();
		conjuntosB = new ArrayList<Conjunto>();
		conjuntosA = new ArrayList<Conjunto>();
	}

	public JPanel criarTela() {

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// NOME FANTASIA - RAZÃO SOCIAL
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Label e TextField Nome Fantasia
		nomeFantasiaLB = new JLabel(bn.getString("telaEmpresa.label.nomeFantasia"));
		nomeFantasiaTF = new JTextField(45);

		// Label e TextField Razão Social
		razaoSocialLB = new JLabel(bn.getString("telaEmpresa.label.razaoSocial"));
		razaoSocialTF = new JTextField(45);

		// JPanel
		pnNome = new JPanel(new GridLayout(4, 1));
		pnNome.add(nomeFantasiaLB);
		pnNome.add(nomeFantasiaTF);
		pnNome.add(razaoSocialLB);
		pnNome.add(razaoSocialTF);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// CNPJ
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Label CNPJ
		cnpjLB = new JLabel(bn.getString("telaEmpresa.label.cpnj"));

		// MaskFormatter e JFormattedTextField CNPJ
		try {
			cnpjMF = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cnpjMF.setPlaceholderCharacter('_');
		cnpjJFT = new JFormattedTextField(cnpjMF);

		// JPanel CNPJ
		pnCnpj = new JPanel(new GridLayout(2, 2, 5, 5));
		pnCnpj.add(cnpjLB);
		pnCnpj.add(new JLabel(""));
		pnCnpj.add(cnpjJFT);
		pnCnpj.add(new JLabel(""));

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// P(nomeF, razaoS) + P(cnpj)
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		pnSubEmpresa1 = new JPanel(new GridLayout(2, 1, 5, 5));
		pnSubEmpresa1
				.setBorder(new TitledBorder(new EtchedBorder(), bn.getString("telaEmpresa.bordertitle.dadosEmpresa")));
		pnSubEmpresa1.add(pnNome);
		pnSubEmpresa1.add(pnCnpj);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// lista de horário para JComboBox
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
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Horário de funcionamento e Conjuntos
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

		inicioFuncLB = new JLabel(bn.getString("telaEmpresa.label.inicioFunc"));
		inicioJCB = new JComboBox(horario);
		inicioJCB.setSelectedIndex(18);
		inicioJCB.setMaximumRowCount(5);

		fimFuncLB = new JLabel(bn.getString("telaEmpresa.label.fimFunc"));
		fimJCB = new JComboBox(horario);
		fimJCB.setSelectedIndex(34);
		fimJCB.setMaximumRowCount(5);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		pnHorario = new JPanel(new GridLayout(2, 2, 5, 5));
		pnHorario.setBorder(
				new TitledBorder(new EtchedBorder(), bn.getString("telaEmpresa.bordertitle.horFuncionamento")));
		pnHorario.add(inicioFuncLB);
		pnHorario.add(fimFuncLB);
		pnHorario.add(inicioJCB);
		pnHorario.add(fimJCB);

		conjuntosLB = new JLabel("", SwingConstants.CENTER);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		pnConjuntoF = new JPanel(new GridLayout(1, 1, 5, 5));
		pnConjuntoF.add(conjuntosLB);
		pnConjuntoF.setBorder(
				new TitledBorder(new EtchedBorder(), bn.getString("telaEmpresa.bordertitle.conjuntoEdificio")));

		ConjuntoCtrl conjuntoCtrl = new ConjuntoCtrl();
		conjuntoCtrl.consultarConjunto();
		conjuntosBD = conjuntoCtrl.getConjuntos();
		
		conjuntoBT = new JButton(bn.getString("telaEmpresa.button.conjuntos"));
		conjuntoBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFrame frame = new JFrame();
				if (modo == 5) {
					conjuntos = TelaConjuntos.confirmacao(bn, frame, modo, null, conjuntosBD);
					// conjuntosA = conjuntos;
				} else {
					// conjuntosB = conjuntosA;
					//ArrayList<Conjunto> conjuntosI = new ArrayList<Conjunto>();
					//conjuntosI = conjuntos;
					boolean teste = true;
					for(int i = 0; i < conjuntosBD.size(); i++) {
						for (int j = 0; j < conjuntos.size(); j++) {
							if(conjuntosBD.get(i).getNumeroConjunto() == conjuntos.get(j).getNumeroConjunto()) {
								teste = true;
								break;
							} else {
								teste = false;
							}
						}
						if(teste) {
							conjuntosBD.remove(i);
						}
					}
					conjuntos = TelaConjuntos.confirmacao(bn, frame, modo, conjuntos, conjuntosBD);
					// conjuntosA = conjuntos;
				}

				if (conjuntos.size() != 0) {
					String conj = "";
					for (int i = 0; i < conjuntos.size(); i++) {
						conj += conjuntos.get(i).getNumeroConjunto() + " ";
					}
					conjuntosLB.setText(conj);
				}
			}
		});

		JPanel pnButtonConjunto = new JPanel(new GridLayout(1, 3));
		pnButtonConjunto.add(new JLabel());
		pnButtonConjunto.add(conjuntoBT);
		pnButtonConjunto.add(new JLabel());

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		pnConjuntoS = new JPanel(new BorderLayout());
		pnConjuntoS.add(pnButtonConjunto, BorderLayout.NORTH);
		pnConjuntoS.add(pnConjuntoF, BorderLayout.CENTER);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		pnSubEmpresa2 = new JPanel(new GridLayout(2, 1, 5, 5));
		pnSubEmpresa2.add(pnConjuntoS);
		pnSubEmpresa2.add(pnHorario);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Ar Condicionado
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		horFuncionamentoLB = new JLabel(bn.getString("telaEmpresa.bordertitle.horFuncionamento"));

		temperaturaLB = new JLabel(bn.getString("telaEmpresa.label.temperatura"));

		temperaturas = new String[13];
		int temp = 15;
		for (int i = 0; i < temperaturas.length; i++) {
			temperaturas[i] = temp + " °C";
			temp++;
		}
		temperaturaJCB = new JComboBox(temperaturas);
		temperaturaJCB.setSelectedIndex(4);
		temperaturaJCB.setMaximumRowCount(5);

		JPanel pnTemperatura = new JPanel(new GridLayout(2, 1, 5, 5));
		pnTemperatura.add(temperaturaLB);
		pnTemperatura.add(temperaturaJCB);

		acOnLB = new JLabel(bn.getString("telaEmpresa.label.acOn"));
		acOnJCB = new JComboBox(horario);
		acOnJCB.setSelectedIndex(20);
		acOnJCB.setMaximumRowCount(5);

		acOffLB = new JLabel(bn.getString("telaEmpresa.label.acOff"));
		acOffJCB = new JComboBox(horario);
		acOffJCB.setSelectedIndex(30);
		acOffJCB.setMaximumRowCount(5);

		JPanel pnHorAC = new JPanel(new GridLayout(2, 2, 5, 5));
		pnHorAC.add(acOnJCB);
		pnHorAC.add(acOnLB);
		pnHorAC.add(acOffJCB);
		pnHorAC.add(acOffLB);

		pnAC = new JPanel(new GridLayout(2, 1, 5, 5));
		pnAC.setBorder(new TitledBorder(new EtchedBorder(), bn.getString("telaEmpresa.bordertitle.horTemperatura")));
		pnAC.add(pnTemperatura);
		pnAC.add(pnHorAC);

		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// = = = = = = = = = = = = = = =
		pnSubEmpresa3 = new JPanel(new GridLayout(1, 2, 5, 5));
		pnSubEmpresa3.add(pnSubEmpresa2);
		pnSubEmpresa3.add(pnAC);

		JPanel pnSubEmpresaFinal = new JPanel(new GridLayout(2, 1, 5, 5));
		pnSubEmpresaFinal.add(pnSubEmpresa1);
		pnSubEmpresaFinal.add(pnSubEmpresa3);

		if (modo == 5) {
			cadastrarBT = new JButton(bn.getString("telaEmpresa.button.cadastrar"));
		} else if (modo == 6) {
			cadastrarBT = new JButton(bn.getString("telaEmpresa.button.alterar"));
		} else if (modo == 7) {
			cadastrarBT = new JButton();
			conjuntoBT.setEnabled(false);
		} else {
			cadastrarBT = new JButton(bn.getString("telaEmpresa.button.remover"));
		}

		cadastrarBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				EmpresaCtrl empCtrl = new EmpresaCtrl();
				ConjuntoCtrl conjCtrl = new ConjuntoCtrl();
				ArrayList<Conjunto> conjuntosInsert = new ArrayList<Conjunto>();
				ArrayList<Conjunto> conjuntosRemove = new ArrayList<Conjunto>();
				JFrame frame = new JFrame();
				emp = new Empresa();
				if (modo == 5) {
					if (setAtributos() || !conjuntosLB.getText().equals("") || conjuntosLB.getText() != null) {
						Empresa aux = empCtrl.cadastrarEmpresa(emp);
						if (aux == null) {
							TelaMensagem.confirmacao(bn, frame, 6);
						} else {
							conjCtrl.cadastrarConjunto(aux);
							TelaMensagem.confirmacao(bn, frame, 7);
							cadastrarBT.setEnabled(false);
						}
					} else {
						TelaMensagem.confirmacao(bn, frame, 4);
					}
				} else if (modo == 6) { // alterar
					if (setAtributos()) {
						if (TelaConfirm.confirmacao(bn, frame, 6)) {
							String[] ids = idLB.getText().split(" ");
							int conta = empCtrl.consultarEmpresa(Integer.parseInt(ids[1]));
							emp.setIdEmpresa((Integer.parseInt(ids[1])));
							empCtrl.alterarEmpresa(emp);
							
							conjuntosB = conjuntosA;
							conjuntosA = conjuntos;
							
							if (conjCtrl.consultarConjunto(Integer.parseInt(ids[1])) == 1) {
								conjCtrl.removerEmpresa(Integer.parseInt(ids[1]));
								Conjunto conj = new Conjunto();
								String[] conjunt = conjuntosLB.getText().split(" ");
								conjuntosB = conjuntosA;
								if (conjuntosB.size() != conjuntosA.size()) {
									boolean still = false;
									for (int i = 0; i < conjuntosB.size(); i++) {
										for (int j = 0; j < conjuntosA.size(); j++) {
											if (conjuntosA.get(j).getNumeroConjunto() == conjuntosB.get(i)
													.getNumeroConjunto()) {
												still = true;
												break;
											} else {
												still = false;
											}
										}
										if (!still) {
											conjuntosRemove.add(conjuntosB.get(i));
										}
									}
									for (int i = 0; i < conjuntosA.size(); i++) {
										for (int j = 0; j < conjuntosB.size(); j++) {
											if (conjuntosA.get(i).getNumeroConjunto() == conjuntosB.get(j)
													.getNumeroConjunto()) {
												still = true;
												break;
											} else {
												still = false;
											}
										}
										if (!still) {
											conjuntosInsert.add(conjuntosA.get(i));
										}
									}
								}
								emp.setConjuntos(conjuntosInsert);
								conjCtrl.cadastrarConjunto(emp);
								emp.setConjuntos(conjuntosRemove);
								conjCtrl.removerConjunto(emp);
							}

							TelaMensagem.confirmacao(bn, frame, 7);
							cadastrarBT.setEnabled(false);
						}
					} else {
						TelaMensagem.confirmacao(bn, frame, 4);
					}
				} else if (modo == 7) { // consultar

				} else { // remover
					if (TelaConfirm.confirmacao(bn, frame, 7)) {
						String[] idLabel = idLB.getText().split(" ");
						int id = Integer.parseInt(idLabel[idLabel.length - 1]);
						empCtrl.removerEmpresa(id);
						if (emp.getExist() == 1) {
							TelaMensagem.confirmacao(bn, frame, 8);
						} else if (emp.getExist() == 0) {
							TelaMensagem.confirmacao(bn, frame, 9);
							cadastrarBT.setEnabled(false);
						}
					}
				}

			}
		});

		idLB = new JLabel("ID: ");
		if (modo == 5) {
			tituloLB = new JLabel(bn.getString("telaEmpresa.label.titulo1"), SwingConstants.CENTER);
		} else if (modo == 6) {
			tituloLB = new JLabel(bn.getString("telaEmpresa.label.titulo2"), SwingConstants.CENTER);
		} else if (modo == 7) {
			tituloLB = new JLabel(bn.getString("telaEmpresa.label.titulo3"), SwingConstants.CENTER);
		} else {
			tituloLB = new JLabel(bn.getString("telaEmpresa.label.titulo4"), SwingConstants.CENTER);
		}
		tituloLB.setFont(new Font("Titulo", Font.BOLD, 20));

		JPanel pnTitulo = new JPanel(new GridLayout(2, 1));
		pnTitulo.add(tituloLB);
		pnTitulo.add(idLB);

		panelEmpresa = new JPanel(new BorderLayout());
		panelEmpresa.setLayout(new BorderLayout());
		panelEmpresa.add(pnTitulo, BorderLayout.NORTH);
		panelEmpresa.add(pnSubEmpresaFinal, BorderLayout.CENTER);

		if (modo != 7) {
			JPanel pnButtonFinal = new JPanel(new GridLayout(1, 3, 5, 5));
			pnButtonFinal.add(new JLabel());
			pnButtonFinal.add(cadastrarBT);
			pnButtonFinal.add(new JLabel());
			panelEmpresa.add(pnButtonFinal, BorderLayout.SOUTH);
		}

		return panelEmpresa;

	}

	public boolean setAtributos() {
		boolean ok = true;
		if (cnpjJFT.getText().equals("__.___.___/____-__")) {
			ok = false;
		} else {
			emp.setCnpj(cnpjJFT.getText());
		}

		if (nomeFantasiaTF.getText() == null || nomeFantasiaTF.getText().equals("") || !ok) {
			ok = false;
		} else {
			emp.setNomeFantasia(nomeFantasiaTF.getText());
		}

		if (razaoSocialTF.getText() == null || razaoSocialTF.getText().equals("") || !ok) {
			ok = false;
		} else {
			emp.setRazaoSocial(razaoSocialTF.getText());
		}

		emp.setHorarioInicio(inicioJCB.getSelectedItem().toString());

		emp.setHorarioFim(fimJCB.getSelectedItem().toString());

		String[] temp = temperaturaJCB.getSelectedItem().toString().split(" ");
		emp.setTemperatura(Integer.parseInt(temp[0]));

		emp.setHorarioLigarAC(acOnJCB.getSelectedItem().toString());

		emp.setHorarioDesligarAC(acOffJCB.getSelectedItem().toString());

		return ok;
	}

	public void setCampos(Empresa empCampos) {
		idLB.setText("ID: " + empCampos.getIdEmpresa());
		cnpjJFT.setText(empCampos.getCnpj());
		nomeFantasiaTF.setText(empCampos.getNomeFantasia());

		razaoSocialTF.setText(empCampos.getRazaoSocial());

		String res = "";
		conjuntos = empCampos.getConjuntos();
		for (int i = 0; i < empCampos.getConjuntos().size(); i++) {
			res += empCampos.getConjuntos().get(i).getNumeroConjunto() + " ";
		}
		conjuntosLB.setText(res);

		int inicio = binary(horario, empCampos.getHorarioInicio());
		if (inicio < 0) {
			inicioJCB.setSelectedIndex(0);
		} else {
			inicioJCB.setSelectedIndex(inicio);
		}

		int fim = binary(horario, empCampos.getHorarioFim());
		if (fim < 0) {
			fimJCB.setSelectedIndex(0);
		} else {
			fimJCB.setSelectedIndex(fim);
		}

		int ligar = binary(horario, empCampos.getHorarioLigarAC());
		if (ligar < 0) {
			acOnJCB.setSelectedIndex(0);
		} else {
			acOnJCB.setSelectedIndex(inicio);
		}

		int desligar = binary(horario, empCampos.getHorarioDesligarAC());
		if (desligar < 0) {
			acOffJCB.setSelectedIndex(0);
		} else {
			acOffJCB.setSelectedIndex(desligar);
		}

		int temp = binary(temperaturas, emp.getTemperatura() + " °C");
		if (temp < 0) {
			temperaturaJCB.setSelectedIndex(0);
		} else {
			temperaturaJCB.setSelectedIndex(temp);
		}
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

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public int getIdEmpresaTl() {
		String[] ids = idLB.getText().split(" ");
		return Integer.parseInt(ids[1]);
	}

}
