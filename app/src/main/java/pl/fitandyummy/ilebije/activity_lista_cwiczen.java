package pl.fitandyummy.ilebije;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class activity_lista_cwiczen extends AppCompatActivity {


    Bundle bundle;
    public ArrayList<ElementyListyCwiczen> listaCW,  obieListy, listaZapisanychCW;
    public ArrayList<ElementyListyTreningow> listaLTR;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //public AlarmManager alarmManagerRaz;
  //  public AlarmManager alarmManager2;
   // public PendingIntent pendingIntentTrzy;
   // public PendingIntent pendingIntent2;
    public Typeface text1;
    private String nazwaCwiczen, godzinaStartu, godzinaStartuDoListyTreningow ;

    private String nazwaCwiczenDoListyTreningow = "  ";

    private TextView nazwaCwiczenia, czasRest, czasWork, data, godzina, telebim;

    private EditText koment;

/*
    private EditText mililitry;
    private EditText nazwaTowca;
    private Button btnInsert;
    private Button btnReset;
    private EditText liczba_szczalow;
    private EditText edt_godzina;
    private EditText data;
    private TextView nazwaTowaruu;
    private EditText edtokres;



    public String przekazywanaszesc;
    private String przekazywana_iloscml;
    private int przekazywana_iloscszczał;

    public int okres;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timepicker;

    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;


*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cwiczen);

        loadData();

        createRecyclerView();

        koment =findViewById(R.id.edittext_koment);

        text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");

        telebim = findViewById(R.id.telebim);
        telebim.setTypeface(text1);
        koment.setTypeface(text1);



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
                listaZapisanychCW.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyDataSetChanged();

            }
        });

        helper.attachToRecyclerView(mRecyclerView);


       /* mililitry =findViewById(R.id.edittext_ml);
        nazwaTowca =findViewById(R.id.edittext_nazwaTowaru);
        btnInsert=findViewById(R.id.button_insert);
        btnReset=findViewById(R.id.button_reset);

        liczba_szczalow=findViewById(R.id.edittext_liczna);
        edt_godzina=findViewById(R.id.edittext_godzina);

        edtokres =findViewById(R.id.coIleDni);*/

 /*

        komentarzTreningu=findViewById(R.id.komentarzTreningu);
        nazwyCwiczenia=findViewById(R.id.nazwyCwiczenia);
        godzinaStartu=findViewById(R.id.godzinaStartu);
        data=findViewById(R.id.data);
        godzinaZakonczenia=findViewById(R.id.godzinaZakonczenia);





        komentarzTreningu.setTypeface(text1);
        nazwyCwiczenia.setTypeface(text1);
        godzinaStartu.setTypeface(text1);
        godzinaZakonczenia.setTypeface(text1);
        data.setTypeface(text1);


       liczba_szczalow.setTypeface(text1);
        edt_godzina.setTypeface(text1);
        edtokres.setTypeface(text1);*/






/*



// pobiera date i godzine z classy calendar
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        final int h = c.get(Calendar.HOUR_OF_DAY);
        final int m = c.get(Calendar.MINUTE);


        data.setText("");


//datapicker pobiera date i wstawia w tym przypadku w edittext
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datedayPicker, int i, int i1, int i2 ) {

                TextView data = (TextView) findViewById(R.id.edittext_data);

                dataRok=i;
                dataMiesiac=i1;
                dataDzien=i2;
                data.setText(i2 + "/" + String.format("%02d", i1 + 1) + "/" + i+" ");
            }



        },year, month, day);




        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });


//to samo z godziną
        timepicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                 TextView godzinaZakonczenia = (TextView) findViewById(R.id.edittext_godzina);
                 timegodziny = hourOfDay;
                 timeminuty = minute;
               godzinaZakonczenia.setText(hourOfDay + ":" + minute);
            }
        },h,m,true);


        edt_godzina.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timepicker.show();
            }
        }));


//klik WCHODZE
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (nazwaTowca.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();

                } else if (data.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();

                } else if (liczba_szczalow.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();

                } else if (edtokres.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();

                } else if (edt_godzina.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();

                } else if (mililitry.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();

                } else{

                int position = 0;

                int liczba = Integer.parseInt(liczba_szczalow.getText().toString());



//petla wstawiająca kazdy kolejny strzał

                for ( int w = 0; w < liczba; w++ ) {

                    nazwaTowca =findViewById(R.id.edittext_nazwaTowaru);
                    przekazywanaszesc = nazwaTowca.getText().toString();



                    mililitry =findViewById(R.id.edittext_ml);
                    przekazywana_iloscml =  mililitry.getText().toString();

                    liczba_szczalow =findViewById(R.id.edittext_liczna);
                    przekazywana_iloscszczał =  Integer.parseInt(liczba_szczalow.getText().toString());




                    edtokres =findViewById(R.id.coIleDni);
                    okres =  Integer.parseInt(edtokres.getText().toString());




                 //ustawia date i godzine dla notyfikacji date bierze z datapickera

                    c.set(Calendar.DAY_OF_MONTH,dataDzien);
                    c.set(Calendar.MONTH,dataMiesiac);
                    c.set(Calendar.YEAR,dataRok);
                    c.set(Calendar.HOUR_OF_DAY, timegodziny);
                    c.set(Calendar.MINUTE,timeminuty);

                //dodaje okres do liczby dni, uzywane do wyswietlania kolejnej daty w spisie strzałów

                    c.add(Calendar.DAY_OF_MONTH,w*okres);


                    Date dupa  = c.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String date = sdf.format(dupa);




                //tworzy array list
                    listaLTR.add(new ElementyKalendarza(R.drawable.fiolka, przekazywanaszesc+"", "  "+przekazywana_iloscml+"ml", "strzał nr. " , w+1 , date, timegodziny + ":" + timeminuty));

                 //odswierza i pokazuje array list
                    mAdapter.notifyItemChanged(position);
                    mAdapter.notifyDataSetChanged();

                }




   //ustawianie alarmu notyfikacji

                //pobiera date i czas z datapickera

                c.set(Calendar.DAY_OF_MONTH,dataDzien);
                c.set(Calendar.MONTH,dataMiesiac);
                c.set(Calendar.YEAR,dataRok);
                c.set(Calendar.HOUR_OF_DAY, timegodziny);
                c.set(Calendar.MINUTE,timeminuty);

        //tworzy intencje zbudowaną w osobnej class'ie

                Intent intent = new Intent(getApplicationContext(), srodek_jeden_Notyfication_reciver.class);
                pendingIntentTrzy = PendingIntent.getBroadcast(getApplicationContext(),100, intent,PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres

                alarmManagerRaz = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManagerRaz.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY*okres,pendingIntentTrzy);

// wiadomosc toast witaj na bombie

                    Toast.makeText(getApplicationContext(), " Witaj na Bombie BYKU !!! ", Toast.LENGTH_LONG).show();


// zapis array list
                saveData();


            }}


        });


        //klik ZCHODZE
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(alarmManagerRaz!=null) {
                    alarmManagerRaz.cancel(pendingIntentTrzy);

                }else{

                    Intent intent = new Intent(getApplicationContext(), srodek_jeden_Notyfication_reciver.class);

                    pendingIntentTrzy = PendingIntent.getBroadcast(getApplicationContext(),100, intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManagerRaz = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManagerRaz.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntentTrzy);

                    alarmManagerRaz.cancel(pendingIntentTrzy);
                }



//pobieranie daty z tej chwili do wyswietlenia notyfikacji kończącej

                c.get(Calendar.YEAR);
                c.get(Calendar.MONTH);
                c.get(Calendar.DAY_OF_MONTH);
                c.get(Calendar.HOUR_OF_DAY);
                c.get(Calendar.MINUTE);


//tworzy intencje zbudowaną z osobnej class'ie
               Intent intent2 = new Intent(getApplicationContext(), KoniecNotyfication_reciver.class);
                pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(),200, intent2,PendingIntent.FLAG_UPDATE_CURRENT);




//jednorazowy alarm notyfikacji konczącej
                alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntent2);

// czyszczenie listy
                listaLTR.clear();


//tworzy array list - musi bo sie wyjebie
                listaLTR = new ArrayList<ElementyKalendarza>();

                mAdapter = new AdapterTowarow(listaLTR);

                mRecyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();

//zapisuje pustą array list
                saveData();


            }
        });

*/

//przycisk powrotu na pasku
        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);


        //klik powrót
   /*     back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveCW();

                Intent doPreMainIntent = new Intent(getApplicationContext(),ActivityIleCwiczy.class);
                startActivity(doPreMainIntent);
            }
        });*/



