package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

//dao
//자동으로 bean 등록이 된다
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User,Integer>{//이 jpa레퍼지토리는 user테이블을 관리하는 레퍼지토리이며 이 user테이블의 fk는 integer야  
	//JPA NAMING 전략
	//	SELECT * FROM user WHERE username = ?1 AND password =?2
	User findByUsernameAndPassword(String username,String password);
	
	/*
	 * @Query(value="SELECT * FROM user WHERE username = ?1 AND password =?2"
	 * ,nativeQuery=true) User login(String username, String password);
	 */
}
