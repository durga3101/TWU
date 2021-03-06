package com.trailblazers.freewheelers.service.impl;

import com.trailblazers.freewheelers.mappers.MyBatisUtil;
import com.trailblazers.freewheelers.mappers.OrderMapper;
import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.OrderStatus;
import com.trailblazers.freewheelers.service.OrderService;
import com.trailblazers.freewheelers.model.Order;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{


    private final SqlSession sqlSession;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(){
        this(MyBatisUtil.getSqlSessionFactory().openSession());
    }

    public OrderServiceImpl(SqlSession sqlSession){
        this.sqlSession = sqlSession;
        orderMapper = sqlSession.getMapper(OrderMapper.class);
    }

    public Order createOrder(Account account) {
        Order newOrder = new Order(account.getAccount_id(), new Date(), OrderStatus.NEW);
        orderMapper.insert(newOrder);
        sqlSession.commit();
        return newOrder;
    }

    @Override
    public List<Order> getOrdersByAccountId(Long account_id) {
        return orderMapper.getAllOrdersByAccountId(account_id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }
}
