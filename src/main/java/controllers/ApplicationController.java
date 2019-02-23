package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.*;
import ninja.Context;
import ninja.Result;
import ninja.Results;

import javax.validation.constraints.Null;

@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }

    public Result newGame() {
        Game g = new Game();
        return Results.json().render(g);
    }

    public Result placeShip(Context context, PlacementGameAction g) {
        System.out.println("Hello!");
        Game game = g.getGame();
        Ship ship = null;
        if(g.getShipType().equals("MINESWEEPER")) {
            System.out.print(g.getShipType());
            ship = new Minesweeper(g.getShipType());
        }
        if(g.getShipType().equals("DESTROYER")) {
            ship = new Destroyer(g.getShipType());
        }
        if(g.getShipType().equals("BATTLESHIP")) {
            ship = new Battleship(g.getShipType());
        }
        System.out.println("I should place something.");
        boolean result = game.placeShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result attack(Context context, AttackGameAction g) {
        Game game = g.getGame();
        boolean result = game.attack(g.getActionRow(), g.getActionColumn());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }
}
