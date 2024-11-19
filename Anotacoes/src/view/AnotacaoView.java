// Pacote: view
package view;

import controller.AnotacaoController;
import model.Anotacao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AnotacaoView extends JFrame {
    // Controlador de Anotações
    private AnotacaoController controller;
    // Campo de texto para o título da anotação
    private JTextField tituloField;
    // Área de texto para a descrição da anotação
    private JTextArea descricaoArea;
    // ComboBox para o dia da data do lembrete
    private JComboBox<String> diaComboBox;
    // ComboBox para o mês da data do lembrete
    private JComboBox<String> mesComboBox;
    // ComboBox para o ano da data do lembrete
    private JComboBox<String> anoComboBox;
    // Spinner para a hora do lembrete
    private JSpinner horaLembrete;
    // Tabela para listar as anotações
    private JTable tabelaAnotacoes;
    // Modelo da tabela
    private DefaultTableModel tabelaModelo;

    // Lista de anotações
    private List<Anotacao> anotacoes;

    // Construtor da classe AnotacaoView
    public AnotacaoView() {
        // Inicializa o controlador de anotações
        controller = new AnotacaoController();
        setTitle("Aplicativo de Anotações");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Centraliza a janela

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campo Título
        JLabel tituloLabel = new JLabel("Título:");
        tituloField = new JTextField();
        tituloField.setMaximumSize(new Dimension(Integer.MAX_VALUE, tituloField.getPreferredSize().height));

        // Campo Descrição
        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoArea = new JTextArea(5, 20);
        descricaoArea.setLineWrap(true); // Quebra de linha automática
        descricaoArea.setWrapStyleWord(true); // Quebra de linha por palavra
        JScrollPane descricaoScrollPane = new JScrollPane(descricaoArea);

        // Campo Data
        JLabel dataLabel = new JLabel("Data do Lembrete:");
        diaComboBox = new JComboBox<>(getDias()); // ComboBox para dias
        mesComboBox = new JComboBox<>(getMeses()); // ComboBox para meses
        anoComboBox = new JComboBox<>(getAnos()); // ComboBox para anos
        JPanel dataPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dataPanel.add(diaComboBox);
        dataPanel.add(mesComboBox);
        dataPanel.add(anoComboBox);

        // Campo Hora
        JLabel horaLabel = new JLabel("Hora do Lembrete:");
        SpinnerDateModel horaModel = new SpinnerDateModel();
        horaLembrete = new JSpinner(horaModel); // Spinner para selecionar a hora
        JSpinner.DateEditor horaEditor = new JSpinner.DateEditor(horaLembrete, "HH:mm"); // Formato da hora
        horaLembrete.setEditor(horaEditor);

        // Botões
        JButton adicionarButton = new JButton("Adicionar Anotação");
        adicionarButton.addActionListener(this::adicionarAnotacao); // Evento para adicionar anotação

        JButton editarButton = new JButton("Editar Anotação");
        editarButton.addActionListener(this::editarAnotacao); // Evento para editar anotação

        JButton apagarButton = new JButton("Apagar Anotação");
        apagarButton.addActionListener(this::apagarAnotacao); // Evento para apagar anotação

        JButton novaNotaButton = new JButton("Nova Anotação");
        novaNotaButton.addActionListener(this::limparCampos); // Evento para limpar campos e preparar para nova anotação

        // Tabela de anotações
        tabelaModelo = new DefaultTableModel(new String[]{"Título", "Descrição", "Data", "Hora"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desabilita a edição direta nas células da tabela
            }
        };
        tabelaAnotacoes = new JTable(tabelaModelo);
        tabelaAnotacoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Seleção única
        tabelaAnotacoes.setRowSelectionAllowed(true); // Permite a seleção de linhas
        tabelaAnotacoes.setColumnSelectionAllowed(false); // Desabilita a seleção de colunas
        tabelaAnotacoes.setSelectionBackground(new Color(184, 207, 229)); // Cor de fundo para linha selecionada
        JScrollPane tabelaScrollPane = new JScrollPane(tabelaAnotacoes);
        tabelaScrollPane.setPreferredSize(new Dimension(750, 300));

        // Adicionando eventos de interação na tabela
        tabelaAnotacoes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Carrega a anotação ao clicar uma ou duas vezes
                if (e.getClickCount() == 1 || e.getClickCount() == 2) {
                    carregarAnotacao();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarMenuPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarMenuPopup(e);
                }
            }
        });

        // Adicionando componentes ao painel principal
        painelPrincipal.add(tituloLabel);
        painelPrincipal.add(tituloField);
        painelPrincipal.add(Box.createVerticalStrut(10)); // Espaçamento

        painelPrincipal.add(descricaoLabel);
        painelPrincipal.add(descricaoScrollPane);
        painelPrincipal.add(Box.createVerticalStrut(10));

        painelPrincipal.add(dataLabel);
        painelPrincipal.add(dataPanel);
        painelPrincipal.add(Box.createVerticalStrut(10));

        painelPrincipal.add(horaLabel);
        painelPrincipal.add(horaLembrete);
        painelPrincipal.add(Box.createVerticalStrut(10));

        painelPrincipal.add(adicionarButton);
        painelPrincipal.add(editarButton);
        painelPrincipal.add(apagarButton);
        painelPrincipal.add(novaNotaButton);
        painelPrincipal.add(Box.createVerticalStrut(10));

        painelPrincipal.add(new JLabel("Anotações:"));
        painelPrincipal.add(tabelaScrollPane);

        // Adiciona o painel principal ao JFrame
        add(painelPrincipal, BorderLayout.CENTER);

        // Atualiza a tabela de anotações com os dados atuais
        atualizarTabela();
    }

    /*
     * Métodos personalizados 
     * para interação com a interface   
     */
    
     
    // Método para adicionar uma anotação
    private void adicionarAnotacao(ActionEvent e) {
        try {
            Anotacao anotacao = new Anotacao();
            anotacao.setTitulo(tituloField.getText()); // Define o título
            anotacao.setDescricao(descricaoArea.getText()); // Define a descrição
            anotacao.setDataLembrete(getDataSelecionada()); // Define a data do lembrete
            anotacao.setHoraLembrete((Date) horaLembrete.getValue()); // Define a hora do lembrete
            controller.adicionarAnotacao(anotacao); // Adiciona a anotação no controlador
            atualizarTabela(); // Atualiza a tabela de anotações
            JOptionPane.showMessageDialog(this, "Anotação adicionada com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar anotação: " + ex.getMessage());
        }
    }

    // Método para carregar uma anotação selecionada na tabela
    private void carregarAnotacao() {
        int index = tabelaAnotacoes.getSelectedRow();
        if (index >= 0) {
            Anotacao anotacao = anotacoes.get(index);

            // Preenche os campos com os dados da anotação selecionada
            tituloField.setText(anotacao.getTitulo());
            descricaoArea.setText(anotacao.getDescricao());

            // Configura a data nos ComboBoxes
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(anotacao.getDataLembrete());
            diaComboBox.setSelectedItem(String.valueOf(calendar.get(java.util.Calendar.DAY_OF_MONTH)));
            mesComboBox.setSelectedIndex(calendar.get(java.util.Calendar.MONTH));
            anoComboBox.setSelectedItem(String.valueOf(calendar.get(java.util.Calendar.YEAR)));

            // Configura a hora no JSpinner
            horaLembrete.setValue(anotacao.getHoraLembrete());
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma anotação para carregar.");
        }
    }

    // Método para editar uma anotação selecionada na tabela
    private void editarAnotacao(ActionEvent e) {
        int index = tabelaAnotacoes.getSelectedRow();
        if (index >= 0) {
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja salvar as alterações?", "Confirmar edição", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                Anotacao anotacao = anotacoes.get(index);
                anotacao.setTitulo(tituloField.getText()); // Atualiza o título
                anotacao.setDescricao(descricaoArea.getText()); // Atualiza a descrição
                anotacao.setDataLembrete(getDataSelecionada()); // Atualiza a data
                anotacao.setHoraLembrete((Date) horaLembrete.getValue()); // Atualiza a hora

                try {
                    controller.atualizarAnotacao(anotacao); // Atualiza a anotação no controlador
                    atualizarTabela(); // Atualiza a tabela de anotações
                    JOptionPane.showMessageDialog(this, "Anotação editada com sucesso!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao editar anotação: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma anotação para editar.");
        }
    }

    // Método para apagar uma anotação selecionada na tabela
    private void apagarAnotacao(ActionEvent e) {
        int index = tabelaAnotacoes.getSelectedRow();
        if (index >= 0) {
            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja apagar esta anotação?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                Anotacao anotacao = anotacoes.get(index);
                try {
                    controller.deletarAnotacao(anotacao.getId()); // Apaga a anotação no controlador
                    atualizarTabela(); // Atualiza a tabela de anotações
                    JOptionPane.showMessageDialog(this, "Anotação apagada com sucesso!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao apagar anotação: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma anotação para apagar.");
        }
    }

    // Método para mostrar menu popup ao clicar com o botão direito
    private void mostrarMenuPopup(MouseEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editarItem = new JMenuItem("Editar");
        JMenuItem apagarItem = new JMenuItem("Apagar");

        editarItem.addActionListener(event -> editarAnotacao(new ActionEvent(tabelaAnotacoes, ActionEvent.ACTION_PERFORMED, null)));
        apagarItem.addActionListener(event -> apagarAnotacao(new ActionEvent(tabelaAnotacoes, ActionEvent.ACTION_PERFORMED, null)));

        popupMenu.add(editarItem);
        popupMenu.add(apagarItem);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    // Método para atualizar a tabela de anotações
    private void atualizarTabela() {
        tabelaModelo.setRowCount(0); // Limpa a tabela
        try {
            anotacoes = controller.listarAnotacoes(); // Obtém a lista de anotações do controlador
            // Ordena as anotações por data e hora
            Collections.sort(anotacoes, Comparator.comparing(Anotacao::getDataLembrete).thenComparing(Anotacao::getHoraLembrete));
            for (Anotacao anotacao : anotacoes) {
                // Adiciona cada anotação à tabela
                tabelaModelo.addRow(new Object[]{
                        anotacao.getTitulo(),
                        anotacao.getDescricao(),
                        anotacao.getDataLembrete(),
                        anotacao.getHoraLembrete()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar anotações.");
        }
    }

    // Método para limpar os campos para inserir uma nova anotação
    private void limparCampos(ActionEvent e) {
        tituloField.setText("");
        descricaoArea.setText("");
        diaComboBox.setSelectedIndex(0);
        mesComboBox.setSelectedIndex(0);
        anoComboBox.setSelectedIndex(0);
        horaLembrete.setValue(new Date());
    }

    // Método para obter os dias do mês
    private String[] getDias() {
        String[] dias = new String[31];
        for (int i = 0; i < 31; i++) {
            dias[i] = String.valueOf(i + 1); // Preenche o array com dias de 1 a 31
        }
        return dias;
    }

    // Método para obter os meses do ano
    private String[] getMeses() {
        return new String[]{"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    }

    // Método para obter os anos a partir do ano atual
    private String[] getAnos() {
        String[] anos = new String[10];
        int anoAtual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            anos[i] = String.valueOf(anoAtual + i); // Preenche o array com os próximos 10 anos
        }
        return anos;
    }

    // Método para obter a data selecionada nos ComboBoxes
    private Date getDataSelecionada() {
        int dia = Integer.parseInt((String) diaComboBox.getSelectedItem()); // Obtém o dia selecionado
        int mes = mesComboBox.getSelectedIndex(); // Obtém o mês selecionado
        int ano = Integer.parseInt((String) anoComboBox.getSelectedItem()); // Obtém o ano selecionado

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(ano, mes, dia, 0, 0, 0); // Configura o calendário com a data selecionada
        return calendar.getTime(); // Retorna a data
    }
}





