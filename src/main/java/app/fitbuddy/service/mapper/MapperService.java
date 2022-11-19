package app.fitbuddy.service.mapper;

import java.util.List;

public interface MapperService<RequestDTO, ResponseDTO, UpdateDTO, Entity> {
	
	public Entity requestDtoToEntity(RequestDTO requestDTO);
	public ResponseDTO entityToResponseDto(Entity entity);
	public List<ResponseDTO> entitiesToResponseDtos(List<Entity> entities);
	public Entity applyUpdateDtoToEntity(Entity entity, UpdateDTO updateDTO);

}
