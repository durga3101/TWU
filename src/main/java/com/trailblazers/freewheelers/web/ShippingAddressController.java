package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.model.ShippingAddress;
import com.trailblazers.freewheelers.service.AccountService;
import com.trailblazers.freewheelers.service.ItemService;
import com.trailblazers.freewheelers.service.ReserveOrderService;
import com.trailblazers.freewheelers.service.impl.AccountServiceImpl;
import com.trailblazers.freewheelers.service.impl.ItemServiceImpl;
import com.trailblazers.freewheelers.service.impl.ReserveOrderServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shippingAddress")
public class ShippingAddressController {
    ItemService itemService = new ItemServiceImpl();
    AccountService accountService = new AccountServiceImpl();

    @RequestMapping(method = RequestMethod.GET)
    public String get(Model model, HttpServletRequest request) {
        if(request.getSession().getAttribute("itemOnConfirm") == null){
            return "shippingAddress";
        }
        Item item = (Item) request.getSession().getAttribute("itemOnConfirm");
        Item itemOnConfirm = itemService.get(item.getItemId());
        model.addAttribute("totalAmount", itemOnConfirm.getPrice());
        return "shippingAddress";
    }

    @RequestMapping(value = {"/addShippingAddress"},method = RequestMethod.POST)
    public String getShippingAddress(HttpServletRequest request){
        String street1 = request.getParameter("street1");
        String street2 = request.getParameter("street2");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String postcode = request.getParameter("postcode");
        ShippingAddress shippingAddress = new ShippingAddress(street1,street2,city,state,postcode);
        return "Success";
    }
}
