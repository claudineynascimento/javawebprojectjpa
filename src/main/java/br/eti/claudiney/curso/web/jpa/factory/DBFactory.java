package br.eti.claudiney.curso.web.jpa.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class DBFactory {
	
	private EntityManagerFactory factory = null;

	public static final DBFactory INSTANCE = new DBFactory();
	
	private DBFactory() {
		try {
			factory = Persistence.createEntityManagerFactory("GestaoDados");
			System.out.println("Fabrica de conexoes INICIALIZADA com sucesso!");
		} catch(Throwable t) {
			t.printStackTrace();
			System.err.println("Nao foi possivel inicializar a fabrica de conexao: " + t.getMessage());
		}
	}
	
	public EntityManager getEntityManager() {
		if( factory != null ) {
			return factory.createEntityManager();
		}
		return null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		if( factory != null ) {
			factory.close();
		}
		System.out.println("Fabrica de conexoes FINALIZADA com sucesso!");
	}
	
}
