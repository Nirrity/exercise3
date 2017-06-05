package wdsr.exercise3.client;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import wdsr.exercise3.model.Product;
import wdsr.exercise3.model.ProductType;

public class ProductService extends RestClientBase {
	protected ProductService(final String serverHost, final int serverPort, final Client client) {
		super(serverHost, serverPort, client);
	}
	
	public List<Product> retrieveProducts(Set<ProductType> types) {
		WebTarget statusTarget = baseTarget.path("/products").queryParam("type", types.toArray());
		Response response = statusTarget.request().get(Response.class);
		List<Product> products = response.readEntity(new GenericType<ArrayList<Product>>() {});
		return products;
	}
	
	public List<Product> retrieveAllProducts() {
		WebTarget statusTarget = baseTarget.path("/products");
		Response response = statusTarget.request().get(Response.class);
		List<Product> AllProducts = response.readEntity(new GenericType<ArrayList<Product>>() {});
		return AllProducts;
	}
	
	public Product retrieveProduct(int id) {
		WebTarget statusTarget = baseTarget.path("/products/"+id);
		Response response = statusTarget.request().get(Response.class);
		if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) throw new NotFoundException("Product not found");
		Product productsById = response.readEntity(Product.class);
		return productsById;
	}	
	
	public int storeNewProduct(Product product) {
		WebTarget statusTarget = baseTarget.path("/products");
		Response response = statusTarget.request().post(Entity.entity(product, MediaType.APPLICATION_JSON));
			return 0;	// niedokoñczone
			
	}
	
	public void updateProduct(Product product) {
		WebTarget statusTarget = baseTarget.path("/products/" + product.getId());
		Response response = statusTarget.request().put(Entity.entity(product, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) throw new NotFoundException("Product not found");
	}

	public void deleteProduct(Product product) {
		WebTarget statusTarget = baseTarget.path("/products/" + product.getId());
		Response response = statusTarget.request().delete();
		if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode())throw new NotFoundException("Product not found");
	}
}
