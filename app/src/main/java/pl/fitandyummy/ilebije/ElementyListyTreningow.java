package pl.fitandyummy.ilebije;

public class ElementyListyTreningow {

    private String komentarzCwiczenn;
    private String nazwaCwiczenienn;

    private String godzinaStartuu;
    private String godzinaZakonczeniaa;
    private String dataaa;


    public String getKomentarzCwiczenn() {
        return komentarzCwiczenn;
    }

    public void setKomentarzCwiczenn(String komentarzCwiczenn) {
        this.komentarzCwiczenn = komentarzCwiczenn;
    }

    public String getNazwaCwiczenienn() {
        return nazwaCwiczenienn;
    }

    public void setNazwaCwiczenienn(String nazwaCwiczenienn) {
        this.nazwaCwiczenienn = nazwaCwiczenienn;
    }

    public String getGodzinaStartuu() {
        return godzinaStartuu;
    }

    public void setGodzinaStartuu(String godzinaStartuu) {
        this.godzinaStartuu = godzinaStartuu;
    }

    public String getGodzinaZakonczeniaa() {
        return godzinaZakonczeniaa;
    }

    public void setGodzinaZakonczeniaa(String godzinaZakonczeniaa) {
        this.godzinaZakonczeniaa = godzinaZakonczeniaa;
    }

    public String getDataaa() {
        return dataaa;
    }

    public void setDataaa(String dataa) {
        this.dataaa = dataa;
    }

    public ElementyListyTreningow(String komentarzCwiczen, String nazwaCwiczenia, String godzinaStartu, String godzinaZakonczenia , String data ) {

        komentarzCwiczenn=komentarzCwiczen;
        nazwaCwiczenienn=nazwaCwiczenia;
        godzinaStartuu=godzinaStartu;

        godzinaZakonczeniaa=godzinaZakonczenia;
        dataaa=data;






    }

}
