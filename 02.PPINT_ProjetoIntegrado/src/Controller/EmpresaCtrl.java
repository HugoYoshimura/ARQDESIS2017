package Controller;

import java.util.ArrayList;

import Model.Empresa;

public class EmpresaCtrl {
	private int idEmpresa;
	private String nomeFantasia, razaoSocial, cnpj, horarioInicio, horarioFim,
			horarioLigarAC, horarioDesligarAC;
	private int temperatura;
	private ArrayList<Integer> conjuntos;
	private Empresa emp;

	public EmpresaCtrl() {
		emp = new Empresa();
	}
	
	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(String horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public String getHorarioFim() {
		return horarioFim;
	}

	public void setHorarioFim(String horarioFim) {
		this.horarioFim = horarioFim;
	}

	public String getHorarioLigarAC() {
		return horarioLigarAC;
	}

	public void setHorarioLigarAC(String horarioLigarAC) {
		this.horarioLigarAC = horarioLigarAC;
	}

	public String getHorarioDesligarAC() {
		return horarioDesligarAC;
	}

	public void setHorarioDesligarAC(String horarioDesligarAC) {
		this.horarioDesligarAC = horarioDesligarAC;
	}

	public int getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}

	public ArrayList<Integer> getConjuntos() {
		return conjuntos;
	}

	public void setConjuntos(ArrayList<Integer> conjuntos) {
		this.conjuntos = conjuntos;
	}

	public Empresa getEmp() {
		return emp;
	}

	public void setEmp(Empresa emp) {
		this.emp = emp;
	}

	public Empresa cadastrarEmpresa(Empresa emp) {
		emp.inserirEmpresa(emp);
		this.emp = emp.consultarEmpresa(emp, 0);
		return this.emp;
	}

	public int consultarEmpresa(int idEmpresa) {
		this.emp.setIdEmpresa(idEmpresa);
		this.emp = emp.consultarEmpresa(emp, 1);
		if (emp.getExist() == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	public void alterarEmpresa(Empresa emp) {
		emp.alterarEmpresa(emp);
	}

	public void removerEmpresa(int idEmprea) {
		emp.setIdEmpresa(idEmpresa);
		emp.excluirEmpresa(emp);
	}
}
