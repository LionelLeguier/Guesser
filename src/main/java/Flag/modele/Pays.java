package Flag.modele;

public class Pays {
    private String Drapeau;
    private String Nom;
    private String Code;

    Pays(String Drapeau,String Nom,String code){
        this.Drapeau = Drapeau;
        this.Nom = Nom;
        this.Code = code;
    }

    public String getDrapeau() {
        return Drapeau;
    }

    public void setDrapeau(String drapeau) {
        Drapeau = drapeau;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
