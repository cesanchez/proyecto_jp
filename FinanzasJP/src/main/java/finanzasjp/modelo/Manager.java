package finanzasjp.modelo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class Manager {
	
	protected SessionFactory sessionFactory;
	 
    protected void setup() {
        // code to load Hibernate Session factory
    	
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			StandardServiceRegistryBuilder.destroy(registry);
		}
    }
 
    protected void exit() {
        // code to close Hibernate Session factory
    	
    	sessionFactory.close();
    }
 
    protected void create() {
        // code to save a book
    }
 
    protected void read() {
        // code to get a book
    	
	    Session session = sessionFactory.openSession();
	    
	    long id = 1130;
	    Cliente_VIP clientevip = session.get(Cliente_VIP.class, id);
	 
	    System.out.println("Nombre: " + clientevip.getNombre());
	    System.out.println("Apellido: " + clientevip.getApellido());
	    session.close();
    }
 
    protected void update() {
        // code to modify a book
    }
 
    protected void delete() {
        // code to remove a book
    }
 
    public static void main(String[] args) {
        // code to run the program
    	Manager manager = new Manager();
    	manager.setup();
    	manager.read();
    	manager.exit();
    }

}
