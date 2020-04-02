package pl.fitandyummy.ilebije;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;


//import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import pl.fitandyummy.ilebije.srodki.activity_srodek1;
import pl.fitandyummy.ilebije.srodki.activity_srodek2;
import pl.fitandyummy.ilebije.srodki.activity_srodek3;
import pl.fitandyummy.ilebije.srodki.activity_srodek4;
import pl.fitandyummy.ilebije.srodki.activity_srodek5;
import pl.fitandyummy.ilebije.srodki.activity_srodek6;

public class PudelkoActivity extends AppCompatActivity {

    private ImageButton srodek1, srodek2, srodek3, srodek4, srodek5, srodek6;
    TextView mocarnoscTxt, nasza, sprobuj;
    public Typeface text1;

    LinearLayout dajhajsy;

    private AdView mAdView;

  //  private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pudelko);



        //baner AdMob

          MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~8122554600");

        //   MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");


        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()

                //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);


// TODO: Add adView to your view hierarchy.


        srodek1 = (ImageButton) findViewById(R.id.srodekPierwszy);
        srodek2 = (ImageButton) findViewById(R.id.srodekDrugi);
        srodek3 = (ImageButton) findViewById(R.id.srodekTrzeci);
        srodek4 = (ImageButton) findViewById(R.id.srodekCzwarty);
        srodek5 = (ImageButton) findViewById(R.id.srodekPiaty);
        srodek6 = (ImageButton) findViewById(R.id.srodekSzosty);

      //  nasza = (TextView) findViewById(R.id.naszaapka);
      //  sprobuj = (TextView) findViewById(R.id.sprobuj);





        text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");

      //  nasza.setTypeface(text1);
      //  sprobuj.setTypeface(text1);




        srodek1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),activity_srodek1.class);
                startActivity(intent);
            }
        });

        srodek2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),activity_srodek2.class);
                startActivity(intent2);
            }
        });

        srodek3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(),activity_srodek3.class);
                startActivity(intent3);
            }
        });

        srodek4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getApplicationContext(),activity_srodek4.class);
                startActivity(intent4);
            }
        });
        srodek5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(getApplicationContext(),activity_srodek5.class);
                startActivity(intent5);
            }
        });
        srodek6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getApplicationContext(),activity_srodek6.class);
                startActivity(intent6);
            }
        });



        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),PreMainActivity.class);
                startActivity(doMainIntent);
            }
        });

    //    dajhajsy = (LinearLayout) findViewById(R.id.dajhajsylayout);

    /*    dajhajsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FY.class);
                startActivity(intent);
            }
        });
*/


        //przycisk powrotu na pasku
        ImageView ilebije = (ImageView) findViewById(R.id.toolchangeBtn);


        //klik do ilebije
        ilebije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(doMainIntent);
            }
        });

/*        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });*/
    }



}
