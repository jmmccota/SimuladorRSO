package simuladorrso;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//Main  
public class Main {

    // quantum
    static int qpass;
    static int qTC;
    // resolucao
    static int xr, yr;
    // janela principal
    static JFrame jprincipal;
    // tabela em que os processos são inceridos
    static JTable table;
    // modelo de table
    static DefaultTableModel model;
    // campo de texto para a quantidade
    static JTextField tfQtde;
    static JTextField tfTrocaContexto;
    // contador de processos
    static int p = 0;
    // variavel para controle da janela adicionar
    static int openJa = 0;
    private JButton btAdd;
    private JButton btRemove;
    private JButton btCalc;
    private JLabel lbQtde;
    private JLabel lbTrocaContexto;
    private JScrollPane scrollTable;
    private JButton btSobre;

    public Main() {
        // inicializa frame principal
        jprincipal = new JFrame();
        jprincipal.getContentPane().setLayout(null);
        jprincipal.setTitle("Simulador Escalonamento de processos");

        // define resolucao
        xr = (int) Resolucao.resolucao(1);
        yr = (int) Resolucao.resolucao(2);
//		jprincipal.setBounds(0, 0, xr, yr);
        jprincipal.setSize(1070, 577);

        // define como maximizado
//		jprincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // cria modelo para a tabela
        model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        model.addColumn("Processo");
        model.addColumn("Tempo de Chegada");
        model.addColumn("Tempo de Execucao");
        model.addColumn("Prioridade");

        // cria tabela
        table = new JTable(model);

        // cria Scroll
        scrollTable = new JScrollPane(table);
        scrollTable.setBounds(10, 11, 866, 516);
        scrollTable.setHorizontalScrollBar(new JScrollBar(0));

        // cria/inicializa demais componentes
        btAdd = new JButton("Adicionar Processo");
        btAdd.setToolTipText("Adicionar um processo.\r\nDeve ter no minimo um processo iniciando no tempo 0, n\u00E3o ter espa\u00E7os de tempo entre os processos, e serem todos os valores numeros inteiros");
        btSobre = new JButton("Sobre");
        btAdd.setToolTipText("Projeto da Disciplina de SO. CEFETMG. Alunos: João Marcos e Henrique Castro");
        btRemove = new JButton("Remover");
        btCalc = new JButton("Calcular");
        btCalc.setToolTipText("Saida: - \r\ntempo de espera de cada processo - \r\ntempo de espera m\u00E9dio de cada algoritmo - \r\nturnaround m\u00E9dio de cada algoritmo - \r\nMostra qual algoritmo teve o melhor maior desempenho");
        lbQtde = new JLabel("Qtde Tempo");
        tfQtde = new JTextField();
        lbTrocaContexto = new JLabel("Troca Contexto");
        tfTrocaContexto = new JTextField();

        // define local
        btAdd.setBounds(886, 109, 150, 30);
        btRemove.setBounds(886, 186, 150, 30);
        btCalc.setBounds(886, 259, 150, 30);
        lbQtde.setBounds(886, 316, 90, 20);
        tfQtde.setBounds(986, 311, 50, 30);
        lbTrocaContexto.setBounds(886, 350, 90, 20);
        tfTrocaContexto.setBounds(986, 344, 50, 30);

        btSobre.setBounds(886, 380, 150, 30);

        // adiciona componentes
        jprincipal.getContentPane().add(scrollTable);
        jprincipal.getContentPane().add(btAdd);
        jprincipal.getContentPane().add(btRemove);
        jprincipal.getContentPane().add(btCalc);
        jprincipal.getContentPane().add(lbQtde);
        jprincipal.getContentPane().add(tfQtde);
        jprincipal.getContentPane().add(lbTrocaContexto);
        jprincipal.getContentPane().add(tfTrocaContexto);
        jprincipal.getContentPane().add(btSobre);

        // eventos
        // evento botao adicionar
        btAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // abre frame
                if (openJa == 0) {
                    JAdicionarProcesso ja = new JAdicionarProcesso();
                    // define janela como aberta
                    openJa = 1;
                }
            }
        });
        // evento botao remover
        btRemove.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // remove linha
                int colunas = table.getSelectedColumn();
                int linhas = table.getSelectedRow();
                if (linhas == -1 || colunas == -1) {
                    // System.out.println("Selecione linha");
                } else {
                    model.removeRow(linhas);
                    // decrementa a varialvel de controle para processos
                    p--;
                }
            }
        });
        // evento botao calcular
        btCalc.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // abre frame resultado e pega quantum
                qpass = Integer.parseInt(tfQtde.getText());
                qTC = Integer.parseInt(tfTrocaContexto.getText());
                Resultado r = new Resultado();
            }
        });

        btSobre.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jprincipal, "Projeto da Disciplina de SO. CEFETMG. \nAlunos: João Marcos (joao_marcos_cota@hotmail.com) e Henrique Castro.\nProfessor Odilon", "Sobre o Simulador", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        tfQtde.setText("1");
        tfTrocaContexto.setText("0");
        // conf. finais jprincipal
        jprincipal.setVisible(true);
        jprincipal.setLocationRelativeTo(null);
        jprincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // adiciona linha ao modelo
    public static void adicionaLinha(Object nl[]) {
        model.addRow(nl);

    }

}