//przycisk powrotu na pasku
        ImageView ilebije = (ImageView) findViewById(R.id.toolchangeBtn);


        //klik do ilebije
   /*     ilebije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveCW();

                Intent doMainIntent = new Intent(getApplicationContext(),ActivityIleCwiczy.class);
                startActivity(doMainIntent);
            }
        });*/



    }









    public void createRecyclerView(){

        mRecyclerView = findViewById(R.id.listaTreningowRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdapterListyCwiczen(listaZapisanychCW);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }



    private void loadData(){







        SharedPreferences sharedPreferences2 = getSharedPreferences("cyckicycki",0);
        Gson gson2 = new Gson();
        String json2 = sharedPreferences2.getString("cyckidupa", null);
        Type type2 = new TypeToken<ArrayList<ElementyListyCwiczen>>() {}.getType();
        listaZapisanychCW = gson2.fromJson(json2,type2);

        SharedPreferences sharedPreferences = getSharedPreferences("dupadupa",0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dupacycki", null);
        Type type = new TypeToken<ArrayList<ElementyListyCwiczen>>() {}.getType();
        listaCW = gson.fromJson(json,type);




        if (listaZapisanychCW ==null){

            listaZapisanychCW = new ArrayList<ElementyListyCwiczen>();



        }

        //listaLTR = new ArrayList<ElementyListyCwiczen>();

       // listaLTR.addAll (listaLTR);
        listaZapisanychCW.addAll (0,listaCW);





    }

    private void saveCW(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorr = preferences.edit();
        editorr.putString("xxx","xxx");
        editorr.putInt("qqq",2);
        editorr.putInt("zzz",3);
        editorr.putString("rrr","rrr");
        editorr.putString("fff","fff");
        editorr.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("cyckicycki",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(listaZapisanychCW);
        editor.putString("cyckidupa",json2);
        editor.apply();

    }



    public void saveDoListyTreningow(){


        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        final int h = c.get(Calendar.HOUR_OF_DAY);
        final int m = c.get(Calendar.MINUTE);


       // textViewGodzina.setText(h + ":" + m);

      //  textVietData.setText(day + "/" + String.format("%02d", month + 1) + "/" + year+" ");



        listaLTR = new ArrayList<ElementyListyTreningow>();


        int i2;
        for (i2 = 0; i2<listaZapisanychCW.size(); i2++ ){

            nazwaCwiczen= listaZapisanychCW.get(i2).getNazwaCwiczeniaa();



            //nazwaCwiczenDoListyTreningow = "  ";

            nazwaCwiczenDoListyTreningow = nazwaCwiczenDoListyTreningow + nazwaCwiczen+",  ";


        }



        int i1;

        for (i1 = 0; i1<listaZapisanychCW.size(); i1++ ){

            godzinaStartu = listaZapisanychCW.get(i1).getGodzinaa();



           // nazwaCwiczenDoListyTreningow = "  ";

           // godzinaStartuDoListyTreningow = godzinaStartuDoListyTreningow + godzinaStartu;


        }









            String komentarz = koment.getText().toString();


            listaLTR.add(new ElementyListyTreningow(komentarz, nazwaCwiczenDoListyTreningow, godzinaStartu, h + ":" + String.format("%02d",m), day + "/" + String.format("%02d", month + 1) + "/" + year + " "));


            SharedPreferences preferences3 = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editorr = preferences3.edit();
            editorr.putString("xxx", "xxx");
            editorr.putString("qqq", "test");
            editorr.putString("zzz", "test3");
            editorr.putString("rrr", "rrr");
            editorr.putString("fff", "fff");
            editorr.apply();

            SharedPreferences sharedPreferences3 = getSharedPreferences("SPdoListyTreningow", 0);
            SharedPreferences.Editor editor = sharedPreferences3.edit();
            Gson gson3 = new Gson();
            String json3 = gson3.toJson(listaLTR);
            editor.putString("json3dpListyTreningow", json3);
            editor.apply();

            listaLTR.clear();


            // saveCW();


    }


    public void doNastepnegoCwiczenia(View view) {


        saveCW();
        saveDoListyTreningow();

        Intent doNastepnegoCwiczenia = new Intent(getApplicationContext(),ActivityPreIleCwiczy.class);
        startActivity(doNastepnegoCwiczenia);
    }


    public void doListyTreningow(View view) {


        if (koment.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(), " Podaj opis treningu. ", Toast.LENGTH_LONG).show();



        } else {


            Intent doListyTreningow = new Intent(getApplicationContext(), activity_lista_treningow.class);
            startActivity(doListyTreningow);


            saveDoListyTreningow();
            listaZapisanychCW.clear();

            saveCW();
        }
    }
}


