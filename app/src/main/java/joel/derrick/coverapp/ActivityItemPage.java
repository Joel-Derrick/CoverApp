package joel.derrick.coverapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * Created by JD on 10/10/17.
 * Behaviour for the item page, user can add a quantity of items to the cart
 */

public class ActivityItemPage extends ActivityNavBarListenerImplemented {
    private ItemModel item;
    TextView name, price, description;
    EditText quantity;
    Button add_to_cart;
    ShoppingCart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navBarOnCreate(R.layout.item_page_layout);

        cart = ShoppingCart.getInstance();

        Intent intent = getIntent();
        item = intent.getParcelableExtra("item");

        name=(TextView)findViewById(R.id.item_name_label);
        price=(TextView)findViewById(R.id.item_price_label);
        description=(TextView)findViewById(R.id.item_desc_body);
        quantity=(EditText)findViewById(R.id.item_page_quantity_value);
        add_to_cart=(Button)findViewById(R.id.update_quantity);

        name.setText(item.getName());
        price.setText(NumberFormat.getCurrencyInstance().format(item.getPrice()));
        description.setText(item.getDesc());

        //add the selected quantity of current item to the cart
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant;
                if(quantity.getText().toString().compareTo("")==0){
                    quant = 1;
                }
                else{
                    quant = Integer.parseInt(quantity.getText().toString());
                }
                cart.addItem(item,quant);
                makeToast(getResources().getString(R.string.added_to_cart));
            }
        });
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
