package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.ActivationRequest;
import au.com.telstra.simcardactivator.dto.SimCardActivationResponse;
import au.com.telstra.simcardactivator.service.SimCardActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SimCardActivationController {

    @Autowired
    private SimCardActivationService activationService;

    @PostMapping("/activate")
    public String activateSimCard(@RequestBody ActivationRequest request) {
        return activationService.activateSimCard(request);
    }

    @GetMapping("/activation/{simCardId}")
    public ResponseEntity<SimCardActivationResponse> getActivationById(@PathVariable Long simCardId) {
        SimCardActivationResponse response = activationService.getActivationById(simCardId);
        
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} 