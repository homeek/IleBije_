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
import java.util.Timer;
import java.util.TimerTask;

import pl.fitandyummy.ilebije.AdapterTowarow;
import pl.fitandyummy.ilebije.ElementyKalendarza;
import pl.fitandyummy.ilebije.MainActivity;
import pl.fitandyummy.ilebije.PudelkoActivity;
import pl.fitandyummy.ilebije.R;
import pl.fitandyummy.ilebije.broadcast.srodek_trzy_Notyfication_reciver;

public class activity_srodek3_reload extends AppCompatActivity {


    Bundle bundle;
    Timer timer;

    public ArrayList<ElementyKalendarza> listaTowarow;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public AlarmManager alarmManager;
    public AlarmManager alarmManager2;
    public PendingIntent pendingIntent3;

    public Typeface text1;
    //public PendingIntent pendingIntent2;

    private EditText mililitry;
    private EditText nazwaTowca;
    private Button btnInsert;
    private Button btnReset;
    private EditText liczba_szczalow;
    private EditText edt_godzina;
    private EditText data;
    private TextView nazwaTowaruu;
    private EditText edtokres;



    public String przekazywanatrzy;
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



   public String value;
    public String date;

   public Integer godziny ;
   public Integer minuty ;
   public Integer dni;
   public Integer miesiace;
   public Integer lata;



   public String loadMltrzy;
   public Integer loadOkrestrzy;
    public Integer loadIloscStrzalowtrzy;

    public SharedPreferences preferences;

    public TextView terminNastepnegoBiciaTV;
    public String terminNastepnegoBiciaSTR;
    public String terminNastepnegoBiciaSTRLoad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srodek3_reload);

        loadData();

        createRecyclerView();


        mililitry =findViewById(R.id.edittext_ml);
        nazwaTowca =findViewById(R.id.edittext_nazwaTowaru);
        btnInsert=findViewById(R.id.button_insert);
        btnReset=findViewById(R.id.button_reset);
        data=findViewById(R.id.edittext_data);
        liczba_szczalow=findViewById(R.id.edittext_liczna);
        edt_godzina=findViewById(R.id.edittext_godzina);
        nazwaTowaruu = findViewById(R.id.nazwyCwiczen);
        edtokres =findViewById(R.id.coIleDni);
        terminNastepnegoBiciaTV = findViewById(R.id.terminNastepnegoBicia);



//dodanie czcionki


         text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");


        mililitry.setTypeface(text1);
        nazwaTowca.setTypeface(text1);
        btnInsert.setTypeface(text1);
        btnReset.setTypeface(text1);
        data.setTypeface(text1);
        liczba_szczalow.setTypeface(text1);
        edt_godzina.setTypeface(text1);
        edtokres.setTypeface(text1);
        terminNastepnegoBiciaTV.setTypeface(text1);







        terminNastepnegoBiciaSTR = terminNastepnegoBiciaSTRLoad;

        terminNastepnegoBiciaTV.setText(terminNastepnegoBiciaSTR);






        przekazywana_iloscszczał= loadIloscStrzalowtrzy;
        przekazywanatrzy =value;
        okres= loadOkrestrzy;
        timegodziny=godziny;
        timeminuty=minuty;
        przekazywana_iloscml= loadMltrzy;
        // porządkowanie zmiennych - bez tego przy potwierdzeniu bicia wypierdaa sie data
        dataDzien = dni;
        dataMiesiac = miesiace;
        dataRok = lata;


// wstawia dane
        mililitry.setText(loadMltrzy);
        nazwaTowca.setText(przekazywanatrzy);
        liczba_szczalow.setText(String.valueOf(loadIloscStrzalowtrzy));
        edtokres.setText(String.valueOf(loadOkrestrzy));



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


