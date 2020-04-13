package com.conpany.project;


import com.study.room.Application;
import com.study.room.model.Seat;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 单元测试继承该类即可
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@Rollback
public abstract class Tester {
    public static void main(String[] args) {
        String[] row = "[0000,0000,0000]".replace("[", "").replace("]", "").split(",");
        String seats = row[0];
        char[] _seats = seats.toCharArray();
        _seats[1] = Seat.SEAT.FULL;
        System.out.println(_seats);
    }
}



