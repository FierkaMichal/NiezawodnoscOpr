import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

public class App extends JFrame {
    private JPanel panel1;
    private JTextArea resultText;
    private JButton fileButton;

    public App() {
        setContentPane(panel1);
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getCode();
    }

    public void getCode() {
        fileButton.addActionListener(e -> onButtonClick());
    }

    private void onButtonClick() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        File file = null;
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            file = jfc.getSelectedFile();
        }

        String output = null;
        try {
            output = CCSMRunner.execCmd("ccsm --exclude-file=.h$$ --output-metrics=OP_CNT," +
                    "OP_TYPES_CNT,HALSTEAD_OPERAND_UNIQUE_CNT,HALSTEAD_OPERAND_CNT "
                    + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Double> metrics = MetricsCalculator.getInstance().giveMetrics(output);

        DecimalFormat f = new DecimalFormat("#.##");

        resultText.setText("MIARY HALSTEADA:\n");
        resultText.append("Liczba unikalnych operatorów: " + metrics.get("uniqueOperators"));
        resultText.append("\nLiczba unikalnych operandów: " + metrics.get("uniqueOperands"));
        resultText.append("\nŁączna liczba operatorów: " + metrics.get("operators"));
        resultText.append("\nŁączna liczba operandów: " + metrics.get("operands"));
        resultText.append("\nSłownik: " + metrics.get("dictionary"));
        resultText.append("\nDługość: " + metrics.get("length"));
        resultText.append("\nObjętość: " + f.format(metrics.get("volume")));
        resultText.append("\nTrudność: " + f.format(metrics.get("difficulty")));
        resultText.append("\nWymagany nakład pracy: " + f.format(metrics.get("work")));
        resultText.append("\nPrzewidywalna liczba błędów: " + f.format(metrics.get("errors")));
    }
}
