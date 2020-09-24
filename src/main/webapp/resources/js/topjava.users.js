const userAjaxUrl = "ajax/admin/users/";

// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: userAjaxUrl,
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            }),
            updateTable: function () {
                $.get(userAjaxUrl, updateTableByData);
            }
        }
    );
});

function enable(checked, id) {
    $.ajax({
        url: userAjaxUrl + id,
        type: "POST",
        data: "enabled=" + checked
    }).done(function () {
        successNoty("Updated");
        updateTableByData();
    });
}