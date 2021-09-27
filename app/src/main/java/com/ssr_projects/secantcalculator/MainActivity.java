package com.ssr_projects.secantcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText equationBox, variableOneBox, variableTwoBox, noOfIteraionBox;
    ListView listView;
    Button submitButton;

    String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Snackbar.make(getWindow().getDecorView().getRootView(), "This app was made by Soorya S Rajan :)", Snackbar.LENGTH_LONG).show();

        equationBox = findViewById(R.id.equation);
        variableOneBox = findViewById(R.id.x0);
        variableTwoBox = findViewById(R.id.x1);
        submitButton = findViewById(R.id.submit);
        noOfIteraionBox = findViewById(R.id.iterations);
        listView = findViewById(R.id.list_view);

        final ArrayList<String> iterationItems = new ArrayList<String>();
        final ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, iterationItems);
        listView.setAdapter(itemsAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iterationItems.clear();
                String equationString = equationBox.getText().toString();
                String numberOne = variableOneBox.getText().toString(); //k-1
                String numberTwo = variableTwoBox.getText().toString(); //k
                String iterations = noOfIteraionBox.getText().toString();

                if(equationString.isEmpty() || numberOne.isEmpty() || numberTwo.isEmpty() || iterations.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter all field(s)", Toast.LENGTH_SHORT).show();
                    return;
                }

                int n = Integer.parseInt(iterations);

                Object resultOfIteration = null;


                for(int i = 0; i < n; i++){

                    try{
                        String fOneX = equationString.replaceAll("x", numberTwo);
                        fOneX = "(" + fOneX + ")";
                        String fTwoX = equationString.replaceAll("x", numberOne);
                        fTwoX = "(" + fTwoX + ")";

                        String resultToCalculate = "(" + "(" + fOneX + "*" + numberOne + ")" + "-" + "(" + fTwoX + "*" + numberTwo + ")" + ")" + "/" + "(" + fOneX + "-" + fTwoX + ")";
                        Log.e(TAG, "onClick: " + resultToCalculate );

                        Expression calc = new ExpressionBuilder(resultToCalculate).build();

                        resultOfIteration = calc.evaluate();
                        Log.e(TAG, "onClick: " + resultOfIteration.toString());

                        numberOne = numberTwo;
                        numberTwo = resultOfIteration.toString();

                        iterationItems.add("Iteration " + (i+1) + " : " + resultOfIteration.toString());
                        itemsAdapter.notifyDataSetChanged();
                    }
                    catch(ArithmeticException e){
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    catch(IllegalArgumentException e){
                        Toast.makeText(MainActivity.this, "Please use variable x", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    }

            }
        });
    }
}