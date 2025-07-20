import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;



public class OrderData extends BaseData {

        private final String CREATE_ORDER = "/orders";
        private final String CANCEL_ORDER = "/orders/cancel";

        @Step("Создание заказа")
        public ValidatableResponse createOrder(Order order) {
            return getBaseSpec()
                    .body(order)
                    .when()
                    .post(CREATE_ORDER)
                    .then().log().all();
        }

        @Step("Получение заказа")
        public OrdersResponse getOrders(){
            return   getBaseSpec()
                    .when()
                    .get(CREATE_ORDER)
                    .body().as(OrdersResponse.class);

        }

    @Step("Удаление заказа")
    public void  cancelOrder(int orderId) {

    getBaseSpec()

                .when()
                .body("{\"track\":" + orderId + "}")
                .delete(CANCEL_ORDER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

}
