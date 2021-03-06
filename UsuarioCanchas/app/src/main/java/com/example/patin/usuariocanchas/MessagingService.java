package com.example.patin.usuariocanchas;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.patin.usuariocanchas.Activities.SplashActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //remoteMessage.getNotification().getBody();


        this.crearNotificacion( CreateMessage.getHeader(remoteMessage.getNotification().getBody()));
        //Log.d("MESSAGING_FIREBASE", "From: " + remoteMessage.getFrom());
        //Log.d("MESSAGING_FIREBASE", "Notification Message Body: " + remoteMessage.getNotification().getBody());



    }



    public void crearNotificacion(String mensaje){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //icon appears in device notification bar and right hand corner of notification
        builder.setSmallIcon(R.drawable.logo_inicial);

        // This intent is fired when notification is clicked
        Intent intent = new Intent(getApplicationContext(),SplashActivity.class );
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Large icon appears on the left of the notification
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_inicial));

        // Content title, which appears in large type at the top of the notification
        builder.setContentTitle("CanchApp");

        // Content text, which appears in smaller text below the title
        builder.setContentText(mensaje);

        // The subtext, which appears under the text on newer devices.
        // This will show-up in the devices with Android 4.2 and above only
        builder.setSubText("Presiona para ver mas.");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Sound of notification
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        // Will display the notification in the notification bar
        notificationManager.notify(NotificationManager.IMPORTANCE_HIGH, builder.build());



    }
}
