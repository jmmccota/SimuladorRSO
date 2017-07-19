/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simuladorrso;

import java.util.ArrayList;

public class Calcular {

	int quantum = 0;
	int qTC = 0;
	double tempoTotal = 0;
	double tempoTotal2 = 0;
	int tempoTotalSJF = 0;
	int[] escFIFO;
	int[] escSJF;
	int[] escPrio;
	int[] escCircular;
	ArrayList<Integer> FIFO;
	ArrayList<Integer> SJF;
	ArrayList<Integer> PRIO;
	ArrayList<Integer> CIRC;
	// matrizes
	double[][] mD;
	double[][] mFIFO;
	double[][] mSJF;
	double[][] mPrioridade;
	double[][] mCircular;
	// tempo de execução e turnaround de cada algoritmo
	double[] tEspPrio;
	double[] tTurPrio;
	double[] tEspFIFO;
	double[] tTurFIFO;
	double[] tEspSJF;
	double[] tTurSJF;
	double[] tEspCircular;
	double[] tTurCircular;
	// tempo médio
	double tmeFIFO = 0;
	double tmeSJF = 0;
	double tmePrio = 0;
	double tmeCircular = 0;
	int linhas;
	int[] ordem;
	int[] ordems;
	int[] ordemp;
	int[] ordemc;

	public Calcular() {

		// determina numero de linhas
		linhas = Main.table.getRowCount();

		double ttemp;
		int clinha = 0;
		int i = 0, j = 0;
		ordem = new int[linhas];
		ordems = new int[linhas];
		ordemp = new int[linhas];
		ordemc = new int[linhas];
		// determina tempo de execucao somando o tempo de execucao de todos os
		// processos
		while (clinha < linhas) {
			ttemp = Double.parseDouble((String) Main.table
					.getValueAt(clinha, 2));
			tempoTotal = tempoTotal + ttemp;

			clinha++;
		}

		// inicializa matriz uma para cada algoritmo
		mFIFO = new double[linhas][3];
		mD = new double[linhas][3];
		mSJF = new double[linhas][3];
		mPrioridade = new double[linhas][3];
		mCircular = new double[linhas][3];

		// cria matrizes para cada escalonamento e uma geral que não vai ser
		// alterada
		while (i < linhas) {

			// tempo de chegada
			mD[i][0] = Double.parseDouble((String) Main.table.getValueAt(i, 1));
			mFIFO[i][0] = Double.parseDouble((String) Main.table.getValueAt(i,
					1));
			mSJF[i][0] = Double.parseDouble((String) Main.table
					.getValueAt(i, 1));
			mPrioridade[i][0] = Double.parseDouble((String) Main.table
					.getValueAt(i, 1));
			mCircular[i][0] = Double.parseDouble((String) Main.table
					.getValueAt(i, 1));

			// tempo de execução
			mD[i][1] = Double.parseDouble((String) Main.table.getValueAt(i, 2));
			mFIFO[i][1] = Double.parseDouble((String) Main.table.getValueAt(i,
					2));
			mSJF[i][1] = Double.parseDouble((String) Main.table
					.getValueAt(i, 2));
			mPrioridade[i][1] = Double.parseDouble((String) Main.table
					.getValueAt(i, 2));
			mCircular[i][1] = Double.parseDouble((String) Main.table
					.getValueAt(i, 2));

			// prioridade
			mD[i][2] = Double.parseDouble((String) Main.table.getValueAt(i, 3));
			mFIFO[i][2] = Double.parseDouble((String) Main.table.getValueAt(i,
					3));
			mSJF[i][2] = Double.parseDouble((String) Main.table
					.getValueAt(i, 3));
			mPrioridade[i][2] = Double.parseDouble((String) Main.table
					.getValueAt(i, 3));
			mCircular[i][2] = Double.parseDouble((String) Main.table
					.getValueAt(i, 3));
			i++;

		}
		// recebe o valor do quantum para o Circular
		quantum = Main.qpass;
		// recebe troca de contexto
		qTC = Main.qTC;

	}

