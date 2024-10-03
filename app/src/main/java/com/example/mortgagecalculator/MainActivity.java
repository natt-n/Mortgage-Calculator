package com.example.mortgagecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText principalEditText, amortizationEditText;
    private Spinner paymentFrequencySpinner;
    private Button submitButton;
    private TextView prevCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views by ID
        principalEditText = findViewById(R.id.principal);
        amortizationEditText = findViewById(R.id.amort);
        paymentFrequencySpinner = findViewById(R.id.paymentfrequency);
        submitButton = findViewById(R.id.submit);
        prevCalculation = findViewById(R.id.prev_val);

        // Set up Spinner with array from resources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.time_period_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentFrequencySpinner.setAdapter(adapter);

        // Get previous calculation from Intent (if any)
        Intent incomingIntent = getIntent();
        String previousResult = incomingIntent.getStringExtra("previousResult");
        if (previousResult != null) {
            prevCalculation.setText(previousResult);
        }

        // Set up Submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateMortgage();
            }
        });
    }

    private void calculateMortgage() {
        // Retrieve user input
        String principal = principalEditText.getText().toString();
        String amortizationPeriod = amortizationEditText.getText().toString();
        String paymentFrequency = paymentFrequencySpinner.getSelectedItem().toString();

        if (!principal.isEmpty() && !amortizationPeriod.isEmpty()) {
            // Create an Intent to send data to ResultActivity
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("principal", Double.parseDouble(principal));
            intent.putExtra("amortizationPeriod", Integer.parseInt(amortizationPeriod));
            intent.putExtra("paymentFrequency", paymentFrequency);
            startActivity(intent);
        }
    }
}
