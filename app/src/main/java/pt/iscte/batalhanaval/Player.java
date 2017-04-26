package pt.iscte.batalhanaval;

/**
 * Created by Sara on 13/04/2017.
 */

public class Player {
    private boolean turn;
    private String playerName;
    public Ship[] ships;
    private int [][] board;

    public Player (String playerName){
        turn = false;
        this.playerName = playerName;
        ships = new Ship[5];
        board = new int[10][10];


    }

    public void initBoard(){
        for(int x = 0; x<10; x++){
            for(int y=0; y<10; y++){
                board [x][y] = 0;
            }
        }
    }

    public boolean getTurn(){
        return turn;
    }

    public boolean startTurn(){
        return true;
    }

    public boolean endTurn(){
        return false;
    }

    public Ship [] getShips (){ return ships;}
    //public void setShips(Ship [] ships) {
         //ships1= ships;

    //}



    public void deleteShip (Ship ship){
        for(int i =0; i<5; i++){
            for (int j = 0 ; j<5; j++){
                if(board[i] [j] == ship.getIdShip())
                    board[i] [j] = 0;
            }
        }

    }

    public void addShipsToGrid(int row, int col, Ship ship){

        if(row+ship.getHeight() <= 10 && col+ship.getLenght() <= 10){

            //Fazer metodo para verificar se nao existe nenhum navio no mesmo sitio

            for(int i = row; i< row + ship.getHeight(); i++){
                board[i][col] = ship.getIdShip();
            }

            for(int j = col; j< col + ship.getLenght() ; j++){
                board[row][j] = ship.getIdShip();
            }

        }
    }
}
