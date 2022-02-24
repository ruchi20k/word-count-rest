package com.example.demo;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc
@SpringBootTest(classes = WordCounterRestApplication.class)
class WordCountApiTest {

    private static final String GET_WORD_COUNT = "/getWordCount/{word}";
    private static final String ADD_WORD = "/addWord";

    public static final String VALID_USER_TEST_CREDENTIALS = "admin" + ":" + "password";

    @Autowired
    private MockMvc mockmvc;


    @Test
    public void addAndGetWordTest() throws Exception {

	mockmvc.perform(MockMvcRequestBuilders.post(ADD_WORD)
		.header(HttpHeaders.AUTHORIZATION, getAuthToken(VALID_USER_TEST_CREDENTIALS))
		.content("ruchi")
		.contentType(MediaType.TEXT_PLAIN))
	.andExpect(status().isCreated());
	
	mockmvc.perform(MockMvcRequestBuilders.get(GET_WORD_COUNT, "ruchi")
                .header(HttpHeaders.AUTHORIZATION, getAuthToken(VALID_USER_TEST_CREDENTIALS))
                .content("ruchi")
                .accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
	
    }

    public static String getAuthToken(String credentials) {
	return "Basic " + DatatypeConverter.printBase64Binary(credentials.getBytes());
    }

}
