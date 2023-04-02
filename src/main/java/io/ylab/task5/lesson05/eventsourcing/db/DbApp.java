package io.ylab.task5.lesson05.eventsourcing.db;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SuppressWarnings("SqlResolve")
public class DbApp {

  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    // в контексте иницилизируем dbAppTest который и делает демонстрацию работы
  }



}
