<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>フォロー日報一覧</h2>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_action">操作</th>
                </tr>
                <c:forEach var="follow" items="${follow_report}" varStatus="status">
                    <tr class="row${status.count%2}">
                        <td class="report_name">
                            <c:out value="${follow.employee.name}"/>
                        </td>
                        <td class="report_date">
                            <fmt:formatDate value="${follow.report_date}" pattern="yyy-MM-dd"/>
                        </td>
                        <td class="report_title">${follow.title}</td>
                        <td class="report_action">
                            <a href="<c:url value='/reports/show?r.id=${follow.id}&id=${follow.employee.id}&p=1'/>">
                                詳細を見る
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            (全${follow_reports_count}件)<br/>
            <c:forEach var="i" begin="1" end="${((follow_reports_count-1)/15)+1}" step="1">
                <c:choose>
                    <c:when test="${i==page}">
                        <c:out value="${i}"/>&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="/follow/index?page=${i}"/>">
                            <c:out value="${i}"/>&nbsp;
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/'/>">ホームに戻る</a></p>
    </c:param>
</c:import>