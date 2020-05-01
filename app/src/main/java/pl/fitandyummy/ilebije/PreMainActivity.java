package pl.fitandyummy.ilebije;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import com.google.firebase.auth.FirebaseAuth;

public class PreMainActivity extends AppCompatActivity {

    Button ilebije, ileml, kiedybije;
    TextView zaloguj, wyloguj;
    FirebaseAuth fAuth;

    private AdView mAdView;

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

        ilebije = (Button) findViewById(R.id.ilebije);
        ileml = (Button) findViewById(R.id.facebookIleBije);
        kiedybije = (Button) findViewById(R.id.kiedyBije);
        zaloguj = (TextView) findViewById(R.id.textButtonZaloguj);
        wyloguj = (TextView) findViewById(R.id.textButtonWyloguj);

        Typeface text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");

        ilebije.setTypeface(text1);
        ileml.setTypeface(text1);
        kiedybije.setTypeface(text1);
        zaloguj.setTypeface(text1);
        wyloguj.setTypeface(text1);

        fAuth = FirebaseAuth.getInstance();

//wskaznik zalogowania
        if (fAuth.getCurrentUser() != null) {
            String loginZFB = fAuth.getCurrentUser().getEmail();
            zaloguj.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGreenDark));
            zaloguj.setText("zalogowany jako   " + loginZFB);
            wyloguj.setVisibility(View.VISIBLE);
        }

        kiedybije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kalendarzintent = new Intent(getApplicationContext(), PudelkoActivity.class);
                startActivity(kalendarzintent);
            }
        });

        ilebije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kalkintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(kalkintent);
            }
        });

        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(), Intro.class);
                startActivity(doMainIntent);
            }
        });

        zaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent zalogujIntent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(zalogujIntent);
            }
        });
    }

    public static Intent openFacebook(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/214946139076107"));
        } catch (Exception e) {

            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/ilebije"));
        }
    }

//klik do ile cwiczy

    public void FBIB(View view) {
        Intent preileCwiczyIntent = new Intent(getApplicationContext(), ActivityPreIleCwiczy.class);
        startActivity(preileCwiczyIntent);
    }

    public void wyloguj(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), PreMainActivity.class));
        finish();
    }
}