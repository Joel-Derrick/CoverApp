package joel.derrick.coverapp;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

/**
 * Created by JD on 06/10/17.
 * Activity to get in contact with me
 */

public class ActivityContact extends ActivityNavBarListenerImplemented {
    Button button_email, button_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navBarOnCreate(R.layout.contact);

        //Send me an email
        button_email =(Button)findViewById(R.id.button_email);
        button_email.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
                //only partial contact information due to privacy concerns
                String[] address = {"joel.derrick@"};
                Resources res = getResources();
                email.putExtra(Intent.EXTRA_EMAIL, address );
                email.putExtra(Intent.EXTRA_SUBJECT, res.getString(R.string.email_subject));
                email.putExtra(Intent.EXTRA_TEXT, res.getString(R.string.email_content));
                if (email.resolveActivity(getPackageManager()) != null) {
                    startActivity(email);
                }
            }
        });

        //call me
        button_call=(Button)findViewById(R.id.button_call);
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("tel:613400"));//only partial contact information due to privacy concerns
                startActivity(i);
            }
        });
    }
}