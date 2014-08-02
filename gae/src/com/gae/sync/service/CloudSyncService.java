package com.gae.sync.service;

import java.util.ArrayList;
import java.util.List;

import com.gae.sync.model.CloudSyncDTO;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

@Api(name = "sync", version = "v1", description = "cloud-gae-sync API")
public class CloudSyncService {
	public static List<CloudSyncDTO> dtos = new ArrayList<CloudSyncDTO>();

	@ApiMethod(name = "add")
	public void addCloudSyncData(@Named("id") long id,
			@Named("data") String data) {
		CloudSyncDTO dto = new CloudSyncDTO();
		dto.setId(id);
		dto.setData(data);
		dtos.add(dto);
	}

	@ApiMethod(name = "list")
	public List<CloudSyncDTO> getCloudSyncData() {
		return dtos;
	}
}
