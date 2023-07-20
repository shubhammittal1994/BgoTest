package com.bango.bangoLive.FirebaseNotification;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bango.bangoLive.HomeActivity;

import com.bango.bangoLive.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    private final int NOTIFICATION_ID = 10;
    private NotificationChannel channel = null;
    private Uri defaultSound;
    private Notification notification;
    private NotificationChannel mChannel;
    private Intent notificationIntent;
    private Intent voiceCutIntent;
    private String liveType = "chatNotification";
    String bigContent;

    private static RemoteViews contentView;
    private static NotificationManager notificationManager;
    private static final int NotificationID = 1005;
    private static NotificationCompat.Builder mBuilder;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("onMessageReceived", "onMessageReceived: " + URLBuilder.Parameter.type);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

//            setBookingOreoNotification(remoteMessage.getData().get(URLBuilder.Parameter.title.toString()),
//                    remoteMessage.getData().get(URLBuilder.Parameter.message.toString()),
//                    remoteMessage.getData().get(URLBuilder.Parameter.image.toString()),
//                    Objects.requireNonNull(remoteMessage.getData().get(URLBuilder.Parameter.type.toString())), "", bigContent);

            Log.d("onMessageReceived", "onMessageReceived: " + URLBuilder.Parameter.type);
            setBookingOreoNotification("", remoteMessage.getData().get(URLBuilder.Parameter.title.toString()),
                    remoteMessage.getData().get(URLBuilder.Parameter.message.toString()),
                    remoteMessage.getData().get(URLBuilder.Parameter.type.toString()), "", bigContent);

        } else {
            Log.d("onMessageReceived", "onMessageReceived: " + URLBuilder.Parameter.type);

            showNotification("", remoteMessage.getData().get(URLBuilder.Parameter.title.toString()),
                    remoteMessage.getData().get(URLBuilder.Parameter.message.toString()), remoteMessage.getData().get(URLBuilder.Parameter.image.toString()), "",
                    Objects.requireNonNull(remoteMessage.getData().get(URLBuilder.Parameter.type.toString())), bigContent);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private void showNotification(String s, String title, String message, String sx, String type, String image, String bigContent) {


        Log.d("onMessageReceived", "onMessageReceived: " + type);

        Intent intent = null;

        if (!type.equalsIgnoreCase("")) {
            intent = new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("data_key", "1");


        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_IMMUTABLE);
        defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Random random = new Random();
        final int m = random.nextInt(9999 - 1000) + 1000;
        Log.d("onMessageReceived", "onMessageReceived: " + type);

        if (!type.equalsIgnoreCase("")) {

            if (type.equalsIgnoreCase(URLBuilder.Type.live.toString())) {

                Log.d("onMessageReceived", "onMessageReceived: " + type);

                final Notification.Builder builder = new Notification.Builder(getApplicationContext());
                Notification.BigTextStyle textStyle = new Notification.BigTextStyle(builder);
//
//                builder.setStyle(new Notification.BigTextStyle(builder)
//                                .bigText(sx)
//                                .setBigContentTitle("Chat Notification")
//                                .setSummaryText("Notification"))
//                        .setContentTitle(title)
//                        .setContentText(message)
//                        .setVibrate(new long[]{200, 200, 200, 200})
//                        .setSound(defaultSound)
//                        .setContentIntent(pendingIntent)
//                        .setGroup(type).setAutoCancel(true)
//                        .setSmallIcon(R.drawable.bango);
//
//                notificationManager.notify(m, builder.build());
                if (image.equalsIgnoreCase("")) {
                    builder.setStyle(new Notification.BigTextStyle(builder)
                                    .bigText(sx)
                                    .setBigContentTitle("Chat Notification")
                                    .setSummaryText("Notification"))
                            .setContentTitle(title)
                            .setContentText(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setSound(defaultSound)
                            .setContentIntent(pendingIntent)
                            .setGroup(type).setAutoCancel(true)
                            .setSmallIcon(R.drawable.bango_icon);

                    notificationManager.notify(m, builder.build());
                } else {
                    Notification.BigPictureStyle pictureStyle = new Notification.BigPictureStyle(builder);
                    builder.setStyle(pictureStyle
                                    .setBigContentTitle(bigContent)
                                    .setSummaryText("Notification"))
                            .setContentTitle(title)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setGroup(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setSound(defaultSound)
                            .setSmallIcon(R.drawable.bango_icon);


                }
            } else {
                Log.d("onMessageReceived", "onMessageReceived: " + type);

                final Notification.Builder builder = new Notification.Builder(getApplicationContext());
                Notification.BigTextStyle textStyle = new Notification.BigTextStyle(builder);
                if (image.equalsIgnoreCase("")) {
                    builder.setStyle(new Notification.BigTextStyle(builder)
                                    .bigText(sx)
                                    .setBigContentTitle("Chat Notification")
                                    .setSummaryText("Notification"))
                            .setContentTitle(title)
                            .setContentText(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setSound(defaultSound)
//                             .setContentIntent(pendingIntent)
                            .setGroup(type)
                            .setSmallIcon(R.drawable.bango_icon);

                    notificationManager.notify(m, builder.build());
                } else {
                    Notification.BigPictureStyle pictureStyle = new Notification.BigPictureStyle(builder);
                    builder.setStyle(pictureStyle
                                    .setBigContentTitle(bigContent)
                                    .setSummaryText("Notification"))
                            .setContentTitle(title)
                            .setAutoCancel(true)
//                             .setContentIntent(pendingIntent)
                            .setGroup(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setSound(defaultSound)
                            .setSmallIcon(R.drawable.bango_icon);


                }
            }

        } else {
            notification = new NotificationCompat.Builder(this)
                    .setGroup(type)
                    .setContentText(message)
                    .setContentTitle(title)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.bango_icon)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{200, 200, 200, 200})
                    .setSound(defaultSound)
                    .build();
            notificationManager.notify(m, notification);
        }
    }

    public void setNotification(String title, String body) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2").setSmallIcon(R.drawable.bango_icon).setContentTitle(title).setContentText(body).setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(101, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private void setBookingOreoNotification(String s, String title, String message, String type, String image, String bigContent) {


        Intent intent = null;
        RunNotification(title, message, type, "", bigContent);
        if (!type.equalsIgnoreCase("")) {

            intent = new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("data_key", "1");
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101,
                intent, PendingIntent.FLAG_IMMUTABLE);


// Sets an ID for the notification, so it can be updated.
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "@Ep_Live";// The user-visible name of the channel.

        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        }
        final Notification.Builder builder = new Notification.Builder(getApplicationContext());
// Create a notification and set the notification channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setGroup(type)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.bango_icon)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .build();

            if (!type.equalsIgnoreCase("")) {

                if (type.equalsIgnoreCase(URLBuilder.Type.live.toString())) {
                    builder.setStyle(new Notification.BigTextStyle(builder)
                                    .setBigContentTitle(bigContent)
                                    .setSummaryText("Notification"))
                            .setContentTitle(title)
                            .setContentText(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setChannelId(CHANNEL_ID)
                            .setContentIntent(pendingIntent)
                            .setGroup(type)
                            .setSmallIcon(R.drawable.bango_icon);
                } else if (type.equalsIgnoreCase(URLBuilder.Type.follow_request.toString())) {
                    builder.setStyle(new Notification.BigTextStyle(builder)
                                    .setBigContentTitle(bigContent)
                                    .setSummaryText("Notification"))
                            .setContentTitle(title)
                            .setContentText(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setChannelId(CHANNEL_ID)
                            .setContentIntent(pendingIntent)
                            .setGroup(type)
                            .setSmallIcon(R.drawable.bango_icon);
                } else if (type.equalsIgnoreCase(URLBuilder.Type.mess.toString())) {
                    builder.setStyle(new Notification.BigTextStyle(builder)
                                    .setBigContentTitle(bigContent)
                                    .setSummaryText("Notification"))
                            .setContentTitle(title)
                            .setContentText(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setChannelId(CHANNEL_ID)
                            .setContentIntent(pendingIntent)
                            .setGroup(type)
                            .setSmallIcon(R.drawable.bango_icon);
                } else {
                    builder.setStyle(new Notification.BigTextStyle(builder)
                                    .setBigContentTitle(bigContent)
                                    .setSummaryText("Notification"))
                            .setContentTitle(title)
                            .setContentText(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setChannelId(CHANNEL_ID)
                            .setContentIntent(pendingIntent)
                            .setGroup(type)
                            .setSmallIcon(R.drawable.bango_icon);
                }


            }

        }
        final NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(mChannel);
        }

// Issue the notification.
        Random random = new Random();
        final int m = random.nextInt(9999 - 1000) + 1000;

        if (!type.equalsIgnoreCase("")) {

            if (type.equalsIgnoreCase(URLBuilder.Type.live.toString())) {
                if (image.equalsIgnoreCase("")) {
                    mNotificationManager.notify(m, builder.build());
                } else {
                    builder.setStyle(new Notification.BigTextStyle(builder)
                                    .setBigContentTitle(message)
                                    .setSummaryText("Notification"))
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setGroup(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setSmallIcon(R.drawable.bango_icon);
                }
            } else if (type.equalsIgnoreCase(URLBuilder.Type.follow_request.toString())) {
                if (image.equalsIgnoreCase("")) {
                    mNotificationManager.notify(m, builder.build());
                } else {
                    builder.setStyle(new Notification.BigTextStyle(builder)
                                    .setBigContentTitle(message)
                                    .setSummaryText("Notification"))
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setGroup(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setSmallIcon(R.drawable.bango_icon);
                }
            } else if (type.equalsIgnoreCase(URLBuilder.Type.mess.toString())) {
                if (image.equalsIgnoreCase("")) {
                    mNotificationManager.notify(m, builder.build());
                } else {
                    builder.setStyle(new Notification.BigTextStyle(builder)
                                    .setBigContentTitle(message)
                                    .setSummaryText("Notification"))
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setGroup(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setSmallIcon(R.drawable.bango_icon);
                }
            } else {
                if (image.equalsIgnoreCase("")) {
                    mNotificationManager.notify(m, builder.build());
                } else {
                    builder.setStyle(new Notification.BigTextStyle(builder)
                                    .setBigContentTitle(message)
                                    .setSummaryText("Notification"))
                            .setAutoCancel(true)
//                        .setContentIntent(pendingIntent)
                            .setGroup(message)
                            .setVibrate(new long[]{200, 200, 200, 200})
                            .setSmallIcon(R.drawable.bango_icon);

                }
            }

        } else {

            mNotificationManager.notify(m, notification);

        }
    }

    public class LatestFirebaseMessagingService extends FirebaseMessagingService {

        @Override
        public void onNewToken(String mToken) {
            super.onNewToken(mToken);

        }

        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);
        }
    }

    @SuppressLint("RemoteViewLayout")
    private void RunNotification(String title, String message, String type, String image, String bigContent) {

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");

        contentView = new RemoteViews(getPackageName(), R.layout.notification);
//
        contentView.setImageViewBitmap(R.id.image, getBitmapFromURL(image));
        contentView.setTextViewText(R.id.title, title);
        contentView.setTextViewText(R.id.text, message);
        mBuilder.setSmallIcon(R.drawable.bango_icon);
        mBuilder.setAutoCancel(false);
        mBuilder.setOngoing(true);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        mBuilder.setOnlyAlertOnce(true);
        mBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
        mBuilder.setContent(contentView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        notification = mBuilder.build();
        notificationManager.notify(NotificationID, notification);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}