package Controller;

import java.util.Locale;
import java.util.ResourceBundle;

import Model.Funcionario;
import Model.Usuario;
import View.TelaUsuario;

public class UsuarioCtrl {

	private String cpf; // CHAR(14) > CHAR(11)
	private String nome; // VARCHAR(45)
	private String dataNascimento; // dd/MM/yyyy
	private char sexo; // M / F
	private String endereco; // VARCHAR(60)
	private String cep; // CHAR(9) > CHAR(8)
	private String telefone; // CHAR(14) > CHAR(10)
	private int tipoConta; // Funcionário(1) / Funcionário CAC(2) / Atendente(3)
							// / Síndico(4)
	private int tipoAcesso; // Restrito(1) / Livre(2)
	private String cnpj; // Se Funcionário CHAR(18) > CHAR(14)
	private String horEntrada;
	private String horSaida;

	private Usuario user;
	private Funcionario func;

	private String login;
	private String senha;

	public UsuarioCtrl() {
		user = new Usuario();
		func = new Funcionario();
	}

	public UsuarioCtrl(String cpf, String nome, String dataNascimento, char sexo, String endereco, String cep,
			String telefone, int tipoConta, int tipoAcesso, String cnpj, String horEntrada, String horSaida) {

	}

	public Funcionario getFuncionario() {
		return func;
	}

	public Usuario getUsuario() {
		return user;
	}

	public Usuario cadastrarUsuario(Usuario user) {
		user.inserirUsuario(user);
		this.user = user.consultarUsuario(user, 0);
		try {
			user.cadastrarSenhaRSA(this.user);
			return this.user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void cadastrarUsuario(Funcionario func) {
		func.inserirFuncionario(func);
	}

	public int consultarUsuario(int idUsuario) {
		user.setIdUsuario(idUsuario);
		this.user = user.consultarUsuario(user, 1);
		if (user.getExist() == 0) {
			return 3;
		} else {
			if (user.getTipoConta() == 1 || user.getTipoConta() == 2) {
				func.setIdUsuario(idUsuario);
				this.func = func.consultarFuncionario(func);
				func.setUsuarioAtributos(this.user);
				return 1;
			} else {
				return 0;
			}
		}
	}

	public boolean consultarFuncionario(int idUsuario) {
		func.setIdUsuario(idUsuario);
		this.func = func.consultarFuncionario(func);
		return true;
	}

	public void alterarUsuario(Usuario user) {
		user.alterarUsuario(user);
	}

	public void alterarUsuario(Funcionario func) {
		func.alterarUsuario(func);
	}

	public void removerUsuario(int idUsuario) {
		user.setIdUsuario(idUsuario);
		user.excluirUsuario(user);
	}
	
	public void removerFuncionario(int idUsuario) {
		func.setIdUsuario(idUsuario);
		func.excluirFuncionario(func);
	}
}
