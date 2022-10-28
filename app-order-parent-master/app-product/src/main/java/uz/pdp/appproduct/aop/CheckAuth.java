package uz.pdp.appproduct.aop;

import uz.pdp.appproduct.dto.enums.PermissionEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckAuth {

    PermissionEnum[] permissions() default {};

}
