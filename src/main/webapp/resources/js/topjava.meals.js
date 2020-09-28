const mealUrl = "profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return formatDate(date);
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if (!data.excess) {
                    $(row).attr("data-mealExcess", false);
                } else {
                    $(row).attr("data-mealExcess", true);
                }
            }
        }),
        updateTable: function () {
            $.get(mealUrl, updateTableByData);
        }
    });
});

$('#startDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
});

$('#endDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
});

$('#startTime').datetimepicker({
    datepicker:false,
    format:'H:i'
});

$('#endTime').datetimepicker({
    datepicker:false,
    format:'H:i'
});

$('#dateTime').datetimepicker({
    format: 'Y-m-d H:i'
});