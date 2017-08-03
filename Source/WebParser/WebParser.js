var matches = []
var size = 0

/*
  Parses from the ranking.txt file to the global matches array.
  I assumed that, since the processing of this module is short,
  it was unecessary to divide this in multiple modules.
*/
$(document).ready(function() {
  $.get('../../Outputs/ranking.txt', function(text) {
    var lines = text.split("\n");
    var parsing = false;
    var match = new Array();
    for (line in lines) {
      if (!parsing && lines[line].substring(0, 4) == "game") {
        parsing = true;
        match = new Array();
      } else if (parsing && lines[line].charAt(0) == '}') {
        parsing = false;
        matches[matches.length] = match;
      } else if (parsing) {
        var username = lines[line].substring(lines[line].indexOf("\""), lines[line].lastIndexOf("\"") + 1);
        var halves = lines[line].split(",");
        var words = halves[1].split(" ");
        match[match.length] = new Array(username, words[2]);
      }
    }
  });
});

/*
  Waits until the reading is done,
  and calls the populating method.
*/
function waitReading() {
  if (matches.length > 0) {
    populateSelect();
  } else {
    setTimeout(waitReading, 100);
  }
}

waitReading();

/*
  Responsible for providing options in the select tag
  so the user can choose what match's rank he wants to see.
*/
function populateSelect() {
  for(var match = 0; match < matches.length; match++) {
    $('.matchOption').append($('<option>', {
      value: 'Game ' + (match + 1),
      text: 'Game ' + (match + 1)
    }));
  }
}

var matchSelector = document.getElementById("selectMatch");

matchSelector.addEventListener('change', function() {
  var value = matchSelector.value.split(" ");
  updateData(parseInt(value[1]));
});

/*
  Method responsible for deleting all row tags in the table
  and updating the table with the new rows corresponding to the
  current match selected.
*/
function updateData(option) {
  players = matches[option - 1]
  var tableDiv = document.getElementById("tableid");

  var toDelete = tableDiv.getElementsByClassName("row");
  while (toDelete[0]) {
    toDelete[0].parentNode.removeChild(toDelete[0]);
  }

  var counter = 0;
  for(index in players) {
    var positionDiv = document.createElement("div");
    positionDiv.className = "cell";
    positionDiv.innerHTML = ++counter;

    var usernameDiv = document.createElement("div");
    usernameDiv.className = "cell";
    usernameDiv.innerHTML = players[index][0].substr(1, players[index][0].length - 2);

    var killsDiv = document.createElement("div");
    killsDiv.className = "cell";
    killsDiv.innerHTML = players[index][1];

    var playerDiv = document.createElement("div");
    playerDiv.className = "row";
    playerDiv.appendChild(positionDiv);
    playerDiv.appendChild(usernameDiv);
    playerDiv.appendChild(killsDiv);

    tableDiv.appendChild(playerDiv);
  }
}
