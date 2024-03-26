package Flag.modele;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

@Service
public class propFlag implements IpropFlag{
    WebClient client = WebClient.create();
    ArrayList<Integer> Liste_chiffre_hasard = new ArrayList<Integer>();
    ArrayList<String> Liste_avec_Clef = new ArrayList<String>();
    ArrayList<Pays> Liste_proposition = new ArrayList<Pays>();
    String url_drapeau = "https://flagcdn.com/w320/";
    Random random = new Random();
    @Override
    public ArrayList<Pays> Proposition_Drapeau(String Code_Pays_Question,int Nb_prop) {

        Mono<String> bodyMono = client.get().uri("https://flagcdn.com/fr/codes.json").retrieve().bodyToMono(String.class) // Récupérer le corps de la réponse comme une chaîne de caractères
                .doOnNext(response -> {
                    Liste_proposition.clear();

                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                    Set<String> liste_Clef =  jsonObject.keySet();
                    Liste_avec_Clef.addAll(liste_Clef);
                    while(Liste_chiffre_hasard.size() < Nb_prop){
                        int nouveau_chiffre = random.nextInt(Liste_avec_Clef.size());
                        if (!Liste_chiffre_hasard.contains(nouveau_chiffre) && !(jsonObject.get(Liste_avec_Clef.get(nouveau_chiffre)).toString().equals(Code_Pays_Question))){
                            Liste_chiffre_hasard.add(nouveau_chiffre);
                        }
                    }
                    for(int i=0;i<Liste_chiffre_hasard.size();i++){
                        url_drapeau = "https://flagcdn.com/w320/";
                        url_drapeau += Liste_avec_Clef.get(Liste_chiffre_hasard.get(i))+".png";
                        Liste_proposition.add(new Pays(url_drapeau,jsonObject.get(Liste_avec_Clef.get(Liste_chiffre_hasard.get(i))).toString(),Liste_avec_Clef.get(i)));
                    }
                    Liste_chiffre_hasard.clear();

                });

        String response = bodyMono.block();

        return Liste_proposition;
    }
}
/*package Flag.modele;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class propFlag implements IpropFlag {
    WebClient client = WebClient.create();
    String url_drapeau = "https://flagcdn.com/w320/";

    @Override
    public ArrayList<Pays> Proposition_Drapeau(String Code_Pays_Question, String difficulty) {
        switch (difficulty) {
            case "facile":
                return genererPropositionsFacile(Code_Pays_Question);
            case "moyen":
                return genererPropositionsMoyen(Code_Pays_Question);
            case "difficile":
                return genererPropositionsDifficile(Code_Pays_Question);
            default:
                throw new IllegalArgumentException("Difficulté non prise en charge : " + difficulty);
        }
    }

    private ArrayList<Pays> genererPropositionsFacile(String codePaysQuestion) {
        ArrayList<Pays> propositions = new ArrayList<>();
        propositions.add(genererPaysAleatoire(codePaysQuestion));
        propositions.add(genererPaysAleatoire(codePaysQuestion));
        return propositions;
    }

    private ArrayList<Pays> genererPropositionsMoyen(String codePaysQuestion) {
        ArrayList<Pays> propositions = new ArrayList<>();
        propositions.addAll(genererPropositionsFacile(codePaysQuestion));
        propositions.add(genererPaysAleatoire(codePaysQuestion));
        propositions.add(genererPaysAleatoire(codePaysQuestion));
        return propositions;
    }

    private ArrayList<Pays> genererPropositionsDifficile(String codePaysQuestion) {
        ArrayList<Pays> propositions = new ArrayList<>();
        propositions.add(genererPaysAleatoire(codePaysQuestion));
        propositions.add(genererPaysAleatoire(codePaysQuestion));
        propositions.add(genererPaysAleatoire(codePaysQuestion));
        return propositions;
    }

    private Pays genererPaysAleatoire(String codePaysQuestion) {
        Mono<String> bodyMono = client.get().uri("https://flagcdn.com/fr/codes.json").retrieve().bodyToMono(String.class)
                .doOnNext(response -> {
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                    Set<String> liste_Clef = jsonObject.keySet();
                    String[] clefs = liste_Clef.toArray(new String[0]);
                    String randomCode;
                    do {
                        randomCode = clefs[ThreadLocalRandom.current().nextInt(clefs.length)];
                    } while (randomCode.equals(codePaysQuestion)); // Pour s'assurer que le pays généré n'est pas le même que la question
                    url_drapeau += randomCode + ".png";
                });
        bodyMono.block();
        return new Pays(url_drapeau, "", ""); // Ajoutez le nom et le code du pays si nécessaire
    }
}
*/
