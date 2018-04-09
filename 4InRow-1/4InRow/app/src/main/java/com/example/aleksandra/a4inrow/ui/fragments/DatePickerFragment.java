package com.example.aleksandra.a4inrow.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.example.aleksandra.a4inrow.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by AleksandraStanojevic on 7/31/2017
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    IDatePickerView listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            listener = (IDatePickerView) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + getString(R.string.error));
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        Date date = c.getTime();

        listener.updateDate(date);
    }

    public interface IDatePickerView {
        void updateDate(Date date);
    }
}
