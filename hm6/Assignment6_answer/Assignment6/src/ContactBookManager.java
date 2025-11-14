import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactBookManager extends JFrame {
    private JTable contactTable;
    private DefaultTableModel tableModel;
    private JTextField tfName;
    private JTextField tfStreet;
    private JTextField tfCity;
    private JTextField tfState;
    private JTextField tfPhone;
    private JTextField tfEmail;
    private static final String DEFAULT_DIR = System.getProperty("user.dir");

    public ContactBookManager() {
        super("Contact Book Manager");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        String[] columnNames = {"Name", "Street", "City", "State", "Phone", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        contactTable = new JTable(tableModel);
        contactTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        contactTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(contactTable);
        scrollPane.setBorder(new EmptyBorder(8, 8, 8, 8));
        add(scrollPane, BorderLayout.CENTER);

        JPanel west = new JPanel(new BorderLayout(0, 8));
        west.setBorder(new EmptyBorder(8, 8, 8, 0));
        west.add(buildFormPanel(), BorderLayout.NORTH);
        west.add(buildActionPanel(), BorderLayout.CENTER);
        add(west, BorderLayout.WEST);

        setJMenuBar(buildMenuBar());

        setSize(1000, 560);
        setLocationRelativeTo(null);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem miOpen = new JMenuItem("Open...");
        JMenuItem miSave = new JMenuItem("Save...");
        JMenuItem miExit = new JMenuItem("Exit");
        miOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        miExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        miOpen.addActionListener(e -> loadContacts());
        miSave.addActionListener(e -> saveContacts());
        miExit.addActionListener(e -> System.exit(0));
        fileMenu.add(miOpen);
        fileMenu.add(miSave);
        fileMenu.addSeparator();
        fileMenu.add(miExit);
        menuBar.add(fileMenu);
        return menuBar;
    }

    private JPanel buildFormPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("New Contact"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 6, 4, 6);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        tfName = new JTextField(16);
        tfStreet = new JTextField(16);
        tfCity = new JTextField(16);
        tfState = new JTextField(16);
        tfPhone = new JTextField(16);
        tfEmail = new JTextField(16);

        String[] labels = {"Name:", "Street:", "City:", "State:", "Phone:", "Email:"};
        JTextField[] fields = {tfName, tfStreet, tfCity, tfState, tfPhone, tfEmail};

        for (int i = 0; i < labels.length; i++) {
            gc.gridx = 0; gc.gridy = i;
            gc.weightx = 0; gc.fill = GridBagConstraints.NONE;
            p.add(new JLabel(labels[i]), gc);
            gc.gridx = 1;
            gc.weightx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
            p.add(fields[i], gc);
        }

        tfEmail.addActionListener(e -> onAdd());
        return p;
    }

    private JPanel buildActionPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Actions"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 6, 4, 6);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        JButton btnAdd = new JButton("Add");
        JButton btnRemove = new JButton("Remove Selected");
        JButton btnClear = new JButton("Clear");

        btnAdd.addActionListener(e -> onAdd());
        btnRemove.addActionListener(e -> onRemove());
        btnClear.addActionListener(e -> onClear());

        gc.gridx = 0; gc.gridy = 0; p.add(btnAdd, gc);
        gc.gridy = 1; p.add(btnRemove, gc);
        gc.gridy = 2; p.add(btnClear, gc);

        return p;
    }

    private void onAdd() {
        String name = tfName.getText().trim();
        String street = tfStreet.getText().trim();
        String city = tfCity.getText().trim();
        String state = tfState.getText().trim();
        String phone = tfPhone.getText().trim();
        String email = tfEmail.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            tfName.requestFocus();
            return;
        }
        Contact c = new Contact(name, street, city, state, phone, email);
        tableModel.addRow(c.toRow());
        clearInputs();
    }

    private void onRemove() {
        int[] rows = contactTable.getSelectedRows();
        if (rows.length == 0) {
            JOptionPane.showMessageDialog(this, "No rows selected.");
            return;
        }
        for (int i = rows.length - 1; i >= 0; i--) {
            tableModel.removeRow(contactTable.convertRowIndexToModel(rows[i]));
        }
    }

    private void onClear() {
        if (tableModel.getRowCount() == 0) return;
        int ok = JOptionPane.showConfirmDialog(this, "Clear all contacts?", "Confirm",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok == JOptionPane.OK_OPTION) {
            tableModel.setRowCount(0);
        }
    }

    private void clearInputs() {
        tfName.setText("");
        tfStreet.setText("");
        tfCity.setText("");
        tfState.setText("");
        tfPhone.setText("");
        tfEmail.setText("");
        tfName.requestFocus();
    }

    private void saveContacts() {
        JFileChooser chooser = new JFileChooser(DEFAULT_DIR);
        chooser.setDialogTitle("Save contacts");
        chooser.setSelectedFile(new File("contacts.csv"));
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".csv")) {
                file = new File(file.getParentFile(), file.getName() + ".csv");
            }
            try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {
                int rows = tableModel.getRowCount();
                int cols = tableModel.getColumnCount();
                for (int r = 0; r < rows; r++) {
                    List<String> fields = new ArrayList<>(cols);
                    for (int c = 0; c < cols; c++) {
                        Object v = tableModel.getValueAt(r, c);
                        fields.add(v == null ? "" : v.toString());
                    }
                    pw.println(CsvUtils.join(fields));
                }
                JOptionPane.showMessageDialog(this, "Saved: " + file.getName());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to save: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadContacts() {
        JFileChooser chooser = new JFileChooser(DEFAULT_DIR);
        chooser.setDialogTitle("Open contacts");
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            List<String[]> rows = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] arr = CsvUtils.parse(line);
                    if (arr.length == 1) {
                        String[] byTab = line.split("\\t");
                        if (byTab.length > 1) arr = byTab;
                        else {
                            String[] bySpace = line.split("\\s+");
                            if (bySpace.length > 1) arr = bySpace;
                        }
                    }
                    rows.add(arr);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to open: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            tableModel.setRowCount(0);
            for (String[] arr : rows) {
                String[] six = new String[6];
                for (int i = 0; i < six.length; i++) {
                    six[i] = (i < arr.length) ? arr[i] : "";
                }
                tableModel.addRow(six);
            }
            JOptionPane.showMessageDialog(this, "Loaded: " + file.getName());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactBookManager().setVisible(true));
    }
}
