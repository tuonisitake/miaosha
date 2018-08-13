<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>秒杀商品详情</title>
    <!-- 静态包含 -->
    <%@include file="common/head.jsp"%>
</head>
<body>
  <div class="container">
      <div class="panel panel-default text-center">
          <div class="panel-heading">
              <h1>${skill.name}</h1></div>
          <div class="panel-body">
              <h2 class="text-danger">
                  <span class="glyphicon glyphicon-time"></span>
                  <span class="glyphicon" id="skill-box"></span>
              </h2>
              <h5 id="myPrize" class="text-right"><a href="#">我的奖品</a></h5>
          </div>
      </div>
  </div>

  <div id="killPhoneModal" class="modal fade">
      <div class="modal-dialog">
          <div class="modal-content">
              <div class="modal-header">
                  <h3 class="modal-title text-center">
                      <span class="glyphicon glyphicon-phone"></span>秒杀电话:
                  </h3>
              </div>


          <div class="modal-body">
              <div class="row">
                  <div class="col-xs-8 col-xs-offset-2">
                      <input type="text" name="killPhone" id="killPhoneKey" placeholder="填写手机号码" class="form-control">
                  </div>
              </div>
          </div>

          <div class="modal-footer">
              <span id="killPhoneMessage" class="glyphicon"></span>
              <button type="button" id="killPhoneBtn" class="btn btn-success">
                  <span class="glyphicon glyphicon-phone"></span>
                  提交
              </button>
          </div>
          </div>
      </div>
  </div>
  <!-- 查看我的奖品模态框 -->
  <div id="myPrizeModal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title">我的奖品</h4>
              </div>
              <div class="modal-body">
                  <table id="myPrizeTable" class="table table-hover">
                      <thead>
                        <tr>
                            <td>商品名称</td>
                            <td>秒杀时间</td>
                            <td>手机号</td>
                        </tr>
                      </thead>
                      <tbody>

                      </tbody>
                  </table>
              </div>
          </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
</body>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- jQuery Cookie 插件 与 倒计时插件 -->
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- 开始编写交互逻辑 -->
<script src="${APP_PATH}/resources/js/skill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        //使用EL表达式传入参数
        skill.detail.init({
            sId:${skill.sId},
            //拿到系统的毫秒时间
            startTime:${skill.startTime.time},
            endTime:${skill.endTime.time}
        });
    });
</script>
</html>
