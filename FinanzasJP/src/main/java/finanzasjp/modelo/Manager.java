package finanzasjp.modelo;

import java.util.HashSet;
import java.util.Set;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


public class Manager {
	
	protected SessionFactory sessionFactory;
	 
//    protected void setup() {
//        // code to load Hibernate Session factory
//    	
//		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
//		try {
//			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//		} catch (Exception ex) {
//			System.out.println(ex.getMessage());
//			StandardServiceRegistryBuilder.destroy(registry);
//		}
//    }
 
    protected void exit() {
        // code to close Hibernate Session factory
    	
    	sessionFactory.close();
    }
 
    protected void create() {
        // code to save a book
    }
 
    protected void read(Session session) {
        // code to get a book
    	
	    //session = sessionFactory.openSession();
	    
	    String id = "1130";
	    Cliente_VIP clientevip = (Cliente_VIP) session.get(Cliente_VIP.class, id);
	 
	    System.out.println("Nombre: " + clientevip.getNombre());
	    System.out.println("Apellido: " + clientevip.getApellido());
	    System.out.println(clientevip.getClientes().size());
	    
	    for(Cliente cl : clientevip.getClientes()){
	    	System.out.println(cl.getNombre() +"  " + cl.getRecibos().size());
	    	
	    	for(Recibo rec:cl.getRecibos()) {
	    		System.out.println("Cuotas"+rec.getCuotas().size());
	    	}
	    }
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
    	 Configuration configuration = new Configuration().configure();
         ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
         registry.applySettings(configuration.getProperties());
         ServiceRegistry serviceRegistry = registry.buildServiceRegistry();
          
         // builds a session factory from the service registry
         SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
          
         // obtains the session
         Session session = sessionFactory.openSession();
         
         Manager manager = new Manager();
         
         manager.read(session);

    }

}
