package Flag.modele;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class propFlagTest {

    private propFlag instance;

    @BeforeEach
    void setUp() {
        instance = new propFlag();
    }

    @Test
    void testProposition_Drapeau() {
        // Appel de la méthode à tester
        String codePaysQuestion = "fr"; // Exclure la France des propositions
        int nbProp = 3; // Nombre de propositions souhaité

        ArrayList<Pays> propositions = instance.Proposition_Drapeau(codePaysQuestion, nbProp);

        // Vérifications
        assertNotNull(propositions, "La liste des propositions ne doit pas être nulle");
        assertEquals(nbProp, propositions.size(), "Le nombre de propositions doit être égal à " + nbProp);
        for (Pays pays : propositions) {
            assertNotEquals(codePaysQuestion, pays.getCode(), "Le code du pays de la question ne doit pas être dans les propositions");
        }

        // Vérifie l'unicité des propositions
        Set<String> uniqueCodes = new HashSet<>();
        for (Pays pays : propositions) {
            assertTrue(uniqueCodes.add(pays.getCode()), "Le code du pays ne doit pas être dupliqué : " + pays.getCode());
        }

        // Affiche les propositions pour vérification visuelle (optionnel)
        propositions.forEach(pays -> System.out.println("Pays: " + pays.getNom() + ", Code: " + pays.getCode()));
    }
}
