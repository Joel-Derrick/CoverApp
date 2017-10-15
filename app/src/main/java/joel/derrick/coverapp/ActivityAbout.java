package joel.derrick.coverapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JD on 13/10/17.
 * Navigate pages with information about me
 */

public class ActivityAbout extends ActivityNavBarListenerImplemented {
    Button last, next;
    TextView page_num_text;
    int page_num;
    private FragmentManager fragmentManager = getFragmentManager();
    private FragmentTransaction fragmentTransaction;

    private ArrayList<MutableFragment> fragments = new ArrayList<MutableFragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navBarOnCreate(R.layout.activity_about_me);

        //set up the fragments
        MutableFragment frag = new MutableFragment();
        frag.setLayoutId(R.layout.about_fragment_1);
        fragments.add(frag);
        frag = new MutableFragment();
        frag.setLayoutId(R.layout.about_fragment_2);
        fragments.add(frag);
        frag = new MutableFragment();
        frag.setLayoutId(R.layout.about_fragment_3);
        fragments.add(frag);

        last=(Button)findViewById(R.id.about_last);
        next=(Button)findViewById(R.id.about_next);
        page_num_text =(TextView) findViewById(R.id.about_page_num);

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next.setEnabled(true);
                setPage(page_num-1);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last.setEnabled(true);
                setPage(page_num+1);
            }
        });

        //set the fragment
        page_num=1;
        setPage(page_num);
    }

    //set the new fragment page, note page [1,n], not [0,(n-1)]
    void setPage(int i){
        if(i>0 &i<=fragments.size()) {
            page_num = i;
            if(page_num==1) {
                last.setEnabled(false);//can't go below page 1
            }
            else if (page_num==fragments.size()){
                next.setEnabled(false);//can't go above max pages
            }
            //change page fragment
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.about_me_page_fragment, fragments.get(i-1));
            fragmentTransaction.commit();
        }
        page_num_text.setText(Integer.valueOf(page_num).toString());
    }
}
