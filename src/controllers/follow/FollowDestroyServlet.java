package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowDestroyServlet
 */
@WebServlet("/follow/destroy")
public class FollowDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String _token=(String)request.getParameter("_token");
        if(_token !=null && _token.equals(request.getSession().getId())){
            EntityManager em=DBUtil.createEntityMagaer();

            Employee login_employee=(Employee)request.getSession().getAttribute("login_employee");
            Employee follow=em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
            Integer r= Integer.parseInt(request.getParameter("r.id"));
            Follow f=(Follow)em.createNamedQuery("getDestroyFollow",Follow.class)
                    .setParameter("user_id", login_employee)
                    .setParameter("follow_id", follow)
                    .getSingleResult();


            f.setFollow_flag(0);

            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            response.sendRedirect(request.getContextPath()+"/reports/show?id="+follow.getId()+"&r.id="+r);

        }
    }

}
