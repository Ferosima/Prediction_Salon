package com.example.predictionsalon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.sip.SipSession;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class GameView extends View {
    Paint paint;
    GameView gameView;
    Boolean first = true;
    Bitmap girl_look_ball;
    Bitmap ball_background;
    Bitmap coin;
    Bitmap elements[][];
    int number_elements;
    int number_true_element;
    ArrayList<Bitmap> man;
    int number;
    Random random;
    int h, w;
    int w_element;
    int maxY = 20;
    int maxX = 10;
    int number_of_coins = 0;
    int round;//case
    int timer = 3000;//для игры(показ элем)
    CountDownTimer timer_meeting, timer_newMan, timer_look, timer_game;
    Boolean meeting = true;
    Boolean newMan = false;//добавление нового перса
    Boolean lookInBall = false;//пауза перед запуском игры(смотрит на шар)
    Boolean game_timer_start = false;
    Boolean initialization_1 = true;
    Boolean initialization_2 = true;
    Boolean initialization_timer = true;
    Boolean initialization_element = true;
    Boolean clickOn = false;//для onTouchEvent


    //Listener mListener;
//
//    public interface Listener { // create an interface
//        void setBackground();
//    }
    public GameView(Context context) {
        super(context);
        // this.mListener=mListener;
        paint = new Paint();
        gameView = this;
        man = new ArrayList<>();
        random = new Random();
        elements = new Bitmap[4][8];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (first) {

            h = canvas.getHeight();
            w = canvas.getWidth();

            girl_look_ball = BitmapFactory.decodeResource(getResources(), R.drawable.background_3_1);
            girl_look_ball = Bitmap.createScaledBitmap(girl_look_ball, (int) canvas.getWidth(),
                    (int) h, true);
            ball_background = BitmapFactory.decodeResource(getResources(), R.drawable.background_4);
            ball_background = Bitmap.createScaledBitmap(ball_background, canvas.getWidth(), (int) h, true);

            man.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.visitor_1), w / 4, h / 9 * 8, true));
            man.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.visitor_2), w / 4, h / 9 * 8, true));
            number = random.nextInt(2);
            first = false;
        }
        Action(canvas);
        Game(canvas);
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // координаты Touch-события
        float evX = event.getX();
        float evY = event.getY();

        switch (event.getAction()) {
            // касание началось
            case MotionEvent.ACTION_DOWN:


            case MotionEvent.ACTION_UP:


                invalidate();
                break;
        }
        return true;
    }

    public void Action(final Canvas canvas) {
        if (meeting) {
            Log.d("timer_meeting", "true");
            timer_meeting = new CountDownTimer(500, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("timer_meeting", "check");
                }

                public void onFinish() {
                    newMan = true;
                    meeting = false;
                    //invalidate();
                }
            }.start();
        }
        if (newMan) {
            canvas.drawBitmap(man.get(number), w / 4 * 3, h / 18, paint);
            Log.d("timer_newMan", "true");
            timer_newMan = new CountDownTimer(500, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("timer_new", "true");
                    if (initialization_1) {
                        elements[0][0] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_1);
                        elements[0][0] = Bitmap.createScaledBitmap(elements[0][0], (int) (w / 3.9), h / 7, false);

                        elements[0][1] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_2);
                        elements[0][1] = Bitmap.createScaledBitmap(elements[0][1], (int) (w / 3.9), h / 7, false);

                        elements[0][2] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_3);
                        elements[0][2] = Bitmap.createScaledBitmap(elements[0][2], (int) (w / 3.9), h / 7, false);

                        elements[0][3] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_4);
                        elements[0][3] = Bitmap.createScaledBitmap(elements[0][3], (int) (w / 3.9), h / 7, false);

                        elements[0][4] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_5);
                        elements[0][4] = Bitmap.createScaledBitmap(elements[0][4], (int) (w / 3.9), h / 7, false);

                        elements[0][5] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_6);
                        elements[0][5] = Bitmap.createScaledBitmap(elements[0][5], (int) (w / 3.9), h / 7, false);

                        elements[0][6] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_7);
                        elements[0][6] = Bitmap.createScaledBitmap(elements[0][6], (int) (w / 3.9), h / 7, false);

                        elements[0][7] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_8);
                        elements[0][7] = Bitmap.createScaledBitmap(elements[0][7], (int) (w / 3.9), h / 7, false);


                        elements[1][0] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_1);
                        elements[1][0] = Bitmap.createScaledBitmap(elements[1][0], (int) (w / 3.9), h / 7, false);

                        elements[1][1] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_2);
                        elements[1][1] = Bitmap.createScaledBitmap(elements[1][1], (int) (w / 3.9), h / 7, false);

                        elements[1][2] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_3);
                        elements[1][2] = Bitmap.createScaledBitmap(elements[1][2], (int) (w / 3.9), h / 7, false);

                        elements[1][3] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_4);
                        elements[1][3] = Bitmap.createScaledBitmap(elements[1][3], (int) (w / 3.9), h / 7, false);

                        elements[1][4] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_5);
                        elements[1][4] = Bitmap.createScaledBitmap(elements[1][4], (int) (w / 3.9), h / 7, false);

                        elements[1][5] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_6);
                        elements[1][5] = Bitmap.createScaledBitmap(elements[1][5], (int) (w / 3.9), h / 7, false);

                        elements[1][6] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_7);
                        elements[1][6] = Bitmap.createScaledBitmap(elements[1][6], (int) (w / 3.9), h / 7, false);

                        elements[1][7] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_8);
                        elements[1][7] = Bitmap.createScaledBitmap(elements[1][7], (int) (w / 3.9), h / 7, false);
                        initialization_1 = false;
                    }
                }

                public void onFinish() {
                    newMan = false;
                    lookInBall = true;
                    //invalidate();
                }
            }.start();
        }
        if (lookInBall) {
            canvas.drawBitmap(girl_look_ball, 0, 0, paint);
            canvas.drawBitmap(man.get(number), w / 4 * 3, h / 18, paint);
            timer_look = new CountDownTimer(500, 1000) {
                public void onTick(long millisUntilFinished) {

                    Log.d("timer_look", "true");
                    if (initialization_2) {
                        elements[2][0] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_1);
                        elements[2][0] = Bitmap.createScaledBitmap(elements[2][0], (int) (w / 3.9), h / 7, false);

                        elements[2][1] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_2);
                        elements[2][1] = Bitmap.createScaledBitmap(elements[02][1], (int) (w / 3.9), h / 7, false);

                        elements[2][2] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_3);
                        elements[2][2] = Bitmap.createScaledBitmap(elements[2][2], (int) (w / 3.9), h / 7, false);

                        elements[2][3] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_4);
                        elements[2][3] = Bitmap.createScaledBitmap(elements[2][3], (int) (w / 3.9), h / 7, false);

                        elements[2][4] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_5);
                        elements[2][4] = Bitmap.createScaledBitmap(elements[2][4], (int) (w / 3.9), h / 7, false);

                        elements[2][5] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_6);
                        elements[2][5] = Bitmap.createScaledBitmap(elements[2][5], (int) (w / 3.9), h / 7, false);

                        elements[2][6] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_7);
                        elements[2][6] = Bitmap.createScaledBitmap(elements[2][6], (int) (w / 3.9), h / 7, false);

                        elements[2][7] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_8);
                        elements[2][7] = Bitmap.createScaledBitmap(elements[2][7], (int) (w / 3.9), h / 7, false);

