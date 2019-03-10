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
var message="blank";
var opacity=1;
var isView = false;
var check_sonar = 0;
var moveInt = 0;
var check_move = 0;
var flag = false;
var use_weapon = "Bomb";

function moveleft() {
    moveInt = 1;
    result = 0;
    sendXhr("POST", "/move", {game: game, moveInt: moveInt}, function(data) {
        game = data;
        check_move++;
        redrawGrid();
    });
}
function moveup() {
    moveInt = 2;
    result = 0;
    sendXhr("POST", "/move", {game: game, moveInt: moveInt}, function(data) {
        game = data;
        check_move++;
        redrawGrid();
    });
}
function moveright() {
    moveInt = 3;
    result = 0;
    sendXhr("POST", "/move", {game: game, moveInt: moveInt}, function(data) {
        game = data;
        check_move++;
        redrawGrid();
    });
}
function movedown() {
    moveInt = 4;
    result = 0;
    sendXhr("POST", "/move", {game: game, moveInt: moveInt}, function(data) {
        game = data;
        check_move++;
        redrawGrid();
    });
}
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



function markHits(board, elementId, surrenderText, laser) {

  var result = 0;
  var loop = 0;
  var x = 0;

  board.attacks.forEach((attack) => {
    loop +=1;
        if (elementId == "player"){
          player = 1;
        }else if (elementId == "opponent"){
          player = 2;
        }
    let className;
    if (attack.result === "MISS"){
      className = "miss";
      message="You missed"
      result = 1;

    } if (attack.result === "HIT"){
      className = "hit";
      message="You hit the opponents ship!"
      result = 2;
    }else if (attack.result === "SUNK"){
      className = "sink";
      message="You have SUNK an opponents ship!!"
      result = 3;
    }else if (attack.result === "SURRENDER"){
      result = 4;
    }else if (attack.result === "CQHIT"){
      message="You hit Captains Quarters!!"
      className = "cqhit";
      result = 2;
    }

    if (!document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.contains('sink')){
      document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
    }
  });
    if(flag == true) {
        moveInt = 0;
        result = 0;
        flag = false;
    }
    if(moveInt != 0) {
        result = 0;
        flag = true;
    }
    updatelog(player, result,surrenderText);

  //Mark SUNK ships from the boards sunk array

  board.hits.forEach((square) => {
    document.getElementById(elementId).rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("sink");
  });
}




function redrawGrid() {
  Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
  Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
  makeGrid(document.getElementById("opponent"), false);
  makeGrid(document.getElementById("player"), true);
  if (game === undefined) {
    return;
  }

  var sqnum = 0;


  game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {

    if(square.row  == ship.capq.row && square.column == ship.capq.column){
      document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("capq");
    }
    else{
      document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }

  }));

  //TEST99!!!/
  //THIS PRINTS OPPONENTS MAP
  ////////

//  game.opponentsBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {

//    if(square.row  == ship.capq.row && square.column == ship.capq.column){
//    }
//  else{
//      document.getElementById("opponent").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
//    }


//  }));
  var x = 0;
//  if( (message==="You hit the opponents ship!" || message==="You hit Captains Quarters!!" || message==="You have SUNK an opponents ship!!") && enemy_sunk >= 1){
//    x = 1;
  //  laserAttack(game.opponentsBoard, "opponent", "You won the game", 1);
//  }

if(x===0){
  markHits(game.opponentsBoard, "opponent", "You won the game", 0);
}
  fastmessage(message); //print result of hit message
  markHits(game.playersBoard, "player", "You lost the game", 0);
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
                message="You placed a ship"
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
            if(shipType === "SUBMARINE") {
                document.getElementById("place_submarine").removeEventListener("click", submarine);
                var name = document.getElementById("place_submarine");
                name.className = "grayout";
            }
            placedShips++;
            if (placedShips == 4) {
                var shipDiv = document.getElementById("ship-holder");
                var statsDiv = document.getElementById("stats-holder");
                shipDiv.style.display = "none";
                statsDiv.style.display = "block";
                document.getElementById("myInfo").innerHTML="Now Attacking:";
                document.getElementById("myInfo2").innerHTML="Click the map on the right to attack squares. Try hit your opponents ships. Sink all enemy ships before they get yours to win!!";

                isSetup = false;
                registerCellListener((e) => {});
            }
        });
    }else if(isView) {
        drawSonar(row, col);
    } else {
        sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
            game = data;
            redrawGrid();
        })
    }
}

