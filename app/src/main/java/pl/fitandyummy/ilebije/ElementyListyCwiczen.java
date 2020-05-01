package pl.fitandyummy.ilebije;

public class ElementyListyCwiczen {


    private String nazwaCwiczeniaa;
    private String czasWorkk;
    private String czasRestt;
    private String dataa;
    private String godzinaa;

    public String getNazwaCwiczeniaa() {
        return nazwaCwiczeniaa;
    }

    public void setNazwaCwiczeniaa(String nazwaCwiczeniaa) {
        this.nazwaCwiczeniaa = nazwaCwiczeniaa;
    }

    public String getCzasWorkk() {
        return czasWorkk;
    }

    public void setCzasWorkk(String czasWorkk) {
        this.czasWorkk = czasWorkk;
    }

    public String getCzasRestt() {
        return czasRestt;
    }

    public void setCzasRestt(String czasRestt) {
        this.czasRestt = czasRestt;
    }

    public String getDataa() {
        return dataa;
    }

    public void setDataa(String dataa) {
        this.dataa = dataa;
    }

    public String getGodzinaa() {
        return godzinaa;
    }

    public void setGodzinaa(String godzinaa) {
        this.godzinaa = godzinaa;
    }

    public ElementyListyCwiczen(String nazwaCwiczenia, String czasWork, String czasRest, String data, String godzina) {
        nazwaCwiczeniaa = nazwaCwiczenia;
        czasWorkk = czasWork;
        czasRestt = czasRest;
        dataa = data;
        godzinaa = godzina;
    }
}