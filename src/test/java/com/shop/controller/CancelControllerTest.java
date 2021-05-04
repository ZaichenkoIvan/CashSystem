package com.shop.controller;

import com.shop.config.LoginSuccessHandler;
import com.shop.model.domain.Check;
import com.shop.model.service.CheckService;
import com.shop.model.service.ReportService;
import com.shop.model.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@WithMockUser(username = "scashier@gmail.com", authorities = "senior_cashier")
@WebMvcTest(value = CancelController.class)
public class CancelControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LoginSuccessHandler handler;

    @MockBean
    private CheckService checkService;

    @MockBean
    private ReportService reportService;


    @Test
    public void mainShouldReturnMainPage() throws Exception {
        mvc.perform(get("/cancel"))
                .andExpect(view().name("/cancel"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldShowAllOrders() throws Exception {
        when(checkService.findById(anyLong())).thenReturn(new Check());
        when(checkService.findOrderByCheck(anyLong())).thenReturn(Collections.emptyList());

        mvc.perform(get("/cancel"))
                .andExpect(view().name("/cancel"))
                .andExpect(status().is(200));
    }
}
