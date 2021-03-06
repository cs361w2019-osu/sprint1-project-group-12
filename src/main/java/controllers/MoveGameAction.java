package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs361.battleships.models.Game;

public class MoveGameAction {

    @JsonProperty private Game game;
    @JsonProperty private int moveInt;

    public Game getGame() {
        return game;
    }

    public int getMoveInt() {
        return moveInt;
    }
}
