/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

$(function(){
	$(".address").hover(function(){
		$(this).addClass("address-hover");	
	},function(){
		$(this).removeClass("address-hover");	
	});
})

$(function(){
	$(".addr-item .name").click(function(){
		 $(this).toggleClass("selected").siblings().removeClass("selected");	
	});
	$(".payType li").click(function(){
		 $(this).toggleClass("selected").siblings().removeClass("selected");	
	});
})
