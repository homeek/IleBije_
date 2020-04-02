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

public class MainActivity extends AppCompatActivity {

    TextView mocarnoscTxt, nasza, sprobuj;
    EditText mocarnoscEdt;
    Button ilemg, ileml, coile;
    Bundle bundle;
    String stezenieStr;
    int intmocarnosc;

    private AdView mAdView;


    LinearLayout dajhajsy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mocarnoscTxt = (TextView) findViewById(R.id.textMocarnosc);
      //  nasza = (TextView) findViewById(R.id.naszaapka);
      //  sprobuj = (TextView) findViewById(R.id.sprobuj);
        mocarnoscEdt = (EditText) findViewById(R.id.mocarnosc);
        ilemg = (Button) findViewById(R.id.srodekPierwszy);
        ileml = (Button) findViewById(R.id.srodekDrugi);
        coile = (Button) findViewById(R.id.kiedyBije);


        Typeface text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");
        mocarnoscTxt.setTypeface(text1);
        mocarnoscEdt.setTypeface(text1);
        ilemg.setTypeface(text1);
        ileml.setTypeface(text1);
        coile.setTypeface(text1);
//        nasza.setTypeface(text1);
     //   sprobuj.setTypeface(text1);
        //stezenie = mocarnoscEdt.getText().toString();

       // stezenieStr = mocarnoscEdt.getText().toString();


       // stezenie = Integer.parseInt(stezenieStr);



        //baner AdMob

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~8122554600");

        //  MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");


        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()

                //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);









        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),PreMainActivity.class);
                startActivity(doMainIntent);
            }
        });

       // dajhajsy = (LinearLayout) findViewById(R.id.dajhajsylayout);

       /* dajhajsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FY.class);
                startActivity(intent);
            }
        });

*/
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





    }



    public void ileMg(View view) {

        if (mocarnoscEdt.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), " Podaj mocarność eliksiru ", Toast.LENGTH_LONG).show();

        } else {




        Intent ilemgintent = new Intent(getApplicationContext(), IleBijeMiligramow.class);

        intmocarnosc = Integer.parseInt(mocarnoscEdt.getText().toString());

        ilemgintent.putExtra("stezenie",intmocarnosc);

        startActivity(ilemgintent);





    }}

    public void ileMl(View view) {



        if (mocarnoscEdt.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), "Podaj mocarność eliksiru", Toast.LENGTH_LONG).show();

        } else {


        Intent ilemlintent = new Intent(getApplicationContext(), IleBijeMililitrow.class);

        intmocarnosc = Integer.parseInt(mocarnoscEdt.getText().toString());

        ilemlintent.putExtra("stezenie",intmocarnosc);


        startActivity(ilemlintent);
    }}

    public void coIle(View view) {

        if (mocarnoscEdt.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), "Podaj mocarność eliksiru ", Toast.LENGTH_LONG).show();

        } else {


        Intent coilebijeintent = new Intent(getApplicationContext(), CoIleBije.class);

        intmocarnosc = Integer.parseInt(mocarnoscEdt.getText().toString());

        coilebijeintent.putExtra("stezenie",intmocarnosc);

        startActivity(coilebijeintent);


    }}



}
