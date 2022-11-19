package app.fitbuddy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name= "role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;

}
