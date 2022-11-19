package app.fitbuddy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "history")
public class History {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "exercise_id", referencedColumnName = "id")	
	private Exercise exercise;
	
	@Column(name = "weight")
	private Integer weight;
	
	@Column(name = "reps")
	private Integer reps;
	
	@Column(name = "created_on")
	private String createdOn;
	
	@ManyToOne
	@JoinColumn(name = "app_user_id", referencedColumnName = "id")
	private AppUser appUser;

}
