package Flag.modele;

import java.util.ArrayList;

public interface Ipartie {
    Boolean FinDePartie(int NombreQuestion);
    int DebutPartie(String difficulte);
    Resultat Verification(String BonneReponse,String ReponseJoueur,int score, int nb_question);
    String Pause(int Score, int NbQuestion,ArrayList<String> ListePaysDejaSortie,String difficulte);

    SauvegardePartie renvoieSauvegarde(String Id);
}
