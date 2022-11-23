package app.fitbuddy.service.mapper;

import app.fitbuddy.dto.appuser.AppUserRequestDTO;
import app.fitbuddy.dto.appuser.AppUserResponseDTO;
import app.fitbuddy.dto.appuser.AppUserUpdateDTO;
import app.fitbuddy.entity.AppUser;
import app.fitbuddy.entity.Role;
import app.fitbuddy.exception.FitBuddyException;
import app.fitbuddy.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserMapperService implements MapperService<AppUserRequestDTO, AppUserResponseDTO,
        AppUserUpdateDTO, AppUser> {

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AppUserMapperService(RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public AppUser requestDtoToEntity(AppUserRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }
        Optional<Role> optionalRole = roleRepository.findByName(requestDTO.getRolename());
        if (optionalRole.isEmpty()) {
            throw new FitBuddyException("Role doesn't exists with name: " +
                    requestDTO.getRolename());
        }
        AppUser appUser = new AppUser();
        appUser.setName(requestDTO.getName());
        appUser.setPassword(bCryptPasswordEncoder.encode(requestDTO.getPassword()));
        appUser.setRole(optionalRole.get());
        return appUser;
    }

    @Override
    public AppUserResponseDTO entityToResponseDto(AppUser entity) {
        if (entity == null) {
            return null;
        }
        return new AppUserResponseDTO(entity.getId(), entity.getName(), entity.getPassword(),
                entity.getRole().getName());
    }
    
    @Override
	public List<AppUserResponseDTO> entitiesToResponseDtos(List<AppUser> entities) {
    	if (entities == null || entities.isEmpty()) {
    		return Collections.emptyList();
    	}
    	List<AppUserResponseDTO> result = new ArrayList<>();
    	for (AppUser entity : entities) {
    		result.add(entityToResponseDto(entity));
    	}
    	return result;
	}

	@Override
	public AppUser applyUpdateDtoToEntity(AppUser entity, AppUserUpdateDTO updateDTO) {
		if (entity == null || updateDTO == null) {
			return null;
		}
		if (updateDTO.getName() != null) {
			entity.setName(updateDTO.getName());
		}
		if (updateDTO.getPassword() != null) {
			entity.setPassword(bCryptPasswordEncoder.encode(updateDTO.getPassword()));
		}
		if (updateDTO.getRolename() != null) {
			Optional<Role> optionalRole = roleRepository.findByName(updateDTO.getRolename());
			if (optionalRole.isEmpty()) {
				throw new FitBuddyException("Role doesn't exists with name: " +
						updateDTO.getRolename());
			}
			entity.setRole(optionalRole.get());
		}
		return entity;
	}
    
}
