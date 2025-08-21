import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class TestGetListOfOrder {


    private OrderData orderData = new OrderData();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение спискф заказов и проверка, что объект не пустой")
    public void getListOrdersNotNull() {
        OrdersResponse response = orderData.getOrders();

        MatcherAssert.assertThat(response, notNullValue());

    }


}
