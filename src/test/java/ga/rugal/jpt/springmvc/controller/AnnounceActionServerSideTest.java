/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.rugal.jpt.springmvc.controller;

import ga.rugal.ControllerServerSideTestBase;
import ga.rugal.jpt.common.tracker.common.ClientRequestMessageBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Rugal Bernstein
 */
public class AnnounceActionServerSideTest extends ControllerServerSideTestBase
{

    @Autowired
    private AnnounceAction announceAction;

    @Test
    @Ignore
    public void testAnnounce()
    {
        request.setRequestURI("/announce");
        request.setMethod(HttpMethod.POST.name());
        request.setParameter("info_hash", "%92%c345%c0%28%15%e4rr%b1y%17%b7%cbs%0a%ef%9a%fc");
        request.setParameter("peer_id", "654321");
        request.setParameter("port", "6881");
        Class<?>[] parameterTypes = new Class<?>[]
        {
            ClientRequestMessageBean.class, HttpServletRequest.class, HttpServletResponse.class

        };
        ModelAndView mv = null;
        try
        {
            mv = handlerAdapter
                .handle(request, response, new HandlerMethod(announceAction, "announce", parameterTypes));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
