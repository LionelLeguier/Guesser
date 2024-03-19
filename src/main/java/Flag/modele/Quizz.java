package Flag.modele;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Set;

@Service
public class Quizz implements Iquizz{
    WebClient client = WebClient.create();
    public Pays Question(){

        client.get().uri("https://flagcdn.com/fr/codes.json").retrieve().bodyToMono(String.class) // Récupérer le corps de la réponse comme une chaîne de caractères
                .subscribe(response -> {

                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                    Set<String> Liste_clef =  jsonObject.keySet();
                    System.out.println(Liste_clef);
                });

            return new Pays("khkh","hjhj","kjhgjg");
    }


}
