package com.vemser.rest.data.factory;

import com.vemser.rest.utils.Manipulation;
import net.datafaker.Faker;

import java.util.Locale;
import java.util.Properties;

public class BaseDataFactory{
    protected static Faker faker = new Faker(new Locale("PT-BR"));
    private static final Properties prop = Manipulation.getProp();

    public static int gerarIntAleatorio(){
        return  faker.number().numberBetween(1, 100);
    }
    public static boolean gerarBoleanoAleatorio(){
        return  faker.bool().bool();
    }
    public static String gerarStringAleatorio(){
        return  faker.lorem().characters();
    }
    public static String emailProp() { return prop.getProperty("email"); }
    public static String senhaProp() { return prop.getProperty("password"); }
}

