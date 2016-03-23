package com.hitherejoe.notifi.ui.message;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.hitherejoe.notifi.R;
import com.hitherejoe.notifi.ui.base.BaseActivity;
import com.hitherejoe.notifi.util.NotificationUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageActivity extends BaseActivity {

    @Bind(R.id.text_you_clicked)
    TextView mYouClickedText;

    @Bind(R.id.text_message)
    TextView mMessageText;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MessageActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        cancelNotification();
        CharSequence messageText = getMessageText(getIntent());
        if (messageText != null) {
            mMessageText.setText(getString(R.string.label_you_typed, messageText));
        }

        String label = getIntent().getStringExtra(NotificationUtil.KEY_PRESSED_ACTION);
        if (label != null) {
            mYouClickedText.setText(getString(R.string.label_you_clicked, label));
        }
    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(NotificationUtil.KEY_TEXT_REPLY);
        }
        return null;
    }

    private void cancelNotification() {
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(NotificationUtil.REMOTE_INPUT_ID);
    }
}
