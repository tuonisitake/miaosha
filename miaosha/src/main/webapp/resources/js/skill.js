//存放主要交互逻辑
//js 模块化
//skill.detail.init(params)
var skill = {
    //封装秒杀相关ajax的地址
    URL : {
        now:function () {
            // console.log('${APP_PATH}/skill/time/now');
            return '/miaosha/skill/time/now';
        },
        exposer:function (sId) {
            return '/miaosha/skill/'+sId+'/exposer';
        },
        executor:function (sId,md5) {
            return '/miaosha/skill/'+sId+'/'+md5+'/executor';
        },
        myPrize:function () {
            return '/miaosha/skill/myPrize';
        }
    },
    handleSkillKill:function(sId,node){
        //处理秒杀逻辑,获取秒杀地址，控制显示逻辑，执行秒杀,node节点中放一系列按钮，倒计时，提示信息
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');//按钮
        $.post(skill.URL.exposer(sId),{},function (result) {
            //在回调函数中执行交互流程
            if(result && result['success']){
                var exposer = result['data'];
                //拿到exposer的exposed，看是否开启秒杀
                if(exposer['exposed']){
                    //开启秒杀
                    //获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = skill.URL.executor(sId,md5);
                    console.log("killUrl"+killUrl);
                    //绑定一次点击事件,防止秒杀开始时，客户一直点击按钮，导致服务器端收到
                    //大量相同请求，one代表只发送一次请求，之后的点击事件其实不触发
                    $('#killBtn').one('click',function () {
                        //执行秒杀操作
                        //1.先禁用按钮
                        $(this).addClass('disabled');
                        //2.发送秒杀请求,执行秒杀
                        $.post(killUrl,{},function (result) {
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //3.显示秒杀结果
                                node.html('<label class="label label-success">'+stateInfo+'</label>');
                            }else {
                                console.log("result:"+result);
                            }
                        });
                    });
                    //逻辑结束后，显示node节点
                    node.show();
                }else {
                    //未开启秒杀，PC端时间可能与服务端时间不一致
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新计算计时逻辑
                    skill.countDown(sId,now,start,end);
                }
            }else {
                console.log("result"+result);
            }
        });
    },
    //验证手机号
    validatePhone:function(phone){
        //判断手机号是否为空，手机号长度是否为6位，手机号是否为非数字
        if(phone && phone.length == 6 && !isNaN(phone)){
            return true;
        }else {
            return false;
        }
    },
    //验证手机号模态框
    validatePhoneModal:function(){
        var killPhone = $.cookie("killPhone");
        //验证手机号
        if(!skill.validatePhone(killPhone)){
            //绑定手机号，获取弹出层,控制输出
            var killPhoneModal = $('#killPhoneModal');
            //显示弹出层
            killPhoneModal.modal({
                show:true,//显示弹出层
                backdrop:'static',//禁止位置关闭
                keyboard:false,//禁止键盘事件
            });
            $('#killPhoneBtn').click(function () {
                var inputPhone = $('#killPhoneKey').val();
                console.log("inputPhone="+inputPhone);
                if(skill.validatePhone(inputPhone)){
                    //电话写入cookie,7天有效，仅在skill路径下有效
                    $.cookie('killPhone',inputPhone,{expires:7,path:'/'});
                    //刷新页面
                    window.location.reload();
                }else {
                    $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                }
            });
        }
    },
    countDown:function(sId,nowTime,startTime,endTime){
        var skillBox = $('#skill-box');
        //时间判断
        if(nowTime>endTime){
            //秒杀结束
            skillBox.html('秒杀结束！');
        }else if (nowTime<startTime){
            //秒杀未开始，计时事件绑定,加一秒防止客户端的计时偏移
            var killTime = new Date(startTime+1000);
            //Jquery countdown 插件
            skillBox.countdown(killTime,function (event) {
                //时间的格式
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                skillBox.html(format);
                /*时间完成后回调函数*/
            }).on('finish.countdown',function () {
                //获取秒杀地址，控制执行逻辑，执行秒杀,node节点中放一系列按钮，倒计时，提示信息
                skill.handleSkillKill(sId,skillBox);
            });
        }else{
            //秒杀开始
            skill.handleSkillKill(sId,skillBox);
        }
    },
    //查询我的奖品，往表格中填充数据
    myPrizeTable:function(result){
        //清空table表格
        $('#myPrizeTable tbody').empty();
        //获取返回的listData数据
        var list_data = result['listData'];
        $.each(list_data,function (index,item) {
            //var createTime = new Date();
           var nameTd = $('<td></td>').append(item.skill.name);
           var skillTimeTd = $('<td></td>').append(item.createTime);
           var phoneTd = $('<td></td>').append(item.userPhone);
           $('<tr></tr>')
               .append(nameTd)
               .append(skillTimeTd)
               .append(phoneTd)
               .appendTo('#myPrizeTable tbody');
        });
    },
    //详情页秒杀逻辑
    detail:{
        //详情页初始化
        init:function (params) {
            //手机验证和登录，计时交互
            //规划交互流程
            //在cookie中查找手机号
            skill.validatePhoneModal();
            //已经登录
            //计时交互
            //js访问json的一种方式
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var sId = params['sId'];
            console.log(skill.URL.now());
            $.get(skill.URL.now(),{},function (result) {
                //result必须存在，并且result为success为true
                if(result && result['success']){
                    var nowTime = result['data'];
                    //时间判断
                    skill.countDown(sId,nowTime,startTime,endTime);
                }else {
                    console.log('result'+result);
                }
            });
        }
    }

}
//“我的奖品”点击事件
$('#myPrize a').click(function () {
    //skill.validatePhoneModal();
    $.ajax({
        url:skill.URL.myPrize(),
        type:"POST",
        success:function (result) {
            if(result && result['success']){
                $('#myPrizeModal').modal({
                    backdrop:'static'//禁止位置关闭
                });
                skill.myPrizeTable(result);
            }else {
                alert(result['error']);
            }

        }
    });
});