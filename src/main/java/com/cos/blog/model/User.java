package com.cos.blog.model;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder//빌터 패턴
@Entity//User 클래스가 MySQL에 테이블 생성된다
//@DynamicInsert     insert 시에 null인 필드를 자동으로 제외시켜준다
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에 연결된 DB의 넘버링 전략을 따라간다.
	private int id;
    
	@Column(nullable = false, length =30, unique = true)//중복허용 x
	private String username;
	
	@Column(nullable = false, length =100)
	private String password;
	
	@Column(nullable = false, length =50)
	private String email;
	
	//@ColumnDefault("user")
	//db는 RoleType이라는게 없다
	@Enumerated(EnumType.STRING)
	private RoleType role;//Enum을 쓰는게 좋다. //admin,user 등등 권한 준다
	
	@CreationTimestamp//시간이 자동 입력
	private Timestamp createDate;
}
