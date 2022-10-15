package com.laszlojanku.fitbuddy.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "history")
public class History {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "exercise_id")	
	private int exerciseId;
	
	@Column(name = "weight")
	private int weight;
	
	@Column(name = "reps")
	private int reps;
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "app_user_id")
	private int appUserId;

}
