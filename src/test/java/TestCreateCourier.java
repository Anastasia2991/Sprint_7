
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestCreateCourier {

        private Courier courier;
        private CourierAccount courierAccount;
        private int courierId = 0;


        @Before
        public void setup() {
        courier = Courier.getRandomCourier();
        courierAccount = new CourierAccount();
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
        @DisplayName("Проверка успешного создания курьера")
        @Description("Создаем рандомного курьера и логинимся под ним")
        public void createCourier(){
        boolean isOk = courierAccount.create(courier)
                .statusCode(SC_CREATED)
                .extract().path("ok");
        assertTrue(isOk);

    }

        @Test // Текст ошибки взял из спеки
        @DisplayName("Проверка создания двух одинаковых курьеров")
        @Description("Создаем одного курьера и сразу второго с такими же данными")
        public void createDuplicateCourier(){
        boolean isOk = courierAccount.create(courier)
                .statusCode(SC_CREATED)
                .extract().path("ok");

        String actual = courierAccount.create(courier)
                .statusCode(SC_CONFLICT)
                .extract().path("message");

        String expected = "Этот логин уже используется.";

        assertTrue(isOk);
        assertEquals("Должна быть ошибка, что такой логин используется", expected, actual);

    }

        @Test
        @DisplayName("Проверка создания курьера без логина")
        @Description("Отправляем запрос на создание курьера без атрибута логина")
        public void createCourierWithoutLogin(){
        courier = Courier.getCourierWithoutLogin();

        String actual = courierAccount.create(courier)
                .statusCode(SC_BAD_REQUEST)
                .extract().path("message");

        String expected = "Недостаточно данных для создания учетной записи";

        assertEquals("Должна быть ошибка, что недостаточно данных",expected, actual);

    }


        @Test
        @DisplayName("Проверка создания курьера без пароля")
        @Description("Отправка запроса на создание курьера без пароля")
        public void createCourierWithoutPassword(){
        courier = Courier.getCourierWithoutPassword();

        String actual = courierAccount.create(courier)
                .statusCode(SC_BAD_REQUEST)
                .extract().path("message");

             String expected = "Недостаточно данных для создания учетной записи";

             assertEquals("Должен быть текст,что данных недостаточно", expected, actual);

         }

        @Test
        @DisplayName("Проверка создания курьера без имени")
        @Description("Отправляем запрос на создание курьера без атрибута имени")
        public void createCourierWithoutFirstName(){
        boolean isOk = courierAccount.create(courier)
                .statusCode(SC_CREATED)
                .extract().path("ok");

                assertTrue(isOk);
        }
}
