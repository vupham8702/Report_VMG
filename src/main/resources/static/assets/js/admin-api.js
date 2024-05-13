(function ($) {
    // USE STRICT
    "use strict";
    try {
        $(function () {
            $.ajax({
                url: "http://localhost:8080/Admin/api/adminHome",
                method: 'get',
                success: function (data) {
                    $("#username").text(data.username);
                    $("#sumRevenue").text(data.sumRevenue);
                    $("#sumAccountsPayable").text(data.sumAccountsPayable);
                    $("#sumExpenses").text(data.sumExpenses);
                    $("#sumProfit").text(data.sumProfit);
                },
                error: function (err) {
                    console.log(err);
                },
            });
        });
    } catch (error) {
        console.log(error);

    }
})(jQuery);
(function ($) {
    "use strict"
    try {
        $("#createUserBtn").on('click', function (e) {
            e.preventDefault();
            Swal.fire({
                title: "Bạn có chắc muốn tạo người dùng mới không ?",
                showDenyButton: true,
                confirmButtonText: "Có",
                denyButtonText: "Không",
            }).then((result) => {
                if (result.isConfirmed) {
                    // $("#createUser").submit();
                    $.ajax({
                        url: 'http://localhost:8080/Admin/api/createUser',
                        data: JSON.stringify({
                            name: $("#name").val(),
                            email: $("#email").val(),
                            username: $("#userName").val(),
                            password: $("#password").val(),
                            company: $("#company").val()
                        }),
                        contentType: "application/json; charset=utf-8",
                        method: 'post',
                        success: function () {
                            Swal.fire("Saved!", "", "success");
                        },
                        error: function () {
                            Swal.fire("Changes are not saved", "", "info");
                        }
                    })
                } else if (result.isDenied) {
                    Swal.fire("Changes are not saved", "", "info");
                }
            });
        })
    } catch (error) {
        console.log(error);
    }

})(jQuery);
(function ($) {
    "use strict";
    try {
        $(function () {
            $.ajax({
                url: "http://localhost:8080/Admin/api/adminListUser",
                method: 'get',
                success: function (listUser) {
                    let tableHTML = `<table class="table table-striped table-hover mr-auto m-sm-4 bg-light" id="table_user">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên</th>
                                <th>Công ty</th>
                                <th>Email</th>
                                <th>Username</th>
                                <th>Status</th>
                                <th>Hành Động</th>
                            </tr>
                        </thead>
                        <tbody  id="userList">`;
                    $.each(listUser, function (index, list) {
                        tableHTML += `<tr>
                            <td>${list.id}</td>
                            <td>${list.name}</td>
                            <td>${list.company}</td>
                            <td>${list.email}</td>
                            <td>${list.username}</td>
                            <td>${list.status}</td>
                            <td style="white-space: nowrap">
                                <button class="btn btn-primary btn-sm ms-1 btnEditUser" data-id="${list.id}">Sửa</button>
                                <button class="btn btn-danger btn-sm btnDeleteUser" data-id="${list.id}">Xóa</button>
                            </td>
                        </tr>`;
                    });
                    tableHTML += `</tbody></table>`;
                    $("#table_user").html(tableHTML);
                    $(".btnEditUser").on('click', function () {
                        const id = $(this).data('id');
                        editUser(id);
                    });

                    $(".btnDeleteUser").on('click', function () {
                        const id = $(this).data('id');
                        deleteUser(id);
                    });
                }
            });

            function editUser(id) {
                window.location.replace('/Admin/editor?id=' + id)
            }

            function deleteUser(id) {
                if (confirm('Bạn có chắc chắn muốn xóa không?')) {
                    $.ajax({
                        url: '/Admin/api/delete?id=' + id,
                        method: 'get',
                        success: function (response) {
                            console.log("Vô hiệu hóa người dùng thành công:", response);
                            alert("Vô hiệu hóa người dùng thành công");
                            window.location.reload();
                        },
                        error: function (xhr, status, error) {
                            console.error("Lỗi khi vô hiệu hóa người dùng:", error);
                            alert("Đã xảy ra lỗi khi vô hiệu hóa người dùng");
                        }
                    });
                }
            }
        });
    } catch (error) {
        console.log(error);
    }
})(jQuery);
(function ($) {
    "use strict";
    try {
        const urlParams = new URLSearchParams(window.location.search);
        const id = urlParams.get('id');
        console.log(id)
        $.ajax({
            url: '/Admin/api/editor?id=' + id,
            method: 'get',
            success: function (i) {
                console.log(i)
                $("#nameEdit").val(i.name);
                $("#userNameEdit").val(i.username);

            },
            error: function (xhr, status, error) {
                console.log(xhr.responseText);
            }
        });
    } catch (error) {
        console.log(error);
    }
})(jQuery);
(function ($) {
    $(document).ready(function () {
        $("#editUserBtn").click(function () {
            const urlParams = new URLSearchParams(window.location.search);
            const id = urlParams.get('id');
            const name = $("#nameEdit").val();
            const username = $("#userNameEdit").val();
            const company = $("#companyIdEdit").val();

            if (!name || !username || !company) {
                alert("Vui lòng điền đầy đủ thông tin");
                return; // Dừng lại nếu có bất kỳ trường nào bị thiếu
            }

            $.ajax({
                url: "/Admin/api/editor?id=" + id,
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    name: name,
                    username: username,
                    company: company
                }),
                success: function (response) {
                    console.log("Cập nhật thành công:", response);
                    alert("Cập nhật thông tin người dùng thành công");
                    window.location.href = "/Admin/list_user";
                },
                error: function (xhr, status, error) {
                    console.error("Lỗi khi cập nhật thông tin người dùng:", error);
                    alert("Đã xảy ra lỗi khi cập nhật thông tin người dùng");
                }
            });
        });
    });
})(jQuery);
(function ($) {
    "use strict";
    try {
        $(function () {
            $.ajax({
                url: "/Admin/api/listReport",
                method: 'get',
                success: function (listReport) {
                    let tableHTML = `<table class="table table-striped table-hover mr-auto m-sm-4 bg-light" id="table_user">
                        <thead>
                            <tr>
                                <th >ID</th>
                                <th >Tên</th>
                                <th >Công ty</th>
                                <th >Ngày gửi</th>
                                <th >Báo cáo</th>
                                <th >Hành động</th>
                            </tr>
                        </thead>
                        <tbody  id="reportList">`;
                    $.each(listReport, function (index, report) {
                        tableHTML += `<tr>
                            <td>${report.id}</td>
                            <td>${report.createBy}</td>
                            <td>${report.company.name}</td>
                            <td>${report.updateAt}</td>
                            <td><a>${report.file.name}</a>
                            </td>                            
                            <td style="white-space: nowrap">
                                <button class=" btn btn-danger btn-sm btnDeleteReport" data-id="${report.id}">Xóa</button>
                            </td>
                        </tr>`;
                    });
                    tableHTML += `</tbody></table>`;
                    $("#table_report").html(tableHTML);


                    $(".btnDeleteReport").on('click', function () {
                        const id = $(this).data('id');
                        deleteReport(id);
                    });
                }
            });



            function deleteReport(id) {
                if (confirm('Bạn có chắc chắn muốn xóa không?')) {
                    $.ajax({
                        url: '/Admin/api/deleteReport?id=' + id,
                        method: 'get',
                        success: function (response) {
                            console.log("Xóa thành công báo cáo :", response);
                            alert("Xóa thành công");
                            window.location.reload();
                        },
                        error: function (xhr, status, error) {
                            console.error("Lỗi khi xóa báo cáo :", error);
                            alert("Đã xảy ra lỗi khi xóa báo cáo ");
                        }
                    });
                }
            }
        });
    } catch (error) {
        console.log(error);
    }
})(jQuery);


