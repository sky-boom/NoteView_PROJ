package com.wzr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wzr.dao")
public class NoteviewApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(NoteviewApplication.class, args);
    }

}
