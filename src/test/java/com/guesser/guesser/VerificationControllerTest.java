package com.guesser.guesser;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class VerificationControllerTest {

    @Test
    public void testVerification_Difficile_ReponseCorrecte() {
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("Score")).thenReturn(0);
        Mockito.when(session.getAttribute("Nombre_Question")).thenReturn(0);
        String reponse_joueur = "bonneReponse";

        VerificationController controller = new VerificationController();
        boolean resultat = controller.Verif(null, session, "difficile", "bonneReponse", reponse_joueur);
        assertTrue(resultat);
        Mockito.verify(session, Mockito.times(1)).setAttribute("Score", 1);
        Mockito.verify(session, Mockito.times(1)).setAttribute("Nombre_Question", 1);
    }

    @Test
    public void testVerification_Difficile_ReponseIncorrecte() {
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("Score")).thenReturn(0);
        Mockito.when(session.getAttribute("Nombre_Question")).thenReturn(0);
        String reponse_joueur = "réponseIncorrecte";
        VerificationController controller = new VerificationController();
        boolean resultat = controller.Verif(null, session, "difficile", "bonneReponse", reponse_joueur);
        assertFalse(resultat);
        Mockito.verify(session, Mockito.times(1)).setAttribute("Nombre_Question", 1);
    }

    @Test
    public void testCorrectResponse() {
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("Score")).thenReturn(0);
        Mockito.when(session.getAttribute("Nombre_Question")).thenReturn(0);
        String reponseJoueur = "bonneReponse";
        VerificationController controller = new VerificationController();

        boolean result = controller.Verif(null, session, "facile", "bonneReponse", reponseJoueur);

        assertTrue(result);
        Mockito.verify(session, Mockito.times(1)).setAttribute("Nombre_Question", 1);
        Mockito.verify(session, Mockito.times(1)).setAttribute("Score", 1);
    }

    @Test
    public void testIncorrectResponse() {
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(session.getAttribute("Score")).thenReturn(0);
        Mockito.when(session.getAttribute("Nombre_Question")).thenReturn(0);
        String reponseJoueur = "mauvaiseReponse";
        VerificationController controller = new VerificationController();

        boolean result = controller.Verif(null, session, "facile", "bonneReponse", reponseJoueur);

        assertFalse(result);
        Mockito.verify(session, Mockito.times(1)).setAttribute("Nombre_Question", 1);
    }
}
