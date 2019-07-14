package ar.edu.unq.desapp.grupoa.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import static java.util.Arrays.asList;

@Aspect
@Component
public class LoggingAspect {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //This tells the aspect what is going to be intercepting. In this case, i'm telling it to
    //Intercept everything that is in the service package.

    //the proceedingJoinPoint intercepts and captures metadata that is meant for the real object being called
    //You can do what you want with that data, and at the end is necessary to call "proceed"
    // in order for the real method to be called

    //    @Around("@annotation(LogEntryAndArguments)")
    @Around("execution(public * ar.edu.unq.desapp.grupoa.service.*.*(..)))")
    public Object LogEntryAndArguementsAnnotation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String userName = getCurrentUsername();
        String method = getMethod(proceedingJoinPoint);
        String timeStamp = DateTime.now().toString();
        String arguments = getArguments(proceedingJoinPoint);

        LOGGER.info("///////// \n  user {} called {}  at {}  \n with arguments: \n {} \n /////////",userName, method, timeStamp, arguments);
        return proceedingJoinPoint.proceed();
    }

    private String getArguments(ProceedingJoinPoint proceedingJoinPoint) {
        StringBuilder sb = new StringBuilder();

        asList(proceedingJoinPoint.getArgs()).stream().forEach(argument -> {
            sb.append(" " + argument);
        });

        return sb.toString();
    }

    private String getMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        return String.valueOf(methodSignature.getMethod());
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String userName = "System";
        if (auth != null ){
            userName = auth.getName();
        }

        return userName;
    }


}
