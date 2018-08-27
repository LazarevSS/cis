<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${isModel.name}</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/rosneft.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/treestyle.css">
</head>


<body TEXT="#000000" BGCOLOR="#FFFFFF">
<div class="page_top">
    <div class="page_top_logo">
        <A href="${pageContext.request.contextPath}">
            <IMG alt="Rosneft logo" src="${pageContext.request.contextPath}/resources/img/rnlogorus.png">
        </A>
    </div>
    <p style="text-align: right; padding: 30px"><a href="/" title="dthyenm">Вернуться на главную страницу</a></p>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9">
            <div style="padding: 10px">
                <p class="infoitem">Система: ${isModel.name}</p>
                <p class="infoitem">Сокращенное наименование: ${isModel.isOwner}</p>
            </div>
            <div style="padding: 10px">
                <p class="infoitem">Тип: ${isModel.isType}</p>
                <p class="infoitem">Владелец: ${isModel.isOwner}</p>
            </div>
            <table wigth="700px">
                <tr>
                    <td>
                        <div class="d3chartarea_main">
                            <script src="${pageContext.request.contextPath}/resources/js/d3.min.js"></script>
                            <script src="${pageContext.request.contextPath}/resources/js/d3.tip.v0.6.3.js"></script>
                            <script src="${pageContext.request.contextPath}/resources/js/d3bubblechart.js"></script>

                            <script>
                                var chartWidth = window.screen.width * 8 / 12;
                                chartWidth = (chartWidth > 700) ? 700 : chartWidth;
                                drawBubbleChart('getGraph?ISNAME=${param.ISNAME}', chartWidth);
                            </script>
                        </div>
                    </td>
                </tr>
            </table>

            <div class="infobox">
                <hr>
            </div>
            <h1>Информационные ресурсы системы и их связи</h1>
            <table id="data_main" class="table table-striped table-bordered" border="1">
                <thead>
                <tr>
                    <th colspan="2">Источник</th>
                    <th rowspan="2">Тип связи</th>
                    <th colspan="2">Приемник</th>
                </tr>
                <tr>
                    <th align="left">Наименоваие ИС</th>
                    <th align="left">Наименоварие информационного ресурса</th>
                    <th align="left">Наименоварие информационного ресурса</th>
                    <th align="left">Наименоваие ИС</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="commonModel" items="${table}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/is/?ISNAME=${commonModel.isName}"
                                   title="${commonModel.isName}">${commonModel.isName}</a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/ir/?IRNAME=${commonModel.name}"
                                   title="${commonModel.name}">${commonModel.name}</a>
                            </td>
                            <td>КСиП</td>
                            <td>
                                <%--<a href="${pageContext.request.contextPath}/ir/?IRNAME=${joinModel.irName}"
                                   title="${joinModel.irName}">${joinModel.irName}</a>--%>
                            </td>
                            <td>
                                <%--<a href="${pageContext.request.contextPath}/is/?ISNAME=${joinModel.isName}"
                                   title="${joinModel.isName}">${joinModel.isName}</a>--%>
                            </td>
                        </tr>
                </c:forEach>
                </tbody>
            </table>
            <input id="addDialog" type="button" onclick="openDialog()" value="Добавить"/>
            <input id="download" type="button" onclick="location.href='download?isName=${isModel.name}'"
                   value="Выгрузить"/>
        </div>
        <div class="col-sm-3">
            <h3>Название ИР</h3>
            <table class="table rn_sm_table" width="95%">
                <thead>
                <tr>
                    <th align="left">Название</th>
                    <th align="left">Код</th>
                </tr>
                <tbody>
                <c:forEach var="irModels" items="${irModels}">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/ir/?IRNAME=${irModels.irName}"
                               title="${irModels.name}">${irModels.name}</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/ir/?IRID=${irModels.irName}"
                               title="${irModels.irCode}">${irModels.irCode}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip({animation: true, delay: {show: 100, hide: 100}, html: true});
        $('#data_main').dataTable({
            "iDisplayLength": 10
        });
    });
</script>
</body>

<body>
<div id="dialog">
    <form method="POST" action="/uploadFile" enctype="multipart/form-data">
            
        <table>
                    
            <tr>
                            
                <td><label path="file">Select a file to upload</label></td>
                            
                <td><input type="file" name="file"/></td>
                        
            </tr>
                    
            <tr>
                            
                <td><input type="submit" value="Submit"/></td>
                        
            </tr>
                
        </table>
    </form>
</div>
</body>

</html>


<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
    $('#dialog').dialog({autoOpen: false});

    function openDialog() {
        $("#dialog").dialog(
            {
                height: 330,
                width: 400,
                autoOpen: true
            }
        );
    }

    function download() {
        var ajaxUrl = "${pageContext.request.contextPath}/download";
        var name = "${isModel.name}";
        $.ajax({
            type: 'GET',
            url: ajaxUrl,
            data: ({
                fileName: name
            }),
            success: function () {
                alert("Success")
            },
            error: function (xhr, str) {
                alert('Возникла ошибка: ' + xhr.responseCode);
            }
        });

    }
</script>




