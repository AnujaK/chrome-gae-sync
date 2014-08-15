package com.bootsimply.sync.entity;

import static com.bootsimply.sync.entity.Constants.ACTIVE;
import static com.bootsimply.sync.entity.Constants.ARCHIVED;
import static com.bootsimply.sync.entity.Constants.DELETED;
import static com.bootsimply.sync.entity.Constants._createdAt;
import static com.bootsimply.sync.entity.Constants._createdBy;
import static com.bootsimply.sync.entity.Constants._status;
import static com.bootsimply.sync.entity.Constants._updatedAt;
import static com.bootsimply.sync.entity.Constants._updatedBy;
import static com.bootsimply.sync.entity.Constants.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
@Api(name = "dataendpoint", namespace = @ApiNamespace(ownerDomain = "bootsimply.com", ownerName = "bootsimply.com", packagePath = "sync.entity"))
public class DataEndpoint {
    /**
     * This method lists all the entities inserted in datastore. It uses HTTP GET method and paging support.
     * 
     * @return A CollectionResponse class containing the list of all entities persisted and a cursor to the next page.
     * @throws UnauthorizedException
     */
    @ApiMethod(name = "listStore", scopes = { Config.EMAIL_SCOPE }, clientIds = { Config.CHROME_CLIENT_ID, Config.WEB_CLIENT_ID,
	    Config.API_EXPLORER_CLIENT_ID })
    public CollectionResponse<ServiceResponse> listStore(@Named("_name") String _name, @Nullable @Named("cursor") String cursorString,
	    @Nullable @Named("limit") Integer limit, User user) throws UnauthorizedException {
		if (user == null) {
			throw new UnauthorizedException(
					"UnauthorizedException # User is Null.");
		}
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		if (limit == null) {
			limit = 10;
		}
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(limit);
		if (cursorString != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(cursorString));
		}
		Query q = new Query(_name);
		q.addSort("_updatedAt", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		
		List<ServiceResponse> responses = new ArrayList<ServiceResponse>();
		ServiceResponse res = null;
		for (Entity entity : results) {
			res = new ServiceResponse();
			//TODO add properties from entity to service response
			res.set_id(entity.getKey().getId());
    	    
    	    res.set_createdAt((Date) entity.getProperty(_createdAt));
    	    res.set_createdBy((String) entity.getProperty(_createdBy));
    	    
    	    res.set_upatedAt((Date) entity.getProperty(_updatedAt));
    	    res.set_updatedBy((String) entity.getProperty(_updatedBy));
    	    
    	    res.set_status((String) entity.getProperty(_status));
    	    
    	    Text dataText = (Text)entity.getProperty(data);
			res.setData(dataText.getValue());
			
			responses.add(res);
		}
		
		cursorString = results.getCursor().toWebSafeString();

		return CollectionResponse.<ServiceResponse> builder().setItems(responses).setNextPageToken(cursorString).build();
    }

    /**
     * This method gets the entity having primary key id. It uses HTTP GET method.
     * 
     * @param id
     *            the primary key of the java bean.
     * @return The entity with primary key id.
     * @throws UnauthorizedException
     */
    @ApiMethod(name = "getStore", scopes = { Config.EMAIL_SCOPE }, clientIds = { Config.CHROME_CLIENT_ID, Config.WEB_CLIENT_ID,
	    Config.API_EXPLORER_CLIENT_ID })
    public ServiceResponse getStore(@Named("_name") String _name, @Named("_id") Long _id, User user) throws UnauthorizedException {
    	if (user == null) {
    	    throw new UnauthorizedException("UnauthorizedException # User is Null.");
    	}
    	Key key = KeyFactory.createKey(_name, _id);

    	DatastoreService dsService = DatastoreServiceFactory.getDatastoreService();
    	ServiceResponse res = new ServiceResponse();
    	try {
    	    Entity entity = dsService.get(key);
    	    res.set_id(entity.getKey().getId());
    	    
    	    res.set_createdAt((Date) entity.getProperty(_createdAt));
    	    res.set_createdBy((String) entity.getProperty(_createdBy));
    	    
    	    res.set_upatedAt((Date) entity.getProperty(_updatedAt));
    	    res.set_updatedBy((String) entity.getProperty(_updatedBy));
    	    
    	    res.set_status((String) entity.getProperty(_status));
    	    
    	    Text dataText = (Text)entity.getProperty(data);
			res.setData(dataText.getValue());
    	    
    	} catch (com.google.appengine.api.datastore.EntityNotFoundException e) {
    	    throw new EntityNotFoundException("Object does not exist.");
    	}

    	return res;
    }

