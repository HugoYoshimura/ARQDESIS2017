package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Model.Empresa;

public class EmpresaDAO {
	private int idEmpresa;
	private String nomeFantasia, razaoSocial, cnpj, temperatura, horarioInicio,
			horarioFim;
	private Empresa emp;

	public EmpresaDAO() {
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

	public String getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(String temperatura) {
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

	/**
	 * Método que insere novo usuário no Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 */
	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	public void inserirEmpresa(Connection conn, Empresa emp) {
		String sqlInsert = "INSERT INTO empresa(cnpj, razaoSocial, nomeFantasia, horarioInicio, horarioFechamento, temperaturaPadrao, horarioPadraoOnAC, horarioPadraoOffAC) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stm = null;
		
		String[] horEnt = emp.getHorarioInicio().split(":");
		int horaE = Integer.parseInt(horEnt[0]);
		int minutoE = Integer.parseInt(horEnt[1]);

		String[] horSaida = emp.getHorarioFim().split(":");
		int horaS = Integer.parseInt(horSaida[0]);
		int minutoS = Integer.parseInt(horSaida[1]);

		String[] horOnAC = emp.getHorarioLigarAC().split(":");
		int horaOnAC = Integer.parseInt(horOnAC[0]);
		int minutoOnAC = Integer.parseInt(horOnAC[1]);

		String[] horOffAC = emp.getHorarioDesligarAC().split(":");
		int horaOffAC = Integer.parseInt(horOffAC[0]);
		int minutoOffAC = Integer.parseInt(horOffAC[1]);
		try {
			stm = conn.prepareStatement(sqlInsert); // pre configura
			stm.setString(1, emp.getCnpj());
			stm.setString(2, emp.getRazaoSocial());
			stm.setString(3, emp.getNomeFantasia());
			stm.setTime(4, new java.sql.Time(horaE, minutoE, 00));
			stm.setTime(5, new java.sql.Time(horaS, minutoS, 00));
			stm.setInt(6, emp.getTemperatura());
			stm.setTime(7, new java.sql.Time(horaOnAC, minutoOnAC, 00));
			stm.setTime(8, new java.sql.Time(horaOffAC, minutoOffAC, 00));
			stm.execute();
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
		//return consultarEmpresaCnpj(conn, emp);
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que consulta usuário no Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 * @return
	 */
	public Empresa consultarEmpresa(Connection conn, Empresa emp) {
		String sqlSelect = "SELECT * FROM empresa WHERE id_empresa = ?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.prepareStatement(sqlSelect);
			stm.setInt(1, emp.getIdEmpresa());
			rs = stm.executeQuery();
			while (rs.next()) {
				emp.setIdEmpresa(rs.getInt(1));
				emp.setCnpj(rs.getString(2));
				emp.setRazaoSocial(rs.getString(3));
				emp.setNomeFantasia(rs.getString(4));
				emp.setHorarioInicio(rs.getTime(5) + "");
				emp.setHorarioFim(rs.getTime(6) + "");
				emp.setTemperatura(rs.getInt(7));
				emp.setHorarioLigarAC(rs.getTime(8) + "");
				emp.setHorarioDesligarAC(rs.getTime(9) + "");
				conn.commit();
				emp.setExist(1);
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
					return emp;
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
				}
			}
		}
		return emp;
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que consulta usuário no Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 * @return
	 */
	public Empresa consultarEmpresaCnpj(Connection conn, Empresa emp) {
		String sqlSelect = "SELECT * FROM empresa WHERE cnpj = ?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.prepareStatement(sqlSelect);
			stm.setString(1, emp.getCnpj());
			rs = stm.executeQuery();
			while (rs.next()) {
				this.emp.setIdEmpresa(rs.getInt(1));
				this.emp.setCnpj(rs.getString(2));
				this.emp.setNomeFantasia(rs.getString(3));
				this.emp.setRazaoSocial(rs.getString(4));
				this.emp.setHorarioInicio(rs.getDate(5) + "");
				this.emp.setHorarioFim(rs.getDate(6) + "");
				this.emp.setTemperatura(rs.getInt(7));
				this.emp.setHorarioLigarAC(rs.getDate(8) + "");
				this.emp.setHorarioDesligarAC(rs.getDate(9) + "");
				conn.commit();
				this.emp.setExist(1);
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
					return this.emp;
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
				}
			}
		}
		return this.emp;
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que altera usuário no Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 * @return
	 * @throws ParseException
	 */
	public Empresa alterarEmpresa(Connection conn, Empresa emp) {

		String sqlUpdate = "UPDATE empresa SET cnpj = ?, razaoSocial = ?, nomeFantasia = ?, horarioInicio = ?, horarioFechamento = ?, temperaturaPadrao = ?, horarioPadraoOnAC = ?, horarioPadraoOffAC = ? WHERE id_empresa = "
				+ emp.getIdEmpresa();
		PreparedStatement stm = null;
		String[] horEnt = emp.getHorarioInicio().split(":");
		int horaE = Integer.parseInt(horEnt[0]);
		int minutoE = Integer.parseInt(horEnt[1]);

		String[] horSaida = emp.getHorarioFim().split(":");
		int horaS = Integer.parseInt(horSaida[0]);
		int minutoS = Integer.parseInt(horSaida[1]);

		String[] horOnAC = emp.getHorarioLigarAC().split(":");
		int horaOnAC = Integer.parseInt(horOnAC[0]);
		int minutoOnAC = Integer.parseInt(horOnAC[1]);

		String[] horOffAC = emp.getHorarioDesligarAC().split(":");
		int horaOffAC = Integer.parseInt(horOffAC[0]);
		int minutoOffAC = Integer.parseInt(horOffAC[1]);
		try {
			stm = conn.prepareStatement(sqlUpdate); // pre configura
			stm.setString(1, emp.getCnpj());
			stm.setString(2, emp.getRazaoSocial());
			stm.setString(3, emp.getNomeFantasia());
			stm.setTime(4, new java.sql.Time(horaE, minutoE, 00));
			stm.setTime(5, new java.sql.Time(horaS, minutoS, 00));
			stm.setInt(6, emp.getTemperatura());
			stm.setTime(7, new java.sql.Time(horaOnAC, minutoOnAC, 00));
			stm.setTime(8, new java.sql.Time(horaOffAC, minutoOffAC, 00));
			stm.execute();
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

		return consultarEmpresa(conn, emp);
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que exclui o usuário do Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 */
	public void excluirEmpresa(Connection conn, Empresa emp) {
		String sqlDelete = "DELETE FROM empresa WHERE id_empresa = ?";
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
