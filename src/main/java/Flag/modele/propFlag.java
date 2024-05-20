package Flag.modele;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class propFlag implements IpropFlag{
    WebClient client = WebClient.create();
    ArrayList<Integer> Liste_chiffre_hasard = new ArrayList<Integer>();
    ArrayList<String> Liste_avec_Clef = new ArrayList<String>();
    ArrayList<Pays> Liste_proposition = new ArrayList<Pays>();
    String url_drapeau = "https://flagcdn.com/w320/";
    Random random = new Random();

    @Override
    public ArrayList<Pays> Proposition_Drapeau(String Code_Pays_Question, int Nb_prop) {
        // Initialisation de la liste de propositions
        ArrayList<Pays> Liste_proposition = new ArrayList<>();
        Set<Integer> Liste_chiffre_hasard = new HashSet<>();
        List<String> Liste_avec_Clef = new ArrayList<>();

        // Récupération du JSON contenant les codes de pays
        Mono<String> bodyMono = client.get()
                .uri("https://flagcdn.com/fr/codes.json")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> {
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                    Set<String> liste_Clef = jsonObject.keySet();
                    Liste_avec_Clef.addAll(liste_Clef);

                    // Sélection des propositions uniques aléatoires
                    while (Liste_chiffre_hasard.size() < Nb_prop) {
                        int nouveau_chiffre = random.nextInt(Liste_avec_Clef.size());
                        String codePays = Liste_avec_Clef.get(nouveau_chiffre);
                        String nomPays = jsonObject.get(codePays).toString().replace("\"", "");

                        if (!Liste_chiffre_hasard.contains(nouveau_chiffre) && !codePays.equals(Code_Pays_Question)) {
                            Liste_chiffre_hasard.add(nouveau_chiffre);
                            String url_drapeau = "https://flagcdn.com/w320/" + codePays + ".png";
                            Liste_proposition.add(new Pays(url_drapeau, nomPays, codePays));
                        }
                    }
                });

        // Bloquer jusqu'à ce que la réponse soit reçue
        bodyMono.block();

        return Liste_proposition;
    }

}

