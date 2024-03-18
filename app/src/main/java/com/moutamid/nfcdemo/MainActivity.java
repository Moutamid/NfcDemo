package com.moutamid.nfcdemo;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NfcManager nfcManager = (NfcManager) getSystemService(Context.NFC_SERVICE);
        if (nfcManager.getDefaultAdapter() == null) {
            Log.d(TAG, "onCreate/31| adapter null: ");
        } else
            Log.d(TAG, "onCreate/29| enabled: " + nfcManager.getDefaultAdapter().isEnabled());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent/28| : " + intent.getAction());

        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()) || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Log.d(TAG, "onNewIntent/40| tech discovered: ");
            // Get the NFC tag from the intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            TextView textView = findViewById(R.id.aaaa);
            textView.setText("TAG ID: " + Arrays.toString(tag.getId()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Enable NFC foreground dispatch
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                PendingIntent.FLAG_MUTABLE);
        IntentFilter[] intentFiltersArray = new IntentFilter[]{};
        String[][] techListsArray = new String[][]{{NfcA.class.getName()}};

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null) {
            Log.d(TAG, "onResume/83| enableForegroundDispatch: ");
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);

        } else {
            Log.d(TAG, "onResume/85| nfcAdapter null: ");
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        // Disable NFC foreground dispatch
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);

    }

}
