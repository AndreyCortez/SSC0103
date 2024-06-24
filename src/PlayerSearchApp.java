import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerSearchApp extends JFrame {
    private HttpClientSocket cliente;
    private String table = null; 

    private JTextField idField;
    private JTextField ageField;
    private JTextField nameField;
    private JTextField nationalityField;
    private JTextField clubField;
    private JTextArea resultArea;
    private JPanel formPanel; 

    public PlayerSearchApp() {
        setTitle("Player Search Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuração do menu
        createMenuBar();

        // Configuração do formulário
        createFormPanel();

        // Área de resultados
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Adiciona componentes ao frame
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem connectServerItem = new JMenuItem("Conectar ao Servidor");
        JMenuItem loadFileItem = new JMenuItem("Carregar arquivo FIFA");
        JMenuItem listAllPlayersItem = new JMenuItem("Listar Todos os Jogadores");

        fileMenu.add(connectServerItem);
        fileMenu.add(loadFileItem);
        fileMenu.add(listAllPlayersItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        connectServerItem.addActionListener(e -> connectToServer());
        loadFileItem.addActionListener(e -> loadFIFAFile());
        listAllPlayersItem.addActionListener(e -> listAllPlayers());
    }

    private void createFormPanel() {
        formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Idade:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Nome do Jogador:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Nacionalidade:"));
        nationalityField = new JTextField();
        formPanel.add(nationalityField);

        formPanel.add(new JLabel("Nome do Clube:"));
        clubField = new JTextField();
        formPanel.add(clubField);

        JButton searchButton = new JButton("Buscar");
        formPanel.add(searchButton);

        searchButton.addActionListener(e -> searchPlayer());
    }

    private void connectToServer() {
        JTextField addressField = new JTextField();
        JTextField portField = new JTextField();
        Object[] message = {
            "Address:", addressField,
            "Port:", portField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Conectar ao servidor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String address = addressField.getText();
            int port = Integer.parseInt(portField.getText());
            cliente = new HttpClientSocket(address, port);
            JOptionPane.showMessageDialog(null, "Conectado ao servidor em: " + address + ":" + port);
        }
    }

    private void loadFIFAFile() {
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Por favor conecte com um servidor primeiro.");
            return;
        }


        Map<String, List<String>> parametros = new HashMap<>();
        parametros.put("operation", Arrays.asList("disponiveis"));
        JsonNode respostaJson = cliente.enviarRequest("/", parametros);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        if (respostaJson.isArray()) {
            for (JsonNode item : respostaJson) {
                listModel.addElement(item.toString());
            }
        }

        JList<String> itemList = new JList<>(listModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        int result = JOptionPane.showConfirmDialog(
            null,
            new JScrollPane(itemList),
            "Selecione um item",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String selectedItem = itemList.getSelectedValue();
            if (selectedItem != null) {
                JOptionPane.showMessageDialog(null, "Você selecionou: " + selectedItem);
                table = selectedItem;
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
            }
        }
    }

    private void listAllPlayers() {
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Por favor conecte com um servidor primeiro.");
            return;
        }
        
        if (table == null) {
            JOptionPane.showMessageDialog(null, "Por favor selecione um arquivo de dados.");
            return;
        }

        Map<String, List<String>> parametros = new HashMap<>();
        parametros.put("operation", Arrays.asList("todos"));
        parametros.put("table", Arrays.asList(table));
        JsonNode respostaJson = cliente.enviarRequest("/", parametros);

        StringBuilder resultado = new StringBuilder();
        if (respostaJson.isArray()) {
            for (JsonNode item : respostaJson) {
                resultado.append(item.toString()).append("\n");
            }
        }

        resultArea.setText(resultado.toString());
    }

    private void searchPlayer() {
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Por favor conecte com um servidor primeiro.");
            return;
        }

        
        if (table == null) {
            JOptionPane.showMessageDialog(null, "Por favor selecione um arquivo de dados.");
            return;
        }

        Map<String, List<String>> parametros = new HashMap<>();
        parametros.put("operation", Arrays.asList("buscar"));

        List<String> col = new ArrayList<>();
        List<String> val_col = new ArrayList<>();

        if (!idField.getText().isEmpty()) {
            col.add("id");
            val_col.add(idField.getText());
        }
        if (!ageField.getText().isEmpty()) {
            col.add("idade");
            val_col.add(ageField.getText());
        }
        if (!nameField.getText().isEmpty()) {
            col.add("nomeJogador");
            val_col.add(nameField.getText());
        }
        if (!nationalityField.getText().isEmpty()) {
            col.add("nacionalidade");
            val_col.add(nationalityField.getText());
        }
        if (!clubField.getText().isEmpty()) {
            col.add("clubeJogador");
            val_col.add(clubField.getText());
        }

        parametros.put("col", col);
        parametros.put("val_col", val_col);
        parametros.put("table", Arrays.asList(table));

        JsonNode respostaJson = cliente.enviarRequest("/", parametros);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<List<String>> originalList = new ArrayList<>();

        if (respostaJson.isArray()) {
            for (JsonNode item : respostaJson) {
                List<String> subList = new ArrayList<>();
                for (JsonNode subItem : item) {
                    subList.add(subItem.asText());
                }
                originalList.add(subList);
                listModel.addElement(String.join(", ", subList));
            }
        }

        JList<String> itemList = new JList<>(listModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        int result = JOptionPane.showConfirmDialog(
            null,
            new JScrollPane(itemList),
            "Selecione um item",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex != -1) {
                List<String> selectedItem = originalList.get(selectedIndex);
                JOptionPane.showMessageDialog(null, "Você selecionou: " + selectedItem);
                showEditDialog(selectedItem);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum item selecionado.");
            }
        }
    }

    private void showEditDialog(List<String> selectedItem) {
        JDialog editDialog = new JDialog(this, "Editar jogador", true);
        editDialog.setSize(300, 300);
        editDialog.setLayout(new GridLayout(7, 2)); // Ajustado para 7 linhas

        JTextField idFieldEdit = new JTextField(selectedItem.get(0));
        JTextField ageFieldEdit = new JTextField(selectedItem.get(1));
        JTextField nameFieldEdit = new JTextField(selectedItem.get(2));
        JTextField nationalityFieldEdit = new JTextField(selectedItem.get(3));
        JTextField clubFieldEdit = new JTextField(selectedItem.get(4));

        editDialog.add(new JLabel("ID:"));
        editDialog.add(idFieldEdit);
        editDialog.add(new JLabel("Idade:"));
        editDialog.add(ageFieldEdit);
        editDialog.add(new JLabel("Nome do Jogador:"));
        editDialog.add(nameFieldEdit);
        editDialog.add(new JLabel("Nacionalidade:"));
        editDialog.add(nationalityFieldEdit);
        editDialog.add(new JLabel("Nome do Clube:"));
        editDialog.add(clubFieldEdit);

        JButton finalizeEditButton = new JButton("Finalizar Edição");
        finalizeEditButton.addActionListener(e -> finalizeEdit(idFieldEdit, ageFieldEdit, nameFieldEdit, nationalityFieldEdit, clubFieldEdit, editDialog));

        JButton removePlayerButton = new JButton("Remover Jogador");
        removePlayerButton.addActionListener(e -> removePlayer(idFieldEdit, ageFieldEdit, nameFieldEdit, nationalityFieldEdit, clubFieldEdit, editDialog));

        editDialog.add(new JLabel()); // Placeholder para manter o botão na posição correta
        editDialog.add(finalizeEditButton);
        editDialog.add(new JLabel()); // Placeholder para manter o botão na posição correta
        editDialog.add(removePlayerButton);

        editDialog.setLocationRelativeTo(this);
        editDialog.setVisible(true);
    }

    private void finalizeEdit(JTextField idFieldEdit, JTextField ageFieldEdit, JTextField nameFieldEdit, JTextField nationalityFieldEdit, JTextField clubFieldEdit, JDialog editDialog) {
        Map<String, List<String>> parametros = new HashMap<>();
        parametros.put("operation", Arrays.asList("apagar"));

        List<String> col = new ArrayList<>();
        List<String> val_col = new ArrayList<>();
        List<String> val = new ArrayList<>();

        if (!idFieldEdit.getText().isEmpty()) {
            col.add("id");
            val_col.add(idFieldEdit.getText());
            val.add(idFieldEdit.getText());
        } else {
            warn("campo vazio detectado, substituição cancelada");
            editDialog.dispose();
        }
        if (!ageFieldEdit.getText().isEmpty()) {
            col.add("idade");
            val_col.add(ageFieldEdit.getText());
            val.add(ageFieldEdit.getText());
        } else {
            warn("campo vazio detectado, substituição cancelada");
            editDialog.dispose();
        }
        if (!nameFieldEdit.getText().isEmpty()) {
            col.add("nomeJogador");
            val_col.add(nameFieldEdit.getText());
            val.add(nameFieldEdit.getText());
        } else {
            warn("campo vazio detectado, substituição cancelada");
            editDialog.dispose();
        }
        if (!nationalityFieldEdit.getText().isEmpty()) {
            col.add("nacionalidade");
            val_col.add(nationalityFieldEdit.getText());
            val.add(nationalityFieldEdit.getText());
        } else {
            warn("campo vazio detectado, substituição cancelada");
            editDialog.dispose();
        }
        if (!clubFieldEdit.getText().isEmpty()) {
            col.add("clubeJogador");
            val_col.add(clubFieldEdit.getText());
            val.add(clubFieldEdit.getText());
        } else {
            warn("campo vazio detectado, substituição cancelada");
            editDialog.dispose();
        }

        parametros.put("col", col);
        parametros.put("val_col", val_col);
        parametros.put("table", Arrays.asList(table));
        cliente.enviarRequest("/", parametros);

        parametros = new HashMap<>();
        parametros.put("operation", Arrays.asList("inserir"));
        parametros.put("val", val);
        parametros.put("table", Arrays.asList(table));
        cliente.enviarRequest("/", parametros);

        editDialog.dispose();
    }

    private void removePlayer(JTextField idFieldEdit, JTextField ageFieldEdit, JTextField nameFieldEdit, JTextField nationalityFieldEdit, JTextField clubFieldEdit, JDialog editDialog) {
        Map<String, List<String>> parametros = new HashMap<>();
        parametros.put("operation", Arrays.asList("apagar"));

        List<String> col = new ArrayList<>();
        List<String> val_col = new ArrayList<>();

        if (!idFieldEdit.getText().isEmpty()) {
            col.add("id");
            val_col.add(idFieldEdit.getText());
        }
        if (!ageFieldEdit.getText().isEmpty()) {
            col.add("idade");
            val_col.add(ageFieldEdit.getText());
        }
        if (!nameFieldEdit.getText().isEmpty()) {
            col.add("nomeJogador");
            val_col.add(nameFieldEdit.getText());
        }
        if (!nationalityFieldEdit.getText().isEmpty()) {
            col.add("nacionalidade");
            val_col.add(nationalityFieldEdit.getText());
        }
        if (!clubFieldEdit.getText().isEmpty()) {
            col.add("clubeJogador");
            val_col.add(clubFieldEdit.getText());
        }

        parametros.put("col", col);
        parametros.put("val_col", val_col);
        parametros.put("table", Arrays.asList(table));
        cliente.enviarRequest("/", parametros);

        editDialog.dispose();
    }

    private void warn(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlayerSearchApp().setVisible(true));
    }
}
