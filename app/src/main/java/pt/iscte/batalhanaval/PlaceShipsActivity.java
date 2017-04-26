package pt.iscte.batalhanaval;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
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

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlaceShipsActivity extends AppCompatActivity implements View.OnTouchListener {
    private LinearLayout linearLayout;
    private GridLayout gridLayout;

    private ImageView ship3r, ship2r, ship1r, ship4r, ship5r;
    private Ship s1, s2, s3, s4, s5;
    private Ship[] ships = new Ship[5];
    private Player player1, player2;
    private ImageButton button1;
    private ImageButton button2;
    private ImageButton button8;
    private ImageButton button9;
    private Button button17;
    private Button button18;
    private Button button19;
    private Button button16;
    private ImageButton button10;
    private Button button20;
    private Button button29;
    private Button button30;
    private Button button28;
    private Button button38;
    private Button button39;
    private Button button37;
    private Button button48;
    private Button button49;
    private GridLayout left;
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

        button1 = (ImageButton) findViewById(R.id.button1);
        button2 = (ImageButton) findViewById(R.id.button2);

        left = (GridLayout) findViewById(R.id.left);



        //ship1r.setOnTouchListener(this);
        //ship2r.setOnTouchListener(this);


        player1 = (Player) getIntent().getSerializableExtra("Player1");
        player2 = (Player) getIntent().getSerializableExtra("Player2");


        ship1r.setOnLongClickListener(longClickListener);
        button1.setOnDragListener(dragListener);
        button2.setOnDragListener(dragListener);
        // t = (Button) findViewById(R.id.t);


        // ship3r.setOnDragListener(dragListener);
        // ship3r.setOnLongClickListener(longClickListener);

        //Drawable dr = button8.getDrawable();
        // button9.setOnLongClickListener(longClickListener);


        //  button9.setOnLongClickListener(longClickListener);
        //ship5r.setOnLongClickListener(longClickListener ) ;


        //ship3r.setOnLongClickListener(longClickListener);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        // gridLayout = (GridLayout) findViewById(R.id.gridLayout8);



    }



        //Buttons

    /*
        button8 = (ImageButton) findViewById(R.id.button8);
        button9 = (ImageButton) findViewById(R.id.button9);
        button10 = (ImageButton) findViewById(R.id.button10);
        button17 = (Button) findViewById(R.id.button17);
        button18 = (Button) findViewById(R.id.button18);
        button19 = (Button) findViewById(R.id.button19);
        button16 = (Button) findViewById(R.id.button16);
        button20 = (Button) findViewById(R.id.button20);
        button29 = (Button) findViewById(R.id.button29);
        button30 = (Button) findViewById(R.id.button30);
        button28 = (Button) findViewById(R.id.button28);
        button37 = (Button) findViewById(R.id.button37);
        button38 = (Button) findViewById(R.id.button38);
        button39 = (Button) findViewById(R.id.button39);
        button48 = (Button) findViewById(R.id.button48);
        button49 = (Button) findViewById(R.id.button49);

        //Coluna1 - ship3r
        Drawable draw81;
        Drawable draw91;
        Drawable draw11;

        Bitmap[] x = new Bitmap[3];
        BitmapDrawable drawable = (BitmapDrawable) ship3r.getDrawable();
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        draw81 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()/3));
        draw91 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap, 0, bitmap.getHeight()/3 , bitmap.getWidth(), bitmap.getHeight()/3) );
        draw11 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap, 0,(2*bitmap.getHeight()/3), bitmap.getWidth(), bitmap.getHeight()/3) );

       // button8.setBackground(draw81);

        button8.setImageDrawable(draw81);
        button9.setImageDrawable(draw91);
        button10.setImageDrawable(draw11);

        //Coluna2 - ship1r
        Drawable draw62;
        Drawable draw72;
        Drawable draw82;
        Drawable draw92;

        Bitmap[] x2 = new Bitmap[2];
        BitmapDrawable drawable1 = (BitmapDrawable) ship1r.getDrawable();
        drawable1.setBounds(0,0,drawable1.getIntrinsicWidth(),drawable1.getIntrinsicHeight());
        Bitmap bitmap1 = drawable1.getBitmap();
        Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);



        draw82 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight()/2));
        draw92 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap1, 0, bitmap1.getHeight()/2, bitmap1.getWidth(), bitmap1.getHeight()/2));



        button19.setBackground(draw82);
        button20.setBackground(draw92);

        //Coluna 3 - ship2r
        Drawable draw73;
        Drawable draw83;
        Drawable draw93;

        Bitmap[] x3 = new Bitmap[3];
        BitmapDrawable drawable2 = (BitmapDrawable) ship2r.getDrawable();
        drawable2.setBounds(0,0,drawable2.getIntrinsicWidth(),drawable2.getIntrinsicHeight());
        Bitmap bitmap2 = drawable2.getBitmap();
        Bitmap scaledBitmap2 = Bitmap.createScaledBitmap(bitmap2, bitmap2.getWidth(), bitmap2.getHeight(), true);


        draw73 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight()/3));
        draw83 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap2, 0, bitmap2.getHeight()/3, bitmap2.getWidth(), bitmap2.getHeight()/3));
        draw93 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap2, 0, 2*bitmap2.getHeight()/3, bitmap2.getWidth(), bitmap2.getHeight()/3));

        button28.setBackground(draw73);
        button29.setBackground(draw83);
        button30.setBackground(draw93);


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

        button37.setBackground(draw84);
        button38.setBackground(draw94);
        button39.setBackground(draw104);

        //Coluna 5 - ship5r


        Drawable draw95;
        Drawable draw105;

        BitmapDrawable drawable4 = (BitmapDrawable) ship5r.getDrawable();
        drawable4.setBounds(0,0,drawable4.getIntrinsicWidth(),drawable4.getIntrinsicHeight());
        Bitmap bitmap4 = drawable4.getBitmap();
        Bitmap scaledBitmap4 = Bitmap.createScaledBitmap(bitmap4, bitmap4.getWidth(), bitmap4.getHeight(), true);


        draw95 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap4, 0, 0, bitmap4.getWidth(), bitmap4.getHeight()/2));
        draw105 = new BitmapDrawable(getResources(), Bitmap.createBitmap(scaledBitmap4, 0, bitmap4.getHeight()/2, bitmap4.getWidth(), bitmap4.getHeight()/2));

        button48.setBackground(draw95);
        button49.setBackground(draw105);

       // button9.setOnLongClickListener(longClickListener);



      // gridLayout.addView(button8, 1);
       //gridLayout.addView(button9);
       //gridLayout.addView(button10);

       //gridLayout.setOnLongClickListener(longClickListener);





    }





*/
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
            switch (dragEvent){
                case DragEvent.ACTION_DRAG_ENTERED:


                case DragEvent.ACTION_DROP:
                    if(view.getId() == R.id.ship1r){
                            GridView left1;
                            left1 = (GridView) findViewById(R.id.left);
                            view.animate()
                                    .x(linearLayout.getX())
                                    .y(linearLayout.getY())
                                    .setDuration(200)
                                    .start();

                        }

            }
            return true;
        }
    };



    public void ready(View view) {

        if(player1 == null || player2 == null)
        {
            player1 = (Player)getIntent().getSerializableExtra("Player1");
            player2 = (Player)getIntent().getSerializableExtra("Player2");
        }

        s1 = new Ship(2, 0, 1,  R.drawable.ship1r, R.id.ship1r);
        s2 = new Ship(3, 0, 2, R.drawable.ship2r, R.id.ship2r );
        s3 = new Ship(2, 0, 3, R.drawable.ship3r,  R.id.ship3r);
        s4 = new Ship(3, 0, 4, R.drawable.ship4r, R.id.ship4r);
        s5 = new Ship(2, 0, 5, R.drawable.ship5r, R.id.ship5r);

        ships[0] = s1;
        ships[1] = s2;
        ships[2] = s3;
        ships[3] = s4;
        ships[4] = s5;

        player1.ships = ships;
        player2.ships= ships;


        Log.d("readdy ", "entrei no ready");

    }

    float x = 0, y = 0, xf = 0, yf = 0;
    boolean moving = false;
    int i;
    @Override
    public boolean onTouch(View view, MotionEvent event) {


        for (i = 0; i < ships.length; i++)
       // Log.d("tag",  Integer.toString(view.getId()));

        if (view.getId() ==  findViewById(ships[i].viewID).getId())
            break;
        Log.d("Aqui ", "passou o erro");
    //    player1.deleteShip(ships[i]);
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.d("sar", "ontouch : estou aqui");
                x = view.getX() - event.getRawX();
                y = view.getY() - event.getRawY();
                moving = true;
                break;


            case MotionEvent.ACTION_MOVE:

                //if (moving == true) {
                    Log.d("Agora ", "ontouch : action move");

                    xf = event.getRawX() + x;
                    yf = event.getRawY() + y;
                    view.setX(xf);
                    view.setY(yf);


                    findViewById(ships[i].viewID).setBackgroundResource(ships[i].imageID);
                    Log.d("action_move", "entrei no action move");


                break;
        }


        return true;

    }



    }

