package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import Model.Conjunto;
import Model.Empresa;

public class ConjuntoDAO {
	private ArrayList<Conjunto> conjuntos;
	private int idConjunto, idEmpresa, numeroConjunto;
	
	public ConjuntoDAO() {
		conjuntos = new ArrayList<Conjunto>();
		idConjunto = 0;
		idEmpresa = 0;
		numeroConjunto = 0;
	}
	
	
	public ArrayList<Conjunto> inserirConjunto(Connection conn, Empresa emp) {
		String sqlInsert = "INSERT INTO conjunto(numeroConjunto, id_empresa) VALUES (?, ?)";
		PreparedStatement stm = null;
		
		try {
			for (int i = 0; i < emp.getConjuntos().size(); i++) {
				stm = conn.prepareStatement(sqlInsert); // pre configura
				stm.setInt(1, emp.getConjuntos().get(i).getNumeroConjunto());
				stm.setInt(2, emp.getIdEmpresa());
				stm.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback(); // <-> commit
			} catch (SQLException e1) {
				System.out.print(e1.getStackTrace());
			}
		} finally {
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
				}
			}
		}
		return consultarConjunto(conn, emp);
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que consulta usuário no Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 * @return
	 */
	public ArrayList<Conjunto> consultarConjunto(Connection conn, Empresa emp) {
		String sqlSelect = "SELECT * FROM conjunto WHERE id_empresa = ?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		Conjunto conj;
		try {
			stm = conn.prepareStatement(sqlSelect);
			stm.setInt(1, emp.getIdEmpresa());
			rs = stm.executeQuery();
			while (rs.next()) {
				conj = new Conjunto();
				conj.setIdConjunto(rs.getInt(1));
				conj.setNumeroConjunto(rs.getInt(2));
				conj.setIdEmpresa(rs.getInt(3));
				conn.commit();
				conjuntos.add(conj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.print(e1.getStackTrace());
			}
		} finally {
			if (stm != null) {
				try {
					stm.close();
					return conjuntos;
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
				}
			}
		}
		return conjuntos;
	}

	
	public ArrayList<Conjunto> consultarConjunto(Connection conn) {
		String sqlSelect = "SELECT * FROM conjunto";
		PreparedStatement stm = null;
		ResultSet rs = null;
		Conjunto conj;
		try {
			stm = conn.prepareStatement(sqlSelect);
			rs = stm.executeQuery();
			while (rs.next()) {
				conj = new Conjunto();
				conj.setIdConjunto(rs.getInt(1));
				conj.setNumeroConjunto(rs.getInt(2));
				conj.setIdEmpresa(rs.getInt(3));
				conn.commit();
				conjuntos.add(conj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.print(e1.getStackTrace());
			}
		} finally {
			if (stm != null) {
				try {
					stm.close();
					return conjuntos;
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
				}
			}
		}
		return conjuntos;
	}
	
	public void excluirConjunto(Connection conn, Empresa emp) {
		String sqlDelete = "DELETE FROM conjunto WHERE id_empresa = ?";
		PreparedStatement stm = null;
		try {
			stm = conn.prepareStatement(sqlDelete);
			stm.setInt(1, emp.getIdEmpresa());
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.print(e1.getStackTrace());
			}
		} finally {
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
				}
			}
		}
	}

}
