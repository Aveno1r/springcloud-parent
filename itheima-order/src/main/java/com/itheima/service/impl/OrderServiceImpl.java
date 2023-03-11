package com.itheima.service.impl;

import com.itheima.dao.OrderDao;
import com.itheima.order.pojo.OrderInfo;
import com.itheima.service.OrderService;

import com.itheima.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 根据ID查询订单信息
     */
    @Override
    public OrderInfo findById(Long id) {
        //1.查询订单
        OrderInfo orderInfo = orderDao.selectById(id);
        //2.根据订单查询用户信息->需要调用  【item-user】  服务
        String url = "http://localhost:18081/user/" + orderInfo.getUserId();
        User user = restTemplate.getForObject(url, User.class);
        //3.封装user到订单中
        orderInfo.setUser(user);
        //4.返回订单信息
        return orderInfo;
    }
}