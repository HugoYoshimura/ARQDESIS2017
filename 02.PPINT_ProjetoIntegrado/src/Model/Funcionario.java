package Model;

import java.sql.SQLException;

import DAO.ConexaoBD;
import DAO.FuncionarioDAO;
import DAO.UsuarioDAO;


/** Classe para objetos tipo Funcion�rio, onde ser�o contidos, valores e m�todos para o mesmo.
 * 
 * @author Hugo M. Yoshimura 	RA:201505661
 * @author Jos� R. A. Wanderley	RA: 201416062
 * @version 1.0 
 */

public class Funcionario extends Usuario {
	protected int idFuncionario;
	protected String horarioEntrada;
	protected String horarioSaida;
	protected int idEmpresaFK;
	protected FuncionarioDAO fdao;
	
	
	public Funcionario() {
		super();
		idFuncionario = 0;
		horarioEntrada = null;
		horarioSaida = null;
		idEmpresaFK = 0;
	}
	
	public Funcionario(String cpf, String nome, String login, String senha, String dataNascimento, String sexo, String endereco,
			String cep, String telefone, int tipoConta, int tipoAcesso, String horarioEntrada, String horarioSaida, int idEmpresa) {
		super(cpf, nome, login, senha, dataNascimento, sexo, endereco, cep, telefone, tipoConta, tipoAcesso);
		this.horarioEntrada = horarioEntrada;
		this.horarioSaida = horarioSaida;
		this.idEmpresaFK = idEmpresa;
	}
	
	public void setUsuarioAtributos(Usuario user) {
		this.idUsuario = user.getIdUsuario();
		this.cpf = user.getCpf();
		this.nome = user.getNome();
		this.login = user.getLogin();
		this.dataNascimento = user.getDataNascimento();
		this.sexo = user.getSexo();
		this.endereco = user.getEndereco();
		this.cep = user.getCep();
		this.telefone = user.getTelefone();
		this.tipoConta = user.getTipoConta();
		this.tipoAcesso = user.getTipoAcesso();
	}
	
	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
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

	public int getIdEmpresaFK() {
		return idEmpresaFK;
	}

	public void setIdEmpresaFK(int idEmpresa) {
		this.idEmpresaFK = idEmpresa;
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		/**
		 * M�todo que insere usu�rio no Banco de Dados
		 * 
		 * @param user
		 */
		public void inserirFuncionario(Funcionario func) {
			fdao = new FuncionarioDAO();
			bd = new ConexaoBD();
			try {
				conn = bd.obtemConexao();
				conn.setAutoCommit(false);
				fdao.inserirFuncionario(conn, func);
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		/**
		 * M�todo que consulta usu�rio no Banco de Dados
		 * 
		 * @param user
		 */
		public Funcionario consultarFuncionario(Funcionario func) {
			fdao = new FuncionarioDAO();
			bd = new ConexaoBD();
			Funcionario funcdao = new Funcionario();
			try {
				conn = bd.obtemConexao();
				conn.setAutoCommit(false);
				funcdao = fdao.consultarFuncionario(conn, func);
				conn.commit();
				return funcdao;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return funcdao;
		}

		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		/**
		 * M�todo que consulta usu�rio no Banco de Dados
		 * 
		 * @param user
		 */
		public void alterarUsuario(Funcionario func) {
			fdao = new FuncionarioDAO();
			bd = new ConexaoBD();
			Funcionario funcdao = new Funcionario();
			try {
				conn = bd.obtemConexao();
				conn.setAutoCommit(false);
				fdao.alterarFuncionario(conn, func);
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		/**
		 * M�todo que exclui usu�rio no Banco de Dados
		 * 
		 * @param user
		 */
		public void excluirFuncionario(Funcionario func) {
			fdao = new FuncionarioDAO();
			bd = new ConexaoBD();
			try {
				conn = bd.obtemConexao();
				conn.setAutoCommit(false);
				fdao.excluirFuncionario(conn, func);
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	
	


	public void alterarTemperatura() {
		
	}
}
