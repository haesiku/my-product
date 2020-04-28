package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaListner {

//    @StreamListener(Processor.INPUT)
//    public void onEventByString(@Payload String productChanged){
//        System.out.println(productChanged);
//    }

    @Autowired
    ProductRepository productRepository;

    @StreamListener(Processor.INPUT)
    public void onEventByString(@Payload OrderPlaced orderPlaced){
        if (orderPlaced.getEventType().equals("OrderPlaced")) {
            System.out.println("Order Id : " + orderPlaced.getOrderId());
            System.out.println("Ordered Product Id : " + orderPlaced.getProductId());
            System.out.println("Ordered Product Name : " + orderPlaced.getProductName());
            System.out.println("Ordered Product Quantity : " + orderPlaced.getProductQty());

            Product orderedProduct = new Product();
            orderedProduct.setName(orderPlaced.getProductName());
            orderedProduct.setStock(orderPlaced.getProductQty());

            this.productRepository.save(orderedProduct);
        }
    }

}
