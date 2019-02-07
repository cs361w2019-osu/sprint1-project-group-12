var isSetup = true;
var placedShips = 0;
var game;
var shipType;
var vertical;
var player_hit = 0;
var player_miss = 0;
var player_sunk = 0;
var enemy_hit = 0;
var enemy_miss = 0;
var enemy_sunk = 0;
var player = 1;
var enemy = 2;

function makeGrid(table, isPlayer) {
    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        for (j=0; j<10; j++) {
            let column = document.createElement('td');
            column.addEventListener("click", cellClick);
            row.appendChild(column);
        }
        table.appendChild(row);
    }
}

function markHits(board, elementId, surrenderText) {

    var result = 0;

    board.attacks.forEach((attack) => {
        let className;
        if (attack.result === "MISS"){
            className = "miss";
            result = 1;

        }else if (attack.result === "HIT"){
            className = "hit";
            result = 2;

        }else if (attack.result === "SUNK"){
            className = "hit"
                result = 3;

        }else if (attack.result === "SURRENDER"){
            alert(surrenderText);
        }

        if (elementId == "player"){
            player = 1;
        }else if (elementId == "opponent"){
            player = 2;
        }

        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
    });

    updatelog(player, result);
}

function redrawGrid() {
    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));
    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if (isSetup) {
        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical}, function(data) {
            game = data;
            redrawGrid();
            if(shipType === "MINESWEEPER") {
                document.getElementById("place_minesweeper").removeEventListener("click", minesweeper);
                var name = document.getElementById("place_minesweeper");
                name.className = "grayout";
            }
            if(shipType === "DESTROYER") {
                document.getElementById("place_destroyer").removeEventListener("click", destroyer);
                var name = document.getElementById("place_destroyer");
                name.className = "grayout";
            }
            if(shipType === "BATTLESHIP") {
                document.getElementById("place_battleship").removeEventListener("click", battleship);
                var name = document.getElementById("place_battleship");
                name.className = "grayout";
            }
            placedShips++;
            if (placedShips == 3) {
                var shipDiv = document.getElementById("ship-holder");
                var statsDiv = document.getElementById("stats-holder");
                shipDiv.style.display = "none";
                statsDiv.style.display = "block";
                isSetup = false;
                registerCellListener((e) => {});
            }
        });
    } else {
        sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
            game = data;
            redrawGrid();
        })
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            alert("Cannot complete the action");
            return;
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

function place(size) {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        vertical = document.getElementById("is_vertical").checked;
        let table = document.getElementById("player");
        for (let i=0; i<size; i++) {
            let cell;
            if(vertical) {
                let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else {
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }
            cell.classList.toggle("placed");
        }
    }
}

function initGame() {
    let mineFlag = false;
    let destFlag = false;
    let battFlag = false;
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    document.getElementById("place_minesweeper").addEventListener("click", minesweeper);
    document.getElementById("place_destroyer").addEventListener("click", destroyer);
    document.getElementById("place_battleship").addEventListener("click", battleship);
    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });
    if(mineFlag === true) {
        console.log("works");

    }

};

function minesweeper() {
    shipType = "MINESWEEPER";
    registerCellListener(place(2));
};

function destroyer() {
    shipType = "DESTROYER";
    registerCellListener(place(3));
    document.getElementById("place_minesweeper").removeEventListener("click", destroyer);

}

function battleship() {
    shipType = "BATTLESHIP";
    registerCellListener(place(4));
    document.getElementById("place_minesweeper").removeEventListener("click", battleship);

}


function updatelog(player,result){
    if (player == 2){
        change_player(result);
    }else if (player == 1){
        change_enemy(result);
    }
}

function change_player(result){
    if (result == 1){
        enemy_miss += 1;
        document.getElementById("enemy_miss").innerHTML = "Miss: " + enemy_miss;
    }else if (result == 2){
        enemy_hit += 1;
        document.getElementById("enemy_hit").innerHTML = "Hit: " + enemy_hit;
    }else if (result == 3){
        enemy_sunk += 1;
        enemy_hit += 1;
        document.getElementById("enemy_sunk").innerHTML = "Sunk: " + enemy_sunk;
        document.getElementById("enemy_hit").innerHTML = "Hit: " + enemy_hit;
    }
}

function change_enemy(result){
    if (result == 1){
        player_miss += 1;
        document.getElementById("player_miss").innerHTML = "Miss: " + player_miss;
    }else if (result == 2){
        player_hit += 1;
        document.getElementById("player_hit").innerHTML = "Hit: " + player_hit;
    }else if (result == 3){
        player_sunk += 1;
        enemy_hit += 1;
        document.getElementById("player_sunk").innerHTML = "Sunk: " + player_sunk;
        document.getElementById("player_hit").innerHTML = "Hit: " + player_hit;
    }
}


