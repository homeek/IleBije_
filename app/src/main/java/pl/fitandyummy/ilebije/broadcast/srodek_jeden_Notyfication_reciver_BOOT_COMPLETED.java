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

public class srodek_jeden_Notyfication_reciver_BOOT_COMPLETED extends BroadcastReceiver {

    public AlarmManager alarmManagerRaz;

    public PendingIntent pendingIntentRaz;

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
    public void onReceive(final Context context1, Intent intent1) {
        context1.getApplicationContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context1);

        timegodziny = preferences.getInt("godzinaZakonczenia", 99);
        timeminuty = preferences.getInt("minuta", 99);

// przekazywanie daty  po to aby wstawiał date od pierwszego bicia
        dataDzien = preferences.getInt("dzienBicia", 99);
        dataMiesiac = preferences.getInt("miesiacBicia", 99);
        dataRok = preferences.getInt("rokBicia", 99);
        loadOkresszesc = preferences.getInt("okres", 99);
        boolBoot = preferences.getBoolean("bollBoot", false);

        if (boolBoot == false) {
            Toast.makeText(context1, "  pierwszy pusty ", Toast.LENGTH_LONG).show();

        } else if (boolBoot == true) {
            Toast.makeText(context1, "    ", Toast.LENGTH_LONG).show();
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
            preferences = PreferenceManager.getDefaultSharedPreferences(context1);
            SharedPreferences.Editor editorr = preferences.edit();
            editorr.putString("info1", terminNastepnegoBiciaSTR);
            editorr.apply();

//tworzy intencje zbudowaną w osobnej class'ie
            Intent intentRaz = new Intent(context1, srodek_jeden_Notyfication_reciver.class);
            pendingIntentRaz = PendingIntent.getBroadcast(context1, 100, intentRaz, PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres  ( w nowej wersji juz bez interwału, tylko alarm )
            alarmManagerRaz = (AlarmManager) context1.getSystemService(Context.ALARM_SERVICE);
            alarmManagerRaz.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntentRaz);
        }
    }
}