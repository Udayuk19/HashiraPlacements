import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.LinkedHashMap;
import java.util.Map;
import com.google.gson.*;

public class HashiraPlacements {

    public static void main(String[] args) {
    try {
        if (args.length < 1) {
            System.out.println("Please provide input JSON filename as argument.");
            return;
        }
        String filename = args[0];
        
        JsonObject json = JsonParser.parseReader(new FileReader(filename)).getAsJsonObject();

        JsonObject keys = json.getAsJsonObject("keys");
        int n = keys.get("n").getAsInt();
        int k = keys.get("k").getAsInt();
        int degree = k - 1;

        LinkedHashMap<BigDecimal, BigDecimal> roots = new LinkedHashMap<>();
        for (int i = 1; i <= n; i++) {
            String key = String.valueOf(i);
            if (!json.has(key)) continue;

            JsonObject root = json.getAsJsonObject(key);
            String baseStr = root.get("base").getAsString();
            String valueStr = root.get("value").getAsString();

            int base = Integer.parseInt(baseStr);
            BigInteger value = new BigInteger(valueStr, base);
            roots.put(new BigDecimal(i), new BigDecimal(value));

            if (roots.size() >= k) break;
        }

        BigDecimal[][] A = new BigDecimal[k][k];
        BigDecimal[] B = new BigDecimal[k];
        int row = 0;
        for (Map.Entry<BigDecimal, BigDecimal> entry : roots.entrySet()) {
            BigDecimal x = entry.getKey();
            BigDecimal y = entry.getValue();

            for (int col = 0; col < k; col++) {
                A[row][col] = x.pow(degree - col);
            }
            B[row] = y;
            row++;
        }

        BigDecimal[] coefficients = gaussianElimination(A, B);
        System.out.println("Constant term c = " + coefficients[k - 1]);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    // Gaussian elimination method
    private static BigDecimal[] gaussianElimination(BigDecimal[][] A, BigDecimal[] B) {
        int n = B.length;
        MathContext mc = new MathContext(50);

        // Forward elimination
        for (int i = 0; i < n; i++) {
            // Make the diagonal element 1
            BigDecimal diag = A[i][i];
            for (int j = i; j < n; j++) {
                A[i][j] = A[i][j].divide(diag, mc);
            }
            B[i] = B[i].divide(diag, mc);

            // Eliminate below
            for (int k = i + 1; k < n; k++) {
                BigDecimal factor = A[k][i];
                for (int j = i; j < n; j++) {
                    A[k][j] = A[k][j].subtract(factor.multiply(A[i][j], mc), mc);
                }
                B[k] = B[k].subtract(factor.multiply(B[i], mc), mc);
            }
        }

        // Back substitution
        BigDecimal[] x = new BigDecimal[n];
        for (int i = n - 1; i >= 0; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j < n; j++) {
                sum = sum.add(A[i][j].multiply(x[j], mc), mc);
            }
            x[i] = B[i].subtract(sum, mc);
        }

        return x;
    }
}
