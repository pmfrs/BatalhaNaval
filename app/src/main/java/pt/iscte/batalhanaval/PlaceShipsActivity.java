package pt.iscte.batalhanaval;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static pt.iscte.batalhanaval.R.drawable.shape_button;

public class PlaceShipsActivity extends AppCompatActivity implements View.OnTouchListener {
    private LinearLayout linearLayout;
    private GridLayout gridLayout;

    private ImageView ship3r, ship2r, ship1r, ship4r, ship5r;
    private Drawable shapeButton;
    private Ship s1, s2, s3, s4, s5;
    private Ship[] ships = new Ship[5];
    private Player player1, player2;
    public TextView[][] buttons;
    private TextView B00;
    private TextView B10;
    private TextView B20;
    private TextView B30;
    private TextView B40;
    private TextView B50;
    private TextView B60;
    private TextView B70;
    private TextView B80;
    private TextView B90;
    private TextView B01;
    private TextView B11;
    private TextView B21;
    private TextView B31;
    private TextView B41;
    private TextView B51;
    private TextView B61;
    private TextView B71;
    private TextView B81;
    private TextView B91;
    private TextView B02;
    private TextView B12;
    private TextView B22;
    private TextView B32;
    private TextView B42;
    private TextView B52;
    private TextView B62;
    private TextView B72;
    private TextView B82;
    private TextView B92;
    private TextView B03;
    private TextView B13;
    private TextView B23;
    private TextView B33;
    private TextView B43;
    private TextView B53;
    private TextView B63;
    private TextView B73;
    private TextView B83;
    private TextView B93;
    private TextView B04;
    private TextView B14;
    private TextView B24;
    private TextView B34;
    private TextView B44;
    private TextView B54;
    private TextView B64;
    private TextView B74;
    private TextView B84;
    private TextView B94;
    private TextView B05;
    private TextView B15;
    private TextView B25;
    private TextView B35;
    private TextView B45;
    private TextView B55;
    private TextView B65;
    private TextView B75;
    private TextView B85;
    private TextView B95;
    private TextView B06;
    private TextView B16;
    private TextView B26;
    private TextView B36;
    private TextView B46;
    private TextView B56;
    private TextView B66;
    private TextView B76;
    private TextView B86;
    private TextView B96;
    private TextView B07;
    private TextView B17;
    private TextView B27;
    private TextView B37;
    private TextView B47;
    private TextView B57;
    private TextView B67;
    private TextView B77;
    private TextView B87;
    private TextView B97;
    private TextView B08;
    private TextView B18;
    private TextView B28;
    private TextView B38;
    private TextView B48;
    private TextView B58;
    private TextView B68;
    private TextView B78;
    private TextView B88;
    private TextView B98;
    private TextView B09;
    private TextView B19;
    private TextView B29;
    private TextView B39;
    private TextView B49;
    private TextView B59;
    private TextView B69;
    private TextView B79;
    private TextView B89;
    private TextView B99;


    private Button placeShips;
    private Button ready;

    private int boatsDisplay = 99;
    // private Button t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ships);
        ship3r = (ImageView) findViewById(R.id.ship3r);
        ship1r = (ImageView) findViewById(R.id.ship1r);
        ship2r = (ImageView) findViewById(R.id.ship2r);
        ship4r = (ImageView) findViewById(R.id.ship4r);
        ship5r = (ImageView) findViewById(R.id.ship5r);


