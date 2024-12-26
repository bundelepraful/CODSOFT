import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CurrencyConverter {

    // Main method to execute the program
    public static void main(String[] args) {
        try {
            // Greet the user
            greetUser();

            // Get user input: base currency, target currency, and the amount
            String baseCurrency = getBaseCurrency();
            String targetCurrency = getTargetCurrency();
            double amount = getAmountToConvert(baseCurrency);

            // Fetch the exchange rate from the API
            double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);

            // Perform the currency conversion
            double convertedAmount = convertCurrency(amount, exchangeRate);

            // Display the result in a human-friendly format
            displayConvertedAmount(amount, baseCurrency, convertedAmount, targetCurrency);

        } catch (Exception e) {
            // In case of any errors (like invalid currency codes or connection issues)
            System.out.println("Oops! Something went wrong: " + e.getMessage());
        }
    }

    // Greet the user with a friendly message
    private static void greetUser() {
        System.out.println("Welcome to the Currency Converter!");
        System.out.println("You can convert any currency to another.");
        System.out.println("Let's get started!");
    }

    // Ask the user for the base currency they want to convert from
    private static String getBaseCurrency() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("\nEnter the base currency (e.g., USD, EUR, GBP, INR): ");
        return scanner.nextLine().toUpperCase();
    }

    // Ask the user for the target currency they want to convert to
    private static String getTargetCurrency() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("\nEnter the target currency (e.g., USD, EUR, GBP, INR): ");
        return scanner.nextLine().toUpperCase();
    }

    // Get the amount the user wants to convert
    private static double getAmountToConvert(String baseCurrency) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("\nEnter the amount in " + baseCurrency + ": ");
        return scanner.nextDouble();
    }

    // Fetch the exchange rate between the base and target currencies from the API
    private static double fetchExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        String apiKey = "YOUR_API_KEY";  // Replace with your own API key
        String apiUrl = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", apiKey, baseCurrency);

        // Create a URL object to send the request
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Read the response from the API
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the JSON response to get the exchange rate
        JSONObject jsonResponse = new JSONObject(response.toString());

        if (!jsonResponse.getString("result").equals("success")) {
            throw new Exception("Failed to retrieve exchange rate data. Please check the currency codes.");
        }

        // Extract the exchange rate for the target currency
        JSONObject conversionRates = jsonResponse.getJSONObject("conversion_rates");
        if (!conversionRates.has(targetCurrency)) {
            throw new Exception("Target currency not found in the conversion rates.");
        }

        // Return the exchange rate for the target currency
        return conversionRates.getDouble(targetCurrency);
    }

    // Convert the amount using the fetched exchange rate
    private static double convertCurrency(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }

    // Display the result in a readable and friendly format
    private static void displayConvertedAmount(double amount, String baseCurrency, double convertedAmount, String targetCurrency) {
        System.out.printf("\n%.2f %s is equal to %.2f %s.%n", amount, baseCurrency, convertedAmount, targetCurrency);
    }
}

