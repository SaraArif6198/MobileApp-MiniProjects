package com.example.destinationplanner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity: collects basic destination info and sends it via Intent to DisplayActivity.
 */
public class MainActivity extends AppCompatActivity {

    private EditText edtDestination;
    private EditText edtNotes;
    private Button btnPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtDestination = findViewById(R.id.edtDestination);
        edtNotes = findViewById(R.id.edtNotes);
        btnPlan = findViewById(R.id.btnPlan);

        btnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destination = edtDestination.getText().toString().trim();
                String notes = edtNotes.getText().toString().trim();

                if (TextUtils.isEmpty(destination)) {
                    Toast.makeText(MainActivity.this, "Please enter a destination", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create intent and put extras. We're using Intent explicitly here.
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("destination_name", destination);
                intent.putExtra("destination_notes", notes);
                // Start DisplayActivity which will receive the Intent
                startActivity(intent);
            }
        });
    }
}
