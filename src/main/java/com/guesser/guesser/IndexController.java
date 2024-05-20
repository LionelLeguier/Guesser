package com.guesser.guesser;

import Flag.modele.*;
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

    private final Iquizz Quizz;

    private final IpropFlag Proposition;

    private final Ipartie Partie;
    private int compteur = 0;
private ArrayList<String> liste_pays_deja_sortie = new ArrayList<String>();


public IndexController(Iquizz Quizz,IpropFlag Proposition,Ipartie Partie){
    this.Quizz = Quizz;
    this.Proposition = Proposition;
    this.Partie = Partie;
}

    @GetMapping("/recupererSauvegarde")
    public String reprendrePartie(HttpSession session, @RequestParam("gameId") String Id) {
        SauvegardePartie save = Partie.renvoieSauvegarde(Id);
        // Mettre à jour les attributs de la session avec ce que renvoie la sauvegarde
        session.setAttribute("Score", save.getScore());
        session.setAttribute("Nombre_Question", save.getNbquestion());
        session.setAttribute("liste_pays_sortie", save.getListeQuestionDejaSortie());
        session.setAttribute("difficulte", save.getDifficulte());
        return "redirect:/jeu?difficulte=" + save.getDifficulte();
    }




    @GetMapping("/jeu")
    public String home(final Model model, HttpSession session,@RequestParam("difficulte")String difficulte) throws ExecutionException, InterruptedException {
        session.setAttribute("difficulte",difficulte);
        if((session.getAttribute("Nombre_Question") != null) && Partie.FinDePartie((int) session.getAttribute("Nombre_Question"))){
            String messageEvaluation= Quizz.ScoreToString((int)session.getAttribute("Score"));
            session.setAttribute("MessageFin",messageEvaluation);
            return "findejeu";
        }
        if(session.getAttribute("liste_pays_sortie") != null){
            liste_pays_deja_sortie = (ArrayList<String>) session.getAttribute("liste_pays_sortie");
        }


        CompletableFuture<Pays> questionFuture = CompletableFuture.supplyAsync(() -> {

            return Quizz.Question();

        });

        int Nb_props;

        Pays question = questionFuture.get(); // Attendre que la question soit générée
        while (liste_pays_deja_sortie.contains(question.getCode())){
            question = questionFuture.get();
        }

        liste_pays_deja_sortie.add(question.getCode());

        Nb_props = Partie.DebutPartie(difficulte);
        if(compteur == 0){
            session.setAttribute("Score",0);
            compteur++;
        }





        Pays finalQuestion = question;
        CompletableFuture<ArrayList<Pays>> propositionFuture = CompletableFuture.supplyAsync(() -> {
            ArrayList<Pays> proposition = Proposition.Proposition_Drapeau(finalQuestion.getCode(),Nb_props);
            return proposition;
        });

        ArrayList<Pays> proposition = propositionFuture.get(); // Attendre que les propositions soient générées
        proposition.add(question);
        Collections.shuffle(proposition);



        session.setAttribute("liste_pays_sortie",liste_pays_deja_sortie);
        model.addAttribute("appName", appName);
        model.addAttribute("Question", question);
        model.addAttribute("Proposition", proposition);
        model.addAttribute("Difficulte",difficulte);



        return "index";
    }




}