	// calcula FIFO
	public void getFIFO() {
		int contFIFO = 0;

		FIFO = new ArrayList<Integer>();
		for (int ii = 0; ii < linhas; ii++) {// enche o vetor ordem com o
												// código dos processos
			ordem[ii] = ii;
		}
		// coloca a fila na ordem de chegada
		int ord;
		for (int ii = 0; ii < linhas; ii++) {
			for (int jj = 0; jj < linhas - 1; jj++) {
				if (mFIFO[ordem[jj]][0] > mFIFO[ordem[jj + 1]][0]) {
					ord = ordem[jj];
					ordem[jj] = ordem[jj + 1];
					ordem[jj + 1] = ord;

				}
			}
		}

		int qtdTC = linhas - 1;
		int addTotal = qtdTC * qTC;
		tempoTotal2 = tempoTotal + addTotal;
		// representacao do gráfico de Gantt
		// escFIFO = new int[(int) tempoTotal2];
		int i = 0;
		boolean FIFOt = true;

		// faz FIFO até que o contador alcance o tempo total de execucao
		while (FIFOt) {
			if (mFIFO[ordem[i]][0] > contFIFO) {
				while (mFIFO[ordem[i]][0] > contFIFO) {
					FIFO.add(-1);
					contFIFO++;
				}
			} else {
				// passa processo enquanto o tempo de execução for maior que
				// zero
				while (mFIFO[ordem[i]][1] > 0) {
					// decresse o tempo de execução
					mFIFO[ordem[i]][1]--;
					// escFIFO[contFIFO] = ordem[i];
					FIFO.add(ordem[i]);
					contFIFO++;
				}

				i++;// incrementa o número do processo
				if (i >= linhas) {
					FIFOt = false;
				} else {
					if (qTC > 0) {
						for (int contTC = 0; contTC < qTC; contTC++) {
							FIFO.add(-1);
							contFIFO++;
						}
					}
				}
			}

		}

		// calcula tempo de espera e turnaround
		int contFIFO2 = 0;
		i = 0;
		tEspFIFO = new double[(int) linhas];
		tTurFIFO = new double[(int) linhas];
		int contProcesso = 0;

		// procura a primeira "aparição" do processo
		while (contProcesso < linhas) {
			if (FIFO.get(i) == ordem[contProcesso]) {
				tEspFIFO[ordem[contProcesso]] = i - mD[ordem[contProcesso]][0];
				contProcesso++;
			}
			i++;
		}
		int j = FIFO.size() - 1;
		contProcesso = linhas - 1;

		// procura a ultima apariçao do processo
		while (contProcesso >= 0) {
			if (FIFO.get(j) == ordem[contProcesso]) {
				tTurFIFO[ordem[contProcesso]] = j - mD[ordem[contProcesso]][0]
						+ 1;
				contProcesso--;
			}
			j--;
		}

	}

