package com.example.mywebquizengine.Controller.api;

import com.example.mywebquizengine.Model.Projection.ReceivedRequestView;
import com.example.mywebquizengine.Model.Projection.SentRequestView;
import com.example.mywebquizengine.Model.Request;
import com.example.mywebquizengine.Service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ApiRequestController {

    @Autowired
    private RequestService requestService;


    @PostMapping(path = "/sendRequest")
    @ResponseBody
    public void sendRequest(@RequestBody Request request, @AuthenticationPrincipal Principal principal) throws JsonProcessingException, ParseException {
        requestService.sendRequest(request, principal);
        throw new ResponseStatusException(HttpStatus.OK);
    }

    @PostMapping(path = "/acceptRequest")
    //@PreAuthorize(value = "!#principal.name.equals(#user.username)")
    public Long acceptRequest(@RequestBody Request request, @AuthenticationPrincipal Principal principal) {
        return requestService.acceptRequest(request.getId(), principal);
    }

    @GetMapping(path = "/requests")
    public ArrayList<ReceivedRequestView> getMyRequests(@AuthenticationPrincipal Principal principal) {
        return requestService.getMyRequests(principal);
    }

    @PostMapping(path = "/rejectRequest")
    public void rejectRequest(@RequestBody Request request, @AuthenticationPrincipal Principal principal) {
        requestService.rejectRequest(request, principal);
        throw new ResponseStatusException(HttpStatus.OK);
    }


    @GetMapping(path = "/sentRequests")
    public List<SentRequestView> getSentRequests(@AuthenticationPrincipal Principal principal) {
        return requestService.getSentRequests(principal);
    }

}
