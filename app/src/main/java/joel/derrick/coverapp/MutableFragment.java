package joel.derrick.coverapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Warpath on 13/10/17.
 * Reusable fragment for the About Me pages
 */

public class MutableFragment extends Fragment {
    int layout_id;
    //changing fragments likes to leave part of the initial page, starting with a blank layout fixes this
    public MutableFragment(){
        layout_id=R.layout.blank;
    }

    public void setLayoutId(int layout_id) {
        this.layout_id = layout_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Place the layout onto the given container
        return inflater.inflate(layout_id, container, false);
    }
}