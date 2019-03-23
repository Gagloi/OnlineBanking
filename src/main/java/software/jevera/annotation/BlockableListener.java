package software.jevera.annotation;



import software.jevera.domain.BankAccount;
import software.jevera.exceptions.BusinessException;
import software.jevera.service.bankaccount.BankAccountService;
import software.jevera.service.bankaccount.BankAccountStateEnum;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


import static software.jevera.service.bankaccount.BankAccountStateEnum.BLOCKED_BY_BANK;
import static software.jevera.service.bankaccount.BankAccountStateEnum.BLOCKED_BY_USER;


public class BlockableListener {

    public static void blocked(Class c, BankAccount bankAccount) throws NoSuchFieldException, InstantiationException, IllegalAccessException{
        Class bankAccountService = BankAccountService.class;
        Method[] methods = bankAccountService.getDeclaredMethods();


        Field field = c.getDeclaredField("currentState");
        field.setAccessible(true);
        BankAccountStateEnum value = (BankAccountStateEnum) field.get(bankAccount);
        for (Method m: methods){
            Annotation[] annotations = m.getDeclaredAnnotations();
            for (Annotation annotation: annotations){
                if (annotation.annotationType().equals(Blockable.class) && (value.equals(BLOCKED_BY_BANK) || value.equals(BLOCKED_BY_USER))) {
                    throw new BusinessException("Неа!");
                }
            }

        }
    }

}
