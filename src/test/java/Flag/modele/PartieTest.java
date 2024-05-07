package Flag.modele;

import org.junit.jupiter.api.Test;

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
        assertTrue(partie.FinDePartie(5));

        // Test avec un nombre de questions insuffisant
        assertFalse(partie.FinDePartie(3));

        // Test avec un nombre de questions égal au seuil
        assertTrue(partie.FinDePartie(5));

        // Test avec un nombre de questions supérieur au seuil
        assertTrue(partie.FinDePartie(6));

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
        assertEquals(0, resultatIncorrect.nb_questions);
        assertFalse(resultatIncorrect.estCorrect);
    }

}