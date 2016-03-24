package com.hitherejoe.notifi.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import com.hitherejoe.notifi.R;
import com.hitherejoe.notifi.ui.main.MainActivity;

import javax.inject.Inject;

public class NotificationUtil {

    public static final int REPLY_INTENT_ID = 0;
    public static final int ARCHIVE_INTENT_ID = 1;

    public static final int REMOTE_INPUT_ID = 1247;

    public static final String LABEL_REPLY = "Reply";
    public static final String LABEL_ARCHIVE = "Archive";
    public static final String REPLY_ACTION = "com.hitherejoe.notifi.util.ACTION_MESSAGE_REPLY";
    public static final String KEY_PRESSED_ACTION = "KEY_PRESSED_ACTION";
    public static final String KEY_TEXT_REPLY = "KEY_TEXT_REPLY";
    private static final String KEY_NOTIFICATION_GROUP = "KEY_NOTIFICATION_GROUP";

    @Inject
    public NotificationUtil() { }

    public void showStandardNotification(Context context) {
        NotificationCompat.Builder notification = createNotificationBuider(context,
                "Standard Notification", "This is just a standard notification!");
        showNotification(context, notification.build(), 0);
    }

    public void showBundledNotifications(Context context) {

        PendingIntent archiveIntent = PendingIntent.getActivity(context,
                ARCHIVE_INTENT_ID,
                getMessageReplyIntent(LABEL_ARCHIVE),
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action replyAction =
                new NotificationCompat.Action.Builder(android.R.drawable.sym_def_app_icon,
                        LABEL_REPLY, archiveIntent)
                        .build();
        NotificationCompat.Action archiveAction =
                new NotificationCompat.Action.Builder(android.R.drawable.sym_def_app_icon,
                        LABEL_ARCHIVE, archiveIntent)
                        .build();

        NotificationCompat.Builder first = createNotificationBuider(
                context, "First notification", "This is the first bundled notification");
        first.setGroupSummary(true).setGroup(KEY_NOTIFICATION_GROUP);

        NotificationCompat.Builder second = createNotificationBuider(
                context, "Second notification", "Here's the second one");
        second.setGroup(KEY_NOTIFICATION_GROUP);

        NotificationCompat.Builder third = createNotificationBuider(
                context, "Third notification", "And another for luck!");
        third.setGroup(KEY_NOTIFICATION_GROUP);
        third.addAction(replyAction);
        third.addAction(archiveAction);

        NotificationCompat.Builder fourth = createNotificationBuider(
                context, "Fourth notification", "This one sin't a part of our group");
        third.setGroup(KEY_NOTIFICATION_GROUP);

        showNotification(context, first.build(), 0);
        showNotification(context, second.build(), 1);
        showNotification(context, third.build(), 2);
        showNotification(context, fourth.build(), 3);
    }

    public void showStandardHeadsUpNotification(Context context) {

        PendingIntent archiveIntent = PendingIntent.getActivity(context,
                ARCHIVE_INTENT_ID,
                getMessageReplyIntent(LABEL_ARCHIVE), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action replyAction =
                new NotificationCompat.Action.Builder(android.R.drawable.sym_def_app_icon,
                        LABEL_REPLY, archiveIntent)
                        .build();
        NotificationCompat.Action archiveAction =
                new NotificationCompat.Action.Builder(android.R.drawable.sym_def_app_icon,
                        LABEL_ARCHIVE, archiveIntent)
                        .build();

        NotificationCompat.Builder notificationBuider = createNotificationBuider(
                context, "Heads up!", "This is a normal heads up notification");
        notificationBuider.setPriority(Notification.PRIORITY_HIGH).setVibrate(new long[0]);
        notificationBuider.addAction(replyAction);
        notificationBuider.addAction(archiveAction);

        Intent push = new Intent();
        push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        push.setClass(context, MainActivity.class);

        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                push, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuider.setFullScreenIntent(fullScreenPendingIntent, true);
        showNotification(context, notificationBuider.build(), 0);
    }

    public void showCustomLayoutHeadsUpNotification(Context context) {

        RemoteViews remoteViews = createRemoteViews(context,
                R.layout.notification_custom_content, R.drawable.ic_phonelink_ring_primary_24dp,
                "Heads up!", "This is a custom heads-up notification",
                R.drawable.ic_priority_high_primary_24dp);

        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse("http://www.hitherejoe.com"));
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification.Builder builder = createCustomNotificationBuilder(context);
        builder.setCustomContentView(remoteViews)
                .setStyle(new Notification.DecoratedCustomViewStyle())
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[0])
                .setContentIntent(contentIntent);

        Intent push = new Intent();
        push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        push.setClass(context, MainActivity.class);

        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                push, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(fullScreenPendingIntent, true);
        showNotification(context, builder.build(), 0);
    }

