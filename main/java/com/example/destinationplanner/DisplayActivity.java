package com.example.destinationplanner;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {

    private TextView tvDestination;
    private TextView tvNotes;
    private Button btnOpenMaps;
    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        tvDestination = findViewById(R.id.tvDestination);
        tvNotes = findViewById(R.id.tvNotes);
        btnOpenMaps = findViewById(R.id.btnOpenMaps);
        btnShare = findViewById(R.id.btnShare);

        // Retrieve intent extras — MAKE THEM FINAL
        final String destination = getIntent().getStringExtra("destination_name");
        final String notesTemp = getIntent().getStringExtra("destination_notes");

        String safeNotes = (notesTemp == null || notesTemp.isEmpty()) ? "(No notes)" : notesTemp;

        tvDestination.setText(destination);
        tvNotes.setText(safeNotes);

        btnOpenMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(destination));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                try {
                    startActivity(mapIntent);
                } catch (ActivityNotFoundException e) {
                    Intent fallback = new Intent(Intent.ACTION_VIEW, geoUri);
                    try {
                        startActivity(fallback);
                    } catch (ActivityNotFoundException ex) {
                        Toast.makeText(DisplayActivity.this, "No map application found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareText = "Destination: " + destination + "\nNotes: " + safeNotes;
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(share, "Share via"));
            }
        });
    }
}
