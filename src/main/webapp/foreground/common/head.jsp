<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
	.blog-title {
		font-size: 60px;
		font-weight: bold;
		text-transform: uppercase;
		background-image: linear-gradient(90deg,
				#32CD32,
				#00FF7F,
				#00FFFF,
				#1E90FF,
				#ADFF2F
			);
		background-size: 300% 300%;
		-webkit-background-clip: text;
		color: transparent;
		animation: gradientMove 3s infinite linear;
		text-shadow: 0px 0px 20px rgba(50, 205, 50, 0.8);
		transition: transform 0.3s ease-in-out;
	}

	.blog-title:hover {
		transform: scale(1.1);
	}

	@keyframes gradientMove {
		0% {
			background-position: 0% 50%;
		}

		50% {
			background-position: 100% 50%;
		}

		100% {
			background-position: 0% 50%;
		}
	}
</style>
<div class="row">
	<div class="col-md-8" style="height:85px">
		<h1><strong>arth的个人博客</strong></h1>
	</div>
	<div class="col-md-4">
		<iframe width="400" scrolling="no" height="85" frameborder="0" allowtransparency="true"
			src="https://i.tianqi.com?c=code&id=48&icon=5&py=nanshanqu&num=3&site=12&lang=cn"></iframe>
	</div>
</div>