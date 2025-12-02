import java.util.InputMismatchException;
import java.util.Scanner;

public class exercice1 {

    static int[] tableau = {17, 12, 15, 38, 29, 157, 89, -22, 0, 5};

    static int division(int indice, int diviseur) {
        return tableau[indice] / diviseur;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean calculValide = false;

        while (!calculValide) {
            try {
                System.out.print("Entrez l'indice de l'entier à diviser : ");
                int x = sc.nextInt();

                System.out.print("Entrez le diviseur : ");
                int y = sc.nextInt();

                // Tentative de calcul
                int resultat = division(x, y);

                System.out.println("Le résultat de la division est : " + resultat);
                calculValide = true; // FIN, car le calcul a réussi

            } catch (InputMismatchException e) {
                System.out.println("Erreur : veuillez entrer un nombre entier !");
                sc.nextLine(); // vider le buffer

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Erreur : indice invalide (0 à " + (tableau.length - 1) + ").");

            } catch (ArithmeticException e) {
                System.out.println("Erreur : division par zéro impossible !");

            } catch (Exception e) {
                System.out.println("Erreur inattendue : " + e.getMessage());
            }

            System.out.println("--------------------------------------------------");
        }

        sc.close();
    }
}
