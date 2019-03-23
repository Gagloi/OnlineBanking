package software.jevera.annotation;


import software.jevera.domain.BankAccount;
import software.jevera.service.bankaccount.BankAccountStateEnum;
import software.jevera.service.bankaccount.StateMachine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Target(value = ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Blockable {


}
