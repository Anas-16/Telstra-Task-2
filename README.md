# Telstra Starter Repo :bird:

This repo has everything you need to get started on the Telstra program!

## SIM Card Activation Microservice

This microservice acts as a gateway for SIM card activation requests from Telstra stores. It receives activation requests and forwards them to the actuator service.

### Prerequisites

- Java 11 JDK
- Maven
- The actuator service JAR file (included in `services/` folder)

### Running the Application

1. **Start the Actuator Service**
   ```bash
   cd services
   java -jar SimCardActuator.jar
   ```
   The actuator will start on `http://localhost:8444`

2. **Start the SIM Card Activator Service**
   ```bash
   mvn spring-boot:run
   ```
   The service will start on `http://localhost:8080`

### API Endpoints

#### POST /api/activate
Activates a SIM card by forwarding the request to the actuator service.

**Request Body:**
```json
{
  "iccid": "12345678901234567890",
  "customerEmail": "customer@example.com"
}
```

**Response:**
- `"SIM card activation successful"` - if activation succeeds
- `"SIM card activation failed"` - if activation fails
- `"Error: Unable to communicate with actuator"` - if actuator is unreachable

### Testing

You can test the API using curl:

```bash
curl -X POST http://localhost:8080/api/activate \
  -H "Content-Type: application/json" \
  -d '{
    "iccid": "12345678901234567890",
    "customerEmail": "customer@example.com"
  }'
```

### Project Structure

- `src/main/java/au/com/telstra/simcardactivator/`
  - `controller/` - REST controllers
  - `dto/` - Data Transfer Objects
  - `config/` - Configuration classes
- `src/test/` - Test files including Cucumber BDD tests
