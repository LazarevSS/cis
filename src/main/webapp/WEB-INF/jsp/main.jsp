<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <title>Модель</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/rosneft.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/treestyle.css">
    <style>
        div.container {     width: 100%;     border: 1px solid gray; }
    </style>
</head>
<body TEXT="#000000" BGCOLOR="#FFFFFF">
<div class="page_top">
    <div class="page_top_logo"><A href="${pageContext.request.contextPath}"><IMG alt="Rosneft logo"
                                                                  src="${pageContext.request.contextPath}/resources/img/rnlogorus.png" ></A></div>
    <form style="direction: ltr; float: right" action="hr.PM_PUB_PAGE.search" method="POST">
        <td valign=center><input type=text name=sstring value="">
            <input type="submit" value="Поиск" />
    </form>
</div>
</body>
</html>
