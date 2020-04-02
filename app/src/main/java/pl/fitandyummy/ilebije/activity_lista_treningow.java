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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class activity_lista_treningow extends AppCompatActivity {


    Bundle bundle;
    public ArrayList<ElementyListyTreningow> listaCW, listaLTR, obieListy, listaZapisanychLTR;

    private RecyclerView mRecyclerViewLT;
    private RecyclerView.Adapter mAdapterLT;
    private RecyclerView.LayoutManager mLayoutManagerLT;
    //public AlarmManager alarmManagerRaz;
  //  public AlarmManager alarmManager2;
   // public PendingIntent pendingIntentTrzy;
   // public PendingIntent pendingIntent2;
    public Typeface text1;



    private TextView nazwaCwiczenia, czasRest, czasWork, data, godzina, telebim;


    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timepicker;

    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;


    public AlarmManager alarmManager2;

    public PendingIntent pendingIntent2;

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
        setContentView(R.layout.activity_lista_treningow);

        loadData();

        createRecyclerView();


        text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");

        telebim = findViewById(R.id.telebim);
        telebim.setTypeface(text1);



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
                listaZapisanychLTR.remove(viewHolder.getAdapterPosition());
                mAdapterLT.notifyDataSetChanged();

            }
        });

        helper.attachToRecyclerView(mRecyclerViewLT);


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
                    mAdapterLT.notifyItemChanged(position);
                    mAdapterLT.notifyDataSetChanged();

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

                mAdapterLT = new AdapterTowarow(listaLTR);

                mRecyclerViewLT.setAdapter(mAdapterLT);

                mAdapterLT.notifyDataSetChanged();

//zapisuje pustą array list
                saveData();


            }
        });

