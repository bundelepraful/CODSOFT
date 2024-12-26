import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {

    // Method to play a single round of the game
    public static void playRound() {
        // Create a Scanner object for reading user input
        Scanner scanner = new Scanner(System.in);
        
        // Generate a random number between 1 and 100
        Random random = new Random();
        int numberToGuess = random.nextInt(100) + 1;
        
        int attempts = 0;
        int maxAttempts = 6;
        boolean isCorrect = false;

        System.out.println("I have selected a number between 1 and 100. Try to guess it!");
        
        // Start the guessing game
        while (attempts < maxAttempts && !isCorrect) {
            attempts++;
            System.out.print("Attempt " + attempts + " of " + maxAttempts + ": Enter your guess: ");
            int userGuess = scanner.nextInt();
            
            if (userGuess < numberToGuess) {
                System.out.println("Too low! Try again.");
            } else if (userGuess > numberToGuess) {
                System.out.println("Too high! Try again.");
            } else {
                System.out.println("Correct! You've guessed the number in " + attempts + " attempts.");
                isCorrect = true;
            }
        }

        if (!isCorrect) {
            System.out.println("Sorry, you've used all your attempts. The correct number was: " + numberToGuess);
        }
    }

    // Main method to control the flow of the game
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;
        int totalRoundsWon = 0;
        int totalRoundsPlayed = 0;

        while (playAgain) {
            totalRoundsPlayed++;
            System.out.println("\nStarting a new round...");
            playRound();

            // Ask if the user wants to play again
            System.out.print("Do you want to play again? (yes/no): ");
            String userResponse = scanner.next();

            if (userResponse.equalsIgnoreCase("yes")) {
                playAgain = true;
            } else {
                playAgain = false;
                System.out.println("\nThanks for playing!");
                System.out.println("You played " + totalRoundsPlayed + " round(s) in total.");
            }
        }
    }
}
