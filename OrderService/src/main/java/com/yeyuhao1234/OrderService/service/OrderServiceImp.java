package com.yeyuhao1234.OrderService.service;

import com.yeyuhao1234.OrderService.entity.Order;
import com.yeyuhao1234.OrderService.exception.CustomException;
import com.yeyuhao1234.OrderService.external.client.PaymentService;
import com.yeyuhao1234.OrderService.external.client.ProductService;
import com.yeyuhao1234.OrderService.external.request.PaymentRequest;
import com.yeyuhao1234.OrderService.external.response.PaymentResponse;
import com.yeyuhao1234.OrderService.model.OrderRequest;
import com.yeyuhao1234.OrderService.model.OrderResponse;
import com.yeyuhao1234.OrderService.model.ProductResponse;
import com.yeyuhao1234.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImp implements  OrderService{

    @Autowired
    private OrderRepository orderRepository;
    // Order Entity -> save the data with Status Order created
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public OrderResponse getOrderById(long orderId) {
        log.info("Get the order for orderId: {}" , orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Order not found for the orderId"+ orderId
                    ,"NOT_FOUND",404
                ));

        log.info("Invoking Product service to fetch product for product Id ");
        ProductResponse productResponse = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/api/v1/product/" + order.getProductId(),
                ProductResponse.class
        );


        log.info("Getting payment information from the payment Service");
        PaymentResponse paymentResponse = restTemplate.getForObject(
                "http://PAYMENT-SERVICE/api/v1/payment/order/"+order.getId(),
                    PaymentResponse.class
        );



        OrderResponse.ProductDetails productDetails
                = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .price(productResponse.getPrice())
                .build();

        OrderResponse.PaymentDetails paymentDetails
                = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .status(paymentResponse.getStatus())
                .paymentMode(paymentResponse.getPaymentMode())
                .paymentDate(paymentResponse.getPaymentDate())
                .orderId(paymentResponse.getOrderId())
                .amount(paymentResponse.getAmount())
                .build();


        OrderResponse orderResponse = OrderResponse
                .builder()
                .orderId(orderId)
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        return orderResponse;
    }

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing Order request: {}", orderRequest);

        // Call api by feign.
        // Product service - Block Products (Reduce quantity)


        log.info("Creating Order with created Status");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
        order = orderRepository.save(order);


        log.info("Order Place successfully with order Id: {}", order.getId());

        // payment service -> payment -> success -> Complete, else
        log.info("Callig payment Service to complete the payment");
        PaymentRequest paymentRequest = PaymentRequest
                .builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;
        try{
            // Call payment service api by feign.
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Changing the ORDER status to placed");
            orderStatus = "PAYMENT_PLACED";
        }catch (Exception e){
            log.error("Error Occured in payment. Chaing the Order status to failed");
            orderStatus = "PAYMENT_FAIL";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order Places successfully with Order ID");
        return order.getId();

    }
}
