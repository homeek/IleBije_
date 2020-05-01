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

    public ArrayList<ElementyListyTreningow> listaLTR, listaZapisanychLTR;

    private RecyclerView mRecyclerViewLT;
    private RecyclerView.Adapter mAdapterLT;
    private RecyclerView.LayoutManager mLayoutManagerLT;
    public Typeface text1;
    private TextView telebim;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timepicker;

    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;

    public AlarmManager alarmManager2;

    public PendingIntent pendingIntent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_treningow);

        loadData();

        createRecyclerView();

        text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");

        telebim = findViewById(R.id.telebim);
        telebim.setTypeface(text1);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                listaZapisanychLTR.remove(viewHolder.getAdapterPosition());
                mAdapterLT.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(mRecyclerViewLT);
    }

    public void createRecyclerView() {
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

        if (listaLTR == null && listaZapisanychLTR == null) {
            listaLTR = new ArrayList<ElementyListyTreningow>();
            listaZapisanychLTR = new ArrayList<ElementyListyTreningow>();

        } else if (listaZapisanychLTR == null) {
            listaZapisanychLTR = new ArrayList<ElementyListyTreningow>();
            listaZapisanychLTR.addAll(0, listaLTR);

        } else {
            listaZapisanychLTR.addAll(0, listaLTR);
        }
    }

    private void saveLTR() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorr = preferences.edit();
        editorr.putString("xxx", "xxx");
        editorr.putString("qqq", "test");
        editorr.putString("zzz", "test3");
        editorr.putString("rrr", "rrr");
        editorr.putString("fff", "fff");
        editorr.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("zalisaneLTR", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson4 = new Gson();
        String json4 = gson4.toJson(listaZapisanychLTR);
        editor.putString("zapisaneLTR", json4);
        editor.apply();
    }

    private void saveOtrzymanychLtr() {
        listaLTR = new ArrayList<ElementyListyTreningow>();

        SharedPreferences sharedPreferences3 = getSharedPreferences("SPdoListyTreningow", 0);
        SharedPreferences.Editor editor = sharedPreferences3.edit();
        Gson gson3 = new Gson();
        String json3 = gson3.toJson(listaLTR);
        editor.putString("json3dpListyTreningow", json3);
        editor.apply();
    }

    public void doNastepnegoCwiczenia(View view) {
        saveLTR();
        if (listaLTR == null) {
            listaLTR = new ArrayList<ElementyListyTreningow>();

        } else {
            listaLTR.clear();
            saveOtrzymanychLtr();
        }
        Intent doNastepnegoCwiczenia = new Intent(getApplicationContext(), PreMainActivity.class);
        startActivity(doNastepnegoCwiczenia);
    }

    public void doListyTreningow(View view) {
        saveLTR();

        if (listaLTR == null) {
            listaLTR = new ArrayList<ElementyListyTreningow>();
        } else {

            listaLTR.clear();
            saveOtrzymanychLtr();
        }

        Intent doMainIntent = new Intent(getApplicationContext(), ActivityUstawiaczTreningow.class);
        startActivity(doMainIntent);
    }
}