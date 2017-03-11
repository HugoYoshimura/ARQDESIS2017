package Controller;

import java.util.ArrayList;

import Model.Conjunto;
import Model.Empresa;

public class ConjuntoCtrl {
	private int idConjunto, idEmpresa, numeroConjunto;
	private Conjunto conj;
	private ArrayList<Conjunto> conjuntos;

	public ConjuntoCtrl() {
		idConjunto = 0;
		idEmpresa = 0;
		numeroConjunto = 0;
		conj = new Conjunto();
		conjuntos = new ArrayList<Conjunto>();
	}

	public int getIdConjunto() {
		return idConjunto;
	}

	public void setIdConjunto(int idConjunto) {
		this.idConjunto = idConjunto;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public int getNumeroConjunto() {
		return numeroConjunto;
	}

	public void setNumeroConjunto(int numeroConjunto) {
		this.numeroConjunto = numeroConjunto;
	}

	public Conjunto getConj() {
		return conj;
	}

	public void setConj(Conjunto conj) {
		this.conj = conj;
	}

	public ArrayList<Conjunto> getConjuntos() {
		return conjuntos;
	}

	public void setConjuntos(ArrayList<Conjunto> conjuntos) {
		this.conjuntos = conjuntos;
	}

	public ArrayList<Conjunto> cadastrarConjunto(Empresa emp) {
		conj.inserirConjunto(emp);
		conjuntos = conj.consultarConjunto(emp);
		return conjuntos;
	}

	public int consultarConjunto(int idEmpresa) {
		Empresa emp = new Empresa();
		emp.setIdEmpresa(idEmpresa);
		conjuntos = conj.consultarConjunto(emp);
		if (conjuntos.size() <= 0) {
			return 0;
		} else {
			return 1;
		}
	}

	public int consultarConjunto() {
		conjuntos = conj.consultarConjunto();
		if (conjuntos.size() <= 0) {
			return 0;
		} else {
			return 1;
		}
	}

	public void removerConjunto(Empresa emp) {
		conj.excluirConjunto(emp);
		conjuntos = conj.consultarConjunto(emp);
	}
	
	public void removerEmpresa(int idEmprea) {
		Empresa emp = new Empresa();
		emp.setIdEmpresa(idEmpresa);
		conj.excluirConjunto(emp);
	}
	
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
				inicio = meio;
			}
		}
		return -1;
	}
	
}
