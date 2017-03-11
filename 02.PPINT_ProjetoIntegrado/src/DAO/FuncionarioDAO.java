package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import Model.Funcionario;
import Model.Usuario;

public class FuncionarioDAO extends UsuarioDAO{
	private String horarioEntrada;
	private String horarioSaida;
	private int cnpj;
	private Funcionario funcionario;
	
	public FuncionarioDAO() {
		
	}
	
	public int getCnpj() {
		return cnpj;
	}

	public void setCnpj(int cnpj) {
		this.cnpj = cnpj;
	}

	public String getHorarioEntrada() {
		return horarioEntrada;
	}

	public void setHorarioEntrada(String horarioEntrada) {
		this.horarioEntrada = horarioEntrada;
	}

	public String getHorarioSaida() {
		return horarioSaida;
	}

	public void setHorarioSaida(String horarioSaida) {
		this.horarioSaida = horarioSaida;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}



	/**
	 * Método que insere novo usuário no Banco de Dados
	 * @param conn
	 * @param user
	 */
	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	public Funcionario inserirFuncionario(Connection conn, Funcionario func) {
		String sqlInsert = "INSERT INTO funcionario(horarioEntrada, horarioSaida, id_usuario, id_empresa) VALUES (?, ?, ?, ?)";
		PreparedStatement stm = null;
		String[] horEnt = func.getHorarioEntrada().split(":");
		int horaE = Integer.parseInt(horEnt[0]);
		int minutoE = Integer.parseInt(horEnt[1]);

		String[] horSaida = func.getHorarioSaida().split(":");
		int horaS = Integer.parseInt(horSaida[0]);
		int minutoS = Integer.parseInt(horSaida[1]);

		
		try {
			stm = conn.prepareStatement(sqlInsert); // pre configura
			stm.setTime(1, new java.sql.Time(horaE, minutoE, 00));
			stm.setTime(2, new java.sql.Time(horaS, minutoS, 00));
			stm.setInt(3, func.getIdUsuario());
			stm.setInt(4, func.getIdEmpresaFK());
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
		return consultarFuncionario(conn, func);
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que consulta usuário no Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 * @return
	 */
	public Funcionario consultarFuncionario(Connection conn, Funcionario func) {
		String sqlSelect = "SELECT funcionario.* FROM usuario INNER JOIN funcionario ON usuario.id_usuario = funcionario.id_usuario WHERE usuario.id_usuario = ?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.prepareStatement(sqlSelect);
			stm.setInt(1, func.getIdUsuario());
			rs = stm.executeQuery();
			while (rs.next()) {
				func.setIdFuncionario(rs.getInt(1));
				func.setHorarioEntrada(rs.getTime(2) + "");
				func.setHorarioSaida(rs.getTime(3) + "");
				func.setIdUsuario(rs.getInt(4));
				func.setIdEmpresaFK(rs.getInt(5));
				conn.commit();
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
					return func;
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
				}
			}
		}
		return func;
	}
	/*
	public Funcionario consultarFuncionario(Connection conn, Funcionario func) {
		String sqlSelect = "SELECT usuario.*, funcionario.* FROM usuario INNER JOIN funcionario ON usuario.id_usuario = funcionario.id_usuario WHERE usuario.id_usuario = ?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.prepareStatement(sqlSelect);
			stm.setInt(1, func.getIdUsuario());
			rs = stm.executeQuery();
			if (rs.next()) {
				func.setIdUsuario(rs.getInt(1));
				func.setCpf(rs.getString(2));
				func.setNome(rs.getString(3));
				func.setLogin(rs.getString(4));
				func.setDataNascimento(rs.getDate(5) + "");
				func.setSexo(rs.getString(6).charAt(0));
				func.setEndereco(rs.getString(7));
				func.setCep(rs.getString(8));
				func.setTelefone(rs.getString(9));
				func.setTipoConta(rs.getInt(10));
				func.setTipoAcesso(rs.getInt(11));
				func.setIdFuncionario(rs.getInt(12));
				func.setHorarioEntrada(rs.getTime(13) + "");
				func.setHorarioSaida(rs.getTime(14) + "");
				func.setIdUsuarioFK(rs.getInt(15));
				func.setIdEmpresaFK(rs.getInt(16));
				conn.commit();
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
					return func;
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
				}
			}
		}
		return func;
	}
	*/
	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	// = = = = =
	/**
	 * Método que altera usuário no Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 * @return
	 */
	public void alterarFuncionario(Connection conn, Funcionario func) {
		String sqlUpdate = "UPDATE funcionario SET horarioEntrada = ?, horarioSaida = ?, id_empresa = ? WHERE id_usuario = "
				+ func.getIdUsuario();
		PreparedStatement stm = null;
		String[] horEnt = func.getHorarioEntrada().split(":");
		int horaE = Integer.parseInt(horEnt[0]);
		int minutoE = Integer.parseInt(horEnt[1]);

		String[] horSaida = func.getHorarioSaida().split(":");
		int horaS = Integer.parseInt(horSaida[0]);
		int minutoS = Integer.parseInt(horSaida[1]);
		try {
			stm = conn.prepareStatement(sqlUpdate); // pre configura
			stm.setTime(1, new java.sql.Time(horaE, minutoE, 00));
			stm.setTime(2, new java.sql.Time(horaS, minutoS, 00));
			stm.setInt(3, func.getIdEmpresaFK());
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
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	// = = = = =
	/**
	 * Método que exclui o usuário do Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 */
	public void excluirFuncionario(Connection conn, Funcionario func) {
		String sqlDelete = "DELETE FROM funcionario WHERE id_usuario = ?";
		PreparedStatement stm = null;
		try {
			stm = conn.prepareStatement(sqlDelete);
			stm.setInt(1, func.getIdUsuario());
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
