package org.ymall.learn.easyExcel.annotation;

import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cheng-qiang
 * @date 2022年11月17日10:58
 */
@AllArgsConstructor
public class ExcelValidator<T> {

    private final Validator validator;
    private final Integer beginIndex;


    /**
     *  集合校验
     *
     * @param data 待校验的集合
     * @return list
     */
    public List<String> validate(Collection<T> data) {
        int index = beginIndex + 1;
        List<String> messages = new ArrayList<>();
        for (T datum : data) {
            String validated = this.doValidate(index, datum);
            if (StringUtils.hasText(validated)) {
                messages.add(validated);
            }
            index++;
        }
        return messages;
    }

    /**
     * 这里是校验的根本方法
     *
     * @param index 本条数据所在的行号
     * @param data 待校验的某条数据
     * @return 对数据的校验异常进行提示，如果有触发校验规则的会封装提示信息。
     */
    private String doValidate(int index, T data) {
        // 这里使用了JSR303的的校验器，同时使用了分组校验，Excel为分组标识
        Set<ConstraintViolation<T>> validate = validator.validate(data, Excel.class);
        Path propertyPath = null;
        for (ConstraintViolation<T> tConstraintViolation : validate) {
            propertyPath = tConstraintViolation.getPropertyPath();
        }
        return validate.size()>0 ? propertyPath+"_第_" + index +
                "_行触发约束" + validate.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(",")): "";
    }
}
