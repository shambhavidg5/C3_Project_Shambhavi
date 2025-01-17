import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    Restaurant restaurant = Mockito.mock(Restaurant.class) ;
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    private Object itemNotFoundException;

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        LocalTime currentTime = LocalTime.parse("12:00:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currentTime);

        restaurant.openingTime = LocalTime.parse("10:30:00");
        restaurant.closingTime = LocalTime.parse("22:00:00");

        Mockito.when(restaurant.isRestaurantOpen()).thenReturn(true) ;

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        LocalTime currentTime = LocalTime.parse("06:00:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currentTime);

        restaurant.openingTime = LocalTime.parse("10:30:00");
        restaurant.closingTime = LocalTime.parse("22:00:00");

        Mockito.when(restaurant.isRestaurantOpen()).thenReturn(false) ;
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void get_order_total_value_should_return_sum_of_prices_if_menu_is_passed() throws itemNotFoundException {
        LocalTime currentTime = LocalTime.parse("12:00:00");
        Mockito.when(restaurant.isRestaurantOpen()).thenReturn(true) ;
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currentTime);
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",10);
        restaurant.addToMenu("Vegetable lasagne", 20);
        List<Item> menulist = restaurant.getMenu();
        assertEquals(30,restaurant.getOrderTotalValue(menulist));

    }

    @Test
    public void get_order_total_value_should_throw_exception_for_empty_cart() throws itemNotFoundException {
        LocalTime currentTime = LocalTime.parse("12:00:00");
        Mockito.when(restaurant.isRestaurantOpen()).thenReturn(true) ;
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currentTime);
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);

        List<Item> menulist = restaurant.getMenu();
        assertEquals(itemNotFoundException.toString(),restaurant.getOrderTotalValue(menulist));
    }
}