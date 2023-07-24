package utils;

import execption.ServiceRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestEither {

    public static void main(String[] args) {
        // Stream中不允许抛出异常

        List<Either<String, User>> users = Stream.iterate(1, i -> i + 1)
            .limit(100)
            .map(TestEither::readLine)
            .collect(Collectors.toList());

        Either<String, List<User>> either = Either.sequence(users, (s1, s2) -> s1 + s2, () -> ServiceRuntimeException.of());

        if (either.isLeft()) {
            System.out.println(either.getLeft());
        } else {
            for (User user : either.getRight()) {
                System.out.println(user);
            }
        }

        // 改造readLine方法，让其返回Either


        // 将List<Either>转换为Either


    }

    public static Either<String, User> readLine(int i) {
        if (new Random().nextInt(100) <= 100) {
            return Either.right(new User("0", "张三", 20, 1));
        } else {
            return Either.left("第" + i + "行数据错误");
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User{

        private String id;

        private String name;

        private int age;
        /**
         * 性别 0-女 1-男
         */
        private int gender;
    }
}
