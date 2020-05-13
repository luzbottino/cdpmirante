package br.com.bottinocode.cdpmirante.operadores;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
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

import br.com.bottinocode.cdpmirante.pessoas.Pessoa;
import br.com.bottinocode.cdpmirante.pessoas.PessoasRepository;
import br.com.bottinocode.cdpmirante.pessoas.PessoasService;
import br.com.bottinocode.cdpmirante.util.Resources;

@RunWith(Arquillian.class)
public class OperadoresServiceTest {
	   @Deployment
	    public static Archive<?> createTestArchive() {
	        return ShrinkWrap.create(WebArchive.class, "test.war")
	                .addClasses(Operador.class, PessoasService.class, PessoasRepository.class, Resources.class)
	                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
	                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
	                // Deploy our test datasource
	                .addAsWebInfResource("test-ds.xml");
	    }

	    @Inject
	    PessoasService service;

	    @Inject
	    Logger log;

	    @Test
	    public void testRegistro() throws Exception {
	    	Pessoa pessoa = Pessoa.com()
	    		.nome("Luis")
	    		.dataNascimento(new Date())
	    		.documento("04715587406")
	    		.nomeMae("Maria")
	    		.nomePai("Armando")
	    		.login("lhaha")
	    		.tipo(Pessoa.Tipo.FISICA)
	    			.construir();    	
	    	
	        
	    	service.salvar(pessoa);
	        assertNotNull(pessoa.getId());
	        log.info(pessoa.getNome() + " nome foi recuperado " + pessoa.getId());
	    }

}
