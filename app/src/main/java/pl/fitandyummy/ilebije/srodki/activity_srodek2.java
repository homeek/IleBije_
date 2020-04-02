package pl.fitandyummy.ilebije.srodki;

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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.fitandyummy.ilebije.AdapterTowarow;
import pl.fitandyummy.ilebije.ElementyKalendarza;
import pl.fitandyummy.ilebije.MainActivity;
import pl.fitandyummy.ilebije.PudelkoActivity;
import pl.fitandyummy.ilebije.R;
import pl.fitandyummy.ilebije.broadcast.KoniecNotyfication_reciver;
import pl.fitandyummy.ilebije.broadcast.srodek_dwa_Notyfication_reciver;

public class activity_srodek2 extends AppCompatActivity {


    Bundle bundle;
    public ArrayList<ElementyKalendarza> listaTowarow;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public AlarmManager alarmManager;
    public AlarmManager alarmManager2;
    public PendingIntent pendingIntent;
    public PendingIntent pendingIntent2;
    public Typeface text1;


    private EditText mililitry;
    private EditText nazwaTowca;
    private Button btnInsert;
    private Button btnReset;
    private Button btnChange;
    private EditText liczba_szczalow;
    private EditText edt_godzina;
    private EditText data;
    private TextView nazwaTowaruu;
    private EditText edtokres;



    public String nazwaSrodek2;
    private String iloscMlSrodek2;
    private int iloscStrzalSrodek2;
    public int okresStrzalow2;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timepicker;

    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;

    public SharedPreferences preferences;

    public TextView terminNastepnegoBiciaTV;
    public String terminNastepnegoBiciaSTR;
    public String terminNastepnegoBiciaSTRLoad;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srodek2);

        loadData();

        createRecyclerView();


        mililitry =findViewById(R.id.edittext_ml);
        nazwaTowca =findViewById(R.id.edittext_nazwaTowaru);
        btnInsert=findViewById(R.id.button_insert);
        btnReset=findViewById(R.id.button_reset);
        btnChange=findViewById(R.id.button_change);
        data=findViewById(R.id.edittext_data);
        liczba_szczalow=findViewById(R.id.edittext_liczna);
        edt_godzina=findViewById(R.id.edittext_godzina);
        nazwaTowaruu = findViewById(R.id.nazwyCwiczen);
        edtokres =findViewById(R.id.coIleDni);
        terminNastepnegoBiciaTV = findViewById(R.id.terminNastepnegoBicia);






         text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");


        mililitry.setTypeface(text1);
        nazwaTowca.setTypeface(text1);
        btnInsert.setTypeface(text1);
        btnReset.setTypeface(text1);
        btnChange.setTypeface(text1);
        data.setTypeface(text1);
        liczba_szczalow.setTypeface(text1);
        edt_godzina.setTypeface(text1);
        edtokres.setTypeface(text1);
        terminNastepnegoBiciaTV.setTypeface(text1);





        if(listaTowarow.isEmpty()) {

            terminNastepnegoBiciaSTR = terminNastepnegoBiciaSTRLoad = "  ";

            terminNastepnegoBiciaTV.setText(terminNastepnegoBiciaSTR);


        }else {

            terminNastepnegoBiciaSTR = terminNastepnegoBiciaSTRLoad;

            terminNastepnegoBiciaTV.setText(terminNastepnegoBiciaSTR);

        }














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
                 TextView godzina = (TextView) findViewById(R.id.edittext_godzina);
                 timegodziny = hourOfDay;
                 timeminuty = minute;
               godzina.setText(hourOfDay + ":" + minute);
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
                    nazwaSrodek2 = nazwaTowca.getText().toString();



                    mililitry =findViewById(R.id.edittext_ml);
                    iloscMlSrodek2 =  mililitry.getText().toString();

                    liczba_szczalow =findViewById(R.id.edittext_liczna);
                    iloscStrzalSrodek2 =  Integer.parseInt(liczba_szczalow.getText().toString());




                    edtokres =findViewById(R.id.coIleDni);
                    okresStrzalow2 =  Integer.parseInt(edtokres.getText().toString());




//ustawia date i godzine dla notyfikacji date bierze z datapickera
                    c.set(Calendar.DAY_OF_MONTH,dataDzien);
                    c.set(Calendar.MONTH,dataMiesiac);
                    c.set(Calendar.YEAR,dataRok);
                    c.set(Calendar.HOUR_OF_DAY, timegodziny);
                    c.set(Calendar.MINUTE,timeminuty);

//dodaje okres do liczby dni, uzywane do wyswietlania kolejnej daty w spisie strzałów
                    c.add(Calendar.DAY_OF_MONTH,w*okresStrzalow2);

//formatuje dane na format daty
                    Date dupa  = c.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String date = sdf.format(dupa);

                    Date dupaTime  = c.getTime();
                    SimpleDateFormat sdfTime = new SimpleDateFormat("h:mm a");
                    String dateTime = sdfTime.format(dupaTime);




