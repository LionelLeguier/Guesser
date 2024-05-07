package Flag.modele;

public interface Ipartie {
    Boolean FinDePartie(int NombreQuestion);
    int DebutPartie(String difficulte);
    Resultat Verification(String BonneReponse,String ReponseJoueur,int score, int nb_question);
}
