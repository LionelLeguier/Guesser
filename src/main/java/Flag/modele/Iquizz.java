package Flag.modele;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface Iquizz {
    public Pays Question() ;
    public String ScoreToString(int score);
}
