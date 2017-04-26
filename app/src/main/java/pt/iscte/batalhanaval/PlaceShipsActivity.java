package pt.iscte.batalhanaval;

import android.content.ClipData;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

public class PlaceShipsActivity extends AppCompatActivity {

    private ImageView ship3r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ships);
        ship3r = (ImageView) findViewById (R.id.ship3r);
        //ship3r.setOnDragListener(dragListener);
        ship3r.setOnLongClickListener(longClickListener);
    }
    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);

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

            int dragEvent = event.getAction();
            switch (dragEvent){
                case DragEvent.ACTION_DRAG_ENTERED:
                    final View view = (View) event.getLocalState();
            }
            return true;
        }
    };
}
