<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>

<!-- isAnonymouse(): 로그인 안한 상태(인증도 권한도 없을 때) -->
<sec:authorize access="isAnonymous()">
    <a href="/login">로그인</a>
</sec:authorize>
<!-- isAuthenticated(): 로그인 한 상태 -->
<sec:authorize access="isAuthenticated()">
    <form action="/logout" method="post">
        <sec:authentication property="principal.username"/>님 안녕하세요.
<%--        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">--%>
        <sec:csrfInput/>
        <button type="submit">로그아웃</button>
    </form>
</sec:authorize>

<a href="<c:url value="/board/list"/>">게시판</a>
</body>
</html>