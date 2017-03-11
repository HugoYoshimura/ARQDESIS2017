package Controller;

import javax.swing.JFrame;

import Model.Usuario;
import View.TelaLogin;
import View.TelaPrincipal;

@SuppressWarnings("serial")
public class LoginCtrl extends TelaLogin {

	private String login, senha;
	private int tipoConta;
	private TelaLogin telaLogin;

	public LoginCtrl() {
		login = "";
		senha = "";
		tipoConta = 0;
		telaLogin = new TelaLogin();
	}


	// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	// Métodos gets e sets
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

	public int getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(int tipoConta) {
		this.tipoConta = tipoConta;
	}

	public TelaLogin getTelaLogin() {
		return telaLogin;
	}

	public void setTelaLogin(TelaLogin telaLogin) {
		this.telaLogin = telaLogin;
	}

	/** = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	 * Método que chama método validar login da classe Usuario
	 * @param login
	 * @param senha
	 * @return
	 * @throws Exception
	 */
	public int validarLogin(String login, String senha) throws Exception {
		Usuario user = new Usuario();
		return user.validarSenhaRSA(login, senha);
	}

	/** = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	 * Método que chama tela principal
	 */
	public void chamarTelaPrincipal(int type) {
		TelaPrincipal principal = new TelaPrincipal(type);
		principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		principal.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//principal.setSize(1024, 768); // set frame size
		principal.setVisible(true); // display frame
		principal.setLocationRelativeTo(null);
	}
}
