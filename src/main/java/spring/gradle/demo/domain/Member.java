package spring.gradle.demo.domain;

import javax.persistence.*;

//이거는 JPA가 관리하는 entity이다.
@Entity
public class Member {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@Column(name = "username")
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
