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


public class BankAccountListener {

    public static void blocked(Class c, BankAccount bankAccount) throws NoSuchFieldException, InstantiationException, IllegalAccessException{
        Class bankAccountService = BankAccountService.class;
        Method[] methods = bankAccountService.getDeclaredMethods();


        Field field = c.getDeclaredField("currentState");
        field.setAccessible(true);
        BankAccountStateEnum value = (BankAccountStateEnum) field.get(bankAccount);
        System.out.println(value);
        for (Method m: methods){
            Annotation[] annotations = m.getDeclaredAnnotations();
            for (Annotation annotation: annotations){
                System.out.println("hii");
                if (annotation.annotationType().equals(BlockedMethod.class) && value.equals(BLOCKED_BY_BANK)) {
                    throw new BusinessException("Неа!");
                }
            }

        }
    }

}