        B00 = (TextView) findViewById(R.id.button00);
        B10 = (TextView) findViewById(R.id.button10);
        B20 = (TextView) findViewById(R.id.button20);
        B30 = (TextView) findViewById(R.id.button30);
        B40 = (TextView) findViewById(R.id.button40);
        B50 = (TextView) findViewById(R.id.button50);
        B60 = (TextView) findViewById(R.id.button60);
        B70 = (TextView) findViewById(R.id.button70);
        B80 = (TextView) findViewById(R.id.button80);
        B90 = (TextView) findViewById(R.id.button90);
        B01 = (TextView) findViewById(R.id.button01);
        B11 = (TextView) findViewById(R.id.button11);
        B21 = (TextView) findViewById(R.id.button21);
        B31 = (TextView) findViewById(R.id.button31);
        B41 = (TextView) findViewById(R.id.button41);
        B51 = (TextView) findViewById(R.id.button51);
        B61 = (TextView) findViewById(R.id.button61);
        B71 = (TextView) findViewById(R.id.button71);
        B81 = (TextView) findViewById(R.id.button81);
        B91 = (TextView) findViewById(R.id.button91);
        B02 = (TextView) findViewById(R.id.button02);
        B12 = (TextView) findViewById(R.id.button12);
        B22 = (TextView) findViewById(R.id.button22);
        B32 = (TextView) findViewById(R.id.button32);
        B42 = (TextView) findViewById(R.id.button42);
        B52 = (TextView) findViewById(R.id.button52);
        B62 = (TextView) findViewById(R.id.button62);
        B72 = (TextView) findViewById(R.id.button72);
        B82 = (TextView) findViewById(R.id.button82);
        B92 = (TextView) findViewById(R.id.button92);
        B03 = (TextView) findViewById(R.id.button03);
        B13 = (TextView) findViewById(R.id.button13);
        B23 = (TextView) findViewById(R.id.button23);
        B33 = (TextView) findViewById(R.id.button33);
        B43 = (TextView) findViewById(R.id.button43);
        B53 = (TextView) findViewById(R.id.button53);
        B63 = (TextView) findViewById(R.id.button63);
        B73 = (TextView) findViewById(R.id.button73);
        B83 = (TextView) findViewById(R.id.button83);
        B93 = (TextView) findViewById(R.id.button93);
        B04 = (TextView) findViewById(R.id.button04);
        B14 = (TextView) findViewById(R.id.button14);
        B24 = (TextView) findViewById(R.id.button24);
        B34 = (TextView) findViewById(R.id.button34);
        B44 = (TextView) findViewById(R.id.button44);
        B54 = (TextView) findViewById(R.id.button54);
        B64 = (TextView) findViewById(R.id.button64);
        B74 = (TextView) findViewById(R.id.button74);
        B84 = (TextView) findViewById(R.id.button84);
        B94 = (TextView) findViewById(R.id.button94);
        B05 = (TextView) findViewById(R.id.button05);
        B15 = (TextView) findViewById(R.id.button15);
        B25 = (TextView) findViewById(R.id.button25);
        B35 = (TextView) findViewById(R.id.button35);
        B45 = (TextView) findViewById(R.id.button45);
        B55 = (TextView) findViewById(R.id.button55);
        B65 = (TextView) findViewById(R.id.button65);
        B75 = (TextView) findViewById(R.id.button75);
        B85 = (TextView) findViewById(R.id.button85);
        B95 = (TextView) findViewById(R.id.button95);
        B06 = (TextView) findViewById(R.id.button06);
        B16 = (TextView) findViewById(R.id.button16);
        B26 = (TextView) findViewById(R.id.button26);
        B36 = (TextView) findViewById(R.id.button36);
        B46 = (TextView) findViewById(R.id.button46);
        B56 = (TextView) findViewById(R.id.button56);
        B66 = (TextView) findViewById(R.id.button66);
        B76 = (TextView) findViewById(R.id.button76);
        B86 = (TextView) findViewById(R.id.button86);
        B96 = (TextView) findViewById(R.id.button96);
        B07 = (TextView) findViewById(R.id.button07);
        B17 = (TextView) findViewById(R.id.button17);
        B27 = (TextView) findViewById(R.id.button27);
        B37 = (TextView) findViewById(R.id.button37);
        B47 = (TextView) findViewById(R.id.button47);
        B57 = (TextView) findViewById(R.id.button57);
        B67 = (TextView) findViewById(R.id.button67);
        B77 = (TextView) findViewById(R.id.button77);
        B87 = (TextView) findViewById(R.id.button87);
        B97 = (TextView) findViewById(R.id.button97);
        B08 = (TextView) findViewById(R.id.button08);
        B18 = (TextView) findViewById(R.id.button18);
        B28 = (TextView) findViewById(R.id.button28);
        B38 = (TextView) findViewById(R.id.button38);
        B48 = (TextView) findViewById(R.id.button48);
        B58 = (TextView) findViewById(R.id.button58);
        B68 = (TextView) findViewById(R.id.button68);
        B78 = (TextView) findViewById(R.id.button78);
        B88 = (TextView) findViewById(R.id.button88);
        B98 = (TextView) findViewById(R.id.button98);
        B09 = (TextView) findViewById(R.id.button09);
        B19 = (TextView) findViewById(R.id.button19);
        B29 = (TextView) findViewById(R.id.button29);
        B39 = (TextView) findViewById(R.id.button39);
        B49 = (TextView) findViewById(R.id.button49);
        B59 = (TextView) findViewById(R.id.button59);
        B69 = (TextView) findViewById(R.id.button69);
        B79 = (TextView) findViewById(R.id.button79);
        B89 = (TextView) findViewById(R.id.button89);
        B99 = (TextView) findViewById(R.id.button99);
        shapeButton = B00.getBackground();
        placeShips = (Button) findViewById(R.id.place_ships);
        ready = (Button) findViewById(R.id.ready);

