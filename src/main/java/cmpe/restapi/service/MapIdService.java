package cmpe.restapi.service;

import org.springframework.data.cassandra.repository.MapId;

public interface MapIdService {
	
	public MapId toMapId(Long id);
}
