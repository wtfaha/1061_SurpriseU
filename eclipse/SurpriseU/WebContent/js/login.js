	var url = window.location.href;
	
	/*判斷輸入帳密是否正確*/
	$(document).ready(function() {
		$("#loginId").click(function(e) {
			$.ajax({
				type : "GET",
				url : "LoginVerificationServlet",
				data : {
					state : "login",
					account : $("#account").val(),
					password : $("#password").val()
				},
				dataType : "json",

				success : function(response) {		
					if (response.result == "登入成功") {	//登入成功
						alert("登入成功");
					}
					else {	//登入失敗
						$("#loginMessage").empty();
						$("#loginMessage").append(response.result);
						alert("登入失敗");
					}
				},
				error : function() {
					console.log("錯誤訊息");
				}
			});
		});
	});