*/
/*
//przycisk powrotu na pasku
        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);


        //klik powrót
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // saveCW();

                Intent doPreMainIntent = new Intent(getApplicationContext(),ActivityIleCwiczy.class);
                startActivity(doPreMainIntent);
            }
        });



//przycisk powrotu na pasku
        ImageView ilebije = (ImageView) findViewById(R.id.toolchangeBtn);


        //klik do ilebije
        ilebije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //saveCW();

                Intent doMainIntent = new Intent(getApplicationContext(),ActivityIleCwiczy.class);
                startActivity(doMainIntent);
            }
        });

*/

    }









    public void createRecyclerView(){


        mRecyclerViewLT = findViewById(R.id.listaTreningowRecyclerView);
        mRecyclerViewLT.setHasFixedSize(true);
        mLayoutManagerLT = new LinearLayoutManager(this);
        mAdapterLT = new AdapterListyTreningow(listaZapisanychLTR);
        mRecyclerViewLT.setLayoutManager(mLayoutManagerLT);
        mRecyclerViewLT.setAdapter(mAdapterLT);
    }



    private void loadData() {



        SharedPreferences sharedPreferences3 = getSharedPreferences("SPdoListyTreningow", 0);
        Gson gson3 = new Gson();
        String json3 = sharedPreferences3.getString("json3dpListyTreningow", null);
        Type type3 = new TypeToken<ArrayList<ElementyListyTreningow>>() {
        }.getType();
        listaLTR = gson3.fromJson(json3, type3);


        SharedPreferences sharedPreferences4 = getSharedPreferences("zalisaneLTR", 0);
        Gson gson4 = new Gson();
        String json4 = sharedPreferences4.getString("zapisaneLTR", null);
        Type type4 = new TypeToken<ArrayList<ElementyListyTreningow>>() {
        }.getType();
        listaZapisanychLTR = gson4.fromJson(json4, type4);






        if (listaLTR ==null&&listaZapisanychLTR ==null) {


            listaLTR = new ArrayList<ElementyListyTreningow>();
            listaZapisanychLTR = new ArrayList<ElementyListyTreningow>();

           // Toast.makeText(getApplicationContext(), " chujasek ", Toast.LENGTH_LONG).show();




        } else if (listaZapisanychLTR ==null){

            listaZapisanychLTR = new ArrayList<ElementyListyTreningow>();


          //  Toast.makeText(getApplicationContext(), " cipuszka ", Toast.LENGTH_LONG).show();


            listaZapisanychLTR.addAll(0, listaLTR);






           /* SharedPreferences sharedPreferences3 = getSharedPreferences("SPdoListyTreningow", 0);
            Gson gson3 = new Gson();
            String json3 = sharedPreferences3.getString("json3dpListyTreningow", null);
            Type type3 = new TypeToken<ArrayList<ElementyListyTreningow>>() {
            }.getType();
            listaLTR = gson3.fromJson(json3, type3);


            //  listaLTR = new ArrayList<ElementyListyTreningow>();


            SharedPreferences sharedPreferences4 = getSharedPreferences("zalisaneLTR", 0);
            Gson gson4 = new Gson();
            String json4 = sharedPreferences4.getString("zapisaneLTR", null);
            Type type4 = new TypeToken<ArrayList<ElementyListyTreningow>>() {
            }.getType();
            listaZapisanychLTR = gson4.fromJson(json4, type4);

            listaZapisanychLTR.addAll(0, listaLTR);*/


        } else {



            listaZapisanychLTR.addAll(0, listaLTR);


        }




    }



    private void saveLTR(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorr = preferences.edit();
        editorr.putString("xxx","xxx");
        editorr.putString("qqq","test");
        editorr.putString("zzz","test3");
        editorr.putString("rrr","rrr");
        editorr.putString("fff","fff");
        editorr.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("zalisaneLTR",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson4 = new Gson();
        String json4 = gson4.toJson(listaZapisanychLTR);
        editor.putString("zapisaneLTR",json4);
        editor.apply();



    }


    private void saveOtrzymanychLtr (){


        listaLTR = new ArrayList<ElementyListyTreningow>();


        SharedPreferences sharedPreferences3 = getSharedPreferences("SPdoListyTreningow",0);
        SharedPreferences.Editor editor = sharedPreferences3.edit();
        Gson gson3 = new Gson();
        String json3 = gson3.toJson(listaLTR);
        editor.putString("json3dpListyTreningow",json3);
        editor.apply();

    }





  /*  private void loadData(){





        SharedPreferences sharedPreferences2 = getSharedPreferences("cyckicycki",0);
        Gson gson2 = new Gson();
        String json2 = sharedPreferences2.getString("cyckidupa", null);
        Type type2 = new TypeToken<ArrayList<ElementyListyCwiczen>>() {}.getType();
        listaLTR = gson2.fromJson(json2,type2);

        SharedPreferences sharedPreferences = getSharedPreferences("dupadupa",0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dupacycki", null);
        Type type = new TypeToken<ArrayList<ElementyListyCwiczen>>() {}.getType();
        listaCW = gson.fromJson(json,type);




        if (listaLTR ==null){

            listaLTR = new ArrayList<ElementyListyCwiczen>();



        }

        //listaLTR = new ArrayList<ElementyListyCwiczen>();

       // listaLTR.addAll (listaLTR);
        listaLTR.addAll (0,listaCW);





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
        String json2 = gson2.toJson(listaLTR);
        editor.putString("cyckidupa",json2);
        editor.apply();

    }
*/

    public void doNastepnegoCwiczenia(View view) {

        saveLTR();



        if (listaLTR ==null){

            listaLTR = new ArrayList<ElementyListyTreningow>();



        } else {


            listaLTR.clear();

            saveOtrzymanychLtr();


        }

        Intent doNastepnegoCwiczenia = new Intent(getApplicationContext(),PreMainActivity.class);
        startActivity(doNastepnegoCwiczenia);
    }


    public void doListyTreningow(View view) {


        saveLTR();



        if (listaLTR ==null){

            listaLTR = new ArrayList<ElementyListyTreningow>();



        } else {


            listaLTR.clear();
            saveOtrzymanychLtr();

        }



        Intent doMainIntent = new Intent(getApplicationContext(),ActivityUstawiaczTreningow.class);
        startActivity(doMainIntent);
    }
}


