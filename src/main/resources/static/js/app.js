$(function () {

    getBoard()

});

function getBoard() {
    $.ajax({
        url: "http://localhost:8080/board",
        data: {},
        type: "GET",
        dataType: "json"
    }).done(function (result) {

        var boardDiv = $('#boardDiv').html('');
        var table = $('<table id="board">');
        boardDiv.append(table);

        var boardArray = result["cells"];

        $.each(boardArray, function (i, row) {
            var tr = $('<tr>');
            table.append(tr);
            $.each(row, function (j, cell) {
                var td = $('<td>');
                tr.append(td);
                var button = $('<button class="boardButton" type="button"></button>');
                if (cell["isRevealed"] === false) {
                    button.appendTo(td);
                    button.attr('x', i).attr('y', j);
                    if (result["status"] === "LOST" || result["status"] === "WIN") {
                        button.prop("disabled", true);
                    }
                } else {
                    td.text(cell["neighbourBombs"]);
                }
            });
        });

        if (result["status"] === "LOST") {
            var lostDiv = $('<div class="lost">YOU LOST!</div>');
            boardDiv.after(lostDiv);
        } else if (result["status"] === "WIN") {
            var winDiv = $('<div class="win">YOU WIN!</div>');
            boardDiv.after(winDiv);
        }

        var buttons = $('button');
        buttons.on('click', function () {
            uncover($(this).attr('x'), $(this).attr('y'));
        })

    }).fail(function (xhr, status, err) {
    }).always(function (xhr, status) {
    });

}

function uncover(x, y) {
    $.ajax({
        url: "http://localhost:8080/board",
        data: {x: x, y: y},
        type: "POST",
        dataType: "json"
    }).done(function (result) {

        getBoard()

    }).fail(function (xhr, status, err) {
    }).always(function (xhr, status) {
    });

}