//klik przybijam
        //
        //
        //
        //
        //

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){


                final Calendar c = Calendar.getInstance();

                final int day = c.get(Calendar.DAY_OF_MONTH);

                final int year = c.get(Calendar.YEAR);
                final int month = c.get(Calendar.MONTH);


                int position = 0;
                int liczba = przekazywana_iloscszczał;





                // zerowanie listy towarów

                listaTowarow.clear();



//petla wstawiająca kazdy kolejny strzał

                for ( int w = 0; w < liczba; w++ ) {






                 //ustawia date i godzine dla notyfikacji date bierze z sheredpref



                    c.set(Calendar.DAY_OF_MONTH,dni);
                    c.set(Calendar.MONTH,miesiace);
                    c.set(Calendar.YEAR,lata);
                    c.set(Calendar.HOUR_OF_DAY, timegodziny);
                    c.set(Calendar.MINUTE,timeminuty);

                    //dodaje okres do liczby dni, uzywane do wyswietlania kolejnej daty w spisie strzałów
                    c.add(Calendar.DAY_OF_MONTH,w* loadOkrestrzy);

//formatuje dane na format daty
                    Date dupa  = c.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String date = sdf.format(dupa);

                    Date dupaTime  = c.getTime();
                    SimpleDateFormat sdfTime = new SimpleDateFormat("h:mm a");
                    String dateTime = sdfTime.format(dupaTime);




                //tworzy array list
                    listaTowarow.add(new ElementyKalendarza(R.drawable.omcia, przekazywanatrzy +"", "  "+przekazywana_iloscml+"ml", "strzał nr. " , w+1 , date, dateTime));

                 //odswierza i pokazuje array list
                    mAdapter.notifyItemChanged(position);
                    mAdapter.notifyDataSetChanged();




                }



                //ustawianie alarmu notyfikacji

                //pobiera date i czas z datapickera


  /*                c.set(Calendar.DAY_OF_MONTH,day+ loadOkresszesc);
                    c.set(Calendar.DAY_OF_MONTH,day);
                    c.set(Calendar.MONTH,month);
                    c.set(Calendar.YEAR,year);
                    c.set(Calendar.HOUR_OF_DAY, timegodziny);
                    c.set(Calendar.MINUTE,timeminuty);

 */

                c.set(Calendar.DAY_OF_MONTH,dataDzien+loadOkrestrzy);
                c.set(Calendar.MONTH,dataMiesiac);
                c.set(Calendar.YEAR,dataRok);
                c.set(Calendar.HOUR_OF_DAY, timegodziny);
                c.set(Calendar.MINUTE,timeminuty);

                    //tworzy intencje zbudowaną w osobnej class'ie

                    Intent intent = new Intent(getApplicationContext(), srodek_trzy_Notyfication_reciver.class);
                    pendingIntent3 = PendingIntent.getBroadcast(getApplicationContext(),300, intent,PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres  ( w nowej wersji juz bez interwału, tylko alarm )

                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent3);

/*
                alarmManagerRaz = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManagerRaz.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY*okres/24/4/3,pendingIntentTrzy);
*/

//formatuje dane na format daty do toasta
                Date dupa2  = c.getTime();
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                String date2 = sdf2.format(dupa2);

                Date dupa3  = c.getTime();
                SimpleDateFormat sdf3 = new SimpleDateFormat("h:mm a");
                String date3 = sdf3.format(dupa3);



// wiadomosc toast

                Toast.makeText(getApplicationContext(), " Potwierdzono zakłuty poślad !!!   Następne bicie  " +date2+" "+date3, Toast.LENGTH_LONG).show();


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

// porządkowanie zmiennych - bez tego przy potwierdzeniu bicia wypierdaa sie data
                dataDzien = dni+loadOkrestrzy;
                miesiace = dataMiesiac = miesiace;
                dataRok = lata;


// zapis array list

                saveData();

//zamyka apke z opoznieniem 3s

               timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();

                    }
                },3000);


            }


        });


        //klik przesuwam
        //
        //
        //
        //
        //
        btnReset.setOnClickListener(new View.OnClickListener() {
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


                    // zerowanie listy towarów

                    listaTowarow.clear();

                    int position = 0;

                    int liczba = Integer.parseInt(liczba_szczalow.getText().toString());




//petla wstawiająca kazdy kolejny strzał

                    for ( int w = 0; w < liczba; w++ ) {

                        nazwaTowca =findViewById(R.id.edittext_nazwaTowaru);
                        przekazywanatrzy = nazwaTowca.getText().toString();



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


//formatuje dane na format daty
                        Date dupa  = c.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        String date = sdf.format(dupa);

                        Date dupaTime  = c.getTime();
                        SimpleDateFormat sdfTime = new SimpleDateFormat("h:mm a");
                        String dateTime = sdfTime.format(dupaTime);




                        //tworzy array list
                        listaTowarow.add(new ElementyKalendarza(R.drawable.omcia, przekazywanatrzy +"", "  "+przekazywana_iloscml+"ml", "strzał nr. " , w+1 , date, dateTime));

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

                    Intent intent = new Intent(getApplicationContext(), srodek_trzy_Notyfication_reciver.class);
                    pendingIntent3 = PendingIntent.getBroadcast(getApplicationContext(),300, intent,PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres (teraaz bez interwału, tylko wysyłła)



                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent3);


/*
                alarmManagerRaz = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManagerRaz.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY*okres,pendingIntentTrzy);
*/
//formatuje dane na format daty do toasta
                    Date dupa2  = c.getTime();
                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                    String date2 = sdf2.format(dupa2);

                    Date dupa3  = c.getTime();
                    SimpleDateFormat sdf3 = new SimpleDateFormat("h:mm a");
                    String date3 = sdf3.format(dupa3);



// wiadomosc toast

                    Toast.makeText(getApplicationContext(), " Potwierdzono zakłuty poślad !!!   Następne bicie  " +date2+" "+date3, Toast.LENGTH_LONG).show();


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



// zapis array list
                    saveData();

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            finish();

                        }
                    },3000);


                }}


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


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorr = preferences.edit();

        boolean boolBoot;

        if (listaTowarow==null){
            boolBoot = false;
        } else  {
            boolBoot = true;
        }

        editorr.putString("keytrzy", przekazywanatrzy);
        editorr.putInt("godzinatrzy",timegodziny);
        editorr.putInt("minutatrzy",timeminuty);

        // przekazywanie daty dalej po to aby wstawiał date od pierwszego bicia
        editorr.putInt("dzienBiciatrzy",dataDzien);
        editorr.putInt("miesiacBiciatrzy",dataMiesiac);
        editorr.putInt("rokBiciatrzy",dataRok);

        editorr.putString("mltrzy",przekazywana_iloscml);
        editorr.putInt("okrestrzy",okres);
        editorr.putInt("oloscStrzalowtrzy",przekazywana_iloscszczał);
        editorr.putBoolean("bollBoot", boolBoot);

        editorr.putString("info3",terminNastepnegoBiciaSTR);

        editorr.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("szered preftrzy",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listaTowarow);
        editor.putString("dana dla jnosatrzy",json);
        editor.apply();
    }

    private void loadData(){


//editorr.putInt("dzienBicia",dataDzien);
//        editorr.putInt("miesiacBicia",dataMiesiac);
//        editorr.putInt("rokBicia",dataRok);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        value = preferences.getString("keytrzy", "defaultValue");
        godziny = preferences.getInt("godzinatrzy",99);
        minuty = preferences.getInt("minutatrzy",99);

// przekazywanie daty  po to aby wstawiał date od pierwszego bicia
        dni = preferences.getInt("dzienBiciatrzy",99);
        miesiace = preferences.getInt("miesiacBiciatrzy",99);
        lata = preferences.getInt("rokBiciatrzy",99);


        loadMltrzy = preferences.getString("mltrzy", "defaultValue");
        loadOkrestrzy = preferences.getInt("okrestrzy",99);
        loadIloscStrzalowtrzy = preferences.getInt("oloscStrzalowtrzy",99);

        terminNastepnegoBiciaSTRLoad = preferences.getString("info3", "  ");




        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences sharedPreferences = getSharedPreferences("szered preftrzy",0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dana dla jnosatrzy", null);


        Type type = new TypeToken<ArrayList<ElementyKalendarza>>() {}.getType();
        listaTowarow = gson.fromJson(json,type);

        if (listaTowarow==null){

            listaTowarow = new ArrayList<ElementyKalendarza>();



        }



    }




}






