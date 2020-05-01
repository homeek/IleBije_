package pl.fitandyummy.ilebije.srodki;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import pl.fitandyummy.ilebije.AdapterTowarow;
import pl.fitandyummy.ilebije.AdapterTowarowFirestore;
import pl.fitandyummy.ilebije.ElementyKalendarza;
import pl.fitandyummy.ilebije.ElementyKalendarzaFS;
import pl.fitandyummy.ilebije.MainActivity;
import pl.fitandyummy.ilebije.PudelkoActivity;
import pl.fitandyummy.ilebije.R;
import pl.fitandyummy.ilebije.Register;
import pl.fitandyummy.ilebije.SprawdzaczDanych;
import pl.fitandyummy.ilebije.broadcast.KoniecNotyfication_reciver;
import pl.fitandyummy.ilebije.broadcast.srodek_jeden_Notyfication_reciver;

public class activity_srodek1 extends AppCompatActivity {

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

    public String nazwaSrodek1;
    private String iloscMlSrodek1;
    private int iloscStrzalSrodek1;
    public int okresStrzalow1;

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

    public Boolean sprawdzaczDanychBool = false;

    DatabaseReference databaseReference;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String IDUser = fAuth.getCurrentUser().getUid();
    private CollectionReference iduserref = fStore.collection("dupa");
    private AdapterTowarowFirestore fstoreadapter;

    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srodek1);

        if (listaTowarow == null) {
            listaTowarow = new ArrayList<ElementyKalendarza>();
        }

        // fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {

            //loadFBRDB();
            // loadFSDB();
        } else {
            loadData();

        }

        setUpRecyclerView();
        createRecyclerView();

        mililitry = findViewById(R.id.edittext_ml);
        nazwaTowca = findViewById(R.id.edittext_nazwaTowaru);
        btnInsert = findViewById(R.id.button_insert);
        btnReset = findViewById(R.id.button_reset);
        btnChange = findViewById(R.id.button_change);
        data = findViewById(R.id.edittext_data);
        liczba_szczalow = findViewById(R.id.edittext_liczna);
        edt_godzina = findViewById(R.id.edittext_godzina);
        nazwaTowaruu = findViewById(R.id.nazwyCwiczen);
        edtokres = findViewById(R.id.coIleDni);
        terminNastepnegoBiciaTV = findViewById(R.id.terminNastepnegoBicia);

//wstawia czcionke
        text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");

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

