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

function enable(checkbox, id) {
    const enabled = checkbox.is(":checked");

    $.ajax({
        url: userAjaxUrl + id,
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        checkbox.closest("tr").attr("data-userEnabled", enabled);
        successNoty("Updated")
    });
}