package tth.loyalty.program.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

//@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

	
    @Autowired
  //  private TestRestTemplate restTemplate;

    @Test
    public void testPurchasePoints() {
    	/*
        ResponseEntity<Client> responseEntity =

            restTemplate.postForEntity("/clients", new CreateClientRequest("Foo"), Client.class);

        Client client = responseEntity.getBody();



        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        assertEquals("Foo", client.getName()); */

    }

}