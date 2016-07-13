package com.trailblazers.freewheelers.web;

import com.trailblazers.freewheelers.model.Item;
import com.trailblazers.freewheelers.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private static final String ITEM_FOR_RESERVE = "itemForReserve";
    private static final String ITEM_ON_CONFIRM = "itemOnConfirm";
    private ItemService itemService;

    @Autowired
    public CartController(ItemService itemService) {
        this.itemService = itemService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String get(HttpServletRequest request, Model model, Principal principal) {

        if (isPrincipalNull(principal)) return "redirect:/login";

        Item item = (Item) request.getSession().getAttribute(ITEM_FOR_RESERVE);
        setModel(model, item);

        setItemAttribute(request, null, ITEM_FOR_RESERVE);
        setItemAttribute(request, item, ITEM_ON_CONFIRM);
        return "cart";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(HttpServletRequest request, Model model, Principal principal, @ModelAttribute Item item) {
        if (isPrincipalNull(principal)) {
            setItemAttribute(request, item, ITEM_FOR_RESERVE);
            return "redirect:/login";
        }

        item = getItemFromSession(request, item);
        setModel(model, item);
        setItemAttribute(request, item, ITEM_ON_CONFIRM);

        return "cart";
    }

    private Item getItemFromSession(HttpServletRequest request, @ModelAttribute Item item) {
        if (item == null) {
            item = (Item) request.getSession().getAttribute(ITEM_FOR_RESERVE);
        }
        return item;
    }

    private boolean isPrincipalNull(Principal principal) {
        return principal == null;
    }

    private void setModel(Model model, @ModelAttribute Item item) {
        Item itemToReserve = itemService.get(item.getItemId());
        model.addAttribute("item", itemToReserve);
    }

    private void setItemAttribute(HttpServletRequest request, @ModelAttribute Item item, String itemAttribute) {
        request.getSession().setAttribute(itemAttribute, item);
    }
}
