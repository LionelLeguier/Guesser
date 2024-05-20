package Flag.modele;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartieTest {
    @Test
    void DebutPartieTest() {
        Partie votreObjet = new Partie();

        // Test de la difficulté facile
        assertEquals(1, votreObjet.DebutPartie("facile"));

        // Test de la difficulté moyenne
        assertEquals(3, votreObjet.DebutPartie("moyen"));

        // Test de la difficulté difficile
        assertEquals(0, votreObjet.DebutPartie("difficile"));

        // Test de la valeur par défaut
        assertEquals(1, votreObjet.DebutPartie("inconnu"));
    }

    @Test
    void FinPartieTest() {
        Partie partie = new Partie();

        // Test avec un nombre de questions suffisant
        assertTrue(partie.FinDePartie(10));

        // Test avec un nombre de questions insuffisant
        assertFalse(partie.FinDePartie(3));

        // Test avec un nombre de questions supérieur au seuil
        assertTrue(partie.FinDePartie(11));

    }

    @Test
    void VerificationTest() {
        Partie votreObjet = new Partie();

        // Test avec une réponse correcte
        Resultat resultatCorrect = votreObjet.Verification("bonne réponse", "bonne réponse", 0, 0);
        assertEquals(1, resultatCorrect.score);
        assertEquals(1, resultatCorrect.nb_questions);
        assertTrue(resultatCorrect.estCorrect);

        // Test avec une réponse incorrecte
        Resultat resultatIncorrect = votreObjet.Verification("bonne réponse", "mauvaise réponse", 0, 0);
        assertEquals(0, resultatIncorrect.score);
        assertEquals(1, resultatIncorrect.nb_questions);
        assertFalse(resultatIncorrect.estCorrect);
    }

    @Test
    void testPause() {
        // Création d'instances de votre classe
        Partie instance = new Partie();

        // Paramètres pour la méthode Pause
        int score = 1;
        int nbQuestions = 4;
        ArrayList<String> listePays = new ArrayList<>();
        listePays.add("Fr");
        listePays.add("Al");
        String difficulte = "Facile";

        // Appel de la méthode Pause
        String id = instance.Pause(score, nbQuestions, listePays, difficulte);

        // Vérifications
        assertNotNull(id);
        assertFalse(id.isEmpty()); // Vérifie que l'ID n'est pas vide
    }
    private void createTestCSV(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("313450988", "6", "8", "Fr,Al", "Facile");
        }
    }

    @Test
    void testRenvoieSauvegarde() {
        // Création d'instances de votre classe
        Partie instance = new Partie();

        // Chemin du fichier CSV de test
        String testCSVFilePath = "output.csv";

        // Création du fichier CSV de test
        try {
            createTestCSV(testCSVFilePath);

            // Appel de la méthode renvoieSauvegarde avec l'ID de test
            SauvegardePartie save = instance.renvoieSauvegarde("313450988");

            // Vérifications
            assertNotNull(save);
            assertEquals(6, save.getScore()); // Vérifie le score
            assertEquals(8, save.getNbquestion()); // Vérifie le nombre de questions
            assertEquals("Facile", save.getDifficulte()); // Vérifie la difficulté
        } catch (IOException e) {
            fail("Erreur lors de la création du fichier CSV de test : " + e.getMessage());
        }finally {
            // Suppression de la dernière ligne du fichier
            try {
                List<String> lines = Files.readAllLines(Paths.get(testCSVFilePath));
                if (!lines.isEmpty()) {
                    lines.remove(lines.size() - 1);
                    Files.write(Paths.get(testCSVFilePath), lines);
                }
            } catch (IOException e) {
                fail("Erreur lors de la suppression de la dernière ligne du fichier CSV de test : " + e.getMessage());
            }
        }
    }

}

