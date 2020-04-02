package pl.fitandyummy.ilebije;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

public class IleBijeMililitrowCo_Dni extends AppCompatActivity {




    TextView wynikml;


    int intmocarnosc;


    TextView wynikmg, txt1,txt2,txt3,txt4,txt5,kliknij, fajnaapka, mocarnoscTxt;
    Button ilemg, ileml, coile, pomozkoledze;


    double stala ;
    double zmiennaDwa;
    double r;

    private AdView mAdView;


    LinearLayout dajhajsy;


    String stezenieStr;

    EditText mocarnoscET;
    EditText mgDlaMililitrow;
    EditText dniDlaMililitrow;

    double mgNaMl;
    double dniNaMl;
    double intmililitry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ile_bije_mililitrow_co_x_dni);


        //baner AdMob

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~8122554600");

        //  MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");


        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()

                //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);

      /*  dajhajsy = (LinearLayout) findViewById(R.id.dajhajsylayout);

        dajhajsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FajnaApka.class);
                startActivity(intent);
            }
        });
*/


        mocarnoscET = (EditText) findViewById(R.id.mocarnosc);

//strzałki na pasku

        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(doMainIntent);
            }
        });

//przycisk powrotu na pasku
        ImageView kiedy = (ImageView) findViewById(R.id.toolchangeBtn);


//klik do ilebije
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



//czcionka
        txt1 = (TextView) findViewById(R.id.TXT1);
        txt2 = (TextView) findViewById(R.id.TXT2);
        txt3 = (TextView) findViewById(R.id.TXT3);
        txt4 = (TextView) findViewById(R.id.TXT4);
        txt5 = (TextView) findViewById(R.id.TXT5);

       // fajnaapka = (TextView) findViewById(R.id.fajnaapka);
       // kliknij = (TextView) findViewById(R.id.kliknij);


        ilemg = (Button) findViewById(R.id.srodekPierwszy);
        ileml = (Button) findViewById(R.id.srodekDrugi);
        coile = (Button) findViewById(R.id.kiedyBije);
        pomozkoledze = (Button) findViewById(R.id.pomozkoledze);


        mgDlaMililitrow = (EditText) findViewById(R.id.mgDlaMililitrow);
        dniDlaMililitrow = (EditText) findViewById(R.id.dniDlaMililitrow);
        mocarnoscTxt = (TextView) findViewById(R.id.textMocarnosc);



        Typeface text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");
        txt1.setTypeface(text1);
        txt2.setTypeface(text1);
        txt3.setTypeface(text1);
        txt4.setTypeface(text1);
        txt5.setTypeface(text1);


        mocarnoscET.setTypeface(text1);
        mgDlaMililitrow.setTypeface(text1);
        dniDlaMililitrow.setTypeface(text1);
        mocarnoscTxt.setTypeface(text1);

        ilemg.setTypeface(text1);
        ileml.setTypeface(text1);
        coile.setTypeface(text1);
        pomozkoledze.setTypeface(text1);

       // fajnaapka.setTypeface(text1);
     //  kliknij.setTypeface(text1);



    }

    public void licz(View view) {

        mocarnoscET = (EditText) findViewById(R.id.mocarnosc);
        mgDlaMililitrow = (EditText) findViewById(R.id.mgDlaMililitrow);
        dniDlaMililitrow = (EditText) findViewById(R.id.dniDlaMililitrow);


        if (mocarnoscET.getText().toString().equals("")|| mgDlaMililitrow.getText().toString().equals("") || dniDlaMililitrow.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), "Uzupełnij dane Dziku ", Toast.LENGTH_LONG).show();

        } else {

            intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());
            mgNaMl = Integer.parseInt(mgDlaMililitrow.getText().toString());
            dniNaMl = Integer.parseInt(dniDlaMililitrow.getText().toString());
 //działanie
            zmiennaDwa = mgNaMl / intmocarnosc;
            intmililitry = (zmiennaDwa * dniNaMl) / stala;

            r = Math.round(intmililitry * 100.00) / 100.00;


            wynikml = (TextView) findViewById(R.id.wynikmililitry);
            wynikml.setText(String.valueOf("  " + r + "  ml"));

        }

//zagłębiony czy zduplikowany Listner działa dopiero po drugim nacisnieciu batona

        pomozkoledze.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (mocarnoscET.getText().toString().equals("")|| mgDlaMililitrow.getText().toString().equals("") || dniDlaMililitrow.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Uzupełnij dane Dziku ", Toast.LENGTH_LONG).show();

                } else {

 //powtorzona czynność zeby liczył za kazdym razem
                intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());

                mgNaMl = Integer.parseInt(mgDlaMililitrow.getText().toString());
                dniNaMl = Integer.parseInt(dniDlaMililitrow.getText().toString());


                zmiennaDwa = mgNaMl / intmocarnosc;

                intmililitry = (zmiennaDwa * dniNaMl) / stala;

                r = Math.round(intmililitry * 100.00) / 100.00;


                wynikml = (TextView) findViewById(R.id.wynikmililitry);
                wynikml.setText(String.valueOf("  " + r + "  ml"));





               //fajnaapka = (TextView) findViewById(R.id.fajnaapka);
               // kliknij = (TextView) findViewById(R.id.kliknij);
//                fajnaapka.setText("KLIKNIJ TUTAJ !!!");
    //            kliknij.setText("Fajna apka ???");

            }}
        });

    }

    public void ileMg(View view) {
        Intent ilemgintent = new Intent(getApplicationContext(), IleBijeMiligramow.class);

        intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());

        ilemgintent.putExtra("stezenie",intmocarnosc);

        startActivity(ilemgintent);
    }

    public void coIle(View view) {
        Intent coileintent = new Intent(getApplicationContext(), CoIleBije.class);

        intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());

        coileintent.putExtra("stezenie",intmocarnosc);

        startActivity(coileintent);
    }
}
