package joel.derrick.coverapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * Created by JD on 11/10/17.
 * Activity to edit the quantity or remove an item from the cart
 */

public class ActivityCartItemUpdate extends ActivityNavBarListenerImplemented {
    private ItemModel item;
    int position;
    TextView name, price, description;
    EditText quantity;
    Button update, remove;
    ShoppingCart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navBarOnCreate(R.layout.activity_item_in_cart);

        cart = ShoppingCart.getInstance();

        Intent intent = getIntent();
        item = intent.getParcelableExtra("item");
        position = intent.getIntExtra("position", 0);

        name=(TextView)findViewById(R.id.item_name_label);
        price=(TextView)findViewById(R.id.item_price_label);
        description=(TextView)findViewById(R.id.item_desc_body);
        quantity=(EditText)findViewById(R.id.cart_update_quantity_value);
        update=(Button)findViewById(R.id.update_quantity);
        remove=(Button)findViewById(R.id.remove_from_cart);

        name.setText(item.getName());
        price.setText(NumberFormat.getCurrencyInstance().format(item.getPrice()));
        description.setText(item.getDesc());
        quantity.setText(String.valueOf(intent.getIntExtra("quantity",0)));

        //update quantity, if value is 0 or blank remove it from the cart instead
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity.getText().toString().compareTo("")==0){
                    cart.removeItem(position);
                }
                else{
                    int new_quant = Integer.parseInt(quantity.getText().toString());
                    if(new_quant==0){
                        cart.removeItem(position);
                    }
                    else {
                        cart.setQuantityOfItem(position, new_quant);
                    }
                }
                returnToCart();
            }
        });

        //remove item from cart
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.removeItem(position);
                returnToCart();
            }
        });
    }

    //change activity back to cart, do not allow returning to the item view
    void returnToCart(){
        Intent intent = new Intent(this, ActivityCart.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}