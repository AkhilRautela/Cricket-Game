package com.cricketgame;

enum Teams{
    INDIA,
    AUSTRALIA,
    PAKISTAN,
    ENGLAND;
}

public class Team{
    String name;
    int score;
    int wickets;
    Player[] players;

    Team(String name) {
        try{
            this.name = Teams.valueOf(name).toString();
            this.score = 0;
            this.wickets = 0;
        }
        catch (Exception e){
            System.out.println(name + " Team does not exist. ");
            System.exit(0);
        }
    }

    void getPlayers() {
        //fetch Players from database
        players = new Player[11];

        for (int i = 0; i < 11; i++) {
            players[i] = new Player("player " + (i + 1), 10, 10, 10);
        }

    }


}
