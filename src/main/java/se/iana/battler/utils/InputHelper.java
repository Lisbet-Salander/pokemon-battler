package se.iana.battler.utils;

import java.util.Scanner;

public class InputHelper {

    // Metod för att säkert läsa in ett heltal inom ett specifikt intervall
    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isBlank()) {
                System.out.println("Fel: inmatningen får inte vara tom.");
                continue;
            }

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }

                System.out.printf("Fel: ange ett tal mellan %d och %d.%n", min, max);

            } catch (NumberFormatException e) {
                // Fångar kraschen om användaren skriver bokstäver istället för siffror
                System.out.println("Fel: det där var inget giltigt heltal. Försök igen.");
            }
        }
    }

    // Metod för att säkert läsa in text som inte är tom och inte för lång
    public static String readString(Scanner scanner, String prompt, int maxLength) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isBlank()) {
                System.out.println("Fel: texten får inte vara tom.");
                continue;
            }
            if (input.length() > maxLength) {
                System.out.printf("Fel: texten är för lång (max %d tecken).%n", maxLength);
                continue;
            }

            return input;
        }
    }
}