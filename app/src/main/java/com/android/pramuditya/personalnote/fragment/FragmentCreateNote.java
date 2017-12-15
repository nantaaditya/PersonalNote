package com.android.pramuditya.personalnote.fragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.pramuditya.personalnote.AlarmReceiver;
import com.android.pramuditya.personalnote.R;
import com.android.pramuditya.personalnote.Util.SpinnerAdapter;
import com.android.pramuditya.personalnote.db.DBHelper;
import com.android.pramuditya.personalnote.model.Note;
import com.android.pramuditya.personalnote.model.Tag;
import com.mikepenz.iconics.view.IconicsButton;

import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 18/11/2017.
 */

public class FragmentCreateNote extends Fragment implements View.OnClickListener {
    private View view;
    private EditText etTitle, etDate, etTime, etNote;
    private Button bSave;
    private Calendar currentCalendar = Calendar.getInstance();
    private Calendar selectedCalendar = Calendar.getInstance();
    private Date date = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int day, month, year, hour, minute;
    private Spinner sTag;
    private ArrayList<Tag> tags;
    private SpinnerAdapter spinnerAdapter;
    //DB
    private DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view = (View) inflater.inflate(R.layout.fragment_create, container, false);
        this.initializeElement();
        return this.view;
    }

    private void initializeElement() {
        this.tags = new ArrayList<>();
        this.etDate = (EditText) this.view.findViewById(R.id.et_date);
        this.etDate.setInputType(InputType.TYPE_NULL);
        this.etTime = (EditText) this.view.findViewById(R.id.et_time);
        this.etTime.setInputType(InputType.TYPE_NULL);
        this.etTitle = (EditText) this.view.findViewById(R.id.et_title);
        this.sTag = (Spinner) this.view.findViewById(R.id.s_tag);
        this.etNote = (EditText) this.view.findViewById(R.id.et_note);

        this.bSave = (Button) this.view.findViewById(R.id.b_save);
        this.bSave.setOnClickListener(this);

        this.etDate.setOnClickListener(this);
        this.etTime.setOnClickListener(this);
        this.dbHelper = new DBHelper(getActivity());
        this.spinnerAdapter = new SpinnerAdapter(getActivity(), R.layout.spinner_item, tags);
        try {
            tags.addAll(this.dbHelper.getAll(Tag.class));
            this.sTag.setAdapter(this.spinnerAdapter);
        } catch (SQLException e) {
            Log.e("DB", e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        if (view == etDate) {
            this.day = currentCalendar.get(Calendar.DAY_OF_MONTH);
            this.month = currentCalendar.get(Calendar.MONTH);
            this.year = currentCalendar.get(Calendar.YEAR);

            this.datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            selectedCalendar.set(year, monthOfYear, dayOfMonth);
                            etDate.setText(dateFormat.format(selectedCalendar.getTime()));
                        }
                    }, this.year, this.month, this.day);
            datePickerDialog.show();
        } else if (view == etTime) {
            this.hour = currentCalendar.get(Calendar.HOUR_OF_DAY);
            this.minute = currentCalendar.get(Calendar.MINUTE);
            this.timePickerDialog = new TimePickerDialog(getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            date.setHours(hour);
                            date.setMinutes(minute);
                            etTime.setText(timeFormat.format(date));
                        }
                    }, this.hour, this.minute, true);
            timePickerDialog.show();
        } else if (view == bSave) {
            final Tag tag;
            try {
                tag = this.dbHelper.findTagByName(Tag.class, this.sTag.getSelectedItem().toString());
                String title = this.etTitle.getText().toString();
                String isi = this.etNote.getText().toString();
                Date waktu = selectedCalendar.getTime();
                waktu.setHours(date.getHours());
                waktu.setMinutes(date.getMinutes());
                Note note = new Note(title, waktu, tag, isi);
                Log.d("test", note.getTitle());
                this.dbHelper.createOrUpdate(note);
                this.scheduleAlarm(waktu, note);
            } catch (Exception e) {
                Log.d("", e.getMessage());
            }

        }
    }

    private void scheduleAlarm(Date time, Note note) {
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("note", note);
        PendingIntent pending = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, time.getTime(), pending);
    }

    private void cancelAlarm() {
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pending = PendingIntent.getBroadcast(getActivity(), 0, new Intent(getActivity(), AlarmReceiver.class), 0);
        manager.cancel(pending);
    }
}
