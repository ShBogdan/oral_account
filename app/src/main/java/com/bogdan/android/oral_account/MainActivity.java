package com.bogdan.android.oral_account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class MainActivity extends Activity {
    private final String LOG = "____MyLog___";
    int count = 0;
    Handler handler = new Handler();
    HashSet<Integer> btnAnswerSet = new HashSet<>();
    int min = 1;
    int max = 9;
    int rightAnswer;
    int firstInt;
    int secondInt;
    int widthPixelsD;

    Button btnPlus, btnMinus, btnMultiple, btnDivide, btnLevel_1, btnLevel_2, btnLevel_3, btnLevel_4,
            btnVariant_1, btnVariant_2, btnVariant_3, btnVariant_4;
    TextView tvFirstInt, tvSecondInt, tvOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(widthPixelsD)
        setContentView(R.layout.activity_main);

        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnPlus.setEnabled(false);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnMultiple = (Button) findViewById(R.id.btnMultiple);
        btnDivide = (Button) findViewById(R.id.btnDivide);

        btnLevel_1 = (Button) findViewById(R.id.btnLevel_1);
        btnLevel_1.setEnabled(false);
        btnLevel_2 = (Button) findViewById(R.id.btnLevel_2);
        btnLevel_3 = (Button) findViewById(R.id.btnLevel_3);
        btnLevel_4 = (Button) findViewById(R.id.btnLevel_4);

        btnVariant_1 = (Button) findViewById(R.id.btnVariant_1);
        btnVariant_2 = (Button) findViewById(R.id.btnVariant_2);
        btnVariant_3 = (Button) findViewById(R.id.btnVariant_3);
        btnVariant_4 = (Button) findViewById(R.id.btnVariant_4);

        btnPlus.setOnClickListener(onCLOperation);
        btnMinus.setOnClickListener(onCLOperation);
        btnMultiple.setOnClickListener(onCLOperation);
        btnDivide.setOnClickListener(onCLOperation);

        btnLevel_1.setOnClickListener(onCLLevel);
        btnLevel_2.setOnClickListener(onCLLevel);
        btnLevel_3.setOnClickListener(onCLLevel);
        btnLevel_4.setOnClickListener(onCLLevel);

        btnVariant_1.setOnClickListener(onCLVariant);
        btnVariant_2.setOnClickListener(onCLVariant);
        btnVariant_3.setOnClickListener(onCLVariant);
        btnVariant_4.setOnClickListener(onCLVariant);

        tvFirstInt = (TextView) findViewById(R.id.tvFirstInt);
        tvSecondInt = (TextView) findViewById(R.id.tvSecondInt);
        tvOperation = (TextView) findViewById(R.id.tvOperation);
        tvOperation.setText(R.string.plus);

        defBtnAnswer();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.send_like) {

            Toast.makeText(getBaseContext(), "Идем на гуглпей", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    OnClickListener onCLOperation = new OnClickListener() {
        @Override
        public void onClick(View v) {
            changeOperation(v);
            defBtnAnswer();
        }
    };
    OnClickListener onCLLevel = new OnClickListener() {
        @Override
        public void onClick(View v) {
            changeLevel(v);
            defBtnAnswer();
        }
    };
    OnClickListener onCLVariant = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getTag().equals(String.valueOf(rightAnswer))) {
                tvFirstInt.setTextColor(Color.GREEN);
                tvSecondInt.setTextColor(Color.GREEN);
                tvOperation.setTextColor(Color.GREEN);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        defBtnAnswer();
                    }
                }, 100);
            } else {
                tvFirstInt.setTextColor(Color.RED);
                tvSecondInt.setTextColor(Color.RED);
                tvOperation.setTextColor(Color.RED);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        defBtnAnswer();
                    }
                }, 100);
            }
            Log.d(LOG, "" + count);
        }
    };

    void changeLevel(View v) {
        btnLevel_1.setEnabled(true);
        btnLevel_2.setEnabled(true);
        btnLevel_3.setEnabled(true);
        btnLevel_4.setEnabled(true);

        switch (v.getId()) {
            case R.id.btnLevel_1:
                btnLevel_1.setEnabled(false);
                min = 1;
                max = 9;
                break;
            case R.id.btnLevel_2:
                btnLevel_2.setEnabled(false);
                min = 1;
                max = 99;
                break;
            case R.id.btnLevel_3:
                btnLevel_3.setEnabled(false);
                min = 1;
                max = 999;
                break;
            case R.id.btnLevel_4:
                min = 1;
                max = 9999;
                btnLevel_4.setEnabled(false);
                break;
        }


    }

    void changeOperation(View v) {
        btnPlus.setEnabled(true);
        btnMinus.setEnabled(true);
        btnMultiple.setEnabled(true);
        btnDivide.setEnabled(true);
        switch (v.getId()) {
            case R.id.btnPlus:
                btnPlus.setEnabled(false);
                tvOperation.setText(R.string.plus);
                break;
            case R.id.btnMinus:
                btnMinus.setEnabled(false);
                tvOperation.setText(R.string.minus);
                break;
            case R.id.btnMultiple:
                btnMultiple.setEnabled(false);
                tvOperation.setText(R.string.multiple);
                break;
            case R.id.btnDivide:
                btnDivide.setEnabled(false);
                tvOperation.setText(R.string.divide);
                break;
        }
    }


    int random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    void defBtnAnswer() {
        firstInt = random(min, max);
        secondInt = random(min, max);
        btnAnswerSet = new HashSet<>();
        /*Plus*/
        if (!btnPlus.isEnabled()) {
            rightAnswer = firstInt + secondInt;
            btnAnswerSet.add(rightAnswer);
            while (btnAnswerSet.size() != 4)
                btnAnswerSet.add(random(min, max));
        }
        /*Minus*/
        if (!btnMinus.isEnabled()) {
            rightAnswer = firstInt - secondInt;
            btnAnswerSet.add(rightAnswer);
            while (btnAnswerSet.size() != 4)
                btnAnswerSet.add(random(min, max));
        }

        /*Multiple*/
        if (!btnMultiple.isEnabled()) {
            if (!btnLevel_1.isEnabled()) {
                firstInt = random(2, 9);
                secondInt = random(2, 9);
                rightAnswer = firstInt * secondInt;
            }
            if (!btnLevel_2.isEnabled()) {
                firstInt = random(2, 9);
                secondInt = random(10, 99);
                rightAnswer = firstInt * secondInt;
                if (random(1, 2) == 1) {
                    int temp = firstInt;
                    firstInt = secondInt;
                    secondInt = temp;
                }
            }
            if (!btnLevel_3.isEnabled()) {
                firstInt = random(9, 99);
                secondInt = random(9, 99);
                rightAnswer = firstInt * secondInt;
            }
            if (!btnLevel_4.isEnabled()) {
                firstInt = random(11, 999);
                secondInt = random(101, 9999);
                rightAnswer = firstInt * secondInt;
                if (random(1, 2) == 1) {
                    int temp = firstInt;
                    firstInt = secondInt;
                    secondInt = temp;
                }
            }
            btnAnswerSet.add(rightAnswer);
            while (btnAnswerSet.size() != 4)
                btnAnswerSet.add(random(min, max) * firstInt);
        }

        /*Divide*/
        if (!btnDivide.isEnabled()) {
            if (!btnLevel_1.isEnabled()) {
                do {
                    firstInt = random(2, 99);
                    secondInt = random(2, 9);
                    rightAnswer = firstInt / secondInt;
                }
                while (firstInt % secondInt != 0 || firstInt == secondInt || firstInt > secondInt * 10);
            } else
                do {
                    firstInt = random(min, max);
                    secondInt = random(min, max);
                    rightAnswer = firstInt / secondInt;
                } while (firstInt % secondInt != 0 || firstInt == secondInt || secondInt == 1);
            btnAnswerSet.add(rightAnswer);

            while (btnAnswerSet.size() != 4) {
                int result;
                int tempfirstInt;
                int tempsecondInt;
                do {
                    tempfirstInt = random(min, max);
                    tempsecondInt = random(min, max);
                    result = tempfirstInt / tempsecondInt;
                }
                while (tempfirstInt % tempsecondInt != 0 || result < 2 || tempfirstInt < tempsecondInt);
                btnAnswerSet.add(result);

            }
        }

        ArrayList<Integer> btnAnswer = new ArrayList<>(btnAnswerSet);
        Collections.shuffle(btnAnswer);
        btnVariant_1.setText(String.valueOf(btnAnswer.get(0)));
        btnVariant_1.setTag(String.valueOf(btnAnswer.get(0)));
        btnVariant_2.setText(String.valueOf(btnAnswer.get(1)));
        btnVariant_2.setTag(String.valueOf(btnAnswer.get(1)));
        btnVariant_3.setText(String.valueOf(btnAnswer.get(2)));
        btnVariant_3.setTag(String.valueOf(btnAnswer.get(2)));
        btnVariant_4.setText(String.valueOf(btnAnswer.get(3)));
        btnVariant_4.setTag(String.valueOf(btnAnswer.get(3)));
        tvFirstInt.setText(String.valueOf(firstInt));
        tvSecondInt.setText(String.valueOf(secondInt));
        tvFirstInt.setTextColor(Color.BLACK);
        tvSecondInt.setTextColor(Color.BLACK);
        tvOperation.setTextColor(Color.BLACK);
    }

    void getDisResolution() {
    Display display = this.getWindowManager().getDefaultDisplay();
    DisplayMetrics metricsB = new DisplayMetrics();
    display.getMetrics(metricsB);
    widthPixelsD = metricsB.widthPixels;
}

}
