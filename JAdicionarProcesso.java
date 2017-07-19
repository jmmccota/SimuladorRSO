package simuladorrso;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JAdicionarProcesso {

	static JFrame jAdicionar;
	static Object[] nl;
	JTextField tfProcess;
	JTextField tfTArr;
	JTextField tfTExec;
	JTextField tfPrioridade;
	JLabel lbProcess;
	JLabel lbTArr;
	JLabel lbTExec;
	JLabel lbPrioridade;

	// cria janela
	public JAdicionarProcesso() {
		Main.p++;

		// inicializa janela
		jAdicionar = new JFrame();

		// define propriedades
		jAdicionar.setTitle("Adicionar Processo");
		jAdicionar.getContentPane().setLayout(null);
		jAdicionar.setSize(240, 250);
		jAdicionar.setLocationRelativeTo(null);

		// cria componentes
		lbProcess = new JLabel("Processo");
		lbTArr = new JLabel("Tempo de Chegada");
		lbTExec = new JLabel("Tempo de Execucao");
		lbPrioridade = new JLabel("Prioridade");

		tfProcess = new JTextField();
		tfTArr = new JTextField();
		tfTArr.setText("0");
		tfTExec = new JTextField();
		tfTExec.setText("0");
		tfPrioridade = new JTextField();
		tfPrioridade.setText("1");

		JButton btOK = new JButton("OK");
		btOK.setMnemonic('O');
		JButton btCancel = new JButton("CANCELAR");
		btCancel.setMnemonic('C');
		// define propriedade dos componentes
		lbProcess.setBounds(10, 11, 100, 20);
		lbTArr.setBounds(10, 50, 150, 20);
		lbTExec.setBounds(10, 81, 150, 20);
		lbPrioridade.setBounds(10, 124, 100, 20);

		tfProcess.setBounds(135, 6, 80, 30);
		tfTArr.setBounds(135, 42, 80, 30);
		tfTExec.setBounds(135, 81, 80, 30);
		tfPrioridade.setBounds(135, 119, 80, 30);

		btOK.setBounds(10, 170, 100, 30);
		btCancel.setBounds(114, 170, 100, 30);

		// define processo ("P"+var)
		tfProcess.setEditable(false);
		tfProcess.setText("P" + Main.p);

		// adiciona a janela
		jAdicionar.getContentPane().add(lbProcess);
		jAdicionar.getContentPane().add(lbTArr);
		jAdicionar.getContentPane().add(lbTExec);
		jAdicionar.getContentPane().add(lbPrioridade);
		jAdicionar.getContentPane().add(tfProcess);
		jAdicionar.getContentPane().add(tfTArr);
		jAdicionar.getContentPane().add(tfTExec);
		jAdicionar.getContentPane().add(tfPrioridade);
		jAdicionar.getContentPane().add(btOK);
		jAdicionar.getContentPane().add(btCancel);
		// eventos
		btOK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// adiciona lina
				nl = new Object[] { tfProcess.getText(), tfTArr.getText(),
						tfTExec.getText(), tfPrioridade.getText() };
				Main.adicionaLinha(nl);
				Main.openJa = 0;
				jAdicionar.dispose();
			}
		});
		btCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Main.p--;
				Main.openJa = 0;
				jAdicionar.dispose();

			}
		});
		// opcoes finais da janela
		jAdicionar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jAdicionar.setVisible(true);
	}
}