function place_player_sonar(){
    if (enemy_sunk >= 1 && check_sonar < 2){
        isView = !isView;
    }else if (enemy_sunk < 1 || !isView || check_sonar > 2){
        alert("You cannot use sonar at this time!");
    }
}
function sendXhr(method, url, data, handler) {
  var req = new XMLHttpRequest();
  req.addEventListener("load", function(event) {
    if (req.status != 200) {
      if (req.status != 200) {
        if(isSetup){
          message="Please choose a valid location!"
          for(var i = 0; i < placedShips; i++){
            if(game.playersBoard.ships[i].kind == data.shipType)
            message="Please choose a new type of ship!!"
          }
          if(!shipType){
            message="Please choose a ship to place!!"
          }
        }
        if(!isSetup && moveInt == 0) {
            message="Please choose a new square"
        }
        if(moveInt == 0) {
            errormessage(message);
        }

        return;
      }
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


function fastmessage(message){

  document.getElementById("myPopup").innerHTML=message;
  document.getElementById("myPopup").style.opacity=1
  opacity=1
  window.setTimeout("hidemessage()", 200);

}

function errormessage(message){

  document.getElementById("myPopup").innerHTML=message;
  document.getElementById("myPopup").style.backgroundColor="red";
  document.getElementById("myPopup").style.opacity=1
  opacity=1
  window.setTimeout("hidemessage()", 1100);
}

function hidemessage(){
  opacity -= 0.1
  document.getElementById('myPopup').style.opacity=opacity;
  if (opacity < 0)
  {
    opacity = 1;
    document.getElementById("myPopup").style.backgroundColor="grey";
    return;}

    setTimeout("hidemessage()", 50)
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
    document.getElementById("place_submarine").addEventListener("click", submarine);
    sendXhr("GET", "/game", {}, function(data) {
      game = data;
    });
    if(mineFlag === true) {
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
  function submarine() {
    shipType = "SUBMARINE";
    registerCellListener(place(4));
    document.getElementById("place_minesweeper").removeEventListener("click", submarine);

  }


  function updatelog(player,result,surrenderText){
    if (player == 2){
      change_player(result,surrenderText);
    }else if (player == 1){
      change_enemy(result,surrenderText);
    }
  }

  function change_player(result,surrenderText){
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
    if(enemy_sunk > 0 && check_sonar < 2) {
      var sonarDiv = document.getElementById("place_player_sonar");
      sonarDiv.className = "buttonable";
      document.getElementById("place_player_sonar").addEventListener("click", place_player_sonar);
    }

    if(enemy_sunk > 1 && check_move < 2) {
        result = 0;
        document.getElementById("move_left").addEventListener("click", moveleft);
        document.getElementById("move_right").addEventListener("click", moveright);
        document.getElementById("move_up").addEventListener("click", moveup);
        document.getElementById("move_down").addEventListener("click", movedown);

        document.getElementById("move_left").className = "move_but";
        document.getElementById("move_right").className = "move_but";
        document.getElementById("move_up").className = "move_but";
        document.getElementById("move_down").className = "move_but";
    }

    if(check_move > 1) {
        document.getElementById("move_left").removeEventListener("click", moveleft);
        document.getElementById("move_right").removeEventListener("click", moveright);
        document.getElementById("move_up").removeEventListener("click", moveup);
        document.getElementById("move_down").removeEventListener("click", movedown);

        document.getElementById("move_left").className = "move_but_grey";
        document.getElementById("move_right").className = "move_but_grey";
        document.getElementById("move_up").className = "move_but_grey";
        document.getElementById("move_down").className = "move_but_grey";
    }

    //Catch for end-game
    if(enemy_sunk > 3)
    {   setTimeout(function()
      { alert(surrenderText);
        location.reload(true);
      },500);
    }

  }

  function change_enemy(result,surrenderText){
    if (result == 1){
      player_miss += 1;
      document.getElementById("player_miss").innerHTML = "Miss: " + player_miss;
    }else if (result == 2){
      player_hit += 1;
      document.getElementById("player_hit").innerHTML = "Hit: " + player_hit;
    }else if (result == 3){
      player_sunk += 1;
      player_hit += 1;
      document.getElementById("player_sunk").innerHTML = "Sunk: " + player_sunk;
      document.getElementById("player_hit").innerHTML = "Hit: " + player_hit;
    }

    //Catch for end-game
    if(player_sunk > 3){
      setTimeout(function()
      { alert(surrenderText);
        location.reload(true);
      },500);
    }

    if (enemy_sunk === 0){
          use_weapon = "Bomb";
          //console.log(use_weapon);
          document.getElementById("update_weapon").innerHTML="Weapon: " + use_weapon;
      }else if(enemy_sunk >= 1){
          use_weapon = "Laser";
          //console.log(use_weapon);
          document.getElementById("update_weapon").innerHTML="Weapon: " + use_weapon;
      }
  }
function drawSonar(xrow, ycol){
  //  document.getElementById("opponent").rows[x-1].cells[y.charCodeAt(0)-'A'.charCodeAt(0)].classList.add("occupied");
  check_sonar++;
  var sonAr = [];

  sonAr.push({x:xrow, y:ycol.charCodeAt(0)-65});

   sonAr.push({x:xrow+1, y:ycol.charCodeAt(0)-65});
   sonAr.push({x:xrow+2, y:ycol.charCodeAt(0)-65});
   sonAr.push({x:xrow-1, y:ycol.charCodeAt(0)-65});
   sonAr.push({x:xrow-2, y:ycol.charCodeAt(0)-65});


   sonAr.push({x:xrow, y:ycol.charCodeAt(0)+1-65});
   sonAr.push({x:xrow, y:ycol.charCodeAt(0)+2-65});
   sonAr.push({x:xrow, y:ycol.charCodeAt(0)-1-65});
   sonAr.push({x:xrow, y:ycol.charCodeAt(0)-2-65});


   sonAr.push({x:xrow+1, y:ycol.charCodeAt(0)+1-65});
   sonAr.push({x:xrow+1, y:ycol.charCodeAt(0)-1-65});
   sonAr.push({x:xrow-1, y:ycol.charCodeAt(0)+1-65});
   sonAr.push({x:xrow-1, y:ycol.charCodeAt(0)-1-65});


    for(i=0; i<13; i++){
    rownum = sonAr[i].x;
    colnum = sonAr[i].y;
        if(document.getElementById("opponent").rows[rownum-1]) {
            if(document.getElementById("opponent").rows[rownum-1].cells[colnum]) {
                 document.getElementById("opponent").rows[rownum-1].cells[colnum].classList.add("sonarSQ");
            }
        }
    }

    game.opponentsBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {

    var occSq = 0;

    for(i = 0; i<13; i++){
        rownum = sonAr[i].x;
        colnum = sonAr[i].y;
       // colnum.fromCharCode(colnum + 65);
       colchar = String.fromCharCode(colnum + 65);

        if(square.row == rownum && square.column == colchar) {
            document.getElementById("opponent").rows[rownum-1].cells[colnum].classList.remove("sonarSQ");
            document.getElementById("opponent").rows[rownum-1].cells[colnum].classList.add("occupied")

           //  document.getElementById("opponent").rows[rownum-1].cells[colnum].classList.add("occupied")
        }
    }
            isView = false;
   }));



    if(check_sonar === 2) {
        var sonarDiv = document.getElementById("place_player_sonar");
        document.getElementById("place_player_sonar").removeEventListener("click", place_player_sonar);
        sonarDiv.className = "buttonGrey";
    }
}
