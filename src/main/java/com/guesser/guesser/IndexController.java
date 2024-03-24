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
    private int Score = 0;


    /*@GetMapping("/toto")
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

    @GetMapping("/toto")
    public String home(final Model model, HttpSession session) throws ExecutionException, InterruptedException {
        //System.out.println("Avant tout");
        CompletableFuture<Pays> questionFuture = CompletableFuture.supplyAsync(() -> {
            //System.out.println("dans la promesse");

            return Quizz.Question();

        });
        //String difficulte = (String) session.getAttribute("difficulte");
        /*if ("facile".equals(difficulte)) {
            // Pour le mode facile, afficher deux propositions et donner 30 secondes
            proposition = Proposition.Proposition_Drapeau(question.getCode(), 2);
            session.setAttribute("tempsRestant", 30); // Durée en secondes
        } else if ("moyen".equals(difficulte)) {
            // Pour le mode moyen, afficher quatre propositions et donner 15 secondes
            proposition = Proposition.Proposition_Drapeau(question.getCode(), 4);
            session.setAttribute("tempsRestant", 15); // Durée en secondes
        } else if ("difficile".equals(difficulte)) {
            // Pour le mode difficile, aucune proposition et donner 10 secondes
            session.setAttribute("tempsRestant", 10); // Durée en secondes
        }
         */
        System.out.println("arriver au patientage");
        Pays question = questionFuture.get(); // Attendre que la question soit générée

        System.out.println("TEst async  "+question.getNom());

        CompletableFuture<ArrayList<Pays>> propositionFuture = CompletableFuture.supplyAsync(() -> {
            ArrayList<Pays> proposition = Proposition.Proposition_Drapeau(question.getCode());
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
        session.setAttribute("Score",Score);


        return "index";
    }

    @GetMapping("/Verification")
    public Boolean Verif(Model model,HttpSession session,@RequestParam("Code_bon") String Bonne_reponse,@RequestParam("Code_joueur") String reponse_joueur){
        if(Bonne_reponse.equals(reponse_joueur)){

            Score = (int) session.getAttribute("Score");
            Score++;
            session.setAttribute("Score",Score);
            System.out.println("C'est bon !!");
            System.out.println("Score : "+session.getAttribute("Score"));
            return true;
        }else{
            System.out.println("C'est mauvais");
            System.out.println("Score : "+session.getAttribute("Score"));
            return false;
        }
    }


}
