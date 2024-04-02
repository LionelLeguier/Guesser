package com.guesser.guesser;

import Flag.modele.IpropFlag;
import Flag.modele.Iquizz;
import Flag.modele.Pays;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Controller
public class IndexController {
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    private Iquizz Quizz;
    @Autowired
    private IpropFlag Proposition;
    private int compteur = 0;



    /*@GetMapping("/jeu")
    public String home(final Model model, HttpSession session) {
        Pays question = Quizz.Question();
        ArrayList<Pays> proposition = Proposition.Proposition_Drapeau(question.getCode());
        System.out.println("###########################");
        for (int i =0;i<proposition.size();i++){

            System.out.println("le nom est "+proposition.get(i).getNom());
            System.out.println(" Le code est "+proposition.get(i).getCode());
            System.out.println(" Le drapeau est "+proposition.get(i).getDrapeau());
            System.out.println("------------------------");
        }
        System.out.println("Reponse dans le controlleur " + question.getNom() + " Et le code du pays "+question.getCode() +" et l'URL du pays : "+question.getDrapeau());
        model.addAttribute("appName", appName);
        model.addAttribute("Question",question);
        model.addAttribute("Proposition",proposition);
        session.setAttribute("score",Score);

        return "index";
    }*/

    @GetMapping("/jeu")
    public String home(final Model model, HttpSession session,@RequestParam("difficulte")String difficulte) throws ExecutionException, InterruptedException {
        //System.out.println("Avant tout");
        System.out.println("iciciiciciic"+session.getAttribute("Nombre_Question"));
        if((session.getAttribute("Nombre_Question") != null) && ((int) session.getAttribute("Nombre_Question") >= 5)){

                return "findejeu";
        }

        CompletableFuture<Pays> questionFuture = CompletableFuture.supplyAsync(() -> {
            //System.out.println("dans la promesse");

            return Quizz.Question();

        });

        int Nb_props;
        System.out.println("arriver au patientage");
        Pays question = questionFuture.get(); // Attendre que la question soit générée


        if (difficulte.equals("facile")) {
            Nb_props= 1;
            if(compteur == 0){
                session.setAttribute("Score",0);
                compteur++;
            }

            session.setAttribute("tempsRestant", 30); // Durée en secondes
        } else if (difficulte.equals("moyen")) {
            // Pour le mode moyen, afficher quatre propositions et donner 15 secondes
            Nb_props= 3;
            if(compteur == 0){
                session.setAttribute("Score",0);
                compteur++;
            }
            session.setAttribute("tempsRestant", 15); // Durée en secondes
        } else if (difficulte.equals("difficile")) {
            Nb_props= 0;
            if(compteur == 0){
                session.setAttribute("Score",0);
                compteur++;
            }
            session.setAttribute("tempsRestant", 10); // Durée en secondes
        } else {
            Nb_props = 0;
        }
        //Vérification du nombre de questions posées
        Integer nombreQuestions = (Integer) session.getAttribute("nombreQuestions");
        if (nombreQuestions == null) {
            nombreQuestions = 0;
        }

        if (nombreQuestions >= 10) {
            // Redirection vers la page de fin de jeu
            return "finJeu";
        }




        System.out.println("TEst async  "+question.getNom());

        CompletableFuture<ArrayList<Pays>> propositionFuture = CompletableFuture.supplyAsync(() -> {
            ArrayList<Pays> proposition = Proposition.Proposition_Drapeau(question.getCode(),Nb_props);
            System.out.println("###########################");
            for (Pays pays : proposition) {
                System.out.println("le nom est " + pays.getNom());
                System.out.println(" Le code est " + pays.getCode());
                System.out.println(" Le drapeau est " + pays.getDrapeau());
                System.out.println("------------------------");
            }
            System.out.println("Reponse dans le controlleur " + question.getNom() + " Et le code du pays " + question.getCode() + " et l'URL du pays : " + question.getDrapeau());
            return proposition;
        });

        ArrayList<Pays> proposition = propositionFuture.get(); // Attendre que les propositions soient générées
        proposition.add(question);
        Collections.shuffle(proposition);




        model.addAttribute("appName", appName);
        model.addAttribute("Question", question);
        model.addAttribute("Proposition", proposition);
        model.addAttribute("Difficulte",difficulte);



        return "index";
    }

    /*@GetMapping("/Verification")
    public String Verif(Model model,HttpSession session,@RequestParam("Code_bon") String Bonne_reponse,@RequestParam("Code_joueur") String reponse_joueur){
        if(Bonne_reponse.equals(reponse_joueur)){
            boolean bool=true;
            Score = (int) session.getAttribute("Score");
            Score++;
            session.setAttribute("Score",Score);
            System.out.println("C'est bon !!");
            System.out.println("Score : "+session.getAttribute("Score"));
            return "index";
        }else{
            boolean bool=false;

            System.out.println("C'est mauvais");
            System.out.println("Score : "+session.getAttribute("Score"));
            return "index";
        }
    }*/
    /*@GetMapping("/finJeu")
    public String finJeu(HttpSession session, Model model) {
        // Calcul du score final
        Integer scoreFinal = (Integer) session.getAttribute("score");
        String messageEvaluation;

        if (scoreFinal == 10) {
            messageEvaluation = "C'est parfait!";
        }
         else if (scoreFinal >= 8) {
            messageEvaluation = "C'est presque parfait!";
        } else if (scoreFinal >= 4) {
            messageEvaluation = "Vous pouvez mieux faire!";
        } else if (scoreFinal >= 0) {
            messageEvaluation = "Vous êtes nul!";
        } else {
            messageEvaluation = "Score invalide!";
        }

        // Passage des données à la vue
        model.addAttribute("scoreFinal", scoreFinal);
        model.addAttribute("messageEvaluation", messageEvaluation);

        // Effacer les données de session
        session.removeAttribute("score");
        session.removeAttribute("nombreQuestions");

        return "finJeu";
    }*/


}
