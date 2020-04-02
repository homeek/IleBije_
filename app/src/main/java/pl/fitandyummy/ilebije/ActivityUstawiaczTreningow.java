package pl.fitandyummy.ilebije;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import pl.fitandyummy.ilebije.broadcast.potwierdz_trening_Notyfication_reciver;
import pl.fitandyummy.ilebije.broadcast.ustaw_trening_Notyfication_reciver;

public class ActivityUstawiaczTreningow extends AppCompatActivity {


    private ImageButton StartWork, StopWork, ResetWork, StartRest, StopRest, ResetRest;
    TextView mocarnoscTxt, nasza, sprobuj, textView,  textView2, sumaWork, sumaRest;
    public Typeface text1;

    public EditText telebim,textVietData, textViewGodzina;

    public Chronometer chronometerWork;
    public Chronometer chronometerRest;
    public boolean runningWork;
    public boolean runningRest;
    public long pauseoffsetWork;
    public long pauseoffsetRest;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timepicker;


    public AlarmManager alarmManager;
    public AlarmManager alarmManager2;
    public PendingIntent pendingIntent;
    public PendingIntent pendingIntent2;


    public ScrollView mSvlaps2, mSvlaps;

    private int mRest, mLaps, elapsedMillisWork, elapsedMilisRest;

    private long intSumaWork, intSumaRest;


    private ImageView TheButtonStart, trzydziesciSekundOn, szescdziesiatSekundOn, saveButton;


    public String lap2, lap, przekazywana;

    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;

    private AdView mAdView;

    public boolean trzysziesciSekundRestBoolean, szescdziesiatSekundRestBoolen =false;

    private RecyclerView mRecyclerViewWork, mRecyclerViewRest;
    private RecyclerView.Adapter mAdapterWork, mAdapterRest;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<ElementyTreningu> listaETWork, listaETRest ;
    public ArrayList<ElementyListyCwiczen> listaCW;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawiacz_treningow);



//        createRecyclerViewWork();
    //    createRecyclerViewRest();

        //tworzy array list - musi bo sie wyjebie

        listaETWork = new ArrayList<ElementyTreningu>();
        listaETRest = new ArrayList<ElementyTreningu>();
        listaCW = new ArrayList<ElementyListyCwiczen>();

        mAdapterWork = new AdapterET(listaETWork);
        mAdapterRest = new AdapterET(listaETRest);

//        mRecyclerViewWork.setAdapter(mAdapterWork);
   //     mRecyclerViewRest.setAdapter(mAdapterWork);

        mAdapterWork.notifyDataSetChanged();


        //baner AdMob

       // MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~8122554600");

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");


        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()

             //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);


        //deklaracja obiektow z xml

     //   chronometerWork = (Chronometer) findViewById(R.id.chronoWork);
      //  chronometerRest = (Chronometer) findViewById(R.id.godzinaTextV);



        saveButton = (ImageView) findViewById(R.id.savebutton);


        trzydziesciSekundOn = (ImageView) findViewById(R.id.trzydziesciSOff);
        szescdziesiatSekundOn = (ImageView) findViewById(R.id.szescdziesiatSOff);


        textVietData = (EditText) findViewById(R.id.dataTextV);
        textViewGodzina = (EditText) findViewById(R.id.godzinaTextV);


//          textViewGodzina = (EditText) findViewById(R.id.godzinaTextV);
  //        textVietData = (EditText) findViewById(R.id.dataTextV);
   //     textView2 = (TextView) findViewById(R.id.mEtLaps);
    //    textView = (TextView) findViewById(R.id.mEtLaps2);
       // mSvlaps = (ScrollView) findViewById(R.id.mSvLaps);
       // mSvlaps2 = (ScrollView) findViewById(R.id.mSvLaps2);
    //    sumaWork = (TextView) findViewById(R.id.sumaWork);
    //    sumaRest = (TextView) findViewById(R.id.sumaRest);


        telebim = (EditText) findViewById(R.id.telebim);
        przekazywana = telebim.getText().toString();



        text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");

//        chronometerWork.setTypeface(text1);
  //      chronometerRest.setTypeface(text1);

        textViewGodzina.setTypeface(text1);
        textVietData.setTypeface(text1);

//        sumaRest.setTypeface(text1);
  //      sumaWork.setTypeface(text1);

        telebim.setTypeface(text1);












// pobiera date i godzine z classy calendar
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        final int h = c.get(Calendar.HOUR_OF_DAY);
        final int m = c.get(Calendar.MINUTE);


      //  textVietData.setText("");


