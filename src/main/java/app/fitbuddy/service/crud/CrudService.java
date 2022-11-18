package app.fitbuddy.service.crud;

public interface CrudService<RequestDTO, ResponseDTO, UpdateDTO> {
	
	public ResponseDTO create(RequestDTO requestDTO);
	public ResponseDTO readById(Integer id);	
	public ResponseDTO update(Integer id, UpdateDTO updateDTO);
	public void delete(Integer id);
	
}