	// calcula SJF
	public void getSJF() {
		SJF = new ArrayList<Integer>();
		for (int ii = 0; ii < linhas; ii++) {// coloca ordem dos processos
			ordems[ii] = ii;
		}
		int ord;
		for (int ii = 0; ii < linhas; ii++) {// coloca na ordem de chegada
			for (int jj = 0; jj < linhas - 1; jj++) {
				if (mSJF[ordems[jj]][0] > mSJF[ordems[jj + 1]][0]) {
					ord = ordems[jj];
					ordems[jj] = ordems[jj + 1];
					ordems[jj + 1] = ord;

				}
			}
		}
		// coloca a fila na primeira ordem

		for (int ii = 0; ii < linhas; ii++) {// confere menor tempo e ordem de
												// chegada
			for (int jj = 0; jj < linhas - 1; jj++) {
				if (mSJF[ordems[jj + 1]][1] < mSJF[ordems[jj]][1]) {
					if (mSJF[ordems[jj + 1]][0] <= mSJF[ordems[jj]][0]) {
						ord = ordems[jj];
						ordems[jj] = ordems[jj + 1];
						ordems[jj + 1] = ord;
					}

				}
			}
		}
		boolean SJFt = true;
		int contSJF = 0, i = 0;
		// faz FIFO até que o contador alcance o tempo total de execucao
		while (SJFt) {
			if (mSJF[ordems[i]][0] > contSJF) {
				while (mSJF[ordems[i]][0] > contSJF) {
					SJF.add(-1);
					contSJF++;
				}
			} else {
				// passa processo enquanto o tempo de execução for maior que
				// zero
				while (mSJF[ordems[i]][1] > 0) {
					// decresse o tempo de execução
					mSJF[ordems[i]][1]--;
					// escFIFO[contFIFO] = ordem[i];
					SJF.add(ordems[i]);
					contSJF++;
				}
				i++;// incrementa o número do processo

				if (i >= linhas) {
					SJFt = false;
				} else {
					if (qTC > 0) {
						for (int contTC = 0; contTC < qTC; contTC++) {
							SJF.add(-1);
							contSJF++;
						}
						for (int ii = i; ii < linhas; ii++) {
							for (int jj = i; jj < linhas - 1; jj++) {
								if (mSJF[ordems[jj + 1]][1] < mSJF[ordems[jj]][1]) {
									if (mSJF[ordems[jj + 1]][0] <= contSJF) {
										ord = ordems[jj + 1];
										ordems[jj + 1] = ordems[jj];
										ordems[jj] = ord;
									}
								}
							}
						}
					}
				}
			}

		}

		int contFIFO2 = 0;
		i = 0;
		tEspSJF = new double[(int) linhas];
		tTurSJF = new double[(int) linhas];
		int contProcesso = 0;

		// procura a primeira "aparição" do processo
		while (contProcesso < linhas) {
			if (SJF.get(i) == ordems[contProcesso]) {
				tEspSJF[ordems[contProcesso]] = i - mD[ordems[contProcesso]][0];
				contProcesso++;
			}
			i++;
		}
		int j = SJF.size() - 1;
		contProcesso = linhas - 1;

		// procura a ultima apariçao do processo
		while (contProcesso >= 0) {
			if (SJF.get(j) == ordems[contProcesso]) {
				tTurSJF[ordems[contProcesso]] = j - mD[ordems[contProcesso]][0]
						+ 1;
				contProcesso--;
			}
			j--;
		}

	}