    /**
     * This inserts a new entity into App Engine datastore. If the entity already exists in the datastore, an exception is thrown. It uses HTTP POST
     * method.
     * 
     * @param req
     *            the entity to be inserted.
     * @return The inserted entity.
     * @throws UnauthorizedException
     */
    @ApiMethod(name = "insertStore", scopes = { Config.EMAIL_SCOPE }, clientIds = { Config.CHROME_CLIENT_ID, Config.WEB_CLIENT_ID,
	    Config.API_EXPLORER_CLIENT_ID })
    public ServiceResponse insertStore(@Named("_name") String _name, ServiceRequest req, User user) throws UnauthorizedException {
	if (user == null) {
	    throw new UnauthorizedException("UnauthorizedException # User is Null.");
	} else if (req == null || req.getData() == null) {
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
	entity.setUnindexedProperty(data, new Text(req.getData()));

	dsService.put(entity);

	ServiceResponse res = new ServiceResponse();
	res.set_id(entity.getKey().getId());

	return res;
    }

    /**
     * This method is used for updating an existing entity. If the entity does not exist in the datastore, an exception is thrown. It uses HTTP PUT
     * method.
     * 
     * @param store
     *            the entity to be updated.
     * @return The updated entity.
     * @throws UnauthorizedException
     */
    @ApiMethod(name = "updateStore", scopes = { Config.EMAIL_SCOPE }, clientIds = { Config.CHROME_CLIENT_ID, Config.WEB_CLIENT_ID,
	    Config.API_EXPLORER_CLIENT_ID })
    public ServiceResponse updateStore(@Named("_name") String _name, @Named("_id") Long _id, ServiceRequest req, User user)
	    throws UnauthorizedException {
	if (user == null) {
	    throw new UnauthorizedException("UnauthorizedException # User is Null.");
	}
	Key key = KeyFactory.createKey(_name, _id);
	Date currentDate = new Date();
	String userEmail = user.getEmail();

	DatastoreService dsService = DatastoreServiceFactory.getDatastoreService();
	ServiceResponse res = new ServiceResponse();
	try {
	    Entity entity = dsService.get(key);

	    entity.setProperty(_updatedAt, currentDate);
	    entity.setProperty(_updatedBy, userEmail);

	    entity.setUnindexedProperty(data, new Text(req.getData()));

	    dsService.put(entity);
	    
	    res.set_id(entity.getKey().getId());
	} catch (com.google.appengine.api.datastore.EntityNotFoundException e) {
		throw new EntityNotFoundException("Object does not exist.");
	}

	return res;
    }

    @ApiMethod(name = "archive", scopes = { Config.EMAIL_SCOPE }, clientIds = { Config.CHROME_CLIENT_ID, Config.WEB_CLIENT_ID,
    	    Config.API_EXPLORER_CLIENT_ID })
        public ServiceResponse archive(@Named("_name") String _name, @Named("_id") Long _id, User user)
    	    throws UnauthorizedException {
    	ServiceResponse res = updateStatus(_name, _id, ARCHIVED, user);
    	
    	return res;
        }
        
        @ApiMethod(name = "softDelete", scopes = { Config.EMAIL_SCOPE }, clientIds = { Config.CHROME_CLIENT_ID, Config.WEB_CLIENT_ID,
    	    Config.API_EXPLORER_CLIENT_ID })
        public ServiceResponse softDelete(@Named("_name") String _name, @Named("_id") Long _id, User user)
    	    throws UnauthorizedException {
    	ServiceResponse res = updateStatus(_name, _id, DELETED, user);
    	
    	return res;
        }
        
        private ServiceResponse updateStatus(String _name, Long _id, String _status, User user) throws UnauthorizedException {
    	if (user == null) {
    	    throw new UnauthorizedException("UnauthorizedException # User is Null.");
    	}
    	Key key = KeyFactory.createKey(_name, _id);
    	Date currentDate = new Date();
    	String userEmail = user.getEmail();

    	DatastoreService dsService = DatastoreServiceFactory.getDatastoreService();
    	ServiceResponse res = new ServiceResponse();
    	try {
    	    Entity entity = dsService.get(key);

    	    entity.setProperty(_updatedAt, currentDate);
    	    entity.setProperty(_updatedBy, userEmail);

    	    entity.setProperty(_status, _status);

    	    dsService.put(entity);
    	    
    	    res.set_id(entity.getKey().getId());
    	    
    	} catch (com.google.appengine.api.datastore.EntityNotFoundException e) {
    	    throw new EntityNotFoundException("Object does not exist");
    	}
    	return res;
        }

        /**
         * This method removes the entity with primary key id. It uses HTTP DELETE method.
         * 
         * @param id
         *            the primary key of the entity to be deleted.
         * @throws UnauthorizedException
         */
        @ApiMethod(name = "removeStore", scopes = { Config.EMAIL_SCOPE }, clientIds = { Config.CHROME_CLIENT_ID, Config.WEB_CLIENT_ID,
    	    Config.API_EXPLORER_CLIENT_ID })
        public void removeStore(@Named("_name") String _name, @Named("_id") Long _id, User user) throws UnauthorizedException {
    	if (user == null) {
    	    throw new UnauthorizedException("UnauthorizedException # User is Null.");
    	}
    	Key key = KeyFactory.createKey(_name, _id);
    	DatastoreService dsService = DatastoreServiceFactory.getDatastoreService();
    	dsService.delete(key);
        }
        


}