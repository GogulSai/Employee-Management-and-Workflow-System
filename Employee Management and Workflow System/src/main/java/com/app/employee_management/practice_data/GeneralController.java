package com.app.employee_management.practice_data;

import com.app.employee_management.practice_data.multibean_condOnProp.MsgNotificationService;
import com.app.employee_management.practice_data.multibean_condOnProp.MultipleBeanProblem;
import com.app.employee_management.practice_data.profiling.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
public class GeneralController {


    private final NotificationService notificationService;
    private final MsgNotificationService msgNotificationService;

    private final Environment environment;

    private final InjectValue injectValue; //Make it private final for auto constructor injection

    private final MultipleBeanProblem multipleBeanProblem;

    //Qualifier usage
   /* @Autowired
    public ProfileController(@Qualifier("second") MultipleBeanProblem multipleBeanProblem) {
        this.multipleBeanProblem = multipleBeanProblem;
    }*/

    //Profile difference
    @GetMapping("/notify")
    public String notify(@RequestParam String msg) {
        notificationService.send(msg); // prints in console based on active profile
        return "Triggered: " + notificationService.getClass().getSimpleName()
                + " | Active Profiles: " + Arrays.toString(environment.getActiveProfiles());

    }

    //Value inject
    @GetMapping("/inject")
    public String inject() {
       return injectValue.toString();

    }

    //Bean Conflict
    @GetMapping("/multiBean")
    public String bean() {
        return multipleBeanProblem.beanName("BEAN");

    }

    //ConditionalOnProperty
    @GetMapping("/conditionOn")
    public void conditionOn() {
        msgNotificationService.send("What comes");

    }

    //Request HTTP types
    @GetMapping(path = "/header", consumes="application/json", produces="application/xml")
    public String withHeader(@RequestHeader("X-head") String value, @CookieValue(name="SESSION", required=false) String session, HttpServletRequest req){
        return value + session + req.getRequestURI();
    }

    //Versioning
    @GetMapping(path = "/version", params="version=2")
    public String version(){
    return "Version 2";
    }

    @GetMapping(path = "/version", params="X-API-VERSION=1")
    public String versionHdr(){
        return "Header Version 1";
    }
}
