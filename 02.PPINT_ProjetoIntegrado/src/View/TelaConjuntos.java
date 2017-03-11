package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.ConjuntoCtrl;
import Model.Conjunto;

public class TelaConjuntos extends JDialog implements ActionListener {

	private JLabel mensagemLB;
	private JButton conjuntosBT[], confirmBT, cancelBT;
	private String[] conjuntosName;
	private int modo, idEmpresa, conjuntosStatus[];
	private ArrayList<Conjunto> confirm;
	private ResourceBundle bn;
	private ConjuntoCtrl conjuntoCtrl;

	public TelaConjuntos(ResourceBundle bn, JFrame frame, int modo, ArrayList<Conjunto> conj,
			ArrayList<Conjunto> conjuntosGeral) {
		super(frame, true);
		this.bn = bn;
		this.modo = modo;
		conjuntoCtrl = new ConjuntoCtrl();
		/*
		 * modo 5 - cadastrar 6 - alterar 7 - consultar 8 - remover
		 */

		super.setTitle(bn.getString("telaConjuntos.label.titulo"));

		mensagemLB = new JLabel(bn.getString("telaConjuntos.label.titulo"));

		Container container = getContentPane();
		container.setLayout(new BorderLayout(5, 5));
		confirm = new ArrayList<Conjunto>();
		conjuntosStatus = new int[40];
		conjuntosName = new String[40];
		int andar = 1;
		int conjunto = 1;
		for (int i = 0; i < 40; i++) {
			if (conjunto < 9) {
				conjuntosName[i] = andar + "0" + conjunto;
				conjunto++;
			} else {
				conjunto = 1;
				andar++;
				conjuntosName[i] = andar + "0" + conjunto;
				conjunto++;
			}
		}

		conjuntosBT = new JButton[40];
		for (int i = 0; i < 40; i++) {
			conjuntosBT[i] = new JButton(conjuntosName[i]);
			conjuntosBT[i].addActionListener(this);
		}

		if (conjuntoCtrl.consultarConjunto() == 1) {
			if (modo == 5) {
				for (int i = 0; i < conjuntosGeral.size(); i++) {
					for (int j = 0; j < conjuntosName.length; j++) {
						if (conjuntosGeral.get(i).getNumeroConjunto() == Integer.parseInt(conjuntosName[j])) {
							conjuntosBT[j].setEnabled(false);
						}
					}
				}
			} else {
				ArrayList<Conjunto> conjuntos = new ArrayList<Conjunto>();
				conjuntos = conj;
				for (int i = 0; i < conjuntosGeral.size(); i++) {
					for (int j = 0; j < conjuntosName.length; j++) {
						if (conjuntosGeral.get(i).getNumeroConjunto() == Integer.parseInt(conjuntosName[j])) {
							for (int k = 0; k < conjuntos.size(); k++) {
								if (conjuntos.get(k).getNumeroConjunto() == Integer.parseInt(conjuntosName[j])) {
									conjuntosBT[j].setSelected(true);
									conjuntosBT[j].doClick();
									break;
								}
							}
							if (!conjuntosBT[j].isSelected()) {
								conjuntosBT[j].setEnabled(false);
								break;
							} else {
								break;
							}
						}
					}
				}
			}
		}

		JPanel pnConjuntos = new JPanel(new GridLayout(10, 4, 10, 10));

		for (int i = 0; i < conjuntosBT.length; i++) {
			pnConjuntos.add(conjuntosBT[i]);
		}

		confirmBT = new JButton(bn.getString("telaConjuntos.button.confirm"));
		confirmBT.addActionListener(this);

		cancelBT = new JButton(bn.getString("telaConjuntos.button.cancel"));
		cancelBT.addActionListener(this);

		JPanel pnButtons = new JPanel(new GridLayout(1, 2, 5, 5));
		pnButtons.add(confirmBT);
		pnButtons.add(cancelBT);

		add(mensagemLB, BorderLayout.NORTH);
		add(pnConjuntos, BorderLayout.CENTER);
		add(pnButtons, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 500); // set frame size
		setLocationRelativeTo(null);
		setVisible(true);
	}

	static ArrayList<Conjunto> confirmacao(ResourceBundle bn, JFrame frame, int modo, ArrayList<Conjunto> conj,
			ArrayList<Conjunto> conjuntosGeral) {
		TelaConjuntos tela = new TelaConjuntos(bn, frame, modo, conj, conjuntosGeral);
		return tela.confirm;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == confirmBT) {
			quickSort(confirm);
			dispose();
		}
		if (event.getSource() == cancelBT) {
			confirm = new ArrayList<Conjunto>();
			dispose();
		}
		for (int i = 0; i < conjuntosBT.length; i++) {
			if (event.getSource() == conjuntosBT[i]) {
				if (conjuntosStatus[i] == 0) {
					Conjunto novo = new Conjunto();
					novo.setNumeroConjunto(Integer.parseInt(conjuntosName[i]));
					confirm.add(novo);
					conjuntosBT[i].setForeground(Color.RED);
					conjuntosStatus[i] = 1;
				} else {
					quickSort(confirm);
					int pos = binary(confirm, Integer.parseInt(conjuntosName[i]));
					confirm.remove(pos);
					conjuntosBT[i].setForeground(Color.BLACK);
					conjuntosStatus[i] = 0;
				}
				break;
			}
		}
	}

	/**
	 * Método que busca uma mercadoria com uma busca linear
	 * 
	 * @param conjuntos
	 * @param codigo
	 * @return
	 */
	public int binary(ArrayList<Conjunto> conjuntos, int codigo) {
		int inicio = 0;
		int fim = conjuntos.size() - 1;
		while (inicio <= fim) {
			int meio = ((inicio + fim) / 2);
			if (conjuntos.get(meio).getNumeroConjunto() == codigo) {
				return meio;
			} else if (conjuntos.get(meio).getNumeroConjunto() > codigo) {
				fim = meio - 1;
			} else {
				inicio = meio + 1;
			}
		}
		return -1;
	}

	/**
	 * Ordenar ArrayList conjuntos
	 * 
	 * @param conjuntos
	 */
	public void quickSort(ArrayList<Conjunto> conjuntos) {
		quickSort(conjuntos, 0, conjuntos.size() - 1);
	}

	public void quickSort(ArrayList<Conjunto> conjuntos, int esquerda, int direita) {
		int indice = particao(conjuntos, esquerda, direita);
		if (esquerda < indice - 1) {
			quickSort(conjuntos, esquerda, indice - 1);
		}
		if (direita > indice) {
			quickSort(conjuntos, indice, direita);
		}
	}

	public int particao(ArrayList<Conjunto> conjuntos, int esquerda, int direita) {
		int i = esquerda;
		int j = direita;
		Conjunto aux = new Conjunto();
		int pivo = 0;
		pivo = conjuntos.get((direita + esquerda) / 2).getNumeroConjunto();
		while (i <= j) {
			while (conjuntos.get(i).getNumeroConjunto() < pivo) {
				i++;
			}
			while (conjuntos.get(j).getNumeroConjunto() > pivo) {
				j--;
			}
			if (i <= j) {
				aux = conjuntos.get(i);
				conjuntos.set(i, conjuntos.get(j));
				conjuntos.set(j, aux);
				i++;
				j--;
			}
		}
		return i;
	}
}