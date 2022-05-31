package ca.josue.aws.lambda.apis;

import ca.josue.aws.lambda.apis.dto.Order;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateOrderLambda {
    public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.readValue(request.getBody(), Order.class);

        // initialize dynamoDB client
        DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());

        // create table if not exists
        Table orders_table = dynamoDB.getTable(System.getenv("ORDERS_TABLE"));

        // create item
        Item item = new Item()
                .withPrimaryKey("id", order.id)
                .withString("itemName", order.itemName)
                .withInt("quantity", order.quantity);

        // save item
        orders_table.putItem(item);

        return new APIGatewayProxyResponseEvent().withStatusCode(201).withBody("Order ID : " + order.id);
    }
}
