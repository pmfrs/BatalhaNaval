package pt.iscte.batalhanaval;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlaceShipsActivity extends AppCompatActivity implements View.OnTouchListener {

    public GridLayout glT;
    private ImageView ship3r, ship2r, ship1r, ship4r, ship5r;
    private Ship s1, s2, s3, s4, s5;
    private Ship[] ships = new Ship[5];
    private Player player1, player2;

    private TextView B1;
    private TextView B2;
    private TextView B11;
    private TextView B21;
    private TextView B62;
    private TextView B52;
    private TextView B42;
    private TextView B72;
    private TextView B55;
    private TextView B65;
    private TextView B75;
    private TextView B28;
    private TextView B38;



    private TextView B01;
    private Button placeShips;
    private Button ready;

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

        glT = (GridLayout) findViewById(R.id.glTop);

        B1 = (TextView) findViewById(R.id.button00);
        B2 = (TextView) findViewById(R.id.button10);
        B11 = (TextView) findViewById((R.id.button11)) ;
        B01= (TextView) findViewById((R.id.button01));
        B21 = (TextView) findViewById(R.id.button21);
        B42 = (TextView) findViewById(R.id.button42);
        B52 = (TextView) findViewById(R.id.button52);
        B62 = (TextView) findViewById(R.id.button62);
        B72 = (TextView) findViewById(R.id.button72);
        B55 = (TextView) findViewById(R.id.button55);
        B65 = (TextView) findViewById(R.id.button65);
        B75 = (TextView) findViewById(R.id.button75);
        B38 = (TextView) findViewById(R.id.button38);
        B28 = (TextView) findViewById(R.id.button28);

        placeShips = (Button) findViewById(R.id.place_ships);
        ready = (Button) findViewById(R.id.ready);

        placeShips.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
                putShips();
              ready.setVisibility(View.VISIBLE);
            }
        }) ;

        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GameActivity.class );
                startActivity(i);




            }
        });






        player1 = (Player) getIntent().getSerializableExtra("Player1");
        player2 = (Player) getIntent().getSerializableExtra("Player2");

        // ship1r.setOnTouchListener(this);
        //ship2r.setOnTouchListener(this);






        // gridLayout = (GridLayout) findViewById(R.id.gridLayout8);


    }


    //Buttons


/*








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




    public void putShips(){
     //Coluna2 - ship1r

        Drawable draw82;
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



    }

    public void setGrid(){
        glT.setVisibility( View.VISIBLE);
    }




}

