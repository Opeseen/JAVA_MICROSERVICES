package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.products.rest.CreateProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService{
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

  public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate){
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public String createProduct(CreateProductModel productModel) throws Exception {
    String productId = UUID.randomUUID().toString();

    //TODO: Persist product details into database table b4 pushing an event

    ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
        productId, productModel.getTitle(), productModel.getPrice(), productModel.getQuantity()
    );

    LOGGER.info("Before publishing a ProductCreatedEvent");

    // @INFO: This is synchronous
    SendResult<String, ProductCreatedEvent> result =
        kafkaTemplate.send("topic2", productId, productCreatedEvent).get();

    LOGGER.info("Partition: {}", result.getRecordMetadata().partition());
    LOGGER.info("Topic: {}", result.getRecordMetadata().topic());
    LOGGER.info("Offset: {}", result.getRecordMetadata().offset());

    LOGGER.info("******************** Returning product id");
    return productId;
  }
}
