package com.bootsimply.sync.entity;

import static com.bootsimply.sync.entity.Constants.ACTIVE;
import static com.bootsimply.sync.entity.Constants._createdAt;
import static com.bootsimply.sync.entity.Constants._createdBy;
import static com.bootsimply.sync.entity.Constants._status;
import static com.bootsimply.sync.entity.Constants._updatedAt;
import static com.bootsimply.sync.entity.Constants._updatedBy;

import java.util.Date;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;


@Api(name = "relationendpoint", namespace = @ApiNamespace(ownerDomain = "bootsimply.com", ownerName = "bootsimply.com", packagePath = "sync.entity"))
public class RelationEndpoint {
	@ApiMethod(name = "addRelation", scopes = { Config.EMAIL_SCOPE }, clientIds = { Config.CHROME_CLIENT_ID, Config.WEB_CLIENT_ID,
    	    Config.API_EXPLORER_CLIENT_ID })
        public ServiceResponse addRelation(@Named("_name") String _name, RelationRequest req, User user) throws UnauthorizedException {
    	RelationFrom from = req.getFrom();
    	RelationTo to = req.getTo();
		if (user == null) {
    	    throw new UnauthorizedException("UnauthorizedException # User is Null.");
    	} else if (req == null || from == null || to == null) {
    	    return null;
    	}

    	String userEmail = user.getEmail();
    	Date currentDate = new Date();

    	DatastoreService dsService = DatastoreServiceFactory.getDatastoreService();
    	Entity entity = new Entity(_name);

    	entity.setProperty(_createdAt, currentDate);
    	entity.setProperty(_createdBy, userEmail);

    	entity.setProperty(_updatedAt, currentDate);
    	entity.setProperty(_updatedBy, userEmail);

    	entity.setProperty(_status, ACTIVE);
    	
    	entity.setProperty("from_"+from.get_fromName(), from.get_fromId());
    	entity.setProperty("to_"+to.get_toName(), to.get_toId());

    	dsService.put(entity);

    	ServiceResponse res = new ServiceResponse();
    	res.set_id(entity.getKey().getId());

    	return res;
        }
}