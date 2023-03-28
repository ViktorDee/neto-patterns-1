package utils;
import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import entities.DeliveryData;

import java.util.Locale;

@UtilityClass
public class DataGenerator {

    @UtilityClass
    public static class Delivery {
        public static DeliveryData generateData(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new DeliveryData(faker.name().fullName(),
                    faker.phoneNumber().phoneNumber());
        }
    }
}
