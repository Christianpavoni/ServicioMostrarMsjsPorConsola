package com.example.serviciomostrarmsjsporconsola;

import android.Manifest;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class MyService extends Service {
    private static int contador=5;

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        final Uri sms= Telephony.Sms.CONTENT_URI;
        final ContentResolver cr=getContentResolver();


        Runnable leer=new Runnable() {
            Cursor cursor;
            @Override
            public void run() {
                while(true) {
                    cursor = cr.query(sms, null, null, null, null);

                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext() && contador > 0) {
                            String nro = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.ADDRESS));
                            String texto = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.BODY));
                            String fecha = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.DATE));
                            Date fechaMsj = new Date(Long.valueOf(fecha));

                            Log.d("salida", "De: " + nro);
                            Log.d("salida", "Cuerpo: " + texto);
                            Log.d("salida", "Fecha: " + String.valueOf(fechaMsj));
                            Log.d("salida", "----------------------------------------------------");

                            contador--;
                        }
                        contador = 5;
                    }
                    try{
                        Thread.sleep(9000);
                    }
                    catch (InterruptedException e)
                    {
                        Log.e("Error",e.getMessage());
                    }

                }
            }
        };
        Thread thread=new Thread(leer);
        thread.start();
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {

    return null;
    }
}