	// calcula Prioridade
	public void getPrioridade() {
		PRIO = new ArrayList<Integer>();
		ArrayList<Integer> chega = new ArrayList<Integer>();
		for (int ii = 0; ii < linhas; ii++) {// enche o vetor com processos
			ordemp[ii] = ii;
		}

		int ord;
		for (int ii = 0; ii < linhas; ii++) {// ordem de chegada
			for (int jj = 0; jj < linhas - 1; jj++) {
				if (mPrioridade[ordemp[jj]][0] > mPrioridade[ordemp[jj + 1]][0]) {
					ord = ordemp[jj];
					ordemp[jj] = ordemp[jj + 1];
					ordemp[jj + 1] = ord;

				}
			}
		}
		for (int ii = 0; ii < linhas; ii++) {// confere prioridade e tempo de
												// chegada
			for (int jj = 0; jj < linhas - 1; jj++) {
				if (mPrioridade[ordemp[jj + 1]][2] > mPrioridade[ordemp[jj]][2]) {
					if (mPrioridade[ordemp[jj + 1]][0] <= mPrioridade[ordemp[jj]][0]) {
						ord = ordemp[jj + 1];
						ordemp[jj + 1] = ordemp[jj];
						ordemp[jj] = ord;
					}
				}
			}
		}

		boolean PRIOt = true;
		int contPrio = 0, i = 0, pzerado = 0;
		while (mPrioridade[ordemp[0]][0] > contPrio) {
			PRIO.add(-1);
			contPrio++;
		}
		while (PRIOt) {

			// array com processos que ja chegaram e nao foram executados
			chega = new ArrayList<Integer>();
			for (int ii = 0; ii < linhas; ii++) {

				if (mPrioridade[ii][0] <= contPrio && mPrioridade[ii][1] > 0) {
					chega.add(ii);
				}
			}
			if (chega.size() == 0) {
				PRIO.add(-1);
				contPrio++;
			} else {

				if (chega.size() > 1) {
					for (int ii = 0; ii < chega.size(); ii++) {
						for (int jj = 0; jj < chega.size() - 1; jj++) {
							if (mPrioridade[chega.get(jj + 1)][2] > mPrioridade[chega
									.get(jj)][2]) {
								ord = chega.get(jj);
								chega.set(jj, chega.get(jj + 1));
								chega.set(jj + 1, ord);
							}
						}
					}
				}
				if (qTC > 0) {
					if (PRIO.size() > 1 && chega.size() > 0) {

						int fim = PRIO.size() - 1;
						int cTC = 0;
						if (PRIO.get(fim) != chega.get(0)
								&& PRIO.get(fim) != -1) {

							while (cTC < qTC) {

								PRIO.add(-1);
								contPrio++;
								cTC++;
							}
							chega = new ArrayList<Integer>();
							for (int ii = 0; ii < linhas; ii++) {

								if (mPrioridade[ii][0] <= contPrio
										&& mPrioridade[ii][1] > 0) {
									chega.add(ii);
								}
							}
							for (int ii = 0; ii < chega.size(); ii++) {
								for (int jj = 0; jj < chega.size() - 1; jj++) {
									if (mPrioridade[chega.get(jj + 1)][2] > mPrioridade[chega
											.get(jj)][2]) {
										ord = chega.get(jj);
										chega.set(jj, chega.get(jj + 1));
										chega.set(jj + 1, ord);
									}
								}
							}

						}

					}
				}
				if (chega.size() > 0) {
					mPrioridade[chega.get(0)][1]--;
					PRIO.add(chega.get(0));
					contPrio++;
				}

				// parar o while se tiver tudo executado
				pzerado = 0;
				for (int x = 0; x < linhas; x++) {
					if (mPrioridade[x][1] <= 0) {
						pzerado++;
					}
				}
				if (pzerado >= linhas) {
					PRIOt = false;
				}
			}

		}
		int contFIFO2 = 0;
		i = 0;
		tEspPrio = new double[(int) linhas];
		tTurPrio = new double[(int) linhas];
		int contProcesso = 0;

		// procura a primeira "aparição" do processo
		while (contProcesso < linhas) {
			if (PRIO.get(i) == contProcesso) {
				tEspPrio[contProcesso] = i - mD[contProcesso][0];
				contProcesso++;
				i = 0;
			} else {
				i++;
			}

			if (i >= PRIO.size()) {
				i = 0;
			}
		}
		int j = PRIO.size() - 1;
		// contProcesso = linhas - 1;
		contProcesso = 0;

		// procura a ultima apariçao do processo
		while (contProcesso < linhas) {// >0
			if (PRIO.get(j) == contProcesso) {
				tTurPrio[contProcesso] = j - mD[contProcesso][0] + 1;
				contProcesso++;
				j = PRIO.size() - 1;
			} else {
				j--;
			}

			if (j < 0) {
				j = PRIO.size() - 1;
			}
		}

		// faz Priotidade até que o contador alcance o tempo total
	}

