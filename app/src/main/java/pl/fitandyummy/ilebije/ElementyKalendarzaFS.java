package pl.fitandyummy.ilebije;

public class ElementyKalendarzaFS {

    private String nazwaTowara;
    private String iloscTowara;
    private String strzalll;
    private int ktoryStrzl;
    private String dataa;
    private String godzinaa;

    public ElementyKalendarzaFS() {
    }

    public String getNazwaTowara() {
        return nazwaTowara;
    }

    public void setNazwaTowara(String nazwaTowara) {
        this.nazwaTowara = nazwaTowara;
    }

    public String getIloscTowara() {
        return iloscTowara;
    }

    public void setIloscTowara(String iloscTowara) {
        this.iloscTowara = iloscTowara;
    }

    public String getStrzalll() {
        return strzalll;
    }

    public void setStrzalll(String strzalll) {
        this.strzalll = strzalll;
    }

    public int getKtoryStrzl() {
        return ktoryStrzl;
    }

    public void setKtoryStrzl(int ktoryStrzl) {
        this.ktoryStrzl = ktoryStrzl;
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

    public ElementyKalendarzaFS(String nazwatowaru, String ilosctowaru, String strzal, int ktorystrzal, String data, String godzina) {

        nazwaTowara = nazwatowaru;
        iloscTowara = ilosctowaru;
        strzalll = strzal;
        ktoryStrzl = ktorystrzal;
        dataa = data;
        godzinaa = godzina;
    }
}