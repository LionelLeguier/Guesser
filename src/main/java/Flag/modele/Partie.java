package Flag.modele;

import org.springframework.stereotype.Service;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;

@Service
public class Partie implements Ipartie {
    public Boolean FinDePartie(int NombreQuestion){
         if(NombreQuestion >=10){
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

    public String Pause(int Score, int NbQuestion,ArrayList<String> ListePaysDejaSortie,String difficulte){
        Random random = new Random();
        int ID =  1 + random.nextInt(1_000_000_000);

        String csvFile = "output.csv";
        boolean fileExists = new File(csvFile).exists();

        if (!fileExists){
            try(FileWriter writer = new FileWriter(csvFile);
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
                String Listepays = String.join(",",ListePaysDejaSortie);

                csvPrinter.printRecord(ID,String.valueOf(Score),String.valueOf(NbQuestion),Listepays,difficulte);


                System.out.println("Fichier CSV créé ou écrasé avec succès.");
            } catch (IOException e) {
                System.err.println("Erreur lors de l'écriture du fichier CSV : " + e.getMessage());
            }
        }else {
            try(FileWriter writer = new FileWriter(csvFile,true);
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
                String Listepays = String.join(",",ListePaysDejaSortie);

                csvPrinter.printRecord(ID,String.valueOf(Score),String.valueOf(NbQuestion),Listepays,difficulte);


                System.out.println("Fichier CSV créé ou écrasé avec succès.");
            } catch (IOException e) {
                System.err.println("Erreur lors de l'écriture du fichier CSV : " + e.getMessage());
            }
        }

        return String.valueOf(ID);
    }


    public SauvegardePartie renvoieSauvegarde(String Id) {
        ArrayList<String> liste = new ArrayList<>();
        SauvegardePartie save = null;

        // Construire le chemin vers le fichier
        String relativePath = "output.csv";
        Path filePath = Paths.get("").toAbsolutePath().resolve(relativePath);

        try (CSVReader reader = new CSVReader(new FileReader(filePath.toFile()))) {
            List<String[]> records = reader.readAll();
            for (String[] record : records) {
                if (record[0].equals(Id)) {
                    // Séparer la quatrième colonne en utilisant les virgules comme délimiteurs
                    String[] values = record[3].split(",");
                    for (String value : values) {
                        // Ajouter chaque valeur à la liste
                        liste.add(value.trim()); // Utilisez trim() pour supprimer les espaces blancs autour des valeurs
                    }
                    // Créer l'objet SauvegardePartie avec les valeurs extraites
                    save = new SauvegardePartie(liste, Integer.parseInt(record[1]), Integer.parseInt(record[2]), record[4]);
                    break; // Sortir de la boucle une fois que la sauvegarde est trouvée
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            // Gérer l'exception d'une manière appropriée selon votre cas d'utilisation
        }
        return save;
    }

}
