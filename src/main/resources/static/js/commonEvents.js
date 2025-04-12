$(function() {
    $("#searchText").on("keydown", function(e) {
        if (e.key === 'Enter') {
            filterData();
        }
    });
});