package View;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TelaMensagem extends JDialog implements ActionListener {
	
	private JLabel mensagemLB;
	private JButton confirmBT;
	private boolean confirm;
	private ResourceBundle bn;
	
	public TelaMensagem(ResourceBundle bn, JFrame frame, int modo) {
		super(frame, true);
		this.bn = bn;
		
		super.setTitle(bn.getString("telaMensagem.label.titulo"));
		
		
		Container container = getContentPane();
		container.setLayout(new BorderLayout(5, 5));
		if(modo == 0){
			mensagemLB = new JLabel(bn.getString("telaMensagem.label.mensagem0"), SwingConstants.CENTER);
		} else if(modo == 1){
			mensagemLB = new JLabel(bn.getString("telaMensagem.label.mensagem1"), SwingConstants.CENTER);
		} else if(modo == 2) {
			mensagemLB = new JLabel(bn.getString("telaMensagem.label.mensagem2"), SwingConstants.CENTER);
		} else if(modo == 3) {
			mensagemLB = new JLabel(bn.getString("telaMensagem.label.mensagem3"), SwingConstants.CENTER);
		} else if(modo == 4) {
			mensagemLB = new JLabel(bn.getString("telaMensagem.label.mensagem4"), SwingConstants.CENTER);
		} else if(modo == 5) {
			mensagemLB = new JLabel(bn.getString("telaMensagem.label.mensagem5"), SwingConstants.CENTER);
		} else if(modo == 6) {
			mensagemLB = new JLabel(bn.getString("telaMensagem.label.mensagem6"), SwingConstants.CENTER);
		} else if(modo == 7) {
			mensagemLB = new JLabel(bn.getString("telaMensagem.label.mensagem7"), SwingConstants.CENTER);
		} else if(modo == 8) {
			mensagemLB = new JLabel(bn.getString("telaMensagem.label.mensagem8"), SwingConstants.CENTER);
		} else if(modo == 9) {
			mensagemLB = new JLabel(bn.getString("telaMensagem.label.mensagem9"), SwingConstants.CENTER);
		}
		
		confirmBT = new JButton(bn.getString("telaMensagem.button.confirm"));
		confirmBT.addActionListener(this);
		
		JPanel pnButtons = new JPanel(new GridLayout(1, 3, 5, 5));
		pnButtons.add(new JLabel(""));
		pnButtons.add(confirmBT);
		pnButtons.add(new JLabel(""));
		
		
		add(mensagemLB, BorderLayout.CENTER);
		add(pnButtons, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(250, 100); // set frame size
		setLocationRelativeTo(null);
		setVisible(true); 
	}
	
	static boolean confirmacao(ResourceBundle bn, JFrame frame, int modo) {
		TelaMensagem tela = new TelaMensagem(bn, frame, modo);
		return tela.confirm;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == confirmBT) {
			confirm = true;
			dispose();
		}
	}
}
