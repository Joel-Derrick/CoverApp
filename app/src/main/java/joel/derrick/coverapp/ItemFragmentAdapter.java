package joel.derrick.coverapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by JD on 06/10/17.
 * List adapter for browsing items in a search
 */

public class ItemFragmentAdapter extends ArrayAdapter<ItemModel> {

    public ItemFragmentAdapter(Context context, ArrayList<ItemModel> items)
    {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemModel item = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_fragment_layout, parent, false);
        }

        TextView item_name = convertView.findViewById(R.id.item_name_frag_label);
        TextView item_price = convertView.findViewById(R.id.item_price_frag_label);

        item_name.setText(item.getName());

        item_price.setText(NumberFormat.getCurrencyInstance().format(item.getPrice()));

        return convertView;
    }
}
