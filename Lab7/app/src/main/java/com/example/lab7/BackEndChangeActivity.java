package com.example.lab7;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BackEndChangeActivity extends AppCompatActivity {
    EditText editTextName;
    EditText editTextPrice;
    EditText editTextQuantity;
    Button buttonChange;
    Button buttonCancel;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_change);
        editTextName = findViewById(R.id.edit_text_name);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextPrice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, 2)});
        editTextQuantity = findViewById(R.id.edit_text_quantity);
        buttonChange = findViewById(R.id.btn_change);
        buttonCancel = findViewById(R.id.btn_cancel);

        dbHelper = new DBHelper(BackEndChangeActivity.this);
        database = dbHelper.getWritableDatabase();


        Intent i = getIntent();
        if (i.getIntExtra("change_id", 0) == 0) {
            buttonChange.setText(R.string.action_back_add);
            buttonChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editTextName.length() != 0 && editTextPrice.length() != 0 &&
                            editTextQuantity.length() != 0) {
                        database = dbHelper.getWritableDatabase();
                        database.execSQL("INSERT INTO " + DBHelper.TABLE_PRODUCTS +
                                " (" + DBHelper.KEY_NAME + ", " + DBHelper.KEY_PRICE + ", " +
                                DBHelper.KEY_QUANTITY + ") VALUES( \'" + editTextName.getText().toString()
                                + "\', " + Float.valueOf(editTextPrice.getText().toString()) + ", " +
                                Integer.valueOf(editTextQuantity.getText().toString())
                                + ");");
                        database.close();
                        Toast toast = Toast.makeText(BackEndChangeActivity.this,
                                "Товар добавлен", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,
                                0,
                                200);
                        toast.show();
                        Intent i = new Intent(BackEndChangeActivity.this, BackEndActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast toast = Toast.makeText(BackEndChangeActivity.this,
                                "Заполните все поля ввода", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,
                                0,
                                200);
                        toast.show();
                    }
                }
            });
        } else if (i.getIntExtra("change_id", 0) == 1) {
            buttonChange.setText(R.string.action_back_save);
            item = i.getParcelableExtra("item_front");
            editTextName.setText(item.getName());
            editTextPrice.setText(Float.toString(item.getPrice()));
            editTextQuantity.setText(Integer.toString(item.getQuantity()));
            buttonChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editTextName.length() != 0 && editTextPrice.length() != 0 &&
                            editTextQuantity.length() != 0) {
                        database = dbHelper.getWritableDatabase();
                        database.execSQL("UPDATE " + DBHelper.TABLE_PRODUCTS + " SET " +
                                DBHelper.KEY_NAME + "= \"" + editTextName.getText().toString() + "\", " +
                                DBHelper.KEY_PRICE + "=" + Float.parseFloat(editTextPrice.getText().toString()) + ", " +
                                DBHelper.KEY_QUANTITY + "=" +  Integer.parseInt(editTextQuantity.getText().toString()) + " WHERE "
                                + DBHelper.KEY_ID + "=" + item.getID() + ";");
                        database.close();
                        Toast toast = Toast.makeText(BackEndChangeActivity.this,
                                "Товар изменен", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,
                                0,
                                200);
                        toast.show();
                        Intent i = new Intent(BackEndChangeActivity.this, BackEndActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast toast = Toast.makeText(BackEndChangeActivity.this,
                                "Заполните все поля ввода", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,
                                0,
                                200);
                        toast.show();
                    }
                }
            });
        }
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BackEndChangeActivity.this, BackEndActivity.class);
                startActivity(i);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(BackEndChangeActivity.this, BackEndActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_back:
                Intent i = new Intent(BackEndChangeActivity.this, StoreFrontActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

class DecimalDigitsInputFilter implements InputFilter {
    private Pattern mPattern;
    DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = mPattern.matcher(dest);
        if (!matcher.matches())
            return "";
        return null;
    }
}