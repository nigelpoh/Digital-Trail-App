package com.example.a1dproject.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.a1dproject.R;
import com.example.a1dproject.databinding.ActivityAttractionBinding;
import com.example.a1dproject.databinding.ExhibitScannerBinding;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ExhibitScannerActivity extends Activity {

    private NfcAdapter mNfcAdapter;
    private static final String TAG = "NFC123";
    private ExhibitScannerBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding = ExhibitScannerBinding.inflate(getLayoutInflater());
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(binding.getRoot());
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        binding.goBackHomeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExhibitScannerActivity.this, HomeActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null) {
            mNfcAdapter.enableReaderMode(this, new NfcAdapter.ReaderCallback() {
                @Override
                public void onTagDiscovered(Tag tag) {
                    if (Arrays.asList(tag.getTechList()).contains(MifareClassic.class.getName())) {
                        MifareClassic mifareTag = MifareClassic.get(tag);
                        try {
                            mifareTag.connect();

                            int blockIndex = 4;
                            int sectorIndex = mifareTag.blockToSector(blockIndex);

                            byte[] keyA = MifareClassic.KEY_DEFAULT;
                            boolean isAuthenticated = mifareTag.authenticateSectorWithKeyA(sectorIndex, keyA);

                            if (isAuthenticated) {
                                byte[] data = mifareTag.readBlock(blockIndex);
                                String text = new String(data, Charset.forName("UTF-8")).trim();
                                Intent intent = new Intent(ExhibitScannerActivity.this, AttractionActivity.class);

                                intent.putExtra("vid", text);

                                startActivity(intent);
                            } else {
                                Log.e(TAG, "Authentication failed");
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "Error reading MIFARE tag data", e);
                        } finally {
                            try {
                                mifareTag.close();
                            } catch (IOException e) {
                                // Ignore
                            }
                        }
                    }
                }
            }, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null) {
            mNfcAdapter.disableReaderMode(this);
        }
    }
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
