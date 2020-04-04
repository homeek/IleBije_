package pl.fitandyummy.ilebije;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class IleBijeMiligramow extends AppCompatActivity {




    int intmocarnosc;
    double stala = 7;
    double zmiennaRaz;
    double r;

    private AdView mAdView;

    String stezenieStr;

    TextView wynikmg, txt1,txt2,txt3,txt4,txt5,txt6, kliknij, fajnaapka, mocarnoscTxt;
    Button ilemg, ileml, coile, pomozkoledze;

    LinearLayout dajhajsy;

    EditText mocarnoscET;
    EditText mlDlaMiligramow;
    EditText dniDlaMiligramow;

    double mlNaMg;
    double dniNaMg;
    double intmiligramy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ile_bije_miligramow);



//baner AdMob
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~8122554600");
        //  MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
                //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);


        mocarnoscET = (EditText) findViewById(R.id.mocarnosc);

        //przycisk powrotu na pasku
        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(doMainIntent);
            }
        });


        //klik do ilebije
        ImageView kiedy = (ImageView) findViewById(R.id.toolchangeBtn);
        kiedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doPudelka = new Intent(getApplicationContext(),PudelkoActivity.class);
                startActivity(doPudelka);
            }
        });



        int stezenie = getIntent().getIntExtra("stezenie",888);

        stezenieStr = String.valueOf(stezenie);
        mocarnoscET.setText(stezenieStr);



        txt1 = (TextView) findViewById(R.id.tXT1);
        txt2 = (TextView) findViewById(R.id.TXT2);
        txt3 = (TextView) findViewById(R.id.TXT3);
        txt4 = (TextView) findViewById(R.id.TXT4);
        txt5 = (TextView) findViewById(R.id.TXT5);
        txt6 = (TextView) findViewById(R.id.TXT6);

        ilemg = (Button) findViewById(R.id.srodekPierwszy);
        ileml = (Button) findViewById(R.id.srodekDrugi);
        coile = (Button) findViewById(R.id.kiedyBije);
        pomozkoledze = (Button) findViewById(R.id.pomozkoledze);

        mocarnoscET = (EditText) findViewById(R.id.mocarnosc);
        mlDlaMiligramow = (EditText) findViewById(R.id.mlDlaMiligramow);
        dniDlaMiligramow = (EditText) findViewById(R.id.dniDlaMiligramow);
        mocarnoscTxt = (TextView) findViewById(R.id.textMocarnosc);

    //czcionka
        Typeface text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");
        txt1.setTypeface(text1);
        txt2.setTypeface(text1);
        txt3.setTypeface(text1);
        txt4.setTypeface(text1);
        txt5.setTypeface(text1);
        txt6.setTypeface(text1);

        mocarnoscET.setTypeface(text1);
        mlDlaMiligramow.setTypeface(text1);
        dniDlaMiligramow.setTypeface(text1);
        mocarnoscTxt.setTypeface(text1);

        ilemg.setTypeface(text1);
        ileml.setTypeface(text1);
        coile.setTypeface(text1);
        pomozkoledze.setTypeface(text1);

    }

    public void ileMl(View view) {
        Intent ilemlintent = new Intent(this, IleBijeMililitrow.class);
        intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());
        ilemlintent.putExtra("stezenie",intmocarnosc);
        startActivity(ilemlintent);
    }

    public void coIle(View view) {
        Intent coilebijeintent = new Intent(this, CoIleBije.class);
        intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());
        coilebijeintent.putExtra("stezenie",intmocarnosc);
        startActivity(coilebijeintent);
    }

    public void licz(View view) {

        mocarnoscET = (EditText) findViewById(R.id.mocarnosc);
        mlDlaMiligramow = (EditText) findViewById(R.id.mlDlaMiligramow);
        dniDlaMiligramow = (EditText) findViewById(R.id.dniDlaMiligramow);

        if (mocarnoscET.getText().toString().equals("")|| mlDlaMiligramow.getText().toString().equals("") || dniDlaMiligramow.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Dziku ", Toast.LENGTH_LONG).show();

        } else {

            intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());
            mlNaMg = Double.parseDouble(mlDlaMiligramow.getText().toString());
            dniNaMg = Integer.parseInt(dniDlaMiligramow.getText().toString());

            zmiennaRaz = mlNaMg * stala/ dniNaMg;
            intmiligramy = zmiennaRaz *intmocarnosc;

            r = Math.round(intmiligramy * 10.0) / 10.0;
            wynikmg = (TextView) findViewById(R.id.wynikmg);
            wynikmg.setText(String.valueOf("  " + r + "  mg"));

        }

        //zagłębiony czy zduplikowany Listner działa dopiero po drugim nacisnieciu batona

        pomozkoledze.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (mocarnoscET.getText().toString().equals("")|| mlDlaMiligramow.getText().toString().equals("") || dniDlaMiligramow.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Uzupełnij dane Dziku ", Toast.LENGTH_LONG).show();

                } else {



                //powtorzona czynność zeby liczył za kazdym razem
                intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());

                mlNaMg = Double.parseDouble(mlDlaMiligramow.getText().toString());
                dniNaMg = Integer.parseInt(dniDlaMiligramow.getText().toString());


                zmiennaRaz = mlNaMg * stala/ dniNaMg;

                intmiligramy = zmiennaRaz *intmocarnosc;

                r = Math.round(intmiligramy * 10.0) / 10.0;


                wynikmg = (TextView) findViewById(R.id.wynikmg);
                wynikmg.setText(String.valueOf("  " + r + "  mg"));





               // fajnaapka = (TextView) findViewById(R.id.fajnaapka);
               // kliknij = (TextView) findViewById(R.id.kliknij);
               // fajnaapka.setText("KLIKNIJ TUTAJ !!!");
               // kliknij.setText("Fajna apka ???");

            }}
        });

    }



}
