package pl.fitandyummy.ilebije;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityPreIleCwiczy extends AppCompatActivity {

    TextView textVietData, textViewGodzina;
    public Typeface text1;
    private EditText telebim;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timepicker;
    private ImageView TheButtonStart, trzydziesciSekundOn, szescdziesiatSekundOn;
    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;

    private AdView mAdView;
    public boolean trzysziesciSekundRestBoolean, szescdziesiatSekundRestBoolen = false;

    private RecyclerView.Adapter mAdapterWork, mAdapterRest;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<ElementyTreningu> listaETWork, listaETRest;
    public ArrayList<ElementyListyCwiczen> listaCW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_ile_cwiczy);

        listaETWork = new ArrayList<ElementyTreningu>();
        listaETRest = new ArrayList<ElementyTreningu>();
        listaCW = new ArrayList<ElementyListyCwiczen>();

        mAdapterWork = new AdapterET(listaETWork);
        mAdapterRest = new AdapterET(listaETRest);

        mAdapterWork.notifyDataSetChanged();

//baner AdMob
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~8122554600");
        //  MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

//deklaracja obiektow z xml
        TheButtonStart = (ImageView) findViewById(R.id.savebutton);
        trzydziesciSekundOn = (ImageView) findViewById(R.id.trzydziesciSOff);
        szescdziesiatSekundOn = (ImageView) findViewById(R.id.szescdziesiatSOff);
        textViewGodzina = (TextView) findViewById(R.id.godzinaTextV);
        textVietData = (TextView) findViewById(R.id.dataTextV);
        telebim = (EditText) findViewById(R.id.telebim);

        text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");

        textViewGodzina.setTypeface(text1);
        textVietData.setTypeface(text1);
        telebim.setTypeface(text1);

// pobiera date i godzine z classy calendar
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int h = c.get(Calendar.HOUR_OF_DAY);
        final int m = c.get(Calendar.MINUTE);

        textViewGodzina.setText(h + ":" + String.format("%02d", m));
        textVietData.setText(day + "/" + String.format("%02d", month + 1) + "/" + year + " ");

//datapicker pobiera date i wstawia w tym przypadku w edittext
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datedayPicker, int i, int i1, int i2) {

                dataRok = i;
                dataMiesiac = i1;
                dataDzien = i2;
                textVietData.setText(i2 + "/" + String.format("%02d", i1 + 1) + "/" + i + " ");
            }


        }, year, month, day);
        textVietData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

//to samo z godziną
        timepicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                timegodziny = hourOfDay;
                timeminuty = minute;
                textViewGodzina.setText(hourOfDay + ":" + minute);
            }
        }, h, m, true);
        textViewGodzina.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker.show();
            }
        }));

// gbutton START
        TheButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (telebim.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), " Podaj nazwę ćwiczenia ", Toast.LENGTH_LONG).show();

                } else {

                    String nazwaCwiczenia = telebim.getText().toString();

                    Intent doIleCwiczyIntent = new Intent(getApplicationContext(), ActivityIleCwiczy.class);
                    doIleCwiczyIntent.putExtra("nazwaCwiczenia", nazwaCwiczenia);
                    doIleCwiczyIntent.putExtra("boolean30", trzysziesciSekundRestBoolean);
                    doIleCwiczyIntent.putExtra("boolean60", szescdziesiatSekundRestBoolen);
                    doIleCwiczyIntent.putExtra("year", year);
                    doIleCwiczyIntent.putExtra("month", month);
                    doIleCwiczyIntent.putExtra("day", day);
                    doIleCwiczyIntent.putExtra("h", h);
                    doIleCwiczyIntent.putExtra("m", m);

                    startActivity(doIleCwiczyIntent);
                }
            }
        });

//przycisk powrotu na pasku
        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(), PreMainActivity.class);
                startActivity(doMainIntent);
            }
        });

// 30 60 90s rest
        trzydziesciSekundOn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (!trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
                    onTrzydziesciS();

                    //chrono na 30s
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();

                } else if (trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
                    offTrzysiesciS();

                    //chrono zerowy
                    Toast.makeText(ActivityPreIleCwiczy.this, "USER REST", Toast.LENGTH_SHORT).show();

                } else if (trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
                    offTrzysiesciS();

                    //chrono 60s
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();

                } else if (!trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
                    onTrzydziesciS();

                    //chrono 90s
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();
                }
            }


        });

        szescdziesiatSekundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!szescdziesiatSekundRestBoolen && trzysziesciSekundRestBoolean) {
                    onSzescdziesiatS();

                    //chrono 90s
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();

                } else if (szescdziesiatSekundRestBoolen && trzysziesciSekundRestBoolean) {
                    offSzescdziesiatS();

                    //chrono 30s
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();
                }
                if (!szescdziesiatSekundRestBoolen && !trzysziesciSekundRestBoolean) {
                    onSzescdziesiatS();

                    //chrono 60
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();

                } else if (szescdziesiatSekundRestBoolen && !trzysziesciSekundRestBoolean) {
                    offSzescdziesiatS();

                    //chrono 0s
                    Toast.makeText(ActivityPreIleCwiczy.this, "USE REST", Toast.LENGTH_SHORT).show();
                }
            }
        });

//funkcje banera reklamowy AdMob
        mAdView.setAdListener(new AdListener() {
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
        });
    }
//TU SIE KONCZY onCreate()

    //funkcje 30 60 senud

    public void onSzescdziesiatS() {
        szescdziesiatSekundOn.setImageResource(R.drawable.szescdziesiantson2);
        szescdziesiatSekundRestBoolen = true;
    }

    public void offSzescdziesiatS() {
        szescdziesiatSekundOn.setImageResource(R.drawable.szesdziesiatsoff2);
        szescdziesiatSekundRestBoolen = false;
    }

    public void onTrzydziesciS() {
        trzydziesciSekundOn.setImageResource(R.drawable.trzydziescison2);
        trzysziesciSekundRestBoolean = true;
    }

    public void offTrzysiesciS() {
        trzydziesciSekundOn.setImageResource(R.drawable.trzydziescisoff2);
        trzysziesciSekundRestBoolean = false;
    }

    public void doListyTreningowBaton(View view) {
        Intent doListyTreningowIntent = new Intent(getApplicationContext(), activity_lista_treningow.class);
        startActivity(doListyTreningowIntent);
    }
}