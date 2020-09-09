<%--
  Created by IntelliJ IDEA.
  User: cpyang
  Date: 2020/9/5
  Time: 2:32 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/ "/>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#btn1').click(function () {
                $.ajax({
                    url: "send/array1.html",
                    data: {
                        array: [5, 8, 12]
                    },
                    type: "post",
                    dataType: "text",
                    success: function (response) {
                        console.log(response)
                    },
                    error: function (response) {
                        console.log(response)
                    }
                })
            })
            $('#btn2').click(function () {
                var data = [5, 8, 12]
                $.ajax({
                    url: "send/array2.json",
                    data: JSON.stringify(data),
                    contentType: "Application/json;charset=utf-8",
                    type: "post",
                    dataType: "text",
                    success: function (response) {
                        console.log(response)
                    },
                    error: function (response) {
                        console.log(response)
                    }
                })
            })
            $('#btn3').click(function () {
                var data = {
                    "stuId": 1,
                    "stuName": "Tom",
                    "address": {
                        "province": "江西",
                        "city": "赣州",
                        "street": "hi",
                        "subjectList": [
                            {
                                "subjectName": "java",
                                "subjectScore": 90,
                            },
                            {
                                "subjectName": "c",
                                "subjectScore": 90
                            }
                        ]
                    },
                    "map":{
                        "k1":"v1",
                        "k2":"v2"
                    }

                }
                $.ajax({
                    url: "send/compose.json",
                    data: JSON.stringify(data),
                    contentType: "Application/json;charset=utf-8",
                    type: "post",
                    dataType: "json",
                    success: function (response) {
                        console.log(response)
                    },
                    error: function (response) {
                        console.log(response)
                    }
                })
            })
        })
    </script>
</head>
<body>
<a href="test/ssm.html">测试ssm整合环境</a>
<br>
<button id="btn1">Send [5,8,12] one</button>
<br>
<button id="btn2">Send [5,8,12] two</button>
<br>
<button id="btn3">Send compose data</button>
</body>
</html>
