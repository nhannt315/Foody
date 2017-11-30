package nhannt.foody.screen.addplace;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

import nhannt.foody.R;

public class AddPlaceActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private Button mBtnOpenTime, mBtnCloseTime;
    private String mOpenTime = "8:00", mCloseTime = "18:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        setupToolbar();
        initViews();
        initEvents();
    }

    private void initEvents() {
        mBtnCloseTime.setOnClickListener(this);
        mBtnOpenTime.setOnClickListener(this);
    }

    private void initViews() {
        mBtnOpenTime = findViewById(R.id.btn_open_time);
        mBtnCloseTime = findViewById(R.id.btn_close_time);
        mBtnOpenTime.setText(getString(R.string.open_time, mOpenTime));
        mBtnCloseTime.setText(getString(R.string.close_time, mCloseTime));
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_time:
                showDateTimePickerDialog(true);
                break;
            case R.id.btn_close_time:
                showDateTimePickerDialog(false);
                break;
        }
    }

    private void showDateTimePickerDialog(final boolean isOpenTime) {
        int hour;
        if (isOpenTime)
            hour = 8;
        else
            hour = 18;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (isOpenTime) {
                        mOpenTime = hourOfDay + ":" + minute;
                        mBtnOpenTime.setText(getString(R.string.open_time, mOpenTime));
                    } else {
                        mCloseTime = hourOfDay + ":" + minute;
                        mBtnCloseTime.setText(getString(R.string.close_time, mCloseTime));
                    }
                }
            }, hour, 0, true);
        timePickerDialog.show();
    }
}
