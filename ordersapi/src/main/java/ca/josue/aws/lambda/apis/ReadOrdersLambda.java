package ca.josue.aws.lambda.apis;

import ca.josue.aws.lambda.apis.dto.Order;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadOrdersLambda {
    public APIGatewayProxyResponseEvent getOrders() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = new Order(123, "Mac Book Pro", 15);
        String jsonOutput = objectMapper.writeValueAsString(order);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonOutput);
    }
}
