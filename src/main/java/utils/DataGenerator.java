package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Random;

public class DataGenerator {

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        var cities = new String[]{"Кемерово", "Новосибирск", "Санкт-Петербург", "Рязань", "Самара", "Москва"};

        return cities[new Random().nextInt(cities.length)];
    }
}