//wstawia pusty string jak lista jest pusta
/*        if (listaTowarow.isEmpty()) {
            terminNastepnegoBiciaSTR = terminNastepnegoBiciaSTRLoad = "  ";
            terminNastepnegoBiciaTV.setText(terminNastepnegoBiciaSTR);

        } else {
            terminNastepnegoBiciaSTR = terminNastepnegoBiciaSTRLoad;
            terminNastepnegoBiciaTV.setText(terminNastepnegoBiciaSTR);
        }
*/
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
            public void onDateSet(DatePicker datedayPicker, int i, int i1, int i2) {
                TextView data = (TextView) findViewById(R.id.edittext_data);
                dataRok = i;
                dataMiesiac = i1;
                dataDzien = i2;
                data.setText(i2 + "/" + String.format("%02d", i1 + 1) + "/" + i + " ");
            }
        }, year, month, day);
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
        }, h, m, true);
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
                /*
                if (!sprawdzaczDanychBool){
                    SprawdzaczDanych();
                }
                else if ( sprawdzaczDanychBool == true){
                   */

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
                } else {

                    int position = 0;
                    int liczba = Integer.parseInt(liczba_szczalow.getText().toString());

//petla wstawiająca kazdy kolejny strzał
                    for (int w = 0; w < liczba; w++) {
                        nazwaTowca = findViewById(R.id.edittext_nazwaTowaru);
                        nazwaSrodek1 = nazwaTowca.getText().toString();

                        mililitry = findViewById(R.id.edittext_ml);
                        iloscMlSrodek1 = mililitry.getText().toString();

                        liczba_szczalow = findViewById(R.id.edittext_liczna);
                        iloscStrzalSrodek1 = Integer.parseInt(liczba_szczalow.getText().toString());

                        edtokres = findViewById(R.id.coIleDni);
                        okresStrzalow1 = Integer.parseInt(edtokres.getText().toString());

//ustawia date i godzine dla notyfikacji date bierze z datapickera
                        c.set(Calendar.DAY_OF_MONTH, dataDzien);
                        c.set(Calendar.MONTH, dataMiesiac);
                        c.set(Calendar.YEAR, dataRok);
                        c.set(Calendar.HOUR_OF_DAY, timegodziny);
                        c.set(Calendar.MINUTE, timeminuty);

//dodaje okres do liczby dni, uzywane do wyswietlania kolejnej daty w spisie strzałów
                        c.add(Calendar.DAY_OF_MONTH, w * okresStrzalow1);

//formatuje dane na format daty
                        Date dupa = c.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        String date = sdf.format(dupa);
                        Date dupaTime = c.getTime();
                        SimpleDateFormat sdfTime = new SimpleDateFormat("h:mm a");
                        String dateTime = sdfTime.format(dupaTime);

//tworzy array list
                        listaTowarow.add(new ElementyKalendarza(R.drawable.fiolka, nazwaSrodek1 + "", "  " + iloscMlSrodek1 + "ml", "strzał nr. ", w + 1, date, dateTime));

//odswierza i pokazuje array list
                        mAdapter.notifyItemChanged(position);
                        mAdapter.notifyDataSetChanged();
                    }

//USTWIANIE ALARMU NOTYFIKACJI

//pobiera date i czas z datapickera
                    c.set(Calendar.DAY_OF_MONTH, dataDzien);
                    c.set(Calendar.MONTH, dataMiesiac);
                    c.set(Calendar.YEAR, dataRok);
                    c.set(Calendar.HOUR_OF_DAY, timegodziny);
                    c.set(Calendar.MINUTE, timeminuty);

//tworzy intencje zbudowaną w osobnej class'ie
                    Intent intent = new Intent(getApplicationContext(), srodek_jeden_Notyfication_reciver.class);
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres teraaz bez interwału, tylko wysyłła
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

// wiadomosc toast witaj na bombie
                    Toast.makeText(getApplicationContext(), " Witaj na Bombie BYKU !!! ", Toast.LENGTH_LONG).show();

//formatuje dane na format daty
                    Date dupa = c.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String date = sdf.format(dupa);
                    Date dupaTime = c.getTime();
                    SimpleDateFormat sdfTime = new SimpleDateFormat("h:mm a");
                    String dateTime = sdfTime.format(dupaTime);

 // wstawia textview
                    terminNastepnegoBiciaSTR = "Następne bicie, " + " " + date + ",  " + dateTime;
                    terminNastepnegoBiciaTV.setText(terminNastepnegoBiciaSTR);

// zapis array list
                    saveData();
                    //saveFBRDB();
                    saveFSDB();
                }
            }
        });

//KLIK ZMIENIAM
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listaTowarow.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Najpierw przyjeb Byku ", Toast.LENGTH_LONG).show();

                } else {
                    Intent doZmianyIntent = new Intent(getApplicationContext(), activity_srodek1_reload.class);
                    startActivity(doZmianyIntent);
                }
            }
        });

//klik ZCHODZE

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaTowarow.isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Najpierw przyjeb Byku ", Toast.LENGTH_LONG).show();
                } else if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), srodek_jeden_Notyfication_reciver.class);
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                    alarmManager.cancel(pendingIntent);

//pobieranie daty z tej chwili do wyswietlenia notyfikacji kończącej
                    c.get(Calendar.YEAR);
                    c.get(Calendar.MONTH);
                    c.get(Calendar.DAY_OF_MONTH);
                    c.get(Calendar.HOUR_OF_DAY);
                    c.get(Calendar.MINUTE);

//tworzy intencje zbudowaną z osobnej class'ie
                    Intent intent2 = new Intent(getApplicationContext(), KoniecNotyfication_reciver.class);
                    pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 200, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

//jednorazowy alarm notyfikacji konczącej
                    alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager2.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent2);

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
                    // saveData();
                }
            }
        });


//przycisk powrotu na pasku
        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doPreMainIntent = new Intent(getApplicationContext(), PudelkoActivity.class);
                startActivity(doPreMainIntent);
            }
        });

