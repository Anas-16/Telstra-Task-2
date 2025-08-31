package au.com.telstra.simcardactivator.repository;

import au.com.telstra.simcardactivator.entity.SimCardActivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimCardActivationRepository extends JpaRepository<SimCardActivation, Long> {
    // Basic CRUD operations are automatically provided by JpaRepository
    // We can add custom query methods here if needed in the future
} 