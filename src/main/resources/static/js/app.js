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
                        var span = $('<span class="fas fa-bomb"></span>');
                        td.append(span);
                    } else {
                        var span = $('<span class="neighbouring"></span>');
                        td.append(span);
                        span.text(cell["neighbouringBombs"])
                    }
                }
            });
        });

        var playAgainBtn = $('<button class="btn btn-primary" type="button">PLAY AGAIN</button>');

        if (result["status"] === "LOST") {
            var resultDiv = $('<div class="alert alert-danger">YOU LOST!</div>');
            boardDiv.after(resultDiv);
            resultDiv.after(playAgainBtn);

        } else if (result["status"] === "WIN") {
            var resultDiv = $('<div class="alert alert-success">YOU WIN!</div>');
            boardDiv.after(resultDiv);
            resultDiv.after(playAgainBtn);
        }

        var buttons = $('.boardButton');
        buttons.mousedown(function (e) {
           if (e.button === 0) {
               uncover($(this).attr('x'), $(this).attr('y'));
           } else {
               flagButton($(this).attr('x'), $(this).attr('y'))
           }
        });



        playAgainBtn.on('click', function () {

            var levelDiv = $('#levelDiv');
            boardDiv.hide();
            resultDiv.hide();
            playAgainBtn.hide();
            levelDiv.show();

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
            var boardDiv = $('#boardDiv').html('');

            levelDiv.hide();
            boardDiv.show();
            getBoard()

        }).fail(function (xhr, status, err) {
            console.log(err);
        }).always(function (xhr, status) {
        });
    })

}
