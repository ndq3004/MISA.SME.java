﻿$(document).ready(function(){
//	debugger
	if(localStorage.getItem("authenCookie") != "" && localStorage.getItem("authenCookie") != null){
		$.ajax({
			method:"GET",
			url: "http:/localhost:9090/api/home",
			beforeSend: function(xhr) {
			      xhr.setRequestHeader('authorization',localStorage.getItem("authenCookie"));
			},
			success: function(data, txtStatus){
				var dt = JSON.stringify(data);
				console.log(dt.status + dt.email + data);
				if(txtStatus == "success") {
					window.location.href="http://localhost:8080/home";
				}
			},
			error: function(){
				
			}
		})	
	}

	//if there no token
	$('#btnLogin').on('click', function(){
		var jsonObj = {"email":$('#txtUserName').val(), "password": $('#txtPassword').val()};
    	$.ajax({
			method:"POST",
			url:"http://localhost:9090/api/login",
			contentType: "application/json",
			data: JSON.stringify(jsonObj),
			success: function(data, txtStatus, xhr){
				if(txtStatus=="success") 
					{
						localStorage.setItem("authenCookie", data.token);
						$.ajax({
							method:"GET",
							url: "http://localhost:9090/api/home",
							beforeSend: function(xhr) {
						      xhr.setRequestHeader('authorization',localStorage.getItem("authenCookie"));
						    }
						})
						.done(function(data, txtStatus,xhr){
							 if(txtStatus == "success") {
								 window.location.href="http://localhost:8080/home";
								 console.log(data);	
							 }
						})
					}
			},
			error: function(){
				$('#errorlogin').text("Lỗi đăng nhập. Sai tên tài khoản hoặc mật khẩu!");
			}
		
			
    	})
	})
})

