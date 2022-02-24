package com.example.demo.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.example.demo.WordCount;


@RestController
public class WordController {

    @Autowired
    WordCount wordCount;

    @PostMapping("/addWord")
    public  ResponseEntity<String> addWord(@RequestBody String word) {
	try {
	    if(!StringUtils.isEmpty(word)) {
		if(wordCount.addWord(word)) {
		    return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	    } 
	}catch (HttpServerErrorException e) {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return null;
    }

    @GetMapping("/getWordCount/{word}")
    public  ResponseEntity<String> getWordCount(@PathVariable String word) {
	int count = wordCount.getCount(word);

	if (count == 0)
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	else {
	    return new ResponseEntity<>(
		    "Word Count is " + count, 
		    HttpStatus.OK);
	}
    }

}
