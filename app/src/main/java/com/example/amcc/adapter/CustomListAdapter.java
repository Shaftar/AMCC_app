package com.example.amcc.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.example.amcc.R;
import com.example.amcc.model.FunctionsList;

import java.util.ArrayList;
import java.util.List;


public class CustomListAdapter extends ArrayAdapter<FunctionsList> {

    //to reference the Activity
    private final Activity context;

    List<FunctionsList> functionsLists;

    public CustomListAdapter(Activity context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<FunctionsList> list) {
        super(context, R.layout.listview_row, list);
        this.context = context;
        this.functionsLists = list;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        @SuppressLint("ViewHolder") View rowView;
        LayoutInflater inflater;
        if (convertView == null) {
            inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.listview_row, parent, false);
        } else {
            rowView = convertView;
        }
        FunctionsList currentFunction = functionsLists.get(position);
        //this code gets references to objects in the listView_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.listRowtextViewTitleID);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.infoListTxtViewRowID);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.listImgVID);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(currentFunction.getName());
        infoTextField.setText(currentFunction.getInfo());
        imageView.setImageResource(currentFunction.getImage());


        return rowView;
    }
}
