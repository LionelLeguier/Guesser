package Flag.modele;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuizzTest {

    @Test
    void question() {
       Quizz test_quizz = new Quizz();
       Pays test_Pays = test_quizz.Question();
       assertEquals(test_Pays.getClass(), Pays.class);
    }
}