//////////////////////////////////////number 3
                        elements[3][0] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_1);
                        elements[3][0] = Bitmap.createScaledBitmap(elements[3][0], (int) (w / 3.9), h / 7, false);

                        elements[3][1] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_2);
                        elements[3][1] = Bitmap.createScaledBitmap(elements[3][1], (int) (w / 3.9), h / 7, false);

                        elements[3][2] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_3);
                        elements[3][2] = Bitmap.createScaledBitmap(elements[3][2], (int) (w / 3.9), h / 7, false);

                        elements[3][3] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_4);
                        elements[3][3] = Bitmap.createScaledBitmap(elements[3][3], (int) (w / 3.9), h / 7, false);

                        elements[3][4] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_5);
                        elements[3][4] = Bitmap.createScaledBitmap(elements[3][4], (int) (w / 3.9), h / 7, false);

                        elements[3][5] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_6);
                        elements[3][5] = Bitmap.createScaledBitmap(elements[3][5], (int) (w / 3.9), h / 7, false);

                        elements[3][6] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_7);
                        elements[3][6] = Bitmap.createScaledBitmap(elements[3][6], (int) (w / 3.9), h / 7, false);

                        elements[3][7] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_8);
                        elements[3][7] = Bitmap.createScaledBitmap(elements[3][7], (int) (w / 3.9), h / 7, false);
                        initialization_2 = false;
                    }
                }

                public void onFinish() {
                    if (initialization_element) {
                        number_elements = random.nextInt(4);
                        number_true_element = random.nextInt(8);
                        initialization_element = false;
                    }
                    lookInBall = false;
                    game_timer_start = true;
                    //invalidate();
                }
            }.start();
        }
    }


    public void Drawing(final Canvas canvas) {

    }

    public void Game(final Canvas canvas) {
        if (game_timer_start) {
            switch (round) {
                case 5:
                    timer = 7000;
                case 7:
                    timer = 5000;
            }
            canvas.drawBitmap(ball_background, 0, 0, paint);
            canvas.drawBitmap(elements[number_elements][number_true_element], (int) (w / 2.0 - (w / 7.8)), (int) (h / 2.0 - (h / 14.0)), paint);
            clickOn = true;
            timer_game = new CountDownTimer(timer, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("timer_game", "true");

                }

                public void onFinish() {
                    game_timer_start = false;
                    meeting = true;
                    initialization_element = true;
                    // invalidate();

                }
            }.start();
        }
    }

}


