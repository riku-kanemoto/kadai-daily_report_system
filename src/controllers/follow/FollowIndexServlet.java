package controllers.follow;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FollowIndexServlet
 */
@WebServlet("/follow/index")
public class FollowIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em=DBUtil.createEntityMagaer();
        Employee login_employee=em.find(Employee.class,Integer.parseInt(request.getParameter("id")));

        int page;
        try{
            page=Integer.parseInt(request.getParameter("page"));
        }catch(Exception e){
            page=1;
        }
        List<Employee> fe=em.createNamedQuery("getAllFollowEmployees",Employee.class)
                .setParameter("user_id", login_employee)
                .getResultList();

        List<Report> follow_report=em.createNamedQuery("getFollowAllReports",Report.class)
                .setParameter("employee",fe)
                .setFirstResult(15*(page-1))
                .setMaxResults(15)
                .getResultList();
        long follow_reports_count = (long)em.createNamedQuery("getFollowReportsCount", Long.class)
                .setParameter("employee", fe)
                .getSingleResult();

        em.close();
        request.setAttribute("follow_report",follow_report);
        request.setAttribute("follow_reports_count", follow_reports_count);
        request.setAttribute("page", page);
        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/follows/index.jsp");
        rd.forward(request, response);


    }

}
