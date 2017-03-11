package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Controller.LoginCtrl;

@SuppressWarnings("serial")
public class TelaLogin extends JFrame implements ActionListener {

	private JButton loginButton, cancelaButton;
	private JTextField loginField;
	private JPasswordField senhaField;
	private JLabel loginLabel, senhaLabel;
	private String mensagem;
	private JPanel mainPanel, inputPanel, input2Panel, buttonPanel, button2Panel;
	private LoginCtrl loginCtrl;

	// Construtor sem parametros para configurar a GUI
	public TelaLogin() {
		super("Login");

		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		//							- Intancia��o dos JLabels, JButtons e Fields -
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instancia��o de labels "Login" e "Senha"
		loginLabel = new JLabel("Login: ");
		senhaLabel = new JLabel("Senha: ");
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instancia��o do campo de preenchimento do login.
		loginField = new JTextField(10);
		
		// Adicionando a��o ao campo de preenchimento do login que selecionar� o texto todo quando for selecionado.
		loginField.addFocusListener(new java.awt.event.FocusAdapter() {
		    public void focusGained(java.awt.event.FocusEvent evt) {
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                loginField.selectAll();
		            }
		        });
		    }
		});
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instancia��o do campo de preenchimento da senha 
		senhaField = new JPasswordField(10);
		
		// Adicionando a��o ao campo de preenchimento da senha que selecionar� o texto todo quando for selecionado.
		senhaField.addFocusListener(new java.awt.event.FocusAdapter() {
		    public void focusGained(java.awt.event.FocusEvent evt) {
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                senhaField.selectAll();
		            }
		        });
		    }
		});
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instancia��o bot�o "OK".
		loginButton = new JButton("OK");
				
		// Adicionando a��o de valida��o de login e senha ao bot�o "OK".
		loginButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				loginCtrl = new LoginCtrl();
				String senha = "";
				for(int i = 0; i < senhaField.getPassword().length; i++){
					senha += senhaField.getPassword()[i];
				}
				
				try {
					int tipo = loginCtrl.validarLogin(loginField.getText(), senha);
					if (tipo == 0) {
						ResourceBundle bn = ResourceBundle.getBundle("tela", new Locale("pt", "BR"));
						JFrame frame = new JFrame();
						TelaMensagem.confirmacao(bn, frame, 0);
						//loginField.setText("");
						loginField.grabFocus();
						senhaField.setText("");
					} else {
						loginCtrl.chamarTelaPrincipal(tipo);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instancia��o do bot�o "Cancelar"
		cancelaButton = new JButton("Cancelar");
		
		// Adicionando a��o de desist�ncia de login ao bot�o "Cancelar".
		cancelaButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		//									- Instancia��o dos JPanels -
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instancia��o do JPanel input2Panel que ter� os labels e campos de preenchimentos Login e Senha
		input2Panel = new JPanel();
		input2Panel.setLayout(new GridLayout(2, 2));
		 
		input2Panel.add(loginLabel);
		input2Panel.add(loginField);
		input2Panel.add(senhaLabel);
		input2Panel.add(senhaField);
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instancia��o do JPanel inputPanel que ter� o JPanel input2Panel que tem como objetivo centralizar os campos 
		inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		
		inputPanel.add(input2Panel, BorderLayout.CENTER);
		inputPanel.add(new JPanel(), BorderLayout.NORTH);
		inputPanel.add(new JPanel(), BorderLayout.SOUTH);
		inputPanel.add(new JPanel(), BorderLayout.EAST);
		inputPanel.add(new JPanel(), BorderLayout.WEST);
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instancia��o do JPanel button2Panel que ter� os bot�es "OK" e "Cancelar"
		button2Panel = new JPanel();
		button2Panel.setLayout(new GridLayout(1, 2, 10, 10));

		button2Panel.add(loginButton);
		button2Panel.add(cancelaButton);

		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instancia��o do JPanel buttonPanel que ter� o JPanel button2Panel que tem como objetivo centralizar os bot�es
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());

		buttonPanel.add(button2Panel, BorderLayout.CENTER);
		buttonPanel.add(new JPanel(), BorderLayout.SOUTH);
		buttonPanel.add(new JPanel(), BorderLayout.EAST);
		buttonPanel.add(new JPanel(), BorderLayout.WEST);
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instancia��o do JPanel mainPanel que ter� os JPanels inputPanel e buttonPanel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.add(inputPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// fixando o mainPanel no JFrame
		setContentPane(mainPanel);
		
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

}