        placeShips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putShips();
                ready.setVisibility(View.VISIBLE);
            }
        });

        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                String s = Integer.toString(boatsDisplay);
                i.putExtra("BOATS_DISPLAY", s);
                startActivity(i);
            }
        });

        player1 = (Player) getIntent().getSerializableExtra("Player1");
        player2 = (Player) getIntent().getSerializableExtra("Player2");

        // ship1r.setOnTouchListener(this);
        //ship2r.setOnTouchListener(this);


        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        // gridLayout = (GridLayout) findViewById(R.id.gridLayout8);


    }


    //Buttons
/*      // button9.setOnLongClickListener(longClickListener);
      // gridLayout.addView(button8, 1);
       //gridLayout.addView(button9);
       //gridLayout.addView(button10);

       //gridLayout.setOnLongClickListener(longClickListener);
    }*/


    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("", "");
            Button.DragShadowBuilder myShadow = new Button.DragShadowBuilder(v);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(data, myShadow, v, 0);
            } else {
                v.startDrag(data, myShadow, v, 0);
            }
            return true;
        }

    };


    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();

            int dragEvent = event.getAction();
            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DROP:
                    if (view.getId() == R.id.ship1r) {

                        //Log.d("tag", Float.toString(B1.getX()));
                        //Log.d("tag", Float.toString(B2.getY()));
                        Drawable x = ship1r.getDrawable();

                        gridLayout.setBackground(x);
                    }
                    //view.animate()
                    //      .x(B1.getX() )
                    // .y(B1.getY() )
                    //.start();


                    break;

            }
            return true;

        }
    };

    public void onBackPressed() {

    }

    public void ready(View view) {

        if (player1 == null || player2 == null) {
            player1 = (Player) getIntent().getSerializableExtra("Player1");
            player2 = (Player) getIntent().getSerializableExtra("Player2");
        }

        s1 = new Ship(2, 0, 1, R.drawable.ship1r, R.id.ship1r);
        s2 = new Ship(3, 0, 2, R.drawable.ship2r, R.id.ship2r);
        s3 = new Ship(4, 0, 3, R.drawable.ship3r, R.id.ship3r);
        s4 = new Ship(5, 0, 4, R.drawable.ship4r, R.id.ship4r);
        s5 = new Ship(6, 0, 5, R.drawable.ship5r, R.id.ship5r);

        ships[0] = s1;
        ships[1] = s2;
        ships[2] = s3;
        ships[3] = s4;
        ships[4] = s5;

        player1.ships = ships;
        player2.ships = ships;


        Log.d("readdy ", "entrei no ready");

    }

    float x = 0, y = 0, xf = 0, yf = 0;
    boolean moving = false;
    int i;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Log.d("tag", Integer.toString(view.getId()));
        ready(view);


        for (i = 0; i < ships.length; i++)
            if (view == findViewById(ships[i].viewID))
                break;

        Log.d("Aqui ", "passou o erro");
        player1.deleteShip(ships[i]);
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.d("sar", "ontouch : estou aqui");
                x = view.getX() - event.getRawX();
                y = view.getY() - event.getRawY();
                moving = true;
                break;

            case MotionEvent.ACTION_MOVE:

                if (moving == true) {
                    Log.d("Agora ", "ontouch : action move");

                    xf = event.getRawX() + x;
                    yf = event.getRawY() + y;
                    view.setX(xf);
                    view.setY(yf);


                    findViewById(ships[i].viewID).setBackgroundResource(ships[i].imageID);
                    Log.d("action_move", "entrei no action move");

                    break;
                }
        }
        return true;
    }


    private void resetShips(TextView[][] buttons) {
        Log.d("s", "Entrei no reset");

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Log.d("Reset", "i " + i);

                Log.d("Reset", "j " + j);
                buttons[i][j].setBackground(shapeButton);
            }
        }

    }


    public void putShips() {

        int counter = 0;

        String[][] positions = new String[][]{
                {"1", "Ship1", "11 21", ""},
                {"1", "Ship2", "22 32 42", ""},
                {"1", "Ship3", "43 53 63 73", ""},
                {"1", "Ship4", "66 76 86", ""},
                {"1", "Ship5", "39 49", ""},
                {"2", "Ship1", "24 25", "R"},
                {"2", "Ship2", "39 49 59", ""},
                {"2", "Ship3", "66 67 68 69", "R"},
                {"2", "Ship4", "75 85 95", ""},
                {"2", "Ship5", "44 45", "R"},
        };

        String[][] finalPositions = new String[5][3];

         buttons = new TextView[][]{
                 {B00, B01, B02, B03, B04, B05, B06, B07, B08, B09,},
                 {B10, B11, B12, B13, B14, B15, B16, B17, B18, B19,},
                 {B20, B21, B22, B23, B24, B25, B26, B27, B28, B29,},
                 {B30, B31, B32, B33, B34, B35, B36, B37, B38, B39,},
                 {B40, B41, B42, B43, B44, B45, B46, B47, B48, B49,},
                 {B50, B51, B52, B53, B54, B55, B56, B57, B58, B59,},
                 {B60, B61, B62, B63, B64, B65, B66, B67, B68, B69,},
                 {B70, B71, B72, B73, B74, B75, B76, B77, B78, B79,},
                 {B80, B81, B82, B83, B84, B85, B86, B87, B88, B89,},
                 {B90, B91, B92, B93, B94, B95, B96, B97, B98, B99,}
         };
        Matrix matrix = new Matrix();
        matrix.postRotate(-90);
        Random rnd = new Random();
        boatsDisplay = rnd.nextInt((2 - 1) + 1) + 1;
        ImageView ship;
        TextView box;
        resetShips(buttons);
        Log.d("tag", "Random escolheu " + Integer.toString(boatsDisplay));
        for (i = 0; i < 10; i++) {

            if (positions[i][0].equals(Integer.toString(boatsDisplay))) {

                finalPositions[counter][0] = positions[i][1];
                finalPositions[counter][1] = positions[i][2];
                finalPositions[counter][2] = positions[i][3];
                counter++;
            }
        }

        for (int i = 0; i < 5; i++) {
            Drawable draw;
            BitmapDrawable drawable1;

            if (finalPositions[i][0] == "Ship1") {
                ship = ship1r;
                drawable1 = (BitmapDrawable) ship1r.getDrawable();
            } else if (finalPositions[i][0] == "Ship2") {
                ship = ship2r;
                drawable1 = (BitmapDrawable) ship2r.getDrawable();
            } else if (finalPositions[i][0] == "Ship3") {
                ship = ship3r;
                drawable1 = (BitmapDrawable) ship3r.getDrawable();
            } else if (finalPositions[i][0] == "Ship4") {
                ship = ship4r;
                drawable1 = (BitmapDrawable) ship4r.getDrawable();
            } else {
                ship = ship5r;
                drawable1 = (BitmapDrawable) ship5r.getDrawable();
            }
            ;

            drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(), drawable1.getIntrinsicHeight());
            Bitmap bitmap1 = drawable1.getBitmap();
            Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
            String[] parts = finalPositions[i][1].split(" ");

            for (int j = 0; j < parts.length; j++) {
                Log.d("tag", "For dentro das parts");
                if (j == 0) {
                    if (finalPositions[i][2].equals("R")) {
                        draw = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight() / parts.length, matrix, true));

                    } else {
                        draw = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight() / parts.length));
                    }
                } else {

                    //draw = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap1, 0, ((parts.length - 1) *bitmap1.getHeight()/parts.length ), bitmap1.getWidth(), bitmap1.getHeight()/parts.length) );
                    if (finalPositions[i][2].equals("R")) {
                        draw = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap1, 0, (j * bitmap1.getHeight() / parts.length), bitmap1.getWidth(), bitmap1.getHeight() / parts.length, matrix, true));
                    } else {
                        draw = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap1, 0, (j * bitmap1.getHeight() / parts.length), bitmap1.getWidth(), bitmap1.getHeight() / parts.length));

                    }
                }


                box = buttons[Character.getNumericValue(parts[j].charAt(0))][Character.getNumericValue(parts[j].charAt(1))];

                box.setBackground(draw);
                box.setId(ship.getId());

                //Integer.toString(box.getId()));



            }

        }
    }
    public TextView[][] getButtons(){
        return buttons;

    }



        /*Drawable draw82;
        Drawable draw92;

        BitmapDrawable drawable1 = (BitmapDrawable) ship1r.getDrawable();
        drawable1.setBounds(0,0,drawable1.getIntrinsicWidth(),drawable1.getIntrinsicHeight());
        Bitmap bitmap1 = drawable1.getBitmap();
        Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);

        draw82 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight()/2));
        draw92 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap1, 0, bitmap1.getHeight()/2, bitmap1.getWidth(), bitmap1.getHeight()/2));

        B1.setBackground(draw82);
        B2.setBackground(draw92);

        B1.setId(ship1r.getId());
        B2.setId(ship1r.getId());

        //player1.addShipsToGrid(0,0, s1);
        //player1.addShipsToGrid(1,0, s1);

        //ship3r - Coluna 2
        Drawable draw81;
        Drawable draw91;
        Drawable draw11;

        BitmapDrawable drawable = (BitmapDrawable) ship3r.getDrawable();
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        draw81 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()/3));
        draw91 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap, 0, bitmap.getHeight()/3 , bitmap.getWidth(), bitmap.getHeight()/3) );
        draw11 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap, 0,(2*bitmap.getHeight()/3), bitmap.getWidth(), bitmap.getHeight()/3) );

        B01.setBackground(draw81);
        B11.setBackground(draw91);
        B21.setBackground(draw11);

        B01.setId(ship3r.getId());
        B11.setId(ship3r.getId());
        B21.setId(ship3r.getId());


        //Coluna 3 - ship2r
        Drawable draw73;
        Drawable draw83;
        Drawable draw93;
        Drawable draw103;

        Bitmap[] x3 = new Bitmap[3];
        BitmapDrawable drawable2 = (BitmapDrawable) ship2r.getDrawable();
        drawable2.setBounds(0,0,drawable2.getIntrinsicWidth(),drawable2.getIntrinsicHeight());
        Bitmap bitmap2 = drawable2.getBitmap();
        Bitmap scaledBitmap2 = Bitmap.createScaledBitmap(bitmap2, bitmap2.getWidth(), bitmap2.getHeight(), true);


        draw73 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight()/4));
        draw83 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap2, 0, bitmap2.getHeight()/4, bitmap2.getWidth(), bitmap2.getHeight()/4));
        draw93 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap2, 0, 2*bitmap2.getHeight()/4, bitmap2.getWidth(), bitmap2.getHeight()/4));
        draw103 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap2, 0, 3*bitmap2.getHeight()/4, bitmap2.getWidth(), bitmap2.getHeight()/4));


        B42.setBackground(draw73);
        B52.setBackground(draw83);
        B62.setBackground(draw93);
        B72.setBackground(draw103);


        //Coluna 4 - ship4r

        Drawable draw84;
        Drawable draw94;
        Drawable draw104;

        BitmapDrawable drawable3 = (BitmapDrawable) ship4r.getDrawable();
        drawable3.setBounds(0,0,drawable3.getIntrinsicWidth(),drawable3.getIntrinsicHeight());
        Bitmap bitmap3 = drawable3.getBitmap();
        Bitmap scaledBitmap3 = Bitmap.createScaledBitmap(bitmap3, bitmap3.getWidth(), bitmap3.getHeight(), true);

        draw84 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap3, 0, 0, bitmap3.getWidth(), bitmap3.getHeight()/3));
        draw94 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap3, 0, bitmap3.getHeight()/3, bitmap3.getWidth(), bitmap3.getHeight()/3));
        draw104 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap3, 0, 2*bitmap3.getHeight()/3, bitmap3.getWidth(), bitmap3.getHeight()/3));

        B55.setBackground(draw84);
        B65.setBackground(draw94);
        B75.setBackground(draw104);

        //Coluna 5 - ship5r

        Drawable draw95;
        Drawable draw105;

        BitmapDrawable drawable4 = (BitmapDrawable) ship5r.getDrawable();
        drawable4.setBounds(0,0,drawable4.getIntrinsicWidth(),drawable4.getIntrinsicHeight());
        Bitmap bitmap4 = drawable4.getBitmap();
        Bitmap scaledBitmap4 = Bitmap.createScaledBitmap(bitmap4, bitmap4.getWidth(), bitmap4.getHeight(), true);


        draw95 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap4, 0, 0, bitmap4.getWidth(), bitmap4.getHeight()/2));
        draw105 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap4, 0, bitmap4.getHeight()/2, bitmap4.getWidth(), bitmap4.getHeight()/2));

        B28.setBackground(draw95);
        B38.setBackground(draw105);
*/


}

