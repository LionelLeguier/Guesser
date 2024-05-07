package Flag.modele;

public class Resultat {
    public int score;
    public int nb_questions;
    public boolean estCorrect;

    public Resultat(int score, int nb_questions, boolean estCorrect) {
        this.score = score;
        this.nb_questions = nb_questions;
        this.estCorrect = estCorrect;
    }
}