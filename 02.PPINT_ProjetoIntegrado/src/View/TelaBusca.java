package View;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

public class TelaBusca extends JDialog implements ActionListener {

	private static int idUsuario;
	private JLabel mensagemLB, idUsuarioLB;
	private MaskFormatter idUsuarioMF;
	private JFormattedTextField idUsuarioJFT;
	private JButton confirmBT, cancelBT;
	private boolean confirm;
	private ResourceBundle bn;

	public TelaBusca(ResourceBundle bn, JFrame frame, int modo) {
		super(frame, true);
		this.bn = bn;
		super.setTitle(bn.getString("telaBusca.label.titulo"));

		Container container = getContentPane();
		container.setLayout(new BorderLayout(5, 5));

		JPanel pnCampos = new JPanel(new BorderLayout());
		JPanel pnSubcampos = new JPanel(new GridLayout(2, 1, 5, 5));

		if (modo == 1) {
			mensagemLB = new JLabel(bn.getString("telaBusca.label.mensagem1"),
					SwingConstants.CENTER);
		} else {
			mensagemLB = new JLabel(bn.getString("telaBusca.label.mensagem2"),
					SwingConstants.CENTER);
		}

		idUsuarioLB = new JLabel(bn.getString("telaBusca.label.id"));

		try {
			idUsuarioMF = new MaskFormatter("####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		idUsuarioMF.setPlaceholder("0000");

		idUsuarioJFT = new JFormattedTextField(idUsuarioMF);

		pnSubcampos.add(idUsuarioLB);
		pnSubcampos.add(idUsuarioJFT);

		pnCampos.add(mensagemLB, BorderLayout.NORTH);
		pnCampos.add(pnSubcampos, BorderLayout.CENTER);

		confirmBT = new JButton(bn.getString("telaBusca.button.confirm"));
		confirmBT.addActionListener(this);

		cancelBT = new JButton(bn.getString("telaBusca.button.cancel"));
		cancelBT.addActionListener(this);

		JPanel pnButtons = new JPanel(new GridLayout(1, 2, 5, 5));
		pnButtons.add(confirmBT);
		pnButtons.add(cancelBT);

		add(pnCampos, BorderLayout.CENTER);
		add(pnButtons, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(250, 150); // set frame size
		setLocationRelativeTo(null);
		setVisible(true);
	}

	static int busca(ResourceBundle bn, JFrame frame, int modo) {
		TelaBusca tela = new TelaBusca(bn, frame, modo);
		return idUsuario;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JFrame frame = new JFrame();
		if (event.getSource() == confirmBT) {
			if (idUsuarioJFT.getText().equals("____")
					|| Integer.parseInt(idUsuarioJFT.getText()) < 1000) {
				TelaMensagem.confirmacao(bn, frame, 3);
			} else {
				idUsuario = Integer.parseInt(idUsuarioJFT.getText());
				dispose();
			}
		}
		if (event.getSource() == cancelBT) {
			dispose();
		}
	}
}
