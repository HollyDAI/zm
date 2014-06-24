package com.zuma.base;

public final class C {
	
	@SuppressWarnings("unused")
	private static boolean isLogin = false;
	
	public static final class api {
		public static final String Server			= "http://115.28.238.91:8000/";
		public static final String login			= Server + "user/login";
		public static final String actList			= Server + "get/activityByCount";
		public static final String postAct			= Server + "post/activity";
		public static final String usernameCheck    = Server + "user/usernameCheck";
		public static final String register         = Server + "user/register";
		
		
	}
	

	public static final class dir {
		public static final String base				= "/sdcard/demos";
		public static final String faces			= base + "/faces";
		public static final String images			= base + "/images";
	}
	
	public static final class task {
		public static final int index				= 1001;
		public static final int login				= 1002;
		public static final int logout				= 1003;
		public static final int faceView			= 1004;
		public static final int faceList			= 1005;
		public static final int blogList			= 1006;
		public static final int blogView			= 1007;
		public static final int blogCreate			= 1008;
		public static final int commentList			= 1009;
		public static final int commentCreate		= 1010;
		public static final int customerView		= 1011;
		public static final int customerEdit		= 1012;
		public static final int fansAdd				= 1013;
		public static final int fansDel				= 1014;
		public static final int notice				= 1015;
	}
	
	public static final class err {
		public static final String network			= "网络错误";
		public static final String message			= "信息错误";
		public static final String jsonFormat		= "读取信息格式错误";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// intent & action settings
	
	public static final class intent {
		public static final class action {
			public static final String EDITTEXT		= "com.app.demos.EDITTEXT";
			public static final String EDITBLOG		= "com.app.demos.EDITBLOG";
		}
	}
	
	public static final class action {
		public static final class edittext {
			public static final int CONFIG			= 2001;
			public static final int COMMENT			= 2002;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// additional settings
	
	public static final class web {
		public static final String base				= "http://";
		public static final String index			= base + "/index.php";
		public static final String gomap			= base + "/gomap.php";
	}
}