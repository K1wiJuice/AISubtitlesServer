function getLoginUser()
{
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "../login/getLoginUser",
        async: false,
        success: function(data){ 
            if (data.code == 200) {
                $(".userName").text(data.data.nickname);
                $(".btn-wrapper").hide();
                $(".user-wrapper").show();
            } else {
                $(".userName").text("");
                $(".btn-wrapper").show();
                $(".user-wrapper").hide();
            }
        }                   
    });
}

function redirectIndex(){
	window.location.href="index.html";
}

function redirectWerke(){
	window.location.href="werke/production.html";
}