//tworzy array list
                    listaTowarow.add(new ElementyKalendarza(R.drawable.lotka2, nazwaSrodek2+"", "  "+iloscMlSrodek2+"ml", "strzał nr. " , w+1 , date, dateTime));

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

//tworzy intencje zbudowaną z osobnej class'ie
                Intent intent = new Intent(getApplicationContext(), srodek_dwa_Notyfication_reciver.class);

                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),200, intent,PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres teraaz bez interwału, tylko wysyłła


                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);


             //   alarmManagerRaz = (AlarmManager) getSystemService(ALARM_SERVICE);
              //  alarmManagerRaz.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY*okres,pendingIntentTrzy);


// wiadomosc toast witaj na bombie

                    Toast.makeText(getApplicationContext(), " Witaj na Bombie BYKU !!! ", Toast.LENGTH_LONG).show();

//formatuje dane na format daty

                    Date dupa  = c.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String date = sdf.format(dupa);

                    Date dupaTime  = c.getTime();
                    SimpleDateFormat sdfTime = new SimpleDateFormat("h:mm a");
                    String dateTime = sdfTime.format(dupaTime);

 // wstawia textview

                    terminNastepnegoBiciaSTR = "Następne bicie, "+" "+ date +",  "+ dateTime;

                    terminNastepnegoBiciaTV.setText(terminNastepnegoBiciaSTR);


// no kurwa wiadomo chyba
                saveData();


            }}


        });

        //klim zmieniam

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listaTowarow.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Najpierw przyjeb Byku ", Toast.LENGTH_LONG).show();

                }else {
                    Intent doZmianyIntent = new Intent(getApplicationContext(),activity_srodek2_reload.class);
                    startActivity(doZmianyIntent);
                }
            }
        });


        //klik ZCHODZE
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(alarmManager!=null) {
                    alarmManager.cancel(pendingIntent);

                }else{

                    Intent intent = new Intent(getApplicationContext(), srodek_dwa_Notyfication_reciver.class);

                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),200, intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntent);

                    alarmManager.cancel(pendingIntent);
                }



//pobieranie daty z tej chwili do wyswietlenia notyfikacji kończącej

                c.get(Calendar.YEAR);
                c.get(Calendar.MONTH);
                c.get(Calendar.DAY_OF_MONTH);
                c.get(Calendar.HOUR_OF_DAY);
                c.get(Calendar.MINUTE);


//tworzy intencje zbudowaną z osobnej class'ie
               Intent intent2 = new Intent(getApplicationContext(), KoniecNotyfication_reciver.class);
                pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(),999, intent2,PendingIntent.FLAG_UPDATE_CURRENT);




//jednorazowy alarm notyfikacji konczącej
                alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntent2);

// czyszczenie listy
                listaTowarow.clear();


//tworzy array list - musi bo sie wyjebie
                listaTowarow = new ArrayList<ElementyKalendarza>();

                mAdapter = new AdapterTowarow(listaTowarow);

                mRecyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();

//zeruje napis

                terminNastepnegoBiciaSTR = "  ";

                terminNastepnegoBiciaTV.setText(terminNastepnegoBiciaSTR);


//zapisuje pustą array list
                saveData();


            }
        });

//przycisk powrotu na pasku
        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);


        //klik powrót
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doPreMainIntent = new Intent(getApplicationContext(),PudelkoActivity.class);
                startActivity(doPreMainIntent);
            }
        });


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

    }


    public void createRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerKalendarz);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdapterTowarow(listaTowarow);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void saveData(){


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorr = preferences.edit();

        boolean boolBoot;

        if (listaTowarow==null){
            boolBoot = false;
        } else  {
            boolBoot = true;
        }
        editorr.putBoolean("bollBoot", boolBoot);
        editorr.putString("keyDwa",nazwaSrodek2);
        editorr.putInt("godzinaDwa",timegodziny);
        editorr.putInt("minutaDwa",timeminuty);
        editorr.apply();

        // przekazywanie daty dalej po to aby wstawiał date od pierwszego bicia
        editorr.putInt("dzienBiciaDwa",dataDzien);
        editorr.putInt("miesiacBiciaDwa",dataMiesiac);
        editorr.putInt("rokBiciaDwa",dataRok);

        editorr.putString("mlDwa",iloscMlSrodek2);
        editorr.putInt("okresDwa",okresStrzalow2);
        editorr.putInt("oloscStrzalowDwa",iloscStrzalSrodek2);

        editorr.putString("info2",terminNastepnegoBiciaSTR);

        editorr.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("szered prefDwa",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listaTowarow);
        editor.putString("dana dla jnosaDwa",json);
        editor.apply();
    }

    private void loadData(){

        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        terminNastepnegoBiciaSTRLoad = preferences.getString("info2", " ");

        SharedPreferences sharedPreferences = getSharedPreferences("szered prefDwa",0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dana dla jnosaDwa", null);
        Type type = new TypeToken<ArrayList<ElementyKalendarza>>() {}.getType();
        listaTowarow = gson.fromJson(json,type);

        if (listaTowarow==null){

            listaTowarow = new ArrayList<ElementyKalendarza>();



        }



    }



}


