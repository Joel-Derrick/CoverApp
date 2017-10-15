package joel.derrick.coverapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by JD on 11/10/17.
 * Activity to edit the current cart and proceed to checkout
 */

public class ActivityCart extends ActivityNavBarListenerImplemented {
    ListView list_view;
    CartFragmentAdapter adapter;
    Spinner province_select;
    ShoppingCart cart = ShoppingCart.getInstance();
    TextView subtotal, tax, shipping, cart_owner;
    Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navBarOnCreate(R.layout.activity_cart);

        subtotal = (TextView)findViewById(R.id.cart_subtotal_text);
        tax = (TextView)findViewById(R.id.cart_tax_text);
        shipping = (TextView)findViewById(R.id.cart_shipping_text);
        province_select = (Spinner)findViewById(R.id.province_drop_down);
        list_view = (ListView)findViewById(R.id.cart_list_view);
        checkout = (Button)findViewById(R.id.cart_checkout_button);
        cart_owner = (TextView) findViewById(R.id.owners_cart);
        //replace %s int he string to the username
        cart_owner.setText(cart_owner.getText().toString().replaceFirst("%s", ShoppingCart.getInstance().getUserName()));
       //start that the cart is empty
        if(cart.isEmpty()){
            checkout.setEnabled(false);
            cart_owner.setText(getResources().getString(R.string.cart_is_empty).replaceFirst("%s", cart_owner.getText().toString()));
        }

        setPriceLabels();
        province_select.setSelection(cart.getSelectedProvince());
        adapter = new CartFragmentAdapter(this, cart.getItems());
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                callItemPage(position);
            }
        });

        province_select = (Spinner) findViewById(R.id.province_drop_down);

        province_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                cart.setSelectedProvince(pos);
                setPriceLabels();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        checkout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCheckout();
            }
        });
    }

    //Switch to the item page activity and the item which to display
    private void callItemPage(int position){
        ArrayList<ItemModel> items = cart.getItems();
        if (position >= 0 && position<items.size()) {
            Intent intent = new Intent(this, ActivityCartItemUpdate.class);
            intent.putExtra("item", items.get(position));
            intent.putExtra("position", position);
            intent.putExtra("quantity", cart.getQuantityOfItem(position));
            startActivity(intent);
        }
    }

    //update price lables with the values from the cart
    private void setPriceLabels(){
        if(cart.isEmpty()){
            String blank_value = getResources().getString(R.string.money_blank);
            subtotal.setText(blank_value);
            tax.setText(blank_value);
            shipping.setText(blank_value);
        }
        else {
            subtotal.setText(NumberFormat.getCurrencyInstance().format(cart.calcSubtotal()));
            tax.setText(NumberFormat.getCurrencyInstance().format(cart.calcTax()));
            shipping.setText(NumberFormat.getCurrencyInstance().format(cart.calcShippingPrice()));
        }
    }

    //change the checkout activity
    private void goToCheckout(){
        Intent myIntent = new Intent(this, ActivityCheckout.class);
        startActivity(myIntent);
    }
}