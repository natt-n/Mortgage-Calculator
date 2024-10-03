package com.example.mortgagecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView resultTextView;
    private Button calculateAnotherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultTextView = findViewById(R.id.result_value);
        calculateAnotherButton = findViewById(R.id.add_another);

        // Get data from the intent
        double principal = getIntent().getDoubleExtra("principal", 0);
        int amortizationPeriod = getIntent().getIntExtra("amortizationPeriod", 0);
        String paymentFrequency = getIntent().getStringExtra("paymentFrequency");

        // Calculate mortgage result
        double result = calculateMortgage(principal, amortizationPeriod, paymentFrequency);

        // Display result
        resultTextView.setText(String.format("Your monthly payment is: %.2f", result));

        // Set up the button to return to MainActivity
        calculateAnotherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent(ResultActivity.this, MainActivity.class);
                returnIntent.putExtra("previousResult", String.format("Last calculation: %.2f", result));
                startActivity(returnIntent);
            }
        });
    }

    private double calculateMortgage(double principal, int amortizationPeriod, String paymentFrequency) {
        // Simplified mortgage calculation formula
        // Assumes fixed interest rate of 5% for example purposes
        double annualInterestRate = 0.05;
        double monthlyInterestRate = annualInterestRate / 12;
        int totalPayments = amortizationPeriod * 12; // Monthly payments for the duration

        // Mortgage calculation formula (Monthly Payment = P * (r(1+r)^n) / ((1+r)^n-1))
        double monthlyPayment = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalPayments)) /
                (Math.pow(1 + monthlyInterestRate, totalPayments) - 1);

        return monthlyPayment;
    }
}
