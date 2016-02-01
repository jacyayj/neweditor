///**
// * Created on 2014-2-14
// */
//package com.weiyin.mobile.neweditor.Controller;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonDeserializer;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParseException;
//import com.google.gson.reflect.TypeToken;
//import com.juchuang.mobile.foundation.util.Base64Util;
//import com.juchuang.mobile.foundation.util.EncryptUtil;
//import com.juchuang.mobile.foundation.util.JsonParser;
//import com.juchuang.mobile.foundation.util.MobileUtil;
//import com.juchuang.mobile.foundation.util.Utility;
//import com.juchuang.mobile.healthcare_user.HealthApplication;
//import com.juchuang.mobile.healthcare_user.model.AlipayOrder;
//import com.juchuang.mobile.healthcare_user.model.Appointment;
//import com.juchuang.mobile.healthcare_user.model.AppointmentTemplate;
//import com.juchuang.mobile.healthcare_user.model.BannerAd;
//import com.juchuang.mobile.healthcare_user.model.Order;
//import com.juchuang.mobile.healthcare_user.model.Doctor;
//import com.juchuang.mobile.healthcare_user.model.Information;
//import com.juchuang.mobile.healthcare_user.model.InformationCate;
//import com.juchuang.mobile.healthcare_user.model.ListItem;
//import com.juchuang.mobile.healthcare_user.model.MedicinalInfo;
//import com.juchuang.mobile.healthcare_user.model.Page;
//import com.juchuang.mobile.healthcare_user.model.Product;
//import com.juchuang.mobile.healthcare_user.model.PushMessage;
//import com.juchuang.mobile.healthcare_user.model.Question;
//import com.juchuang.mobile.healthcare_user.model.RecommendApp;
//import com.juchuang.mobile.healthcare_user.model.Request;
//import com.juchuang.mobile.healthcare_user.model.Response;
//import com.juchuang.mobile.healthcare_user.model.Section;
//import com.juchuang.mobile.healthcare_user.model.User;
//import com.juchuang.mobile.healthcare_user.model.UserComment;
//import com.juchuang.mobile.healthcare_user.model.UserConversation;
//import com.juchuang.mobile.healthcare_user.model.UserSubject;
//import com.juchuang.mobile.healthcare_user.model.Weather;
//import com.lidroid.xutils.HttpUtils;
//import com.lidroid.xutils.exception.HttpException;
//import com.lidroid.xutils.http.RequestParams;
//import com.lidroid.xutils.http.ResponseInfo;
//import com.lidroid.xutils.http.callback.RequestCallBack;
//import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
//
//import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//
//import java.io.File;
//import java.lang.reflect.Type;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * 业务接口访问类
// *
// * @author billy lee
// *
// */
//public final class ServiceHelper {
//
//	public interface ServiceHelperListener {
//		public void onServiceFinished(String method, Response<?> response);
//	}
//
//	// 用户有效标识
//	public static final int INVALID_FLAG = 0;	// 无效
//	public static final int VALID_FLAG = 1;		// 有效
//
//	// 用户状态
//	public static final int PREREGISTRATION_STATUS = 0;	// 预开户
//	public static final int ACTIVATION_STATUS = 1;		// 已激活
//	public static final int CHARGING_STATUS = 2;		// 等待充值结果返回
//
//	//private static final String URL_TEMPLATE = "http://119.6.254.105/health_v2/index.php?r=ClientService/%s";
//
//
//	private static final HttpMethod HTTP_METHOD = HttpMethod.POST;
//	private static final int CONNECT_TIMEOUT = 10000;
//	public static String TERMINAL_ID;
//	private static final String VERSION;
//	public static final String CHANNEL;
//	public static final String PUBLIC_KEY;
//
//	private static HttpUtils gHttp;
//	private static Gson gGson;
//
//	private ServiceHelperListener mListener = null;
//
//	static {
//		Context context = HealthApplication.getInstance().getApplicationContext();
//
//		TERMINAL_ID = MobileUtil.getSIM(context);
//		if(TextUtils.isEmpty(TERMINAL_ID)) {
//			TERMINAL_ID = MobileUtil.getIMEI(context);
//		}
//		VERSION = ClientUtil.getVersionName(context);
//		CHANNEL = ClientUtil.getChannelCode(context);
//		PUBLIC_KEY = ClientUtil.getPublicKey(context);
//
//        gGson = new GsonBuilder()
//			.excludeFieldsWithoutExposeAnnotation()	// 不导出实体中没有用@Expose注解的属性
//			//.setDateFormat("yyyy-MM-dd HH:mm:ss")
//			.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
//
//				DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//				@Override
//				public Date deserialize(JsonElement json, Type typeOfT,
//						JsonDeserializationContext context)
//						throws JsonParseException {
//					try {
//						String str = json.getAsString();
//						if (str.length() == 10) {
//							return dateFormat.parse(str);
//						} else {
//							return timeFormat.parse(str);
//						}
//					} catch (ParseException e) {
//						return null;
//					}
//				}
//			})
//			.create();
//
//		gHttp = new HttpUtils(CONNECT_TIMEOUT);
//		//gHttp.configResponseTextCharset(HTTP.UTF_8);
//		//mHttp.configDefaultHttpCacheExpiry(1024 * 10);
//
//	}
//
//	public ServiceHelper(ServiceHelperListener listener) {
//		mListener = listener;
//	}
//
//	private String getUserKey() {
//		User user = ClientUtil.getUser();
//		if(user == null) {
//			return PUBLIC_KEY;
//		}
//
//		return user.userKey;
//	}
//
//	/**
//	 * 生成请求参数
//	 * @param key
//	 * @param params
//	 * @return
//	 */
//	private RequestParams buildRequest(String key, NameValuePair... params) {
//		String requestId = Utility.getUuid();
//		String timeStamp = String.format("%013d%03d", System.currentTimeMillis(), Utility.getRndNumber(0, 999));
//
//		StringBuilder str = new StringBuilder();
//		str.append(requestId);
//		File fileUpload = null;
//		ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();
//		for(NameValuePair param : params) {
//			// 文件上传识别
//			if (param.getName().equals("file")) {
//				if (!TextUtils.isEmpty(param.getValue())) {
//					fileUpload = new File(param.getValue());
//				}
//
//				continue;
//			};
//
//			str.append(param.getValue());
//			paramList.add(param);
//		}
//		str.append(timeStamp);
//		str.append(key);
//
//		Request request = new Request();
//		request.requestId = requestId;
//		request.version = VERSION;
//		request.channel = CHANNEL;
//		request.terminalId = TERMINAL_ID;
//		request.timeStamp = timeStamp;
//		request.hashCode = EncryptUtil.md5(str.toString(), false);
//
//		RequestParams requestParams = new RequestParams();
//		requestParams.addBodyParameter("request", Base64Util.encodeString(JsonParser.toJsonString(request)));
//		requestParams.addBodyParameter(paramList);
//		if (fileUpload != null) {
//			requestParams.addBodyParameter("file", fileUpload);
//		}
//
//		return requestParams;
//	}
//
//	/**
//	 * 解析响应数据
//	 * @param code		响应代码
//	 * @param data		原始数据
//	 * @param typeOfT	数据类型
//	 * @return
//	 */
//	private Response<?> parseResponse(int code, String result, Type typeOfT) {
//		if(code == HttpStatus.SC_OK) {
//			try {
//				return gGson.fromJson(result, typeOfT);
//			} catch (Exception ex) {
//                return new Response<Void>(-999, "数据解析失败。");
//			}
//		} else {
//			return new Response<Void>(-code, result);
//		}
//	}
//
//	/**
//	 * 解析推送消息
//	 * @param <T>
//	 * @param data
//	 * @param typeOfT
//	 * @return
//	 */
//	public static <T> PushMessage<T> parseMessage(String data, Type typeOfT) {
//		try {
//			return gGson.fromJson(data, typeOfT);
//		} catch (Exception ex) {
//			return null;
//		}
//	}
//
//	/**
//	 * 发送请求
//	 * @param method 		方法名
//	 * @param key 			密匙
//	 * @param responseType	响应数据类型
//	 * @param params 		请求参数
//	 */
//	private void sendRequest(final String method, String key, final Type responseType, NameValuePair... params) {
//		gHttp.send(HTTP_METHOD,
//				String.format(URL_TEMPLATE, method),
//				buildRequest(key, params),
//				new RequestCallBack<String>() {
//
//					@Override
//					public void onFailure(HttpException ex, String desc) {
//						Log.e("serviceError", desc);
//
//						int statusCode = 9999;
//						String result = "数据通信失败，请检查当前网络状态。";
//
//						if (mListener != null) {
//							mListener.onServiceFinished(method, parseResponse(statusCode, result, responseType));
//						}
//					}
//
//					@Override
//					public void onSuccess(ResponseInfo<String> responseInfo) {
//						int statusCode = responseInfo.statusCode;
//						String result  = responseInfo.result;
//
//						Log.i(method, TextUtils.isEmpty(result) ? "null" : result);
//
//						if (mListener != null) {
//							mListener.onServiceFinished(method, parseResponse(statusCode, result, responseType));
//						}
//					}
//
//				});
//	}
//
//	/**
//	 * 登录
//	 * @param userNo
//	 * @param checkCode
//	 * @return
//	 */
//	public void login(String userNo, String checkCode) {
//		sendRequest("login", PUBLIC_KEY, new TypeToken<Response<User>>(){}.getType(),
//				new BasicNameValuePair("checkCode", checkCode),
//				new BasicNameValuePair("userNo", userNo));
//	}
//
//
//
//	/**
//	 * 分页接口示例
//	 * @param userNo
//	 * @param page
//	 * @return
//	 */
//	public void getInformation(int pageSize, int page) {
//		if (ClientUtil.getUser() == null) {
//			sendRequest("getinformation", PUBLIC_KEY, new TypeToken<Response<Page<Information>>>(){}.getType(),
//					new BasicNameValuePair("page", String.valueOf(page)),
//					new BasicNameValuePair("pageSize", String.valueOf(pageSize)));
//		} else {
//			sendRequest("getinformation", getUserKey(), new TypeToken<Response<Page<Information>>>(){}.getType(),
//					new BasicNameValuePair("page", String.valueOf(page)),
//					new BasicNameValuePair("pageSize", String.valueOf(pageSize)),
//					new BasicNameValuePair("userNo",ClientUtil.getUser().userNo));
//		}
//	}
//
//
//	/**
//	 * 带图片上传示例
//	 * @param userNo
//	 * @param subjectId
//	 * @param type
//	 * @param content
//	 * @param filePath
//	 */
//	public void postNewConversation(String userNo, long subjectId, int type, String content, String filePath) {
//		sendRequest("postnewconversation", getUserKey(), new TypeToken<Response<UserConversation>>(){}.getType(),
//				new BasicNameValuePair("content", content),
//				new BasicNameValuePair("subjectId", String.valueOf(subjectId)),
//				new BasicNameValuePair("type", String.valueOf(type)),
//				new BasicNameValuePair("userNo", userNo),
//				new BasicNameValuePair("file", filePath));
//	}
//
//}
