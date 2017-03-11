package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.ConexaoBD;
import DAO.EmpresaDAO;
import DAO.UsuarioDAO;

public class Empresa {

	private int idEmpresa;
	private String nomeFantasia, razaoSocial, cnpj, horarioInicio,
			horarioFim, horarioLigarAC, horarioDesligarAC;
	private int temperatura, exist;
	private ArrayList<Conjunto> conjuntos;
	private EmpresaDAO edao;
	private ConexaoBD bd;
	private Connection conn = null;

	public Empresa() {
		idEmpresa = 0;
		nomeFantasia = null;
		razaoSocial = null;
		cnpj = null;
		temperatura = 0;
		horarioInicio = null;
		horarioFim = null;
		horarioLigarAC = null;
		horarioDesligarAC = null;
		conjuntos = new ArrayList<Conjunto>();
	}

	public Empresa(int idEmpresa, String nomeFantasia, String razaoSocial,
			String cnpj, int temperatura, String horarioInicio,
			String horarioFim, String horarioLigarAC, String horarioDesligarAC){//, ArrayList<Integer> conjuntos) {
		this.idEmpresa = idEmpresa;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.temperatura = temperatura;
		this.horarioInicio = horarioInicio;
		this.horarioFim = horarioFim;
		this.horarioLigarAC = horarioLigarAC;
		this.horarioDesligarAC = horarioDesligarAC;
		//this.conjuntos = conjuntos;
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

	public int getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
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

	public ArrayList<Conjunto> getConjuntos() {
		return conjuntos;
	}

	public void setConjuntos(ArrayList<Conjunto> conjuntos) {
		this.conjuntos = conjuntos;
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
	
	public int getExist() {
		return exist;
	}

	public void setExist(int exist) {
		this.exist = exist;
	}

	public void inserirEmpresa(Empresa emp) {
		edao = new EmpresaDAO();
		bd = new ConexaoBD();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			edao.inserirEmpresa(conn, emp);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Empresa consultarEmpresa(Empresa emp, int modo) {
		edao = new EmpresaDAO();
		bd = new ConexaoBD();
		Empresa empdao = new Empresa();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			if (modo == 0) {
				empdao = edao.consultarEmpresaCnpj(conn, emp);
			} else {
				empdao = edao.consultarEmpresa(conn, emp);
			}
			conn.commit();
			return empdao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empdao;
	}
	
	public void alterarEmpresa(Empresa emp) {
		edao = new EmpresaDAO();
		bd = new ConexaoBD();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			edao.alterarEmpresa(conn, emp);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void excluirEmpresa(Empresa emp) {
		edao = new EmpresaDAO();
		bd = new ConexaoBD();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			edao.excluirEmpresa(conn, emp);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