//przycisk powrotu na pasku
        ImageView ilebije = (ImageView) findViewById(R.id.toolchangeBtn);
        ilebije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(doMainIntent);
            }
        });
    }

    private void setUpRecyclerView() {
        Query query = iduserref.orderBy("ktoryStrzl", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ElementyKalendarzaFS> options = new FirestoreRecyclerOptions.Builder<ElementyKalendarzaFS>()
                .setQuery(query, ElementyKalendarzaFS.class)
                .build();

        fstoreadapter = new AdapterTowarowFirestore(options);

        RecyclerView fsrecyclerView = findViewById(R.id.recyclerKalendarz);
        fsrecyclerView.setHasFixedSize(true);
        fsrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fsrecyclerView.setAdapter(fstoreadapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fstoreadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fstoreadapter.stopListening();
    }


    public void createRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerKalendarz);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdapterTowarow(listaTowarow);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void saveData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorr = preferences.edit();

        boolean boolBoot;

        if (listaTowarow == null) {
            boolBoot = false;
        } else {
            boolBoot = true;
        }
        editorr.putBoolean("bollBoot", boolBoot);
        editorr.putString("key", nazwaSrodek1);
        editorr.putInt("godzinaZakonczenia", timegodziny);
        editorr.putInt("minuta", timeminuty);

// przekazywanie daty dalej po to aby wstawiał date od pierwszego bicia
        editorr.putInt("dzienBicia", dataDzien);
        editorr.putInt("miesiacBicia", dataMiesiac);
        editorr.putInt("rokBicia", dataRok);

        editorr.putInt("pierwszeBicie", dataDzien);

        editorr.putString("ml", iloscMlSrodek1);
        editorr.putInt("okres", okresStrzalow1);
        editorr.putInt("oloscStrzalow", iloscStrzalSrodek1);

        editorr.putString("info1", terminNastepnegoBiciaSTR);

        editorr.apply();

// tu zapisuje arraylist w JSON
        SharedPreferences sharedPreferences = getSharedPreferences("szered pref", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listaTowarow);
        editor.putString("dana dla jnosa", json);
        editor.apply();
    }

    private void loadData() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        terminNastepnegoBiciaSTRLoad = preferences.getString("info1", " ");

        SharedPreferences sharedPreferences = getSharedPreferences("szered pref", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dana dla jnosa", null);
        Type type = new TypeToken<ArrayList<ElementyKalendarza>>() {
        }.getType();
        listaTowarow = gson.fromJson(json, type);

        if (listaTowarow == null) {
            listaTowarow = new ArrayList<ElementyKalendarza>();
        }
    }

    public void saveFBRDB() {

        // wysylanie listy do FBDB


        IDUser = fAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(IDUser);
        databaseReference.child("Lista pierwszego środka").setValue(listaTowarow);

        Date dupa = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(dupa);
        Date dupaTime = c.getTime();
        SimpleDateFormat sdfTime = new SimpleDateFormat("h:mm a");
        String dateTime = sdfTime.format(dupaTime);

        ElementyKalendarza ek = new ElementyKalendarza(R.drawable.fiolka, nazwaSrodek1, iloscMlSrodek1, "strzał", 1, date, dateTime);

        databaseReference.child("start data").setValue(ek);
    }

    public void saveFSDB() {
        //  iduserref = FirebaseFirestore.getInstance().collection("dupa");
        iduserref.add(new ElementyKalendarzaFS(nazwaSrodek1, "gyh", "stszał", 1, "sw", "re"));
        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
        // finish();
    }

