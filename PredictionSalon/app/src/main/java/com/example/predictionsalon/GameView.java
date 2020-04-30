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
    Bitmap coin_bitmap;
    Bitmap elements[][];
    int number_elements;
    int number_true_element;
    int number_true_coord;
    ArrayList<Bitmap> man;
    int number;
    Random random;
    int h, w;
    int h_coord, w_coord;
    double div_w = 6;
    double div_h = 3.5;

    int maxY = 20;
    int maxX = 10;
    int coins = 0;
    int round = 0;//case
    int timer = 10000;//для игры(показ элем)
    CountDownTimer timer_meeting, timer_newMan, timer_look, timer_game;
    Boolean meeting = true;
    Boolean newMan = false;//добавление нового перса
    Boolean lookInBall = false;//пауза перед запуском игры(смотрит на шар)
    Boolean game_timer_start = false;
    Boolean initialization_1 = true;
    Boolean initialization_2 = true;
    Boolean initialization_timer = true;
    Boolean initialization_element = true;
    Boolean initialization_coords = true;
    Boolean clickOn = false;//для onTouchEvent
    Boolean clickTrue = false;
    Boolean clickFalse = false;
    Coord coord_elements[];
    Coord coord_text;


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
        coord_elements = new Coord[8];

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

            coin_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.board);
            coin_bitmap = Bitmap.createScaledBitmap(coin_bitmap, canvas.getWidth() / 8, (int) h / 10, true);

            man.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.visitor_1), w / 4, h / 9 * 8, true));
            man.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.visitor_2), w / 4, h / 9 * 8, true));
            number = random.nextInt(2);

            coord_text=new Coord ( h / 24+ h / 10,w / 40, w / 40+w/8,h / 24);

            paint.setTextSize(50);
            paint.setColor(Color.WHITE);
            first = false;
        }
        Action(canvas);
        Game(canvas);
        Log.d("round", round + " ");
        // invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // координаты Touch-события
        float evX = event.getX();
        float evY = event.getY();

        switch (event.getAction()) {
            // касание началось
            case MotionEvent.ACTION_DOWN:
                if (clickOn) {
                    if (evX >= coord_elements[number_true_element].left && evX <= coord_elements[number_true_element].right &&
                            evY >= coord_elements[number_true_element].top && evY <= coord_elements[number_true_element].bottom) {
                        clickTrue = true;
                        break;
                    }
                    if (checkFalse(evX, evY)) {
                        clickFalse = true;
                        break;
                    }
                    break;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (clickOn) {
                    if (clickTrue) {
                        Log.d("click_true", "true");
                        timer_game.onFinish();
                        timer_game.cancel();
                        clickTrue = false;
                        coins+=random.nextInt(91)+10;
                        break;
                    }
                    if (clickFalse) {
                        timer_game.onFinish();
                        timer_game.cancel();
                        clickFalse = false;
                        break;
                    }
                }
                break;
        }
        return true;
    }

    public Boolean checkFalse(float evX, float evY) {
        for (int i = 0; i < 8; i++) {
            if (i != number_true_element) {
                if (evX >= coord_elements[i].left && evX <= coord_elements[i].right &&
                        evY >= coord_elements[i].top && evY <= coord_elements[i].bottom) {
                    return true;
                }
            }
        }
        return false;
    }

    public void drawCoin(Canvas canvas)
    {
        canvas.drawBitmap(coin_bitmap, w / 40, h / 24, paint);
       // coord_text.CoordDraw(canvas,paint);
        canvas.drawText(Integer.toString(coins), w / 40+w/8/3, coord_text.centerY+(int)(coord_text.h/4), paint);
        Log.d("h_coord",coord_text.h+" "+coord_text.h/4);
    }
    public void Action(final Canvas canvas) {
        if (meeting) {
            Log.d("timer_meeting", "true");
            drawCoin(canvas);
            timer_meeting = new CountDownTimer(700, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("timer_meeting", "check");
                }

                public void onFinish() {
                    newMan = true;
                    meeting = false;
                    invalidate();
                    round++;
                }
            }.start();
        }
        if (newMan) {
           // canvas.drawBitmap(coin_bitmap, w / 40, h / 24, paint);
            canvas.drawBitmap(man.get(number), w / 4 * 3, h / 18, paint);
            drawCoin(canvas);
           // coord_text.CoordDraw(canvas,paint);
          //  canvas.drawText(Integer.toString(coins), coord_text.centerX, h / 24+ h / 10/2, paint);
            Log.d("timer_newMan", "true");
            timer_newMan = new CountDownTimer(700, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("timer_new", "true");
                    if (initialization_1) {
                        h_coord = (int) (h / 3.5);
                        w_coord = (int) (w / 7);
                        elements[0][0] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_1);
                        elements[0][0] = Bitmap.createScaledBitmap(elements[0][0], (int) (w / div_w), (int) (h / div_h), false);

                        elements[0][1] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_2);
                        elements[0][1] = Bitmap.createScaledBitmap(elements[0][1], (int) (w / div_w), (int) (h / div_h), false);

                        elements[0][2] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_3);
                        elements[0][2] = Bitmap.createScaledBitmap(elements[0][2], (int) (w / div_w), (int) (h / div_h), false);

                        elements[0][3] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_4);
                        elements[0][3] = Bitmap.createScaledBitmap(elements[0][3], (int) (w / div_w), (int) (h / div_h), false);

                        elements[0][4] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_5);
                        elements[0][4] = Bitmap.createScaledBitmap(elements[0][4], (int) (w / div_w), (int) (h / div_h), false);

                        elements[0][5] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_6);
                        elements[0][5] = Bitmap.createScaledBitmap(elements[0][5], (int) (w / div_w), (int) (h / div_h), false);

                        elements[0][6] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_7);
                        elements[0][6] = Bitmap.createScaledBitmap(elements[0][6], (int) (w / div_w), (int) (h / div_h), false);

                        elements[0][7] = BitmapFactory.decodeResource(getResources(), R.drawable.art_1_8);
                        elements[0][7] = Bitmap.createScaledBitmap(elements[0][7], (int) (w / div_w), (int) (h / div_h), false);


                        elements[1][0] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_1);
                        elements[1][0] = Bitmap.createScaledBitmap(elements[1][0], (int) (w / div_w), (int) (h / div_h), false);

                        elements[1][1] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_2);
                        elements[1][1] = Bitmap.createScaledBitmap(elements[1][1], (int) (w / div_w), (int) (h / div_h), false);

                        elements[1][2] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_3);
                        elements[1][2] = Bitmap.createScaledBitmap(elements[1][2], (int) (w / div_w), (int) (h / div_h), false);

                        elements[1][3] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_4);
                        elements[1][3] = Bitmap.createScaledBitmap(elements[1][3], (int) (w / div_w), (int) (h / div_h), false);

                        elements[1][4] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_5);
                        elements[1][4] = Bitmap.createScaledBitmap(elements[1][4], (int) (w / div_w), (int) (h / div_h), false);

                        elements[1][5] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_6);
                        elements[1][5] = Bitmap.createScaledBitmap(elements[1][5], (int) (w / div_w), (int) (h / div_h), false);

                        elements[1][6] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_7);
                        elements[1][6] = Bitmap.createScaledBitmap(elements[1][6], (int) (w / div_w), (int) (h / div_h), false);

                        elements[1][7] = BitmapFactory.decodeResource(getResources(), R.drawable.art_2_8);
                        elements[1][7] = Bitmap.createScaledBitmap(elements[1][7], (int) (w / div_w), (int) (h / div_h), false);
                        initialization_1 = false;
                    }
                }

                public void onFinish() {
                    newMan = false;
                    lookInBall = true;
                    invalidate();
                }
            }.start();
        }
        if (lookInBall) {
            canvas.drawBitmap(girl_look_ball, 0, 0, paint);
           // canvas.drawBitmap(coin_bitmap, w / 40, h / 24, paint);
           // coord_text.CoordDraw(canvas,paint);
            canvas.drawBitmap(man.get(number), w / 4 * 3, h / 18, paint);
          //  canvas.drawText(Integer.toString(coins), w / 40+canvas.getWidth()/8/3, h / 24+ h / 10/2, paint);
            drawCoin(canvas);
            timer_look = new CountDownTimer(700, 1000) {
                public void onTick(long millisUntilFinished) {

                    Log.d("timer_look", "true");
                    if (initialization_2) {
                        elements[2][0] = BitmapFactory.decodeResource(getResources(), R.drawable.art_3_1);
                        elements[2][0] = Bitmap.createScaledBitmap(elements[2][0], (int) (w / div_w), (int) (h / div_h), false);

                        elements[2][1] = BitmapFactory.decodeResource(getResources(), R.drawable.art_3_2);
                        elements[2][1] = Bitmap.createScaledBitmap(elements[02][1], (int) (w / div_w), (int) (h / div_h), false);

                        elements[2][2] = BitmapFactory.decodeResource(getResources(), R.drawable.art_3_3);
                        elements[2][2] = Bitmap.createScaledBitmap(elements[2][2], (int) (w / div_w), (int) (h / div_h), false);

                        elements[2][3] = BitmapFactory.decodeResource(getResources(), R.drawable.art_3_4);
                        elements[2][3] = Bitmap.createScaledBitmap(elements[2][3], (int) (w / div_w), (int) (h / div_h), false);

                        elements[2][4] = BitmapFactory.decodeResource(getResources(), R.drawable.art_3_5);
                        elements[2][4] = Bitmap.createScaledBitmap(elements[2][4], (int) (w / div_w), (int) (h / div_h), false);

                        elements[2][5] = BitmapFactory.decodeResource(getResources(), R.drawable.art_3_6);
                        elements[2][5] = Bitmap.createScaledBitmap(elements[2][5], (int) (w / div_w), (int) (h / div_h), false);

                        elements[2][6] = BitmapFactory.decodeResource(getResources(), R.drawable.art_3_7);
                        elements[2][6] = Bitmap.createScaledBitmap(elements[2][6], (int) (w / div_w), (int) (h / div_h), false);

                        elements[2][7] = BitmapFactory.decodeResource(getResources(), R.drawable.art_3_8);
                        elements[2][7] = Bitmap.createScaledBitmap(elements[2][7], (int) (w / div_w), (int) (h / div_h), false);

//////////////////////////////////////number 3
                        elements[3][0] = BitmapFactory.decodeResource(getResources(), R.drawable.art_4_1);
                        elements[3][0] = Bitmap.createScaledBitmap(elements[3][0], (int) (w / div_w), (int) (h / div_h), false);

                        elements[3][1] = BitmapFactory.decodeResource(getResources(), R.drawable.art_4_2);
                        elements[3][1] = Bitmap.createScaledBitmap(elements[3][1], (int) (w / div_w), (int) (h / div_h), false);

                        elements[3][2] = BitmapFactory.decodeResource(getResources(), R.drawable.art_4_3);
                        elements[3][2] = Bitmap.createScaledBitmap(elements[3][2], (int) (w / div_w), (int) (h / div_h), false);

                        elements[3][3] = BitmapFactory.decodeResource(getResources(), R.drawable.art_4_4);
                        elements[3][3] = Bitmap.createScaledBitmap(elements[3][3], (int) (w / div_w), (int) (h / div_h), false);

                        elements[3][4] = BitmapFactory.decodeResource(getResources(), R.drawable.art_4_5);
                        elements[3][4] = Bitmap.createScaledBitmap(elements[3][4], (int) (w / div_w), (int) (h / div_h), false);

                        elements[3][5] = BitmapFactory.decodeResource(getResources(), R.drawable.art_4_6);
                        elements[3][5] = Bitmap.createScaledBitmap(elements[3][5], (int) (w / div_w), (int) (h / div_h), false);

                        elements[3][6] = BitmapFactory.decodeResource(getResources(), R.drawable.art_4_7);
                        elements[3][6] = Bitmap.createScaledBitmap(elements[3][6], (int) (w / div_w), (int) (h / div_h), false);

                        elements[3][7] = BitmapFactory.decodeResource(getResources(), R.drawable.art_4_8);
                        elements[3][7] = Bitmap.createScaledBitmap(elements[3][7], (int) (w / div_w), (int) (h / div_h), false);
                        initialization_2 = false;
                    }
                }

                public void onFinish() {
                    if (initialization_element) {
                        number_elements = random.nextInt(4);
                        number_true_element = random.nextInt(8);
                        number_true_coord = random.nextInt(8);
                        initialization_element = false;
                    }
                    lookInBall = false;
                    game_timer_start = true;
                    invalidate();
                }
            }.start();
        }
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

            clickOn = true;

            //leftisde
            coord_elements[0] = new Coord((int) (h / 24 + h / div_h), (int) (w / 20 + w / div_w), (int) (w / 20 + w / div_w * 2), h / 24);
            coord_elements[1] = new Coord((int) (h / 24 + h / div_h + (h / div_h / 2)), w / 40, (int) (w / 40 + w / div_w), (int) (h / 24 + (h / div_h / 2)));
            coord_elements[2] = new Coord((int) h - (int) (h / 24 + (h / div_h / 2)), w / 40, (int) (w / 40 + w / div_w), (int) (h - (h / 24 + h / div_h + (h / div_h / 2))));
            coord_elements[3] = new Coord(h - (h / 24), (int) (w / 20 + w / div_w), (int) (w / 20 + w / div_w * 2), h - (int) (h / 24 + h / 3.5));
            //rightside
            coord_elements[4] = new Coord((int) (h / 24 + h / div_h), w - (int) (w / 20 + w / div_w * 2), w - (int) (w / 20 + w / div_w), h / 24);
            coord_elements[5] = new Coord((int) (h / 24 + h / div_h + (h / div_h / 2)), w - (int) (w / 40 + w / div_w), w - (w / 40), (int) (h / 24 + (h / div_h / 2)));
            coord_elements[6] = new Coord((int) h - (int) (h / 24 + (h / div_h / 2)), w - (int) (w / 40 + w / div_w), w - (int) (w / 40), (int) (h - (h / 24 + h / div_h + (h / div_h / 2))));
            coord_elements[7] = new Coord(h - (h / 24), w - (int) (w / 20 + w / div_w * 2), (int) (w - (w / 20 + w / div_w)), h - (int) (h / 24 + h / div_h));
