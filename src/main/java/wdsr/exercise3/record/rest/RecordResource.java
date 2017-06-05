package wdsr.exercise3.record.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import wdsr.exercise3.record.Record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wdsr.exercise3.record.RecordInventory;

@Path("/records")
public class RecordResource {
	private static final Logger log = LoggerFactory.getLogger(RecordResource.class);
	
	@Inject
	private RecordInventory recordInventory;
	
	@GET
	@Produces("application/xml")
	public Response getAllRecords() {
		List<Record> result = new ArrayList<>();
		result.addAll(recordInventory.getRecords());
		GenericEntity<List<Record>> genericEntity = new GenericEntity<List<Record>>(result) {};
		return Response.ok(genericEntity).build();
	}
	@POST
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response addNewRecord(Record record) 
	{
		if(record.getId() != null)
			return Response.status(Status.BAD_REQUEST).build();
		this.recordInventory.addRecord(record);
		return Response.status(Status.CREATED).entity(record).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/xml")
	public Response getRecordById(@PathParam(value = "id") int id) {
		Record record = recordInventory.getRecord(id);
		if(record != null)
			return Response.ok(record).build();
		else 
			return Response.status(Status.NOT_FOUND).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response updateRecordById(Record record, @PathParam(value = "id") int id){
		if (record.getId() != null && id != record.getId() )
			return Response.status(Status.BAD_REQUEST).build();

		if (this.recordInventory.updateRecord(id, record)) 
			return Response.status(Status.NO_CONTENT).build();
		else 
			return Response.status(Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response deleteRecordById(@PathParam(value = "id") int id) {
		if(recordInventory.deleteRecord(id))
			return Response.status(Status.NO_CONTENT).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}
}