	// calcula Circular
	public void getCircular() {
		int i = 0;
		// contador do quantum

		int cQ = 0;
		int contCircular = 0;
		int nTC = 0;
		for (int ii = 0; ii < linhas; ii++) {// enche o vetor ordem com o
												// código dos processos
			ordemc[ii] = ii;
		}
		// coloca a fila na ordem de chegada
		int ord;
		for (int ii = 0; ii < linhas; ii++) {
			for (int jj = 0; jj < linhas - 1; jj++) {
				if (mCircular[ordemc[jj]][0] > mCircular[ordemc[jj + 1]][0]) {
					ord = ordemc[jj];
					ordemc[jj] = ordemc[jj + 1];
					ordemc[jj + 1] = ord;

				}
			}
		}

		// representa o gráfico de Gantt
		CIRC = new ArrayList<Integer>();
		i = 0;
		boolean CIRCt = true, primeiro = true;
		int contCIRC = 0, iant = 0;
		int[] term = new int[linhas];
		for (int c = 0; c < linhas; c++) {
			term[c] = -1;
		}
		// faz FIFO até que o contador alcance o tempo total de execucao
		while (CIRCt) {// while 1

			if (mCircular[ordemc[i]][0] > contCIRC) {// if 1

				// System.out.println("Passou if 1. Contador = " + contCIRC);
				// System.out.println("Passou if 1. Processo = " + ordemc[i]);
				while (mCircular[ordemc[i]][0] > contCIRC) {// while 2
					// System.out.println("Passou while 2. Contador = " +
					// contCIRC);
					// System.out.println("Passou while 2. Processo = " +
					// ordemc[i]);
					CIRC.add(-1);
					// System.out.println(CIRC.get(contCIRC));
					contCIRC++;
					cQ++;
				}

				cQ = 0;
			}
			if (mCircular[ordemc[i]][1] > 0 && cQ < quantum) {// else if
				// System.out.println("Passou else if. Contador = " + contCIRC);
				// System.out.println("Passou else if. Processo = " +
				// ordemc[i]);
				// System.out.println("Passou else if. Falta executar = " +
				// mCircular[ordemc[i]][1]);
				mCircular[ordemc[i]][1]--;
				CIRC.add(ordemc[i]);
				// System.out.println(CIRC.get(contCIRC));
				contCIRC++;
				cQ++;
				primeiro = false;

			} else if (mCircular[ordemc[i]][1] > 0 && cQ >= quantum
					&& contCircular < linhas - 1) {
				if (i + 1 < linhas) {
					if (mCircular[ordemc[i + 1]][0] > contCIRC) {
						boolean voltar = true;
						int c = 0;
						while (voltar) {
							if (mCircular[ordemc[c]][1] > 0) {

								cQ = 0;
								if (qTC > 0) {
									for (int ii = 0; ii < qTC; ii++) {
										CIRC.add(-1);
										// System.out.println(CIRC.get(contCIRC));
										contCIRC++;
									}
								}

								i = c;
								voltar = false;

							} else if (c < linhas) {
								c++;
							}
						}
					} else {
						i++;
						if (mCircular[ordemc[i]][1] > 0 && qTC > 0) {
							for (int ii = 0; ii < qTC; ii++) {
								CIRC.add(-1);
								// System.out.println(CIRC.get(contCIRC));
								contCIRC++;
							}
						}
						cQ = 0;
					}

				} else {
					boolean voltar = true;
					int c = 0;
					while (voltar) {
						if (mCircular[ordemc[c]][1] > 0) {
							if (ordemc[c] != ordemc[i]) {
								cQ = 0;
								if (qTC > 0) {
									for (int ii = 0; ii < qTC; ii++) {
										CIRC.add(-1);
										// System.out.println(CIRC.get(contCIRC));
										contCIRC++;
									}
								}
							} else {
								cQ--;
							}
							i = c;
							voltar = false;

						} else if (c < linhas) {
							c++;
						}
					}

				}

			} /*
			 * else if (mCircular[ordemc[i]][1] > 0 && cQ >= quantum &&
			 * contCircular == linhas - 1) { mCircular[ordemc[i]][1]--;
			 * CIRC.add(ordemc[i]); System.out.println(CIRC.get(contCIRC));
			 * contCIRC++;
			 * 
			 * }
			 */else {// else
				// System.out.println("Passou else. Contador = " + contCIRC);
				if (mCircular[ordemc[i]][1] <= 0) {
					boolean somador = true;
					for (int cc = 0; cc < linhas; cc++) {
						if (term[cc] == ordemc[i]) {
							somador = false;
						}
					}
					if (somador) {
						term[contCircular] = ordemc[i];
						contCircular++;
						// System.out.println("CONTADOR = " + contCircular);
						// System.out.println("Processo: " + ordemc[i]);
					}

				}
				if (contCircular >= linhas) {
					CIRCt = false;
				}
				i++;

				if (i >= linhas) {
					i = 0;
				}
				if (qTC > 0 && mCircular[ordemc[i]][1] > 0) {
					for (int ii = 0; ii < qTC; ii++) {
						CIRC.add(-1);
						// System.out.println(CIRC.get(contCIRC));
						contCIRC++;
					}
				}
				cQ = 0;
			}

		}
		/*
		 * if (qTC > 0) { for (int ii = 0; ii < CIRC.size() - 1; ii++) { if
		 * (CIRC.get(ii) != CIRC.get(ii + 1) && CIRC.get(ii) != -1) { for (int
		 * jj = 0; jj < qTC; jj++) { CIRC.add(ii + 1, -1); } } } }
		 */

		i = 0;
		tEspCircular = new double[(int) linhas];
		tTurCircular = new double[(int) linhas];
		int contProcesso = 0;

		// procura a primeira "aparição" do processo
		while (contProcesso < linhas) {
			if (CIRC.get(i) == ordemc[contProcesso]) {
				tEspCircular[ordemc[contProcesso]] = i
						- mD[ordemc[contProcesso]][0];
				contProcesso++;
			}
			i++;
		}
		int j = CIRC.size() - 1;
		contProcesso = linhas - 1;

		// procura a ultima apariçao do processo
		while (contProcesso >= 0) {
			if (CIRC.get(j) == ordemc[contProcesso]) {
				tTurCircular[ordemc[contProcesso]] = j
						- mD[ordemc[contProcesso]][0] + 1;
				contProcesso--;
				j = CIRC.size() - 1;
			} else {
				j--;
			}

		}
		// calcula tempo de espera e turnround

	}

