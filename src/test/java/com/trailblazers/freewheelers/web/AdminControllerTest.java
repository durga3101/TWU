package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.OrderStatus;
import com.trailblazers.freewheelers.model.ReserveOrder;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.ItemService;
import com.trailblazers.freewheelers.service.ReserveOrderService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.valueOf;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    private ReserveOrderService reserveOrderService;
    private ItemService itemService;
    private AccountService accountService;
    private AdminController adminController;
    private Model model;

    @Before
    public void setUp() throws Exception {
        reserveOrderService = mock(ReserveOrderService.class);
        itemService = mock(ItemService.class);
        accountService = mock(AccountService.class);
        model = mock(Model.class);
        adminController = new AdminController(reserveOrderService, itemService, accountService);
    }

    @Test
    public void getShouldInvokeAddAttributeMethodOfModel() throws Exception {
        adminController.get(model);
        verify(model).addAttribute((String) any(), any());
    }

    @Test
    public void getShouldInvokeAllDependencies() throws Exception {
        List<ReserveOrder> reserveOrders = new ArrayList<>();
        reserveOrders.add(mock(ReserveOrder.class));
        reserveOrders.add(mock(ReserveOrder.class));
        reserveOrders.add(mock(ReserveOrder.class));
        when(reserveOrderService.getAllOrdersByAccount()).thenReturn(reserveOrders);

        adminController.get(model);

        verify(reserveOrderService).getAllOrdersByAccount();
        verify(accountService, atLeastOnce()).get((Long) any());
        verify(itemService, atLeastOnce()).get((Long) any());
    }

    @Test
    public void updateOrderShouldCallUpdateOrderDetails() {
        String note = "note";
        Long orderId = 1l;

        adminController.updateOrder(model, OrderStatus.NEW.toString(), orderId.toString(), note);

        verify(reserveOrderService).updateOrderDetails(valueOf(orderId), OrderStatus.NEW, note);

    }

}