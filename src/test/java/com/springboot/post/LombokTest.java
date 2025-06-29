package com.springboot.post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LombokTest {
    private Long id;
    private String name;
    public static void main(String[] args) {
        LombokTest test = new LombokTest();
        test.setId(1L);
        test.setName("Test Name");
        System.out.println(test.getId() + ": " + test.getName());
    }
    }
