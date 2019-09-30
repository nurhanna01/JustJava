package com.example.justjava;

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
/**import android.support.v7.app.AppCompatActivity;*/
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        display(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(this, "You cannot have less than 1 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
       /** displayPrice(quantity * 5000);*/

        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckbox=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        int price= calculatePrice(hasWhippedCream,hasChocolate);
        String Message=createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject)+name);
        intent.putExtra(Intent.EXTRA_TEXT,Message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private int calculatePrice(boolean whippedCream,boolean Chocolate){
        int basePrice=5;
        if(whippedCream){
            basePrice += 1;
        }

        if(Chocolate){
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    private String createOrderSummary(String name,int price,boolean addWhippedCream,boolean addChocolate){
        String priceMessage=getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thankyou);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }


}