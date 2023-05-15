import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Sudoku {

        private int[][] SpielFeld;
        private boolean gameover;

        public Sudoku(String Dateipfad) {
            SpielFeld = new int[9][9];
            gameover = false;
            importDatei(Dateipfad);
        }

    private void importDatei(String Dateipfad) {
        try (Scanner scanner = new Scanner(new File(Dateipfad))) {
            for (int Reihe = 0; Reihe < 9; Reihe++) {
                for (int Spalte = 0; Spalte < 9; Spalte++) {
                    if (scanner.hasNextInt()) {
                        SpielFeld[Reihe][Spalte] = scanner.nextInt();
                    } else {
                        scanner.next(); // Skip empty line
                        Spalte--; // Decrement column to retry reading current position
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Datei wurde nicht gefunden: " + Dateipfad);
        }
    }
    public boolean gültigerZug(int Reihe, int Spalte, int Wert) {
        // Überprüfen, ob der Wert bereits in der Zeile oder Spalte vorhanden ist
        for (int i = 0; i < 9; i++) {
            if (SpielFeld[Reihe][i] == Wert || SpielFeld[i][Spalte] == Wert) {
                return false;
            }
        }

        // Überprüfen, ob der Wert bereits im 3x3-Quadrat vorhanden ist
        int boxRow = Reihe - Reihe % 3;
        int boxCol = Spalte - Spalte % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (SpielFeld[i][j] == Wert) {
                    return false;
                }
            }
        }

        return true;
    }
    public void Spielzug(int Reihe, int Spalte, int Wert) {
        if (SpielFeld[Reihe][Spalte] == 0 && gültigerZug(Reihe, Spalte, Wert)) {
            SpielFeld[Reihe][Spalte] = Wert;
        }
    }

    public boolean gelöst() {
        for (int Reihe = 0; Reihe < SpielFeld.length; Reihe++) {
            for (int Spalte = 0; Spalte < SpielFeld[Reihe].length; Spalte++) {
                if (SpielFeld[Reihe][Spalte] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void AusgabeSpielfeld() {
        System.out.println("+-------+-------+-------+");
        for (int Reihe = 0; Reihe < SpielFeld.length; Reihe++) {
            System.out.print("| ");
            for (int Spalte = 0; Spalte < SpielFeld[Reihe].length; Spalte++) {
                if (Spalte == 3 || Spalte == 6) {
                    System.out.print("| ");
                }
                System.out.print(SpielFeld[Reihe][Spalte] == 0 ? " " : Integer.toString(SpielFeld[Reihe][Spalte]));
                System.out.print(' ');
            }
            System.out.print("|\n");
            if (Reihe == 2 || Reihe == 5) {
                System.out.println("+-------+-------+-------+");
            }
        }
        System.out.println("+-------+-------+-------+");
    }


    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (gameover == false) {
            System.out.println("Derzeitiges Spielfeld:");
            AusgabeSpielfeld();
            System.out.print("Bitte gebe eine Reihe an (1-9) : ");
            int Reihe = scanner.nextInt() - 1;
            System.out.print("Bitte gebe eine Zeile an (1-9) : ");
            int Spalte = scanner.nextInt() - 1;
            System.out.print("Welcher Wert soll gesetzt werden ? (1-9): ");
            int Wert = scanner.nextInt();
            Spielzug(Reihe, Spalte, Wert);
            if (gelöst()) {
                System.out.println("Du hast das Sudoku gelöst ! Glückwunsch");
                gameover = true;
            } else {
                System.out.println("Dein Zug ist nicht gültig, bitte versuche es erneut");
            }
        }
        scanner.close();
    }
    public void Backtracking() {
        solveHelper(0, 0);
    }

    private boolean solveHelper(int Reihe, int Spalte) {
        if (Spalte == 9) {
            Spalte = 0;
            Reihe++;
            if (Reihe == 9) {
                return true; // Board komplett gefüllt
            }
        }

        if (SpielFeld[Reihe][Spalte] != 0) {
            return solveHelper(Reihe, Spalte + 1); // bereits belegtes Feld überspringen
        }

        for (int Wert = 1; Wert <= 9; Wert++) {
            if (gültigerZug(Reihe, Spalte, Wert)) {
                SpielFeld[Reihe][Spalte] = Wert;
                if (solveHelper(Reihe, Spalte + 1)) {
                    return true; // rekursiver Aufruf hat erfolgreich zum Ende geführt
                }
                SpielFeld[Reihe][Spalte] = 0; // Backtracking, falls rekursiver Aufruf fehlschlägt
            }
        }

        return false; // keine gültige Zahl gefunden, Backtracking zum letzten Aufruf
    }



}