//datapicker pobiera date i wstawia w tym przypadku w edittext
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datedayPicker, int i, int i1, int i2 ) {



                dataRok=i;
                dataMiesiac=i1;
                dataDzien=i2;
                textVietData.setText(i2 + "/" + String.format("%02d", i1 + 1) + "/" + i+" ");
            }



        },year, month, day);




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
                textViewGodzina.setText(hourOfDay + ":" + String.format("%02d", minute));
            }
        },h,m,true);


        textViewGodzina.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timepicker.show();
            }
        }));













        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

       /* back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),PreMainActivity.class);
                startActivity(doMainIntent);
            }
        });*/

        //przycisk powrotu na pasku
        ImageView ilebije = (ImageView) findViewById(R.id.toolchangeBtn);


        //klik do ilebije
      /*  ilebije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(doMainIntent);
            }
        });*/










        //datapicker pobiera date i wstawia w tym przypadku w edittext
   /*     datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datedayPicker, int i, int i1, int i2 ) {

                TextView data = (TextView) findViewById(R.id.edittext_data);

                dataRok=i;
                dataMiesiac=i1;
                dataDzien=i2;
                data.setText(i2 + "/" + String.format("%02d", i1 + 1) + "/" + i+" ");
            }



        },year, month, day);

       // datePickerDialog.show();


//to samo z godziną
        timepicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                TextView godzina = (TextView) findViewById(R.id.edittext_godzina);
                timegodziny = hourOfDay;
                timeminuty = minute;
                godzina.setText(hourOfDay + ":" + minute);
            }
        },h,m,true);

       // timepicker.show();


/*


        //pobieranie daty z tej chwili do wyswietlenia notyfikacji kończącej

        c.get(Calendar.YEAR);
        c.get(Calendar.MONTH);
        c.get(Calendar.DAY_OF_MONTH);
        c.get(Calendar.HOUR_OF_DAY);
        c.get(Calendar.MINUTE);


//tworzy intencje zbudowaną z osobnej class'ie
        Intent intent2 = new Intent(getApplicationContext(), ustaw_trening_Notyfication_reciver.class);
        pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(),321, intent2,PendingIntent.FLAG_UPDATE_CURRENT);




//jednorazowy alarm notyfikacji konczącej
        alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager2.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntent2);


 */






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



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                if (telebim.getText().toString().equals("")||textVietData.getText().toString().equals("")||textViewGodzina.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), " Uzupełnij dane  ", Toast.LENGTH_LONG).show();

                } else {

                    //ustawianie alarmu notyfikacji


                    //pobieranie daty z tej chwili do wyswietlenia notyfikacji kończącej

                    c.get(Calendar.YEAR);
                    c.get(Calendar.MONTH);
                    c.get(Calendar.DAY_OF_MONTH);
                    c.get(Calendar.HOUR_OF_DAY);
                    c.get(Calendar.MINUTE);


//tworzy intencje zbudowaną z osobnej class'ie
                    Intent intent2 = new Intent(getApplicationContext(), potwierdz_trening_Notyfication_reciver.class);
                    pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 456, intent2, PendingIntent.FLAG_UPDATE_CURRENT);


//jednorazowy alarm notyfikacji konczącej
                    alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager2.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent2);


                    //pobiera date i czas z datapickera

                    c.set(Calendar.DAY_OF_MONTH, dataDzien);
                    c.set(Calendar.MONTH, dataMiesiac);
                    c.set(Calendar.YEAR, dataRok);
                    c.set(Calendar.HOUR_OF_DAY, timegodziny);
                    c.set(Calendar.MINUTE, timeminuty);

                    //tworzy intencje zbudowaną w osobnej class'ie

                    Intent intent = new Intent(getApplicationContext(), ustaw_trening_Notyfication_reciver.class);
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 321, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres

                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

// wiadomosc toast witaj na bombie

                    Toast.makeText(getApplicationContext(), " Przypomnienie ustawione! ", Toast.LENGTH_LONG).show();


                    przekazywana = telebim.getText().toString();


                    saveData();


                    Intent doListyTreningowIntent = new Intent(getApplicationContext(), PreMainActivity.class);
                    startActivity(doListyTreningowIntent);

                }

            }
        });


    }



    //TU SIE KONCZY onCreate()




    public void doListyTreningowBaton(View view) {


        Intent doListyTreningowIntent = new Intent(getApplicationContext(),activity_lista_treningow.class);
        startActivity(doListyTreningowIntent);



    }


    private void saveData(){


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorr = preferences.edit();
        editorr.putString("key",przekazywana);
        editorr.putInt("godzinaZakonczenia",dataDzien);
        editorr.putInt("minuta",dataMiesiac);
        editorr.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("szered pref",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
       // Gson gson = new Gson();
        //String json = gson.toJson(listaTowarow);
       // editor.putString("dana dla jnosa",json);
        editor.apply();
    }


}

