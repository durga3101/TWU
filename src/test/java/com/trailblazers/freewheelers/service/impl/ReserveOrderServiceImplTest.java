package com.trailblazers.freewheelers.service.impl;

import com.trailblazers.freewheelers.mappers.OrderMapper;
import com.trailblazers.freewheelers.mappers.ReserveOrderMapper;
import com.trailblazers.freewheelers.model.ReserveOrder;
import com.trailblazers.freewheelers.web.Order;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;


import static org.mockito.Mockito.*;

public class ReserveOrderServiceImplTest {

    private ReserveOrder reserveOrder;
    private SqlSession sqlSession;
    private ReserveOrderMapper reserveOrderMapper;

    @Before
    public void setUp() throws Exception {
        reserveOrder = mock(ReserveOrder.class);
        sqlSession = mock(SqlSession.class);
        reserveOrderMapper = mock(ReserveOrderMapper.class);
        when(sqlSession.getMapper(ReserveOrderMapper.class)).thenReturn(reserveOrderMapper);
    }

    @Test
    public void shouldInsertOrderIntoDatabaseWhenOrderIsNotThere(){
        when(reserveOrder.getOrder_id()).thenReturn(null);

        ReserveOrderServiceImpl reserveOrderService = new ReserveOrderServiceImpl(sqlSession);
        reserveOrderService.save(reserveOrder);

        verify(reserveOrderMapper).insert(reserveOrder);

    }

    @Test
    public void shouldUpdateOrderIntoDatabaseWhenOrderExists(){
        when(reserveOrder.getOrder_id()).thenReturn(anyLong());

        ReserveOrderServiceImpl reserveOrderService = new ReserveOrderServiceImpl(sqlSession);
        reserveOrderService.save(reserveOrder);

        verify(reserveOrderMapper).update(reserveOrder);

    }

//    @Test
//    public void shouldInvokeOrderMapperWhenSavingAnOrder() throws Exception {
//        OrderMapper orderMapper = mock(OrderMapper.class);
//        Order order = mock(Order.class);
//        when(sqlSession.getMapper(OrderMapper.class)).thenReturn(orderMapper);
//
//        ReserveOrderServiceImpl reserveOrderService = new ReserveOrderServiceImpl(sqlSession);
//        reserveOrderService.saveOrder(order);
//
//        verify(orderMapper).insert(order);
//
//    }
//
//    @Test
//    public void shouldInvokeOrderMapperWhenGettingAnOrder() throws Exception {
//        SqlSession sqlSession = mock(SqlSession.class);
//        OrderMapper orderMapper = mock(OrderMapper.class);
//        when(sqlSession.getMapper(OrderMapper.class)).thenReturn(orderMapper);
//
//        ReserveOrderServiceImpl reserveOrderService = new ReserveOrderServiceImpl(sqlSession);
//
//        reserveOrderService.getAllOrders(anyLong());
//
//        verify(orderMapper).getAllOrdersByAccountId(anyLong());
//    }
}