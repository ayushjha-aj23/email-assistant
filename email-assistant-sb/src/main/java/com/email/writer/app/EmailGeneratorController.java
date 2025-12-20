package com.email.writer.app;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/email")
@CrossOrigin(origins = "*") // Accepting request from any origin -> CORS (Cross Origin Resource Sharing)
@AllArgsConstructor
public class EmailGeneratorController {

    private final EmailGeneratorService emailGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateEmail(@RequestBody EmailRequest emailRequest){
        String response = emailGeneratorService.generateEmailReply(emailRequest);
        return ResponseEntity.ok(response);
    }

    // To Check the endpoint only

    @GetMapping("/generate")
    public ResponseEntity<String> hi(){
        return ResponseEntity.ok("Hi! There");
    }
    
}
