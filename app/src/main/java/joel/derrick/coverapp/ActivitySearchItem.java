package joel.derrick.coverapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by JD on 10/10/17.
 * Activity to set the parameters for search items from the database
 */

public class ActivitySearchItem extends ActivityNavBarListenerImplemented {
    Button submit;
    EditText item_name;
    EditText min_price;
    EditText max_price;
    Spinner order_spinner;
    StoreDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navBarOnCreate(R.layout.activity_search);

        db = new StoreDatabaseHelper(getApplicationContext());

        item_name = (EditText) findViewById(R.id.item_search_name);
        min_price = (EditText) findViewById(R.id.item_search_min);
        max_price = (EditText) findViewById(R.id.item_search_max);
        order_spinner = (Spinner)findViewById(R.id.order_by_dropdown);
        submit = (Button)findViewById(R.id.item_search_button);

        //get all the details of the search and query tje database
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double min, max;
                int order_value;
                StoreDatabaseHelper.ORDER_ITEM_BY order = StoreDatabaseHelper.ORDER_ITEM_BY.NO_ORDER;
                if (min_price.getText().toString().compareTo("") == 0) {
                    min = 0;
                } else {
                    min = Float.parseFloat(min_price.getText().toString());
                }
                if(max_price.getText().toString().compareTo("")==0){
                    max=Double.MAX_VALUE;
                }
                else{
                    max= Float.parseFloat(max_price.getText().toString());
                }
                order_value = order_spinner.getSelectedItemPosition();
                switch(order_value){
                    case 1:
                        order = StoreDatabaseHelper.ORDER_ITEM_BY.ALPHA_A_TO_Z;
                        break;
                    case 2:
                        order = StoreDatabaseHelper.ORDER_ITEM_BY.ALPHA_Z_TO_A;
                        break;
                    case 3:
                        order = StoreDatabaseHelper.ORDER_ITEM_BY.PRICE_LOW_TO_HIGH;
                        break;
                    case 4:
                        order = StoreDatabaseHelper.ORDER_ITEM_BY.PRICE_HIGH_TO_LOW;
                        break;
                }
                browseItems(db.getItemsList(item_name.getText().toString(),min,max,order));
            }
        });
    }
    //Switch to browse item activity and send array list of items to browse
    void browseItems(ArrayList<ItemModel> list){
        Intent intent = new Intent(this, ActivityBrowseItems.class);
        intent.putParcelableArrayListExtra("item_list", list);
        startActivity(intent);
    }
}