/*
        fStore = FirebaseFirestore.getInstance();
      //  String qq = "qq";
      //  String zz = "zz";
        IDUser = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection(IDUser).document("srodek1")
                .collection("strzal nr").document();
       // Map<String,Object> chujek = new HashMap<>();
       // chujek.put ("nazwa",qq);
      //  chujek.put ("ilosc", zz);

        Date dupa = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(dupa);
        Date dupaTime = c.getTime();
        SimpleDateFormat sdfTime = new SimpleDateFormat("h:mm a");
        String dateTime = sdfTime.format(dupaTime);

        ElementyKalendarza ek = new ElementyKalendarza(R.drawable.fiolka, nazwaSrodek1, iloscMlSrodek1, "strzał", 1, date, dateTime);

        documentReference.set(ek).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(activity_srodek1.this, "pynakes ", Toast.LENGTH_LONG).show ();
            }
        });
        */
    //  documentReference.set(chujek).addOnSuccessListener(new OnSuccessListener<Void>() {
    //      @Override
    //      public void onSuccess(Void aVoid) {

    //     }
    //  });


    public void loadFSDB() {
        fStore = FirebaseFirestore.getInstance();
        IDUser = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("duopa").document(IDUser);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                // mililitry.setText(documentSnapshot.getString("nazwa"));

                int position = 0;
                ElementyKalendarza ek = documentSnapshot.toObject(ElementyKalendarza.class);

                nazwaTowca.setText(ek.getDataa());
                listaTowarow.add(ek);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyItemChanged(position);
                mAdapter.notifyDataSetChanged();

                if (listaTowarow == null) {
                    listaTowarow = new ArrayList<ElementyKalendarza>();
                }
            }
        });

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ElementyKalendarza ek = documentSnapshot.toObject(ElementyKalendarza.class);
                /*
                String nazwaTowara = ek.getNazwaTowara();
                String iloscTowara = ek.getIloscTowara();
                terminNastepnegoBiciaTV.setText(nazwaTowara + iloscTowara);
*/
            }
        });
    }

    public void loadFBRDB() {
        IDUser = fAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(IDUser);

        ////POBIERANIE LISTY I WKLEJANIE DO RECYCLERA  "lista pierszego srodka"
        databaseReference.child("Lista pierwszego środka").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int position = 0;
                ElementyKalendarza elementyKalendarza = dataSnapshot.getValue(ElementyKalendarza.class);

                //   String CHUJ = elementyKalendarza.getNazwaTowara();
                //  nazwaTowca.setText(CHUJ);

                nazwaTowca.setText(elementyKalendarza.getDataa());

                listaTowarow.add(elementyKalendarza);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyItemChanged(position);
                mAdapter.notifyDataSetChanged();
                if (listaTowarow == null) {
                    listaTowarow = new ArrayList<ElementyKalendarza>();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

////POBIERANIE DANYCH I WKLEJANIE DO EDITTEXT   TYLKO RELOAD"
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
/*
                ElementyKalendarza elementyKalendarza = dataSnapshot.getValue(ElementyKalendarza.class);
                String CHUJ = elementyKalendarza.getNazwaTowara();
                nazwaTowca.setText(CHUJ);
                Toast.makeText(getApplicationContext(), " to na co czekasz ", Toast.LENGTH_LONG).show();
                */
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
/*
                ElementyKalendarza elementyKalendarza = dataSnapshot.getValue(ElementyKalendarza.class);
                String CHUJ = elementyKalendarza.getNazwaTowara();
                nazwaTowca.setText(CHUJ);
                Toast.makeText(getApplicationContext(), " to na co czekasz 222 ", Toast.LENGTH_LONG).show();
                */
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //POBIERANIE LISTY I WKLEJANIE DO RECYCLERA metodą    "addValue"
        IDUser = fAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(IDUser);
        databaseReference.child("start data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
/*

                  //  ElementyKalendarza ek;
                  //  ek = ds.getValue(ElementyKalendarza.class);
                    String CHUJ = ek.getNazwaTowara();
                    nazwaTowca.setText(CHUJ);
                    Toast.makeText(getApplicationContext(), " to na co czekasz ", Toast.LENGTH_LONG).show();
*/
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        /*
                    //POBIERANIE LISTY I WKLEJANIE DO RECYCLERA metodą    "addValue"
                    IDUser = fAuth.getCurrentUser().getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child(IDUser);
                    databaseReference.child("Lista pierwszego środka").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren())
                            {
                                ElementyKalendarza ek;
                                        ek = ds .getValue(ElementyKalendarza.class);
                                listaTowarow.add(ek);
                                int position =0;
                                mRecyclerView.setAdapter(mAdapter);
                                mAdapter.notifyItemChanged(position);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
*/
    }

    private void SprawdzaczDanych() {
        if (nazwaTowca.getText().toString().equals("")) {
            sprawdzaczDanychBool = false;
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else if (data.getText().toString().equals("")) {
            sprawdzaczDanychBool = false;
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else if (liczba_szczalow.getText().toString().equals("")) {
            sprawdzaczDanychBool = false;
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else if (edtokres.getText().toString().equals("")) {
            sprawdzaczDanychBool = false;
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else if (edt_godzina.getText().toString().equals("")) {
            sprawdzaczDanychBool = false;
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else if (mililitry.getText().toString().equals("")) {
            sprawdzaczDanychBool = false;
            Toast.makeText(getApplicationContext(), "Uzupełnij dane Byku ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "IDZIE !!!!! ", Toast.LENGTH_LONG).show();
            sprawdzaczDanychBool = true;
        }
    }
}