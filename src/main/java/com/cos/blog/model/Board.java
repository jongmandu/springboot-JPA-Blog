package com.cos.blog.model;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder//빌터 패턴
@Entity//User 클래스가 MySQL에 테이블 생성된다
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//auto-increment
	private int id;
	
	@Column(nullable = false,length = 100)
	private String title;
	
	@Lob//대용량 데이터
	private String content;//섬머노트 라이브러리<html>태그가 섞여서 용량 커짐 그래서 lob사용
	
	@ColumnDefault("0")
	private int count; //조회수
	
	@ManyToOne 
	@JoinColumn(name="userId")  //id title content count userId로 인식함
	private User user;//db는 오브젝트를 저장할 수 없다.fk,자바는 오브젝트를 저장할 수 있다.
	
	@CreationTimestamp
	private Timestamp createDate;
}
