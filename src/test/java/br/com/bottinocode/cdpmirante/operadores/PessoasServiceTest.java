package br.com.bottinocode.cdpmirante.operadores;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.bottinocode.cdpmirante.util.Resources;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class PessoasServiceTest {
	   @Deployment
	    public static Archive<?> createTestArchive() {
	        return ShrinkWrap.create(WebArchive.class, "test.war")
	                .addClasses(Operador.class, OperadoresService.class, OperadoresRepository.class, Resources.class)
	                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
	                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
	                // Deploy our test datasource
	                .addAsWebInfResource("test-ds.xml");
	    }

	    @Inject
	    OperadoresService service;

	    @Inject
	    Logger log;

	    @Test
	    public void testRegistro() throws Exception {
	    	Operador operador = new Operador();
	    	operador.setLogin("lbottino");
	    	operador.setNome("Luis");	    	
	    	operador.setSenha("123456");	
	    	operador.setPerfil(Operador.Perfil.ANALISTA);
	        
	    	service.salvar(operador);
	        assertNotNull(operador.getId());
	        log.info(operador.getNome() + " nome foi recuperado " + operador.getId());
	    }

}
