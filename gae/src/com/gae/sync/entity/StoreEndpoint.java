package com.gae.sync.entity;

import com.gae.sync.entity.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "storeendpoint", namespace = @ApiNamespace(ownerDomain = "gae.com", ownerName = "gae.com", packagePath = "sync.entity"))
public class StoreEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listStore")
	public CollectionResponse<Store> listStore(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Store> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Store.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Store>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Store obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Store> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getStore")
	public Store getStore(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		Store store = null;
		try {
			store = mgr.getObjectById(Store.class, id);
		} finally {
			mgr.close();
		}
		return store;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param store the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertStore")
	public Store insertStore(Store store) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			
			if (containsStore(store)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(store);
		} finally {
			mgr.close();
		}
		return store;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param store the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateStore")
	public Store updateStore(Store store) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsStore(store)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(store);
		} finally {
			mgr.close();
		}
		return store;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeStore")
	public void removeStore(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Store store = mgr.getObjectById(Store.class, id);
			mgr.deletePersistent(store);
		} finally {
			mgr.close();
		}
	}

	private boolean containsStore(Store store) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Store.class, store.getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
