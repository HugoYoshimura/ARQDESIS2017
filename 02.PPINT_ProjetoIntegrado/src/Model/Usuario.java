package Model;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import DAO.ConexaoBD;
import DAO.UsuarioDAO;

public class Usuario {
	protected int idUsuario;
	protected String cpf;
	protected String nome;
	protected String login;
	protected String senha;
	protected String dataNascimento;
	protected String sexo;
	protected String endereco;
	protected String cep;
	protected String telefone;
	protected int tipoConta; // Funcionário / Funcionário CAC / Atendente /
								// Síndico
	protected int tipoAcesso; // Acesso ao prédio livre(1) ou restrito(2)
	protected int exist;
	protected byte[] senhaByteClara;
	protected byte[] senhaByteDecryp;
	protected byte[] senhaByteCryp;
	protected CriarArquivo criarFile;
	protected ConsultarArquivo consultarFile;
	protected ArrayList<Usuario> users;
	protected ConexaoBD bd;
	protected Connection conn = null;
	protected UsuarioDAO udao;

	/**
	 * Método construtor padrão
	 */
	public Usuario() {
		idUsuario = 0;
		cpf = null;
		nome = null;
		login = null;
		senha = null;
		dataNascimento = null;
		endereco = null;
		cep = null;
		telefone = null;
		tipoConta = 0;
		tipoAcesso = 0;
		senhaByteCryp = null;
		senhaByteDecryp = null;
		users = new ArrayList<Usuario>();

	}

