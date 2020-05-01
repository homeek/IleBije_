package pl.fitandyummy.ilebije.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class srodek_szesc_Notyfication_reciver_BOOT_COMPLETED extends BroadcastReceiver {

    public AlarmManager alarmManagerSzesc;
    public PendingIntent pendingIntentSzesc;
    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;
    public Integer loadOkresszesc;
    public Timer timer;
    public SharedPreferences preferences;
    public String terminNastepnegoBiciaSTR;

    Boolean boolBoot = false;

    @Override
    public void onReceive(final Context context6, Intent intent6) {

        context6.getApplicationContext();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context6);

        timegodziny = preferences.getInt("godzinaszesc", 99);
        timeminuty = preferences.getInt("minutaszesc", 99);
// przekazywanie daty  po to aby wstawiał date od pierwszego bicia
        dataDzien = preferences.getInt("dzienBiciaszesc", 99);
        dataMiesiac = preferences.getInt("miesiacBiciaszesc", 99);
        dataRok = preferences.getInt("rokBiciaszesc", 99);
        loadOkresszesc = preferences.getInt("okresszesc", 99);

        boolBoot = preferences.getBoolean("bollBootczesc", false);

//warunek, ktory nie puszcza wątku jesli listaTowarow jest pusta
        if (boolBoot == false) {
            Toast.makeText(context6, " trzeci pusty  ", Toast.LENGTH_LONG).show();

        } else if (boolBoot == true) {
            Toast.makeText(context6, " trzeci leci  ", Toast.LENGTH_LONG).show();
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH, dataDzien);
            c.set(Calendar.MONTH, dataMiesiac);
            c.set(Calendar.YEAR, dataRok);
            c.set(Calendar.HOUR_OF_DAY, timegodziny);
            c.set(Calendar.MINUTE, timeminuty);

//formatuje dane na format daty do toasta
            Date dupa2 = c.getTime();
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
            String date2 = sdf2.format(dupa2);
            Date dupa3 = c.getTime();
            SimpleDateFormat sdf3 = new SimpleDateFormat("h:mm a");
            String date3 = sdf3.format(dupa3);

//wiadomosc o biciu
            terminNastepnegoBiciaSTR = "Następne bicie _, " + " " + date2 + ",  " + date3;

            preferences = PreferenceManager.getDefaultSharedPreferences(context6);
            SharedPreferences.Editor editorr = preferences.edit();
            editorr.putString("info6", terminNastepnegoBiciaSTR);
            editorr.apply();

//tworzy intencje zbudowaną w osobnej class'ie
            Intent intentSzesc = new Intent(context6, srodek_szesc_Notyfication_reciver.class);
            pendingIntentSzesc = PendingIntent.getBroadcast(context6, 600, intentSzesc, PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres  ( w nowej wersji juz bez interwału, tylko alarm )
            alarmManagerSzesc = (AlarmManager) context6.getSystemService(Context.ALARM_SERVICE);
            alarmManagerSzesc.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntentSzesc);
        }
    }
}