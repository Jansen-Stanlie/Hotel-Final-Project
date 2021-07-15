package com.Stans.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtil {

    private static SqlSessionFactory factory;

    static{
        Reader reader;
        try{
            reader=Resources.getResourceAsReader("SqlMapConfig.xml");
        }catch(IOException exception){
            throw new RuntimeException(exception.getMessage());
        }
        factory=new SqlSessionFactoryBuilder().build(reader);
    }
    public static SqlSessionFactory getSqlSessionFactory(){
        return factory;
    }
}
