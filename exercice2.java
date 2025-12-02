import java.util.Scanner;
import java.util.InputMismatchException;

class NombreOptionsInvalideException extends Exception {
    public NombreOptionsInvalideException(int n) {
        super("Le nombre d'options (" + n + ") doit être strictement supérieur à 1.");
    }
}

class ChoixHorsIntervalleException extends Exception {
    public ChoixHorsIntervalleException(int choix, int max) {
        super("Le choix " + choix + " est en dehors de l'intervalle valide [1 ; " + max + "].");
    }
}

class SaisieNonEntiereException extends Exception {
    public SaisieNonEntiereException() {
        super("Erreur de saisie : Veuillez entrer un nombre entier pour votre choix.");
    }
}

class Menu {
    private final String[] options;
    private final int nombreOptions;
    // On utilise un seul Scanner pour la classe
    private final Scanner sc = new Scanner(System.in); 
    
    /**
     * Constructeur : initialise le menu avec un tableau d'options.
     */
    public Menu(String[] options) {
        if (options == null) {
            this.options = new String[0];
        } else {
            this.options = options;
        }
        this.nombreOptions = this.options.length;
    }

    private void afficherMenu() {
        if (nombreOptions == 0) {
            System.out.println("❌ Le menu est vide.");
            return;
        }
        System.out.println("\n===== Menu Interactif =====");
        for (int i = 0; i < nombreOptions; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Entrez votre choix (1 à " + nombreOptions + ") : ");
    }
    
    /**
     * Saisit le choix et lève des exceptions spécifiques (Q1).
     */
    private int saisirChoixInterne() 
            throws NombreOptionsInvalideException, ChoixHorsIntervalleException, SaisieNonEntiereException {
        
        // Gère le cas où n <= 1
        if (nombreOptions <= 1) {
            throw new NombreOptionsInvalideException(nombreOptions);
        }
        
        int choix;
        try {
            // Tente de lire un entier
            choix = sc.nextInt();
        } catch (InputMismatchException e) {
            // Gère la Saisie Non Entière
            sc.nextLine(); // Consomme l'entrée invalide
            throw new SaisieNonEntiereException();
        }
        
        // Gère le choix hors intervalle
        if (choix < 1 || choix > nombreOptions) {
            throw new ChoixHorsIntervalleException(choix, nombreOptions);
        }
        
        return choix;
    }

    /**
     * Méthode principale (Q3 & Q4) : affiche le menu et gère les exceptions pour garantir une saisie valide.
     * @return Le choix valide effectué par l'utilisateur.
     */
    public int faireChoix() {
        int choix = -1;
        boolean saisieValide = false;
        
        do {
            try {
                afficherMenu();
                choix = saisirChoixInterne();
                saisieValide = true;
                
            } catch (NombreOptionsInvalideException e) {
                System.out.println("❌ Erreur : " + e.getMessage());
                return -1; // Arrêt si le menu n'est pas valide par conception
                
            } catch (ChoixHorsIntervalleException e) {
                System.out.println("⚠️ " + e.getMessage());
                
            } catch (SaisieNonEntiereException e) {
                System.out.println("⚠️ " + e.getMessage());
            }
            
            if (!saisieValide) {
                System.out.println("--------------------------");
            }
        } while (!saisieValide);
        
        return choix;
    }
    
    // Fermeture du scanner pour un nettoyage propre
    public void closeScanner() {
        sc.close();
    }
}

public class exercice2 {
    public static void main(String[] args) {
        
        // --- 1. Menu valide ---
        String[] options1 = {"Ouvrir un fichier", "Sauvegarder", "Quitter"};
        Menu menu1 = new Menu(options1);
        
        // --- 2. Autre Menu valide ---
        String[] options2 = {"Calculer l'aire", "Calculer le périmètre", "Dessiner", "Effacer"};
        Menu menu2 = new Menu(options2);
        
        // --- 3. Menu avec une seule option (provoque une exception de conception) ---
        String[] options3 = {"Unique Choix"};
        Menu menu3 = new Menu(options3);
        
        try {
            System.out.println("--- Test Menu 1 : Simple (3 options) ---");
            int choix1 = menu1.faireChoix();
            if (choix1 != -1) {
                System.out.println("✅ Choix final du Menu 1 : " + choix1 + " (" + options1[choix1 - 1] + ")");
            }
            
            System.out.println("\n--- Test Menu 2 : Avancé (4 options) ---");
            int choix2 = menu2.faireChoix();
            if (choix2 != -1) {
                System.out.println("✅ Choix final du Menu 2 : " + choix2 + " (" + options2[choix2 - 1] + ")");
            }
            
            // Ce menu devrait entraîner l'affichage de l'exception personnalisée NombreOptionsInvalideException
            System.out.println("\n--- Test Menu 3 : Invalide (1 seule option) ---");
            int choix3 = menu3.faireChoix();
            if (choix3 != -1) {
                System.out.println("✅ Choix final du Menu 3 : " + choix3);
            }

        } catch (Exception e) {
            // Gestion des exceptions inattendues du programme principal
            System.out.println("Une exception non prévue a été capturée dans main : " + e.getMessage());
        } finally {
            // Bonne pratique : fermer les ressources
            menu1.closeScanner(); 
            menu2.closeScanner();
            // menu3.closeScanner() pourrait être appelée si le scanner n'était pas un champ partagé.
        }
    }
}