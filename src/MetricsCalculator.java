import java.util.*;

public class MetricsCalculator {

    private int operators = 0; // 3 linia
    private int uniqueOperators = 0; // 2 linia

    // dla każdej Function:
    private int operands = 0; // Number of halstead operands: 20
    private int uniqueOperands = 0; //Number of different halstead operands:

    private static MetricsCalculator ourInstance = new MetricsCalculator();

    public static MetricsCalculator getInstance() {
        return ourInstance;
    }

    private MetricsCalculator() {
    }

    private void calculateMetrics(String output) {
        List<String> lines = Arrays.asList(output.split("\\n"));

        uniqueOperators = getValueFromLine(lines.get(1));
        operators = getValueFromLine(lines.get(2));

        System.out.println("\n\n");

        String line;

        for(int i = 7; i < lines.size(); ++i) {
            line = lines.get(i);
            if(line.contains("Function")) {
                operands += getValueFromLine(lines.get(i + 4));
                uniqueOperands += getValueFromLine(lines.get(i + 3));
                i += 3;
            }
        }
    }

    public Map<String, Double> giveMetrics(String output) {
        Map<String, Double> metrics = new HashMap<>();

        calculateMetrics(output);

        Double operatorsD = (double) operators;
        Double uniqueOperatorsD = (double) uniqueOperators;
        Double operandsD = (double) operands;
        Double uniqueOperandsD = (double) uniqueOperands;

        metrics.put("operators", operatorsD);
        metrics.put("uniqueOperators", uniqueOperatorsD);
        metrics.put("operands", operandsD);
        metrics.put("uniqueOperands", uniqueOperandsD);

        metrics.put("dictionary", uniqueOperators + uniqueOperandsD);
        metrics.put("length", operatorsD + operandsD);
        metrics.put("volume",
                metrics.get("length")
                        * (Math.log(uniqueOperators + uniqueOperands)
                        / Math.log(2)));
        metrics.put("difficulty", (uniqueOperatorsD
                * operandsD)/(2 * uniqueOperandsD));
        metrics.put("work", metrics.get("volume")
                * metrics.get("difficulty"));
        metrics.put("errors", metrics.get("volume") / 3000);

        return metrics;
    }

    private int getValueFromLine(String line) {
        return Integer.parseInt(line.replaceAll("\\D+",""));
    }
}
