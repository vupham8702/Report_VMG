(function ($) {
    // USE STRICT
    "use strict";
    try {
        $(function () {
            const urlParams = new URLSearchParams(window.location.search);
            const id = urlParams.get('id');
            $.ajax({
                url: "/User/api/fileDetail?id=" + id,
                method: "get",
                success: function (fileDetail) {
                    let tableHTML = `<table class="table table-striped table-hover mr-auto m-sm-4 bg-light" id="table_file">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên báo cáo </th>
                                <th>Doanh thu</th>
                                <th>Nợ</th>
                                <th>Chi phí</th>
                                <th>Lợi nhuận</th>
                                <th>Ngày gửi</th>
                                <th>Người gửi</th>
                            </tr>
                        </thead>
                        <tbody  id="fileDetail">
                        <tr>
                            <td>${fileDetail.id}</td>
                            <td>${fileDetail.name}</td>
                            <td>${fileDetail.revenue}</td>
                            <td>${fileDetail.accountsPayable}</td>
                            <td>${fileDetail.expenses}</td>
                            <td>${fileDetail.profit}</td>
                            <td>${fileDetail.createAt}</td>
                            <td >${fileDetail.createBy}</td>
                        </tr>`;

                    $("#table_file").html(tableHTML);

                },
                error: function (xhr, status, error) {
                    console.log(xhr.responseText);
                }
            })
        })
    } catch (e) {
        console.log(e);
    }
})(jQuery);
(function ($) {
   // USE STRICT
    "use strict";
    try {
        $("#createReportUserBtn").on('click', function (e) {
            e.preventDefault();
            Swal.fire({
                title: "Bạn có chắc muốn tạo không ?",
                showDenyButton: true,
                confirmButtonText: "Có",
                denyButtonText: "Không",
            }).then((result) => {
                if (result.isConfirmed) {
                    const form = $("#createReportUserForm")[0];
                    const data = new FormData(form);

                    $.ajax({
                        url: "/User/api/createReport",
                        data: data,
                        method: 'post',
                        enctype: 'multipart/form-data',
                        contentType: false,
                        processData: false,
                        success: function () {
                            Swal.fire("Đã tạo mới!", "", "success");
                        },
                        error: function () {
                            Swal.fire("Chưa lưu thay đổi", "", "info");
                        },
                    })
                } else if (result.isDenied) {
                    Swal.fire("Changes are not saved", "", "info");
                }
            });
        })
    } catch (e) {
        console.log(e);
    }
})(jQuery);