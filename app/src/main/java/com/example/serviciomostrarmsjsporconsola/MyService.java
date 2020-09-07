package com.example.serviciomostrarmsjsporconsola;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyService extends Service {
    private static int contador=5;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Uri smsRecibidos= Uri.parse("content://sms/inbox");
        ContentResolver cr=getContentResolver();
        Cursor cursor=cr.query(smsRecibidos,null,null,null,null);

        if(cursor.getCount() > 0){
            while(cursor.moveToNext() && contador>0){
                String nro=cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.ADDRESS));
                String texto=cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.BODY));
                String fecha=cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.DATE));
                Date fechaMsj= new Date(Long.valueOf(fecha));

                Log.d("salida","De: "+nro);
                Log.d("salida","Cuerpo: "+texto);
                Log.d("salida", "Fecha: "+String.valueOf(fechaMsj));
                Log.d("salida", "----------------------------------------------------");

                contador--;
            }
            contador=5;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

    return null;
    }
}
