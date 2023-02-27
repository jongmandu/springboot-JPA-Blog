package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;

@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;
	//save함수는 id를 전달 안할 시 insert를 해주고
	//save 함수는 id를 전달 시 해당 id에 대한 데이터가 있으면 update를 해주고
	//save 함수는 id 전달 시 해당 id에 대한 데이터가 없으면 insert를 해준다
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {//EmptyResultDataAccessException이 맞긴한데 어차피 exception의 자식이니까 요렇게해도가능
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제되었습니다.id: "+id;
	}
	
	@Transactional//함수 종료시 자동 커밋이 됨(COMMIT : 저장되지 않은 모든 데이터를 데이터베이스에 저장하고 현재의 트랜잭션을 종료하라는 명령)
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {//json 데이터를 요청했는데 스프링이 java object(MessageConverter의 Jackson라이브러리)가 변환해서 받아줘요.
		System.out.println("id: "+id);
		System.out.println("password: "+requestUser.getPassword());
		System.out.println("email: "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{//자바의 큰 문제점이 파라미터에 함수를 못넣는거였는데 람다식을 이용해서 가능해짐
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);  //@Transactional을 걸면 save하지 않아도 업뎃된다
		//더티체킹!  @Transactional에서 변경된 사실을 인지하면 jpa(영속성컨텍스트)에 변경정보 전달->db에 변경정보 전달
		return user;
	}
	
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	//한페이지당 2건의 데이터를 리턴받아보자
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id",direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;
	}	 

	//{id}주소로 파라미터를 전달받을 수 있다
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//3까지밖에 없는 상황에서 user/4를 찾으면 null값이 뜰거잖아 그럼 문제가 생기니까
		//Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return하자
		
		
		  User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
		  @Override 
		  public IllegalArgumentException get() {
		  		  return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
		  		  } 
		  });
		  //요청:웹브라우저
		  //user 객체=자바 오브젝트
		  //변환 (웹브라우저가 이해할 수 있는 데이터)->json
		  //스프링부트=MessageConverter라는 애가 응답시에 자동 작동
		  //만약에 자바 오브젝트르르 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		  //user 오브젝트를 json으로 변환해서 브라우저에게 던져준다
		  return user; 
		}
		 
		//위 식을 람다식으로 변환하면 좀 더 간단하다
		/*
		 * User user = userRepository.findById(id).orElseThrow(()-> { return new
		 * IllegalArgumentException("해당 유저는 없습니다. id: "+id); }); return user; }
		 */
	//http://localhost:8000/blog/dummy/join(요청)
	//http의 body에 username,password,email 데이터를 가지고(요청)
	@PostMapping("/dummy/join")
		public String join(User user) {
			System.out.println("id: "+user.getId());
			System.out.println("username: "+user.getUsername());
			System.out.println("password: "+user.getPassword());
			System.out.println("email: "+user.getEmail());
			System.out.println("role: "+user.getRole());
			System.out.println("createDate: "+user.getCreateDate());
			
			user.setRole(RoleType.USER);
			userRepository.save(user);
			return "회원가입이 완료되었습니다.";
		}
}
