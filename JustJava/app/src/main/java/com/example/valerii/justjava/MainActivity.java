package com.example.valerii.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity == 100) {
            toast(getString(R.string.more_than_100));
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            toast(getString(R.string.less_than_1));
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    private void toast(String textToast) {
        Toast.makeText(this, textToast, Toast.LENGTH_SHORT).show();
    }

    /**
    * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        displayMessage(priceMessage);
    }

    public String createOrderSummary(int price, boolean hasWhippedCream,
                                     boolean hasChocolate, String name) {
        String priceMessage = getString(R.string.name) + ": " + name;
        priceMessage += "\n" + getString(R.string.add_whipped_cream) + "    ";
        if (hasWhippedCream) {
            priceMessage += "+";
        } else {
            priceMessage += "-";
        }
        priceMessage += "\n" + getString(R.string.add_chocolate) + "    ";
        if (hasChocolate) {
            priceMessage += "+";
        } else {
            priceMessage += "-";
        }
        priceMessage += "\n" + getString(R.string.quantity) + ": " + quantity;
        priceMessage += "\n" + getString(R.string.order_summary) + ": " +
                NumberFormat.getCurrencyInstance(Locale.US).format(price);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    public int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int pricePerCup = 5;
        int chocolate = 2;
        int whippedCream = 1;

        if (!hasChocolate) {
            chocolate = 0;
        }
        if (!hasWhippedCream) {
            whippedCream = 0;
        }
        return quantity * (pricePerCup + chocolate + whippedCream);
    }

    /**
    * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public void submitSend(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        composeEmail(priceMessage, name);
    }

    public void composeEmail(String subject, String name) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.just_java_order_for) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}

