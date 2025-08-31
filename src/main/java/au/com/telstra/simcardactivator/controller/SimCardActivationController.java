package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.ActivationRequest;
import au.com.telstra.simcardactivator.dto.ActuatorRequest;
import au.com.telstra.simcardactivator.dto.ActuatorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class SimCardActivationController {

    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/activate")
    public String activateSimCard(@RequestBody ActivationRequest request) {
        System.out.println("Received activation request: " + request);

        // Create request for actuator
        ActuatorRequest actuatorRequest = new ActuatorRequest(request.getIccid());
        
        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Create HTTP entity
        HttpEntity<ActuatorRequest> entity = new HttpEntity<>(actuatorRequest, headers);

        try {
            // Send request to actuator
            ResponseEntity<ActuatorResponse> response = restTemplate.postForEntity(
                ACTUATOR_URL, 
                entity, 
                ActuatorResponse.class
            );

            ActuatorResponse actuatorResponse = response.getBody();
            
            if (actuatorResponse != null) {
                if (actuatorResponse.isSuccess()) {
                    System.out.println("SIM card activation SUCCESSFUL for ICCID: " + request.getIccid());
                    return "SIM card activation successful";
                } else {
                    System.out.println("SIM card activation FAILED for ICCID: " + request.getIccid());
                    return "SIM card activation failed";
                }
            } else {
                System.out.println("Received null response from actuator for ICCID: " + request.getIccid());
                return "Error: No response from actuator";
            }
            
        } catch (Exception e) {
            System.out.println("Error communicating with actuator for ICCID: " + request.getIccid() + " - " + e.getMessage());
            return "Error: Unable to communicate with actuator";
        }
    }
} 