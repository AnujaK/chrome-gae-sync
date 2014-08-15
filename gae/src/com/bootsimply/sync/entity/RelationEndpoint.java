package com.bootsimply.sync.entity;

import static com.bootsimply.sync.entity.Constants.ACTIVE;
import static com.bootsimply.sync.entity.Constants._createdAt;
import static com.bootsimply.sync.entity.Constants._createdBy;
import static com.bootsimply.sync.entity.Constants._status;
import static com.bootsimply.sync.entity.Constants._updatedAt;
import static com.bootsimply.sync.entity.Constants._updatedBy;
import static com.bootsimply.sync.entity.Constants.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.inject.Named;

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
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;


@Api(name = "relationendpoint", namespace = @ApiNamespace(ownerDomain = "bootsimply.com", ownerName = "bootsimply.com", packagePath = "sync.entity"))
public class RelationEndpoint {
	@ApiMethod(name = "insertRelation", scopes = { Config.EMAIL_SCOPE }, clientIds = { Config.CHROME_CLIENT_ID, Config.WEB_CLIENT_ID,
    	    Config.API_EXPLORER_CLIENT_ID })
        public ServiceResponse insertRelation(@Named("_name") String _name, RelationRequest req, User user) throws UnauthorizedException {
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
    	
    	entity.setProperty(from.get_fromName()+"_id", from.get_fromId());
    	entity.setProperty(to.get_toName()+"_id", to.get_toId());

    	dsService.put(entity);

    	ServiceResponse res = new ServiceResponse();
    	res.set_id(entity.getKey().getId());

    	return res;
    }
	
    @ApiMethod(name = "removeRelation", scopes = { Config.EMAIL_SCOPE }, clientIds = { Config.CHROME_CLIENT_ID, Config.WEB_CLIENT_ID,
    	    Config.API_EXPLORER_CLIENT_ID })
        public void removeRelation(@Named("_name") String _name, @Named("_id") Long _id, User user) throws UnauthorizedException {
    	if (user == null) {
    	    throw new UnauthorizedException("UnauthorizedException # User is Null.");
    	}
    	Key key = KeyFactory.createKey(_name, _id);
    	DatastoreService dsService = DatastoreServiceFactory.getDatastoreService();
    	dsService.delete(key);
     }
    
    @ApiMethod(name = "getRelatedEntities", scopes = { Config.EMAIL_SCOPE }, clientIds = { Config.CHROME_CLIENT_ID, Config.WEB_CLIENT_ID,
    	    Config.API_EXPLORER_CLIENT_ID })
        public List<Long> getRelatedEntities(@Named("_name") String _name, @Named("_relatedEntityName") String _relatedEntityName, @Named("_id") Long _id, User user) throws UnauthorizedException {
    		if (user == null) {
    			throw new UnauthorizedException(
    					"UnauthorizedException # User is Null.");
    		}
    		DatastoreService datastore = DatastoreServiceFactory
    				.getDatastoreService();
    		
    		FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
    		
    		Filter f = new FilterPredicate(_relatedEntityName+"_id", FilterOperator.EQUAL, _id);
    		
			Query q = new Query(_name).setFilter(f);
    		PreparedQuery pq = datastore.prepare(q);    		
    		
    		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
    		
    		List<Long> relatedEntityIds = new ArrayList<Long>();
    		
    		for (Entity entity : results) {
        	    Map<String, Object> properties = entity.getProperties();
        	    Set<String> keySet = properties.keySet();
        	    
        	    for (String mapKey : keySet) {
					if(mapKey.equals("_id")){
						continue;
					}
					else if(mapKey.equals(_relatedEntityName+"_id")){
						continue;
					}
					else if(mapKey.endsWith("_id")){
						relatedEntityIds.add((Long)properties.get(mapKey));
					}
				}
    		}
    		return relatedEntityIds;
        }    
}