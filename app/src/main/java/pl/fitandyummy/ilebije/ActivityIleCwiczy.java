package pl.fitandyummy.ilebije;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ActivityIleCwiczy extends AppCompatActivity {


    private ImageButton StartWork, StopWork, ResetWork, StartRest, StopRest, ResetRest;
    TextView mocarnoscTxt, nasza, sprobuj,  sumaWork, sumaRest;
    public Typeface text1;

    private TextView telebim;

    public Chronometer chronometerWork;
    public Chronometer chronometerRest;
    public boolean runningWork;
    public boolean runningRest;
    public long pauseoffsetWork;
    public long pauseoffsetRest;

    public ScrollView mSvlaps2, mSvlaps;

    private int mRest, mLaps, elapsedMillisWork, elapsedMilisRest;

    private int intSumaWork, intSumaRest;


    private ImageView TheButton, StopButton, trzydziesciSekundOn, szescdziesiatSekundOn;


    public String lap2, lap;

    private AdView mAdView;

    public boolean trzysziesciSekundRestBoolean, szescdziesiatSekundRestBoolen =false;
    public boolean stopSaveBoolen;

    private RecyclerView mRecyclerViewWork, mRecyclerViewRest;
    private RecyclerView.Adapter mAdapterWork, mAdapterRest;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<ElementyTreningu> listaETWork, listaETRest ;
    public ArrayList<ElementyListyCwiczen> listaCW;

     int year;
     int month;
     int day;

     int h ;
    int m ;
    int hStartu ;
    int mStartu ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ile_cwiczy2);





        createRecyclerViewWork();
        createRecyclerViewRest();

        //tworzy array list - musi bo sie wyjebie

        listaETWork = new ArrayList<ElementyTreningu>();
        listaETRest = new ArrayList<ElementyTreningu>();
        listaCW = new ArrayList<ElementyListyCwiczen>();

        mAdapterWork = new AdapterET(listaETWork);
        mAdapterRest = new AdapterET(listaETRest);

        mRecyclerViewWork.setAdapter(mAdapterWork);
        mRecyclerViewRest.setAdapter(mAdapterWork);

        mAdapterWork.notifyDataSetChanged();


        //baner AdMob

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~8122554600");

        //    MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");


        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()

             //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);


        //deklaracja obiektow z xml

        chronometerWork = (Chronometer) findViewById(R.id.chronoWork);
        chronometerRest = (Chronometer) findViewById(R.id.godzinaTextV);



        TheButton = (ImageView) findViewById(R.id.button2);
        trzydziesciSekundOn = (ImageView) findViewById(R.id.trzydziesciSOff);
        szescdziesiatSekundOn = (ImageView) findViewById(R.id.szescdziesiatSOff);

        StopButton = (ImageView) findViewById(R.id.savebutton);


        //  nasza = (TextView) findViewById(R.id.naszaapka);
        //  sprobuj = (TextView) findViewById(R.id.sprobuj);

       // mSvlaps = (ScrollView) findViewById(R.id.mSvLaps);
       // mSvlaps2 = (ScrollView) findViewById(R.id.mSvLaps2);
        sumaWork = (TextView) findViewById(R.id.sumaWork);
        sumaRest = (TextView) findViewById(R.id.sumaRest);


        telebim = (TextView) findViewById(R.id.telebim);


        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {

                // int position = viewHolder.getAdapterPosition();
                //  listaLTR.remove(position);
                //  mAdapterLT.notifyItemRemoved(position);
             //   listaETWork.remove(viewHolder.getAdapterPosition());
                listaETRest.remove(viewHolder.getAdapterPosition());
             //   mAdapterWork.notifyDataSetChanged();
                mAdapterRest.notifyDataSetChanged();

            }
        });

      //  helper.attachToRecyclerView(mRecyclerViewWork);
        helper.attachToRecyclerView(mRecyclerViewRest);


        ItemTouchHelper helper2 = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {

                // int position = viewHolder.getAdapterPosition();
                //  listaLTR.remove(position);
                //  mAdapterLT.notifyItemRemoved(position);
                listaETWork.remove(viewHolder.getAdapterPosition());
              //  listaETRest.remove(viewHolder.getAdapterPosition());
                mAdapterWork.notifyDataSetChanged();
              //  mAdapterRest.notifyDataSetChanged();

            }
        });

        helper2.attachToRecyclerView(mRecyclerViewWork);
       // helper.attachToRecyclerView(mRecyclerViewRest);


        Intent intent = getIntent();
        String nazwaCiczenia = intent.getStringExtra("nazwaCwiczenia");
        int rok = intent.getIntExtra("year",99);
        int miesiac = intent.getIntExtra("month",99);
        int dzien = intent.getIntExtra("day",99);
        int godzina = intent.getIntExtra("h",99);
        int minuty = intent.getIntExtra("m",99);
        boolean intentTrzysziesciSekundRestBoolean = intent.getBooleanExtra("boolean30",false);
        boolean intentSzescdziesiatSekundRestBoolen = intent.getBooleanExtra("boolean60",false);


        trzysziesciSekundRestBoolean = intentTrzysziesciSekundRestBoolean;
        szescdziesiatSekundRestBoolen = intentSzescdziesiatSekundRestBoolen;



         year = rok;
         month = miesiac;
         day = dzien;
         h = godzina;
         m = minuty;






        telebim.setText(nazwaCiczenia);



        text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");

        chronometerWork.setTypeface(text1);
        chronometerRest.setTypeface(text1);



        sumaRest.setTypeface(text1);
        sumaWork.setTypeface(text1);

        telebim.setTypeface(text1);

        ustawienieRestow();



    // główny button petla if else

        TheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!runningWork&&!runningRest) {
                    onClickStartWrok();
                    start();
                } else


             // GO REST
                if ( runningWork &&!runningRest){


                 //NORMAL REST

                    if (!trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){

                        //Toast.makeText(ActivityIleCwiczy.this, "kiedy szary", Toast.LENGTH_SHORT).show();


                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {

                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 10000){
                                }
                            }
                        });

                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();
                    } else


                 //30s REST

                    if (trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){

                        Toast.makeText(ActivityIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();

                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();

                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {
                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 30000){

                                    onClickStartWrok();
                                    letsWork();
                                    onClickStopRest();
                                    onClickResetRest();
                                }
                            }
                        });

                    }

                  //60s REST
                    if (!trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){

                        Toast.makeText(ActivityIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();
                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();

                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {
                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 60000){

                                    onClickStartWrok();
                                    letsWork();
                                    onClickStopRest();
                                    onClickResetRest();
                                }
                            }
                        });

                    }

     //90s REST
                    if (trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){

                        Toast.makeText(ActivityIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();
                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();

                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {
                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 90000){

                                    onClickStartWrok();
                                    letsWork();
                                    onClickStopRest();
                                    onClickResetRest();
                                }
                            }
                        });

                    }






                } else


      //LEST WORK
                if (!runningWork&&runningRest){






                    if (trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen) {

                        Toast.makeText(ActivityIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();

                    }

                    if (!trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen) {

                        Toast.makeText(ActivityIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();
                    }

                    if (trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen) {

                        Toast.makeText(ActivityIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();

                    }


                    if (!trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen) {


                        onClickStartWrok();
                        letsWork();
                        onClickStopRest();
                        onClickResetRest();
                    }




                }
            }
        });


        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

    /*    back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doPreIleCwiczyIntent = new Intent(getApplicationContext(),ActivityPreIleCwiczy.class);
                startActivity(doPreIleCwiczyIntent);
            }
        });*/

        //przycisk powrotu na pasku
        ImageView ilebije = (ImageView) findViewById(R.id.toolchangeBtn);


        //klik do ilebije
  /*      ilebije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(doMainIntent);
            }
        });*/

    // 30 60 90s rest


        trzydziesciSekundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){
                    onTrzydziesciS();

         //chrono na 30s

                    Toast.makeText(ActivityIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 30000){

                                onClickStartWrok();
                                letsWork();
                                onClickStopRest();
                                onClickResetRest();
                            }
                        }
                    });

                }else

                if (trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){
                    offTrzysiesciS();

         //chrono zerowy
                    Toast.makeText(ActivityIleCwiczy.this, "USER REST", Toast.LENGTH_SHORT).show();


                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {

                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 10000){
                            }
                        }
                    });

                }else

                if (trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){
                    offTrzysiesciS();





         //chrono 60s

                    Toast.makeText(ActivityIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 60000){

                                onClickStartWrok();
                                letsWork();
                                onClickStopRest();
                                onClickResetRest();
                            }
                        }
                    });

                }else

                if (!trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){
                    onTrzydziesciS();

         //chrono 90s

                    Toast.makeText(ActivityIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 90000){

                                onClickStartWrok();
                                letsWork();
                                onClickStopRest();
                                onClickResetRest();
                            }
                        }
                    });
                }



            }


        });

        szescdziesiatSekundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!szescdziesiatSekundRestBoolen&&trzysziesciSekundRestBoolean){
                    onSzescdziesiatS();

                    //chrono 90s

                    Toast.makeText(ActivityIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 90000){

                                onClickStartWrok();
                                letsWork();
                                onClickStopRest();
                                onClickResetRest();
                            }
                        }
                    });

                }else

                if (szescdziesiatSekundRestBoolen&&trzysziesciSekundRestBoolean){
                    offSzescdziesiatS();

                    //chrono 30s


                    Toast.makeText(ActivityIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 30000){

                                onClickStartWrok();
                                letsWork();
                                onClickStopRest();
                                onClickResetRest();
                            }
                        }
                    });







                }if (!szescdziesiatSekundRestBoolen&&!trzysziesciSekundRestBoolean){
                    onSzescdziesiatS();

                    //chrono 60

                    Toast.makeText(ActivityIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 60000){

                                onClickStartWrok();
                                letsWork();
                                onClickStopRest();
                                onClickResetRest();
                            }
                        }
                    });











                }else

                if (szescdziesiatSekundRestBoolen&&!trzysziesciSekundRestBoolean){
                    offSzescdziesiatS();

                    //chrono 0s
                    Toast.makeText(ActivityIleCwiczy.this, "USE REST", Toast.LENGTH_SHORT).show();


                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {

                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 10000){
                            }
                        }
                    });

                }

            }
        });

        StopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Stop();





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




  //zmiana The Buttona i kolorow  chronometra

    private void start() {

        //tworzy array list - musi bo sie wyjebie

        listaETRest = new ArrayList<ElementyTreningu>();
        mAdapterRest = new AdapterET(listaETRest);
        mRecyclerViewRest.setAdapter(mAdapterRest);
        mAdapterRest.notifyDataSetChanged();


        //tworzy array list - musi bo sie wyjebie

        listaETWork = new ArrayList<ElementyTreningu>();
        mAdapterWork = new AdapterET(listaETWork);
        mRecyclerViewWork.setAdapter(mAdapterWork);
        mAdapterWork.notifyDataSetChanged();


        TheButton.setImageResource(R.drawable.work);

        mLaps = 0;
        mRest = 0;



        //telebim.setTextColor(0xFFFF7F2A);
        //telebim.setText("WORK");
        //telebim.setTextColor(0xFFFF7F2A);
        chronometerWork.setTextSize(30);
        chronometerWork.setTextColor(0xFF05681C);
        chronometerRest.setTextSize(20);
        chronometerRest.setTextColor(Color.DKGRAY);

    }

    private void goRest() {


        stopSaveBoolen = true;
        StopButton.setImageResource(R.drawable.stop);



        TheButton.setImageResource(R.drawable.rest);


       // telebim.setTextColor(0xFFB73E02);
      //  telebim.setText("REST");
      //  telebim.setTextColor(0xFFB73E02);
        chronometerRest.setTextSize(30);
        chronometerRest.setTextColor(0xFFB73E02);
        chronometerWork.setTextSize(20);
        chronometerWork.setTextColor(Color.DKGRAY);












        lap = chronometerWork.getText().toString();


        mLaps++;



        //tworzy array list
        int position = 0;

        listaETRest.add(0,new ElementyTreningu("SET  no.", mLaps, lap));

                //odswierza i pokazuje array list
        mAdapterRest.notifyItemChanged(position);
        mAdapterRest.notifyDataSetChanged();

        //dodawanie czasów

        int elapsedMillisWork = (int) (SystemClock.elapsedRealtime() - chronometerWork.getBase()) ;

        intSumaWork=intSumaWork+elapsedMillisWork;

       // long intSumaWorkToString = intSumaWork;

       // sumaWork.setText(String.valueOf(intSumaWorkToString));

     /*   mSvlaps.post(new Runnable() {
            @Override
            public void run() {
                mSvlaps.smoothScrollTo(0, textView2.getBottom());

            }
        });*/

    }

    private void letsWork() {

        stopSaveBoolen = true;
        StopButton.setImageResource(R.drawable.stop);




        TheButton.setImageResource(R.drawable.work);

        //telebim.setTextColor(0xFFFF7F2A);
       // telebim.setText("WORK");
       // telebim.setTextColor(0xFFFF7F2A);
        chronometerWork.setTextSize(30);
        chronometerWork.setTextColor(0xFF05681C);
        chronometerRest.setTextSize(20);
        chronometerRest.setTextColor(Color.DKGRAY);

        lap2 = chronometerRest.getText().toString();


        mRest++;

        //tworzy array list
        int position = 0;

        listaETWork.add(0,new ElementyTreningu("REST  no.", mRest, lap2));

        //odswierza i pokazuje array list
        mAdapterWork.notifyItemChanged(position);
        mAdapterWork.notifyDataSetChanged();

        //dodawanie czasów

        int elapsedMillisRest = (int) (SystemClock.elapsedRealtime() - chronometerRest.getBase()) ;

        intSumaRest=intSumaRest+elapsedMillisRest;

       // long intSumaRestToString = intSumaRest;

       // sumaRest.setText(String.valueOf(intSumaRestToString));

/*        mSvlaps2.post(new Runnable() {
            @Override
            public void run() {
                mSvlaps.smoothScrollTo(0, textView.getBottom());

            }
        });*/

    }


        //funkcje chronometrow

    // funkcje głównego buttona dla chronometrow

        private void onClickStartRest() {

            if (!runningRest){
                chronometerRest.setBase(SystemClock.elapsedRealtime() - pauseoffsetRest);
                chronometerRest.start();
                runningRest = true;

            }

        }
        private void onClickStopRest() {
            if (runningRest) {
                chronometerRest.stop();
                pauseoffsetRest = SystemClock.elapsedRealtime() - chronometerRest.getBase();
                runningRest = false;
            }
        }
        private void onClickStopWork() {

            if (runningWork) {
                chronometerWork.stop();
                pauseoffsetWork = SystemClock.elapsedRealtime() - chronometerWork.getBase();
                runningWork = false;
            }
        }

        private void onClickStartWrok() {

            if (!runningWork){
                chronometerWork.setBase(SystemClock.elapsedRealtime() - pauseoffsetWork);
                chronometerWork.start();
                runningWork = true;


            }
        }



        public void onClickResetWork() {

            chronometerWork.setBase(SystemClock.elapsedRealtime());
            pauseoffsetWork = 0;
        }



        public void onClickResetRest() {

            chronometerRest.setBase(SystemClock.elapsedRealtime());
            pauseoffsetRest = 0;
        }




        public void Reset(View view) {




            //tworzy array list - musi bo sie wyjebie

            listaETRest = new ArrayList<ElementyTreningu>();
            mAdapterRest = new AdapterET(listaETRest);
            mRecyclerViewRest.setAdapter(mAdapterRest);
            mAdapterRest.notifyDataSetChanged();


            //tworzy array list - musi bo sie wyjebie

            listaETWork = new ArrayList<ElementyTreningu>();
            mAdapterWork = new AdapterET(listaETWork);
            mRecyclerViewWork.setAdapter(mAdapterWork);
            mAdapterWork.notifyDataSetChanged();


            mLaps = 0;
            mRest = 0;
            intSumaWork = 0;
            intSumaRest = 0;



            stopSaveBoolen = true;
            StopButton.setImageResource(R.drawable.stop);


            runningRest = false;
            runningWork = false;
            TheButton.setImageResource(R.drawable.start);
            //telebim.setText(" ");

            chronometerRest.stop();
            onClickResetRest();
            chronometerWork.stop();
            onClickResetWork();


            chronometerWork.setTextSize(20);
            chronometerWork.setTextColor(Color.DKGRAY);

            chronometerRest.setTextSize(20);
            chronometerRest.setTextColor(Color.DKGRAY);
        }



 //funkcje 30 60 senud


    public void onSzescdziesiatS() {

        szescdziesiatSekundOn.setImageResource(R.drawable.szescdziesiantson2);
        szescdziesiatSekundRestBoolen = true;


    }

    public void offSzescdziesiatS(){

        szescdziesiatSekundOn.setImageResource(R.drawable.szesdziesiatsoff2);
        szescdziesiatSekundRestBoolen = false;
    }

    public void onTrzydziesciS() {


        trzydziesciSekundOn.setImageResource(R.drawable.trzydziescison2);
        trzysziesciSekundRestBoolean = true;
    }

    public void offTrzysiesciS(){

        trzydziesciSekundOn.setImageResource(R.drawable.trzydziescisoff2);
        trzysziesciSekundRestBoolean = false;
    }


    //recycler view
    public void createRecyclerViewWork(){
        mRecyclerViewWork = findViewById(R.id.mSvLaps2);
        mRecyclerViewWork.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapterWork = new AdapterET(listaETWork);

        mRecyclerViewWork.setLayoutManager(mLayoutManager);
        mRecyclerViewWork.setAdapter(mAdapterWork);
    }

    public void createRecyclerViewRest(){
        mRecyclerViewRest = findViewById(R.id.mSvLaps);
        mRecyclerViewRest.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapterRest = new AdapterET(listaETRest);

        mRecyclerViewRest.setLayoutManager(mLayoutManager);
        mRecyclerViewRest.setAdapter(mAdapterRest);
    }

    public void Stop() {



        if (!stopSaveBoolen) {


            listaCW = new ArrayList<ElementyListyCwiczen>();

            String przekazywanNazwaCwiczenia = telebim.getText().toString();


           // intSumaWork=intSumaWork+elapsedMillisWork;
           // intSumaRest=intSumaRest+elapsedMilisRest;


            int secsW = intSumaWork/1000;
            int minsW = secsW/60;

            int secsR = intSumaRest/1000;
            int minsR = secsR/60;


            listaCW.add(0, new ElementyListyCwiczen(przekazywanNazwaCwiczenia, minsW+":"+String.format("%02d",secsW), minsR+":"+String.format("%02d",secsR), day + "/" + String.format("%02d", month + 1) + "/" + year + " ",
                    h + ":" + String.format("%02d",m)));


            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editorr = preferences.edit();
            editorr.putString("raz", "raxz");
            editorr.putInt("dwa", 2);
            editorr.putInt("czy", 3);
            editorr.putString("cztery", "cztery");
            editorr.putString("piec", "piec");
            editorr.apply();

            SharedPreferences sharedPreferences = getSharedPreferences("dupadupa", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(listaCW);
            editor.putString("dupacycki", json);
            editor.apply();


            Intent dolistyCwiczenIntent = new Intent(getApplicationContext(), activity_lista_cwiczen.class);

            //dolistyCwiczenIntent.putExtra("sumaWork",intSumaWork);
            // dolistyCwiczenIntent.putExtra("sumaRest",intSumaRest);


            startActivity(dolistyCwiczenIntent);
        }else

        if (stopSaveBoolen) {

            stopSaveBoolen = false;

            StopButton.setImageResource(R.drawable.save);

            chronometerRest.stop();

            chronometerWork.stop();


            chronometerWork.setTextSize(20);
            chronometerWork.setTextColor(Color.DKGRAY);

            chronometerRest.setTextSize(20);
            chronometerRest.setTextColor(Color.DKGRAY);


        }







    }

    public void ustawienieRestow(){



        if (trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){
            onTrzydziesciS();

            //chrono na 30s

            Toast.makeText(ActivityIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();


        }else

        if (!trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){

            offTrzysiesciS();
            offSzescdziesiatS();

            //chrono zerowy
            Toast.makeText(ActivityIleCwiczy.this, "USER REST", Toast.LENGTH_SHORT).show();


        }else

        if (!trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){
            offTrzysiesciS();
            onSzescdziesiatS();



            //chrono 60s

            Toast.makeText(ActivityIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();



        }else

        if (trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){
            onTrzydziesciS();
            onSzescdziesiatS();

            //chrono 90s

            Toast.makeText(ActivityIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();


        }







    }
}

