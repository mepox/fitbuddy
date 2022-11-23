package app.fitbuddy.controller.operation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.fitbuddy.dto.accountinfo.AccountInfoUpdateDTO;
import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.service.operation.AccountInfoService;

@RestController
@RequestMapping("/user/account")
@PreAuthorize("authenticated")
public class AccountInfoController {
	
	private final AccountInfoService accountInfoService;
	
	@Autowired
	public AccountInfoController(AccountInfoService accountInfoService) {
		this.accountInfoService = accountInfoService;
	}
	
	@GetMapping
	public AppUserResponseDTO read() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return accountInfoService.read(auth.getName());
	}
	
	@PutMapping
	public void update(@RequestBody @Valid AccountInfoUpdateDTO accountInfoUpdateDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		accountInfoService.update(auth.getName(), accountInfoUpdateDTO);				
	}

}
