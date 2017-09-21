package cmpe.restapi.service.impl;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.stereotype.Service;

import cmpe.restapi.service.MapIdService;

@Service
public class MapIdServiceImpl implements MapIdService {

	@Override
	public MapId toMapId(Long id) {
		MapId mId = new BasicMapId();
		mId.put("id", id);
		return mId;
	}

}
