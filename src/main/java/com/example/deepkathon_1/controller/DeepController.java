package com.example.deepkathon_1.controller;

import com.example.deepkathon_1.model.dto.CommentResponse;
import com.example.deepkathon_1.service.DeepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deep")
@RequiredArgsConstructor
public class DeepController {

    private final DeepService deepService;
    @GetMapping("/who/is/winner/{userIdx}/{postIdx}")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable(name = "userIdx") Long userIdx,
            @PathVariable(name = "postIdx") Long postIdx

    ){
        List<CommentResponse> commentResponseList = deepService.getComments(userIdx,postIdx);
        return ResponseEntity.ok(commentResponseList);

    }
}
