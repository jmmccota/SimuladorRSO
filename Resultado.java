package simuladorrso;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Resultado {

	JFrame jResultado;
	Calcular calc;
	Rectangle celula;
	// ColorirCelula cc;
	int linhas;
	boolean cinza = false;
	private JButton btnMostraFIFO;
	private JButton btnMostraPrioridade;
	private JButton btnMostraSJF;
	private JButton btnMostraCircular;
	private JScrollPane sPLinhaTempo;
	private JTable tbLinhaTempo;
	private DefaultTableModel modelLinhaTempo;
	private JLabel lblLinhaDoTempo;
	ArrayList<Integer> pintar;
	ArrayList<Vector<Object>> todasLinhas = new ArrayList<Vector<Object>>();
	Timer timerFifo = new Timer(0, null);
	Timer timerSJF = new Timer(0, null);
	Timer timerCircular = new Timer(0, null);
	Timer timerPrioridade = new Timer(0, null);
	int i = 0;
	int tam = 0;

	public Resultado() {
		calc = new Calcular();
		pintar = new ArrayList<Integer>();
		// conf frame
		jResultado = new JFrame();
		jResultado.getContentPane().setLayout(null);
		jResultado.setTitle("Resultado");
		jResultado.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jResultado.setLocationRelativeTo(null);
		// chama calcula
		calc.getFIFO();
		calc.getSJF();
		calc.getPrioridade();
		calc.getCircular();

		// cria modelos
		DefaultTableModel modelFIFO = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		DefaultTableModel modelSJF = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		DefaultTableModel modelPrio = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		DefaultTableModel modelCircular = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		modelLinhaTempo = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		// cria coluna models
		modelFIFO.addColumn("Processo");
		modelFIFO.addColumn("Tempo de Espera");
		modelFIFO.addColumn("Tempo de Turnaround");

		modelSJF.addColumn("Processo");
		modelSJF.addColumn("Tempo de Espera");
		modelSJF.addColumn("Tempo de Turnaround");

		modelPrio.addColumn("Processo");
		modelPrio.addColumn("Tempo de Espera");
		modelPrio.addColumn("Tempo de Turnaround");

		modelCircular.addColumn("Processo");
		modelCircular.addColumn("Tempo de Espera");
		modelCircular.addColumn("Tempo de Turnaround");

		// popula models
		linhas = Main.table.getRowCount();
		modelLinhaTempo.addColumn("i");
		for (int i = 0; i < linhas; i++) {
			modelLinhaTempo.addColumn("P" + (i + 1));
		}

		int contLinha = 0;
		while (contLinha < linhas) {

			modelFIFO.addRow(new Object[] { "P" + (contLinha + 1),
					calc.tEspFIFO[contLinha], calc.tTurFIFO[contLinha] });
			modelSJF.addRow(new Object[] { "P" + (contLinha + 1),
					calc.tEspSJF[contLinha], calc.tTurSJF[contLinha] });
			modelPrio.addRow(new Object[] { "P" + (contLinha + 1),
					calc.tEspPrio[contLinha], calc.tTurPrio[contLinha] });
			modelCircular
					.addRow(new Object[] { "P" + (contLinha + 1),
							calc.tEspCircular[contLinha],
							calc.tTurCircular[contLinha] });
			contLinha++;
		}
		// tabela
		JTable tableFIFO = new JTable(modelFIFO);
		JTable tableSJF = new JTable(modelSJF);
		JTable tablePrio = new JTable(modelPrio);
		JTable tableCircular = new JTable(modelCircular);
		tbLinhaTempo = new JTable(modelLinhaTempo);

		JScrollPane stableFIFO = new JScrollPane(tableFIFO);
		stableFIFO.setHorizontalScrollBar(new JScrollBar(0));
		JScrollPane stableSJF = new JScrollPane(tableSJF);
		stableSJF.setHorizontalScrollBar(new JScrollBar(0));
		JScrollPane stablePrio = new JScrollPane(tablePrio);
		stablePrio.setHorizontalScrollBar(new JScrollBar(0));
		JScrollPane stableCircular = new JScrollPane(tableCircular);
		stableCircular.setHorizontalScrollBar(new JScrollBar(0));

		stableFIFO.setBounds(10, 32, 400, 250);
		stableSJF.setBounds(459, 32, 400, 250);
		stablePrio.setBounds(10, 365, 400, 250);
		stableCircular.setBounds(459, 365, 400, 250);

		// campos de texto tempo de espera e tempo de turnaround
		JTextField temFIFO = new JTextField();
		JTextField ttmFIFO = new JTextField();
		JTextField temSJF = new JTextField();
		JTextField ttmSJF = new JTextField();
		JTextField temPrio = new JTextField();
		JTextField ttmPrio = new JTextField();
		JTextField temCircular = new JTextField();
		JTextField ttmCircular = new JTextField();

		temFIFO.setEditable(false);
		ttmFIFO.setEditable(false);
		temSJF.setEditable(false);
		ttmSJF.setEditable(false);
		temPrio.setEditable(false);
		ttmPrio.setEditable(false);
		temCircular.setEditable(false);
		ttmCircular.setEditable(false);

		temFIFO.setBounds(102, 293, 100, 30);
		ttmFIFO.setBounds(329, 293, 100, 30);
		temSJF.setBounds(550, 293, 100, 30);
		ttmSJF.setBounds(774, 293, 100, 30);
		temPrio.setBounds(102, 626, 100, 30);
		ttmPrio.setBounds(329, 626, 100, 30);
		temCircular.setBounds(550, 626, 100, 30);
		ttmCircular.setBounds(774, 626, 100, 30);

		// formata tempos e passa para tfs
		temFIFO.setText(""
				+ NumberFormat.getNumberInstance().format(calc.getEsperaFIFO()));
		ttmFIFO.setText(""
				+ NumberFormat.getNumberInstance().format(
						calc.getTurnaroundFIFO()));
		temSJF.setText(""
				+ NumberFormat.getNumberInstance().format(calc.getEsperaSJF()));
		ttmSJF.setText(""
				+ NumberFormat.getNumberInstance().format(
						calc.getTurnaroundSJF()));
		temPrio.setText(""
				+ NumberFormat.getNumberInstance().format(
						calc.getEsperaPrioridade()));
		ttmPrio.setText(""
				+ NumberFormat.getNumberInstance().format(
						calc.getTurnaroundPrioridade()));
		temCircular.setText(""
				+ NumberFormat.getNumberInstance().format(
						calc.getEsperaCircular()));
		ttmCircular.setText(""
				+ NumberFormat.getNumberInstance().format(
						calc.getTurnaroundCircular()));

		// labels
		JLabel ltemFIFO = new JLabel("T.Espera Medio");
		JLabel lttmFIFO = new JLabel("T.Turnaround Medio");
		JLabel ltemSJF = new JLabel("T.Espera Medio");
		JLabel lttmSJF = new JLabel("T.Turnaround Medio");
		JLabel ltemPrio = new JLabel("T.Espera Medio");
		JLabel lttmPrio = new JLabel("T.Turnaround Medio");
		JLabel ltemCircular = new JLabel("T.Espera Medio");
		JLabel lttmCircular = new JLabel("T.Turnaround Medio");

		JLabel lFIFO = new JLabel("FIFO");
		JLabel lSJF = new JLabel("SJF");
		JLabel lPrio = new JLabel("Prioridade");
		JLabel lCircular = new JLabel("Circular");

		ltemFIFO.setBounds(10, 293, 100, 20);
		lttmFIFO.setBounds(212, 293, 120, 20);
		ltemSJF.setBounds(459, 293, 100, 20);
		lttmSJF.setBounds(660, 293, 120, 20);

		ltemPrio.setBounds(10, 621, 100, 20);
		lttmPrio.setBounds(212, 626, 120, 20);
		ltemCircular.setBounds(459, 626, 100, 20);
		lttmCircular.setBounds(660, 626, 120, 20);

		lFIFO.setBounds(10, 11, 100, 20);
		lSJF.setBounds(459, 11, 100, 20);
		lPrio.setBounds(10, 334, 100, 20);
		lCircular.setBounds(459, 334, 100, 20);

		// l bordas para o melhor
		int posicao = calc.getMelhor();

		if (posicao == 1) {
			stableFIFO.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		if (posicao == 2) {
			stableSJF.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		if (posicao == 3) {
			stablePrio.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		if (posicao == 4) {
			stableCircular.setBorder(BorderFactory.createLineBorder(Color.RED));
		}

		// adiciona ao frame
		jResultado.getContentPane().add(stableFIFO);
		jResultado.getContentPane().add(stableSJF);
		jResultado.getContentPane().add(stablePrio);
		jResultado.getContentPane().add(stableCircular);
		jResultado.getContentPane().add(temFIFO);
		jResultado.getContentPane().add(ttmFIFO);
		jResultado.getContentPane().add(temSJF);
		jResultado.getContentPane().add(ttmSJF);
		jResultado.getContentPane().add(temPrio);
		jResultado.getContentPane().add(ttmPrio);
		jResultado.getContentPane().add(temCircular);
		jResultado.getContentPane().add(ttmCircular);
		jResultado.getContentPane().add(ltemFIFO);
		jResultado.getContentPane().add(lttmFIFO);
		jResultado.getContentPane().add(ltemSJF);
		jResultado.getContentPane().add(lttmSJF);
		jResultado.getContentPane().add(ltemPrio);
		jResultado.getContentPane().add(lttmPrio);
		jResultado.getContentPane().add(ltemCircular);
		jResultado.getContentPane().add(lttmCircular);
		jResultado.getContentPane().add(lFIFO);
		jResultado.getContentPane().add(lSJF);
		jResultado.getContentPane().add(lPrio);
		jResultado.getContentPane().add(lCircular);

		btnMostraFIFO = new JButton("Mostra FIFO");
		btnMostraFIFO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpar();
				todasLinhas = new ArrayList<Vector<Object>>();
				Vector<Object> lin;// = new Vector<Object>();
				int nlinhas = calc.FIFO.size();
				int linha = 0, coluna = 0;
				// int[] colunas = new int[linhas + 1];
				for (int i = 0; i < nlinhas; i++) {
					lin = new Vector<Object>();
					String c1 = i + " - " + (i + 1);
					lin.add(c1);
					if (calc.FIFO.get(i) == -1) {
						for (int j = 1; j <= linhas; j++) {
							if (calc.mD[j - 1][0] > i) {
								lin.add(" - ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurFIFO[j - 1]) <= i) {
								lin.add(" Finalizado ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurFIFO[j - 1]) > i) {
								lin.add(" Troca ");
							}
						}
					} else {
						for (int j = 1; j <= linhas; j++) {
							if (calc.FIFO.get(i) == j - 1) {
								lin.add(" Exec ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurFIFO[j - 1]) <= i) {
								lin.add(" Finalizado ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurFIFO[j - 1]) > i) {
								lin.add(" Pronto ");
							} else if (calc.mD[j - 1][0] > i) {
								lin.add(" - ");
							}
						}

					}
					// modelLinhaTempo.addRow(lin);
					todasLinhas.add(lin);
				}
				tam = todasLinhas.size();
				i = 0;
				timerFifo.start();
			}
		});

		timerFifo = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (i < tam) {
					modelLinhaTempo.addRow(todasLinhas.get(i));
				} else {
					((Timer) e.getSource()).stop();
				}
				i++;
			}
		});

		btnMostraFIFO.setBounds(300, 331, 110, 23);
		jResultado.getContentPane().add(btnMostraFIFO);

		btnMostraPrioridade = new JButton("Mostra Prioridade");
		btnMostraPrioridade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
				todasLinhas = new ArrayList<Vector<Object>>();
				Vector<Object> lin;// = new Vector<Object>();
				int nlinhas = calc.PRIO.size();
				int linha = 0, coluna = 0;
				// int[] colunas = new int[linhas + 1];
				for (int i = 0; i < nlinhas; i++) {
					lin = new Vector<Object>();
					String c1 = i + " - " + (i + 1);
					lin.add(c1);
					if (calc.PRIO.get(i) == -1) {
						for (int j = 1; j <= linhas; j++) {
							if (calc.mD[j - 1][0] > i) {
								lin.add(" - ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurPrio[j - 1]) <= i) {
								lin.add(" Finalizado ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurPrio[j - 1]) > i) {
								lin.add(" Troca ");
							}

						}

					} else {
						for (int j = 1; j <= linhas; j++) {
							if (calc.PRIO.get(i) == j - 1) {
								lin.add(" Exec ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurPrio[j - 1]) <= i) {
								lin.add(" Finalizado ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurPrio[j - 1]) > i) {
								lin.add(" Pronto ");
							} else if (calc.mD[j - 1][0] > i) {
								lin.add(" - ");
							}
						}
					}
					// modelLinhaTempo.addRow(lin);
					todasLinhas.add(lin);
				}
				tam = todasLinhas.size();
				i = 0;
				timerPrioridade.start();
			}
		});

		timerPrioridade = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (i < tam) {
					modelLinhaTempo.addRow(todasLinhas.get(i));
				} else {
					((Timer) e.getSource()).stop();
				}
				i++;
			}
		});

		btnMostraPrioridade.setBounds(300, 666, 110, 23);
		jResultado.getContentPane().add(btnMostraPrioridade);

		btnMostraSJF = new JButton("Mostra SJF");
		btnMostraSJF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
				todasLinhas = new ArrayList<Vector<Object>>();
				Vector<Object> lin;// = new Vector<Object>();
				int nlinhas = calc.SJF.size();
				int linha = 0, coluna = 0;
				// int[] colunas = new int[linhas + 1];
				for (int i = 0; i < nlinhas; i++) {
					lin = new Vector<Object>();
					String c1 = i + " - " + (i + 1);
					lin.add(c1);
					if (calc.SJF.get(i) == -1) {
						for (int j = 1; j <= linhas; j++) {
							if (calc.mD[j - 1][0] > i) {
								lin.add(" - ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurSJF[j - 1]) <= i) {
								lin.add(" Finalizado ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurSJF[j - 1]) > i) {
								lin.add(" Troca ");
							}

						}

					} else {
						for (int j = 1; j <= linhas; j++) {
							if (calc.SJF.get(i) == j - 1) {
								lin.add(" Exec ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurSJF[j - 1]) <= i) {
								lin.add(" Finalizado ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurSJF[j - 1]) > i) {
								lin.add(" Pronto ");
							} else if (calc.mD[j - 1][0] > i) {
								lin.add(" - ");
							}
						}
					}
					// modelLinhaTempo.addRow(lin);
					todasLinhas.add(lin);
				}
				tam = todasLinhas.size();
				i = 0;
				timerSJF.start();
			}
		});
		timerSJF = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (i < tam) {
					modelLinhaTempo.addRow(todasLinhas.get(i));
				} else {
					((Timer) e.getSource()).stop();
				}
				i++;
			}
		});

		btnMostraSJF.setBounds(749, 334, 110, 23);
		jResultado.getContentPane().add(btnMostraSJF);

		btnMostraCircular = new JButton("Mostra Circular");
		btnMostraCircular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
				todasLinhas = new ArrayList<Vector<Object>>();
				Vector<Object> lin;// = new Vector<Object>();
				int nlinhas = calc.CIRC.size();
				int linha = 0, coluna = 0;
				// int[] colunas = new int[linhas + 1];
				for (int i = 0; i < nlinhas; i++) {
					lin = new Vector<Object>();
					String c1 = i + " - " + (i + 1);
					lin.add(c1);
					if (calc.CIRC.get(i) == -1) {
						for (int j = 1; j <= linhas; j++) {
							if (calc.mD[j - 1][0] > i) {
								lin.add(" - ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurCircular[j - 1]) <= i) {
								lin.add(" Finalizado ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurCircular[j - 1]) > i) {
								lin.add(" Troca ");
							}

						}

					} else {
						for (int j = 1; j <= linhas; j++) {
							if (calc.CIRC.get(i) == j - 1) {
								lin.add(" Exec ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurCircular[j - 1]) <= i) {
								lin.add(" Finalizado ");
							} else if (calc.mD[j - 1][0] <= i
									&& (calc.mD[j - 1][0] + calc.tTurCircular[j - 1]) > i) {
								lin.add(" Pronto ");
							} else if (calc.mD[j - 1][0] > i) {
								lin.add(" - ");
							}
						}
					}
					// modelLinhaTempo.addRow(lin);
					todasLinhas.add(lin);
				}
				tam = todasLinhas.size();
				i = 0;
				timerCircular.start();
			}
		});

		timerCircular = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (i < tam) {
					modelLinhaTempo.addRow(todasLinhas.get(i));
				} else {
					((Timer) e.getSource()).stop();
				}
				i++;
			}
		});

		btnMostraCircular.setBounds(749, 666, 110, 23);
		jResultado.getContentPane().add(btnMostraCircular);

		sPLinhaTempo = new JScrollPane(tbLinhaTempo);
		sPLinhaTempo.setBounds(907, 32, 437, 583);
		jResultado.getContentPane().add(sPLinhaTempo);
		sPLinhaTempo.setHorizontalScrollBar(new JScrollBar(0));

		lblLinhaDoTempo = new JLabel("Linha do tempo");
		lblLinhaDoTempo.setBounds(907, 14, 90, 14);
		jResultado.getContentPane().add(lblLinhaDoTempo);

		// fin frame
		jResultado.setVisible(true);
		jResultado.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void limpar() {
		int totalLinhas = tbLinhaTempo.getRowCount();// pega numero total de
														// linhas
		for (int i = 0; i < totalLinhas; i++) {// percorre o vetor
			modelLinhaTempo.removeRow(0);// apaga as linhas
		}
	}

	private void colorir(int linha, int coluna, boolean cinza) {

		celula = tbLinhaTempo.getCellRect(linha, coluna, false);

		tbLinhaTempo.putClientProperty("hitColumn", coluna);
		tbLinhaTempo.putClientProperty("hitRow", linha);
		tbLinhaTempo.repaint(celula);
		tbLinhaTempo.setDefaultRenderer(Object.class,
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						Component c = super
								.getTableCellRendererComponent(table, value,
										isSelected, hasFocus, row, column);
						isSelected = false;
						hasFocus = false;

						if (column == coluna && row == linha) {
							System.out.println("row " + row);
							System.out.println("column " + column);
							if (cinza) {
								c.setBackground(Color.GRAY);
							} else {
								c.setBackground(Color.GREEN);
							}
						}

						return this;
					}
				});

	}

	private void colorir() {
		tbLinhaTempo.setDefaultRenderer(Object.class,
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						for (int i = 0; i < pintar.size(); i += 3) {
							Component c = super.getTableCellRendererComponent(
									table, value, isSelected, hasFocus, row,
									column);
							isSelected = false;
							hasFocus = false;
							row = pintar.get(i);
							column = pintar.get(i + 1);
							if (pintar.get(i + 2) == -2) {
								c.setBackground(Color.GRAY);
							} else if (pintar.get(i + 2) == -3) {
								c.setBackground(Color.GREEN);
							}
						}

						return this;
					}
				});
	}

	private void escrever(int linha, int coluna, String escrita) {
		celula = tbLinhaTempo.getCellRect(linha, coluna, false);

	}
}
