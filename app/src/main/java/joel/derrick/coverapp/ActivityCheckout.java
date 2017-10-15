package joel.derrick.coverapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * Created by JD on 12/10/17.
 * Activity to check the final order details
 * Note that at the moment going beyond this point is outside the scope of this project
 */

public class ActivityCheckout extends ActivityNavBarListenerImplemented {
    TextView subtotal, tax, shipping, total;
    ShoppingCart cart = ShoppingCart.getInstance();
    Button complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navBarOnCreate(R.layout.activity_checkout);

        subtotal = (TextView)findViewById(R.id.checkout_subtotal_text);
        tax = (TextView)findViewById(R.id.checkout_tax_text);
        shipping = (TextView)findViewById(R.id.checkout_shipping_text);
        total = (TextView)findViewById(R.id.checkout_total_text);
        complete = (Button) findViewById(R.id.complete_purchase_button);

        subtotal.setText(NumberFormat.getCurrencyInstance().format(cart.calcSubtotal()));
        tax.setText(NumberFormat.getCurrencyInstance().format(cart.calcTax()));
        shipping.setText(NumberFormat.getCurrencyInstance().format(cart.calcShippingPrice()));
        total.setText(NumberFormat.getCurrencyInstance().format(cart.calcTotal()));

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(getResources().getString(R.string.complete_toast));
            }
        });

    }
    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
