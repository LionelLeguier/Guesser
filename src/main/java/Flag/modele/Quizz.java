package Flag.modele;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

@Service
public class Quizz implements Iquizz{
    WebClient client = WebClient.create();
    JsonObject objet_avec_nom;
    ArrayList<String> liste_Clef = new ArrayList<String>();
    Random random = new Random();
    int Chiffre_Choix_pays;

    String url_drapeau = "https://flagcdn.com/w320/";
    public Pays Question(){

        client.get().uri("https://flagcdn.com/fr/codes.json").retrieve().bodyToMono(String.class) // Récupérer le corps de la réponse comme une chaîne de caractères
                .subscribe(response -> {

                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                    objet_avec_nom = jsonObject;
                    Set<String> Liste_Clef =  jsonObject.keySet();
                    liste_Clef.addAll(Liste_Clef);
                    Chiffre_Choix_pays = random.nextInt(liste_Clef.size());
                    url_drapeau += liste_Clef.get(Chiffre_Choix_pays)+".png";


                });



            return new Pays(url_drapeau,objet_avec_nom.get(liste_Clef.get(Chiffre_Choix_pays)).toString(), liste_Clef.get(Chiffre_Choix_pays));
    }


}
