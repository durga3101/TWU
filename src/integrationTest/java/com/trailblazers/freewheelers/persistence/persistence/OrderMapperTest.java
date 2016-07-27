package com.trailblazers.freewheelers.persistence.persistence;

import com.trailblazers.freewheelers.mappers.OrderMapper;
import com.trailblazers.freewheelers.model.OrderStatus;
import com.trailblazers.freewheelers.web.Order;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OrderMapperTest extends MapperTestBase {

    private OrderMapper orderMapper;
    private Order firstOrder;
    private Order secondOrder;
    private Long accountId = Long.valueOf(2);
    private Integer firstOrderId;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        firstOrder = new Order(accountId, new Date(), OrderStatus.NEW);
        secondOrder = new Order(accountId, new Date(), OrderStatus.IN_PROGRESS);
        orderMapper = getSqlSession().getMapper(OrderMapper.class);


        orderMapper.insert(firstOrder);
        orderMapper.insert(secondOrder);
    }

    @Test
    public void shouldContainAOrderIdAfterInsertedIntoDataBase(){
        assertNotNull(firstOrder.getOrder_id());
        assertNotNull(secondOrder.getOrder_id());
    }

//    @Test
//    public void shouldGetOrderFromId() throws Exception {
//       Order fetchedFromDB = orderMapper.getOrderByOrderId(firstOrder.getOrder_id());
//
//
//        assertEquals(firstOrder.getStatus(), fetchedFromDB.getStatus());
//        assertEquals(firstOrder.getAccount_id(), fetchedFromDB.getAccount_id());
//        assertEquals(firstOrder.getReservation_timestamp(), fetchedFromDB.getReservation_timestamp());
//        assertNotNull(fetchedFromDB.getOrder_id());
//        assertNull(firstOrder.getOrder_id());
//    }

    @Test
    public void shouldInsertAnOrderAndGetAListOfOrders() throws Exception {
        List<Order> fetchedFromDB = orderMapper.getAllOrdersByAccountId(accountId);

        assertEquals(fetchedFromDB.size(), 2);
        assertEquals(fetchedFromDB.get(0).getStatus(), OrderStatus.NEW);
        assertEquals(fetchedFromDB.get(1).getStatus(), OrderStatus.IN_PROGRESS);
    }

}
