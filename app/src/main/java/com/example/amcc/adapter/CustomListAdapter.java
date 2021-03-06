package com.example.amcc.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.amcc.R;

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the animal images
    private final Integer[] imageIDarray;

    //to store the list of countries
    private final String[] nameArray;

    //to store the list of countries
    private final String[] infoArray;

    public CustomListAdapter(Activity context, String[] nameArray, String[] infoArray, Integer[] imageIDarray) {
        super(context, R.layout.listview_row, nameArray);
        this.context=context;
        this.imageIDarray=imageIDarray;
        this.nameArray=nameArray;
        this.infoArray=infoArray;
    }

    @Override @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.listview_row, parent, false);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = rowView.findViewById(R.id.listRowtextViewTitleID);
        TextView infoTextField = rowView.findViewById(R.id.infoListTxtViewRowID);
        ImageView imageView = rowView.findViewById(R.id.listImgVID);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        infoTextField.setText(infoArray[position]);
        imageView.setImageResource(imageIDarray[position]);


        return rowView;
    }
}
