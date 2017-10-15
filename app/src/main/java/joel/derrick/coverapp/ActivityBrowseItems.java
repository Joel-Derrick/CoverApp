package joel.derrick.coverapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by JD on 10/10/17.
 * Activity to browse a list of items from a given search
 */

public class ActivityBrowseItems extends ActivityNavBarListenerImplemented {
    ListView list_view;
    ArrayList<ItemModel> array_of_items;
    ItemFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navBarOnCreate(R.layout.activity_browse_item);

        Intent intent = getIntent();
        array_of_items = new ArrayList<ItemModel>();
        array_of_items = intent.getParcelableArrayListExtra("item_list");
        adapter = new ItemFragmentAdapter(this, array_of_items);
        list_view = (ListView)findViewById(R.id.browse_item_list_view);
        list_view.setAdapter(adapter);

        //add event when clicking on an entry in the list view
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                callItemPage(position);

            }
        });
    }
    //Switch to the item page activity and the item which to display
    public void callItemPage(int position){
        if (position >= 0 && position<array_of_items.size()) {
            Intent intent = new Intent(this, ActivityItemPage.class);
            intent.putExtra("item", array_of_items.get(position));
            startActivity(intent);
        }
    }
}
