package Flag.modele;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuizzTest {

    @Test
    void question() {
        Quizz test_quizz = new Quizz();
        Pays test_Pays = test_quizz.Question();
        assertNotEquals(null,test_Pays.getNom());
        assertNotEquals(null,test_Pays.getCode());
        assertNotEquals(null,test_Pays.getDrapeau());
        assertNotEquals(0,test_Pays.getNom().length());
        assertNotEquals(0,test_Pays.getCode().length());
        assertNotEquals(0,test_Pays.getDrapeau().length());
    }

    @Test
    void ScoreToString(){
        Quizz test_quizz = new Quizz();
        assertEquals("C'est parfait !", test_quizz.ScoreToString(5));
        assertEquals("C'est presque parfait !", test_quizz.ScoreToString(3));
        assertEquals("Vous pouvez mieux faire !", test_quizz.ScoreToString(2));
        assertEquals("Vous êtes nul !", test_quizz.ScoreToString(0));
        assertEquals("Score invalide !", test_quizz.ScoreToString(-1));
    }
}

