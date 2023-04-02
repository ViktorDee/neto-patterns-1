package tests;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import utils.DataGenerator;

import java.time.Duration;


import static com.codeborne.selenide.Selenide.*;
import static utils.DataGenerator.generateDate;

public class CardDeliveryTest {


    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    @DisplayName("Should plan meeting successfully")
    void testValidDataWithReplanning() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var firstMeetingDays = (5);
        var firstMeetingDate = DataGenerator.generateDate(firstMeetingDays);
        var secondMeetingDays = (10);
        var secondMeetingDate = DataGenerator.generateDate(secondMeetingDays);


        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $x("//span[contains(text(),'условиями')]").click();
        $x("//span[contains(text(),'Запланировать')]").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(5))
                .shouldBe(Condition.visible);
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $x("//span[contains(text(),'Запланировать')]").click();
        $("[data-test-id=replan-notification]")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible);
        $x("//span[contains(text(),'Перепланировать')]").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(4))
                .shouldBe(Condition.visible);
    }
}
