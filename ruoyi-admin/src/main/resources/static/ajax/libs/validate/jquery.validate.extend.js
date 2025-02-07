/*this is basic form validation using for validation person's basic information author:Clara Guo data:2017/07/20*/
$(document).ready(function(){
	$.validator.setDefaults({       
		submitHandler: function(form) {    
			form.submit();    
		}       
	});
	// 非法字元驗證
	jQuery.validator.addMethod("specialSign",function(value,element) {
		var char = /^[^<>"'|\\]+$/;
		return this.optional(element) || (char.test(value));
	},"不能包含非法字元：< > \" ' \\\ |");
	// 手機號碼驗證身份證正則合併：(^\d{15}$)|(^\d{17}([0-9]|X)$)
	jQuery.validator.addMethod("isPhone",function(value,element) {
		var length = value.length;
		var phone = /^09\d{8}$/;
		return this.optional(element)||(length == 10 && phone.test(value));
	},"請填寫正確的10位手機號碼");
	// 電話號碼驗證
	jQuery.validator.addMethod("isTel",function(value,element) {
		var tel = /^(0\d{2,3}-)?\d{7,8}$/g;//區號3,4位,號碼7,8位
		return this.optional(element) || (tel.test(value));
	},"請填寫正確的座機號碼");
	// 姓名校驗
	jQuery.validator.addMethod("isName",function(value,element) {
		var name = /^[\u4e00-\u9fa5]{2,6}$/;
		return this.optional(element) || (name.test(value));
	},"姓名只能用漢字,長度2-4位");
	// 校驗使用者名稱
	jQuery.validator.addMethod("isUserName",function(value,element) {
		var userName = /^[a-zA-Z0-9]{2,13}$/;
		return this.optional(element) || (userName).test(value);
	},'請輸入數字或者字母,不包含特殊字元');
	// 校驗身份證
	jQuery.validator.addMethod("isIdentity",function(value,element) {
		var id = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X))$/;
		return this.optional(element) || (id.test(value));
	},"請輸入正確的15或18位身份證號,末尾若為X請大寫");
	// 校驗二代身份證
	jQuery.validator.addMethod("isIdentity18",function(value,element) {
		var id = /^(^\d{17}(\d|X))$/;
		return this.optional(element) || (id.test(value));
	},"請輸入正確的18位身份證號，末尾若為X請大寫");
	// 校驗出生日期
	jQuery.validator.addMethod("isBirth",function(value,element) {
		var birth = /^(19|20)\d{2}-(1[0-2]|0?[1-9])-(0?[1-9]|[1-2][0-9]|3[0-1])$/;
		return this.optional(element) || (birth).test(value);
	},"出生日期格式範例2000-01-01");
	// 校驗IP位址
	jQuery.validator.addMethod("isIp",function(value,element) {
		var ip = /^(?:(?:2[0-4][0-9]\.)|(?:25[0-5]\.)|(?:1[0-9][0-9]\.)|(?:[1-9][0-9]\.)|(?:[0-9]\.)){3}(?:(?:2[0-4][0-9])|(?:25[0-5])|(?:1[0-9][0-9])|(?:[1-9][0-9])|(?:[0-9]))$/;
		return this.optional(element) || (ip).test(value);
	},"IP位址格式範例127.0.0.1");
	jQuery.validator.addMethod("notEqual", function(value, element, param) {
        return value != param;
    }, $.validator.format("輸入值不允許為{0}"));
	jQuery.validator.addMethod("gt", function(value, element, param) {
        return value > param;
    }, $.validator.format("輸入值必須大於{0}"));
	// 校驗新舊密碼是否相同
	jQuery.validator.addMethod("isdiff",function(){
		var p1=$("#pwdOld").val();
		var p2=$("#pwdNew").val();
		if(p1==p2){
			return false;
		}else{
			 return true;
		}
		});
	// 校驗新密碼和確認密碼是否相同
	jQuery.validator.addMethod("issame",function(){
		var p3=$("#confirm_password").val();
		var p4=$("#pwdNew").val();
		if(p3==p4){
			return true;
		}else{
			 return false;
		}
		});
	// 校驗基礎資訊表單
	$("#basicInfoForm").validate({
		errorElement:'span',
		errorClass:'help-block error-mes',
		rules:{
			name:{
				required:true,
				isName:true
			},
			sex:"required",
			birth:"required",
            mobile:{
				required:true,
				isPhone:true
			},
			email:{
				required:true,
				email:true
			}
		},
		messages:{
			name:{
				required:"請輸入中文姓名",
				isName:"姓名只能為漢字"
			},
			sex:{
				required:"請輸入性別"
			},
			birth:{
				required:"請輸入出生年月"
			},
            mobile:{
				required:"請輸入手機號碼",
				isPhone:"請填寫正確的11位手機號碼"
			},
			email:{
				required:"請輸入信箱",
				email:"請填寫正確的信箱格式"
			}
		},
	
		errorPlacement:function(error,element){
			element.next().remove();
			element.closest('.gg-formGroup').append(error);
		},
		
		highlight:function(element){
			$(element).closest('.gg-formGroup').addClass('has-error has-feedback');
		},
		success:function(label){
			var el = label.closest('.gg-formGroup').find("input");
			el.next().remove();
			label.closest('.gg-formGroup').removeClass('has-error').addClass("has-feedback has-success");
			label.remove();
		},
		submitHandler:function(form){
			alert("保存成功!");
		}
	});
	
	// 校驗修改密碼表單
	$("#modifyPwd").validate({
		onfocusout: function(element) { $(element).valid()},
		 debug:false,   // 表示校驗通過後是否直接提交表單
		 onkeyup:false, // 表示按鍵鬆開時候監聽驗證
		rules:{
			pwdOld:{
				required:true,
				minlength:6
			},
            pwdNew:{
			   required:true,
			   minlength:6,
			   isdiff:true,
			   //issame:true,
		   },
			confirm_password:{
			  required:true,
			  minlength:6,
			  issame:true,
			}
		  
		   },
		messages:{
			 	pwdOld : {
					 required:'必填',
					 minlength:$.validator.format('密碼長度要大於6')
				},
            	pwdNew:{
				   required:'必填',
				   minlength:$.validator.format('密碼長度要大於6'),
				   isdiff:'原密碼與新密碼不能重複',
				  
			   },
				confirm_password:{
				   required:'必填',
				   minlength:$.validator.format('密碼長度要大於6'),
				   issame:'新密碼要與確認新密碼一致',
				}
		
		},
		errorElement:"mes",
		errorClass:"gg-star",
		errorPlacement: function(error, element) 
		{ 
			element.closest('.gg-formGroup').append(error);

		}
	});
});