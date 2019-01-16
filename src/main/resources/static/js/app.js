$(function () {

    setDifficultyLevel()

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
                td.addClass("cell");
                var button = $('<button class="boardButton" type="button"></button>');
                if (cell["isRevealed"] === false) {
                    button.appendTo(td);
                    button.attr('x', i).attr('y', j);
                    if (result["status"] === "LOST" || result["status"] === "WIN") {
                        button.prop("disabled", true);
                    }
                    if (cell["isFlagged"]) {
                        var span = $('<span>');
                        span.addClass("fas fa-flag");
                        button.append(span);
                    }
                } else {
                    if (cell["isBomb"]) {
                        var span = $('<span>');
                        span.addClass("fas fa-bomb");
                        td.append(span);
                    } else {
                        var span = $('<span>');
                        span.addClass("neighbouring");
                        td.append(span);
                        span.text(cell["neighbouringBombs"])
                    }
                }
            });
        });

        if (result["status"] === "LOST") {
            var lostDiv = $('<div class="alert alert-danger">YOU LOST!</div>');
            boardDiv.after(lostDiv);
        } else if (result["status"] === "WIN") {
            var winDiv = $('<div class="alert alert-success">YOU WIN!</div>');
            boardDiv.after(winDiv);
        }

        var buttons = $('button');
        buttons.mousedown(function (e) {
           if (e.button === 0) {
               uncover($(this).attr('x'), $(this).attr('y'));
           } else {
               flagButton($(this).attr('x'), $(this).attr('y'))
           }
        });



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

function flagButton(x, y) {
    $.ajax({
        url: "http://localhost:8080/flag",
        data: {x: x, y: y},
        type: "POST",
        dataType: "json"
    }).done(function (result) {

        getBoard()

    }).fail(function (xhr, status, err) {
    }).always(function (xhr, status) {
    });
}

function setDifficultyLevel() {
    var submitBtn = $('#submitButton');

    submitBtn.on('click', function () {
        var selectedDifficulty = $("select[name='level']").val();
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: "http://localhost:8080/newgame",
            data: selectedDifficulty,
            type: "POST",
            dataType: "json"

        }).done(function (result) {

            var levelDiv = $('#levelDiv');
            levelDiv.hide();
            getBoard()

        }).fail(function (xhr, status, err) {
        }).always(function (xhr, status) {
        });
    })

}
