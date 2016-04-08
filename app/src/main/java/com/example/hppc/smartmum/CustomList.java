package com.example.hppc.smartmum;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by HP PC on 4/7/2016.
 */
public class CustomList extends ArrayAdapter<String> {

    private String[] ids;
    private String[] names;
    private String[] admin_id;
    private String [] time;
    private Activity context;
    public CustomList(Activity context, String[] ids, String[] names, String[] admin_id, String[] time) {
        super(context, R.layout.activity_list_view, ids);
        this.context = context;
        this.ids = ids;
        this.names = names;
        this.admin_id = admin_id;
        this.time = time;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewGroup = inflater.inflate(R.layout.activity_list_view, null, true);
        TextView textViewId = (TextView) listViewGroup.findViewById(R.id.textViewId);
        TextView textViewName = (TextView) listViewGroup.findViewById(R.id.textViewName);
        TextView textViewAdminId = (TextView) listViewGroup.findViewById(R.id.textViewEmail);
        TextView textViewTime = (TextView) listViewGroup.findViewById(R.id.textViewTime);
        //if(ids>= String.valueOf(2))
        textViewId.setText(ids[position]);
        textViewName.setText(names[position]);
        textViewAdminId.setText(admin_id[position]);
        textViewTime.setText(time[position]);

        textViewId.setTextColor(Color.MAGENTA);
        textViewName.setTextColor(Color.BLUE);
        textViewTime.setTextColor(Color.RED);

        return listViewGroup;
    }

}
