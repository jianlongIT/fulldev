package com.example.fulldev.api.v1;

import com.example.fulldev.bo.PageCounter;
import com.example.fulldev.core.LocalUser;
import com.example.fulldev.core.interceptors.ScopeLevel;
import com.example.fulldev.dto.OrderDTO;
import com.example.fulldev.logic.OrderChecker;
import com.example.fulldev.model.Order;
import com.example.fulldev.service.OrderService;
import com.example.fulldev.util.CommonUtil;
import com.example.fulldev.vo.OrderIdVO;
import com.example.fulldev.vo.OrderPureVO;
import com.example.fulldev.vo.OrderSimplifyVO;
import com.example.fulldev.vo.PagingDozer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("order")
@RestController
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Value("${order.max-sku-paytime-limit}")
    private Long payTimeLimit;

    @RequestMapping("")
    @ScopeLevel()
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUser().getId();
        OrderChecker orderChecker = orderService.isOk(uid, orderDTO);

        Long oid = this.orderService.placeOrder(uid, orderDTO, orderChecker);

        return new OrderIdVO(oid);

    }

    @ScopeLevel
    @RequestMapping("status/unpaid")
    public PagingDozer getUnpaid(@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = orderService.getUnpaid(page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach(o -> ((OrderSimplifyVO) o).setPeriod(payTimeLimit));
        return pagingDozer;
    }

    @ScopeLevel
    @RequestMapping("by/status/{status}}")
    public PagingDozer getStatus(
            @PathVariable int status,
            @RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = orderService.getOrderByStatus(status, page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach(o -> ((OrderSimplifyVO) o).setPeriod(payTimeLimit));
        return pagingDozer;

    }

    @ScopeLevel
    @RequestMapping("/detail/{id}}")
    public OrderPureVO getOrderDetail(@PathVariable(name = "Id") Long id) throws Exception {
        Optional<Order> order = orderService.getOrderDetail(id);
/*        OrderPureVO orderPureVO = order.map(o -> new OrderPureVO(o, payTimeLimit)).orElseThrow(() -> {
            throw new NotFoundException(50000);
        });*/
        return null;
    }

}