    public void showRemoteInputNotification(Context context) {
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(context.getString(R.string.text_label_reply))
                .build();

        PendingIntent replyIntent = PendingIntent.getActivity(context,
                REPLY_INTENT_ID,
                getMessageReplyIntent(LABEL_REPLY),
                PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent archiveIntent = PendingIntent.getActivity(context,
                ARCHIVE_INTENT_ID,
                getMessageReplyIntent(LABEL_ARCHIVE),
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action replyAction =
                new NotificationCompat.Action.Builder(android.R.drawable.sym_def_app_icon,
                        LABEL_REPLY, replyIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        NotificationCompat.Action archiveAction =
                new NotificationCompat.Action.Builder(android.R.drawable.sym_def_app_icon,
                        LABEL_ARCHIVE, archiveIntent)
                        .build();

        NotificationCompat.Builder builder =
                createNotificationBuider(context, "Remote input", "Try typing some text!");
        builder.addAction(replyAction);
        builder.addAction(archiveAction);

        showNotification(context, builder.build(), REMOTE_INPUT_ID);
    }

    public void showCustomContentViewNotification(Context context) {
        RemoteViews remoteViews = createRemoteViews(context,
                R.layout.notification_custom_content, R.drawable.ic_phonelink_ring_primary_24dp,
                "Custom notification", "This is a custom layout",
                R.drawable.ic_priority_high_primary_24dp);

        Notification.Builder builder = createCustomNotificationBuilder(context);
        builder.setCustomContentView(remoteViews).setStyle(new Notification.DecoratedCustomViewStyle());

        showNotification(context, builder.build(), 0);
    }

    public void showCustomBigContentViewNotification(Context context) {
        RemoteViews remoteViews = createRemoteViews(context,
                R.layout.notification_custom_big_content, R.drawable.ic_phonelink_ring_primary_24dp,
                "Custom notification", "This one is a little bigger!",
                R.drawable.ic_priority_high_primary_24dp);

        Notification.Builder builder = createCustomNotificationBuilder(context);
        builder.setCustomBigContentView(remoteViews)
                .setStyle(new Notification.DecoratedCustomViewStyle());

        showNotification(context, builder.build(), 0);
    }

    public void showCustomBothContentViewNotification(Context context) {
        RemoteViews remoteViews = createRemoteViews(context, R.layout.notification_custom_content,
                R.drawable.ic_phonelink_ring_primary_24dp, "Custom notification",
                "This is a custom layout", R.drawable.ic_priority_high_primary_24dp);

        RemoteViews bigRemoteView = createRemoteViews(context,
                R.layout.notification_custom_big_content, R.drawable.ic_phonelink_ring_primary_24dp,
                "Custom notification", "This one is a little bigger",
                R.drawable.ic_priority_high_primary_24dp);

        Notification.Builder builder = createCustomNotificationBuilder(context);
        builder.setCustomContentView(remoteViews)
                .setCustomBigContentView(bigRemoteView)
                .setStyle(new Notification.DecoratedCustomViewStyle());

        showNotification(context, builder.build(), 0);
    }

    public void showCustomMediaViewNotification(Context context) {
        RemoteViews remoteViews = createRemoteViews(context, R.layout.notification_custom_content,
                R.drawable.ic_phonelink_ring_primary_24dp, "Custom media notification",
                "This is a custom media layout", R.drawable.ic_play_arrow_primary_24dp);

        Notification.Builder builder = createCustomNotificationBuilder(context);
        builder.setCustomContentView(remoteViews)
                .setStyle(new Notification.DecoratedMediaCustomViewStyle());

        showNotification(context, builder.build(), 0);
    }

    private RemoteViews createRemoteViews(Context context, int layout, int iconResource,
                                          String title, String message, int imageResource) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layout);
        remoteViews.setImageViewResource(R.id.image_icon, iconResource);
        remoteViews.setTextViewText(R.id.text_title, title);
        remoteViews.setTextViewText(R.id.text_message, message);
        remoteViews.setImageViewResource(R.id.image_end, imageResource);
        return remoteViews;
    }

    public Notification.Builder createCustomNotificationBuilder(Context context) {
        return new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_phonelink_ring_primary_24dp)
                .setAutoCancel(true);
    }


    public NotificationCompat.Builder createNotificationBuider(Context context,
                                                               String title, String message) {
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.me);
        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_phonelink_ring_primary_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(largeIcon)
                .setColor(ContextCompat.getColor(context, R.color.primary))
                .setAutoCancel(true);
    }

    private Intent getMessageReplyIntent(String label) {
        return new Intent()
                .addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .setAction(REPLY_ACTION)
                .putExtra(KEY_PRESSED_ACTION, label);
    }

    private void showNotification(Context context, Notification notification, int id) {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, notification);
    }
}