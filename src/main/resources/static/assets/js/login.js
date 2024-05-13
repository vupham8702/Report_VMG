(function ($){
    // USE STRICT
    "use strict";
    try{
        $("#loginBtn").on('click',function (e){
            e.preventDefault();
            const form = $("#loginForm")[0];
            const data = new FormData(form);
            $.ajax({
                url:'/api/login',
                method:'post',
                data: data,
                enctype: 'multipart/form-data',
                contentType: false,
                processData: false,
                success: function (data){
                  console.log("Đăng nhập thành công")
                },
                error:function (err){
                    console.log("Đăng nhập thất bại")
                }
            })
        })
    }catch (e){
        console.log(e)
    }
})(jQuery)