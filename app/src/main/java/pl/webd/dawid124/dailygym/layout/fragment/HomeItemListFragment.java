package pl.webd.dawid124.dailygym.layout.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import pl.webd.dawid124.dailygym.R;

public class HomeItemListFragment extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public HomeItemListFragment(Activity context, String[] web, Integer[] imageId) {
        super(context, R.layout.training_item_list_fragment, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R .layout.training_item_list_fragment, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.exerciseType);


        txtTitle.setText(web[position]);
        return rowView;
    }
}