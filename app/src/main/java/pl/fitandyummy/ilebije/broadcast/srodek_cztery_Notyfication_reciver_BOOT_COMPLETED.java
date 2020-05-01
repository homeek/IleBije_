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

public class srodek_cztery_Notyfication_reciver_BOOT_COMPLETED extends BroadcastReceiver {

    public AlarmManager alarmManagerCztery;
    public PendingIntent pendingIntentCztery;
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
    public void onReceive(final Context context4, Intent intent4) {

        context4.getApplicationContext();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context4);

//value = preferences.getString("keyszesc", "defaultValue");
        timegodziny = preferences.getInt("godzinacztery", 99);
        timeminuty = preferences.getInt("minutacztery", 99);

// przekazywanie daty  po to aby wstawiał date od pierwszego bicia
        dataDzien = preferences.getInt("dzienBiciacztery", 99);
        dataMiesiac = preferences.getInt("miesiacBiciacztery", 99);
        dataRok = preferences.getInt("rokBiciacztery", 99);
        loadOkresszesc = preferences.getInt("okrescztery", 99);

        boolBoot = preferences.getBoolean("bollBootcztery", false);

        if (boolBoot == false) {
            Toast.makeText(context4, " czwarty pusty  ", Toast.LENGTH_LONG).show();

        } else if (boolBoot == true) {
            Toast.makeText(context4, "    ", Toast.LENGTH_LONG).show();

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

            preferences = PreferenceManager.getDefaultSharedPreferences(context4);
            SharedPreferences.Editor editorr = preferences.edit();
            editorr.putString("info4", terminNastepnegoBiciaSTR);
            editorr.apply();

//tworzy intencje zbudowaną w osobnej class'ie
            Intent intentCztery = new Intent(context4, srodek_cztery_Notyfication_reciver.class);
            pendingIntentCztery = PendingIntent.getBroadcast(context4, 400, intentCztery, PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres  ( w nowej wersji juz bez interwału, tylko alarm )
            alarmManagerCztery = (AlarmManager) context4.getSystemService(Context.ALARM_SERVICE);
            alarmManagerCztery.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntentCztery);
        }
    }
}