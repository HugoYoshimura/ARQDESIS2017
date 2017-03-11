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
		//							- Intanciação dos JLabels, JButtons e Fields -
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instanciação de labels "Login" e "Senha"
		loginLabel = new JLabel("Login: ");
		senhaLabel = new JLabel("Senha: ");
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instanciação do campo de preenchimento do login.
		loginField = new JTextField(10);
		
		// Adicionando ação ao campo de preenchimento do login que selecionará o texto todo quando for selecionado.
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
		// Instanciação do campo de preenchimento da senha 
		senhaField = new JPasswordField(10);
		
		// Adicionando ação ao campo de preenchimento da senha que selecionará o texto todo quando for selecionado.
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
		// Instanciação botão "OK".
		loginButton = new JButton("OK");
				
		// Adicionando ação de validação de login e senha ao botão "OK".
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
		// Instanciação do botão "Cancelar"
		cancelaButton = new JButton("Cancelar");
		
		// Adicionando ação de desistência de login ao botão "Cancelar".
		cancelaButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		//									- Instanciação dos JPanels -
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instanciação do JPanel input2Panel que terá os labels e campos de preenchimentos Login e Senha
		input2Panel = new JPanel();
		input2Panel.setLayout(new GridLayout(2, 2));
		 
		input2Panel.add(loginLabel);
		input2Panel.add(loginField);
		input2Panel.add(senhaLabel);
		input2Panel.add(senhaField);
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instanciação do JPanel inputPanel que terá o JPanel input2Panel que tem como objetivo centralizar os campos 
		inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		
		inputPanel.add(input2Panel, BorderLayout.CENTER);
		inputPanel.add(new JPanel(), BorderLayout.NORTH);
		inputPanel.add(new JPanel(), BorderLayout.SOUTH);
		inputPanel.add(new JPanel(), BorderLayout.EAST);
		inputPanel.add(new JPanel(), BorderLayout.WEST);
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instanciação do JPanel button2Panel que terá os botões "OK" e "Cancelar"
		button2Panel = new JPanel();
		button2Panel.setLayout(new GridLayout(1, 2, 10, 10));

		button2Panel.add(loginButton);
		button2Panel.add(cancelaButton);

		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instanciação do JPanel buttonPanel que terá o JPanel button2Panel que tem como objetivo centralizar os botões
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());

		buttonPanel.add(button2Panel, BorderLayout.CENTER);
		buttonPanel.add(new JPanel(), BorderLayout.SOUTH);
		buttonPanel.add(new JPanel(), BorderLayout.EAST);
		buttonPanel.add(new JPanel(), BorderLayout.WEST);
		
		
		// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
		// Instanciação do JPanel mainPanel que terá os JPanels inputPanel e buttonPanel
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
