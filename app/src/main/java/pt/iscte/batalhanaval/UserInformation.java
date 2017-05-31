package pt.iscte.batalhanaval;

/**
 * Created by pedro on 31/05/2017.
 */

public class UserInformation {

    public String nickname;
    public int wins;
    public int loses;

    public UserInformation(){

    }

    public UserInformation(String nicknameIn, int winsIn, int losesIn){
        nickname = nicknameIn;
        wins = winsIn;
        loses = losesIn;
    }


    public String getNickname() {
        return nickname;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }
}
