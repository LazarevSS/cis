<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="dialog" title="Система хранения файлов - поиск по файлу " class="ui-widget-content">
    <div class="popupSearchPaging">
        <a style="font-size: 17px" href="${pageContext.request.contextPath}/fileStoreView">Перейти к списку файлов</a>
    </div>
    <span class="popupSearchPaging">
        <input type="text" class="popupSearchText">
        <input type="button" class="popupSearchButton" value="Найти">
    </span>
    <p id="resultSearch" hidden="hidden">Результат: </p>
</div>
</body>
</html>
