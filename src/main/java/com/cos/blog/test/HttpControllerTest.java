package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//사용자가 요청->응답(html)
//@Controller

//사용자가 요청->응답(data)
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder().username("jongjong").password("12345").email("steven_97@naver.com").build();
		System.out.println(TAG +"getter: "+m.getUsername());
		m.setUsername("jong");
		System.out.println(TAG +"setter: " + m.getUsername());
	    return "lombok test 완료";
	}
    //인터넷 브라우저 요청은 무조건 get 방식으로 밖에 못해
	//나머지 post put delete 링크 복붙해서 들가면 error:405 뜸
	//그래서 설치한게 postman. 거기서 링크 넣으면 작동함
	//http://localhost:8000/http/get (select)
	@GetMapping("/http/get")
	/*
	 * public String getTest(@RequestParam int id,@RequestParam String password) {
	 * return "get 요청" + id + "," + password; }
	 */
	//이렇게 말고 한번에 받는 방법도 있음. member
	public String getTest(Member m) {//겟방식에서는 이렇게 쿼리 스트링으로 요청해야됨.

		return "get 요청" + m.getId() + "," + m.getPassword()+","+ m.getUsername()+","+ m.getEmail();
	}
	//http://localhost:8000/http/post (insert)
	/*
	 * @PostMapping("/http/post") public String postTest(Member m) { return
	 * "post 요청" + m.getId() + "," + m.getPassword()+","+ m.getUsername()+","+
	 * m.getEmail(); }
	 */
	@PostMapping("/http/post")//text/plain을 보냈다. 이제 application/JSON 보내볼거임.
	public String postTest(@RequestBody Member m) {//messageConverter(스프링부트)가 파싱
		return "post 요청" + m.getId() + "," + m.getPassword()+","+ m.getUsername()+","+ m.getEmail();
	}
	//http://localhost:8000/http/put (update)
	@PutMapping("/http/put")
	public String putTest() {
		return "put 요청";
	}
	//http://localhost:8000/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}

}
