package com.example.budgetplanner2;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
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
import android.widget.Toast;

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

    /*private View.OnScrollChangeListener scrollChangeListener = new View.OnScrollChangeListener()
    {
        @Override
        public void onScrollChange(View view, int i, int i1, int i2, int i3)
        {
            int pos = amountList.getSelectedItemPosition();
            reasonList.setSelection(pos);
        }
    };*/

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
        amountBox.requestFocus();

        reasonBox = findViewById(R.id.ReasonBox);

        totalText = findViewById(R.id.TotalText);

        listAmounts = new ArrayList<String>();

        listReasons = new ArrayList<String>();

        arrayAdapterAmounts = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,listAmounts);
        amountList.setAdapter(arrayAdapterAmounts);

        arrayAdapterReasons = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,listReasons);
        reasonList.setAdapter(arrayAdapterReasons);

        amountList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent,  View view, int position, long id)
            {

                final String selectedItem = (String) amountList.getItemAtPosition(position);
                // final long selectedIndex = id;
                ItemSelecta.previousViewSelected = ItemSelecta.viewSelected;

                ItemSelecta.viewSelected = view;

                ItemSelecta.previousIDSelected = ItemSelecta.IDselected;

                ItemSelecta.IDselected = id;

                ItemSelecta.previousPos = ItemSelecta.pos;

                ItemSelecta.pos = position;

                View reasonView = getViewByPosition(position, reasonList);

                ItemSelecta.reasonViewSelected = reasonView;


                // Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_LONG);
                // toast.show();
                if (ItemSelecta.previousViewSelected != null)
                {
                    View previousReasonView = getViewByPosition(ItemSelecta.previousPos, reasonList);
                    ItemSelecta.previousViewSelected.setBackgroundColor(Color.TRANSPARENT);
                    previousReasonView.setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(0x330011FF);
                reasonView.setBackgroundColor(0x330011FF);
            }
        });
    }

    public View getViewByPosition(int position, ListView listView)
    {
        final int first = listView.getFirstVisiblePosition();
        final int last  = first + listView.getChildCount();

        if (position < first || position > last)
        {
            return listView.getAdapter().getView(position, null, listView);
        }
        else
        {
            return listView.getChildAt(position);
        }
    }

    public static class ItemSelecta
    {
        public static View previousViewSelected = null;
        public static View viewSelected;
        public static View reasonViewSelected;
        public static long previousIDSelected;
        public static long IDselected;
        public static int previousPos;
        public static int pos = -1;
        public static int reasonPos;
        public static boolean deleted;
    }

    @Override
    public void onClick(View v)
    {
        boolean eval;
        switch (v.getId())
        {
            case R.id.IncomeButton:

                eval = EvaluateTextBoxes();
                if (eval)
                {
                    AddIncome();
                    Update();

                }
                else
                {
                    ShowError("Please enter an amount and reason for income :)");
                }
                break;
            case R.id.OutgoingsButton:
                eval = EvaluateTextBoxes();
                if (eval)
                {
                    AddOutgoings();
                    Update();

                }
                else
                {
                    ShowError("Please enter a cost & reason for expense :)");
                }
                break;

            case R.id.DeleteButton:
                if (ItemSelecta.pos !=-1)
                {
                    DeleteItem();
                    Update();
                }
                break;
                //
        }
    }

    public void DeleteItem()
    {
        listAmounts.remove(ItemSelecta.pos);
        listReasons.remove(ItemSelecta.pos);
        ItemSelecta.pos = -1;
        ItemSelecta.viewSelected = null;
        ItemSelecta.previousViewSelected = null;
    }

    public void AddIncome()
    {
        String strAmount;
        String reason = "";

        strAmount = amountBox.getText().toString();
        reason = reasonBox.getText().toString();

        arrayAdapterAmounts.add(strAmount);
        arrayAdapterReasons.add(reason);
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
        amountBox.getText().clear();
        reasonBox.getText().clear();
        amountBox.requestFocus();
        amountList.setAdapter(arrayAdapterAmounts);
        reasonList.setAdapter(arrayAdapterReasons);
        arrayAdapterAmounts.notifyDataSetChanged();
        arrayAdapterReasons.notifyDataSetChanged();
        totalText.setText(GetTotal());
    }

    public boolean EvaluateTextBoxes()
    {
        boolean result = false;
        String amttxt = amountBox.getText().toString();
        String rsntxt = reasonBox.getText().toString();
        if (TextUtils.isEmpty(amttxt) || TextUtils.isEmpty(rsntxt))
        {
            result = false;
        }
        else
        {
            result = true;
        }
        return result;
    }

    public void ShowError(String text)
    {
        Toast toast = Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT);
        toast.show();
    }

}