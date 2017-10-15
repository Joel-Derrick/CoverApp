package joel.derrick.coverapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by JD on 07/10/17.
 * Activity to add items to the database
 */

public class ActivityAddItem extends ActivityNavBarListenerImplemented {

    EditText name,desc,price,weight;
    Button submit, reset;
    StoreDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navBarOnCreate(R.layout.add_item);

        db = new StoreDatabaseHelper(getApplicationContext());

        submit =(Button)findViewById(R.id.database_submit_button);
        reset =(Button)findViewById(R.id.databse_reset_button);
        name=(EditText)findViewById(R.id.add_item_name);
        desc=(EditText)findViewById(R.id.add_item_desc);
        price=(EditText)findViewById(R.id.add_item_price);
        weight=(EditText)findViewById(R.id.add_item_weight);

        //define interactive widget's behaviours
        //submit item to the database
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate that all fields have been entered
                if( name.getText().toString().compareTo("")==0 ||
                        desc.getText().toString().compareTo("")==0 ||
                        price.getText().toString().compareTo("")==0 ||
                        weight.getText().toString().compareTo("")==0){
                    makeToast("Please fill in all the fields");
                }
                else {
                    ItemModel item = new ItemModel(-1, name.getText().toString(),
                                    desc.getText().toString(),
                                    Double.parseDouble(price.getText().toString()),
                                    Double.parseDouble(weight.getText().toString()));
                    db.addEntry(item);
                }
            }
        });

        //reset event
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.reinitialize();
            }
        });
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
