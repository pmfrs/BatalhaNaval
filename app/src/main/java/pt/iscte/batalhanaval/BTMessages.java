package pt.iscte.batalhanaval;

import android.util.Log;

import java.util.Random;

/**
 * Created by pedro on 27/05/2017.
 */

public class BTMessages {
    //Mensagens de Bluetooth vão ser constituidas por três parâmetros
    //O primeiro parâmetro consiste no evento, o segundo numa coordenada e o terceira para um extra content
    //O evento e o extra vai ser constituido por 1 carater e a coordenada por dois

    //Evento 1 - Envio de tiro
    //Evento 2 - Feedback tiro
    //Evento 3 - Envio de Random, para decidir quem começa primeiro
    //Evento 4 - Dá a vez de jogar ao outro jogador

    private static String TAG = "BTMessages";

    public static String sendShot(int coordenada){
        String message="";

        int evento = 1;
        int shot = coordenada;
        int extra = 0;

        if(shot < 10){
            message = "" + evento + "0" + shot + extra;
        } else {
            message = "" + evento + shot + extra;
        }

        return message;
    }

    public static String sendShotFeedback(int coordenada, boolean success){
        String message = "";

        int evento = 2;
        int shot = coordenada;

        //Extra a 2 representa um miss shot
        //Extra a 1 representa um right shot
        int extra = 2;
        if(success) extra = 1;

        if(shot < 10){
            message = "" + evento + "0" + shot  +extra;
        } else {
            message = "" + evento + shot + extra;
        }

        return message = "";
    }

    public static String sendFirstContact(){
        String message = "";

        Random rand = new Random();
        message = "" + 3 + "00" + rand.nextInt(100);

        return message;
    }

    public static String yourTurn(){
        String message = "";

        message = "" + 4 + "000";

        return message;
    }

    public static int[] decodMessage(String message){
        int[] result = new int[3];

        int f = 0;
        int s = 0;
        int t = 0;

        try{
             f = Integer.parseInt(message.substring(0,1));
        } catch (NumberFormatException e){
             f = 0;
            Log.e(TAG, "Error decoding message.");
        }

        try{
             s = Integer.parseInt((message.substring(1,3)));
        } catch (NumberFormatException e){
             s = 0;
            Log.e(TAG, "Error decoding message.");
        }

        try{
             t = Integer.parseInt(message.substring(3));
        } catch (NumberFormatException e){
             t = 0;
            Log.e(TAG, "Error decoding message.");
        }

        result[0] = f;
        result[1] = s;
        result[2] = t;

        return result;
    }
}
