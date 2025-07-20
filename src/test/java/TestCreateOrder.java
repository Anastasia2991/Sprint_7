import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TestCreateOrder {
    private String[] colors;
    private Order order;
    private OrderData orderData = new OrderData();
    private int track;

    public TestCreateOrder(String[] colors){
        this.colors = colors;
    }

    @Before
    public void setup(){
        order = Order.getOrder();
    }

    @After
    public void teardown() {
        orderData.cancelOrder(track);

    }

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"GRAY", "BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с разными параметрами цвета")
    public void createOrderWithParams(){
        order.setColor(colors);

        track = orderData.createOrder(order)
                .statusCode(201)
                .extract().path("track");

        assertTrue(track > 0);
    }
}
