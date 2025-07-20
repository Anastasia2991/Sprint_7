import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


public class CourierAccount extends BaseData {

    private final String CREATE_COURIER = "/courier";
    private final String DELETE_COURIER = "/courier/{courierId}";
    private final String LOGIN_COURIER ="/courier/login";


    @Step("Создание курьера {courier}")
    public ValidatableResponse create(Courier courier) {
        return getBaseSpec()
                .body(courier)
                .when()
                .post(CREATE_COURIER)
                .then().log().all();
    }

    @Step("Удаление курьера {courierId}")
    public void delete(int courierId) {
        getBaseSpec()
                .pathParam("courierId", courierId)
                .when()
                .delete(DELETE_COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
        @Step("Логин {courier} курьера")
         public ValidatableResponse login(CourierCredentials creds) {
        return getBaseSpec()
        .body(creds)
        .when()
        .post(LOGIN_COURIER)
        .then().log().all();
        }
}
