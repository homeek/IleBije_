package pl.fitandyummy.ilebije;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextView zarejestrujTXT;
    TextView zalogujBTN;
    EditText loginETD;
    EditText passwordETD;
    ProgressBar progresbar;
    FirebaseAuth fAuth;
    Button ciach;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        zarejestrujTXT = (TextView) findViewById(R.id.textZarejestruj);
        loginETD = (EditText) findViewById(R.id.login);
        passwordETD = (EditText) findViewById(R.id.password);
        progresbar = (ProgressBar) findViewById(R.id.progressBar);
        zalogujBTN = (TextView) findViewById(R.id.textButtonZaloguj);
        ciach = (Button) findViewById(R.id.ciach);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Typeface text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");
        zarejestrujTXT.setTypeface(text1);
        loginETD.setTypeface(text1);
        zalogujBTN.setTypeface(text1);
        ciach.setTypeface(text1);

//baner AdMob
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~8122554600");
        //  MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

//przyciski  na pasku
        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(), PreMainActivity.class);
                startActivity(doMainIntent);
            }
        });

        ImageView kiedy = (ImageView) findViewById(R.id.toolchangeBtn);
        kiedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doPudelka = new Intent(getApplicationContext(), PudelkoActivity.class);
                startActivity(doPudelka);
            }
        });

// przycisk do logowania
        zalogujBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent zalogujIntent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(zalogujIntent);
            }
        });

        //KOD

        if (fAuth.getCurrentUser() != null) {

            String loginZFB = fAuth.getCurrentUser().getEmail();
            Toast.makeText(Register.this, "Juz Zalogowany jako " + loginZFB, Toast.LENGTH_LONG).show();

            // startActivity(new Intent(getApplicationContext(),PreMainActivity.class));
        }

        ciach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login = loginETD.getText().toString();
                String password = passwordETD.getText().toString();

                if (TextUtils.isEmpty(login)) {
                    loginETD.setError("wpisz login");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordETD.setError("wpisz hasło");
                    return;
                }

                if (password.length() < 6) {
                    passwordETD.setError("minimum 6 znaków");
                }

                progresbar.setVisibility(View.VISIBLE);

                //logowanie do Fire Base
                fAuth.createUserWithEmailAndPassword(login, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Zalogowano", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), PreMainActivity.class));
                            return;
                        } else {
                            Toast.makeText(Register.this, "Błąd" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progresbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    public void ciachfStore(View view) {
        String login = loginETD.getText().toString();
        String password = passwordETD.getText().toString();

        final String userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("user").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("login", login);
        user.put("password", password);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Register.this, "Juz Zalogowany jako " + userID, Toast.LENGTH_LONG).show();
            }
        });
    }
}