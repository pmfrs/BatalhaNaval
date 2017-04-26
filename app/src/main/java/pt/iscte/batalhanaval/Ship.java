package pt.iscte.batalhanaval;

import android.widget.ImageView;

/**
 * Created by Sara on 13/04/2017.
 */

public class Ship {
    public int lenght, height, imageID;
    private boolean placed;
    public int idShip, viewID;

    public Ship(int lenght, int height, int imageID, int idShip, int viewID){
        this.height = height;
        this.idShip = idShip;
        this.viewID = viewID;
        this.imageID = imageID;
    }

    public int getHeight(){
        return height;
    }

    public int getLenght(){
        return lenght;
    }
    public int getIdShip(){
        return idShip;
    }
    public int getViewID() { return viewID; }
    public int getImageID() {return imageID; }

    public Ship(){
        viewID=0;
        height=3;
        imageID=0;
        lenght=1;
        idShip=1;

    }



}
