package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import au.com.telstra.simcardactivator.dto.ActivationRequest;
import au.com.telstra.simcardactivator.dto.SimCardActivationResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    
    @Autowired
    private TestRestTemplate restTemplate;

    private String activationResponse;
    private SimCardActivationResponse queryResponse;

    @Given("the actuator service is running on localhost:8444")
    public void theActuatorServiceIsRunningOnLocalhost8444() {
        System.out.println("Assuming actuator service is running on localhost:8444");
    }

    @When("I send a POST request to {string} with ICCID {string} and customer email {string}")
    public void iSendAPostRequestToWithIccidAndCustomerEmail(String endpoint, String iccid, String customerEmail) {
        ActivationRequest request = new ActivationRequest(iccid, customerEmail);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<ActivationRequest> entity = new HttpEntity<>(request, headers);
        
        ResponseEntity<String> response = restTemplate.postForEntity(
            "http://localhost:8080" + endpoint,
            entity,
            String.class
        );
        
        activationResponse = response.getBody();
        System.out.println("Activation response: " + activationResponse);
    }

    @Then("the response should indicate successful activation")
    public void theResponseShouldIndicateSuccessfulActivation() {
        assertNotNull(activationResponse);
        assertTrue(activationResponse.contains("successful"));
        System.out.println("✓ Activation was successful");
    }

    @Then("the response should indicate failed activation")
    public void theResponseShouldIndicateFailedActivation() {
        assertNotNull(activationResponse);
        assertTrue(activationResponse.contains("failed"));
        System.out.println("✓ Activation failed as expected");
    }

    @Then("the activation record should be saved to the database with ID {int}")
    public void theActivationRecordShouldBeSavedToTheDatabaseWithId(int expectedId) {
        ResponseEntity<SimCardActivationResponse> response = restTemplate.getForEntity(
            "http://localhost:8080/api/activation/" + expectedId,
            SimCardActivationResponse.class
        );
        
        assertTrue(response.getStatusCode().is2xxSuccessful());
        queryResponse = response.getBody();
        assertNotNull(queryResponse);
        System.out.println("✓ Activation record saved with ID: " + expectedId);
    }

    @Then("the activation record with ID {int} should show successful activation")
    public void theActivationRecordWithIdShouldShowSuccessfulActivation(int id) {
        assertNotNull(queryResponse);
        assertTrue(queryResponse.isActive());
        System.out.println("✓ Database record shows successful activation for ID: " + id);
    }

    @Then("the activation record with ID {int} should show failed activation")
    public void theActivationRecordWithIdShouldShowFailedActivation(int id) {
        assertNotNull(queryResponse);
        assertFalse(queryResponse.isActive());
        System.out.println("✓ Database record shows failed activation for ID: " + id);
    }
}