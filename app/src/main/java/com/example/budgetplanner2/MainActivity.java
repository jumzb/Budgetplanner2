package com.example.budgetplanner2;
import android.os.Bundle;
import android.view.MotionEvent;
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
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button incomeButton;
    private Button outgoingsButton;
    public ListView amountList;
    public ListView reasonList;
    public EditText amountBox;
    public EditText reasonBox;
    public TextView totalText;
    public List listAmounts = new ArrayList();
    public List listReasons = new ArrayList();
    ArrayAdapter<String> arrayAdapterAmounts;
    ArrayAdapter<String> arrayAdapterReasons;

    private View.OnScrollChangeListener scrollChangeListener = new View.OnScrollChangeListener()
    {
        @Override
        public void onScrollChange(View view, int i, int i1, int i2, int i3)
        {
            int pos = amountList.getSelectedItemPosition();
            reasonList.setSelection(pos);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button outgoingsButton = findViewById(R.id.OutgoingsButton);
        outgoingsButton.setOnClickListener(this);

        final Button incomeButton = findViewById(R.id.IncomeButton);
        incomeButton.setOnClickListener(this);

        final Button deleteButton = findViewById(R.id.DeleteButton);
        deleteButton.setOnClickListener(this);

        amountList = findViewById(R.id.AmountList);

        reasonList = findViewById(R.id.ReasonList);

        amountBox = findViewById(R.id.AmountBox);

        reasonBox = findViewById(R.id.ReasonBox);

        totalText = findViewById(R.id.TotalText);

        listAmounts = new ArrayList<String>();

        listReasons = new ArrayList<String>();

        arrayAdapterAmounts = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,listAmounts);

        arrayAdapterReasons = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,listReasons);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.IncomeButton:
                AddIncome();
                break;
            case R.id.OutgoingsButton:
                AddOutgoings();
                break;
            /*
            case R.id.DeleteButton:
                DeleteItem();
            */

        }
    }

    public void AddIncome()
    {
        String strAmount;
        String reason = "";

        strAmount = amountBox.getText().toString();
        reason = reasonBox.getText().toString();

        arrayAdapterAmounts.add(strAmount);
        arrayAdapterReasons.add(reason);

        Update();
    }
    public void AddOutgoings()
    {
        String strAmount;
        String reason;

        strAmount = amountBox.getText().toString();
        strAmount = "-" + strAmount;
        reason = reasonBox.getText().toString();

        arrayAdapterAmounts.add(strAmount);
        arrayAdapterReasons.add(reason);

        Update();

    }

    /*public void DeleteItem()
    {
        int selectedItem = amountList.getSelectedItemPosition();
        amountList.removeViewAt(selectedItem);
        reasonList.removeViewAt(selectedItem);
        Update();
    }*/

    public String GetTotal()
    {
        double amount = 0;
        double total = 0;
        String strAmount = "";
        String strTotal = "";
        for (int i = 0; i < listAmounts.size(); i++ )
        {
            strAmount = amountList.getItemAtPosition(i).toString();
            amount = Double.parseDouble(strAmount);
            total += amount;
        }
        strTotal = Double.toString(total);
        return strTotal;
    }

    public void Update()
    {
        amountList.setAdapter(arrayAdapterAmounts);
        reasonList.setAdapter(arrayAdapterReasons);
        arrayAdapterAmounts.notifyDataSetChanged();
        arrayAdapterReasons.notifyDataSetChanged();
        totalText.setText(GetTotal());
    }
}