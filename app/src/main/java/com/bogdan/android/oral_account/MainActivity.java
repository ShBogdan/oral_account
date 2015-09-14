package com.bogdan.android.oral_account;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;


public class MainActivity extends Activity {
    private final String LOG = "____MyLog___";
    Stack<String> answer;
    Handler handler;
    int min = 1;
    int max = 9;
    String answerInput;
    int rightAnswer;
    int firstInt;
    int secondInt;
    int widthPixelsD;
    boolean reDraw = false;
    boolean isRight = true;


    Button btnPlus, btnMinus, btnMultiple, btnDivide, btnLevel_1, btnLevel_2, btnLevel_3, btnLevel_4,
            button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_0, button_minus, button_back;
    TextView tvFirstInt, tvSecondInt, tvOperation, tvAnswer, tvPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getDisResolution()>480)
            setContentView(R.layout.activity_main);
        else
            setContentView(R.layout.activity_main_small);

        answer = new Stack<>();
        answerInput = new String();
        handler = new Handler();


        tvPrevious = (TextView) findViewById(R.id.previous_tv);
        tvPrevious.setText("");
        tvPrevious.setTextColor(Color.parseColor("#F44336"));
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnPlus.setEnabled(false);
        btnPlus.setTextColor(Color.WHITE);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnMultiple = (Button) findViewById(R.id.btnMultiple);
        btnDivide = (Button) findViewById(R.id.btnDivide);

        btnLevel_1 = (Button) findViewById(R.id.btnLevel_1);
        btnLevel_1.setEnabled(false);
        btnLevel_1.setTextColor(Color.WHITE);
        btnLevel_2 = (Button) findViewById(R.id.btnLevel_2);
        btnLevel_3 = (Button) findViewById(R.id.btnLevel_3);
        btnLevel_4 = (Button) findViewById(R.id.btnLevel_4);

        button_0 = (Button) findViewById(R.id.button_0);
        button_1 = (Button) findViewById(R.id.button_1);
        button_2 = (Button) findViewById(R.id.button_2);
        button_3 = (Button) findViewById(R.id.button_3);
        button_4 = (Button) findViewById(R.id.button_4);
        button_5 = (Button) findViewById(R.id.button_5);
        button_6 = (Button) findViewById(R.id.button_6);
        button_7 = (Button) findViewById(R.id.button_7);
        button_8 = (Button) findViewById(R.id.button_8);
        button_9 = (Button) findViewById(R.id.button_9);
        button_minus = (Button) findViewById(R.id.button_minus);
        button_back  = (Button) findViewById(R.id.button_back);

        tvAnswer = (TextView) findViewById(R.id.answer);
        tvFirstInt = (TextView) findViewById(R.id.tvFirstInt);
        tvSecondInt = (TextView) findViewById(R.id.tvSecondInt);
        tvOperation = (TextView) findViewById(R.id.tvOperation);
        tvOperation.setText(R.string.plus);

        btnPlus.setOnClickListener(onCLOperation);
        btnMinus.setOnClickListener(onCLOperation);
        btnMultiple.setOnClickListener(onCLOperation);
        btnDivide.setOnClickListener(onCLOperation);

        btnLevel_1.setOnClickListener(onCLLevel);
        btnLevel_2.setOnClickListener(onCLLevel);
        btnLevel_3.setOnClickListener(onCLLevel);
        btnLevel_4.setOnClickListener(onCLLevel);

        button_0.setOnClickListener(onCNumbers);
        button_1.setOnClickListener(onCNumbers);
        button_2.setOnClickListener(onCNumbers);
        button_3.setOnClickListener(onCNumbers);
        button_4.setOnClickListener(onCNumbers);
        button_5.setOnClickListener(onCNumbers);
        button_6.setOnClickListener(onCNumbers);
        button_7.setOnClickListener(onCNumbers);
        button_8.setOnClickListener(onCNumbers);
        button_9.setOnClickListener(onCNumbers);
        button_minus.setOnClickListener(onCNumbers);
        button_back.setOnClickListener(onCNumbers);


        defBtnAnswer();

    }

    public void btnBack(View view){
        super.onBackPressed();
    }

    OnClickListener onCLOperation = new OnClickListener() {
        @Override
        public void onClick(View v) {
            changeOperation(v);
            defBtnAnswer();
            btnTextColor();
        }
    };
    OnClickListener onCLLevel = new OnClickListener() {
        @Override
        public void onClick(View v) {
            changeLevel(v);
            defBtnAnswer();
            btnTextColor();
        }
    };

    OnClickListener onCNumbers = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!reDraw) {
                Log.d(LOG, "ON");
                switch (v.getId()) {
                    case R.id.button_0:
                        answer.push("0");
                        break;
                    case R.id.button_1:
                        answer.push("1");
                        break;
                    case R.id.button_2:
                        answer.push("2");
                        break;
                    case R.id.button_3:
                        answer.push("3");
                        break;
                    case R.id.button_4:
                        answer.push("4");
                        break;
                    case R.id.button_5:
                        answer.push("5");
                        break;
                    case R.id.button_6:
                        answer.push("6");
                        break;
                    case R.id.button_7:
                        answer.push("7");
                        break;
                    case R.id.button_8:
                        answer.push("8");
                        break;
                    case R.id.button_9:
                        answer.push("9");
                        break;
                    case R.id.button_minus:
                        if (!answerInput.contains("-") && answerInput.length() == 0)
                            answer.push("-");
                        break;
                    case R.id.button_back:
                        if (!answer.isEmpty())
                            answer.pop();
                        break;
                }

                if (!answer.isEmpty()) {
                    answerInput = new ArrayList<>(answer).toString().substring(1, (3 * answer.size() - 1)).replaceAll(", ", "");
                    tvAnswer.setText(answerInput);
                } else {
                    tvAnswer.setText("");
                    answerInput = "";
                }
                if (answerInput.contains("-")) {
                    if (answerInput.length() - 1 == String.valueOf(Math.abs(rightAnswer)).length()) {
                        if (answerInput.equals(String.valueOf(rightAnswer))) {
                            Log.d(LOG, "true");
                            reDraw = true;
                            tvAnswer.setTextColor(Color.parseColor("#2196F3"));
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    defBtnAnswer();
                                }
                            }, 200);


                        } else {
                            Log.d(LOG, "false1");
                            isRight = false;
                            reDraw = true;
                            tvAnswer.setTextColor(Color.parseColor("#F44336"));
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    defBtnAnswer();
                                }
                            }, 200);
                        }
                    }
                } else {
                    if (answerInput.length() == String.valueOf(Math.abs(rightAnswer)).length()) {
                        if (answerInput.equals(String.valueOf(rightAnswer))) {
                            Log.d(LOG, "true");
                            reDraw = true;
                            tvAnswer.setTextColor(Color.parseColor("#2196F3"));
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    defBtnAnswer();
                                }
                            }, 200);
                        } else {
                            Log.d(LOG, "false2");
                            isRight = false;
                            reDraw = true;
                            tvAnswer.setTextColor(Color.parseColor("#F44336"));
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    defBtnAnswer();
                                }
                            }, 200);
                        }
                    }
                }
            }
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
        if(!isRight)
            tvPrevious.setText(String.valueOf(firstInt)+ " " + tvOperation.getText() + " " + secondInt + " = " + rightAnswer);
        else tvPrevious.setText("");
        isRight = true;
        reDraw = false;
        answer = new Stack<>();
        answerInput = new String();
        tvAnswer.setText("");
        tvAnswer.setTextColor(Color.parseColor("#37474F"));
        firstInt = random(min, max);
        secondInt = random(min, max);


        /*Plus*/
        if (!btnPlus.isEnabled()) {
            rightAnswer = firstInt + secondInt;

        }
        /*Minus*/
        if (!btnMinus.isEnabled()) {
            rightAnswer = firstInt - secondInt;
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
        }

        tvFirstInt.setText(String.valueOf(firstInt));
        tvSecondInt.setText(String.valueOf(secondInt));
        tvFirstInt.setTextColor(Color.BLACK);
        tvSecondInt.setTextColor(Color.BLACK);
        tvOperation.setTextColor(Color.BLACK);
    }

    int getDisResolution() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Log.d(LOG, width +" resolution");
        return width;

    }

    void btnTextColor() {
        if (!btnDivide.isEnabled()) {
            btnDivide.setTextColor(Color.WHITE);
        } else
            btnDivide.setTextColor(Color.BLACK);

        if (!btnMultiple.isEnabled()) {
            btnMultiple.setTextColor(Color.WHITE);
        } else
            btnMultiple.setTextColor(Color.BLACK);

        if (!btnMinus.isEnabled()) {
            btnMinus.setTextColor(Color.WHITE);
        } else
            btnMinus.setTextColor(Color.BLACK);

        if (!btnPlus.isEnabled()) {
            btnPlus.setTextColor(Color.WHITE);
        } else
            btnPlus.setTextColor(Color.BLACK);

        if (!btnLevel_1.isEnabled()) {
            btnLevel_1.setTextColor(Color.WHITE);
        } else
            btnLevel_1.setTextColor(Color.BLACK);

        if (!btnLevel_2.isEnabled()) {
            btnLevel_2.setTextColor(Color.WHITE);
        } else
            btnLevel_2.setTextColor(Color.BLACK);

        if (!btnLevel_3.isEnabled()) {
            btnLevel_3.setTextColor(Color.WHITE);
        } else
            btnLevel_3.setTextColor(Color.BLACK);

        if (!btnLevel_4.isEnabled()) {
            btnLevel_4.setTextColor(Color.WHITE);
        } else
            btnLevel_4.setTextColor(Color.BLACK);
    }

}