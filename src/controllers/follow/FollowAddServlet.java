package controllers.follow;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

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
 * Servlet implementation class FollowAddServlet
 */
@WebServlet("/follow/add")
public class FollowAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowAddServlet() {
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
            Integer r= Integer.parseInt(request.getParameter("r.id"));
            Employee follow=em.find(Employee.class,Integer.parseInt(request.getParameter("id")));
            Employee login_employee=(Employee)request.getSession().getAttribute("login_employee");
            List<Follow> fl=em.createNamedQuery("getDestroyFollow",Follow.class)
                    .setParameter("user_id", login_employee)
                    .setParameter("follow_id", follow)
                    .getResultList();
           Integer p=Integer.parseInt(request.getParameter("p"));

            if(fl.size()>0){
                Follow f=(Follow)em.createNamedQuery("getDestroyFollow",Follow.class)
                        .setParameter("user_id", login_employee)
                        .setParameter("follow_id", follow)
                        .getSingleResult();
                f.setFollow_flag(1);

                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();


            }else{
                Follow f=new Follow();
                f.setUser_id(login_employee);
                f.setFollow_id(follow);
                Timestamp currentTime=new Timestamp(System.currentTimeMillis());
                f.setFollow_at(currentTime);
                f.setFollow_flag(1);
                em.getTransaction().begin();
                em.persist(f);
                em.getTransaction().commit();
                em.close();

            }
            response.sendRedirect(request.getContextPath()+"/reports/show?id="+follow.getId()+"&r.id="+r+"&p="+p);

        }
    }

}
