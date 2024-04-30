package com.guesser.guesser;

import Flag.modele.IpropFlag;
import Flag.modele.Iquizz;
import Flag.modele.Pays;
import Flag.modele.Quizz;
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





    @GetMapping("/jeu")
    public String home(final Model model, HttpSession session,@RequestParam("difficulte")String difficulte) throws ExecutionException, InterruptedException {

        if((session.getAttribute("Nombre_Question") != null) && ((int) session.getAttribute("Nombre_Question") >= 5)){
            String messageEvaluation= Quizz.ScoreToString((int)session.getAttribute("Score"));
            session.setAttribute("MessageFin",messageEvaluation);
                return "findejeu";
        }
        CompletableFuture<Pays> questionFuture = CompletableFuture.supplyAsync(() -> {

            return Quizz.Question();

        });

        int Nb_props;

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



        CompletableFuture<ArrayList<Pays>> propositionFuture = CompletableFuture.supplyAsync(() -> {
            ArrayList<Pays> proposition = Proposition.Proposition_Drapeau(question.getCode(),Nb_props);
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




}
