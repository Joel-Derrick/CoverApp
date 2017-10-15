package joel.derrick.coverapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by JD on 5/10/17.
 * Activity to get user's name and proceed to main section of the app
 */

public class ActivityLogin extends AppCompatActivity {
    Button login_button;
    boolean second_attempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login_button=(Button)findViewById(R.id.login_submit_button);
        second_attempt = false;
        //define interactive widget's behaviours
        //login event
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name_field = (EditText)findViewById(R.id.login_name);
                String name = name_field.getText().toString();
                //contact name is know for the job application, but others can try out the app too
                if(name.compareTo("Kayla")==0 || second_attempt){
                    if(name.isEmpty()){
                        name = getResources().getString(R.string.default_name);
                    }
                    login(name);
                }
                else{
                    makeToast(getResources().getString(R.string.expected_name));
                    second_attempt = true;
                }
            }
        });
    }

    private void makeToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    //go to the about activity, store the user's name for later use
    private void login(String name){
        Intent myIntent = new Intent(this, ActivityAbout.class);
        ShoppingCart.getInstance().setUserName(name);
        //initialize database if it is empty(i.e from first run of the app)
        StoreDatabaseHelper db = new StoreDatabaseHelper(getApplicationContext());
        if(db.isEmpty()){
            db.reinitialize();
        }
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myIntent);
    }
}