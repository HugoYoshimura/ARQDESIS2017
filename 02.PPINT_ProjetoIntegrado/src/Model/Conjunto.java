package Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.ConexaoBD;
import DAO.ConjuntoDAO;
import DAO.EmpresaDAO;

public class Conjunto {
	private int idConjunto, idEmpresa, numeroConjunto;
	private ConjuntoDAO cdao;
	private ConexaoBD bd;
	Connection conn = null;
	

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
	
	
	public void inserirConjunto(Empresa emp) {
		cdao = new ConjuntoDAO();
		bd = new ConexaoBD();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			cdao.inserirConjunto(conn, emp);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Conjunto> consultarConjunto(Empresa emp) {
		cdao = new ConjuntoDAO();
		bd = new ConexaoBD();
		ArrayList<Conjunto> conjdao = new ArrayList<Conjunto>();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			conjdao = cdao.consultarConjunto(conn, emp);
			conn.commit();
			return conjdao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conjdao;
	}
	
	public ArrayList<Conjunto> consultarConjunto() {
		cdao = new ConjuntoDAO();
		bd = new ConexaoBD();
		ArrayList<Conjunto> conjdao = new ArrayList<Conjunto>();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			conjdao = cdao.consultarConjunto(conn);
			conn.commit();
			return conjdao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conjdao;
	}
	
	public void excluirConjunto(Empresa emp) {
		cdao = new ConjuntoDAO();
		bd = new ConexaoBD();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			cdao.excluirConjunto(conn, emp);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
