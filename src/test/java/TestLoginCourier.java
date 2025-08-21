import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class TestLoginCourier {
    private Courier courier;
    private CourierAccount courierAccount= new CourierAccount();
    private int courierId;

    @Before
    public void setup(){
        courier = Courier.getRandomCourier();
        courierAccount.create(courier).statusCode(SC_CREATED);
    }

    @After
    public void teardown() {
        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierAccount.login(creds)
                .statusCode(SC_OK)
                .extract().path("id");
        courierAccount.delete(courierId);
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Вход под учетными данными рандомного курьера, проверка statusCode и что вернулся его id")
    public void courierLoginPositiveTest(){

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierAccount.login(creds)
                .statusCode(SC_OK)
                .extract().path("id");

        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Авторизация курьера без атрибута login")
    @Description("Отправка запроса без атрибута login для авторизации и проверка ошибки")
    public void courierLoginWithoutLogin(){

        CourierCredentials creds = CourierCredentials.withoutLoginAttribute(courier);
        String actual = courierAccount.login(creds)
                .statusCode(SC_BAD_REQUEST)
                .extract().path("message");

        String expected = "Недостаточно данных для входа";
        assertEquals("Текс ошибки должен совпадать",expected,actual);
    }

    @Test
    @DisplayName("Авторизация курьера без атрибута Password")
    @Description("Отправка запроса без атрибута Password для авторизации и проверка ошибки")
    public void courierLoginWithoutPassword(){

        CourierCredentials creds = CourierCredentials.withoutPasswordAttribute(courier);
        String actual = courierAccount.login(creds)
                .statusCode(SC_BAD_REQUEST)
                .extract().path("message");

        String expected = "Недостаточно данных для входа";
        assertEquals("Текс ошибки должен совпадать",expected,actual);

    }

    @Test
    @DisplayName("Авторизация курьера c неправильным login")
    @Description("Отправка запроса c неправильным login для авторизации и проверка ошибки")
    public void courierLoginWithIncorrectLogin(){

        CourierCredentials creds = CourierCredentials.withIncorrectLogin(courier);
        String actual = courierAccount.login(creds)
                .statusCode(404)
                .extract().path("message");

        String expected = "Учетная запись не найдена";
        assertEquals("Текс ошибки должен совпадать",expected,actual);
    }
    @Test
    @DisplayName("Авторизация курьера c неправильным паролем")
    @Description("Отправка запроса c неправильным паролем для авторизации и проверка ошибки")
    public void courierLoginWithIncorrectPassword(){

        CourierCredentials creds = CourierCredentials.withIncorrectPassword(courier);
        String actual = courierAccount.login(creds)
                .statusCode(SC_NOT_FOUND)
                .extract().path("message");

        String expected = "Учетная запись не найдена";
        assertEquals("Текс ошибки должен совпадать",expected,actual);

    }
}