	// calcula o melhor algoritmo e retorna
	public int getMelhor() {
		// retorna 1=fifo 2=sjf 3=prio 4=Circular
		int c = 0;
		if (tmeFIFO < tmeSJF && tmeFIFO < tmePrio && tmeFIFO < tmeCircular) {
			c = 1;
		}
		if (tmeSJF < tmeFIFO && tmeSJF < tmePrio && tmeSJF < tmeCircular) {
			c = 2;
		}
		if (tmePrio < tmeFIFO && tmePrio < tmeSJF && tmePrio < tmeCircular) {
			c = 3;
		}
		if (tmeCircular < tmeFIFO && tmeCircular < tmeSJF
				&& tmeCircular < tmePrio) {
			c = 4;
		}
		return c;

	}

	// calcula tempo médio de espera de FIFO e o retorna
	public double getEsperaFIFO() {

		for (int i = 0; i < tEspFIFO.length; i++) {
			tmeFIFO = tEspFIFO[i] + tmeFIFO;
		}
		return tmeFIFO / linhas;
	}

	// calcula tempo médio de espera de SJF e retorna
	public double getEsperaSJF() {

		for (int i = 0; i < tEspSJF.length; i++) {
			tmeSJF = tEspSJF[i] + tmeSJF;
		}
		return tmeSJF / linhas;
	}

	// calcula tempo médio de espera de Prioridade e o retorna
	public double getEsperaPrioridade() {

		for (int i = 0; i < tEspPrio.length; i++) {
			tmePrio = tEspPrio[i] + tmePrio;
		}
		return tmePrio / linhas;
	}

	// calcula tempo médio de espera de Circular e o retorna
	public double getEsperaCircular() {
		for (int i = 0; i < tEspCircular.length; i++) {
			tmeCircular = tEspCircular[i] + tmeCircular;
		}
		return tmeCircular / linhas;
	}

	// calcula turnaround médio de FIFO e o retorna
	public double getTurnaroundFIFO() {
		return (tmeFIFO + tempoTotal) / linhas;

	}

	// calcula turnaround médio de SJF e o retorna
	public double getTurnaroundSJF() {
		return (tmeSJF + tempoTotal) / linhas;
	}

	// calcula turnaround médio de Prioridade e o retorna
	public double getTurnaroundPrioridade() {
		return (tmePrio + tempoTotal) / linhas;
	}

	// calcula turnaround médio de Circular e o retorna
	public double getTurnaroundCircular() {
		return (tmeCircular + tempoTotal) / linhas;
	}
}
