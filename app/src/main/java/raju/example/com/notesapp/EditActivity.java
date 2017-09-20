package raju.example.com.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class EditActivity extends AppCompatActivity implements TextWatcher {
   EditText et;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        et = (EditText) findViewById(R.id.editText);

        Intent i = getIntent();
        position = i.getIntExtra("editIdNo", -1);

        if (position != -1){
            et.setText(MainActivity.arrayList.get(position));
        }

        et.addTextChangedListener(this);



    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
           MainActivity.arrayList.set(position, String.valueOf(s));
           MainActivity.adapter.notifyDataSetChanged();

        SharedPreferences preferences = this.getSharedPreferences("raju.example.com.notesapp", Context.MODE_PRIVATE);

        if (MainActivity.set == null){
            MainActivity.set = new HashSet<>();
        }
        else {
            MainActivity.set.clear();

        }
        MainActivity.set.addAll(MainActivity.arrayList);
        preferences.edit().remove("notes").apply();
        preferences.edit().putStringSet("notes", MainActivity.set).apply();




    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