	/**
	 * 
	 * @param cpf
	 * @param nome
	 * @param login
	 * @param senha
	 * @param dataNascimento
	 * @param endereco
	 * @param tipoConta
	 * @param tipoAcesso
	 */
	public Usuario(String cpf, String nome, String login, String senha,
			String dataNascimento, String sexo, String endereco, String cep,
			String telefone, int tipoConta, int tipoAcesso) {
		this.cpf = cpf;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.endereco = endereco;
		this.cep = cep;
		this.telefone = telefone;
		this.tipoConta = tipoConta;
		this.tipoAcesso = tipoAcesso;
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	// Métodos gets e sets
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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

	public int getExist() {
		return exist;
	}

	public void setExist(int exist) {
		this.exist = exist;
	}

	public void gerarChaveRSA() throws Exception {
		// Instancia um objeto da classe CryptoRSA
		CryptoRSA crsa = new CryptoRSA();
		// Gera as Chaves criptografica RSA publica e privada e os arquivos onde
		// armazenar
		crsa.geraParDeChaves(new File("src/chave.publica"), new File(
				"src/chave.privada"));
		// Gera a cifra RSA da mensagem dada, segundo a chave publica gerada

	}

	public void cadastrarSenhaRSA(Usuario user) throws Exception {
		// Instancia um objeto da classe CryptoRSA
		CryptoRSA crsa = new CryptoRSA();

		senhaByteClara = user.getSenha().getBytes("ISO-8859-1");
		// Gera as Chaves criptografica RSA publica e privada e os arquivos onde
		// armazenar
		crsa.geraCifra(senhaByteClara, new File("src/chave.publica"));
		// Recebe o texto cifrado
		senhaByteCryp = crsa.getTextoCifrado();
		// Converte o texto byte[] no equivalente String
		user.setSenha(new String(senhaByteCryp, "ISO-8859-1"));

		users = listaUsuarios();
		users.add(user);

		criarFile = new CriarArquivo();
		criarFile.abrirArquivo();
		criarFile.adicionarRegistros(users);
		criarFile.fecharArquivo();

	}

	public int validarSenhaRSA(String login, String senha) throws Exception {
		consultarFile = new ConsultarArquivo();
		consultarFile.abrirArquivo();
		consultarFile.consultarRegistros();
		ArrayList<Usuario> users = new ArrayList<Usuario>();
		users = consultarFile.getUsuarios();
		consultarFile.quickSort(users);
		int pos = consultarFile.binary(users, login);
		if (pos >= 0) {
			Usuario informado = users.get(pos);

			// Instancia um objeto da classe CryptoRSA
			CryptoRSA crsa = new CryptoRSA();

			senhaByteClara = senha.getBytes("ISO-8859-1");
			// Gera as Chaves criptografica RSA publica e privada e os arquivos onde
			// armazenar
			crsa.geraCifra(senhaByteClara, new File("src/chave.publica"));
			// Recebe o texto cifrado
			senhaByteCryp = crsa.getTextoCifrado();
			// Converte o texto byte[] no equivalente String
			String senhaTela = (new String(senhaByteCryp, "ISO-8859-1"));
			
			
			// Gera a decifra RSA da mensagem dada, segundo a chave privada
			// gerada
			crsa.geraDecifra(senhaByteCryp, new File("src/chave.privada"));
			// recebe o texto decifrado
			senhaByteClara = crsa.getTextoDecifrado();
			// Converte o texto byte[] no equivalente String
			informado.setSenha(new String(senhaByteClara, "ISO-8859-1"));
			 
			
			if (informado.getSenha().equals(senha)) {
				if (informado.getLogin().equals(login)) {
					return informado.getTipoConta();
				} else {
					return 0;
				}
			} else {
				return 0;
			}
			
		} else
			return 0;
			
		}


	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que gera chave para criptografar e decriptografar (AES).
	 * 
	 * @throws Exception
	 */

	public void gerarChaveAES() throws Exception {
		// Instancia um objeto da classe CryptoAES
		CryptoAES caes = new CryptoAES(); 
		// Gera a Chave criptografica AES simetrica e o nome do arquivo onde será armazenada
		caes.geraChave(new File("src/chave.simetrica"));
		// Gera a cifra AES da mensagem dada, com a chave simetrica dada
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que criptografa(AES) a senha do usuário e armazena no arquivo txt.
	 * 
	 * @param cpf
	 * @param login
	 * @param senha
	 * @param tipoConta
	 * @throws Exception
	 */
	public void cadastrarSenhaAES(Usuario user) throws Exception {
		senhaByteDecryp = user.getSenha().getBytes("ISO-8859-1");
		// Instancia um objeto da classe CryptoAES
		CryptoAES caes = new CryptoAES();
		// Gera a cifra AES da mensagem dada, com a chave simetrica dada
		caes.geraCifra(senhaByteDecryp, new File("src/chave.simetrica"));
		// Recebe o texto cifrado
		senhaByteCryp = caes.getTextoCifrado();
		// Converte o texto byte[] no equivalente String
		user.setSenha(new String(senhaByteCryp, "ISO-8859-1"));

		consultarFile = new ConsultarArquivo();
		consultarFile.abrirArquivo();
		consultarFile.consultarRegistros();
		ArrayList<Usuario> users = new ArrayList<Usuario>();
		users = consultarFile.getUsuarios();
		consultarFile.quickSort(users);
		int pos = consultarFile.binary(users, login);
		users = listaUsuarios();
		if (pos >= 0) {
			users.remove(pos);
		}
		
		users.add(user);

		criarFile = new CriarArquivo();
		criarFile.abrirArquivo();
		criarFile.adicionarRegistros(users);
		criarFile.fecharArquivo();
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que valida login e senha dado por parâmetro consultando e
	 * descriptografando(AES) a senha do arquivo txt.
	 * 
	 * @param login
	 * @param senha
	 * @return se login e senha forem iguais aos parametros, retornar true caso
	 *         contrário false.
	 * @throws Exception
	 */
	public boolean validarSenhaAES(String login, String senha) throws Exception {
		consultarFile = new ConsultarArquivo();
		consultarFile.abrirArquivo();
		consultarFile.consultarRegistros();
		ArrayList<Usuario> users = new ArrayList<Usuario>();
		users = consultarFile.getUsuarios();
		consultarFile.quickSort(users);
		int pos = consultarFile.binary(users, login);
		if (pos >= 0) {
			Usuario informado = users.get(pos);

			CryptoAES caes = new CryptoAES();
			/*
			 * senhaByteCryp = informado.getSenha().getBytes("ISO-8859-1");
			 * caes.geraDecifra(senhaByteCryp, new File("src/chave.simetrica"));
			 * senhaByteDecryp = caes.getTextoDecifrado();
			 * informado.setSenha((new String(senhaByteDecryp, "ISO-8859-1")));
			 * /
			 */
			senhaByteDecryp = senha.getBytes("ISO-8859-1");
			caes.geraCifra(senhaByteDecryp, new File("src/chave.simetrica"));
			// Recebe o texto cifrado
			senhaByteCryp = caes.getTextoCifrado();
			// Converte o texto byte[] no equivalente String
			this.senha = (new String(senhaByteCryp, "ISO-8859-1"));

			// "ISO-8859-1"
			byte[] teste = informado.getSenha().getBytes("ISO-8859-1");

			for (int i = 0; i < teste.length; i++) {
				System.out.println(teste[i]);
			}
			for (int i = 0; i < senhaByteCryp.length; i++) {
				System.out.println(senhaByteCryp[i]);
			}

			boolean validacao = true;
			if (informado.getSenha().equals(this.senha)) {
				if (informado.getLogin().equals(login)) {
					validacao = true;
				} else {
					validacao = false;
				}
			} else {
				validacao = false;
			}
			return validacao;
		} else
			return false;
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que insere usuário no Banco de Dados
	 * 
	 * @param user
	 */
	public void inserirUsuario(Usuario user) {
		udao = new UsuarioDAO();
		bd = new ConexaoBD();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			udao.inserirUsuario(conn, user);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que consulta usuário no Banco de Dados
	 * 
	 * @param user
	 * @param modo
	 *            Se é 0, consultar por CPF e nome caso contrário pelo ID do
	 *            usuário
	 * @return
	 */
	public Usuario consultarUsuario(Usuario user, int modo) {
		udao = new UsuarioDAO();
		bd = new ConexaoBD();
		Usuario userdao = new Usuario();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			if (modo == 0) {
				userdao = udao.consultarUsuarioCpf(conn, user);
			} else {
				userdao = udao.consultarUsuario(conn, user);
			}
			conn.commit();
			return userdao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userdao;
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que consulta usuário no Banco de Dados
	 * 
	 * @param user
	 */
	public void alterarUsuario(Usuario user) {
		udao = new UsuarioDAO();
		bd = new ConexaoBD();
		Usuario userdao = new Usuario();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			udao.alterarUsuario(conn, user);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que exclui usuário no Banco de Dados
	 * 
	 * @param user
	 */
	public void excluirUsuario(Usuario user) {
		udao = new UsuarioDAO();
		bd = new ConexaoBD();
		try {
			conn = bd.obtemConexao();
			conn.setAutoCommit(false);
			udao.excluirUsuario(conn, user);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que retorna lista de usuários salvos no arquivo txt.
	 * 
	 * @return
	 * @throws IOException
	 */
	public ArrayList<Usuario> listaUsuarios() throws IOException {
		consultarFile = new ConsultarArquivo();
		consultarFile.abrirArquivo();
		consultarFile.consultarRegistros();
		ArrayList<Usuario> users = consultarFile.getUsuarios();
		return users;
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que imprime informações de um usuario
	 * 
	 * @param user
	 * @return
	 */
	public String printUsuario(Usuario user) {
		String print = user.getCpf() + " " + user.getLogin() + " "
				+ user.getTipoConta();
		return print;
	}

	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	/**
	 * Método que imprime informações de um usuário de posição pos
	 * 
	 * @param users
	 * @param i
	 * @return
	 */
	public String printUsuarios(ArrayList<Usuario> users, int pos) {
		String print = "";
		print = printUsuario(users.get(pos));
		return print;
	}
}
