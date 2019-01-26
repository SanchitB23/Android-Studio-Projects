package com.udacitytut.orderapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    static CheckBox  hasWhippedCream, hasChocolate;
    static EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hasWhippedCream = findViewById(R.id.whipped_cream);
        hasChocolate = findViewById(R.id.chocolate);
        name = findViewById(R.id.name);
    }

    int numberOfCoffees = 2;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //displayMessage("Total: ₹" +calculatePrice(numberOfCoffees,5)+"\nThank You");
        displayQuantity(numberOfCoffees);
        orderSummary(numberOfCoffees, 5);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView order_summary_text_view = (TextView) findViewById(R.id.order_summary_text_view);
        order_summary_text_view.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    public void decrement(View view) {
        if (numberOfCoffees == 1) {
            Toast.makeText(this, "Cannot order less than 1 Coffee", Toast.LENGTH_SHORT).show();
            return;
        } else
            numberOfCoffees--;
            displayQuantity(numberOfCoffees);

    }

    public void increment(View view) {
        if (numberOfCoffees == 10) {
            Toast.makeText(this, "Cannot order more than 10 Coffees", Toast.LENGTH_SHORT).show();
            return;
        } else
            numberOfCoffees++;
            displayQuantity(numberOfCoffees);

    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView order_summary_text_view = (TextView) findViewById(R.id.order_summary_text_view);
        order_summary_text_view.setText(message);
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity, int cost) {
        if (hasWhippedCream.isChecked()) {
            cost += 1;
        }
        if (hasChocolate.isChecked()) {
            cost += 2;
        }
        int price = quantity * cost;
        return price;
    }

    private void orderSummary(int quanitity, int cost) {
        String getName = name.getText().toString();
        String summary1 = "Name: " + getName + "\n";
        String summary2 = "Quantity: " + quanitity + "\nTotal: ₹" + calculatePrice(quanitity, cost) + "\nThank You!";
        if (hasWhippedCream.isChecked()) {
            summary1 = summary1 + "Whipped Cream Added\n";
        }
        if (hasChocolate.isChecked()) {
            summary1 += "Chocolate Added\n";
        }
        //displayMessage(summary1 + summary2);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));                               //This will take to only mailing apps and none other
        intent.putExtra(intent.EXTRA_SUBJECT,"Order for "+getName);
        intent.putExtra(intent.EXTRA_TEXT,summary1+summary2);
        if(intent.resolveActivity(getPackageManager())!=null)
            startActivity(intent);
        else
            Toast.makeText(this,"No Email App Found!",Toast.LENGTH_SHORT);
    }
}