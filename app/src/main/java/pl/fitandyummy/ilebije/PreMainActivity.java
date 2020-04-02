package pl.fitandyummy.ilebije;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class PreMainActivity extends AppCompatActivity {

    TextView mocarnoscTxt, nasza, sprobuj;
    EditText mocarnoscEdt;
    Button ilebije, ileml, kiedybije;
    Bundle bundle;
    String stezenieStr;
    int intmocarnosc;

    private AdView mAdView;


    LinearLayout dajhajsy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_activity_main);



        //baner AdMob

           MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~8122554600");

        //   MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");


        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()

                //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);






       // nasza = (TextView) findViewById(R.id.naszaapka);
      //  sprobuj = (TextView) findViewById(R.id.sprobuj);

        ilebije = (Button) findViewById(R.id.ilebije);
        ileml = (Button) findViewById(R.id.facebookIleBije);
        kiedybije = (Button) findViewById(R.id.kiedyBije);


        Typeface text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");

        ilebije.setTypeface(text1);
       ileml.setTypeface(text1);
        kiedybije.setTypeface(text1);
      //  nasza.setTypeface(text1);
      //  sprobuj.setTypeface(text1);
        //stezenie = mocarnoscEdt.getText().toString();

       // stezenieStr = mocarnoscEdt.getText().toString();


       // stezenie = Integer.parseInt(stezenieStr);



        kiedybije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kalendarzintent = new Intent(getApplicationContext(),PudelkoActivity.class );
                startActivity(kalendarzintent);
            }
        });



        ilebije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kalkintent = new Intent(getApplicationContext(),MainActivity.class );
                startActivity(kalkintent);

            }
        });







        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),Intro.class);
                startActivity(doMainIntent);
            }
        });

     /*   dajhajsy = (LinearLayout) findViewById(R.id.dajhajsylayout);

        dajhajsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FY.class);
                startActivity(intent);
            }
        });
*/




    }


    public static Intent openFacebook(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/214946139076107"));
        } catch (Exception e){

            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/ilebije"));
        }


    }

//klik do ile cwiczy

    public void FBIB(View view) {


        Intent preileCwiczyIntent = new Intent(getApplicationContext(),ActivityPreIleCwiczy.class);
        startActivity(preileCwiczyIntent);
       // Intent facebookIntent = openFacebook(getApplicationContext());
       // startActivity(facebookIntent);
    }



}


