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

public class srodek_dwa_Notyfication_reciver_BOOT_COMPLETED extends BroadcastReceiver {

    public AlarmManager alarmManagerDwa;

    public PendingIntent pendingIntentDwa;

    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;
    public Integer loadOkresszesc;
    public Timer timer;
    public String terminNastepnegoBiciaSTR;

    Boolean boolBoot = false;

    @Override
    public void onReceive(final Context context2, Intent intent2) {
        context2.getApplicationContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context2);
        timegodziny = preferences.getInt("godzinaDwa", 99);
        timeminuty = preferences.getInt("minutaDwa", 99);

// przekazywanie daty  po to aby wstawiał date od pierwszego bicia
        dataDzien = preferences.getInt("dzienBiciaDwa", 99);
        dataMiesiac = preferences.getInt("miesiacBiciaDwa", 99);
        dataRok = preferences.getInt("rokBiciaDwa", 99);
        loadOkresszesc = preferences.getInt("okresdwa", 99);

        boolBoot = preferences.getBoolean("bollBootdwa", false);

        if (boolBoot == false) {
            Toast.makeText(context2, " dwa  puste  ", Toast.LENGTH_LONG).show();

        } else if (boolBoot == true) {
            Toast.makeText(context2, "    ", Toast.LENGTH_LONG).show();
            final Calendar c = Calendar.getInstance();

//pobiera date i czas z datapickera
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
            preferences = PreferenceManager.getDefaultSharedPreferences(context2);
            SharedPreferences.Editor editorr = preferences.edit();
            editorr.putString("info2", terminNastepnegoBiciaSTR);
            editorr.apply();

//tworzy intencje zbudowaną w osobnej class'ie
            Intent intentDwa = new Intent(context2, srodek_dwa_Notyfication_reciver.class);
            pendingIntentDwa = PendingIntent.getBroadcast(context2, 200, intentDwa, PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres  ( w nowej wersji juz bez interwału, tylko alarm )
            alarmManagerDwa = (AlarmManager) context2.getSystemService(Context.ALARM_SERVICE);
            alarmManagerDwa.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntentDwa);
        }
    }
}