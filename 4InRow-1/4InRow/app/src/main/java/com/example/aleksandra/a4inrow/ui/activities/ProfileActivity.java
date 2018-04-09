package com.example.aleksandra.a4inrow.ui.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.aleksandra.a4inrow.R;
import com.example.aleksandra.a4inrow.models.PlayerModel;
import com.example.aleksandra.a4inrow.presenters.ProfilePresenter;
import com.example.aleksandra.a4inrow.ui.fragments.DatePickerFragment;
import com.example.aleksandra.a4inrow.ui.fragments.DatePickerFragment.IDatePickerView;
import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.utils.Utils;
import com.example.aleksandra.a4inrow.views.IProfileView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import static com.example.aleksandra.a4inrow.utils.SharedPreferencesUtils.getInstance;

public class ProfileActivity extends BaseActivity<IProfileView, ProfilePresenter>
        implements IProfileView, View.OnClickListener, IDatePickerView {

    private static PlayerModel user = null;
    private EditText edFirstName, edLastName, edEmail, dateOfBirth;
    private CheckBox active;
    private RadioButton male, female;
    private ImageView circularImageView;
    private MenuItem menuOptionItem;
    private boolean editMode;
    private boolean visibleMenuItem;
    private static int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViews();
        setListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getExtras() != null) {
            userId = getIntent().getIntExtra(Constants.OBJECT, getInstance().getPrefInt(Constants.MY_ID));
        }
        try {
            getPresenter().getUser(userId);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (user != null) {
            if(userId == getInstance().getPrefInt(Constants.MY_ID)){
                visibleMenuItem = true;
            }
            visibleMenuItem = false;
            showUser(user);
        } else {
            dismissProgressDialog();
            visibleMenuItem = false;
            showToast(getString(R.string.something_wrong));
            user = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        user = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        user = null;
    }

    private void findViews() {
        edFirstName = (EditText) findViewById(R.id.edit_first_name);
        edLastName = (EditText) findViewById(R.id.edit_last_name);
        edEmail = (EditText) findViewById(R.id.edit_email);
        active = (CheckBox) findViewById(R.id.check_box_active);
        male = (RadioButton) findViewById(R.id.radio_button_male);
        female = (RadioButton) findViewById(R.id.radio_button_female);
        circularImageView = (ImageView) findViewById(R.id.profile_picture);
        dateOfBirth = (EditText) findViewById(R.id.edit_DOB);
    }

    private void setListeners() {
        dateOfBirth.setOnClickListener(this);
        dateOfBirth.setClickable(false);
    }

    @NonNull
    @Override
    public ProfilePresenter createPresenter() {
        return new ProfilePresenter();
    }

    public void showUser(PlayerModel data) {
        if (data != null) {
            edFirstName.setText(data.getFirst_name());
            edLastName.setText(data.getLast_name());
            edEmail.setText(data.getEmail());
            if (data.getActive().equalsIgnoreCase("A")) {
                active.setChecked(true);
            }

            Date date;
            try {
                date = (new SimpleDateFormat("dd/MM/yyyy",
                        java.util.Locale.getDefault())).parse(data.getDate_of_birth());
                updateDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (data.getGender().equals(getString(R.string.f))) {
                female.setChecked(true);
            } else if (data.getGender().equals(getString(R.string.m))) {
                male.setChecked(true);
            }

            if (getInstance().getPrefString(Constants.IMAGE_URL).equalsIgnoreCase("no photo")) {
                Utils.loadImageWithGlideCircle(Uri
                        .parse("https://www.lifescitrc.org/images/noprofile.gif"), circularImageView);
            } else {
                Utils.loadImageWithGlideCircle(Uri.parse(getInstance()
                        .getPrefString(Constants.IMAGE_URL)), circularImageView);
            }
        } else {
            edFirstName.setText("");
            edLastName.setText("");
            edEmail.setText("");
            active.setChecked(false);
            Date date;
            try {
                date = (new SimpleDateFormat("dd/MM/yyyy",
                        java.util.Locale.getDefault())).parse("01/01/2000");
                updateDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            female.setChecked(false);
            male.setChecked(false);
        }
    }

    @Override
    public void setUser(PlayerModel data) {
        ProfileActivity.user = data;
    }

    @Override
    public void onClick(View v) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(this.getSupportFragmentManager(), Constants.DATE_PICKER);
    }

    @Override
    public void updateDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        dateOfBirth.setText(dateFormat.format(date));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile, menu);
        menuOptionItem = menu.findItem(R.id.action_edit_profile);
        menuOptionItem.setVisible(visibleMenuItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_profile) {
            if (!editMode) {
                editMode = true;
                menuOptionItem.setIcon(R.drawable.confirm);
                editableProfile(true);
            } else {
                editMode = false;
                menuOptionItem.setIcon(R.drawable.edit);
                editableProfile(false);
                this.changeUser();
                try {
                    getPresenter().updateUser(user);
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeUser() {
        user.setFirst_name(edFirstName.getText().toString());
        user.setLast_name(edLastName.getText().toString());
        user.setDate_of_birth(dateOfBirth.getText().toString());

        if (female.isChecked()) {
            user.setGender(getString(R.string.f));
        } else if (male.isChecked()) {
            user.setGender(getString(R.string.m));
        }
    }

    public void editableProfile(boolean mode) {
        edFirstName.setFocusable(mode);
        edFirstName.setFocusableInTouchMode(mode);
        edFirstName.setClickable(mode);
        edFirstName.setLongClickable(mode);
        edFirstName.setCursorVisible(mode);

        if (mode) {
            edFirstName.requestFocus();
            int textLength = edFirstName.getText().length();
            edFirstName.setSelection(textLength, textLength);
            this.showKeyboard();
        } else {
            this.closeKeyboard(active);
        }

        edLastName.setFocusable(mode);
        edLastName.setFocusableInTouchMode(mode);
        edLastName.setClickable(mode);
        edLastName.setLongClickable(mode);
        edLastName.setCursorVisible(mode);

        female.setClickable(mode);
        male.setClickable(mode);
        dateOfBirth.setClickable(mode);
    }
}

