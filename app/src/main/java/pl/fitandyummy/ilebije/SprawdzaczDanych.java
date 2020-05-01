package pl.fitandyummy.ilebije;

import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

final public class SprawdzaczDanych extends AppCompatActivity {

    EditText nazwaTowca;
    EditText data;
    EditText liczba_szczalow;
    EditText edtokres;
    EditText edt_godzina;
    EditText mililitry;

    public EditText getNazwaTowca() {
        return nazwaTowca;
    }

    public void setNazwaTowca(EditText nazwaTowca) {
        this.nazwaTowca = nazwaTowca;
    }

    public EditText getData() {
        return data;
    }

    public void setData(EditText data) {
        this.data = data;
    }

    public EditText getLiczba_szczalow() {
        return liczba_szczalow;
    }

    public void setLiczba_szczalow(EditText liczba_szczalow) {
        this.liczba_szczalow = liczba_szczalow;
    }

    public EditText getEdtokres() {
        return edtokres;
    }

    public void setEdtokres(EditText edtokres) {
        this.edtokres = edtokres;
    }

    public EditText getEdt_godzina() {
        return edt_godzina;
    }

    public void setEdt_godzina(EditText edt_godzina) {
        this.edt_godzina = edt_godzina;
    }

    public EditText getMililitry() {
        return mililitry;
    }

    public void setMililitry(EditText mililitry) {
        this.mililitry = mililitry;
    }

    public SprawdzaczDanych(EditText nazwaTowca, EditText data, EditText liczba_szczalow, EditText edtokres, EditText edt_godzina, EditText mililitry) {
        this.nazwaTowca = nazwaTowca;
        this.data = data;
        this.liczba_szczalow = liczba_szczalow;
        this.edtokres = edtokres;
        this.edt_godzina = edt_godzina;
        this.mililitry = mililitry;

        if (nazwaTowca.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else if (data.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else if (liczba_szczalow.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else if (edtokres.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else if (edt_godzina.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else if (mililitry.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "IDZIE !!!!! ", Toast.LENGTH_LONG).show();
        }
    }

    public SprawdzaczDanych(int contentLayoutId, EditText nazwaTowca, EditText data, EditText liczba_szczalow, EditText edtokres, EditText edt_godzina, EditText mililitry) {
        super(contentLayoutId);
        this.nazwaTowca = nazwaTowca;
        this.data = data;
        this.liczba_szczalow = liczba_szczalow;
        this.edtokres = edtokres;
        this.edt_godzina = edt_godzina;
        this.mililitry = mililitry;
    }
}