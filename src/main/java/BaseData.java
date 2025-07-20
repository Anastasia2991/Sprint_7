
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class BaseData {
    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/api/v1";

    protected RequestSpecification getBaseSpec() {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URL);

    }

}
