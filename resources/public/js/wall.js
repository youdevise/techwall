$(document).ready(function() {
    var wallId = decodeURIComponent((new RegExp("[?|&]id=([\\d]+)").exec(location.search)||[,""])[1])||null;
    if (!wallId) {
        window.location.replace("/");
    }

    function addEntry($columnView, name) {
        $columnView.find(".entry").realise().text(name);
    }

    function renderWall(wall) {
        $("#title").text(wall.name);
        document.title = wall.name;
        
        $.each(wall.columns, function(i, column) {
            var columnView = $(".column").realise();
            columnView.find(".name").text(column.name);
            $.each(column.entries, function(j, entry) {
                addEntry(columnView, entry);
            });
        });
    }

    function hintTechnologies(names) {
        $(".tags").autocomplete({
            source: names
        });
    }

    $.getJSON("/walls/" + wallId + "/wall.json", renderWall);
    $.getJSON("/technologies.json", hintTechnologies);
    
    $(".connectedSortable").sortable({
        connectWith: ".connectedSortable"
    }).disableSelection();

    $(".tags").keypress(function(e) {
        if (e.keyCode === $.ui.keyCode.ENTER) {
            var $el = $(this);
            addEntry($el.closest(".column"), $el.val());
            $el.val("");
        }
    });
});