package com.rafa.rafaandroidtask.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.rafa.rafaandroidtask.R;
import com.rafa.rafaandroidtask.util.Prefs;

/**
 * Created by rafaelgontijo on 3/23/16.
 */
public class SettingsActivity extends Activity {

    private Spinner windowOptions;
    private Spinner sortOptions;

    private Button aboutButton;
    private Button cancelButton;
    private Button saveButton;

    private Switch showViralSwitch;

    private ArrayAdapter<CharSequence> sortAdapter;
    private ArrayAdapter<CharSequence> windowAdapter;

    private RadioButton listRadioButton;
    private RadioButton gridRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        windowOptions = (Spinner) findViewById(R.id.window_spinner);
        windowAdapter = ArrayAdapter.createFromResource(this,
                R.array.window_options, android.R.layout.simple_spinner_item);
        windowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        windowOptions.setAdapter(windowAdapter);

        sortOptions = (Spinner) findViewById(R.id.sort_spinner);
        sortAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortOptions.setAdapter(sortAdapter);

        showViralSwitch = (Switch) findViewById(R.id.show_viral_switch);

        listRadioButton = (RadioButton) findViewById(R.id.radio_button_list_mode);
        gridRadioButton = (RadioButton) findViewById(R.id.radio_button_grid_mode);

        aboutButton = (Button) findViewById(R.id.about_button);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: show about screen
            }
        });

        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentState();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        getCurrenState();
    }

    private void getCurrenState() {

        String window = Prefs.getWindow(this);
        int selectedWindowPosition = windowAdapter.getPosition(window);
        windowOptions.setSelection(selectedWindowPosition);

        String sort = Prefs.getSort(this);
        int selectedSortPosition = sortAdapter.getPosition(sort);
        sortOptions.setSelection(selectedSortPosition);

        showViralSwitch.setChecked(Prefs.getShowViral(this));

        int viewMode = Prefs.getViewMode(this);
        listRadioButton.setChecked(viewMode == Prefs.VIEW_MODE_LIST);
        gridRadioButton.setChecked(viewMode == Prefs.VIEW_MODE_GRID);

    }

    private void saveCurrentState() {
        String sort = (String) sortOptions.getSelectedItem();
        String window = (String) windowOptions.getSelectedItem();
        boolean showViral = showViralSwitch.isChecked();

        int viewMode = listRadioButton.isChecked() ? Prefs.VIEW_MODE_LIST : Prefs.VIEW_MODE_GRID;

        Prefs.saveCurrentState(this, window, sort, showViral, viewMode);
    }
}
