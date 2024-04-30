package Flag.modele;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class propFlagTest {

    @Test
    void proposition_Drapeau() {
        propFlag prop_flag = new propFlag();
        ArrayList<Pays> Liste_test_2_prop = prop_flag.Proposition_Drapeau("fr",2);
        assertEquals(Liste_test_2_prop.size(),2);
        ArrayList<Pays> Liste_test_3_prop = prop_flag.Proposition_Drapeau("fr",3);
        assertEquals(Liste_test_3_prop.size(),3);
        assertEquals(Liste_test_2_prop.get(0).getClass(),Pays.class);

    }
}