package Flag.modele;

import org.springframework.stereotype.Service;

@Service
public class Partie implements Ipartie {
    public Boolean FinDePartie(int NombreQuestion){
         if(NombreQuestion >=5){
                return true;
         }else{
             return false;
         }

    }

    public int DebutPartie(String difficulte){
        switch (difficulte) {
            case "facile":
                return 1;
            case "moyen":
                return 3;
            case "difficile":
                return 0;
            default:
                return 1;
        }
    }

    public Resultat Verification(String BonneReponse,String ReponseJoueur,int score, int nb_question){

        if(BonneReponse.equalsIgnoreCase(ReponseJoueur)){

            score ++;
            nb_question++;
            return new Resultat(score,nb_question,true);
        }else {

            nb_question++;
            return new Resultat(score,nb_question,false);
        }

    }
}
