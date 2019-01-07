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

        $('#board').html('');

        $.each(result, function (i, row) {
            var tr = $('<tr>');
            $('#board').append(tr);
            $.each(row, function (j, cell) {
                var td = $('<td>');
                tr.append(td);
                var button = $('<button type="button"></button>');
                if (cell === false) {
                    button.appendTo(td);
                    button.attr('x', i).attr('y', j);
                } else {
                    td.text(cell);
                }
            });
        });

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
        data: {x : x, y: y},
        type: "POST",
        dataType: "json"
    }).done(function (result) {

        getBoard()

    }).fail(function (xhr, status, err) {
    }).always(function (xhr, status) {
    });

}