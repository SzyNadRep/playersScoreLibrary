package Inne;

import java.util.ArrayList;
import java.util.Collections;


//TODO
//BŁĘDNA LOGIKA - LEPIEJ BEDIE DODAWAĆ ZEWNETRNIE DO BAZY DANYCH
public class MatchRound {

    public static void main(String[] args) {
        ArrayList<String> teams = new ArrayList<String>();
        teams.add("Poland");
        teams.add("France");
        teams.add("German");
        teams.add("Spain");

        ArrayList<ArrayList<String>> fixtures = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < teams.size()-1; i++) {
            ArrayList<String> roundNumber = new ArrayList<String>();
            Collections.shuffle(teams);

            for (int j = 3; j > 0; j--) {
                String homeTeam = teams.get(i);
                String awayTeam = teams.get(j);
                String match = homeTeam + " vs " + awayTeam;
                roundNumber.add(match);
            }

            fixtures.add(roundNumber);
        }

        for (int i = 0; i < fixtures.size(); i++) {
            System.out.println("Round " + (i+1) + " fixtures:");
            for (int j = 0; j < fixtures.get(i).size(); j++) {
                System.out.println(fixtures.get(i).get(j));
            }
            System.out.println();
        }

    }

}
