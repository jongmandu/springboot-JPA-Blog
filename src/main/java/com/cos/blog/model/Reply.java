package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder//빌터 패턴
@Entity//User 클래스가 MySQL에 테이블 생성된다
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다
	private int id;//시퀀스 , auto_increment

	@Column(nullable = false, length =200)
	private String content;
	
	@ManyToOne
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="userId")
	private User user;//DB는 오브젝트를 저장할 수 없다. fk,자바는 오브젝트 저장 가능
	
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER)//mappedBy =연관관계의 주인이 아니다.(난 fk가 아니에요) db에 컬럼을 만들지 마세요
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
}
