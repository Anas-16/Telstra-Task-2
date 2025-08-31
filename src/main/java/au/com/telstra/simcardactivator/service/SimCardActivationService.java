package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.dto.ActivationRequest;
import au.com.telstra.simcardactivator.dto.ActuatorRequest;
import au.com.telstra.simcardactivator.dto.ActuatorResponse;
import au.com.telstra.simcardactivator.dto.SimCardActivationResponse;
import au.com.telstra.simcardactivator.entity.SimCardActivation;
import au.com.telstra.simcardactivator.repository.SimCardActivationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class SimCardActivationService {

    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";

    @Autowired
    private SimCardActivationRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public String activateSimCard(ActivationRequest request) {
        System.out.println("Received activation request: " + request);

        ActuatorRequest actuatorRequest = new ActuatorRequest(request.getIccid());
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<ActuatorRequest> entity = new HttpEntity<>(actuatorRequest, headers);

        boolean activationSuccess = false;
        String resultMessage;

        try {
            ResponseEntity<ActuatorResponse> response = restTemplate.postForEntity(
                ACTUATOR_URL, 
                entity, 
                ActuatorResponse.class
            );

            ActuatorResponse actuatorResponse = response.getBody();
            
            if (actuatorResponse != null) {
                activationSuccess = actuatorResponse.isSuccess();
                
                if (activationSuccess) {
                    System.out.println("SIM card activation SUCCESSFUL for ICCID: " + request.getIccid());
                    resultMessage = "SIM card activation successful";
                } else {
                    System.out.println("SIM card activation FAILED for ICCID: " + request.getIccid());
                    resultMessage = "SIM card activation failed";
                }
            } else {
                System.out.println("Received null response from actuator for ICCID: " + request.getIccid());
                resultMessage = "Error: No response from actuator";
            }
            
        } catch (Exception e) {
            System.out.println("Error communicating with actuator for ICCID: " + request.getIccid() + " - " + e.getMessage());
            resultMessage = "Error: Unable to communicate with actuator";
        }

        SimCardActivation activation = new SimCardActivation(
            request.getIccid(), 
            request.getCustomerEmail(), 
            activationSuccess
        );
        
        SimCardActivation savedActivation = repository.save(activation);
        System.out.println("Saved activation record to database with ID: " + savedActivation.getId());

        return resultMessage;
    }

    public SimCardActivationResponse getActivationById(Long simCardId) {
        Optional<SimCardActivation> activation = repository.findById(simCardId);
        
        if (activation.isPresent()) {
            SimCardActivation simCard = activation.get();
            return new SimCardActivationResponse(
                simCard.getIccid(),
                simCard.getCustomerEmail(),
                simCard.isActive()
            );
        } else {
            return null;
        }
    }
} 