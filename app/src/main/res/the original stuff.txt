package com.example.budgetplanner;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addButton;
    private Button subtractButton;
    private Button removeButton;
    public ListView budgetList;
    public EditText amountBox;
    public TextView totalText;
    public List list = new ArrayList();
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button subtractButton = findViewById(R.id.SubtractButton);
        subtractButton.setOnClickListener(this);
        final Button addButton = findViewById(R.id.AddButton);
        addButton.setOnClickListener(this);
        final Button removeButton = findViewById(R.id.RemoveButton);
        removeButton.setOnClickListener(this);
        budgetList = findViewById(R.id.BudgetList);
        amountBox = findViewById(R.id.AmountBox);
        totalText = findViewById(R.id.TotalText);
        list = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
    }

        @Override
        public void onClick(View v)
        {
            switch (v.getId()) {
                case R.id.AddButton:
                    AddMethod();
                    break;
                case R.id.SubtractButton:
                    SubtractMethod();
                    break;
                case R.id.RemoveButton:
                    RemoveMethod();
            }
        }

            public void AddMethod() {
                String strAmount;
                String reason;
                strAmount = amountBox.getText().toString();

                arrayAdapter.add(strAmount);
                budgetList.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                totalText.setText(GetTotal());
            }
            public void SubtractMethod() {
                String strAmount;
                String reason;
                strAmount = amountBox.getText().toString();
                strAmount = "-" + strAmount;
                arrayAdapter.add(strAmount);
                budgetList.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                totalText.setText(GetTotal());
            }

            public void RemoveMethod() {
                int selectedItem = budgetList.getSelectedItemPosition();
                budgetList.removeViewAt(selectedItem);
            }
            public String GetTotal() {
                double amount = 0;
                double total = 0;
                String strAmount = "";
                String strTotal = "";
                for (int i = 0; i < list.size(); i++ )
                {
                    strAmount = budgetList.getItemAtPosition(i).toString();
                    amount = Double.parseDouble(strAmount);
                    total += amount;
                }
                strTotal = Double.toString(total);
                return strTotal;
            }
    }
