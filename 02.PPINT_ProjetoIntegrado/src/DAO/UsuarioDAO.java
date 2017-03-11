package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import Model.Usuario;
import View.TelaLogin;
import View.TelaUsuario;
import javafx.util.converter.DateTimeStringConverter;

public class UsuarioDAO {
	private String cpf, nome, login, endereco;
	private Date dataNascimento;
	private int tipoConta; // Funcionário (1) / Atendente (2) / Síndico (3)
	private int tipoAcesso; // Acesso ao prédio livre (1) ou restrito (2)
	private TelaLogin telaLogin;
	private TelaUsuario telaUser;
	private Usuario user;
	private DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	public UsuarioDAO() {

	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(int tipoConta) {
		this.tipoConta = tipoConta;
	}

	public int getTipoAcesso() {
		return tipoAcesso;
	}

	public void setTipoAcesso(int tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}

	public TelaLogin getTelaLogin() {
		return telaLogin;
	}

	public void setTelaLogin(TelaLogin telaLogin) {
		this.telaLogin = telaLogin;
	}

	public TelaUsuario getTelaUser() {
		return telaUser;
	}

	public void setTelaUser(TelaUsuario telaUser) {
		this.telaUser = telaUser;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	/**
	 * Método que insere novo usuário no Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 */
	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	public Usuario inserirUsuario(Connection conn, Usuario user) {
		String sqlInsert = "INSERT INTO usuario(cpf, nome, login, dataNascimento, sexo, endereco, cep, telefone, tipoConta, tipoAcesso) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stm = null;
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = f.parse(user.getDataNascimento());
			try {
				stm = conn.prepareStatement(sqlInsert); // pre configura
				stm.setString(1, user.getCpf());
				stm.setString(2, user.getNome());
				stm.setString(3, user.getLogin());
				stm.setDate(4, new java.sql.Date(date.getTime()));
				stm.setString(5, user.getSexo());
				stm.setString(6, user.getEndereco());
				stm.setString(7, user.getCep());
				stm.setString(8, user.getTelefone());
				stm.setInt(9, user.getTipoConta());
				stm.setInt(10, user.getTipoAcesso());
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
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		return consultarUsuario(conn, user);
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que consulta usuário no Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 * @return
	 */
	public Usuario consultarUsuario(Connection conn, Usuario user) {
		String sqlSelect = "SELECT * FROM usuario WHERE id_usuario = ?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.prepareStatement(sqlSelect);
			stm.setInt(1, user.getIdUsuario());
			rs = stm.executeQuery();
			while (rs.next()) {
				user.setIdUsuario(rs.getInt(1));
				user.setCpf(rs.getString(2));
				user.setNome(rs.getString(3));
				user.setLogin(rs.getString(4));
				user.setDataNascimento(rs.getDate(5) + "");
				user.setSexo(rs.getString(6));
				user.setEndereco(rs.getString(7));
				user.setCep(rs.getString(8));
				user.setTelefone(rs.getString(9));
				user.setTipoConta(rs.getInt(10));
				user.setTipoAcesso(rs.getInt(11));
				conn.commit();
				user.setExist(1);
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
					return user;
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
				}
			}
		}
		return user;
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que consulta usuário no Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 * @return
	 */
	public Usuario consultarUsuarioCpf(Connection conn, Usuario user) {
		String sqlSelect = "SELECT * FROM usuario WHERE cpf = ? AND nome = ?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.prepareStatement(sqlSelect);
			stm.setString(1, user.getCpf());
			stm.setString(2, user.getNome());
			rs = stm.executeQuery();
			while (rs.next()) {
				user.setIdUsuario(rs.getInt(1));
				user.setCpf(rs.getString(2));
				user.setNome(rs.getString(3));
				user.setLogin(rs.getString(4));
				user.setDataNascimento(rs.getDate(5) + "");
				user.setSexo(rs.getString(6));
				user.setEndereco(rs.getString(7));
				user.setCep(rs.getString(8));
				user.setTelefone(rs.getString(9));
				user.setTipoConta(rs.getInt(10));
				user.setTipoAcesso(rs.getInt(11));
				conn.commit();
				user.setExist(1);
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
					return user;
				} catch (SQLException e1) {
					System.out.print(e1.getStackTrace());
				}
			}
		}
		return user;
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
	public Usuario alterarUsuario(Connection conn, Usuario user) {
		String sqlUpdate = "UPDATE usuario SET nome = ?, dataNascimento = ?, sexo = ?, endereco = ?, cep = ?, telefone = ?, tipoConta = ?, tipoAcesso = ? WHERE id_usuario = "
				+ user.getIdUsuario();
		PreparedStatement stm = null;
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = f.parse(user.getDataNascimento());
			try {
				stm = conn.prepareStatement(sqlUpdate); // pre configura
				stm.setString(1, user.getNome());
				stm.setDate(2, new java.sql.Date(date.getTime()));
				stm.setString(3, String.valueOf(user.getSexo()));
				stm.setString(4, user.getEndereco());
				stm.setString(5, user.getCep());
				stm.setString(6, user.getTelefone());
				stm.setInt(7, user.getTipoConta());
				stm.setInt(8, user.getTipoAcesso());
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
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
			return consultarUsuario(conn, user);
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que exclui o usuário do Banco de Dados
	 * 
	 * @param conn
	 * @param user
	 */
	public void excluirUsuario(Connection conn, Usuario user) {
		String sqlDelete = "DELETE FROM usuario WHERE id_usuario = ?";
		PreparedStatement stm = null;
		try {
			stm = conn.prepareStatement(sqlDelete);
			stm.setInt(1, user.getIdUsuario());
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
