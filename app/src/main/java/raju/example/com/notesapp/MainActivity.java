package raju.example.com.notesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
  ListView listView;
   static ArrayList<String> arrayList = new ArrayList<>();
   static ArrayAdapter adapter;
    SharedPreferences preferences;
    static Set<String> set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);


      preferences = this.getSharedPreferences("raju.example.com.notesapp", Context.MODE_PRIVATE);

        set = preferences.getStringSet("notes", null);

        arrayList.clear();
        if (set != null) {
             arrayList.addAll(set);
        }
        else {
            arrayList.add("example string");
            set = new HashSet<>();
            set.addAll(arrayList);
            preferences.edit().putStringSet("notes", set).apply();

        }


          adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), EditActivity.class);
                i.putExtra("editIdNo", position);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Danger")
                        .setMessage("Are You Sure?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                arrayList.remove(position);

                                SharedPreferences preferences = MainActivity.this.getSharedPreferences("raju.example.com.notesapp", Context.MODE_PRIVATE);

                                if (set == null){
                                    set = new HashSet<>();
                                }
                                else {
                                    set.clear();

                                }
                                set.addAll(MainActivity.arrayList);
                                preferences.edit().remove("notes").apply();
                                preferences.edit().putStringSet("notes", set).apply();

//                                Intent i = new Intent(getApplicationContext(), EditActivity.class);
//                                i.putExtra("editIdNo", arrayList.size() - 1);
//                                startActivity(i);
                                adapter.notifyDataSetChanged();




                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();


              //  Toast.makeText(MainActivity.this, "Long Clicked!!!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.addMenuItem){
            arrayList.add("");
            SharedPreferences preferences = this.getSharedPreferences("raju.example.com.notesapp", Context.MODE_PRIVATE);

            if (set == null){
                set = new HashSet<>();
            }
            else {
                set.clear();

            }
                 set.addAll(MainActivity.arrayList);
            preferences.edit().remove("notes").apply();
            preferences.edit().putStringSet("notes", set).apply();

            Intent i = new Intent(getApplicationContext(), EditActivity.class);
            i.putExtra("editIdNo", arrayList.size() - 1);
            startActivity(i);
            adapter.notifyDataSetChanged();

           // Toast.makeText(getApplicationContext(), "Menu Clicked!!!!!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
