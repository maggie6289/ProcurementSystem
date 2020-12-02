<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>后台管理系统</title>
<meta name="author" content="DeathGhost" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/assets/css/style.css">
<!--[if lt IE 9]>
<script src="js/html5.js"></script>
<![endif]-->
<script src="${pageContext.request.contextPath}/assets/js/jquery.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script>
	(function($) {
		$(window).load(
				function() {

					$("a[rel='load-content']").click(
							function(e) {
								e.preventDefault();
								var url = $(this).attr("href");
								$.get(url, function(data) {
									$(".content .mCSB_container").append(data); //load new content inside .mCSB_container
									//scroll-to appended content 
									$(".content").mCustomScrollbar("scrollTo",
											"h2:last");
								});
							});

					$(".content").delegate(
							"a[href='top']",
							"click",
							function(e) {
								e.preventDefault();
								$(".content").mCustomScrollbar("scrollTo",
										$(this).attr("href"));
							});

				});
	})(jQuery);
</script>
</head>
<body>
	<!--header-->
	<header>
		<h1>
			<img
				src="${pageContext.request.contextPath}/assets/images/admin_logo.png" />
		</h1>
		<ul class="rt_nav">
			<li><a href="tzzup" class="set_icon">账号设置</a></li>
			
			<li><a href="tzlogin" class="quit_icon">安全退出</a></li>
		</ul>
	</header>
	<!--aside nav-->
	<!--aside nav-->
		<aside class="lt_aside_nav content mCustomScrollbar">

		<ul>
			<li>
				<dl>
					<dt>用户管理</dt>
					<!--当前链接则添加class:active-->
					<dd>
						<a href="sy">用户列表</a>
					</dd>
					<dd>
						<a href="tjyh">添加用户</a>
					</dd>
				</dl>
			</li>
					
			<li>
				<dl>
					<dt>采购申请</dt>					
					<dd>
						<a href="tzrk">新建物料采购表</a>
					</dd>					
					<dd>
						<a href="tzrksh" >物料采购表审核</a>
					</dd>
					<dd>
						<a href="lookStatus" class="active">查看审核状态</a>
					</dd>
				</dl>
			</li>
		</ul>
	</aside>
<section class="rt_wrap content mCustomScrollbar">
<form action="updateHw" method="post">
 <div class="rt_content">
<input type="hidden" name="id" value="${list.id}">
<input type="hidden" name="isFrame" value="${list.isFrame}">
      <div class="page_title">
       <h2 class="fl">请输入货物信息</h2>
      </div>
      <ul class="ulColumn2">
      <li>
        <span class="item_name" style="width:120px;" diabled="diabled"  name="isFrame">是否框架采购：</span>
        <c:if test="${list.isFrame == 0}"> 
								是
								</c:if>
								<c:if test="${list.isFrame == 1}"> 
								否
								</c:if>
		</li>
       <li>
        <span class="item_name" style="width:120px;">询问价格：</span>
        <input type="text" name="askMoney" value="${list.askMoney }" class="textbox textbox_225"  placeholder="货物名称..."/>
       </li>
       
       <li>
        <span class="item_name" style="width:120px;">货物名称：</span>
        <input type="text" name="name" value="${list.name }" class="textbox textbox_225"  placeholder="货物名称..."/>
       
       </li>
       <li>
        <span class="item_name" style="width:120px;">数量：</span>
        <input type="text" name="number" value="${list.number }" class="textbox textbox_225"  placeholder="数量..."/>
       </li>
       <li>
        <span class="item_name" style="width:120px;" name="sex">价格：</span>
       <input type="text" name="money"  value="${list.money }" class="textbox textbox_225"  placeholder="价格..."/>
       </li>
       <li>
        <span class="item_name" style="width:120px;">供应商：</span>
        <input type="text"  name="gy" value="${list.gy }" class="textbox textbox_225" placeholder="供应商..."/>
       </li>
       <li>
        <span class="item_name" style="width:120px;">联系电话：</span>
        <input type="tel" name="phone" value="${list.phone }" class="textbox textbox_225"  placeholder="联系电话..."/>
       </li>
       
       <li>
        <span class="item_name" style="width:120px;"></span>
        <input type="submit" class="link_btn" value="提交"/>
       </li>
      </ul>
 </div>
 </form>
</section>

</body>
</html>
