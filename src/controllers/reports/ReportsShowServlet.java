package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityMagaer();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("r.id")));
        int p=Integer.parseInt(request.getParameter("p"));
        Employee login_employee=(Employee)request.getSession().getAttribute("login_employee");
        Employee follow=em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
        try{
            Follow f=(Follow)em.createNamedQuery("getDestroyFollow",Follow.class)
                    .setParameter("user_id", login_employee)
                    .setParameter("follow_id", follow)
                    .getSingleResult();
            em.close();
            request.setAttribute("p", p);
            request.setAttribute("f", f.getFollow_flag());
            request.setAttribute("report", r);
            request.setAttribute("_token", request.getSession().getId());
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
            rd.forward(request, response);

        }catch(NoResultException e){
            em.close();
            request.setAttribute("p", p);
            request.setAttribute("f", 0);
            request.setAttribute("report", r);
            request.setAttribute("_token", request.getSession().getId());
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
            rd.forward(request, response);

        }


        }

}
