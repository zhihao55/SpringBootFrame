package com.zhihao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Demo3ApplicationTests {

    @Test
    @Async
    void contextLoads() {



        //创建一个任务对象
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    System.out.println("子线程："+i);
                }
            }
        };
        Thread thread = new Thread(runnable);
        //启动线程
        thread.start();

//        for (int i=0;i<10;i++){
//            System.out.println("主线程："+i);
//        }

    }

    public void test(){

    }

}
