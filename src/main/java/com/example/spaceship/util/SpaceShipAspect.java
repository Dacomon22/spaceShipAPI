package com.example.spaceship.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpaceShipAspect {

    private static final Logger logger = LoggerFactory.getLogger(SpaceShipAspect.class);

    @Pointcut("execution(* com.example.spaceship.service.SpaceShipService.getSpaceShipById(..)) && args(id)")
    public void getSpaceShipByIdPointcut(String id) {}

    @Before("getSpaceShipByIdPointcut(id)")
    public void logIfIdIsNegative(String id) {
        try {
            long numericId = Long.parseLong(id);
            if (numericId < 0) {
                logger.warn("Requested SpaceShip with a negative ID: {}", id);
                throw new ResourceNotFoundException("ID cannot be negative: " + id);
            }
        } catch (NumberFormatException e) {
            logger.warn("Invalid SpaceShip ID: {}", id);
            throw new ResourceNotFoundException("Invalid ID format: " + id);
        }
    }

    @Autowired
    private KafkaProducer kafkaProducer;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    @After("restControllerMethods()")
    public void logRestCall(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String message = "REST call to " + className + "." + methodName + " with arguments: " + joinPoint.getArgs();

        kafkaProducer.sendMessage(message);
    }
}
