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

public class CoIleBije extends AppCompatActivity {

    TextView wynikdni, txt1, txt2, txt3, txt4, txt5, txt6, mocarnoscTxt;

    int intmocarnosc;

    double stala = 7;
    double zmiennaCzy;
    double r;

    private AdView mAdView;

    Button ilemg, ileml, coile, pomozkoledze;

    String stezenieStr;

    EditText mocarnoscET;
    EditText mlDlaDni;
    EditText mgDlaDni;

    double mlNaDni;
    double mgNaDni;
    double intDni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_ile_bije);

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
                Intent doMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(doMainIntent);
            }
        });

        //przycisk  na pasku
        ImageView kiedy = (ImageView) findViewById(R.id.toolchangeBtn);
        kiedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doPudelka = new Intent(getApplicationContext(), PudelkoActivity.class);
                startActivity(doPudelka);
            }
        });

        int stezenie = getIntent().getIntExtra("stezenie", 888);

        stezenieStr = String.valueOf(stezenie);

        mocarnoscET.setText(stezenieStr);

        txt1 = (TextView) findViewById(R.id.TXT1);
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
        mlDlaDni = (EditText) findViewById(R.id.mililitryNaDni);
        mgDlaDni = (EditText) findViewById(R.id.mgNaDni);
        mocarnoscTxt = (TextView) findViewById(R.id.textMocarnosc);

        Typeface text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");
        txt1.setTypeface(text1);
        txt2.setTypeface(text1);
        txt3.setTypeface(text1);
        txt4.setTypeface(text1);
        txt5.setTypeface(text1);
        txt6.setTypeface(text1);

        mocarnoscET.setTypeface(text1);
        mlDlaDni.setTypeface(text1);
        mgDlaDni.setTypeface(text1);
        mocarnoscTxt.setTypeface(text1);

        ilemg.setTypeface(text1);
        ileml.setTypeface(text1);
        coile.setTypeface(text1);
        pomozkoledze.setTypeface(text1);
    }

    public void ileMg(View view) {
        Intent ilemgintent = new Intent(getApplicationContext(), IleBijeMiligramow.class);
        intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());
        ilemgintent.putExtra("stezenie", intmocarnosc);
        startActivity(ilemgintent);
    }

    public void ileMl(View view) {
        Intent ilemlintent = new Intent(getApplicationContext(), IleBijeMililitrow.class);
        intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());
        ilemlintent.putExtra("stezenie", intmocarnosc);
        startActivity(ilemlintent);
    }

    public void licz(View view) {
        mocarnoscET = (EditText) findViewById(R.id.mocarnosc);
        mlDlaDni = (EditText) findViewById(R.id.mililitryNaDni);
        mgDlaDni = (EditText) findViewById(R.id.mgNaDni);

        if (mocarnoscET.getText().toString().equals("") || mlDlaDni.getText().toString().equals("") || mgDlaDni.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Dziku ", Toast.LENGTH_LONG).show();

        } else {
            intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());
            mlNaDni = Double.parseDouble(mlDlaDni.getText().toString());
            mgNaDni = Integer.parseInt(mgDlaDni.getText().toString());

            zmiennaCzy = mgNaDni / intmocarnosc;
            intDni = (mlNaDni * stala) / zmiennaCzy;

            r = Math.round(intDni * 10.00) / 10.00;

            wynikdni = (TextView) findViewById(R.id.wynikDni);
            wynikdni.setText(String.valueOf("  " + r + " "));
        }

        pomozkoledze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mocarnoscET.getText().toString().equals("") || mlDlaDni.getText().toString().equals("") || mgDlaDni.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Uzupełnij dane Dziku ", Toast.LENGTH_LONG).show();

                } else {
                    intmocarnosc = Integer.parseInt(mocarnoscET.getText().toString());
                    mlNaDni = Double.parseDouble(mlDlaDni.getText().toString());
                    mgNaDni = Integer.parseInt(mgDlaDni.getText().toString());

                    zmiennaCzy = mgNaDni / intmocarnosc;

                    intDni = (mlNaDni * stala) / zmiennaCzy;

                    r = Math.round(intDni * 10.00) / 10.00;

                    wynikdni = (TextView) findViewById(R.id.wynikDni);
                    wynikdni.setText(String.valueOf("  " + r + " "));
                }
            }
        });
    }
}