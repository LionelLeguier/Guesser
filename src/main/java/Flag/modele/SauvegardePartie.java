package Flag.modele;

import java.util.ArrayList;

public class SauvegardePartie {
    private int Score;
    private int Nbquestion;
    private ArrayList<String> ListeQuestionDejaSortie;
    private String difficulte;

    public SauvegardePartie(ArrayList<String> listeQuestionDejaSortie,int Score,int Nbquestion,String difficulte) {
        this.ListeQuestionDejaSortie = listeQuestionDejaSortie;
        this.Score = Score;
        this.Nbquestion = Nbquestion;
        this.difficulte = difficulte;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getNbquestion() {
        return Nbquestion;
    }

    public void setNbquestion(int nbquestion) {
        Nbquestion = nbquestion;
    }

    public ArrayList<String> getListeQuestionDejaSortie() {
        return ListeQuestionDejaSortie;
    }

    public void setListeQuestionDejaSortie(ArrayList<String> listeQuestionDejaSortie) {
        ListeQuestionDejaSortie = listeQuestionDejaSortie;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }
}
