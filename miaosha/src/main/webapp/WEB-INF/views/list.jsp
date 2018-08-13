<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tag.jsp"%>
<html>
<head>
    <title>秒杀商品列表</title>
    <!-- 静态包含 -->
    <%@include file="common/head.jsp"%>
</head>
<body>
   <!-- 页面显示部分 -->
   <div class="container">
       <div class="panel panel-default">
           <div class="panel-heading text-center">
                 <h2>秒杀列表</h2>
                 <h4 class="text-right">当前用户:${sessionScope.user.username}</h4>
           </div>
           <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                      <tr>
                          <th>名称</th>
                          <th>库存</th>
                          <th>开始时间</th>
                          <th>结束时间</th>
                          <th>创建时间</th>
                          <th>详情</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="s" items="${list}">
                        <tr>
                            <td>${s.name}</td>
                            <td>${s.number}</td>
                            <td>
                                <fmt:formatDate value="${s.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${s.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${s.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>
                                <a class="btn btn-info" href="${APP_PATH}/skill/${s.sId}/detail" target="_blank">详情</a>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>

                </table>
           </div>
       </div>
   </div>
   <!-- 登录模态框 -->
   <div id="loginModal" class="modal fade" tabindex="-1" role="dialog">
       <div class="modal-dialog" role="document">
           <div class="modal-content">
               <div class="modal-header">
                   <h4 class="modal-title">用户登录</h4>
               </div>
               <div class="modal-body">
                   <!-- 用户登录表单 -->
                   <form id="loginForm">
                       <div class="form-group">
                           <label for="username">用户名</label>
                           <input name="username" type="text" class="form-control" id="username" placeholder="用户名">
                       </div>
                       <div class="form-group">
                           <label for="password">密码</label>
                           <input name="password" type="password" class="form-control" id="password" placeholder="密码">
                       </div>
                       <button id="loginBtn" type="button" class="btn btn-default">登录</button>
                       <span id="errorMessage" class="glyphicon"></span>
                       <%--<span class="glyphicon glyphicon-remove"></span>--%>
                   </form>
               </div>
           </div><!-- /.modal-content -->
       </div><!-- /.modal-dialog -->
   </div><!-- /.modal -->


</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function () {
        if('${sessionScope.user.username}' == ''){
            var loginModal = $('#loginModal');
            loginModal.modal({
                show:true,//显示弹出层
                backdrop:'static',//禁止位置关闭
                keyboard:false,//禁止键盘事件
            });
        }
    });
    $('#loginBtn').click(function () {
        //ev.stopPropagation();
        //ev.preventDefault();
        $.ajax({
            url:'${APP_PATH}/skill/login',
            type:'POST',
            data:$('#loginForm').serialize(),
            success:function (result) {
                if(result['success'] == true){
                    //$('#loginModal').modal('hide');
                    window.location.reload();
                }else{
                    //alert(result['error']);
                    $('#errorMessage').hide().
                              html('<label class="label label-danger">'+result["error"]+'</label>').show(300);
                }
            }
        });
    });
    // function show_validate_msg(ele,status,msg) {
    //     //清除当前元素的校验状态
    //     $(ele).parent().removeClass('has-success has-error');
    //     $(ele).next('span').text('');
    //     if('true'==status){
    //         $(ele).parent().addClass('has-success');
    //         $(ele).next('span').text('');
    //     }else if ('false'==status) {
    //         $(ele).parent().addClass('has-error');
    //         $(ele).next('span').text(msg);
    //     }
    // }
</script>
</html>
