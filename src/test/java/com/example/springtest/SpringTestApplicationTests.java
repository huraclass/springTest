package com.example.springtest;

import com.example.springtest.mapper.BoardMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringTestApplicationTests {

    @Autowired
    BoardMapper mapper;

    @Test
    void contextLoads() {
    }

}