//
            //coord_elements[0].CoordDraw(canvas, paint);
//            coord_elements[1].CoordDraw(canvas, paint);
//            coord_elements[2].CoordDraw(canvas, paint);
//            coord_elements[3].CoordDraw(canvas, paint);
//            coord_elements[4].CoordDraw(canvas,paint);
//            coord_elements[5].CoordDraw(canvas,paint);
//            coord_elements[6].CoordDraw(canvas,paint);
//            coord_elements[7].CoordDraw(canvas,paint);
            for (int i = 0; i < 8; i++) {
                canvas.drawBitmap(elements[number_elements][i], coord_elements[i].left, coord_elements[i].top, paint);
                //         coord_elements[i].CoordDraw(canvas,paint);
                //   if (number_true_element+i<8)
                //    canvas.drawBitmap(elements[number_elements][i],coord_elements[number_true_element+i].left,coord_elements[number_true_element+i].top,paint);
                //   else
                //        canvas.drawBitmap(elements[number_elements][i],coord_elements[i-number_true_element].left,coord_elements[i-number_true_element].top,paint);
            }
            canvas.drawBitmap(elements[number_elements][number_true_element], (int) (w / 2.0 - (w / div_w / 2)), (int) (h / 2.0 - (h / div_h / 2)), paint);
            Log.d("timer number", " " + number_true_element);
            timer_game = new CountDownTimer(timer, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("timer_game", "true");

                }

                public void onFinish() {
                    game_timer_start = false;
                    meeting = true;
                    initialization_element = true;
                    clickOn = false;
                    invalidate();

                }
            }.start();
        }
    }

}


