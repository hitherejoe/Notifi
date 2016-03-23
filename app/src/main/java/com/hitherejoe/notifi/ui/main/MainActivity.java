package com.hitherejoe.notifi.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;

import com.hitherejoe.notifi.R;
import com.hitherejoe.notifi.ui.base.BaseActivity;
import com.hitherejoe.notifi.util.NotificationUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Inject NotificationUtil mNotificationUtil;

    public static final String EXTRA_MESSAGE =
            "com.hitherejoe.notifi.ui.main.MessageActivity.EXTRA_MESSAGE";

    public static Intent getStartIntent(Context context, String message) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        activityComponent().inject(this);
    }

    @OnClick(R.id.button_standard_notification)
    public void onButtonStandardNotificationClick() {
        mNotificationUtil.showStandardNotification(this);
    }

    @OnClick(R.id.button_bundled_notification)
    public void onButtonBundledNotificationsClick() {
        mNotificationUtil.showBundledNotifications(this);
    }

    @OnClick(R.id.button_remote_input_notification)
    public void onButtonRemoteInputNotificationsClick() {
        mNotificationUtil.showRemoteInputNotification(this);
    }

    @OnClick(R.id.button_custom_content_view_notification)
    public void onButtonCustomContentViewNotificationClick() {
        mNotificationUtil.showCustomContentViewNotification(this);
    }

    @OnClick(R.id.button_custom_content_big_view_notification)
    public void onButtonCustomBigContentViewNotificationClick() {
        mNotificationUtil.showCustomBigContentViewNotification(this);
    }

    @OnClick(R.id.button_custom_normal_and_big_content_views_notification)
    public void onButtonCustomNormalAndBigContentViewsNotification() {
        mNotificationUtil.showCustomBothContentViewNotification(this);
    }

    @OnClick(R.id.button_custom_media_content_view_notification)
    public void onButtonCustomMediaContentViewNotificationClick() {
        mNotificationUtil.showCustomMediaViewNotification(this);
    }

    @OnClick(R.id.button_heads_up_notification)
    public void onButtonHeadsUpNotificationClick() {
        mNotificationUtil.showStandardHeadsUpNotification(this);
    }

    @OnClick(R.id.button_custom_layout_heads_up_notification)
    public void onButtonCustomLayoutHeadsUpNotificationClick() {
        mNotificationUtil.showCustomLayoutHeadsUpNotification(this);
    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(NotificationUtil.KEY_TEXT_REPLY);
        }
        return null;
    }
}
