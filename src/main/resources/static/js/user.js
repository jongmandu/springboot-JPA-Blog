let index = {
		init:function(){
			$("#btn-save").on("click",()=>{
				this.save();
			});
		},
		save:function(){
			//alert('user의 save 함수호출됨');  얼랏은 홑따옴표 쓰는구나
			let data = {
					username:$("#username").val(),
					password:$("#password").val(),
					email:$("#email").val()
			};
			//console.log(data);    이렇게하면 f12 누르고 콘솔창에서 데이터 확인 가능
			$.ajax({//ajax호출시 디폴트가 비동기 호출
				type:"POST",
				url:"/blog/api/user",
				data: JSON.stringify(data),//위의 data는 자바스크립트 오브젝트라 json화 해주는거임
				contentType:"application/json; charset=utf-8",//body데이터가 어떤타입인지
				dataType:"json" //요청을 서버로 해서 응답이 왔을 때 기본적으로 모든것이 문자열인데 생긴게 json이라면 javascript로 변환
			}).done(function(resp){
				alert("회원가입이 완료되었습니다.");
				//console.log(resp);
				location.href="/blog";
			}).fail(function(error){
				alert(JSON.stringfy(error));
			});//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청 할거야
		},
		login:function(){
			//alert('user의 save 함수호출됨');  얼랏은 홑따옴표 쓰는구나
			let data = {
					username:$("#username").val(),
					password:$("#password").val(),
				
			};
			
			$.ajax({
				type:"POST",
				url:"/blog/api/user/login",
				data: JSON.stringify(data),//위의 data는 자바스크립트 오브젝트라 json화 해주는거임
				contentType:"application/json; charset=utf-8",//body데이터가 어떤타입인지
				dataType:"json" //요청을 서버로 해서 응답이 왔을 때 기본적으로 모든것이 문자열인데 생긴게 json이라면 javascript로 변환
			}).done(function(resp){
				alert("로그인이 완료되었습니다.");
				//console.log(resp);
				location.href="/blog";
			}).fail(function(error){
				alert(JSON.stringfy(error));
			});//ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청 할거야
		}
		

}
index.init();