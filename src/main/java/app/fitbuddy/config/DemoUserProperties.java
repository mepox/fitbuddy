package app.fitbuddy.config;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "fitbuddy.demo-user")
public class DemoUserProperties {
	
	private boolean enabled;
	private boolean resets;
	@Positive
	private Integer resetPeriod;
	@NotBlank
	@Size(min = 4, max = 15)
	private String name;
	@NotBlank
	@Size(min = 4, max = 15)
	private String password;
	@NotBlank
	@Size(min = 4, max = 16)
	private String rolename;
	private boolean addDefaultExercises;
	private boolean addHistory;
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isResets() {
		return resets;
	}
	public void setResets(boolean resets) {
		this.resets = resets;
	}
	public Integer getResetPeriod() {
		return resetPeriod;
	}
	public void setResetPeriod(Integer resetPeriod) {
		this.resetPeriod = resetPeriod;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public boolean isAddDefaultExercises() {
		return addDefaultExercises;
	}
	public void setAddDefaultExercises(boolean addDefaultExercises) {
		this.addDefaultExercises = addDefaultExercises;
	}
	public boolean isAddHistory() {
		return addHistory;
	}
	public void setAddHistory(boolean addHistory) {
		this.addHistory = addHistory;
	}

}
