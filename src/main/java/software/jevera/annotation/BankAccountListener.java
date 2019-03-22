package software.jevera.annotation;


import software.jevera.service.bankaccount.BankAccountStateEnum;

import java.lang.reflect.Method;

import static software.jevera.service.bankaccount.BankAccountStateEnum.BLOCKED_BY_BANK;


public class BankAccountListener {

    void blocked(Object o, Class c){
        Method[] methods = c.getDeclaredMethods();
    }

}
